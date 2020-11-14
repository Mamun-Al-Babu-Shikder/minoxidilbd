package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Faq;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by A.A.MAMUN on 10/25/2020.
 */
@Repository
public interface FaqRepository extends CrudRepository<Faq, Integer> {

    List<Faq> findFaqByGeneral(boolean general);

    @Query("select f from Faq f where f.general=true")
    List<Faq> findAllGeneralQuestion();

    @Query("select f from Faq f where f.general=false")
    List<Faq> findAllOtherQuestion();
}
