package com.example.nursepatient.repository;

import com.example.nursepatient.entity.Disponibilite;
import com.example.nursepatient.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite,Long> {
    List<Disponibilite> findByInfirmierAndJour(User infirmer, LocalDate jour);
    @Query("SELECT d FROM Disponibilite d JOIN d.infirmier i WHERE i.ville = :ville AND d.jour = :jour")
    List<Disponibilite> finfByInfirmier_VilleAndJour(@Param("ville") String ville, @Param("jour") LocalDate jour);
}
