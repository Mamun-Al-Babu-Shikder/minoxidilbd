package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.Category;
import com.mcubes.minoxidilbd.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public String getCategoryNameById(long id){
        return categoryRepository.findCategoryNameById(id);
    }

    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Category> fetchAllCategory(){
        return (List<Category>) categoryRepository.findAll();
    }

}
