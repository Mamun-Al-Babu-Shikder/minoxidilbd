package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("select c.name from Category c where c.id=?1")
    String findCategoryNameById(long id);

}
