package com.example.nursepatient.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter @Setter
public class Disponibilite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User infirmier;
    private LocalDate jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;

}
