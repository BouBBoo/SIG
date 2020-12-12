package com.example.demo.controller;

import com.example.demo.modele.*;
import com.example.demo.service.EscalierService;
import com.example.demo.service.PositionService;
import com.example.demo.service.SalleService;
import com.example.demo.service.VoisinService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestControl {

    @Autowired
    SalleService salleService;

    @Autowired
    VoisinService voisinService;

    @Autowired
    EscalierService escalierService;

    @Autowired
    PositionService positionService;
    
    @RequestMapping("/rest/salles")
    public List<Salle> findAllSalles(){
        return salleService.findAll();
    }

    @RequestMapping("/rest/escalier")
    public List<Escalier> findAllEscalier(){
        return escalierService.findAll();
    }

    @RequestMapping("/rest/salles/voisins/{id}")
    public Voisin findVoisin(@PathVariable(value = "id") long id){
        return voisinService.findSalleVoisin(id);
    }

    @RequestMapping("/rest/salles/{id}")
    public Salle findSalle(@PathVariable(value = "id") long id){
        return salleService.findSalle(id);
    }

    @RequestMapping("/rest/salles/escalier/{id}")
    public VoisinEscalier findVoisinEscalier(@PathVariable(value = "id") long id){
        return voisinService.findEscalierVoisin(id);
    }
    @RequestMapping("/rest/Escalier/{id}")
    public Escalier findEscalier(@PathVariable(value = "id") long id){
        return escalierService.findEscalier(id);
    }

    @RequestMapping("/rest/Escalier/joint/{id}")
    public EscalierSalle findEscalierSalle(@PathVariable(value = "id") long id){
        return voisinService.findEscalierSalle(id);
    }

    @RequestMapping("/rest/position")
    public Position findPosition(){
        return positionService.findPosition();
    }

    @RequestMapping("/rest/position/{id}/{direction}/{typesallesrc}/{typesalledest}")
    public void updateLocalisation(@PathVariable(value = "id") long id, @PathVariable(value = "direction") String direction
            , @PathVariable(value = "typesallesrc") String typesallesrc, @PathVariable(value = "typesalledest") String typesalledest){
        Position position = positionService.findPosition();
        Coordinate coordinate = new Coordinate();
        coordinate.x = position.getGeom().getCoordinate().x;
        coordinate.y = position.getGeom().getCoordinate().y;
        System.out.println("Yes");
        if(typesalledest.equals(typesallesrc) && typesallesrc.equals("salle")){
            switch (direction){
                case "left":
                    if(coordinate.x == 0.5){
                        coordinate.y += 1;
                    }else{
                        coordinate.y -= 1;
                    }
                    break;
                case "right":
                    if(coordinate.x == 0.5){
                        coordinate.y -= 1;
                    }else{
                        coordinate.y += 1;
                    }
                    break;
                case "face":
                    if(coordinate.x == 0.5){
                        coordinate.x += 2;
                    }else{
                        coordinate.x -= 2;
                    }
                    break;
            }
            Point point = GeometryFactory.createPointFromInternalCoord(coordinate, position.getGeom());
            position.setGeom(point);
            positionService.updatePosition(position);
        }else if(!typesalledest.equals(typesallesrc)){
            switch (direction){
                case "left":
                    if(coordinate.x == 0.5){
                        coordinate.y += 0.75;
                    }else{
                        coordinate.y -= 0.75;
                    }
                    break;
                case "right":
                    if(coordinate.x == 0.5){
                        coordinate.y -= 0.75;
                    }else{
                        coordinate.y += 0.75;
                    }

                    break;
            }
            Point point = GeometryFactory.createPointFromInternalCoord(coordinate, position.getGeom());
            position.setGeom(point);
            positionService.updatePosition(position);
        }
    }
}
