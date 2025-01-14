package telran.queries.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Game {
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

    // Конструкторы, геттеры и сеттеры

    public Game() {
    }

    public Game(String sequence, String creator, String name) {
        this.sequence = sequence;
        this.creator = creator;
        this.name = name != null && !name.isEmpty() ? name : "Game" + id;
        this.dateGame = null; // Пока не started, поле равно null
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
