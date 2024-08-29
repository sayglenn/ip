package ginger.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskHandlerTest {

    @Test
    public void TaskHandlerTest_TaskAddsCorrectly() {
        TaskHandler taskHandler = new TaskHandler();

        // Clear any potential pre-existing tasks for proper testing
        for (int i = 0; i < taskHandler.taskCount(); i++) {
            taskHandler.deleteTask(0);
        }

        Task t = new ToDo("buy groceries");
        taskHandler.addTask(t);

        assertEquals(taskHandler.getTask(0), t);
    }
}
