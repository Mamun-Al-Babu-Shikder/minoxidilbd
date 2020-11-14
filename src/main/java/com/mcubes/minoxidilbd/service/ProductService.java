package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.Product;
import com.mcubes.minoxidilbd.model.CompareItem;
import com.mcubes.minoxidilbd.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Service
@Transactional
public class ProductService {

    public static enum OrderBy {
        ID, NAME, PRICE, BEST_SELL
    }

    @Autowired
    private ProductRepository productRepository;

    public static final int count = 8;

    public Optional<Product> getProductByProductId(long id){
        return productRepository.findById(id);
    }



    public List<Product> getRelatedProduct(long productId, long categoryId){
        return productRepository.fetchRelatedProduct(productId, categoryId, PageRequest.of(0, 8))
                .getContent();
    }

    public List<Product> getNewProducts(){
        return productRepository.findNewProducts(PageRequest.of(0, count)).getContent();
    }

    public List<Product> getBestSellingProducts(){
        return productRepository.findBestSellingProduct(PageRequest.of(0, count)).getContent();
    }

    public List<Product> getHighRatedProducts(){
        return productRepository.findHighRatedProduct(PageRequest.of(0, count)).getContent();
    }

    public List<Product> getTopDiscountedProducts(){
        return productRepository.findTopDiscountedProduct(PageRequest.of(0, count)).getContent();
    }

    public List<Product> getMostViewedProducts(){
        return productRepository.findMostViewedProduct(PageRequest.of(0, count)).getContent();
    }

    public Product getProductForCartItemsByProductId(long id){
        return productRepository.findProductForCartItemsById(id);
    }

    public Product getAvailableProductForCartItemsByProductId(long id){
        return productRepository.findStockAvailableProductsById(id);
    }

    public Product getProductForWishItemsByProductId(long id){
        return productRepository.findProductForCartItemsById(id);
    }

    public Product getProductForCompareListByProductId(long id){
        return productRepository.findProductForCompareListById(id);
    }

    public boolean isProductExist(long id){
        return productRepository.isProductExistById(id);
    }

    public long getStockByProductId(long id){
        return productRepository.findProductStockById(id);
    }

    public Page<Product> getProductByCategory(long category, int page, int count, OrderBy order,  boolean desc){
        Sort.Direction ascOrDesc = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        String orderBy = null;
        switch (order){
            case ID:
                orderBy = "id";
                break;
            case NAME:
                orderBy = "name";
                break;
            case PRICE:
                orderBy = "currentPrice";
                break;
            case BEST_SELL:
                orderBy = "sell";
                break;
        }
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(ascOrDesc, orderBy));
        return productRepository.findProductByCategory(category, pageRequest);
    }



    public Page<Product> getCollections(int page, int count, OrderBy order,  boolean desc){
        Sort.Direction ascOrDesc = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        String orderBy = null;
        switch (order){
            case ID:
                orderBy = "id";
                break;
            case NAME:
                orderBy = "name";
                break;
            case PRICE:
                orderBy = "currentPrice";
                break;
            case BEST_SELL:
                orderBy = "sell";
                break;
        }
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(ascOrDesc, orderBy));
        return productRepository.findCollections(pageRequest);
    }

    public Page<Product> searchProduct(String search, int page){
        return productRepository.findProductByName(search, PageRequest.of(page, 15));
    }

    public void increaseProductView(int views, long id){
         productRepository.increaseProductViewById(views, id);
    }
}
