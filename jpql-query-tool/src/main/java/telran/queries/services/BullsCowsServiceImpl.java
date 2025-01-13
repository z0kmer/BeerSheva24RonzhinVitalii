package telran.queries.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import telran.queries.entities.Game;
import telran.queries.entities.GameGamer;
import telran.queries.entities.Gamer;
import telran.queries.entities.Move;
import telran.queries.repositories.BullsCowsRepository;

public class BullsCowsServiceImpl implements BullsCowsService {

    private final BullsCowsRepository repository;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

    public BullsCowsServiceImpl(BullsCowsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Gamer registerGamer(Gamer gamer) {
        if (!USERNAME_PATTERN.matcher(gamer.getUsername()).matches()) {
            throw new IllegalArgumentException("Имя пользователя может содержать только латинские буквы, цифры и подчеркивание.");
        }
        if (repository.findGamerByUsername(gamer.getUsername()) != null) {
            throw new IllegalArgumentException("Имя уже существует. Пожалуйста, выберите другое имя.");
        }
        gamer.setBirthdate(LocalDate.parse(gamer.getBirthdate().toString()));
        return repository.saveGamer(gamer);
    }

    @Override
    public Gamer loginGamer(String username) {
        return repository.findGamerByUsername(username);
    }

    @Override
    public Game createGame(String gameName, String creatorUsername) {
        Gamer creator = repository.findGamerByUsername(creatorUsername);
        Game game = new Game();
        game.setDateTime(LocalDateTime.now());
        game.setFinished(false);
        game.setSequence(generateRandomSequence());
        return repository.saveGame(game);
    }

    @Override
    public void joinGame(String gameId, String gamerUsername) {
        Gamer gamer = repository.findGamerByUsername(gamerUsername);
        Game game = repository.findGameById(gameId);
        GameGamer gameGamer = new GameGamer();
        gameGamer.setGame(game);
        gameGamer.setGamer(gamer);
        repository.saveGameGamer(gameGamer);
    }

    @Override
    public void startGame(String gameId, String starterUsername) {
        Game game = repository.findGameById(gameId);
        game.setFinished(false);
        repository.saveGame(game);
    }

    @Override
    public Move makeMove(String gameId, String gamerUsername, String moveSequence) {
        Game game = repository.findGameById(gameId);
        Gamer gamer = repository.findGamerByUsername(gamerUsername);
        Move move = new Move();
        move.setGameGamer(findGameGamer(game, gamer));
        move.setSequence(moveSequence);
        move.setBulls(calculateBulls(moveSequence, game.getSequence()));
        move.setCows(calculateCows(moveSequence, game.getSequence()));
        return repository.saveMove(move);
    }

    @Override
    public List<Game> getAvailableGames() {
        return repository.findAllUnstartedGames();
    }

    @Override
    public List<Game> getGamerGames(String gamerUsername) {
        return repository.findAllGamesByGamer(gamerUsername).stream()
                .map(GameGamer::getGame)
                .collect(Collectors.toList());
    }

    @Override
    public List<Game> getStartedGames(String gamerUsername) {
        return repository.findAllStartedGamesByGamer(gamerUsername);
    }

    private GameGamer findGameGamer(Game game, Gamer gamer) {
        return repository.findAllGamesByGamer(gamer.getUsername()).stream()
                .filter(gg -> gg.getGame().equals(game))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Игрок не найден в этой игре."));
    }

    private String generateRandomSequence() {
        return "1234";
    }

    private int calculateBulls(String move, String sequence) {
        return (int) move.chars().filter(ch -> sequence.indexOf(ch) != -1 && move.indexOf(ch) == sequence.indexOf(ch)).count();
    }

    private int calculateCows(String move, String sequence) {
        return (int) move.chars().filter(ch -> sequence.indexOf(ch) != -1 && move.indexOf(ch) != sequence.indexOf(ch)).count();
    }
}
