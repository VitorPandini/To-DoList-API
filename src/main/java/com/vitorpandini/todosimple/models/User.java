package com.vitorpandini.todosimple.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="user")

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode

public class User {

    public interface CreateUser{}

    public interface UpdateUser{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Long id;

    @Column(name = "username",length = 100,nullable = false,unique = true)
    @NotBlank(groups = {CreateUser.class})
    @Size(groups = {CreateUser.class},min = 2,max = 100)
    private String username;

    @Column(name = "password",nullable = false)
    @NotBlank(groups = {CreateUser.class,UpdateUser.class})
    @Size(groups = {CreateUser.class,UpdateUser.class},min = 8,max =255)
    private String password;

    @OneToMany(mappedBy = "user")
    @Getter(AccessLevel.NONE)
    private List<Task> tasks = new ArrayList<Task>();


    @JsonIgnore
    public List<Task> getTask(){
        return tasks;
    }

}
