package telran.queries.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import telran.queries.entities.Game;
import telran.queries.entities.GameGamer;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;

public class BullsCowsRepositoryImpl implements BullsCowsRepository {
    private final EntityManagerFactory emf;

    public BullsCowsRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Gamer saveGamer(Gamer gamer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(gamer);
        em.getTransaction().commit();
        em.close();
        return gamer;
    }

    @Override
    public Gamer findGamerByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        Gamer gamer = em.find(Gamer.class, username);
        em.close();
        return gamer;
    }

    @Override
    public Game saveGame(Game game) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(game);
        em.getTransaction().commit();
        em.close();
        return game;
    }

    @Override
    public Game findGameById(String gameId) {
        EntityManager em = emf.createEntityManager();
        Game game = em.find(Game.class, Long.parseLong(gameId));
        em.close();
        return game;
    }

    @Override
    public List<Game> findAllUnstartedGames() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g WHERE g.dateGame = false", Game.class);
        List<Game> resultList = query.getResultList();
        em.close();
        return resultList;
    }

    @Override
    public List<GameGamer> findAllGamesByGamer(String gamerUsername) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<GameGamer> query = em.createQuery("SELECT gg FROM GameGamer gg WHERE gg.gamer.username = :username",
                GameGamer.class);
        query.setParameter("username", gamerUsername);
        List<GameGamer> resultList = query.getResultList();
        em.close();
        return resultList;
    }

    @Override
    public List<Game> findAllStartedGamesByGamer(String gamerUsername) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Game> query = em.createQuery(
                "SELECT gg.game FROM GameGamer gg WHERE gg.gamer.username = :username AND gg.game.isFinished = false",
                Game.class);
        query.setParameter("username", gamerUsername);
        List<Game> resultList = query.getResultList();
        em.close();
        return resultList;
    }

    @Override
    public Move saveMove(Move move) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(move);
        em.getTransaction().commit();
        em.close();
        return move;
    }

    @Override
    public List<Move> findAllMovesByGameId(String gameId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Move> query = em.createQuery("SELECT m FROM Move m WHERE m.gameGamer.game.id = :gameId", Move.class);
        query.setParameter("gameId", Long.parseLong(gameId));
        List<Move> resultList = query.getResultList();
        em.close();
        return resultList;
    }

    @Override
    public void saveGameGamer(GameGamer gameGamer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(gameGamer);
        em.getTransaction().commit();
        em.close();
    }
}
