package com.pato.todolist.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SaveTask implements Serializable {
    private String task;
}
