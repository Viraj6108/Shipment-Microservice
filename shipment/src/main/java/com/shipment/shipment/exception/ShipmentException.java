package com.shipment.shipment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShipmentException extends  Exception{

    public ShipmentException(String msg)
    {
        super(msg);
    }
}
