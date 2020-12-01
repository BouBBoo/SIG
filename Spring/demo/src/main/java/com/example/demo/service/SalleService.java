package com.example.demo.service;

import com.example.demo.modele.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.SalleRepository;

import java.util.List;

@Service
public class SalleService {
    @Autowired
    SalleRepository salleRepository;

    public List<Salle> findAll(){
        List<Salle> list = (List<Salle>) salleRepository.findAll();

        return list;
    }
}
