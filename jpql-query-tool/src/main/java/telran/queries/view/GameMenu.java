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
        Item[] items = getGameItems();
        Menu menu = new Menu("Game Menu", items);
        menu.perform(io);
    }

    private Item[] getGameItems() {
        return new Item[] {
                Item.of("Create Game", this::createGame),
                Item.of("List Available Games", this::listAvailableGames),
                Item.of("List Games with Winners", this::listGamesWithWinners),
                Item.of("Return to Main Menu", this::returnToMainMenu)
        };
    }

    private void createGame(InputOutput io) {
        String gameName = io.readString("Enter game name");
        Game game = service.createGame(gameName, username);
        io.writeLine("Game created successfully. Game ID: " + game.getId());
        run();
    }

    private void listAvailableGames(InputOutput io) {
        List<Game> games = service.getAvailableGames();
        for (Game game : games) {
            io.writeLine(game.toString());
        }
        run();
    }

    private void listGamesWithWinners(InputOutput io) {
        List<Game> games = service.getStartedGames(username);
        for (Game game : games) {
            io.writeLine(game.toString());
        }
        run();
    }

    private void returnToMainMenu(InputOutput io) {
        MainMenu mainMenu = new MainMenu(service, io);
        mainMenu.run();
    }
}
