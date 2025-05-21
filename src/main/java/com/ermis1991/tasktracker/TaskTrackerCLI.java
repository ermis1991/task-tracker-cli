package com.ermis1991.tasktracker;

import java.util.List;

public class TaskTrackerCLI {

    private static TaskManager taskManager;

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        taskManager = new TaskManager();
        String command = args[0].toLowerCase();

        try {
            switch (command) {
                case "add":
                    if (args.length < 2) {
                        System.out.println("Error: Description is required for adding a task");
                        return;
                    }
                    String description = args[1];
                    Task added = taskManager.addTask(description);
                    System.out.println("Task added: " + added);
                    break;
                case "update":
                    if (args.length < 3) {
                        System.out.println("Error: Task ID and new description are required");
                        return;
                    }
                    int updateId = Integer.parseInt(args[1]);
                    String newDesc = args[2];
                    Task updated = taskManager.updateTask(updateId, newDesc);
                    if (updated != null) {
                        System.out.println("Updated: " + updated);
                    } else {
                        System.out.println("Error: Task not found");
                    }
                    break;
                case "delete":
                    if (args.length < 2) {
                        System.out.println("Error: Task ID is required");
                        return;
                    }
                    int deleteId = Integer.parseInt(args[1]);
                    taskManager.deleteTask(deleteId);
                    break;
                case "mark-in-progress":
                    if (args.length < 2) {
                        System.out.println("Error: Task ID is required");
                        return;
                    }
                    int progressId = Integer.parseInt(args[1]);
                    Task inProgress = taskManager.markInProgress(progressId);
                    if (inProgress != null) {
                        System.out.println("Marked in progress: " + inProgress);
                    } else {
                        System.out.println("Error: Task not found");
                    }
                    break;
                case "mark-done":
                    if (args.length < 2) {
                        System.out.println("Error: Task ID is required");
                        return;
                    }
                    int doneId = Integer.parseInt(args[1]);
                    Task done = taskManager.markDone(doneId);
                    if (done != null) {
                        System.out.println("Marked done: " + done);
                    } else {
                        System.out.println("Error: Task not found");
                    }
                    break;
                case "list":
                    if (args.length == 1) {
                        printTasks(taskManager.listAllTasks());
                    } else {
                        switch (args[1].toLowerCase()) {
                            case "done":
                                printTasks(taskManager.listDoneTasks());
                                break;
                            case "in-progress":
                                printTasks(taskManager.listInProgressTasks());
                                break;
                            case "todo":
                                printTasks(taskManager.listToDoTask());
                                break;
                            default:
                                System.out.println("Invalid status: use done, in-progress, or todo");
                        }
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid task id format!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found!");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  task-cli add \"Description\"");
        System.out.println("  task-cli update <id> \"New Description\"");
        System.out.println("  task-cli delete <id>");
        System.out.println("  task-cli mark-in-progress <id>");
        System.out.println("  task-cli mark-done <id>");
        System.out.println("  task-cli list");
        System.out.println("  task-cli list todo");
        System.out.println("  task-cli list done");
        System.out.println("  task-cli list in-progress");
    }

}
