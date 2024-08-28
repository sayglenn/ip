import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskStorage {
    private File taskFile;

    public TaskStorage(String filePath) {
        this.taskFile = new File(filePath);
    }

    public List<Task> readTasks() {
        List<Task> tasks = new ArrayList<>();

        if (!taskFile.exists()) {
            try {
                taskFile.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred when creating your database.");
            }
        }

        try {
            Scanner dbScanner = new Scanner(taskFile);
            while (dbScanner.hasNextLine()) {
                String line = dbScanner.nextLine();
                String[] parts = line.split("\\|");
                switch (parts[0].trim()) {
                case "T":
                    tasks.add(new ToDo(parts[2].trim(), parts[1].trim().equals("1")));
                    break;
                case "D":
                    tasks.add(new Deadline(parts[2].trim(),
                            LocalDateTime.parse(parts[3].trim()), parts[1].trim().equals("1")));
                    break;
                case "E":
                    tasks.add(new Event(parts[2].trim(), LocalDateTime.parse(parts[3].trim()),
                            LocalDateTime.parse(parts[4].trim()), parts[1].trim().equals("1")));
                    break;
                }
            }
            dbScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        return tasks;
    }

    public void saveTasks(List<Task> taskList) {
        try {
            FileWriter fw = new FileWriter("tasks.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            taskList.forEach(task -> {
                try {
                    bw.write(task.toDbString());
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println("Failed to write tasks to file");
                }
            });
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to write tasks to file");
        }
    }
}
