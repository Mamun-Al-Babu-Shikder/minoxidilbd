package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.model.WishItem;
import com.mcubes.minoxidilbd.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/13/2020.
 */
@Controller
public class WishController {

    private static final Logger LOG = Logger.getLogger(WishController.class.getName());

    @Autowired
    private CommonData commonData;
    @Autowired
    private WishService wishService;

    @RequestMapping("/wishlist")
    public String wishList(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        return "client/pages/wishlist";
    }


    @ResponseBody
    @RequestMapping("/add_product_to_wishlist")
    public String addProductToWishList(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam long pid){
        try{
            return wishService.writeWishToCookie(request, response, pid);
        }catch (Exception e){
            LOG.warning(e.getMessage());
            return "failed";
        }
    }

    @ResponseBody
    @RequestMapping("/read_product_from_wishlist")
    public ResponseEntity<?> fetchWishList(HttpServletRequest request){
        List<WishItem> wishItems = wishService.wishesProducts(request);
        LOG.info("# Wish Product : "+wishItems);
        return ResponseEntity.ok(wishItems);
    }

    @ResponseBody
    @RequestMapping("/delete_product_from_wish")
    public boolean deleteProductFromWishList(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam long pid){
        LOG.info("# Remove Wish Product ID : " + pid);
        try {
            return wishService.removeWishesProductFormCookie(request, response, pid);
        }catch (Exception e){
            LOG.warning(e.getMessage());
            return false;
        }
    }


}
