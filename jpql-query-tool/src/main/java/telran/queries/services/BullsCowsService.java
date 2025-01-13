package telran.queries.services;

import java.util.List;

import telran.queries.entities.Game;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;

public interface BullsCowsService {

    Gamer registerGamer(Gamer gamer);
    Gamer loginGamer(String username);
    Game createGame(String gameName, String creatorUsername);
    void joinGame(String gameId, String gamerUsername);
    void startGame(String gameId, String starterUsername);
    Move makeMove(String gameId, String gamerUsername, String move);
    List<Game> getAvailableGames();
    List<Game> getGamerGames(String gamerUsername);
    List<Game> getStartedGames(String gamerUsername);
}
