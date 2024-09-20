package ginger.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Encapsulates a TaskStorage class which reads and saves tasks to the local database.
 */
public class TaskStorage {
    private final File taskFile;

    public TaskStorage(String filePath) {
        this.taskFile = new File(filePath);
    }

    /**
     * Reads and parses the local database for pre-existing tasks. Creates a new database if non-existent.
     * @return The list of tasks read from the local database.
     */
    public List<Task> readTasks() {
        List<Task> tasks = new ArrayList<>();

        if (!taskFile.exists()) {
            try {
                taskFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Looks like we overcooked that one... An error occurred creating your database.");
            }
        }

        try {
            Scanner dbScanner = new Scanner(taskFile);
            while (dbScanner.hasNextLine()) {
                String line = dbScanner.nextLine();
                String[] parts = line.split("\\|");
                switch (parts[0].trim()) {
                case "T":
                    tasks.add(new ToDo(parts[2].trim(), parts[1].trim().equals("1"), parts[3].trim()));
                    break;
                case "D":
                    tasks.add(new Deadline(parts[2].trim(),
                            LocalDateTime.parse(parts[4].trim()), parts[1].trim().equals("1"), parts[3].trim()));
                    break;
                case "E":
                    tasks.add(new Event(parts[2].trim(), LocalDateTime.parse(parts[4].trim()),
                            LocalDateTime.parse(parts[5].trim()), parts[1].trim().equals("1"), parts[3].trim()));
                    break;
                default:
                    // Do nothing as the current task was badly written, don't need to input it in.
                }
            }
            dbScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves the task to the local database upon exit of the bot.
     * @param taskList The list containing all tasks to be saved.
     */
    public void saveTasks(List<Task> taskList) {
        try {
            FileWriter fw = new FileWriter("tasks.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            taskList.forEach(task -> {
                try {
                    bw.write(task.toDbString());
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println("Looks like we overcooked that one... Failed to write tasks to file");
                }
            });
            bw.close();
        } catch (IOException e) {
            System.out.println("Looks like we overcooked that one... Failed to write tasks to file");
        }
    }
}
