package models.entity;

import java.util.ArrayList;

/**
 * Created by Thomas on 24/02/2015.
 */
public class Player {
    private int id;
    private String pseudo;
    private ArrayList<Scoreboard> scoreboards;
    private Game currentGame;

    public Player(int id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
        this.scoreboards = new ArrayList<>();
        currentGame = null;
    }

    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public ArrayList<Scoreboard> getScoreboards() {
        return scoreboards;
    }

    public Player newScore(Game game){
        scoreboards.add(new Scoreboard(this,game,1));
        return this;
    }

    public Player shot(){
        try {
            this.getCurrentScoreboard().getCurrentTurn().launchBall();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public Player setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
        return this;
    }

    public Scoreboard getCurrentScoreboard() throws Exception {
        for (Scoreboard s : scoreboards){
            if(s.getPlayer()==this && s.getGame()==this.getCurrentGame()){
               return s;
            }
        }
        throw new Exception("Aucun scoreboard trouvé pour le joueur "+this.getPseudo()+" et pour la game "+this.getCurrentGame().toString());
    }

    public boolean canShot() throws Exception {
        return getCurrentScoreboard().getCurrentTurn().getShotRemaining()>0;
    }

}
