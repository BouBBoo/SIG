package com.example.demo.modele;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Voisin implements Serializable {
    @Id
    private Long id;
    private Long idvoisind;
    private Long idvoising;
    private Long idvoisinf;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdvoisind() {
        return idvoisind;
    }

    public void setIdvoisind(long idvoisind) {
        this.idvoisind = idvoisind;
    }

    public long getIdvoising() {
        return idvoising;
    }

    public void setIdvoising(long idvoising) {
        this.idvoising = idvoising;
    }

    public long getIdvoisinf() {
        return idvoisinf;
    }

    public void setIdvoisinf(long idvoisinf) {
        this.idvoisinf = idvoisinf;
    }

    public Voisin() {
    }

    @Override
    public boolean equals(Object obj) {
        Voisin voisin = (Voisin)obj;
        return voisin.getId() == id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
