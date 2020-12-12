package com.example.demo.controller;

import com.example.demo.modele.*;
import com.example.demo.service.EscalierService;
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

    @Autowired
    EscalierService escalierService;
    
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
        System.out.println(id);
        return voisinService.findSalleVoisin(id);
    }

    @RequestMapping("/rest/salles/{id}")
    public Salle findSalle(@PathVariable(value = "id") long id){
        System.out.println(id);
        return salleService.findSalle(id);
    }

    @RequestMapping("/rest/salles/escalier/{id}")
    public VoisinEscalier findVoisinEscalier(@PathVariable(value = "id") long id){
        return voisinService.findEscalierVoisin(id);
    }
    @RequestMapping("/rest/Escalier/{id}")
    public Escalier findEscalier(@PathVariable(value = "id") long id){
        System.out.println(id);
        return escalierService.findEscalier(id);
    }

    @RequestMapping("/rest/Escalier/joint/{id}")
    public EscalierSalle findEscalierSalle(@PathVariable(value = "id") long id){
        return voisinService.findEscalierSalle(id);
    }
}
