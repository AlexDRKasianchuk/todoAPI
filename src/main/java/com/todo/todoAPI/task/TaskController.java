package com.todo.todoAPI.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;


    //get all task
    @GetMapping
    public ResponseEntity<?> getTasks(){
        return ResponseEntity.status(200).body(taskService.getAllTasks());
    }

    //create new task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Map<String,String> taskModel){
        taskService.createNewTask(taskModel.get("text"));
        return ResponseEntity.status(200).build();
    }

    //update task information
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id,@RequestBody Map<String,String> taskModel){
        taskService.updateTaskText(id, taskModel.get("text"));
        return ResponseEntity.status(200).build();
    }

    //update task status
    @PutMapping("/checked/{id}")
    public ResponseEntity<?> checkTask(@PathVariable int id){
        taskService.updateTaskStatus(id);
        return ResponseEntity.status(200).build();
    }

    //delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
        return ResponseEntity.status(200).build();
    }

}
