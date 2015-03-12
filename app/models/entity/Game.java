package models.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thomas on 24/02/2015.
 */
public class Game {
    private int id;
    private Date date;
    private ArrayList<Player> players;
    private int currentTurnNumber = 0;

    public Game(int id) {
        this.id = id;
        this.date = new Date();
        this.players = new ArrayList<>();
    }

    public Game(int id, ArrayList<Player> players) {
        this.id = id;
        this.date = new Date();
        this.players = players;
        for (Player p : players){
            p.setCurrentGame(this);
            p.newScore(this);
        }
    }

    public int getId() {
        return id;
    }

    public int getCurrentTurnNumber() {
        return currentTurnNumber;
    }

    public Game increaseCurrentTurn() {
        this.currentTurnNumber++;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Game addPlayer(Player p){
        players.add(p);
        p.newScore(this);
        p.setCurrentGame(this);
        return this;
    }

    public Player getNextPlayer(){
        for(Player p : getPlayers()){
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

    public int getWinner() throws Exception {
        int bestScore = 0;
        for (Player p : players){
            if(p.getCurrentScoreboard().getTurns().get(9).getTotalScore()>bestScore){
                bestScore=p.getCurrentScoreboard().getTurns().get(9).getTotalScore();
            }
        }
        return bestScore;
    }

}
