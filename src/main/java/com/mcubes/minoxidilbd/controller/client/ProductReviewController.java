package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.entity.ProductReview;
import com.mcubes.minoxidilbd.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.PrintStream;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/23/2020.
 */
@RestController
public class ProductReviewController {

    private static final Logger LOG = Logger.getLogger(ProductReviewController.class.getName());

    @Autowired
    private ProductReviewService productReviewService;

    @GetMapping("/fetch-review")
    public Page<ProductReview> fetchProductReview(@RequestParam long pid, @RequestParam(defaultValue = "0") int page){
        page = page < 0 ? 0 : page;
        return productReviewService.getProductReview(pid, page);
    }

    @PostMapping("/save-review")
    public String saveReview(@Valid @ModelAttribute ProductReview  productReview,BindingResult result){
        if(result.hasErrors()){
            for (ObjectError err : result.getAllErrors()){
                System.out.println("Err : " + err.getDefaultMessage());
            }
            return "Not all the fields have been filled out correctly!";
        }
        try{
            productReviewService.saveReview(productReview);
        }catch (Exception e){
            LOG.warning("# Ex : " + e.getMessage());
            return "Internal server error! Please try again later.";
        }
        return "success";
    }



}
