package com.pato.todolist.dto;

import com.pato.todolist.persistence.util.Status;
import lombok.Data;

import java.io.Serializable;


@Data
public class SaveTaskStatus implements Serializable {

    private Status status;


}
