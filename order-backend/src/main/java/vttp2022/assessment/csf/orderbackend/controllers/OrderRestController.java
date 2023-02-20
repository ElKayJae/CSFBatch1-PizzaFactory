package vttp2022.assessment.csf.orderbackend.controllers;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@Controller
@RequestMapping(path = "/api")
public class OrderRestController {

    @Autowired
    private OrderService orderService;
    
    @ResponseBody
    @PostMapping (path = "/order")
    public ResponseEntity<String> createOrder(@RequestBody String json){
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jsonObject = reader.readObject();
        // orderService.createOrder(jsonObject);
        orderService.createOrderSQL(jsonObject);

        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping (path = "/order/{email}/all")
    public ResponseEntity<String> getAllOrders(@PathVariable String email){
        // List<OrderSummary> summaryList = orderService.getOrdersByEmail(email);
        List<OrderSummary> summaryList = orderService.getOrdersByEmailSQL(email);
        JsonArrayBuilder arr = Json.createArrayBuilder();
        summaryList.stream().forEach(s -> arr.add(s.toJson()));


        return ResponseEntity.ok().body(arr.build().toString());
    }
}
