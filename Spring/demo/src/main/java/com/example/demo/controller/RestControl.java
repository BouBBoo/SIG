package com.example.demo.controller;

import com.example.demo.modele.Salle;
import com.example.demo.modele.Voisin;
import com.example.demo.service.SalleService;
import com.example.demo.service.VoisinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestControl {

    @Autowired
    SalleService salleService;

    @Autowired
    VoisinService voisinService;
    
    @RequestMapping("/rest/salles")
    public List<Salle> findAll(){
        return salleService.findAll();
    }

    @RequestMapping("/rest/salles/voisins/{id}")
    public Voisin findVoisin(@PathVariable(value = "id") long id){
        System.out.println(id);
        return voisinService.findSalleVoisin(id);
    }

    @RequestMapping("/rest/salles/{id}")
    public Salle findSalle(@PathVariable(value = "id") long id){
        System.out.println(id);
        return salleService.findSalle(id);
    }
}
