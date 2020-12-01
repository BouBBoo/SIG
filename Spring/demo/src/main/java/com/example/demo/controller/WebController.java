package com.example.demo.controller;


import com.example.demo.modele.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.SalleService;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    SalleService salleService;

    @GetMapping("/salles")
    public String findSalles(Model model){
        List<Salle> list = salleService.findAll();
        model.addAttribute("salles", list);
        model.addAttribute("etagemax",list.get(list.size() - 1).getEtage());
        return "salles";
    }
}
