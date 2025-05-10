package com.example.nursepatient.repository;

import com.example.nursepatient.entity.Reservation;
import com.example.nursepatient.entity.User;
import com.example.nursepatient.enums.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByPatient(User patient);
    List<Reservation> findByInfirmier(User infirmier);
    List<Reservation> findByInfirmierAndStatut(User infirmier, StatutReservation statut);
    boolean existsByInfirmierAndDateAndHeure(User infirmier, LocalDate date, LocalTime heure);
}
