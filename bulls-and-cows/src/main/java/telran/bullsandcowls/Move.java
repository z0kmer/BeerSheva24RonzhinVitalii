package telran.bullsandcowls;

public class Move {
    private int id;
    private int gameGamerId;
    private int bulls;
    private int cows;
    private String sequence;

    public Move(int gameGamerId, String sequence, String targetSequence) {
        this.gameGamerId = gameGamerId;
        this.sequence = sequence;
        int[] result = calculateBullsAndCows(sequence, targetSequence);
        this.bulls = result[0];
        this.cows = result[1];
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGameGamerId(int gameGamerId) {
        this.gameGamerId = gameGamerId;
    }

    private int[] calculateBullsAndCows(String sequence, String targetSequence) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == targetSequence.charAt(i)) {
                bulls++;
            } else if (targetSequence.indexOf(sequence.charAt(i)) != -1) {
                cows++;
            }
        }
        return new int[]{bulls, cows};
    }

    public int getId() {
        return id;
    }

    public int getGameGamerId() {
        return gameGamerId;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

    public String getSequence() {
        return sequence;
    }

    public int[] getBullsAndCows() {
        return new int[]{bulls, cows};
    }
}
