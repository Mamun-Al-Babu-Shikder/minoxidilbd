package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Subscriber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, String> {


}
