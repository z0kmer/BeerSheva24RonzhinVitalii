package telran.queries.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "move")
public class Move {
    @Id
    @GeneratedValue
    long id;
    int bulls;
    int cows;
    String sequence;
    @ManyToOne
    @JoinColumn(name = "game_gamer_id")
    GameGamer gameGamer;

    public long getId() {
        return id;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public GameGamer getGameGamer() {
        return gameGamer;
    }

    public void setGameGamer(GameGamer gameGamer) {
        this.gameGamer = gameGamer;
    }

    @Override
    public String toString() {
        return "Move [id=" + id + ", bulls=" + bulls + ", cows=" + cows + ", sequence=" + sequence + ", gameGamer=" + gameGamer.getId() + "]";
    }
}
