package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.configuration.security.EncryptDecrypt;
import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.data.ConstantKey;
import com.mcubes.minoxidilbd.entity.*;
import com.mcubes.minoxidilbd.repository.ProductRepository;
import com.mcubes.minoxidilbd.service.*;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/7/2020.
 */
@Controller
public class HomeController {

    private static final Logger LOG = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private CommonData commonData;
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientSayService clientSayService;
    @Autowired
    private AdsService adsService;
    @Autowired
    private SubscriberService subscriberService;


    private static final int MAX_PAGE = 10000000;

    @RequestMapping(value = {"/","/home"})
    public String homePage(Model model, Principal principal){

        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        List<Product> newProducts = productService.getNewProducts();
        List<Product> bestSellingProducts = productService.getBestSellingProducts();
        List<Product> highRatedProducts = productService.getHighRatedProducts();
        List<Product> topDiscountedProducts = productService.getTopDiscountedProducts();
        List<Product> mostViewedProduct = productService.getMostViewedProducts();

        model.addAttribute("newProducts", newProducts);
        model.addAttribute("bestSellingProducts", bestSellingProducts);
        model.addAttribute("highRatedProducts", highRatedProducts);
        model.addAttribute("topDiscountedProducts", topDiscountedProducts);
        model.addAttribute("mostViewedProduct", mostViewedProduct);

        model.addAttribute("sliderAds", adsService.getSliderAds());
        model.addAttribute("bannerAds1", adsService.getSingleAdsById(ConstantKey.BANNER_AD1));
        model.addAttribute("bannerAds2", adsService.getSingleAdsById(ConstantKey.BANNER_AD2));
        model.addAttribute("bannerAds3", adsService.getSingleAdsById(ConstantKey.BANNER_Ad3));

        List<ClientSay> clientSays = clientSayService.getClientCommentsForOurServiceAndProduct();
        model.addAttribute("clientSays", clientSays);

        return "client/pages/index";
    }


    @RequestMapping("/search")
    public String searchResult(@RequestParam(name = "q") String search,
                               @RequestParam(name = "page", defaultValue = "1") int page,
                               Principal principal, Model model) {
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        LOG.info("# Search for : " + search);
        String q = search;
        search = search.trim();
        if(search.length()>20){
            search = search.substring(0, 20);
            q = search + "...";
        }
        model.addAttribute("q", q);
        if(page<1 || page>MAX_PAGE){
            page = 1; // default page = 1
        }
        Page<Product> productPage = productService.searchProduct(search, --page);
        model.addAttribute("productPage", productPage);
        return "client/pages/search_result";
    }

    @ResponseBody
    @RequestMapping("/subscribe")
    @SuppressWarnings("deprecation")
    public String subscribe(@RequestParam String email){
        if(email!=null && email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")){
            Subscriber subscriber = new Subscriber(email, new Date().toLocaleString());
            return subscriberService.subscribe(subscriber);
        }else {
            return "You have entered invalid email address.";
        }
    }

    /*
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @ResponseBody
    @RequestMapping("/encrypt")
    public String encrypt(@RequestParam String v)
    {
        try {
            String value = encryptDecrypt.encrypt(v);
            return value;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/decrypt")
    public String decrypt(@RequestParam String v){
        System.out.println("# val : "+v);
        try{
            return encryptDecrypt.decrypt(v);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
     */




    /*
    @Autowired
    private ClientOrderService clientOrderService;

    @ResponseBody
    @RequestMapping("/client_order")
    public Page<ClientOrder> fetchClientOrder( @RequestParam(name = "status", defaultValue = "Pending") String status) {
        ClientOrder.OrderStatus  orderStatus = commonData.getOrderStatusMap().get(status.toLowerCase());
        System.out.println("# Order Status : " + orderStatus);
        return clientOrderService.fetchClientOrderByStatus(orderStatus);
    }


    @ResponseBody
    @RequestMapping("/thread-test")
    public synchronized String threadTest() throws InterruptedException {
        for(int i=1; i<=100; i++){
            System.out.println(Thread.currentThread().getName()+" --> " + i);
            Thread.sleep(100);
        }
        return "running....";
    }
     */

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "running...";
    }



}
