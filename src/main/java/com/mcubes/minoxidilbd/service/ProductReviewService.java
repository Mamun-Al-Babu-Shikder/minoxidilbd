package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.ProductReview;
import com.mcubes.minoxidilbd.exception.ProductNotFoundException;
import com.mcubes.minoxidilbd.repository.ProductRepository;
import com.mcubes.minoxidilbd.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by A.A.MAMUN on 10/23/2020.
 */
@Service
@Transactional
public class ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private ProductRepository productRepository;

    public Page<ProductReview> getProductReview(long pid, int page){
        return productReviewRepository.findProductReviewByProductId(pid, PageRequest.of(page, 5));
    }

    public void saveReview(ProductReview review) throws Exception{

        long pid = review.getProductId();
        if(productRepository.isProductExistById(pid)){
             productReviewRepository.save(review);
             int rating = (int) productReviewRepository.findProductRatingUsingProductReviewByProductId(pid);
             productRepository.updateProductRating(rating, pid);
        }else {
            throw new ProductNotFoundException(pid);
        }
    }
}
