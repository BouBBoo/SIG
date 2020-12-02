package com.example.demo.service;

import com.example.demo.modele.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.SalleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SalleService {
    @Autowired
    SalleRepository salleRepository;

    public List<Salle> findAll(){
        List<Salle> list = (List<Salle>) salleRepository.findAll();

        return list;
    }

    public void saveAll(List<Salle> salles){
        salleRepository.saveAll(salles);
    }

    public Salle getSalle(long id) {
        Optional<Salle> byId = salleRepository.findById(id);

        return byId.get();
    }

    public void saveSalle(Salle salle) {
        salleRepository.save(salle);
    }
}
