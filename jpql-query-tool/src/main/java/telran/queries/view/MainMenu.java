package telran.queries.view;

import java.time.LocalDate;

import telran.queries.entities.Gamer;
import telran.queries.services.BullsCowsService;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class MainMenu {
    private final BullsCowsService service;
    private final InputOutput io;
    private static final String BLUE_COLOR = "\033[1;34m";
    private static final String WHITE_COLOR = "\033[0;37m";
    private static final String RESET_COLOR = "\033[0m";

    public MainMenu(BullsCowsService service, InputOutput io) {
        this.service = service;
        this.io = io;
    }

    public void run() {
        Item[] items = getMainItems();
        Menu menu = new Menu("Main Menu", items);
        menu.perform(io);
    }

    private Item[] getMainItems() {
        return new Item[] {
                Item.of("Sign In", this::signIn),
                Item.of("Sign Up", this::signUp),
                Item.ofExit()
        };
    }

    private void signIn(InputOutput io) {
        String username = io.readString("Enter username");
        try {
            Gamer gamer = service.loginGamer(username);
            io.writeLine("Welcome back, " + gamer.getUsername() + "!");
            showGameMenu(gamer);
        } catch (IllegalArgumentException e) {
            io.writeLine(e.getMessage());
        }
    }

    private void signUp(InputOutput io) {
        String username = io.readString("Enter username");
        String birthdate = io.readString("Enter birthdate (YYYY-MM-DD)");
        Gamer gamer = new Gamer();
        gamer.setUsername(username);
        gamer.setBirthdate(LocalDate.parse(birthdate));
        try {
            service.registerGamer(gamer);
            io.writeLine("Registration successful. Welcome, " + gamer.getUsername() + "!");
            showGameMenu(gamer);
        } catch (IllegalArgumentException e) {
            io.writeLine(e.getMessage());
        }
    }

    private void showGameMenu(Gamer gamer) {
        Item[] items = {
                Item.of("Create Game", io -> createGame(gamer)),
                Item.of("Join Game", io -> joinGame(gamer)),
                Item.of("Start Game", io -> startGame(gamer)),
                Item.of("Make Move", io -> makeMove(gamer)),
                Item.ofExit()
        };
        Menu menu = new Menu("Game Menu", items);
        menu.perform(io);
    }

    private void createGame(Gamer gamer) {
        String gameName = io.readString("Enter game name");
        service.createGame(gameName, gamer.getUsername());
        io.writeLine("Game created successfully.");
    }

    private void joinGame(Gamer gamer) {
        String gameId = io.readString("Enter game ID");
        try {
            service.joinGame(gameId, gamer.getUsername());
            io.writeLine("Joined game successfully.");
        } catch (IllegalArgumentException e) {
            io.writeLine(e.getMessage());
        }
    }

    private void startGame(Gamer gamer) {
        String gameId = io.readString("Enter game ID");
        try {
            service.startGame(gameId, gamer.getUsername());
            io.writeLine("Game started successfully.");
        } catch (IllegalArgumentException e) {
            io.writeLine(e.getMessage());
        }
    }

    private void makeMove(Gamer gamer) {
        String gameId = io.readString("Enter game ID");
        String moveSequence = io.readString("Enter your move sequence");
        try {
            service.makeMove(gameId, gamer.getUsername(), moveSequence);
            io.writeLine("Move made successfully.");
        } catch (IllegalArgumentException e) {
            io.writeLine(e.getMessage());
        }
    }
}
