package com.example.nursepatient.service;

import com.example.nursepatient.entity.Reservation;
import com.example.nursepatient.entity.User;
import com.example.nursepatient.enums.Role;
import com.example.nursepatient.enums.StatutReservation;
import com.example.nursepatient.repository.DisponibiliteRepository;
import com.example.nursepatient.repository.ReservationRepository;
import com.example.nursepatient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DisponibiliteRepository disponibiliteRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AuthService authService;
    public List<User> rechercherInfirmiersDisponibles(String ville, LocalDate date){
        List<User> infirmiers = userRepository.findByRoleAndVille(Role.INFIRMIER, ville);
        infirmiers.removeIf(infirmier ->disponibiliteRepository.findByInfirmierAndJour(infirmier,date).isEmpty());
        return infirmiers;
    }
    public Reservation reserverCreneau(User infimier , LocalDate date, LocalTime heure){
        if(reservationRepository.existsByInfirmierAndDateAndHeure(infimier, date, heure)){
            throw new RuntimeException("Ce créneau est déjà réservé");
        }
        Reservation reservation = new Reservation();
        reservation.setPatient(authService.getCurrentUser());
        reservation.setInfirmier(infimier);
        reservation.setDate(date);
        reservation.setHeure(heure);
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsPatient(){
        return reservationRepository.findByPatient(authService.getCurrentUser());

    }
    public void updateProfil(String nom , String ville) {
        User patient = authService.getCurrentUser();
        patient.setNom(nom);
        patient.setVille(ville);
        userRepository.save(patient);
    }
}
