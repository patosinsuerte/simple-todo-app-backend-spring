package com.pato.todolist.persistence.entities;


import com.pato.todolist.persistence.util.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String task;
    @Enumerated(EnumType.STRING)
    private Status status;

}
