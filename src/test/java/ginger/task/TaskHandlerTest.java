package ginger.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
