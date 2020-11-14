package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Product;
import com.mcubes.minoxidilbd.model.CompareItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {


    /* fetch new product */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice, p.oldPrice, p.rating, p.stock, p.description)" +
            " from Product p order by p.id desc")
    Page<Product> findNewProducts(Pageable pageable);

    /* fetch best selling product */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice, p.oldPrice, p.rating, p.stock, p.description)" +
            " from Product p order by p.sell desc")
    Page<Product> findBestSellingProduct(Pageable pageable);

    /* fetch high rated product */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice, p.oldPrice, p.rating, p.stock, p.description)" +
            " from Product p order by p.rating desc")
    Page<Product> findHighRatedProduct(Pageable pageable);

    /* fetch top discount product */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice, p.oldPrice, p.rating, p.stock, p.description) " +
            "from Product p order by ((p.currentPrice-p.oldPrice)/p.oldPrice)")
    Page<Product> findTopDiscountedProduct(Pageable pageable);

    /* fetch most viewed product */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice, p.oldPrice, p.rating, p.stock, p.description) " +
            "from Product p order by p.views")
    Page<Product> findMostViewedProduct(Pageable pageable);

    /* fetch product for cart items */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice) from Product p where p.id=?1")
    Product findProductForCartItemsById(long id);

    @Query("select count(p) > 0 from Product p where p.id=?1")
    boolean isProductExistById(long id);

    @Query("select new Product(p.id, p.name, p.currentPrice, p.image, p.stock, p.description)" +
            " from Product p where p.id=?1")
    Product findProductForCompareListById(long id);

    /* fetch stock available product */
    @Query("select p from Product p where p.stock>0 and p.id=?1")
    Product findStockAvailableProductsById(long id);

    /* product count by id */
    @Query("select p.stock from Product p where p.id=?1")
    int findProductStockById(long id);

    /* fetch product by category */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice, p.oldPrice, p.rating, p.stock, p.description)" +
            " from Product p where p.category=?1")
    Page<Product> findProductByCategory(long category, Pageable pageable);

    @Query("select p from Product p")
    Page<Product> findCollections(Pageable pageable);

    /* fetch related product */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice, p.oldPrice, p.rating, p.stock, p.description)" +
            " from Product p where p.category=?2 and not p.id=?1")
    Page<Product> fetchRelatedProduct(long productId, long categoryId, Pageable pageable);

    /* update product rating */
    @Modifying
    @Query("update Product p set p.rating=?1 where p.id=?2")
    void updateProductRating(int rating, long id);

    /* fetch stock */
    @Query("select p.stock from Product p where p.id=?1")
    int findStockByProductId(long id);

    /* update stock */
    @Modifying
    @Query("update Product p set p.stock = ?1 where p.id=?2")
    void updateProductStock(int stock, long id);

    /* fetch sell */
    @Query("select p.sell from Product p where p.id=?1")
    int findSellByProductId(long id);

    /* update sell */
    @Modifying
    @Query("update Product p set p.sell = ?1 where p.id=?2")
    void updateProductSell(int sell, long id);


    /* increase product view */
    @Modifying
    @Query("update Product p set p.views=?1 where p.id=?2")
    void increaseProductViewById(int views, long id);

    /* fetch search product */
    @Query("select new Product(p.id, p.image, p.name, p.currentPrice, p.oldPrice, p.rating, p.stock, p.description)" +
            " from Product p where p.name LIKE %?1%")
    Page<Product> findProductByName(String name, Pageable pageable);

}
