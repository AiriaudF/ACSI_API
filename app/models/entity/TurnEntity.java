package models.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Thomas on 12/03/2015.
 */
@Entity
@Table(name = "turn", schema = "", catalog = "acsi")
public class TurnEntity {
    private int id;
    private int number;
    private int result;
    private int cumul;
    private ScoreboardEntity Scoreboard;
    private List<ShotEntity> Shots;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "result")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Basic
    @Column(name = "cumul")
    public int getCumul() {
        return cumul;
    }

    public void setCumul(int cumul) {
        this.cumul = cumul;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TurnEntity that = (TurnEntity) o;

        if (cumul != that.cumul) return false;
        if (id != that.id) return false;
        if (number != that.number) return false;
        if (result != that.result) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = id;
        result1 = 31 * result1 + number;
        result1 = 31 * result1 + result;
        result1 = 31 * result1 + cumul;
        return result1;
    }

    @ManyToOne
    @JoinColumn(name = "idScoreboard", referencedColumnName = "id", nullable = false)
    public ScoreboardEntity getScoreboard() {
        return Scoreboard;
    }

    public void setScoreboard(ScoreboardEntity scoreboard) {
        Scoreboard = scoreboard;
    }

    @OneToMany(mappedBy = "Turn")
    public List<ShotEntity> getShots() {
        return Shots;
    }

    public void setShots(List<ShotEntity> shots) {
        Shots = shots;
    }
}
