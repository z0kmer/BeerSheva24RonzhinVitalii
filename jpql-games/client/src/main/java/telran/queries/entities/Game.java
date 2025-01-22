package telran.queries.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="game")
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_game")
    private LocalDate dateGame;

    @Column(name = "is_finished")
    private boolean isFinished;

    private String sequence;
    private String creator;
    private String name;

    public Game() {
    }

    public Game(String sequence, String creator, String name) {
        this.sequence = sequence;
        this.creator = creator;
        this.name = name != null && !name.isEmpty() ? name : "Game" + id;
        this.dateGame = null;
        this.isFinished = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateGame() {
        return dateGame;
    }

    public void setDateGame(LocalDate dateGame) {
        this.dateGame = dateGame;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", dateGame=" + dateGame +
                ", isFinished=" + isFinished +
                ", sequence='" + sequence + '\'' +
                ", creator='" + creator + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
