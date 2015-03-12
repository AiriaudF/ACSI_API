package models.entity;

/**
 * Created by Thomas on 24/02/2015.
 */
public class Shot {
    private int id;
    private int skittlesFall;

    public Shot(int id, int skittlesFall) {
        this.id = id;
        this.skittlesFall = skittlesFall;
    }

    public int getId() {
        return id;
    }

    public int getSkittlesFall() {
        return skittlesFall;
    }

    public static Shot random(Turn turn){
        int skittlesFall =(int) Math.round(Math.random()*turn.getNbSkittles());
        return new Shot(1,skittlesFall);
    }
}
