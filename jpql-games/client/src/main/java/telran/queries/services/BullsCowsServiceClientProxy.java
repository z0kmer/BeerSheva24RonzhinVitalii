package telran.queries.services;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import telran.queries.entities.Game;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;

public class BullsCowsServiceClientProxy implements BullsCowsService {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public BullsCowsServiceClientProxy(ObjectOutputStream oos, ObjectInputStream ois) {
        this.oos = oos;
        this.ois = ois;
    }

    private <T> T sendRequest(String command, Object... params) {
        try {
            System.out.println("Sending request: " + command);
            oos.writeObject(command);
            for (Object param : params) {
                oos.writeObject(param);
            }
            oos.flush();
            Object response = ois.readObject();
            System.out.println("Received response: " + response);
            return (T) response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Game createGame(String sequence, String creator, String name) {
        return sendRequest("CREATE_GAME", sequence, creator, name);
    }

    @Override
    public void startGame(String gameId, String username) {
        sendRequest("START_GAME", gameId, username);
    }

    @Override
    public void joinGame(String gameId, String username) {
        sendRequest("JOIN_GAME", gameId, username);
    }

    @Override
    public List<Game> getAvailableGames() {
        Object response = sendRequest("GET_AVAILABLE_GAMES");
        if (response instanceof List<?>) {
            return (List<Game>) response;
        } else {
            throw new RuntimeException("Unexpected response type: " + response.getClass());
        }
    }

    @Override
    public List<Game> getNonStartedGames() {
        Object response = sendRequest("GET_NON_STARTED_GAMES");
        if (response instanceof List<?>) {
            return (List<Game>) response;
        } else {
            throw new RuntimeException("Unexpected response type: " + response.getClass());
        }
    }

    @Override
    public Gamer loginGamer(String username) {
        try {
            System.out.println("Logging in gamer: " + username);
            oos.writeObject("LOGIN_GAMER");
            oos.writeObject(username);
            oos.flush();
            Object response = ois.readObject();
            System.out.println("Received response for login: " + response);
            if (response instanceof Gamer) {
                return (Gamer) response;
            } else {
                throw new RuntimeException("Unexpected response type: " + response.getClass());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerGamer(Gamer gamer) {
        sendRequest("REGISTER_GAMER", gamer.getUsername(), gamer.getBirthdate().toString());
    }

    @Override
    public List<Move> getMoves(String gameId) {
        Object response = sendRequest("GET_MOVES", gameId);
        if (response instanceof List<?>) {
            return (List<Move>) response;
        } else {
            throw new RuntimeException("Unexpected response type: " + response.getClass());
        }
    }

    @Override
    public void makeMove(String gameId, String username, String moveSequence) {
        sendRequest("MAKE_MOVE", gameId, username, moveSequence);
    }

    @Override
    public String getWinner(Long gameId) {
        return sendRequest("GET_WINNER", gameId);
    }

    @Override
    public String generateRandomSequence() {
        return sendRequest("GENERATE_RANDOM_SEQUENCE");
    }
}
