package com.ermis1991.tasktracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {

    private int id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private static final DateTimeFormatter formatter =  DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public enum TaskStatus {
        TODO,
        IN_PROGRESS,
        DONE
    }

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String toJSON() {
        return String.format("{\"id\":%d,\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                id, escape(description), status, createdAt.format(formatter), updatedAt.format(formatter));
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public String toString() {
        return String.format("Task{id=%d, description='%s', status=%s, createdAt=%s, updatedAt=%s}",
                id, escape(description), status, createdAt.format(formatter), updatedAt.format(formatter));
    }

    public static Task fromJSON(String json) {
        json = json.substring(1, json.length() - 1);

        String[] parts = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        int id = -1;
        String description = "";
        TaskStatus status = TaskStatus.TODO;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        for (String part : parts) {
            String[] keyValue = part.split(":", 2);
            if (keyValue.length != 2) continue;

            String key = keyValue[0].trim().replaceAll("\"", "");
            String value = keyValue[1].trim().replaceAll("\"", "");

            switch (key) {
                case "id":
                    id = Integer.parseInt(value);
                    break;
                case "description":
                    description = value;
                    break;
                case "status":
                    status = TaskStatus.valueOf(value);
                    break;
                case "createdAt":
                    createdAt = LocalDateTime.parse(value, formatter);
                    break;
                case "updatedAt":
                    updatedAt = LocalDateTime.parse(value, formatter);
                    break;
            }
        }

        Task task = new Task(id, description);
        task.status = status;
        task.createdAt = createdAt;
        task.updatedAt = updatedAt;

        return task;
    }

    private static String escape(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

}
