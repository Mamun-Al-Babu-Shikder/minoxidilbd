package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.configuration.security.EncryptDecrypt;
import com.mcubes.minoxidilbd.entity.Product;
import com.mcubes.minoxidilbd.model.CompareItem;
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
public class CompareService {


    @Autowired
    private EncryptDecrypt encryptDecrypt;
    @Autowired
    private ProductService productService;

    private static final Logger LOG = Logger.getLogger(CompareService.class.getName());
    private static final int cookieAge = 60*60*24*30;

    public String writeCompareProductToCookie(HttpServletRequest request, HttpServletResponse response, long pid){
        String searchId = "_" + pid + ":";
        String compareList = readCompareProductFromCookie(request);
        if(compareList.contains(searchId)){
            return "exist";
        }
        try {
            Cookie cookie = new Cookie("compare", encryptDecrypt.encrypt(compareList + searchId));
            cookie.setMaxAge(cookieAge);
            response.addCookie(cookie);
        }catch (Exception e){
            LOG.warning(e.getMessage());
            return "failed";
        }
        return "success";
    }

    public String readCompareProductFromCookie(HttpServletRequest request){
        String compareList = "";
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("compare")) {
                    compareList = cookies[i].getValue();
                    if (compareList.length() != 0) {
                        try {
                            compareList = encryptDecrypt.decrypt(compareList);
                        } catch (Exception e) {
                            LOG.warning(e.getMessage());
                            compareList = "";
                        }
                    }
                    break;
                }
            }
        }
        LOG.info("# Compare List : " + compareList);
        return compareList;
    }


    public List<CompareItem> compareProducts(HttpServletRequest request){
        List<CompareItem> list = new ArrayList<>();

        try {
            String compare = readCompareProductFromCookie(request);
            if (compare.length() != 0) {
                String pid[] = compare.split(":");
                for (int i = 0; i < pid.length; i++) {
                    long id = Long.parseLong(pid[i].substring(1));
                    Product product = productService.getProductForCompareListByProductId(id);
                    if (product != null) {
                        list.add(new CompareItem(product.getId(), product.getName(), product.getCurrentPrice(),
                                product.getImage(), product.getStock(), product.getDescription()));
                    }
                }
            }
        }catch (Exception e){
            LOG.warning(e.getMessage());
            list.clear();
        }
        return list;
    }


    public boolean removeCompareProductFormCookie(HttpServletRequest request, HttpServletResponse response, long pid){
        String compareList = readCompareProductFromCookie(request);
        String searchId = "_" + pid + ":";
        if(compareList.contains(searchId)){
            try {
                String afterReplace = compareList.replace(searchId, "");
                Cookie cookie = new Cookie("compare", encryptDecrypt.encrypt(afterReplace));
                cookie.setMaxAge(cookieAge);
                response.addCookie(cookie);
            }catch (Exception e){
                LOG.warning(e.getMessage());
                return false;
            }
        }
        return true;
    }
}
