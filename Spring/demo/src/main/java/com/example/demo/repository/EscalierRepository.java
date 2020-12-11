package com.example.demo.repository;

import com.example.demo.modele.Escalier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscalierRepository extends CrudRepository<Escalier, Long> {
}
