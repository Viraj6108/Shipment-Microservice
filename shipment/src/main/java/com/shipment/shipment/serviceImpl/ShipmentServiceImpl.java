package com.shipment.shipment.serviceImpl;

import com.shipment.shipment.entity.Orders;
import com.shipment.shipment.entity.Shipment;
import com.shipment.shipment.exception.ShipmentException;
import com.shipment.shipment.repository.ShipmentRepository;
import com.shipment.shipment.service.ShipmentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;
    private KafkaTemplate kafkaTemplate;

    public ShipmentServiceImpl(KafkaTemplate<String, String> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }
    private Gson gson= new Gson();
    @KafkaListener(topics = "order-confirmed", groupId = "shipment-group")
    @Override
    public Shipment trackShipment(String shipmentJson) throws ShipmentException {
        System.out.println(shipmentJson);
        Orders orders = gson.fromJson(shipmentJson,Orders.class);
        Shipment shipment = new Shipment();
        shipment.setOrderId(orders.getOrderId());
        shipment.setDeliveryDate(deliveryDate(orders.getAddress()));
        shipment.setStatus(Shipment.STATUS.SHIPPED);
        shipmentRepository.save(shipment);
        String fromShipmentJson = gson.toJson(shipment);
        kafkaTemplate.send("shipment-shipped",fromShipmentJson);
        return shipment;
    }

    @KafkaListener(topics = "order-cancel",groupId = "shipment-group")
    @Override
    public String cancelShipment(String cancelOrderJson) throws ShipmentException {
        System.out.println(cancelOrderJson);
        Orders orders = gson.fromJson(cancelOrderJson, Orders.class);
        Shipment shipment = shipmentRepository.findByOrderId(orders.getOrderId());
        shipment.setStatus(Shipment.STATUS.CANCELLED);
        shipmentRepository.save(shipment);
        String shipmentCancelledJson = gson.toJson(shipment);
        kafkaTemplate.send("shipment-cancelled",shipmentCancelledJson);
        return "Shipment is cancelled";
    }

    public String generateRandomDate()
    {
        Random random = new Random();
        int randomDate = 1+random.nextInt(1,4);
        String stringDate = LocalDate.now().plusDays(randomDate).toString();
        return stringDate;
    }

    public String deliveryDate(String address) throws ShipmentException {
        Shipment shipment = new Shipment();

        String upperCaseAddress = address.toUpperCase();
        System.out.println(upperCaseAddress);
       if(upperCaseAddress.equals("MUMBAI")  )
       {
           return String.valueOf(LocalDate.now().plusDays(2));
       }
      else if(upperCaseAddress.equals("PUNE") )
       {
           return String.valueOf(LocalDate.now().plusDays(1));

       }
       else if(upperCaseAddress.equals("KOLHAPUR") )
       {
           return String.valueOf(LocalDate.now().plusDays(3));
       }
       throw new ShipmentException("we deliver only Mumbai ,pune and kolhapur location");
    }

    @KafkaListener(topics = "shipment-detail",groupId = "shipment-group")
    public void getPaymentDetails(String orderId) throws ShipmentException
    {
    Integer shipment = gson.fromJson(orderId,Integer.class);
    Shipment shipmentDetails = shipmentRepository.findByOrderId(shipment);
    if(shipmentDetails.equals(null))
        throw new ShipmentException("Shipment not found with order Id");
    String shipmentJson = gson.toJson(shipmentDetails);
    String key = "shipment";
    int partition = 1;
    kafkaTemplate.send("order-detail",partition,key,shipmentJson);
    System.out.println(shipmentJson +" "+ key);

    }
}
