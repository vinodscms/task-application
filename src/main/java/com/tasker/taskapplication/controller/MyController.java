package com.tasker.taskapplication.controller;

import com.tasker.taskapplication.bean.Customer;
import com.tasker.taskapplication.bean.QueueObject;
import com.tasker.taskapplication.bean.TaskObject;
import com.tasker.taskapplication.service.TaskService;
import com.tasker.taskapplication.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {

    @Autowired
    TaskService service;

    @Autowired
    QueueService queueService;

    @GetMapping(value="/ping")
    public String getTestMessage(){
        return "Application working fine";
    }

    @GetMapping(value="/getCustomer")
    public Customer getCustomerByFirstName(@RequestParam String firstName){
        return service.getCustomerByFirstName(firstName);
    }

    @GetMapping(value="/getCustomer/{firstName}")
    public ResponseEntity getCustomerByFirstName2(@PathVariable String firstName){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<Customer>(service.getCustomerByFirstName(firstName), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value="/updateDBRecord")
    public String insertRecordToMongo(@RequestParam String firstName,@RequestParam String lastName) {
        return service.insertRecordToMongo(firstName,lastName);
    }

    @PostMapping(value="/updateDBRecordPost")
    public ResponseEntity insertRecordToMongoPost(@RequestBody Customer customer) {
        return new ResponseEntity(service.insertRecordToMongo(customer.getFirstName(),customer.getLastName()), HttpStatus.OK);
    }

    @GetMapping(value="/getTasks/{date}")
    public ResponseEntity getTasks(@PathVariable String date){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(service.getTasksByDate(date), responseHeaders, HttpStatus.OK);
    }

    @PostMapping(value="/updateTasks")
    public ResponseEntity insertTaskToMongoPost(@RequestBody TaskObject task) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Request-Headers", "content-type");
        responseHeaders.set("Access-Control-Request-Method", "POST");
        responseHeaders.set("Access-Control-Allow-Methods", "POST");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        return new ResponseEntity(service.insertTaskToMongo(task.getDate(),task.getTask()), responseHeaders, HttpStatus.OK);
    }

    @PostMapping(value="/insertToMQ")
    public ResponseEntity insertTaskToMongoPost(@RequestBody QueueObject queueObject) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Request-Headers", "content-type");
        responseHeaders.set("Access-Control-Request-Method", "POST");
        responseHeaders.set("Access-Control-Allow-Methods", "POST");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        return new ResponseEntity(queueService.publishMessage(queueObject.message,queueObject.queueName), responseHeaders, HttpStatus.OK);
    }
}
