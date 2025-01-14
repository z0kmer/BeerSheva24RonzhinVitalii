package telran.queries.view;

import java.util.List;

import telran.queries.entities.Move;
import telran.queries.services.BullsCowsService;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class GamePlayMenu {
    private final BullsCowsService service;
    private final InputOutput io;
    private final String username;
    private final String gameId;

    public GamePlayMenu(BullsCowsService service, InputOutput io, String username, String gameId) {
        this.service = service;
        this.io = io;
        this.username = username;
        this.gameId = gameId;
    }

    public void run() {
        io.writeLine("---------------------------------");
        io.writeLine("Game Play Menu");
        Item[] items = getGamePlayItems();
        Menu menu = new Menu("", items);
        io.writeLine("---------------------------------");
        menu.perform(io);
    }

    private Item[] getGamePlayItems() {
        return new Item[] {
                Item.of("Make Move", this::makeMove),
                Item.of("Show All Moves", this::showAllMoves),
                Item.of("Return to Game Menu", this::returnToGameMenu)
        };
    }

    private void makeMove(InputOutput io) {
        String moveSequence = io.readString("Enter your move sequence (four-digit number)");
        if (moveSequence.length() == 4) {
            service.makeMove(gameId, username, moveSequence, service, io);
        } else {
            io.writeLine("Invalid move. Please enter a four-digit number.");
        }
        run();
    }

    private void showAllMoves(InputOutput io) {
        List<Move> moves = service.getMoves(gameId);
        for (Move move : moves) {
            io.writeLine(String.format("%s - bulls: %d - cows: %d", move.getSequence(), move.getBulls(), move.getCows()));
        }
        run();
    }

    private void returnToGameMenu(InputOutput io) {
        GameMenu gameMenu = new GameMenu(service, io, username);
        gameMenu.run();
    }
}
