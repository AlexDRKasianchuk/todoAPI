package com.todo.todoAPI.task;

public class TaskDTO {

    private String name;

    private String description;

    private boolean status;

    private TaskPriority priority;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }
}
