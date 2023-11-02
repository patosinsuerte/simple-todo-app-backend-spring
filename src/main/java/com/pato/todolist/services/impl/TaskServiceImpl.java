package com.pato.todolist.services.impl;

import com.pato.todolist.dto.SaveTask;
import com.pato.todolist.dto.SaveTaskStatus;
import com.pato.todolist.infra.errors.TaskNotFoundException;
import com.pato.todolist.infra.errors.TaskValidationException;
import com.pato.todolist.persistence.entities.Task;
import com.pato.todolist.persistence.repositories.TaskRepository;
import com.pato.todolist.persistence.util.Status;
import com.pato.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));
    }

    @Override
    public Task updateById(Long id, SaveTask saveTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTask(saveTask.getTask());
            taskRepository.save(task);
            return task;
        } else {
            throw new TaskNotFoundException("La tarea con el id " + id);
        }
    }

    @Override
    public void deleteTaskById(Long id) {
        if (id == null){
            throw new TaskNotFoundException("NO se encontro una tarea con el id " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public Task createTask(SaveTask newTask) {

        if(newTask.getTask() == null || !StringUtils.hasText(newTask.getTask())){
            throw new TaskValidationException("El campo 'task' no puede estar vacío o nulo.");
        }

        Task task = new Task();
        task.setTask(newTask.getTask());
        task.setStatus(Status.INCOMPLETE);
        taskRepository.save(task);
        return task;
    }


    public List<Task> getAllDesc(){
        return taskRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Task updateTaskByStatus(SaveTaskStatus status, Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setStatus(status.getStatus());
            taskRepository.save(task);
            return task;
        }
        throw  new TaskNotFoundException("Task with id: " + id);
    }
}
