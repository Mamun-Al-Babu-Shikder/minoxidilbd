package com.mcubes.minoxidilbd.data;

import com.mcubes.minoxidilbd.entity.Ads;
import com.mcubes.minoxidilbd.entity.Category;
import com.mcubes.minoxidilbd.entity.ClientOrder;
import com.mcubes.minoxidilbd.service.AdsService;
import com.mcubes.minoxidilbd.service.CategoryService;
import com.mcubes.minoxidilbd.service.KeyValuePairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Component
public class CommonData {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private KeyValuePairService keyValuePairService;
    @Autowired
    private AdsService adsService;


    public void setCategoryList(Model model){

        List<Category> categoryList = categoryService.fetchAllCategory();
        int size = 4;
        List<Category>[] categoryArray = new List[size];
        for (int i=0; i<categoryArray.length; i++){
            categoryArray[i] = new ArrayList<>();
        }
        for (int i=0; i<categoryList.size(); i++){
            categoryArray[i%size].add(categoryList.get(i));
        }
        for (int i=0; i<size; i++){
            model.addAttribute("categoryList"+i, categoryArray[i]);
        }
    }


    public void setContactAndSocialInfo(Model model){

        model.addAttribute(ConstantKey.FACEBOOK, keyValuePairService.getValue(ConstantKey.FACEBOOK));
        model.addAttribute(ConstantKey.TWITTER, keyValuePairService.getValue(ConstantKey.TWITTER));
        model.addAttribute(ConstantKey.LINKED_IN, keyValuePairService.getValue(ConstantKey.LINKED_IN));
        model.addAttribute(ConstantKey.YOUTUBE, keyValuePairService.getValue(ConstantKey.YOUTUBE));
        model.addAttribute(ConstantKey.INSTAGRAM, keyValuePairService.getValue(ConstantKey.INSTAGRAM));

        /* website name define here, we can define it using database */
        model.addAttribute(ConstantKey.WEBSITE_NAME, "USA Store");
        // model.addAttribute(ConstantKey.WEBSITE_NAME, keyValuePairService.getValue(ConstantKey.WEBSITE_NAME));

        model.addAttribute(ConstantKey.LOCATION, keyValuePairService.getValue(ConstantKey.LOCATION));
        model.addAttribute(ConstantKey.EMAIL, keyValuePairService.getValue(ConstantKey.EMAIL));
        model.addAttribute(ConstantKey.PHONE, keyValuePairService.getValue(ConstantKey.PHONE));

        /* add ads for inside category */
        List<Ads> adsList = adsService.getAdsForInsideCategory();
        model.addAttribute("inside_category_ads", adsList);
    }

    public Map<String, ClientOrder.OrderStatus> getOrderStatusMap(){
        Map<String, ClientOrder.OrderStatus> statusMap = new HashMap<>();
        statusMap.put("pending", ClientOrder.OrderStatus.Pending);
        statusMap.put("processing", ClientOrder.OrderStatus.Processing);
        statusMap.put("transit", ClientOrder.OrderStatus.Transit);
        statusMap.put("complete", ClientOrder.OrderStatus.Complete);
        statusMap.put("failed", ClientOrder.OrderStatus.Failed);
        statusMap.put("canceled", ClientOrder.OrderStatus.Canceled);
        return statusMap;
    }

}
