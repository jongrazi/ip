import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        String separator = "_".repeat(60);

        System.out.println(separator);
        System.out.println("Welcome back sir, I am F.R.I.D.A.Y");
        System.out.println("What can I do for you sir?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            //if bye then go to goodbye block
            if (command.trim().equalsIgnoreCase("bye")) {
                System.out.println(separator);
                System.out.println("Goodbye sir");
                System.out.println(separator);
                break;
            }

            //to echo the text input
            System.out.println(separator);
            System.out.println(" " + command);
            System.out.println(separator);
        }

        scanner.close();
    }
}
