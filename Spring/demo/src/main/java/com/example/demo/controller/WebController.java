package com.example.demo.controller;


import com.example.demo.modele.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.SalleService;

import java.util.Comparator;
import java.util.List;

@Controller
public class WebController {
    @Autowired
    SalleService salleService;


    @GetMapping("/salles")
    public String findSalles(Model model){
        model = setModel(model);
        return "salles";
    }

    private Model setModel(Model model){
        List<Salle> list = salleService.findAll();
        list.sort(Comparator.comparing(Salle::getId));
        model.addAttribute("salles", list);
        model.addAttribute("etagemax",list.get(list.size() - 1).getEtage());
        return model;
    }

    @PostMapping("/update")
    public String update(@RequestParam(value = "nom") String fonction, @RequestParam(value = "id") long id, Model model){
        Salle salle = salleService.getSalle(id);

        salle.setNom(fonction);
        salleService.saveSalle(salle);
        return "redirect:/salles";
    }
}
