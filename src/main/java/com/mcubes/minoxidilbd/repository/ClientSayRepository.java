package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.ClientSay;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientSayRepository extends CrudRepository<ClientSay, Long> {

    @Query("select c from ClientSay c order by c.id desc")
    List<ClientSay> findClientSayByRange(Pageable pageable);

}
