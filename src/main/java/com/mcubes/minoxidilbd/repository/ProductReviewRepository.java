package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/23/2020.
 */
@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {

    @Query("select r from ProductReview r where r.productId=?1 order by r.id desc")
    Page<ProductReview> findProductReviewByProductId(long pid, Pageable pageable);

    @Query("select avg(r.rating) from ProductReview r where r.productId=?1")
    float findProductRatingUsingProductReviewByProductId(long pid);
}
