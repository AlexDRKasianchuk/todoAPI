package com.todo.todoAPI.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<TaskModel> getAllTasks() {
            return taskRepository.findAll();
    }

    public String createNewTask(String text) {
        TaskModel task = new TaskModel();
        task.setText(text);
        task.setStatus(false);
        task.setDate(LocalDateTime.now());
        taskRepository.save(task);
        return "Added";
    }

    public void updateTaskText(int id, String text) {
        TaskModel task = taskRepository.getById(id);
        task.setText(text);
        task.setDate(LocalDateTime.now());
        taskRepository.save(task);
    }

    public void updateTaskStatus(int id) {
        TaskModel task = taskRepository.getById(id);
        task.setStatus(!task.getStatus());
        taskRepository.save(task);
    }

    public void deleteTask(int id) {
        TaskModel task = taskRepository.getById(id);
        taskRepository.delete(task);
    }
}
