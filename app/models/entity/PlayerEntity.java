package models.entity;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Thomas on 12/03/2015.
 */
@Entity
@Table(name = "player", schema = "", catalog = "acsi")
public class PlayerEntity extends Model{
    private int id;
    @Required
    private String pseudo;
    private String nom;
    private String prenom;
    private byte vip;


    @OneToMany(fetch=FetchType.LAZY ,mappedBy = "player")
    public List<ScoreboardEntity> scoreboards;
    @Transient
    private GameEntity currentGame;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public static Finder<Integer, PlayerEntity> find = new Finder<Integer, PlayerEntity>(
            Integer.class, PlayerEntity.class
    );

    @Basic
    @Column(name = "pseudo")
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "vip")
    public byte getVip() {
        return vip;
    }

    public void setVip(byte vip) {
        this.vip = vip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerEntity that = (PlayerEntity) o;

        if (id != that.id) return false;
        if (vip != that.vip) return false;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
        if (prenom != null ? !prenom.equals(that.prenom) : that.prenom != null) return false;
        if (pseudo != null ? !pseudo.equals(that.pseudo) : that.pseudo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pseudo != null ? pseudo.hashCode() : 0);
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        result = 31 * result + (int) vip;
        return result;
    }

//
//    public List<ScoreboardEntity> getScoreboards() {
//        return scoreboards;
//    }
//
//    public void setScoreboards(List<ScoreboardEntity> scoreboards) {
//        this.scoreboards = scoreboards;
//    }

    @Transient
    public GameEntity getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(GameEntity currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * Méthode pour la logique
     */
    public PlayerEntity newScore(GameEntity game){
        //TODO création d'une nouvelle scoreboard
        //scoreboards.add(new Scoreboard(this,game,1));
        return this;
    }


    public PlayerEntity shot(){
        try {
            this.getCurrentScoreboard().getCurrentTurn().launchBall();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Transient
    public ScoreboardEntity getCurrentScoreboard(){
        for (ScoreboardEntity s : scoreboards){
            if(s.player==this && s.game==this.getCurrentGame()){
                return s;
            }
        }
        return null;
    }

}
