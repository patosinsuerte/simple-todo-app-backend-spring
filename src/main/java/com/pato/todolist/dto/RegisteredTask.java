package com.pato.todolist.dto;

import com.pato.todolist.persistence.util.Status;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisteredTask implements Serializable {

    private Long id;
    private String task;
    private Status status;
}
