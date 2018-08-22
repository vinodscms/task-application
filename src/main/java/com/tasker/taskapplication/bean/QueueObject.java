package com.tasker.taskapplication.bean;

public class QueueObject {
    public String queueName;
    public String message;

    public QueueObject(){
    }
    public QueueObject(String queueName, String message) {
        this.queueName = queueName;
        this.message = message;
    }
}
