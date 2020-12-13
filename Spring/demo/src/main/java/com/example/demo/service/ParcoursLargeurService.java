package com.example.demo.service;

import com.example.demo.modele.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ParcoursLargeurService {
    @Autowired
    EscalierRepository escalierRepository;
    @Autowired
    SalleRepository salleRepository;
    @Autowired
    VoisinRepository voisinRepository;
    @Autowired
    VoisinEscalierRepository voisinEscalierRepository;
    @Autowired
    EscalierSalleRepository escalierSalleRepository;
    @Autowired
    PositionRepository positionRepository;

    Object current;
    Salle destination;

    public List<Object> parcourir(long idSource, long idDest) {
        List<Object> parcouru = new ArrayList<>();
        List<EscalierSalle> listEscalierSalle = (List<EscalierSalle>) escalierSalleRepository.findAll();
        List<VoisinEscalier> listVoisinEscalier = (List<VoisinEscalier>) voisinEscalierRepository.findAll();
        List<Voisin> listVoisin = (List<Voisin>) voisinRepository.findAll();
        List<Escalier> listEscalier = (List<Escalier>) escalierRepository.findAll();
        List<Salle> listSalle = (List<Salle>) salleRepository.findAll();
        listSalle.remove(0);

        if (salleRepository.findById(idSource).isPresent()) {
            for (Salle salle: listSalle) {
                if (salle.getId() == idSource) {
                    current = salle;
                }
            }
        } else {
            for (Escalier escalier: listEscalier) {
                if (escalier.getId() == idSource) {
                    current = escalier;
                }
            }
        }

        for (Salle salle: listSalle) {
            if (salle.getId() == idDest) {
                destination = salle;
            }
        }

        if (current == destination) {
            List<Object> res = new ArrayList<>();
            res.add(destination);
            return res;
        }

        parcouru.add(idSource);

        return avancer(listVoisin, listVoisinEscalier, listEscalierSalle, parcouru, current);
    }

    public List<Object> avancer(List<Voisin> listVoisin, List<VoisinEscalier> listVoisinEscalier, List<EscalierSalle> listEscalierSalle,
                                List<Object> parcouru, Object aParcourir) {

        System.out.println("--------------------> ENTRY");
        System.out.print(parcouru);

        // Liste des elements pour la prochaine iteration
        List<Object> temp = new ArrayList<>();
        Voisin voisinRow = null;
        VoisinEscalier voisinEscalierRow = null;
        EscalierSalle voisinEscalierSalleRow = null;

        // Si notre piece courrante est une Salle
        if (aParcourir instanceof Salle) {
            // On trouve les salles voisines
            for (Voisin voisin: listVoisin) {
                if (voisin.getId() == ((Salle) aParcourir).getId()) {
                    voisinRow = voisin;
                }
            }
            if (!Objects.isNull(voisinRow)) {
                // On verifie le voisin de droite
                if (!Objects.isNull(voisinRow.getIdvoisind())) {
                    if (!parcouru.contains(voisinRow.getIdvoisind())) {
                        if (voisinRow.getIdvoisind() == destination.getId()) {
                            List<Object> res = new ArrayList<>();
                            res.add(destination);
                            res.add(0, aParcourir);
                            System.out.println("<--------------------- CALLBACK");
                            return res;
                        }
                        temp.add(salleRepository.findById(voisinRow.getIdvoisind()).get());
                        parcouru.add(voisinRow.getIdvoisind());
                    }
                }
                // On verifie le voisin de gauche
                if (!Objects.isNull(voisinRow.getIdvoising())) {
                    System.out.println(">>>2> " + parcouru + " : " + voisinRow.getIdvoisinf());
                    System.out.println(">>> " + voisinRow.getIdvoising());
                    if (!parcouru.contains(voisinRow.getIdvoising())) {
                        if (voisinRow.getIdvoising() == destination.getId()) {
                            List<Object> res = new ArrayList<>();
                            res.add(destination);
                            res.add(0, aParcourir);
                            System.out.println("<--------------------- CALLBACK");
                            return res;
                        }
                        temp.add(salleRepository.findById(voisinRow.getIdvoising()).get());
                        parcouru.add(voisinRow.getIdvoising());
                    }
                }
                // On verifie le voisin d'en face
                if (!Objects.isNull(voisinRow.getIdvoisinf())) {
                    System.out.println(">>>3> " + parcouru + " : " + voisinRow.getIdvoisinf());
                    if (!parcouru.contains(voisinRow.getIdvoisinf())) {
                        if (voisinRow.getIdvoisinf() == destination.getId()) {
                            List<Object> res = new ArrayList<>();
                            res.add(destination);
                            res.add(0, aParcourir);
                            System.out.println("<--------------------- CALLBACK");
                            return res;
                        }
                        temp.add(salleRepository.findById(voisinRow.getIdvoisinf()).get());
                        parcouru.add(voisinRow.getIdvoisinf());
                    }
                }
                // On trouve les escaliers voisins
            }
            for (VoisinEscalier voisin: listVoisinEscalier) {
                if (voisin.getId() == ((Salle) aParcourir).getId()) {
                    voisinEscalierRow = voisin;
                }
            }
            // On verifie l'escalier
            if (!Objects.isNull(voisinEscalierRow)) {
                if (!Objects.isNull(voisinEscalierRow.getEscalier())) {
                    if (!parcouru.contains((long) voisinEscalierRow.getEscalier())) {
                        temp.add(escalierRepository.findById((long) voisinEscalierRow.getEscalier()).get());
                        parcouru.add((long) voisinEscalierRow.getEscalier());
                    }
                }
            }
        }
        // Si notre piece courrante est un Escalier
        else if (aParcourir instanceof Escalier){
            // On trouve les salles voisines et l'escalier voisin
            for (EscalierSalle voisin: listEscalierSalle) {
                if (voisin.getId() == ((Escalier) aParcourir).getId()) {
                    voisinEscalierSalleRow = voisin;
                }
            }
            if (!Objects.isNull(voisinEscalierSalleRow)) {
                // On verifie le voisin de droite
                if (!Objects.isNull(voisinEscalierSalleRow.getIdvoisind())) {
                    if (!parcouru.contains(voisinEscalierSalleRow.getIdvoisind())) {
                        if (voisinEscalierSalleRow.getIdvoisind() == destination.getId()) {
                            List<Object> res = new ArrayList<>();
                            res.add(destination);
                            res.add(0, aParcourir);
                            System.out.println("<--------------------- CALLBACK");
                            return res;
                        }
                        temp.add(salleRepository.findById(voisinEscalierSalleRow.getIdvoisind()).get());
                        parcouru.add(voisinEscalierSalleRow.getIdvoisind());
                    }
                }
                // On verifie le voisin de gauche
                if (!Objects.isNull(voisinEscalierSalleRow.getIdvoising())) {
                    if (!parcouru.contains(voisinEscalierSalleRow.getIdvoising())) {
                        if (voisinEscalierSalleRow.getIdvoising() == destination.getId()) {
                            List<Object> res = new ArrayList<>();
                            res.add(destination);
                            res.add(0, aParcourir);
                            System.out.println("<--------------------- CALLBACK");
                            return res;
                        }
                        temp.add(salleRepository.findById(voisinEscalierSalleRow.getIdvoising()).get());
                        parcouru.add(voisinEscalierSalleRow.getIdvoising());
                    }
                }
                // On verifie l'escalier a un autre etage
                if (!Objects.isNull(voisinEscalierSalleRow.getIdvoisinf())) {
                    if (!parcouru.contains(voisinEscalierSalleRow.getIdvoisinf())) {
                        temp.add(escalierRepository.findById(voisinEscalierSalleRow.getIdvoisinf()).get());
                        parcouru.add(voisinEscalierSalleRow.getIdvoisinf());
                    }
                }
            }
        }
        // On lance la recurcivite
        for (Object elem: temp) {
            List<Object> res = avancer(listVoisin, listVoisinEscalier, listEscalierSalle, parcouru, elem);
            if (res != null) {
                res.add(0, aParcourir);
                System.out.println("<--------------------- CALLBACK");
                return res;
            }
        }
        return null;
    }

}
