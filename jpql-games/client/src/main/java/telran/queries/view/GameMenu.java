package telran.queries.view;

import java.util.List;
import java.util.Scanner;

import telran.queries.entities.Game;
import telran.queries.services.BullsCowsService;

public class GameMenu {
    private final BullsCowsService service;
    private final Scanner scanner;
    private final String username;

    public GameMenu(BullsCowsService service, Scanner scanner, String username) {
        this.service = service;
        this.scanner = scanner;
        this.username = username;
    }

    public void run() {
        while (true) {
            System.out.println("---------------------------------");
            System.out.println("Game Menu");
            System.out.println("---------------------------------");
            System.out.println("1. Show Available Games");
            System.out.println("2. Create Game");
            System.out.println("3. Start Game");
            System.out.println("4. Join Game");
            System.out.println("5. Exit to Main Menu");
            System.out.print("Select item: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> showAvailableGames();
                case 2 -> createGame();
                case 3 -> startGame();
                case 4 -> joinGame();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showAvailableGames() {
        List<Game> availableGames = service.getAvailableGames();
        if (availableGames == null || availableGames.isEmpty()) {
            System.out.println("No available games found.");
            return;
        }
        availableGames.forEach(game -> System.out.printf("%d - %s (by: %s)\n", game.getId(), game.getName(), game.getCreator()));
    }

    private void createGame() {
        System.out.print("Enter game name: ");
        String gameName = scanner.nextLine();
        service.createGame(service.generateRandomSequence(), username, gameName);
        System.out.println("Game " + gameName + " created. To start the game, you need to start it.");
    }

    private void startGame() {
        List<Game> nonStartedGames = service.getNonStartedGames();
        if (nonStartedGames == null || nonStartedGames.isEmpty()) {
            System.out.println("No non-started games found.");
            return;
        }
        nonStartedGames.forEach(game -> System.out.printf("%d - %s (by: %s) - Not started\n", game.getId(), game.getName(), game.getCreator()));
        
        System.out.print("Enter game ID to start: ");
        String gameId = scanner.nextLine();
        if (nonStartedGames.stream().noneMatch(game -> game.getId().equals(Long.parseLong(gameId)))) {
            System.out.println("No such game in the list.");
            return;
        }

        service.startGame(gameId, username);
        System.out.println("Game with ID " + gameId + " started successfully.");
    }

    private void joinGame() {
        List<Game> availableGames = service.getAvailableGames();
        if (availableGames == null || availableGames.isEmpty()) {
            System.out.println("No available games found.");
            return;
        }
        availableGames.forEach(game -> System.out.printf("%d - %s (by: %s) - started: %s\n", game.getId(), game.getName(), game.getCreator(), game.getDateGame()));

        System.out.print("Enter game ID to join: ");
        String gameId = scanner.nextLine();
        if (availableGames.stream().noneMatch(game -> game.getId().equals(Long.parseLong(gameId)))) {
            System.out.println("No such game in the list.");
            return;
        }

        service.joinGame(gameId, username);
        System.out.println("Joined game with ID " + gameId + " successfully.");
        GamePlayMenu gamePlayMenu = new GamePlayMenu(service, scanner, username, gameId);
        gamePlayMenu.run();
    }
}
