package telran.bullsandcowls;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Main {
    static int numPlayers;
    static int maxMoves;
    static List<Gamer> gamers = new ArrayList<>();
    static List<Game> games = new ArrayList<>();
    static List<GameGamer> gameGamers = new ArrayList<>();
    static List<Move> moves = new ArrayList<>();
    static Random random = new Random();
    static AtomicInteger gameGamerCounter = new AtomicInteger(1);
    static AtomicInteger moveCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        // Чтение конфигурационного файла
        ConfigReader configReader = new ConfigReader("config.properties");
        numPlayers = Integer.parseInt(configReader.getProperty("numPlayers"));
        maxMoves = Integer.parseInt(configReader.getProperty("maxMoves"));

        // Инициализация игроков
        initializeGamers();

        // Выполнение игр в однопоточном режиме
        IntStream.rangeClosed(1, numPlayers).forEach(Main::playGame);

        // Запись данных в файлы
        writeToFile();
    }

    static void initializeGamers() {
        IntStream.rangeClosed(1, numPlayers).forEach(i -> gamers.add(new Gamer("player" + i)));
    }

    static void playGame(int gameId) {
        Game game = new Game(gameId);
        games.add(game);
        gamers.forEach(gamer -> {
            GameGamer gameGamer = new GameGamer(game.getId(), gamer.getUsername());
            gameGamer.setId(gameGamerCounter.getAndIncrement()); // Присвоение id для gameGamer
            boolean isWinner = false;
            int movesCount = 0;
            while (!isWinner && movesCount < maxMoves) {
                String guess = generateGuess(gameGamer, game.getSequence());
                Move move = new Move(gameGamer.getId(), guess, game.getSequence());
                move.setId(moveCounter.getAndIncrement()); // Присвоение id для хода
                gameGamer.addMove(move);
                moves.add(move);
                if (move.getBulls() == 4) {
                    isWinner = true;
                    gameGamer.setWinner(true);
                }
                movesCount++;
            }
            gameGamers.add(gameGamer);
        });
    }

    static String generateGuess(GameGamer gameGamer, String targetSequence) {
        List<Move> previousMoves = gameGamer.getMoves();
        if (previousMoves.isEmpty()) {
            return generateRandomGuess();
        }

        String previousGuess = previousMoves.get(previousMoves.size() - 1).getSequence();
        StringBuilder newGuess = new StringBuilder(previousGuess);
        int[] bullsAndCows = previousMoves.get(previousMoves.size() - 1).getBullsAndCows();

        for (int i = 0; i < 4; i++) {
            if (previousGuess.charAt(i) != targetSequence.charAt(i)) {
                char newChar;
                do {
                    newChar = (char) ('0' + random.nextInt(10));
                } while (newGuess.indexOf(String.valueOf(newChar)) != -1 || newChar == previousGuess.charAt(i));
                newGuess.setCharAt(i, newChar);
            }
        }

        return newGuess.toString();
    }

    static String generateRandomGuess() {
        StringBuilder guess = new StringBuilder(4);
        while (guess.length() < 4) {
            char c = (char) ('0' + random.nextInt(10));
            if (guess.indexOf(String.valueOf(c)) == -1) {
                guess.append(c);
            }
        }
        return guess.toString();
    }

    static void writeToFile() {
        try (FileWriter gamerWriter = new FileWriter("gamers.csv");
             FileWriter gameWriter = new FileWriter("games.csv");
             FileWriter gameGamerWriter = new FileWriter("gameGamers.csv");
             FileWriter moveWriter = new FileWriter("moves.csv")) {

            // Запись игроков
            gamerWriter.write("username,birthdate\n");
            gamers.stream()
                .forEach(gamer -> {
                    try {
                        gamerWriter.write(gamer.getUsername() + "," + gamer.getBirthdate() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            // Запись игр
            gameWriter.write("id,date_time,is_finished,sequence\n");
            games.stream()
                .forEach(game -> {
                    try {
                        gameWriter.write(game.getId() + "," + game.getDateTime() + "," + game.isFinished() + "," + game.getSequence() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            // Запись результатов игр (сортировка по id)
            gameGamerWriter.write("id,game_id,gamer_id,is_winner\n");
            gameGamers.stream()
                .sorted(Comparator.comparingInt(GameGamer::getId))
                .forEach(gameGamer -> {
                    try {
                        gameGamerWriter.write(gameGamer.getId() + "," + gameGamer.getGameId() + "," + gameGamer.getGamerId() + "," + gameGamer.isWinner() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            // Запись ходов (сортировка по game_gamer_id и id хода)
            moveWriter.write("id,game_gamer_id,bulls,cows,sequence\n");
            moves.stream()
                .sorted(Comparator.comparingInt(Move::getGameGamerId).thenComparingInt(Move::getId))
                .forEach(move -> {
                    try {
                        moveWriter.write(move.getId() + "," + move.getGameGamerId() + "," + move.getBulls() + "," + move.getCows() + "," + move.getSequence() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
