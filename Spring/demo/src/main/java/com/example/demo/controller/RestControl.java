package com.example.demo.controller;

import com.example.demo.modele.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.SalleService;

import java.util.List;

@RestController
public class RestControl {

    @Autowired
    SalleService salleService;
    
    @RequestMapping("/rest/salles")
    public List<Salle> findAll(){
        return salleService.findAll();
    }
}
