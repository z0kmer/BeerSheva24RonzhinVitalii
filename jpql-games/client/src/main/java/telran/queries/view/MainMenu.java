package telran.queries.view;

import java.time.LocalDate;
import java.util.Scanner;

import telran.queries.entities.Gamer;
import telran.queries.services.BullsCowsService;

public class MainMenu {
    private final BullsCowsService service;
    private final Scanner scanner;

    public MainMenu(BullsCowsService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("---------------------------------");
        System.out.println("Bulls & Cows (v.0.1)");
        while (true) {
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> signIn();
                case 2 -> signUp();
                case 3 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void signIn() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        try {
            if (service.loginGamer(username) != null) {
                System.out.println("Welcome back, " + username + "!");
                GameMenu gameMenu = new GameMenu(service, scanner, username);
                gameMenu.run();
            } else {
                System.out.println("This player is not registered.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void signUp() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        try {
            if (service.loginGamer(username) == null) {
                System.out.print("Enter birthdate (YYYY-MM-DD): ");
                String birthdate = scanner.nextLine();
                Gamer gamer = new Gamer();
                gamer.setUsername(username);
                gamer.setBirthdate(LocalDate.parse(birthdate));
                service.registerGamer(gamer);
                System.out.println("Registration successful. Welcome, " + username + "!");
                GameMenu gameMenu = new GameMenu(service, scanner, username);
                gameMenu.run();
            } else {
                System.out.println("This player is already registered.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
