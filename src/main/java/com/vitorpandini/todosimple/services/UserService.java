package com.vitorpandini.todosimple.services;

import com.vitorpandini.todosimple.models.User;
import com.vitorpandini.todosimple.repositories.TaskRepository;
import com.vitorpandini.todosimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(()->new RuntimeException(
                "Usuario nao encontrado! Id: "+ id +" Tipo: "+ User.class.getName()
        ));
    }

    @Transactional
    public User create(User obj){
        obj.setId(null);
        var userSave = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj){
        var updateUser = this.findById(obj.getId());
        updateUser.setPassword(obj.getPassword());
        return this.userRepository.save(updateUser);

    }

    public void delete(Long id){
        var userFind=this.findById(id);
        try {
            this.userRepository.delete(userFind);

        }catch (Exception e){
            throw new RuntimeException("Nao e possivel excluir pois ha entidades relacionadas!");
        }
    }

}
