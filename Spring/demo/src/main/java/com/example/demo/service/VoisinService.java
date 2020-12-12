package com.example.demo.service;

import com.example.demo.modele.*;
import com.example.demo.repository.EscalierSalleRepository;
import com.example.demo.repository.VoisinEscalierRepository;
import com.example.demo.repository.VoisinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoisinService {
    @Autowired
    VoisinRepository voisinRepository;

    @Autowired
    VoisinEscalierRepository voisinEscalierRepository;

    @Autowired
    EscalierSalleRepository escalierSalleRepository;

    public List<Voisin> findAll(){
        return (List<Voisin>)this.voisinRepository.findAll();
    }

    public Voisin findSalleVoisin(long id){
        Voisin voisin = this.voisinRepository.findById(id).get();
        System.out.println(voisin);
        return voisin;
    }

    public VoisinEscalier findEscalierVoisin(long id){
        VoisinEscalier voisinEscalier = this.voisinEscalierRepository.findById(id).get();
        return voisinEscalier;
    }

    public EscalierSalle findEscalierSalle(long id){
        return this.escalierSalleRepository.findById(id).get();
    }
}
