package models.entity;

import javax.persistence.*;

/**
 * Created by Thomas on 12/03/2015.
 */
@Entity
@Table(name = "shot", schema = "", catalog = "acsi")
public class ShotEntity {
    private int id;
    private int skittlesFall;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "skittlesFall")
    public int getSkittlesFall() {
        return skittlesFall;
    }

    public void setSkittlesFall(int skittlesFall) {
        this.skittlesFall = skittlesFall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShotEntity that = (ShotEntity) o;

        if (id != that.id) return false;
        if (skittlesFall != that.skittlesFall) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + skittlesFall;
        return result;
    }
}
