package com.tasker.taskapplication.repo;

import com.tasker.taskapplication.bean.TaskObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<TaskObject, String> {

    public List<TaskObject> findByDate(String date);

}
