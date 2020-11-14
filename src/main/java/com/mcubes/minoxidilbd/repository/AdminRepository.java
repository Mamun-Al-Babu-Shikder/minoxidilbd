package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {

}
