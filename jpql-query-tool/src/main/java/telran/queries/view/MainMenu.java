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
    //private static final String BLUE_COLOR = "\033[1;34m";
    //private static final String WHITE_COLOR = "\033[0;37m";
    //private static final String RESET_COLOR = "\033[0m";

    public MainMenu(BullsCowsService service, InputOutput io) {
        this.service = service;
        this.io = io;
    }

    public void run() {
        //io.writeLine(BLUE_COLOR + "Bulls & Cows" + RESET_COLOR);
        //io.writeLine("---------------------------------");
        //io.writeLine(WHITE_COLOR + "Choose an action:" + RESET_COLOR);
        Item[] items = getMainItems();
        Menu menu = new Menu("", items);
        menu.perform(io);
        //io.writeLine(BLUE_COLOR + "---------------------------------" + RESET_COLOR);
        //io.writeLine("v.0.1");
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
            if (service.loginGamer(username) != null) {
                io.writeLine("Welcome back, " + username + "!");
                GameMenu gameMenu = new GameMenu(service, io, username);
                gameMenu.run();
            } else {
                io.writeLine("This player is not registered.");
            }
        } catch (IllegalArgumentException e) {
            io.writeLine(e.getMessage());
        }
        run();
    }

    private void signUp(InputOutput io) {
        String username = io.readString("Enter username");
        try {
            if (service.loginGamer(username) == null) {
                String birthdate = io.readString("Enter birthdate (YYYY-MM-DD)");
                Gamer gamer = new Gamer();
                gamer.setUsername(username);
                gamer.setBirthdate(LocalDate.parse(birthdate));
                service.registerGamer(gamer);
                io.writeLine("Registration successful. Welcome, " + username + "!");
                GameMenu gameMenu = new GameMenu(service, io, username);
                gameMenu.run();
            } else {
                io.writeLine("This player is already registered.");
            }
        } catch (IllegalArgumentException e) {
            io.writeLine(e.getMessage());
        }
        run();
    }
}
