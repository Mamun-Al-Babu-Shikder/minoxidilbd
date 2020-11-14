package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.configuration.security.EncryptDecrypt;
import com.mcubes.minoxidilbd.controller.client.CartController;
import com.mcubes.minoxidilbd.model.CartUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/12/2020.
 */
@Service
public class CartService {

    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private static final int cookieAge = 60*60*24*30;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    public void writeCartItemsToCookie(HttpServletRequest request, HttpServletResponse response, long pid, int qyt){
        Cookie cookie = null;
        String value = "_" + pid + ":" + qyt;
        String cart = readCookie(request, "cart");
        if(cart!=null && cart.length()!=0){
            String searchPid = "_" + pid + ":";
            if(cart.contains(searchPid)){
                int index = cart.indexOf(searchPid);
                int qtyInitPoint = index + searchPid.length();
                StringBuffer quantity = new StringBuffer();
                for (int i=qtyInitPoint; i<cart.length(); i++){
                    char c = cart.charAt(i);
                    if(c=='_')
                        break;
                    quantity.append(c);
                }
                value = cart.replace(searchPid + quantity, searchPid +
                        (Integer.parseInt(quantity.toString()) + qyt));
                LOG.info("# Cart : " + value);
                //cookie.setValue(cart);
            }else {
                value = cart + "_" + pid + ":" + qyt;
                //cookie.setValue(cart + "_" + pid + ":" + qyt);
            }
        }

        try {
            cookie = new Cookie("cart", encryptDecrypt.encrypt(value));
            cookie.setMaxAge(cookieAge);
        }catch (Exception e){
            LOG.warning("# Problem at Write Cookie : " + e.getMessage());
            cookie.setMaxAge(0);
        }finally {
            response.addCookie(cookie);
        }
    }


    public void deleteCartItemFromCookie(HttpServletRequest request, HttpServletResponse response, long pid){

        String cart = readCookie(request, "cart");
        String deletePid = "_" + pid + ":";
        if(cart.contains(deletePid)){
            int index = cart.indexOf(deletePid);
            int qtyInitPoint = index + deletePid.length();
            StringBuffer quantity = new StringBuffer();
            for (int i=qtyInitPoint; i<cart.length(); i++){
                char c = cart.charAt(i);
                if(c=='_')
                    break;
                quantity.append(c);
            }
            cart = cart.replace(deletePid + quantity, "");
            LOG.info("Cart after delete : " + cart);
            try {
                Cookie cookie = new Cookie("cart", encryptDecrypt.encrypt(cart));
                cookie.setMaxAge(cookieAge);
                response.addCookie(cookie);
            }catch (Exception e){
                LOG.warning(e.getMessage());
            }
        }
    }

    public String readCookie(HttpServletRequest request, String name){
        String value = "";
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie c : cookies){
                if(c.getName().equals(name)){
                    try {
                        value = c.getValue();
                        if(value.length()!=0)
                           value =  encryptDecrypt.decrypt(value);
                    }catch (Exception e){
                        LOG.warning("# Problem at Read Cookie : " + e.getMessage());
                        return null;
                    }
                    break;
                }
            }
        }
        return value;
    }


    public String readEncryptedCartCookie(HttpServletRequest request){
        String value = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie c : cookies){
                if(c.getName().equals("cart")){
                    try {
                        value = c.getValue();
                    }catch (Exception e){
                        LOG.warning("# Problem at Read Cookie : " + e.getMessage());
                        return null;
                    }
                    break;
                }
            }
        }
         return value;
    }

    public Map<Long, Integer> getProductIdWithQuantity(HttpServletRequest request){
        String cart = readCookie(request, "cart");
        return extractProductIdWithQuantity(cart);
    }

    public Map<Long, Integer> extractProductIdWithQuantity(String cart){

        Map<Long, Integer> map = new HashMap<>();
        try {
            if (cart != null && cart.length() != 0) {
                String[] pidQtyPairs = cart.substring(1).split("_");
                for (String pidQtyPair : pidQtyPairs) {
                    String[] keyVal = pidQtyPair.split(":");
                    map.put(Long.valueOf(keyVal[0]), Integer.parseInt(keyVal[1]));
                }
            }
        }catch (Exception e){
            LOG.warning(e.getMessage());
            map.clear();
        }
        return map;
    }



    public boolean clearCartCookies(HttpServletResponse response){
        try{
            Cookie cookie = new Cookie("cart", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return true;
        }catch (Exception e){
            LOG.warning(e.getMessage());
            return false;
        }
    }

    public void clearCartCookiesAfterOrderComplete(HttpServletRequest request, HttpServletResponse response,
                                                   String code){
        String cartItemsHashCode = readEncryptedCartCookie(request);
        if(cartItemsHashCode!=null && cartItemsHashCode.equals(code)){
           clearCartCookies(response);
        }
    }

    public boolean updateCartItems(HttpServletRequest request, HttpServletResponse response, CartUpdate update){
        try{
            String cart = readCookie(request, "cart");
            long[] pid = update.getPid();
            int[] qyt = update.getQuantity();
            for(int i=0; i<pid.length; i++){
                String searchPid = "_" + pid[i] + ":";
                if(cart.contains(searchPid)){
                    int index = cart.indexOf(searchPid);
                    int qtyInitPoint = index + searchPid.length();
                    StringBuffer quantity = new StringBuffer();
                    for (int j=qtyInitPoint; j<cart.length(); j++){
                        char c = cart.charAt(j);
                        if(c=='_')
                            break;
                        quantity.append(c);
                    }
                    cart = cart.replace(searchPid + quantity, searchPid + qyt[i]);
                    LOG.info("# Updated_cart_items : " + cart);
                }
            }
            Cookie cookie = new Cookie("cart", encryptDecrypt.encrypt(cart));
            cookie.setMaxAge(cookieAge);
            response.addCookie(cookie);
            return true;
        }catch (Exception e){
            LOG.warning(e.getMessage());
            return false;
        }
    }

}
