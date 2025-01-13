package telran.queries.repositories;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import telran.queries.entities.Game;
import telran.queries.entities.GameGamer;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;

public class BullsCowsRepositoryImpl implements BullsCowsRepository {

    private final EntityManager em;

    public BullsCowsRepositoryImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public Gamer saveGamer(Gamer gamer) {
        em.getTransaction().begin();
        em.persist(gamer);
        em.getTransaction().commit();
        return gamer;
    }

    @Override
    public Gamer findGamerByUsername(String username) {
        return em.find(Gamer.class, username);
    }

    @Override
    public Game saveGame(Game game) {
        em.getTransaction().begin();
        em.persist(game);
        em.getTransaction().commit();
        return game;
    }

    @Override
    public Game findGameById(String gameId) {
        return em.find(Game.class, Long.parseLong(gameId));
    }

    @Override
    public List<Game> findAllUnstartedGames() {
        TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g WHERE g.isFinished = false", Game.class);
        return query.getResultList();
    }

    @Override
    public List<GameGamer> findAllGamesByGamer(String gamerUsername) {
        TypedQuery<GameGamer> query = em.createQuery("SELECT gg FROM GameGamer gg WHERE gg.gamer.username = :username", GameGamer.class);
        query.setParameter("username", gamerUsername);
        return query.getResultList();
    }

    @Override
    public List<Game> findAllStartedGamesByGamer(String gamerUsername) {
        TypedQuery<Game> query = em.createQuery("SELECT gg.game FROM GameGamer gg WHERE gg.gamer.username = :username AND gg.game.isFinished = false", Game.class);
        query.setParameter("username", gamerUsername);
        return query.getResultList();
    }

    @Override
    public Move saveMove(Move move) {
        em.getTransaction().begin();
        em.persist(move);
        em.getTransaction().commit();
        return move;
    }

    @Override
    public List<Move> findAllMovesByGameId(String gameId) {
        TypedQuery<Move> query = em.createQuery("SELECT m FROM Move m WHERE m.gameGamer.game.id = :gameId", Move.class);
        query.setParameter("gameId", Long.parseLong(gameId));
        return query.getResultList();
    }

    @Override
    public void saveGameGamer(GameGamer gameGamer) {
        em.getTransaction().begin();
        em.persist(gameGamer);
        em.getTransaction().commit();
    }
}
