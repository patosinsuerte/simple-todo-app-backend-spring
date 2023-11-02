package com.pato.todolist.controllers;

import com.pato.todolist.dto.RegisteredTask;
import com.pato.todolist.dto.SaveTask;
import com.pato.todolist.dto.SaveTaskStatus;
import com.pato.todolist.persistence.entities.Task;
import com.pato.todolist.services.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;


    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<List<RegisteredTask>> getAll() {
        List<Task> tasks = taskService.getAllDesc();
        List<RegisteredTask> registeredTasks = tasks.stream()
                .map(task -> {
                    RegisteredTask registeredTask = new RegisteredTask();
                    registeredTask.setId(task.getId());
                    registeredTask.setTask(task.getTask());
                    registeredTask.setStatus(task.getStatus());
                    return registeredTask;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(registeredTasks);
    }

    //Get by id
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<RegisteredTask> getByIdFromDb(
            @PathVariable("id") Long id
    ) {
        Task task = taskService.getById(id);

        RegisteredTask registeredTask = new RegisteredTask();
        registeredTask.setId(task.getId());
        registeredTask.setTask(task.getTask());
        registeredTask.setStatus(task.getStatus());

        return ResponseEntity.ok(registeredTask);
    }


    // CREATE TASK
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<RegisteredTask> createTask(
            @RequestBody
            @Valid
            SaveTask saveTask,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        Task task = taskService.createTask(saveTask);
        URI url = uriComponentsBuilder.path("/{id}").buildAndExpand(task.getId()).toUri();
        RegisteredTask registeredTask = new RegisteredTask();
        registeredTask.setId(task.getId());
        registeredTask.setTask(task.getTask());
        registeredTask.setStatus(task.getStatus());
        return ResponseEntity.created(url).body(registeredTask);

    }


    // UPDATE TASK
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RegisteredTask> updateTask(
            @PathVariable("id") Long id,
            @RequestBody
            @Valid
            SaveTask saveTask
    ) {

        Task taskBD = taskService.getById(id);
        taskService.updateById(taskBD.getId(), saveTask);
        RegisteredTask registeredTask = new RegisteredTask();
        registeredTask.setId(taskBD.getId());
        registeredTask.setTask(taskBD.getTask());
        registeredTask.setStatus(taskBD.getStatus());
        return ResponseEntity.ok(registeredTask);
    }


    // DELETE
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteTask(
            @PathVariable("id") Long id
    ) {
        taskService.deleteTaskById(id);
    }


    //UPDATE TASK BY STATUS
    @PutMapping("/update-status/{id}")
    public ResponseEntity<RegisteredTask> updateStatus(
            @PathVariable("id") Long id,
            @RequestBody
            @Valid
            SaveTaskStatus saveTaskStatus,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        Task taskBD = taskService.getById(id);
        taskService.updateTaskByStatus(saveTaskStatus, taskBD.getId());

        RegisteredTask registeredTask = new RegisteredTask();
        registeredTask.setId(taskBD.getId());
        registeredTask.setTask(taskBD.getTask());
        registeredTask.setStatus(taskBD.getStatus());

        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(taskBD.getId()).toUri();

        return ResponseEntity.created(uri).body(registeredTask);
    }

}
