package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.ContactMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/24/2020.
 */
@Repository
public interface ContactRepository extends CrudRepository<ContactMessage, Long> {

}
