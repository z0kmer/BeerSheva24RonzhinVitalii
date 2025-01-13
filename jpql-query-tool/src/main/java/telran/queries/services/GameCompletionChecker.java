package telran.queries.services;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import telran.queries.entities.Game;
import telran.queries.entities.Move;

public class GameCompletionChecker {
    private final EntityManagerFactory emf;

    public GameCompletionChecker(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void checkAndCompleteGames() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        List<Game> games = em.createQuery("SELECT g FROM Game g WHERE g.isFinished = false", Game.class).getResultList();
        
        for (Game game : games) {
            List<Move> moves = em.createQuery("SELECT m FROM Move m WHERE m.gameGamer.game.id = :gameId", Move.class)
                                 .setParameter("gameId", game.getId())
                                 .getResultList();
            boolean isGameCompleted = moves.stream().anyMatch(move -> move.getBulls() == 4);
            
            if (isGameCompleted) {
                game.setFinished(true);
                em.createQuery("UPDATE GameGamer gg SET gg.isWinner = true WHERE gg.game.id = :gameId")
                  .setParameter("gameId", game.getId())
                  .executeUpdate();
            }
        }
        
        em.getTransaction().commit();
        em.close();
    }
}
