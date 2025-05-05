package com.shipment.shipment.entity;

public class ShipmentDeliveredEvent {

    private Integer orderId;
    public ShipmentDeliveredEvent(Integer orderId)
    {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
