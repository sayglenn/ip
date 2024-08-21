import java.util.ArrayList;
import java.util.Scanner;

public class Ginger {
    private final static String HORIZONTAL_LINE = "____________________________________________________________";
    private final static String BOT_NAME = "Ginger";

    private final static ArrayList<String> taskList = new ArrayList<>();

    private String retrieveTasks() {
        String result = "";

        for (int i = 0; i < taskList.size(); i++) {
            result += (i + 1) + ". " + taskList.get(i) + "\n";
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Greet the user
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        String input = sc.nextLine();
        while (!input.equals("bye")) {
            System.out.println(HORIZONTAL_LINE + "\n" + input + "\n" + HORIZONTAL_LINE);
            input = sc.nextLine();
        }
        sc.close();

        System.out.println(HORIZONTAL_LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }
}
