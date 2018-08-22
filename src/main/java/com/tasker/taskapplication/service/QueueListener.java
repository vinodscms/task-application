package com.tasker.taskapplication.service;

import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

//The is a Queue Listener. If you need to activate, UnComment the @Component annotation and give required queue name
@Component
public class QueueListener {

    @JmsListener(destination = "EMAIL.CONFIRM.QC01")
    public void handleMessage(String message) {
        System.out.println("received message from queue: " + message);
    }
}
