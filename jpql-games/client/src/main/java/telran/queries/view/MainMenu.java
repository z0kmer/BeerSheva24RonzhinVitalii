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

    public MainMenu(BullsCowsService service, InputOutput io) {
        this.service = service;
        this.io = io;
    }

    public void run() {
        io.writeLine("---------------------------------");
        io.writeLine("Bulls & Cows (v.0.1)");
        Item[] items = getMainItems();
        Menu menu = new Menu("", items);
        io.writeLine("---------------------------------");
        menu.perform(io);
    }

    private Item[] getMainItems() {
        return new Item[] {
                Item.of("Sign In", this::signIn),
                Item.of("Sign Up", this::signUp),
                Item.of("Exit", this::exit)
        };
    }

    private void signIn(InputOutput io) {
        String username = io.readString("Enter username:");
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
        String username = io.readString("Enter username:");
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

    private void exit(InputOutput io) {
        io.writeLine("Exiting the application. Goodbye!");
        System.exit(0);
    }
}
