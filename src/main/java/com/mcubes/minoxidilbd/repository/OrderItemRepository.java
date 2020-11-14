package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/22/2020.
 */
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
