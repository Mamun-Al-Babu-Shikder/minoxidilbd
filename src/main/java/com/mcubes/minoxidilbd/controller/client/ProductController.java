package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.Product;
import com.mcubes.minoxidilbd.repository.ProductRepository;
import com.mcubes.minoxidilbd.service.CategoryService;
import com.mcubes.minoxidilbd.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Controller
public class ProductController {

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    @Autowired
    private CommonData commonData;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    private static final int MAX_PAGE = 10000000;
    private static final int MAX_COUNT = 60;

    @RequestMapping("/products")
    public String productAccordingToCategory(
            Principal principal,
            Model model, @RequestParam(name = "c") long category,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "count", defaultValue = "1") int count,
            @RequestParam(name = "sort_by", defaultValue = "title-ascending" ) String sort_by,
            @RequestParam(name = "view", defaultValue = "grid") String view
    ){

        model.addAttribute("login", principal!=null);

        LOG.info("# Category : "+category+", Page : "+page+", Count : "+count+", Sort_by : "+
                sort_by+", View : "+view);


        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        boolean desc = false;
        ProductService.OrderBy order = ProductService.OrderBy.NAME;

        if(page<1 || page>MAX_PAGE){
            page = 1; // default page = 1
        }

        if(count<4 || count>MAX_COUNT){
            count = 12; // default count = 4
        }

        if(sort_by.equals("title-ascending")){
            order = ProductService.OrderBy.NAME;
        }else if(sort_by.equals("title-descending")){
            desc = true;
            order = ProductService.OrderBy.NAME;
        }else if(sort_by.equals("price-ascending")){
            order = ProductService.OrderBy.PRICE;
        }else if(sort_by.equals("price-descending")){
            desc = true;
            order = ProductService.OrderBy.PRICE;
        }else if(sort_by.equals("date-ascending")){
            order = ProductService.OrderBy.ID;
        }else if(sort_by.equals("date-descending")){
            desc = true;
            order = ProductService.OrderBy.ID;
        }else if(sort_by.equals("best-selling")){
            desc = true;
            order = ProductService.OrderBy.BEST_SELL;
        }

        Page<Product> productPage = productService.getProductByCategory(category, --page, count, order , desc);

        LOG.info(productPage.toString());

        model.addAttribute("categoryName", categoryService.getCategoryNameById(category));
        model.addAttribute("sort_by", sort_by);
        model.addAttribute("productPage", productPage);

        return "client/pages/products";
    }



    @RequestMapping("/product")
    public String productDetails(Principal principal, Model model, @RequestParam(name = "p") long pid){

        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        Optional<Product> optionalProduct = productService.getProductByProductId(pid);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            model.addAttribute("product", product);
            List<Product> relatedProducts = productService.getRelatedProduct(product.getId(), product.getCategory());
            model.addAttribute("categoryName", categoryService.getCategoryNameById(product.getCategory()));
            model.addAttribute("relatedProducts", relatedProducts);
           // System.out.println("# Related : "+relatedProducts);
            productService.increaseProductView(product.getViews() + 1, pid);
        }else{
            return "error";
        }
        return "client/pages/product";
    }

    @RequestMapping("/collections")
    public String collectionsAll(
            Principal principal,
            Model model, @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "count", defaultValue = "1") int count,
            @RequestParam(name = "sort_by", defaultValue = "title-ascending" ) String sort_by,
            @RequestParam(name = "view", defaultValue = "grid") String view
    ){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        LOG.info("# Page : "+page+", Count : "+count+", Sort_by : "+
                sort_by+", View : "+view);

        boolean desc = false;
        ProductService.OrderBy order = ProductService.OrderBy.NAME;

        if(page<1 || page>MAX_PAGE){
            page = 1; // default page = 1
        }

        if(count<4 || count>MAX_COUNT){
            count = 12; // default count = 4
        }

        if(sort_by.equals("title-ascending")){
            order = ProductService.OrderBy.NAME;
        }else if(sort_by.equals("title-descending")){
            desc = true;
            order = ProductService.OrderBy.NAME;
        }else if(sort_by.equals("price-ascending")){
            order = ProductService.OrderBy.PRICE;
        }else if(sort_by.equals("price-descending")){
            desc = true;
            order = ProductService.OrderBy.PRICE;
        }else if(sort_by.equals("date-ascending")){
            order = ProductService.OrderBy.ID;
        }else if(sort_by.equals("date-descending")){
            desc = true;
            order = ProductService.OrderBy.ID;
        }else if(sort_by.equals("best-selling")){
            desc = true;
            order = ProductService.OrderBy.BEST_SELL;
        }

        Page<Product> productPage = productService.getCollections(--page, count, order , desc);

        LOG.info(productPage.toString());

        //model.addAttribute("categoryName", "none");
        model.addAttribute("sort_by", sort_by);
        model.addAttribute("productPage", productPage);

        return "client/pages/collections";
    }


}
