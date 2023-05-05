package com.vitorpandini.todosimple.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "task")

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    private User user;

    @Column(length = 255,nullable = false)
    @NotBlank
    @Size(min = 1,max = 255)
    private String descrition;



}
