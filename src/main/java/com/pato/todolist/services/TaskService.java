package com.pato.todolist.services;

import com.pato.todolist.dto.SaveTask;
import com.pato.todolist.dto.SaveTaskStatus;
import com.pato.todolist.persistence.entities.Task;
import com.pato.todolist.persistence.util.Status;

import java.util.List;

public interface TaskService {

    // GET ALL
    List<Task> getAll();

    //Get by ID
    Task getById(Long id);

    //Update by id

    Task updateById(Long id, SaveTask saveTask);

    // DELETE TASK by ID
    void deleteTaskById(Long id);

    // POST TASK
    Task createTask(SaveTask newTask);


    // Update status by id

    Task updateTaskByStatus(SaveTaskStatus status, Long id);

}
