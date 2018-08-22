package com.tasker.taskapplication.bean;

import org.springframework.data.annotation.Id;


public class TaskObject {

    @Id
    public String id;

    public String getDate() {       return date;    }
    public String getTask() {        return task;    }

    public String date;
    public String task;

    public TaskObject() {}

    public TaskObject(String date, String task) {
        this.date = date;
        this.task = task;
    }

    @Override
    public String toString() {
        return String.format(
                "Task[id=%s, date='%s', task='%s']",
                id, date, task);
    }

}