package ginger.task;

import java.time.LocalDateTime;
import java.util.List;

public class TaskHandler {
    private final List<Task> taskList;
    private final TaskStorage taskStorage;

    public TaskHandler() {
        this.taskStorage = new TaskStorage("tasks.txt");
        this.taskList = this.taskStorage.readTasks();
    }

    public int taskCount() {
        return this.taskList.size();
    }

    public Task getTask(int index) {
        return this.taskList.get(index);
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public Task deleteTask(int index) {
        return taskList.remove(index);
    }

    public String tasklistToString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            result.append(String.format("%d. %s", i + 1, taskList.get(i)));
            if (i != taskList.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Changes a task status to mark or unmarked
     * @param index The index of the task that is being referenced.
     * @param toMark A boolean to indicate whether to mark or unmark. True to mark, false to unmark.
     */
    public void changeTaskStatus(int index, boolean toMark) {
        Task t = taskList.get(index);
        t.updateStatus(toMark);
    }

    public Task addToDo(String title) {
        ToDo newToDo = new ToDo(title);
        this.addTask(newToDo);
        return newToDo;
    }

    public Task addDeadline(String title, LocalDateTime deadline) {
        Deadline newDeadline = new Deadline(title, deadline);
        this.addTask(newDeadline);
        return newDeadline;
    }

    public Task addEvent(String title, LocalDateTime start, LocalDateTime end) {
        Event newEvent = new Event(title, start, end);
        this.addTask(newEvent);
        return newEvent;
    }

    public void saveTasks() {
        this.taskStorage.saveTasks(this.taskList);
    }
}
