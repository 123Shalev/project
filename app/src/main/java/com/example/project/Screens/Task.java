package com.example.project.Screens;

public class Task {
    private String taskId;
    private String name;
    private String description;
    private String deadlineDate;
    private String deadlineTime;



    public Task(String taskId, String name, String description, String deadlineDate, String deadlineTime) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;

    }

    // Getters
    public String getTaskId() { return taskId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDeadlineDate() { return deadlineDate; }
    public String getDeadlineTime() { return deadlineTime; }
}