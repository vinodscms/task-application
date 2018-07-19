package com.tasker.taskapplication.controller;

import com.tasker.taskapplication.bean.Customer;
import com.tasker.taskapplication.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    TaskService service;

    @GetMapping(value="/ping")
    public String getTestMessage(){
        return "Application working fine";
    }

    @GetMapping(value="/getCustomer")
    public Customer getCustomerByFirstName(@RequestParam String firstName){
        return service.getCustomerByFirstName(firstName);
    }

    @GetMapping(value="/getCustomer/{firstName}")
    public Customer getCustomerByFirstName2(@PathVariable String firstName){
        return service.getCustomerByFirstName(firstName);
    }

    @GetMapping(value="/updateDBRecord")
    public String insertRecordToMongo(@RequestParam String firstName,@RequestParam String lastName) {
        return service.insertRecordToMongo(firstName,lastName);
    }
}
