package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.KeyValuePair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Repository
public interface KeyValuePairRepository extends CrudRepository<KeyValuePair, String> {

    @Query("select p.value from KeyValuePair p where p.accessKey=?1")
    String findValueByKey(String key);

}
