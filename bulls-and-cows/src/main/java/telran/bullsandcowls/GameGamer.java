package telran.bullsandcowls;

import java.util.ArrayList;
import java.util.List;

public class GameGamer {
    private int id;
    private int gameId;
    private String gamerId;
    private boolean isWinner;
    private List<Move> moves;

    public GameGamer(int gameId, String gamerId) {
        this.gameId = gameId;
        this.gamerId = gamerId;
        this.isWinner = false;
        this.moves = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGamerId() {
        return gamerId;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void addMove(Move move) {
        moves.add(move);
    }
}
