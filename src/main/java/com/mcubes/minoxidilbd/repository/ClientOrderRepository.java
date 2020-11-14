package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.ClientOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/19/2020.
 */
@Repository
public interface ClientOrderRepository extends CrudRepository<ClientOrder, Long> {

    Page<ClientOrder> findClientOrderByStatus(ClientOrder.OrderStatus status, Pageable pageable);

    @Query("select o from ClientOrder o where o.regularClient=true and o.clientEmail=?1 order by o.id desc")
    Page<ClientOrder> findRegularClientOrderByClientEmail(String clientEmail, Pageable pageable);

    @Query("select o from ClientOrder o where o.regularClient=true and o.clientEmail=?1 and o.status=?2 order by o.id desc")
    Page<ClientOrder> findRegularClientOrderByClientEmailAndOrderStatus(
            String clientEmail, ClientOrder.OrderStatus status, Pageable pageable
    );

    @Query("select o from ClientOrder o where o.regularClient=true and o.clientEmail=?1 and o.id=?2")
    ClientOrder findClientOrderByClientEmailAndId(String clientEmail, long id);

    @Query("select o.status from ClientOrder o where o.regularClient=true and o.clientEmail=?1 and o.id=?2")
    ClientOrder.OrderStatus findClientOrderStatusByClientEmailAndId(String clientEmail, long id);

    @Modifying
    @Query("update ClientOrder o set o.status=?1 where o.clientEmail=?2 and o.id=?3")
    void updateClientOrderStatus(ClientOrder.OrderStatus status, String clientEmail, long id);
}
