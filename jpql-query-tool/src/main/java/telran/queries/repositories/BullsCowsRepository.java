package telran.queries.repositories;

import java.util.List;

import telran.queries.entities.Game;
import telran.queries.entities.GameGamer;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;

public interface BullsCowsRepository {

    Gamer saveGamer(Gamer gamer);
    Gamer findGamerByUsername(String username);
    Game saveGame(Game game);
    Game findGameById(String gameId);
    List<Game> findAllUnstartedGames();
    List<GameGamer> findAllGamesByGamer(String gamerUsername);
    List<Game> findAllStartedGamesByGamer(String gamerUsername);
    Move saveMove(Move move);
    List<Move> findAllMovesByGameId(String gameId);
    void saveGameGamer(GameGamer gameGamer);
}
