package ginger.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskHandlerTest {

    @Test
    public void taskHandlerTest_taskAddsCorrectly() {
        TaskHandler taskHandler = new TaskHandler();
        int taskCount = taskHandler.taskCount();

        // Clear any potential pre-existing tasks for proper testing
        for (int i = 0; i < taskCount; i++) {
            taskHandler.deleteTask(0);
        }

        Task t = new ToDo("buy groceries");
        taskHandler.addTask(t);

        assertEquals(taskHandler.getTask(0), t);
    }

    @Test
    public void taskHandlerTest_findsTasksCorrectly() {
        TaskHandler taskHandler = new TaskHandler();
        int taskCount = taskHandler.taskCount();

        // Clear any potential pre-existing tasks for proper testing
        for (int i = 0; i < taskCount; i++) {
            taskHandler.deleteTask(0);
        }

        Task t1 = new ToDo("read book");
        Task t2 = new ToDo("read notes");
        Task t3 = new ToDo("buy bottle");

        taskHandler.addTask(t1);
        taskHandler.addTask(t2);
        taskHandler.addTask(t3);

        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(t1);
        expectedTaskList.add(t2);

        assertEquals(taskHandler.findTasks("read"), expectedTaskList);
    }

    @Test
    public void taskHandlerTest_convertsTaskToStringCorrectly() {
        TaskHandler taskHandler = new TaskHandler();
        int taskCount = taskHandler.taskCount();

        // Clear any potential pre-existing tasks for proper testing
        for (int i = 0; i < taskCount; i++) {
            taskHandler.deleteTask(0);
        }

        Task t1 = new ToDo("play games");
        Task t2 = new Deadline("buy present",
                LocalDateTime.of(2024, 10, 20, 12, 0));

        taskHandler.addTask(t1);
        taskHandler.addTask(t2);

        assertEquals(taskHandler.taskListToString(), "1. [T][ ] play games \n"
                + "2. [D][ ] buy present  (due: 20 Oct 2024, 12:00)");

    }
}
