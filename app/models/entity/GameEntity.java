package models.entity;

import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Thomas on 12/03/2015.
 */
@Entity
@Table(name = "game", schema = "", catalog = "acsi")
public class GameEntity {
    @GeneratedValue
    private int id;
    private Timestamp date;
    private List<PlayerEntity> players;
    private int currentTurnNumber = 0;


    public static Model.Finder<Integer, GameEntity> find = new Model.Finder<Integer, GameEntity>(
            Integer.class, GameEntity.class
    );

    @ManyToMany
    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public int getCurrentTurnNumber() {
        return currentTurnNumber;
    }

    public GameEntity increaseCurrentTurn(){
        currentTurnNumber++;
        return this;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }


    @Basic
    @Column(name = "Date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameEntity that = (GameEntity) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    public PlayerEntity getNextPlayer(){
        for(PlayerEntity p : getPlayers()){
            try {
                if(p.getCurrentScoreboard().getCurrentTurn().getNumber()== getCurrentTurnNumber() && p.getCurrentScoreboard().getCurrentTurn().getShotRemaining()>0){
                    return p;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        increaseCurrentTurn();
        return getPlayers().get(0);
    }

    public boolean hasNextPlayer(){
        boolean res = true;
        if(getCurrentTurnNumber()>10){
            res = false;
        }
        return res;
    }

    public int getBestScore() throws Exception {
        int bestScore = 0;
        for (PlayerEntity p : players){
            if(p.getCurrentScoreboard().getTurns().get(9).getCumul()>bestScore){
                bestScore=p.getCurrentScoreboard().getTurns().get(9).getCumul();
            }
        }
        return bestScore;
    }
}
