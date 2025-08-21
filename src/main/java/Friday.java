import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        String separator = "_".repeat(60);

        System.out.println(separator);
        System.out.println("It's good to have you back sir, I am F.R.I.D.A.Y");
        System.out.println("What can I do for you sir?");
        System.out.println(separator);

        List<String> tasks = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.trim().equalsIgnoreCase("bye")) {
                //if bye then go to goodbye block
                System.out.println(separator);
                System.out.println("Affirmative, goodnight sir.");
                System.out.println(separator);
                break;

            } else if (command.equalsIgnoreCase("list")) {
                //if list then show the stored list
                System.out.println(separator);
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }

            System.out.println(separator);

        } else {
            //else store all the text
            tasks.add(command);
            System.out.println(separator);
            System.out.println("added: " + command);
            System.out.println(separator);
        }

        }
        scanner.close();
    }
}
