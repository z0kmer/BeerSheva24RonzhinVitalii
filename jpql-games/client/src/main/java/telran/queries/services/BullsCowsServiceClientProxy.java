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
            oos.writeObject(command);
            for (Object param : params) {
                oos.writeObject(param);
            }
            oos.flush();
            return (T) ois.readObject();
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
        return sendRequest("GET_AVAILABLE_GAMES");
    }

    @Override
    public List<Game> getNonStartedGames() {
        return sendRequest("GET_NON_STARTED_GAMES");
    }

    @Override
    public Gamer loginGamer(String username) {
        return sendRequest("LOGIN_GAMER", username);
    }

    @Override
    public void registerGamer(Gamer gamer) {
        sendRequest("REGISTER_GAMER", gamer.getUsername(), gamer.getBirthdate().toString());
    }

    @Override
    public List<Move> getMoves(String gameId) {
        return sendRequest("GET_MOVES", gameId);
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
