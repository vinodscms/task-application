package com.tasker.taskapplication.service;

import com.tasker.taskapplication.bean.Customer;
import com.tasker.taskapplication.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private CustomerRepository repository;

    public Customer getCustomerByFirstName(String firstName) {
        logger.debug("getCustomerByFirstName for:" + firstName);
        Customer c1 = repository.findByFirstName(firstName);
        logger.debug(c1==null ? "No Record" : c1.toString());
        return repository.findByFirstName(firstName);
    }

    public String insertRecordToMongo(String firstName, String lastName) {
        repository.save(new Customer(firstName,lastName));
        logger.debug("insertRecordToMongo success for:" + firstName);
        return "Success";
    }
}
