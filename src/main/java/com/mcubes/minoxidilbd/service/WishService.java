package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.configuration.security.EncryptDecrypt;
import com.mcubes.minoxidilbd.entity.Product;
import com.mcubes.minoxidilbd.model.WishItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/13/2020.
 */
@Service
public class WishService {

    @Autowired
    private EncryptDecrypt encryptDecrypt;
    @Autowired
    private ProductService productService;

    private static final Logger LOG = Logger.getLogger(WishService.class.getName());
    private static final int cookieAge = 60*60*24*30;

    public String writeWishToCookie(HttpServletRequest request, HttpServletResponse response, long pid){
        String searchId = "_" + pid + ":";
        String wishList = readWishFromCookie(request);
        if(wishList.contains(searchId)){
            return "exist";
        }
        try {
            Cookie cookie = new Cookie("wish", encryptDecrypt.encrypt(wishList + searchId));
            cookie.setMaxAge(cookieAge);
            response.addCookie(cookie);
        }catch (Exception e){
            LOG.warning(e.getMessage());
            return "failed";
        }
        return "success";
    }

    public String readWishFromCookie(HttpServletRequest request){
        String wishList = "";
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("wish")) {
                    wishList = cookies[i].getValue();
                    if (wishList.length() != 0) {
                        try {
                            wishList = encryptDecrypt.decrypt(wishList);
                        } catch (Exception e) {
                            LOG.warning(e.getMessage());
                            wishList = "";
                        }
                    }
                    break;
                }
            }
        }
        LOG.info("# Wish List : " + wishList + ", Length : "+wishList.length());
        return wishList;
    }


    public List<WishItem> wishesProducts(HttpServletRequest request){

        List<WishItem> list = new ArrayList<>();
        try {
            String wish = readWishFromCookie(request);
            if (wish.length() != 0) {
                String pid[] = wish.split(":");
                for (int i = 0; i < pid.length; i++) {
                    long id = Long.parseLong(pid[i].substring(1));
                    Product product = productService.getProductForWishItemsByProductId(id);
                    if (product != null) {
                        list.add(new WishItem(product.getId(), product.getName(), product.getImage(),
                                product.getCurrentPrice()));
                    }
                }
            }
        }catch (Exception e){
            LOG.warning(e.getMessage());
            list.clear();
        }
        return list;
    }

    public boolean removeWishesProductFormCookie(HttpServletRequest request, HttpServletResponse response, long pid){
        String wishList = readWishFromCookie(request);
        String searchId = "_" + pid + ":";
        if(wishList.contains(searchId)){
            try {
                Cookie cookie = new Cookie("wish", encryptDecrypt.encrypt(
                        wishList.replace(searchId, ""))
                );
                cookie.setMaxAge(cookieAge);
                response.addCookie(cookie);
            }catch (Exception e) {
                LOG.info(e.getMessage());
                return false;
            }
        }
        return true;
    }

}
