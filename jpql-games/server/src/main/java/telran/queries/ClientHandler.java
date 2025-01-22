package telran.queries;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;

import telran.queries.entities.Gamer;
import telran.queries.services.BullsCowsService;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BullsCowsService service;

    public ClientHandler(Socket socket, BullsCowsService service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            while (true) {
                String command = (String) ois.readObject();
                Object response = null;
                System.out.println("Received command: " + command);

                switch (command) {
                    case "LOGIN_GAMER":
                        String username = (String) ois.readObject();
                        System.out.println("Logging in gamer: " + username);
                        response = service.loginGamer(username);
                        break;
                    case "REGISTER_GAMER":
                        String user = (String) ois.readObject();
                        String birthdate = (String) ois.readObject();
                        System.out.println("Registering gamer: " + user);
                        Gamer gamer = new Gamer();
                        gamer.setUsername(user);
                        gamer.setBirthdate(LocalDate.parse(birthdate));
                        service.registerGamer(gamer);
                        response = "Registration successful.";
                        break;
                    case "CREATE_GAME":
                        String seq = (String) ois.readObject();
                        String creator = (String) ois.readObject();
                        String name = (String) ois.readObject();
                        System.out.println("Creating game: " + name);
                        response = service.createGame(seq, creator, name);
                        break;
                    case "START_GAME":
                        String gameId = (String) ois.readObject();
                        String userToStart = (String) ois.readObject();
                        System.out.println("Starting game: " + gameId);
                        service.startGame(gameId, userToStart);
                        response = "Game started.";
                        break;
                    case "JOIN_GAME":
                        String gameIdToJoin = (String) ois.readObject();
                        String userToJoin = (String) ois.readObject();
                        System.out.println("Joining game: " + gameIdToJoin);
                        service.joinGame(gameIdToJoin, userToJoin);
                        response = "Joined game.";
                        break;
                    case "MAKE_MOVE":
                        String gameIdForMove = (String) ois.readObject();
                        String moveUser = (String) ois.readObject();
                        String moveSeq = (String) ois.readObject();
                        System.out.println("Making move: " + moveSeq + " for game: " + gameIdForMove + " by user: " + moveUser);
                        try {
                            service.makeMove(gameIdForMove, moveUser, moveSeq);
                            response = "Move made.";
                        } catch (Exception e) {
                            response = "Error making move: " + e.getMessage();
                            System.err.println("Error making move: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case "GET_AVAILABLE_GAMES":
                        System.out.println("Getting available games");
                        response = service.getAvailableGames();
                        break;
                    case "GET_NON_STARTED_GAMES":
                        System.out.println("Getting non-started games");
                        response = service.getNonStartedGames();
                        break;
                    case "GET_MOVES":
                        String gameIdForMoves = (String) ois.readObject();
                        System.out.println("Getting moves for game: " + gameIdForMoves);
                        response = service.getMoves(gameIdForMoves);
                        break;
                    default:
                        response = "Unknown command.";
                        System.out.println("Unknown command received: " + command);
                }

                System.out.println("Sending response: " + response);
                oos.writeObject(response);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
