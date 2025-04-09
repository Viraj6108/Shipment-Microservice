package com.shipment.shipment.repository;

import com.shipment.shipment.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    @Query(nativeQuery = true, name = "select * from shipment s where shipment.orderId : orderId")
    Shipment findByOrderId(@Param("orderId") Integer orderId);

    @Query(value = "SELECT * FROM shipment s WHERE s.delivery_date = :deliveryDate", nativeQuery = true)
    List<Shipment> findByDeliveryDate(@Param("deliveryDate") String deliveryDate);
}
