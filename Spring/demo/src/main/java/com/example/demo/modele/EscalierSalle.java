package com.example.demo.modele;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class EscalierSalle {
    @Id
    private Long id;
    private Long idvoisind;
    private Long idvoising;
    private Long idvoisinf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdvoisind() {
        return idvoisind;
    }

    public void setIdvoisind(Long idvoisind) {
        this.idvoisind = idvoisind;
    }

    public Long getIdvoising() {
        return idvoising;
    }

    public void setIdvoising(Long idvoising) {
        this.idvoising = idvoising;
    }

    public Long getIdvoisinf() {
        return idvoisinf;
    }

    public void setIdvoisinf(Long idvoisinf) {
        this.idvoisinf = idvoisinf;
    }

    @Override
    public boolean equals(Object obj) {
        EscalierSalle escalier = (EscalierSalle) obj;
        return escalier.getId() == id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
