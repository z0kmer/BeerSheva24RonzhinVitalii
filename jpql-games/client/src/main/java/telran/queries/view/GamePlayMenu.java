package telran.queries.view;

import java.util.List;
import java.util.Scanner;

import telran.queries.entities.Move;
import telran.queries.services.BullsCowsService;

public class GamePlayMenu {
    private final BullsCowsService service;
    private final Scanner scanner;
    private final String username;
    private final String gameId;

    public GamePlayMenu(BullsCowsService service, Scanner scanner, String username, String gameId) {
        this.service = service;
        this.scanner = scanner;
        this.username = username;
        this.gameId = gameId;
    }

    public void run() {
        while (true) {
            System.out.println("---------------------------------");
            System.out.println("Game Play Menu");
            System.out.println("---------------------------------");
            System.out.println("1. View All Moves");
            System.out.println("2. Make a Move");
            System.out.println("3. Return to Game Menu");
            System.out.println("4. Exit to Main Menu");
            System.out.print("Select item: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> viewAllMoves();
                case 2 -> makeMove();
                case 3 -> {
                    GameMenu gameMenu = new GameMenu(service, scanner, username);
                    gameMenu.run();
                    return;
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAllMoves() {
        List<Move> moves = service.getMoves(gameId);
        if (moves == null || moves.isEmpty()) {
            System.out.println("No moves found.");
            return;
        }
        for (Move move : moves) {
            System.out.printf("%s - bulls: %d - cows: %d%n", move.getSequence(), move.getBulls(), move.getCows());
        }
    }

    private void makeMove() {
        System.out.print("Enter a four-digit number (or q to return to Game Menu): ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("q")) {
            return;
        }
        if (input.length() != 4 || !input.matches("\\d{4}")) {
            System.out.println("Invalid input. Please enter a four-digit number.");
            return;
        }
        service.makeMove(gameId, username, input);
    }
}
