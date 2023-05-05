package com.vitorpandini.todosimple.services;


import com.vitorpandini.todosimple.models.Task;
import com.vitorpandini.todosimple.models.User;
import com.vitorpandini.todosimple.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(()-> new RuntimeException(
                "Tarefa nao encontrada! id: "+ id +" Tipo: "+ Task.class.getName()
        ));
    }

    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks=this.taskRepository.findByUser_Id(userId);
        return tasks;
    }

    @Transactional
    public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        var createTask=taskRepository.save(obj);
        return createTask;

    }
    @Transactional
    public Task update(Task obj){
        var updateTask=this.findById(obj.getId());
        updateTask.setDescrition(obj.getDescrition());
        return this.taskRepository.save(updateTask);
    }

    public void delete(Long id){
        var deleteTask=this.findById(id);
        try{
            this.taskRepository.delete(deleteTask);
        }catch(Exception e){
            throw  new RuntimeException("Nao e possivel deletar pois tem entidade relacionada!");
        }
    }

}
