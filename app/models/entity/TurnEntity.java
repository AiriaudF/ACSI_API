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
    private ScoreboardEntity scoreboard;
    private List<ShotEntity> shots;
    private State state = State.CLASSIC;
    private int nbSkittles;
    private int shotRemaining;


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
        return scoreboard;
    }

    public void setScoreboard(ScoreboardEntity scoreboard) {
        this.scoreboard = scoreboard;
    }

    @OneToMany(mappedBy = "turn")
    public List<ShotEntity> getShots() {
        return shots;
    }

    public void setShots(List<ShotEntity> shots) {
        this.shots = shots;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getNbSkittles() {
        return nbSkittles;
    }

    public void setNbSkittles(int nbSkittles) {
        this.nbSkittles = nbSkittles;
    }

    public int getShotRemaining() {
        return shotRemaining;
    }

    public void setShotRemaining(int shotRemaining) {
        this.shotRemaining = shotRemaining;
    }


    public ShotEntity launchBall() throws Exception {
        if(getShotRemaining()>0){
            //for moment we use random generated shot
            ShotEntity s = ShotEntity.random(this);
            //        Shot s = new Shot(1,10);
            setNbSkittles(nbSkittles-s.getSkittlesFall());
            //Si j'atteint 0 quilles restantes
            if (getNbSkittles() == 0) {
                //si dernier tour je lui donne plus de tire
                if(this.getNumber()==10){
                    if(shots.size()==0){
                        setState(State.STRIKE);
                        setShotRemaining(2);
                        setNbSkittles(10);
                    }else if(shots.size()==1){
                        if(!getState().equals(State.STRIKE)){
                            setState(State.SPARE);
                        }
                        setShotRemaining(1);
                        setNbSkittles(10);
                    }else{
                        setShotRemaining(0);
                    }
                }else{
                    if(shots.size()==0){ // et que c'est la premiere boule
                        setState(State.STRIKE);
                    }else{
                        setState(State.SPARE);
                    }
                    //Je met à 0 le nombre de tire restant
                    setShotRemaining(0);
                }
            }else{ // sinon je lui enleve un tour
                setShotRemaining(getShotRemaining() - 1);
            }

            shots.add(s);
            if(getShotRemaining()==0){
                getScoreboard().nextTurn();
            }
            return s;
        }
        this.getScoreboard().nextTurn();
        throw new Exception("Aucun tir restant sur ce tour "+this.getNumber());
    }
}
