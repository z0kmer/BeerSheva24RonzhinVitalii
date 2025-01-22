package telran.queries.services;

import java.util.List;

import telran.queries.entities.Game;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;

public interface BullsCowsService {
    Game createGame(String sequence, String creator, String name);
    void startGame(String gameId, String username);
    void joinGame(String gameId, String username);
    List<Game> getAvailableGames();
    List<Game> getNonStartedGames();
    Gamer loginGamer(String username);
    void registerGamer(Gamer gamer);
    List<Move> getMoves(String gameId);
    void makeMove(String gameId, String username, String moveSequence);
    String getWinner(Long gameId);
    String generateRandomSequence();
}
