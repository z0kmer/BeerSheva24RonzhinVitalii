package telran.queries.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue
    long id;
    @Column(name = "date_time")
    LocalDateTime dateTime;
    @Column(name = "is_finished")
    boolean isFinished;
    String sequence;

    public long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    @Override
    public String toString() {
        return "Game [id=" + id + ", dateTime=" + dateTime + ", isFinished=" + isFinished + ", sequence=" + sequence + "]";
    }
}
