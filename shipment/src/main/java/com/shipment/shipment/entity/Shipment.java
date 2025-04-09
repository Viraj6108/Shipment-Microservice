package com.shipment.shipment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipmentId;

    public enum STATUS{
        SHIPPED, DELIVERED, INTRANSIT,CANCELLED
    }
    @Enumerated(EnumType.STRING)
    public STATUS status;

    public String deliveryDate;
    private Integer orderId;

    public Shipment() {
    }

    public Shipment(Integer shipmentId, STATUS status, String deliveryDate, int orderId) {
        this.shipmentId = shipmentId;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.orderId = orderId;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
