package telran.queries.view;

import java.util.List;

import telran.queries.entities.Game;
import telran.queries.services.BullsCowsService;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class GameMenu {
    private final BullsCowsService service;
    private final InputOutput io;
    private final String username;

    public GameMenu(BullsCowsService service, InputOutput io, String username) {
        this.service = service;
        this.io = io;
        this.username = username;
    }

    public void run() {
        io.writeLine("Game Menu");
        io.writeLine("---------------------------------");
        io.writeLine("Choose an action:");
        Item[] items = getGameItems();
        Menu menu = new Menu("", items);
        menu.perform(io);
        io.writeLine("---------------------------------");
        io.writeLine("Select item");
    }

    private Item[] getGameItems() {
        return new Item[] {
                Item.of("Create Game", this::createGame),
                Item.of("Start Game", this::startGameOption),
                Item.of("List Available Games", this::listAvailableGames),
                Item.of("List Games with Winners", this::listGamesWithWinners),
                Item.of("Join Game", this::joinGameOption),
                Item.of("Exit", this::exit)
        };
    }

    private void createGame(InputOutput io) {
        String sequence = "default_sequence";
        String name = "Game" + (System.currentTimeMillis() % 1000);
        Game game = service.createGame(sequence, username, name);
        io.writeLine("Game created successfully. Game ID: " + game.getId());
        io.writeLine("Sequence: " + game.getSequence());

        String gameId = String.valueOf(game.getId());

        Item[] items = {
                Item.of("Start Game", x -> startGame(gameId)),
                Item.of("Join Game", x -> joinGame(gameId)),
                Item.of("Return to Game Menu", x -> run())
        };

        Menu menu = new Menu("Game Options", items);
        menu.perform(io);
    }

    private void startGameOption(InputOutput io) {
        String gameId = io.readString("Enter game ID to start");
        startGame(gameId);
    }

    private void startGame(String gameId) {
        Game game = service.getAvailableGames().stream()
                          .filter(g -> g.getId().toString().equals(gameId))
                          .findFirst()
                          .orElse(null);

        if (game == null) {
            io.writeLine("This game ID does not exist. Returning to Game Menu.");
            run();
        } else {
            service.startGame(gameId, username);
            io.writeLine("Game started successfully.");
            new GamePlayMenu(service, io, username, gameId).run();
        }
    }

    private void joinGame(String gameId) {
        Game game = service.getAvailableGames().stream()
                          .filter(g -> g.getId().toString().equals(gameId))
                          .findFirst()
                          .orElse(null);

        if (game == null) {
            io.writeLine("This game ID does not exist. Returning to Game Menu.");
            run();
        } else if (game.getDateGame() == null) {
            io.writeLine("This game has not started yet. Returning to Game Menu.");
            run();
        } else {
            service.joinGame(gameId, username);
            io.writeLine("Joined game successfully.");
            new GamePlayMenu(service, io, username, gameId).run();
        }
    }

    private void listAvailableGames(InputOutput io) {
        List<Game> games = service.getAvailableGames();
        if (games.isEmpty()) {
            io.writeLine("No available games found.");
        } else {
            for (Game game : games) {
                io.writeLine(game.toString());
            }
        }
        run();
    }

    private void listGamesWithWinners(InputOutput io) {
        List<Game> games = service.getGamesWithWinners();
        if (games.isEmpty()) {
            io.writeLine("No games with winners found.");
        } else {
            for (Game game : games) {
                String winner = service.getWinner(game.getId());
                io.writeLine(String.format("%d - %s - %s - creator: %s - winner: %s",
                        game.getId(), game.getName(), game.getDateGame(), game.getCreator(), winner));
            }
        }
        run();
    }

    private void joinGameOption(InputOutput io) {
        String gameId = io.readString("Enter game ID to join");
        joinGame(gameId);
    }

    private void exit(InputOutput io) {
        io.writeLine("Exiting the application. Goodbye!");
        System.exit(0);
    }
}
