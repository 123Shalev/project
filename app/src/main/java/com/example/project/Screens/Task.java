package com.example.project.Screens;

public class Task {
    private String taskId;
    private String name;
    private String deadlineDate;
    private String deadlineTime;

    // קונסטרוקטור ריק (נדרש עבור Firebase)
    public Task() { }

    // קונסטרוקטור עם פרמטרים
    public Task(String taskId, String name, String deadlineDate, String deadlineTime) {
        this.taskId = taskId;
        this.name = name;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
    }

    // גטרים
    public String getTaskId() { return taskId; }
    public String getName() { return name; }
    public String getDeadlineDate() { return deadlineDate; }
    public String getDeadlineTime() { return deadlineTime; }
}