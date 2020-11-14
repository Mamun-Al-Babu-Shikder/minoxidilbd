package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.TeamMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/24/2020.
 */
@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember, Long> {

}
