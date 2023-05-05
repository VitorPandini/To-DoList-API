package com.vitorpandini.todosimple.controllers;

import com.vitorpandini.todosimple.models.Task;
import com.vitorpandini.todosimple.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")

    public ResponseEntity<Task> findById(@PathVariable Long id){
        var searchTask= taskService.findById(id);
        return ResponseEntity.ok(searchTask);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId) {
        List<Task> tasks = this.taskService.findAllByUserId(userId);

        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping("/add")
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj){
        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj,@PathVariable Long id){
        obj.setId(id);
        this.taskService.update(obj);

        return ResponseEntity.noContent().build();

    };

    @DeleteMapping("{/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }




}
