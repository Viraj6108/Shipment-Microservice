package com.shipment.shipment.serviceImpl;

import com.google.gson.Gson;
import com.shipment.shipment.entity.Shipment;
import com.shipment.shipment.entity.ShipmentDeliveredEvent;
import com.shipment.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleTask {

    private KafkaTemplate kafkaTemplate;
    private Gson gson = new Gson();
    public ScheduleTask(KafkaTemplate<String, String> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }
    //This job will set the status automatically to DELIVERED at the date of delivery
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Scheduled(fixedRate = 1500000000)
    public void executeDeliverUpdate(){
        List<Shipment> shipments = shipmentRepository.findByDeliveryDateAndStatus(LocalDate.now().toString(), String.valueOf(Shipment.STATUS.SHIPPED));
//TODO only fetch status = SHIPPED and then set status = DELIVERED and send notification
        for (Shipment shipment : shipments)
        {
            if(shipment.getStatus().equals(Shipment.STATUS.SHIPPED))
            {
                shipment.setStatus(Shipment.STATUS.DELIVERED);
                ShipmentDeliveredEvent event = new ShipmentDeliveredEvent(shipment.getOrderId());
                String  eventJson = gson.toJson(event);
                kafkaTemplate.send("shipment-delivered",eventJson);
            }else {
                break;
            }

        }
        shipmentRepository.saveAll(shipments);
    }
}
