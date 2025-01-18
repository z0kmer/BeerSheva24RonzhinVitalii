package telran.queries;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;

import telran.queries.entities.Game;
import telran.queries.entities.Gamer;
import telran.queries.services.BullsCowsServiceImpl;
import telran.view.StandardInputOutput;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BullsCowsServiceImpl service;

    public ClientHandler(Socket socket, BullsCowsServiceImpl service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream();
             ObjectInputStream ois = new ObjectInputStream(input);
             ObjectOutputStream oos = new ObjectOutputStream(output)) {
             
            String command;
            while ((command = (String) ois.readObject()) != null) {
                try {
                    switch (command) {
                        case "CREATE_GAME":
                            String sequence = (String) ois.readObject();
                            String creator = (String) ois.readObject();
                            String name = (String) ois.readObject();
                            Game game = service.createGame(sequence, creator, name);
                            oos.writeObject(game);
                            break;
                        case "START_GAME":
                            String gameId = (String) ois.readObject();
                            String username = (String) ois.readObject();
                            service.startGame(gameId, username, service, new StandardInputOutput());
                            oos.writeObject("Game started successfully");
                            break;
                        case "JOIN_GAME":
                            gameId = (String) ois.readObject();
                            username = (String) ois.readObject();
                            service.joinGame(gameId, username);
                            oos.writeObject("Joined game successfully");
                            break;
                        case "LIST_AVAILABLE_GAMES":
                            oos.writeObject(service.getAvailableGames());
                            break;
                        case "LIST_GAMES_WITH_WINNERS":
                            oos.writeObject(service.getGamesWithWinners());
                            break;
                        case "GET_WINNER":
                            gameId = (String) ois.readObject();
                            oos.writeObject(service.getWinner(Long.parseLong(gameId)));
                            break;
                        case "MAKE_MOVE":
                            gameId = (String) ois.readObject();
                            username = (String) ois.readObject();
                            String moveSequence = (String) ois.readObject();
                            service.makeMove(gameId, username, moveSequence, service, new StandardInputOutput());
                            oos.writeObject("Move made successfully");
                            break;
                        case "GET_MOVES":
                            gameId = (String) ois.readObject();
                            oos.writeObject(service.getMoves(gameId));
                            break;
                        case "LOGIN_GAMER":
                            username = (String) ois.readObject();
                            Gamer gamer = service.loginGamer(username);
                            if (gamer != null) {
                                oos.writeObject("User found: " + username);
                            } else {
                                oos.writeObject("User not found: " + username);
                            }
                            break;
                        case "REGISTER_GAMER":
                            username = (String) ois.readObject();
                            gamer = service.loginGamer(username);
                            if (gamer == null) {
                                oos.writeObject("User not found, can register: " + username);
                            } else {
                                oos.writeObject("User already exists: " + username);
                            }
                            break;
                        case "REGISTER_GAMER_COMPLETE":
                            username = (String) ois.readObject();
                            String birthdate = (String) ois.readObject();
                            gamer = new Gamer();
                            gamer.setUsername(username);
                            gamer.setBirthdate(LocalDate.parse(birthdate)); // Убедитесь, что метод setBirthdate существует в классе Gamer
                            service.registerGamer(gamer);
                            oos.writeObject("Registration successful for user: " + username);
                            break;
                        default:
                            oos.writeObject("Unknown command");
                    }
                } catch (EOFException e) {
                    System.err.println("Connection closed by client.");
                } catch (Exception e) {
                    e.printStackTrace();
                    oos.writeObject("Error processing command: " + e.getMessage());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
