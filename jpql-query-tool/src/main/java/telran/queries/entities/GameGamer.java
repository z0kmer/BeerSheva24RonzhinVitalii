package telran.queries.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_gamer")
public class GameGamer {
    @Id
    @GeneratedValue
    long id;
    @ManyToOne
    @JoinColumn(name = "game_id")
    Game game;
    @ManyToOne
    @JoinColumn(name = "gamer_id")
    Gamer gamer;
    @Column(name = "is_winner")
    boolean isWinner;

    public long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

    @Override
    public String toString() {
        return "GameGamer [id=" + id + ", game=" + game.getId() + ", gamer=" + gamer.getUsername() + ", isWinner=" + isWinner + "]";
    }
}
