package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Ads;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepository extends CrudRepository<Ads, String> {

    @Query("select a from Ads a where a.id=?1 or a.id=?2 or a.id=?3")
    List<Ads> findGroupAds(String id1, String id2, String id3);

}
