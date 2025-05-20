package com.ermis1991.tasktracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {

    private static final String TASKS_FILE = "tasks.json";
    private List<Task> tasks;
    private int nextId;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
        loadTasks();
    }

    private void loadTasks() {
        try {
            Path path = Paths.get(TASKS_FILE);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    if (!line.isBlank()) {
                        Task task = Task.fromJSON(line);
                        tasks.add(task);
                        nextId = Math.max(nextId, task.getId() + 1);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
    }

    private void saveTasks() {
        try {
            List<String> taskStrings = tasks.stream()
                    .map(Task::toJSON)
                    .collect(Collectors.toList());
            Files.write(Paths.get(TASKS_FILE), taskStrings);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public Task addTask(String description) {
        Task task = new Task(nextId++, description);
        tasks.add(task);
        saveTasks();
        return task;
    }

    public Task updateTask(int id, String description) {
        Task task = findTask(id);
        task.setDescription(description);
        saveTasks();
        return task;
    }

    public void deleteTask(int id) {
        Task task = findTask(id);
        tasks.remove(task);
        saveTasks();
        System.out.println("Task with id " + id + " has been successfully deleted!");
    }

    private Task findTask(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found!"));
    }

}
