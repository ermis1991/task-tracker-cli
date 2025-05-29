https://roadmap.sh/projects/task-tracker

# Task Tracker CLI

A simple command-line task tracker that allows users to add, update, delete, and list tasks. Tasks are stored in a local JSON file.

##  Project URL

https://github.com/ermis1991/task-tracker-cli

##  How to Use

```bash
# Add a task
task-cli add "Go to school"

# Update a task
task-cli update 1 "Go to university"

# Delete a task
task-cli delete 1

# Mark as in progress or done
task-cli mark-in-progress 1
task-cli mark-done 1

# List tasks
task-cli list
task-cli list todo
task-cli list done
task-cli list in-progress
```

## Storage

All tasks are stored in a ```tasks.json``` file in the current directory. It is created automatically if it doesn’t exist.

## External Libraries

No external libraries or frameworks are used. Everything is built using the Java standard library.