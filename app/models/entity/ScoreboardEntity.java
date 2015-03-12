package models.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Thomas on 12/03/2015.
 */
@Entity
@Table(name = "scoreboard", schema = "", catalog = "acsi")
public class ScoreboardEntity {
    private int id;
    private PlayerEntity player;
    private GameEntity game;
    private Turn currentTurn;
    private List<Turn> turns;
    private int turnRemaining = 10;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreboardEntity that = (ScoreboardEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "idPlayer", referencedColumnName = "id", nullable = false)
    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    @OneToOne
    @JoinColumn(name = "idGame", referencedColumnName = "id", nullable = false)
    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }


    public Turn nextTurn() throws Exception {
        if(getTurnRemaining()>0){
            Turn nextTurn = new Turn(1,getTurns().size()+1,this);
            this.calculScore().decreaseTurnRemaining().setCurrentTurn(nextTurn);
            return nextTurn;
        }
        this.decreaseTurnRemaining().calculScore();
        throw new Exception("Fin du jeu");
    }

    public int getTurnRemaining() {
        return turnRemaining;
    }

    public ScoreboardEntity decreaseTurnRemaining() {
        this.turnRemaining --;
        return this;
    }

    @ManyToOne
    public List<Turn> getTurns() {
        return turns;
    }

    private ScoreboardEntity calculScore(){
        /**
         * je lis les tours dans le sens inverse afin de ne pas avoir à vérifier l'état
         * des tours précédents et économiser des ressources
         */
        for(int i = getTurns().size()-1; i >= 0;i--){
            Turn t = getTurns().get(i);
            t.setResult(0);
            for(int j = 0; j < t.getShots().size();j++){
                Shot s = t.getShots().get(j);
                //Can't do switch because i need to break the loop... damn !
                if(t.getState().equals(State.CLASSIC)){ // Case classic, we add skittles fall to the result
                    t.setResult(t.getResult()+s.getSkittlesFall());
                }else if(t.getState().equals(State.SPARE)){ // Case Spare, we have to check, if we have the previous turn to calculate
                    if(t.getNumber()<10){
                        if(i<=getTurns().size()-2){
                            Turn previousTurn = getTurns().get(i+1);
                            t.setResult(10+previousTurn.getShots().get(0).getSkittlesFall());
                        }else{
                            t.setResult(10);
                        }
                    }else{
                        t.setResult(10+t.getShots().get(2).getSkittlesFall());
                    }
                    break; //We don't need to check the next shot for SPARE
                }else{ // Case Strike is harder to calculate
                    if(t.getNumber()<10){
                        if(i==getTurns().size()-1) { // if we don't have previous shot, we set to 10
                            t.setResult(10);
                        }else{ //else we check the previous shot
                            Turn secondTurn = getTurns().get(i+1);
                            if(!secondTurn.getState().equals(State.STRIKE)){ //if previous shot is not a strike to,i take result
                                t.setResult(10 + secondTurn.getShots().get(0).getSkittlesFall()+secondTurn.getShots().get(1).getSkittlesFall());
                            }else if(i==getTurns().size()-2 && getTurns().size()<10){ //the previous shot is strike i need to get the third shot but i don't have
                                t.setResult(20);
                            }else{
                                if(t.getNumber()==9){
                                    t.setResult(20+secondTurn.getShots().get(1).getSkittlesFall());
                                }else{
                                    Turn thirdTurn = getTurns().get(i+2);
                                    t.setResult(20+thirdTurn.getShots().get(0).getSkittlesFall());
                                }
                            }
                        }
                    }else{ //because strike for 10th turn is not with other turn
                        System.out.println("Strike for t10");
                        if(t.getShots().size()>2){
                            t.setResult(10+t.getShots().get(1).getSkittlesFall()+t.getShots().get(2).getSkittlesFall());
                        }else if(t.getShots().size()>1){
                            t.setResult(10+t.getShots().get(1).getSkittlesFall());
                        }else{
                            t.setResult(10);
                        }
                    }
                    break; //We don't need to check the next shot for STRIKE
                }
            }
        }
        for(int k = 0;k<getTurns().size();k++){
            Turn t = getTurns().get(k);
            if(t.getNumber()==1){
                t.setTotalScore(t.getResult());
            }else {
                t.setTotalScore(getTurns().get(k - 1).getTotalScore() + t.getResult());
            }
        }
        return this;
    }
}
