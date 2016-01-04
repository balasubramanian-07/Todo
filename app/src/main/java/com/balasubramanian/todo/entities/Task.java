package com.balasubramanian.todo.entities;

public class Task {

    private String title;
    private String description;
    private final String dueDate;
    private final String dueTime;

    public Task(String title, String description, String dueDate, String dueTime) {

        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {

        return description;
    }

    public String getDueDate() {

        return dueDate;
    }

    public String getDueTime() {

        return dueTime;
    }
}
