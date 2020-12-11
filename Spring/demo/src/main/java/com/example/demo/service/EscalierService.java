package com.example.demo.service;

import com.example.demo.modele.Escalier;
import com.example.demo.repository.EscalierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EscalierService {
    @Autowired
    EscalierRepository escalierRepository;

    public Escalier findEscalier(long id){
        return escalierRepository.findById(id).get();
    }
}
