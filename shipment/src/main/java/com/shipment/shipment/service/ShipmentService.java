package com.shipment.shipment.service;

import com.shipment.shipment.entity.Shipment;
import com.shipment.shipment.exception.ShipmentException;

public interface ShipmentService {
   public Shipment trackShipment(String shipmentJson) throws ShipmentException;
   public String cancelShipment(String cancelOrderJson)throws ShipmentException;
}
