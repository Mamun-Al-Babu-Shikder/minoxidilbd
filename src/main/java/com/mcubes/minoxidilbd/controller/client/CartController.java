package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.Product;
import com.mcubes.minoxidilbd.model.CartItem;
import com.mcubes.minoxidilbd.model.CartUpdate;
import com.mcubes.minoxidilbd.service.CartService;
import com.mcubes.minoxidilbd.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Controller
public class CartController {

    private static final Logger LOG = Logger.getLogger(CartController.class.getName());

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CommonData commonData;


    @RequestMapping("/cart")
    public String cart(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        return "client/pages/cart";
    }

    @ResponseBody
    @RequestMapping(value = "/add_product_to_cart", method = RequestMethod.POST)
    public String addToCart(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam long pid, @RequestParam int qyt){

        if(productService.isProductExist(pid) && qyt>0 && productService.getStockByProductId(pid)>0) {
            cartService.writeCartItemsToCookie(request, response, pid, qyt);
            return  "success";
        }
        LOG.info("Product not found according to product id : " + pid + " or invalid quantity or not available.");
        //return new ResponseEntity<Object>("Product not found", HttpStatus.BAD_REQUEST);
        return "failed";
    }


    @ResponseBody
    @RequestMapping("/read_product_from_cart")
    public ResponseEntity<?> readCart(HttpServletRequest request){

        Map<Long, Integer> map = cartService.getProductIdWithQuantity(request);
        Set<Long> productIds = map.keySet();
        List<CartItem> cartItems = new ArrayList<>();
        for (long pid : productIds){
            // Product product1 = productService.getAvailableProductForCartItemsByProductId(pid); // checking stock available
            Product product = productService.getProductForCartItemsByProductId(pid);
            if(product!=null){
                CartItem item = new CartItem(product.getId(), product.getImage(), product.getName(),
                        product.getCurrentPrice(), map.get(pid));
                cartItems.add(item);
            }
        }
        LOG.info(cartItems.toString());
        return new ResponseEntity(cartItems, HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping("/delete_product_from_cart")
    public ResponseEntity<?> deleteCartItem(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam long pid){
        cartService.deleteCartItemFromCookie(request, response, pid);
        return ResponseEntity.ok("success");
    }


    @ResponseBody
    @RequestMapping("/clear_cart_items")
    public boolean clearCart(HttpServletResponse response){
        return cartService.clearCartCookies(response);
    }

    @ResponseBody
    @RequestMapping("/cart_update")
    public boolean updateCart(HttpServletRequest request, HttpServletResponse response,
                              @ModelAttribute CartUpdate cartUpdate){
        LOG.info("# update : " + cartUpdate);
        return cartService.updateCartItems(request, response, cartUpdate);
    }

}
