package com.todo.todoAPI.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    //TODO
    // add user verify

    @Autowired
    TaskRepository taskRepository;

    public List<Task> getAllTasks(String taskStatus) {

            List<Task> list;

            switch (taskStatus){
                case "true":
                    list  = taskRepository.findAllByStatus(true);
                    break;
                case "false":
                    list  = taskRepository.findAllByStatus(false);
                    break;
                default:
                    list = taskRepository.findAll();
            }

            if (list.isEmpty()) throw new RuntimeException("Task not found");

            return list;
    }


    public String createNewTask(TaskDTO taskDTO) throws ValidationException {

        Task task = new Task();

        String taskName = taskDTO.getName();
        if(taskName==null || taskName.isEmpty()){
            throw new ValidationException("Name field is required");
        }
        task.setName(taskName);

        task.setDescription(taskDTO.getDescription());


        TaskPriority priority = taskDTO.getPriority();
        if(priority==null){
            task.setPriority(TaskPriority.DEFAULT);
        }else{
            task.setPriority(priority);
        }

        task.setStatus(false);
        task.setDate(LocalDateTime.now());

        taskRepository.save(task);

        return "Task added";
    }

    public String updateTask(int id, TaskDTO taskDTO) throws ValidationException {

        Task task = taskRepository.getById(id);

        String taskName = taskDTO.getName();
        if(taskName.isEmpty()){
            throw new ValidationException("Name field cannot be empty");
        }
        task.setName(taskName);

        if(taskDTO.getDescription()!=null){
            task.setDescription(taskDTO.getDescription());
        }

        if(taskDTO.getPriority()!=null){
            task.setPriority(taskDTO.getPriority());
        }

        task.setStatus(taskDTO.isStatus());
        task.setDate(LocalDateTime.now());

        taskRepository.save(task);

        return "Task has been update";
    }


    public String deleteTask(int id) {
        taskRepository.deleteById(id);
        return "Task has been deleted";
    }
}
