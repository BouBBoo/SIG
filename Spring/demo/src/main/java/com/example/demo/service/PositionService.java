package com.example.demo.service;

import com.example.demo.modele.Position;
import com.example.demo.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    PositionRepository positionRepository;

    public Position findPosition(){
        return positionRepository.findById(Long.parseLong("1")).get();
    }

    public void updatePosition(Position position) {
        positionRepository.save(position);
    }
}
