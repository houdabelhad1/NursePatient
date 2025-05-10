package com.example.nursepatient.entity;

import com.example.nursepatient.enums.StatutReservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User patient;
    @ManyToOne
    private User infirmier;
    private LocalDate date;
    private LocalTime heure;
    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

}
