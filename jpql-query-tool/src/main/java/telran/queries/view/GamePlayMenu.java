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
        Item[] items = getGamePlayItems();
        Menu menu = new Menu("Game Play Menu", items);
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
            service.makeMove(gameId, username, moveSequence);
            io.writeLine("Move made successfully.");
        } else {
            io.writeLine("Invalid move. Please enter a four-digit number.");
        }
        run();
    }

    private void showAllMoves(InputOutput io) {
        List<Move> moves = service.getMoves(gameId);
        for (Move move : moves) {
            io.writeLine(move.toString());
        }
        run();
    }

    private void returnToGameMenu(InputOutput io) {
        GameMenu gameMenu = new GameMenu(service, io, username);
        gameMenu.run();
    }
}
