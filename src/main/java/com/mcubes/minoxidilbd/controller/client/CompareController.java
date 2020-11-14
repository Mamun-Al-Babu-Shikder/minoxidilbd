package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.model.CompareItem;
import com.mcubes.minoxidilbd.model.WishItem;
import com.mcubes.minoxidilbd.service.CompareService;
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
public class CompareController {

    @Autowired
    private CommonData commonData;
    @Autowired
    private CompareService compareService;

    private static final Logger LOG = Logger.getLogger(CompareController.class.getName());

    @RequestMapping("/compare")
    public String compareList(Principal principal, Model model){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        return "client/pages/compare";
    }

    @ResponseBody
    @RequestMapping("/add_product_tp_comparelist")
    public String addProductToCompareList(HttpServletRequest request, HttpServletResponse response, long pid){
        try{
            if(compareService.compareProducts(request).size()<=5) {
                return compareService.writeCompareProductToCookie(request, response, pid);
            }
            return "full";
        }catch (Exception e){
            LOG.warning(e.getMessage());
            return "failed";
        }
    }

    @ResponseBody
    @RequestMapping("/read_product_from_conparelist")
    public ResponseEntity<?> fetchCompareList(HttpServletRequest request){
        List<CompareItem> compareItems = compareService.compareProducts(request);
        //LOG.info("# Compare Product : "+compareItems);
        return ResponseEntity.ok(compareItems);
    }


    //@ResponseBody
    @RequestMapping("/delete_product_from_comparelist")
    public String deleteProductFromWishList(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam long pid){
        LOG.info("# Remove Compare Product ID : " + pid);
        try {
            compareService.removeCompareProductFormCookie(request, response, pid);
        }catch (Exception e){
            LOG.warning(e.getMessage());
            //return false;
        }
        return "redirect:/compare";
    }

}

