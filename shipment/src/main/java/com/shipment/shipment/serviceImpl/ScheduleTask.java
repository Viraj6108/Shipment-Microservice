package com.shipment.shipment.serviceImpl;

import com.shipment.shipment.entity.Shipment;
import com.shipment.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduleTask {

    //This job will set the status automatically to DELIVERED at the date of delivery
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Scheduled(fixedRate = 10000)
    public void executeDeliverUpdate(){
        List<Shipment> shipments = shipmentRepository.findByDeliveryDate(LocalDate.now().toString());
        for (Shipment shipment : shipments)
        {
            shipment.setStatus(Shipment.STATUS.DELIVERED);
        }
        shipmentRepository.saveAll(shipments);
    }
}
