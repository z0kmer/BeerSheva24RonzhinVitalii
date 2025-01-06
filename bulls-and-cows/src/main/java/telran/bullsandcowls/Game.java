package telran.bullsandcowls;

import java.time.LocalDateTime;
import java.util.Random;

public class Game {
    private int id;
    private LocalDateTime dateTime;
    private boolean isFinished;
    private String sequence;
    private static Random random = new Random();

    public Game(int id) {
        this.id = id;
        this.dateTime = LocalDateTime.now();
        this.isFinished = false;
        this.sequence = generateRandomSequence();
    }

    private String generateRandomSequence() {
        StringBuilder sequence = new StringBuilder(4);
        while (sequence.length() < 4) {
            char c = (char) ('0' + random.nextInt(10));
            if (sequence.indexOf(String.valueOf(c)) == -1) {
                sequence.append(c);
            }
        }
        return sequence.toString();
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getSequence() {
        return sequence;
    }
}
