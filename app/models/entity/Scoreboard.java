package models.entity;

import java.util.ArrayList;

/**
 * Created by Thomas on 24/02/2015.
 */
public class Scoreboard {

    private Player player;
    private Game game;
    private int id;
    private Turn currentTurn;
    private ArrayList<Turn> turns;
    private int turnRemaining = 10;

    public Scoreboard(Player player, Game game, int id) {
        this.player = player;
        this.game = game;
        this.id = id;
        this.turns = new ArrayList<>();

    }

    public int getTurnRemaining() {
        return turnRemaining;
    }

    public Scoreboard decreaseTurnRemaining() {
        this.turnRemaining--;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public int getId() {
        return id;
    }

    public Turn getCurrentTurn() {
        if(currentTurn==null){
            try {
                nextTurn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return currentTurn;
    }

    public ArrayList<Turn> getTurns() {
        return turns;
    }

    private Scoreboard setCurrentTurn(Turn turn) {
        turns.add(turn);
        this.currentTurn = turn;
        return this;
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

    private Scoreboard calculScore(){
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
                t.setCumul(t.getResult());
            }else {
                t.setCumul(getTurns().get(k - 1).getCumul() + t.getResult());
            }
        }
        return this;
    }
}
