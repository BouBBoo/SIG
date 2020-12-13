package com.example.demo.modele;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Position implements Serializable {
    @Id
    private Long id;
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private Point geom;
    private Long idsalle;
    private Long idescalier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getGeom() {
        return geom;
    }

    public void setGeom(Point geom) {
        this.geom = geom;
    }

    public Long getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(Long idsalle) {
        this.idsalle = idsalle;
    }

    public Long getIdescalier() {
        return idescalier;
    }

    public void setIdescalier(Long isescalier) {
        this.idescalier = isescalier;
    }
}
