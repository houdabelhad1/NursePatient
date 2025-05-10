package com.example.nursepatient.service;

import com.example.nursepatient.entity.Disponibilite;
import com.example.nursepatient.entity.Reservation;
import com.example.nursepatient.enums.StatutReservation;
import com.example.nursepatient.repository.DisponibiliteRepository;
import com.example.nursepatient.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class InfirmierService {
    @Autowired private DisponibiliteRepository disponibiliteRepository;
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private AuthService authService;
    public List<Reservation> getPlanning(){
        return reservationRepository.findByInfirmier(authService.getCurrentUser());
    }
    public void updateDisponibilities(LocalDate jour, LocalTime debut, LocalTime fin){
        Disponibilite dispo = new Disponibilite();
        dispo.setInfirmier(authService.getCurrentUser());
        dispo.setJour(jour);
        dispo.setHeureDebut(debut);
        dispo.setHeureFin(fin);
        disponibiliteRepository.save(dispo);
    }
    public void traiterReservation(Long reservationId , StatutReservation statut){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new RuntimeException("Réservation non trouvée"));
        if (!reservation.getInfirmier().getId().equals(authService.getCurrentUser().getId())){
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette réservation");
        }
        reservation.setStatut(statut);
        reservationRepository.save(reservation);}
}
