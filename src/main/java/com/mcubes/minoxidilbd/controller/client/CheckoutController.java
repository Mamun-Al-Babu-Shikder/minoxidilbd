package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.configuration.security.EncryptDecrypt;
import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.ClientOrder;
import com.mcubes.minoxidilbd.entity.OrderItem;
import com.mcubes.minoxidilbd.entity.Product;
import com.mcubes.minoxidilbd.entity.ShippingInformation;
import com.mcubes.minoxidilbd.model.CartItem;
import com.mcubes.minoxidilbd.model.OrderInformation;
import com.mcubes.minoxidilbd.service.CartService;
import com.mcubes.minoxidilbd.service.ClientOrderService;
import com.mcubes.minoxidilbd.service.ClientService;
import com.mcubes.minoxidilbd.service.ProductService;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintValidatorContext;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Created by A.A.MAMUN on 10/21/2020.
 */
@Controller
public class CheckoutController {

    private static final Logger LOG = Logger.getLogger(CheckoutController.class.getName());

    @Autowired
    private CommonData commonData;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    @Autowired
    private ClientOrderService clientOrderService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/checkout")
    public String checkout(HttpServletRequest request, RedirectAttributes redirectAttributes){
        //model.addAttribute("login", principal!=null);
        String code = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("cart")) {
                    code = c.getValue();
                    break;
                }
            }
        }
        redirectAttributes.addFlashAttribute("code", code);
        return code == null ? "redirect:/cart" : "redirect:/checkouts";
    }


    @RequestMapping("/buy")
    public String buyProduct(RedirectAttributes redirectAttributes, @RequestParam(name = "p") long pid){

        if(productService.isProductExist(pid) && productService.getStockByProductId(pid)>0) {
            String cart = "_" + pid + ":" + 1;
            try {
                String code = encryptDecrypt.encrypt(cart);
                redirectAttributes.addFlashAttribute("code", code);
                return "redirect:/checkouts";
            } catch (Exception e) {
                LOG.warning("# " + e.getMessage());
                return "client/pages/checkout_error";
            }
        }
        return "client/pages/checkout_error";
    }


    @GetMapping("/checkouts")
    public String checkoutProducts(Principal principal, Model model){


        if(model.getAttribute("code")==null ){
            return "client/pages/checkout_error";
        }

        model.addAttribute("login", principal!=null);
        if(principal!=null){
            System.out.println("# Email : " + principal.getName());
            model.addAttribute("user_email", principal.getName());
            model.addAttribute("user_name", clientService.getClientNameByEmail(principal.getName()));
        }

        try{
            String code = model.getAttribute("code").toString();
            String decryptCartItems = encryptDecrypt.decrypt(code);
            Map<Long, Integer> map = cartService.extractProductIdWithQuantity(decryptCartItems);
            if(map.isEmpty()){
                return "redirect:/cart";
            }else{
                double subtotal = 0;
                Set<Long> productIds = map.keySet();
                List<CartItem> cartItems = new ArrayList<>();
                for (long pid : productIds){
                    Product product = productService.getProductForCartItemsByProductId(pid);
                    if(product!=null){
                        CartItem item = new CartItem(product.getId(), product.getImage(), product.getName(),
                                product.getCurrentPrice(), map.get(pid));
                        subtotal += item.getProductPrice() * item.getQuantity();
                        cartItems.add(item);
                    }
                }
                if(cartItems.size()==0){
                    return "redirect:/cart";
                }
                LOG.info("# Cart Items : " + cartItems);
                LOG.info("# Subtotal : " + subtotal);
                model.addAttribute("cartItems", cartItems);
                model.addAttribute("subtotal", subtotal);
                model.addAttribute("code", code);
                return "client/pages/checkout";
            }

        }catch (Exception e){
            LOG.warning("# Ex : "+e.toString());
            return "client/pages/checkout_error";
        }
    }


    @ResponseBody
    @RequestMapping(value = "/place_order", method = RequestMethod.POST)
    public ResponseEntity<Object> placeOrder(Principal principal, HttpServletRequest request, HttpServletResponse response,
                                     @ModelAttribute(name = "orderInformation") OrderInformation information){

        String orderSuccess;

        if(principal!=null){
            information.setContactNumber(principal.getName());
        }

        Function<String, Boolean> isValidContact = new Function<String, Boolean>() {
            @Override
            public Boolean apply(String s) {
                return s.matches("(\\+8801|01)[1356789][0-9]{8}") || s.matches(
                        "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})"
                );
            }
        };

        if(information==null || (information.getLastName().trim().length()==0 ||
                information.getAddress().trim().length()==0 ||
                information.getCity().trim().length()==0 ||
                information.getCountry().trim().length()==0 ||
                information.getPostalCode().trim().length()==0) || !isValidContact.apply(information.getContactNumber())){
                LOG.warning("# Invalid Information");

            return ResponseEntity.ok("invalid_info");
        }

        try{
            information.setCartItemsCode(information.getCartItemsCode().replace(' ', '+'));
            String decryptCartItems = encryptDecrypt.decrypt(information.getCartItemsCode());
            Map<Long, Integer> map = cartService.extractProductIdWithQuantity(decryptCartItems);

            Set<Long> productIds = map.keySet();
            List<OrderItem> orderItems = new ArrayList<>();
            for (long pid : productIds) {
                Product product = productService.getProductForCartItemsByProductId(pid);
                if (product != null) {
                    OrderItem item = new OrderItem(product.getId(), product.getImage(), product.getName(),
                            product.getCurrentPrice(), map.get(pid));
                    orderItems.add(item);
                }
            }
            ShippingInformation shippingInformation = new ShippingInformation();
            shippingInformation.setFirstName(information.getFirstName());
            shippingInformation.setLastName(information.getLastName());
            shippingInformation.setAddress(information.getAddress());
            shippingInformation.setApartment(information.getApartment());
            shippingInformation.setCity(information.getCity());
            shippingInformation.setCountry(information.getCountry());
            shippingInformation.setPostalCode(information.getPostalCode());

            orderSuccess = clientOrderService.submitOrder(orderItems, shippingInformation,
                    principal==null ? information.getContactNumber() : principal.getName(),principal!=null);

        }catch (Exception e){
            LOG.warning("# Invalid Cart Items HashCode : " + e.getMessage());
            return ResponseEntity.ok("failed");
        }

        if(orderSuccess!=null){
            LOG.info("# order_number : "+orderSuccess);
            cartService.clearCartCookiesAfterOrderComplete(request, response, information.getCartItemsCode());
        }

        try {
            return ResponseEntity.ok(encryptDecrypt.encrypt(orderSuccess));
        }catch (Exception e){
            LOG.warning("# Ex : " + e.getMessage());
            return ResponseEntity.ok("order_number_encryption_failed");
        }
        //return "success";
    }

    @RequestMapping("/order-complete")
    public String orderComplete(Principal principal, Model model, @RequestParam(name = "q") String encryptedOrderNumber)
    {
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        try {
            encryptedOrderNumber = encryptedOrderNumber.replace(' ', '+');
            String decryptedOrderNumber = encryptDecrypt.decrypt(encryptedOrderNumber);
            model.addAttribute("encryptedOrderNumber", encryptedOrderNumber);
            model.addAttribute("orderNumber", decryptedOrderNumber);
        }catch (Exception e){
            LOG.warning("# Ex : " + e.getMessage());
            return "redirect:/error";
        }
        return "client/pages/order_complete";
    }

    @RequestMapping("/invoice")
    public String invoice(@RequestParam(name = "q") String encryptedOrderNumber, Model model){
        commonData.setContactAndSocialInfo(model);
        try {
            encryptedOrderNumber = encryptedOrderNumber.replace(' ', '+');
            String decryptedOrderNumber = encryptDecrypt.decrypt(encryptedOrderNumber);
            long orderId = Long.parseLong(decryptedOrderNumber.substring(decryptedOrderNumber.indexOf('-')+1));
            LOG.info("# Order ID : " + orderId);
            ClientOrder orderInfo = clientOrderService.getClientOrderByOrderId(orderId);
            model.addAttribute("orderInfo", orderInfo);
        }catch (Exception e){
            LOG.warning("# Ex : " + e.getMessage());
            return "redirect:/error";
        }
        return "invoice";
    }

}
