package com.pato.todolist.infra.errors;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
