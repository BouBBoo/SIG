package com.example.demo.repository;

import com.example.demo.modele.EscalierSalle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscalierSalleRepository extends CrudRepository<EscalierSalle, Long> {
}
