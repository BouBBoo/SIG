package com.example.demo.repository;

import com.example.demo.modele.Voisin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoisinRepository extends CrudRepository<Voisin, Long> {
}
