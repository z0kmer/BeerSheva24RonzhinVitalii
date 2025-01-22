package telran.queries.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import telran.queries.entities.Game;
import telran.queries.entities.GameGamer;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;
import telran.queries.repositories.BullsCowsRepository;

public class BullsCowsServiceImpl implements BullsCowsService {

    private final EntityManagerFactory emf;
    private final BullsCowsRepository repository;

    public BullsCowsServiceImpl(EntityManagerFactory emf, BullsCowsRepository repository) {
        this.emf = emf;
        this.repository = repository;
    }

    @Override
    public Game createGame(String sequence, String creator, String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Game game = new Game(generateRandomSequence(), creator, name);
        em.persist(game);
        em.getTransaction().commit();
        em.close();
        return game;
    }

    @Override
    public void startGame(String gameId, String username) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Game game = em.find(Game.class, Long.parseLong(gameId));
        if (game == null || game.getDateGame() != null) {
            em.getTransaction().commit();
            em.close();
            return;
        }
        game.setDateGame(LocalDate.now());
        em.merge(game);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void joinGame(String gameId, String username) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Game game = em.find(Game.class, Long.parseLong(gameId));
        Gamer gamer = em.find(Gamer.class, username);

        if (game != null && gamer != null && game.getDateGame() != null && !game.isFinished()) {
            List<GameGamer> existingGameGamers = em
                    .createQuery(
                            "SELECT gg FROM GameGamer gg WHERE gg.game.id = :gameId AND gg.gamer.username = :username",
                            GameGamer.class)
                    .setParameter("gameId", game.getId())
                    .setParameter("username", username)
                    .getResultList();

            if (existingGameGamers.isEmpty()) {
                GameGamer gameGamer = new GameGamer();
                gameGamer.setGame(game);
                gameGamer.setGamer(gamer);
                gameGamer.setIsWinner(false);
                em.persist(gameGamer);
            }
        }

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Game> getAvailableGames() {
        EntityManager em = emf.createEntityManager();
        List<Game> games = em
                .createQuery("SELECT g FROM Game g WHERE g.dateGame IS NOT NULL AND g.isFinished = false", Game.class)
                .getResultList();
        em.close();
        return games;
    }

    @Override
    public List<Game> getNonStartedGames() {
        EntityManager em = emf.createEntityManager();
        List<Game> games = em
                .createQuery("SELECT g FROM Game g WHERE g.dateGame IS NULL AND g.isFinished = false", Game.class)
                .getResultList();
        em.close();
        return games;
    }

    @Override
    public List<Game> getGamesWithWinners() {
        EntityManager em = emf.createEntityManager();
        List<Game> games = em.createQuery("SELECT g FROM Game g WHERE g.isFinished = true", Game.class).getResultList();
        em.close();
        return games;
    }

    @Override
    public Gamer loginGamer(String username) {
        EntityManager em = emf.createEntityManager();
        Gamer gamer = em.find(Gamer.class, username);
        em.close();
        return gamer;
    }

    @Override
    public void registerGamer(Gamer gamer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(gamer);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Move> getMoves(String gameId) {
        EntityManager em = emf.createEntityManager();
        List<Move> moves = em
                .createQuery("SELECT m FROM Move m WHERE m.gameGamer.game.id = :gameId ORDER BY m.id ASC", Move.class)
                .setParameter("gameId", Long.parseLong(gameId))
                .getResultList();
        em.close();
        return moves;
    }

    @Override
    public void makeMove(String gameId, String username, String moveSequence) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        GameGamer gameGamer;
        try {
            gameGamer = em
                    .createQuery(
                            "SELECT gg FROM GameGamer gg WHERE gg.game.id = :gameId AND gg.gamer.username = :username",
                            GameGamer.class)
                    .setParameter("gameId", Long.parseLong(gameId))
                    .setParameter("username", username)
                    .getSingleResult();
            System.out.println("Found GameGamer: " + gameGamer.getId());
        } catch (NoResultException e) {
            em.getTransaction().commit();
            em.close();
            System.out.println("No GameGamer found for gameId: " + gameId + " and username: " + username);
            return;
        }

        if (gameGamer == null || gameGamer.getGame().isFinished()) {
            em.getTransaction().commit();
            em.close();
            return;
        }

        Move move = new Move();
        move.setGameGamer(gameGamer); // Убедитесь, что gameGamer установлен
        move.setSequence(moveSequence);
        move.setBulls(calculateBulls(moveSequence, gameGamer.getGame().getSequence()));
        move.setCows(calculateCows(moveSequence, gameGamer.getGame().getSequence()));

        System.out.println("Persisting move for gameGamerId: " + gameGamer.getId());
        System.out.println("Move details: sequence=" + move.getSequence() + ", bulls=" + move.getBulls() + ", cows="
                + move.getCows());
        System.out.println("Move gameGamer: " + move.getGameGamer().getId());

        try {
            em.persist(move);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error persisting move: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }

        if (move.getBulls() == 4) {
            Game game = gameGamer.getGame();
            game.setFinished(true);
            gameGamer.setIsWinner(true);
            em.getTransaction().begin();
            try {
                em.merge(game);
                em.merge(gameGamer);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error updating game and gameGamer: " + e.getMessage());
                throw e;
            } finally {
                em.close();
            }
        }
    }

    @Override
    public String getWinner(Long gameId) {
        EntityManager em = emf.createEntityManager();
        Move lastMove = em
                .createQuery("SELECT m FROM Move m WHERE m.gameGamer.game.id = :gameId ORDER BY m.id DESC", Move.class)
                .setParameter("gameId", gameId)
                .setMaxResults(1)
                .getSingleResult();
        em.close();
        return lastMove.getGameGamer().getGamer().getUsername();
    }

    private String generateRandomSequence() {
        Random random = new Random();
        StringBuilder sequence = new StringBuilder();
        while (sequence.length() < 4) {
            int digit = random.nextInt(10);
            if (sequence.indexOf(String.valueOf(digit)) == -1) {
                sequence.append(digit);
            }
        }
        return sequence.toString();
    }

    private int calculateBulls(String guess, String secret) {
        int bulls = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    private int calculateCows(String guess, String secret) {
        int cows = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) != secret.charAt(i) && secret.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }
        return cows;
    }
}
