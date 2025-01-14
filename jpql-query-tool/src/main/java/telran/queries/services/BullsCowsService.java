package telran.queries.services;

import java.util.List;

import telran.queries.entities.Game;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;
import telran.view.InputOutput;

public interface BullsCowsService {
    Game createGame(String sequence, String creator, String name);
    void startGame(String gameId, String username, BullsCowsService service, InputOutput io); // Обновляем интерфейс
    void joinGame(String gameId, String username);
    List<Game> getAvailableGames();
    List<Game> getGamesWithWinners();
    Gamer loginGamer(String username);
    void registerGamer(Gamer gamer);
    List<Move> getMoves(String gameId);
    void makeMove(String gameId, String username, String moveSequence, BullsCowsService service, InputOutput io);
    String getWinner(Long gameId);
}
