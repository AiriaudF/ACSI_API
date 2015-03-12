package models.entity;

import java.util.ArrayList;

/**
 * Created by Thomas on 24/02/2015.
 */
public class Turn {
    private int id;
    /**
     * Number correspond au chiffre du tour si c'est le tour 1 ou 2 ou 3...
     */
    private int number;
    private Scoreboard scoreboard;
    private ArrayList<Shot> shots;
    private int nbSkittles;
    private int shotRemaining;
    private int result = 0;
    private State state=State.CLASSIC;
    private int totalScore = 0;

    public Turn(int id, int number, Scoreboard scoreboard) {
        this.id = id;
        this.scoreboard = scoreboard;
        this.number = number;
        this.shots = new ArrayList<>();
        this.nbSkittles = 10;
        this.shotRemaining = 2;
    }

    public Turn(int id, int number, Scoreboard scoreboard, int shotRemaining) {
        this.id = id;
        this.scoreboard = scoreboard;
        this.number = number;
        this.shots = new ArrayList<>();
        this.nbSkittles = 10;
        this.shotRemaining = shotRemaining;
    }

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public ArrayList<Shot> getShots() {
        return shots;
    }

    public int getNbSkittles() {
        return nbSkittles;
    }

    public Turn setNbSkittles(int nbSkittles) {
        this.nbSkittles = nbSkittles;
        return this;
    }

    public int getResult() {
        return result;
    }

    public Turn setResult(int result) {
        this.result = result;
        return this;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public Shot launchBall() throws Exception {
        if(getShotRemaining()>0){
            //for moment we use random generated shot
            Shot s = Shot.random(this);
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

    public int getShotRemaining() {
        return shotRemaining;
    }

    private Turn setShotRemaining(int shotRemaining) {
        this.shotRemaining = shotRemaining;
        return this;
    }

    public State getState() {
        return state;
    }

    public Turn setState(State state) {
        this.state = state;
        return this;
    }
}
