package com.pato.todolist.infra.errors;

public class TaskValidationException extends RuntimeException{
    public TaskValidationException(String message) {
        super(message);
    }
}
