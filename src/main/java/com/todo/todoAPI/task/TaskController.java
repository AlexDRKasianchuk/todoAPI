package com.todo.todoAPI.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    //TODO
    // add user auth

    //get all task
    @Operation(summary = "Get all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task received",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Task.class)))),
            @ApiResponse(responseCode = "404", description = "Task not found")
             })
    @GetMapping( produces = { "application/json"})
    public ResponseEntity<?> getTasks(@RequestParam(required = false, defaultValue = "all") String taskStatus){

        Map<String,String> result = new HashMap<>();

        try{
            return ResponseEntity.status(200).body(taskService.getAllTasks(taskStatus));
        }catch (RuntimeException ex){
            result.put("message",ex.getMessage());
            return ResponseEntity.status(404).body(result);
        }

    }

    //create new task
    @Operation(summary = "create new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created"),
            @ApiResponse(responseCode = "400", description = "Name field is required and can not be empty")
    })
    @PostMapping(produces = {"application/json"})
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDTO taskDTO){

        Map<String,String> result = new HashMap<>();

        try {
            String message = taskService.createNewTask(taskDTO);
            result.put("message",message);
            return ResponseEntity.status(201).body(result);

        }catch (ValidationException ex){
            result.put("message",ex.getMessage());
            return ResponseEntity.status(400).body(result);
        }

    }

    //update task information
    @Operation(summary = "update task information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task has been update"),
            @ApiResponse(responseCode = "400", description = "Name field cannot be empty"),
            @ApiResponse(responseCode = "400", description = "Task with id not found")
    })
    @PutMapping(value = "/{id}",produces = {"application/json"})
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid TaskDTO taskDTO){

        Map<String,String> result = new HashMap<>();

        try{
            String message = taskService.updateTask(id, taskDTO);
            result.put("message",message);
            return ResponseEntity.status(200).build();
        }catch (ValidationException ex){
            result.put("message",ex.getMessage());
            return ResponseEntity.status(400).body(result);
        }catch (EntityNotFoundException ex){
            result.put("message","Task with id = " + id+ " not found");
            return ResponseEntity.status(400).body(result);
        }

    }

    //delete task
    @Operation(summary = "delete task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task has been deleted"),
            @ApiResponse(responseCode = "400", description = "Task with id not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id){

        Map<String,String> result = new HashMap<>();

        try {
            String message = taskService.deleteTask(id);
            result.put("message", message);
            return ResponseEntity.status(200).body(result);
        }catch (EmptyResultDataAccessException e){
                result.put("message", "Task with id = " +id +" not found");
            return ResponseEntity.badRequest().body(result);
        }
    }

}
