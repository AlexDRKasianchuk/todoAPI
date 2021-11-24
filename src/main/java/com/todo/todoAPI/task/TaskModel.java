package com.todo.todoAPI.task;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    private LocalDateTime date;
    private Boolean status;

//    connect to user
//    private User user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskModel taskModel = (TaskModel) o;
        return Objects.equals(id, taskModel.id) && Objects.equals(text, taskModel.text) && Objects.equals(date, taskModel.date) && Objects.equals(status, taskModel.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, date, status);
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
