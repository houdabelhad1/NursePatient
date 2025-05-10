package com.example.nursepatient.console;
import com.example.nursepatient.repository.ReservationRepository;

import com.example.nursepatient.entity.Reservation;
import com.example.nursepatient.entity.User;
import com.example.nursepatient.enums.Role;
import com.example.nursepatient.enums.StatutReservation;
import com.example.nursepatient.repository.UserRepository;
import com.example.nursepatient.service.AuthService;
import com.example.nursepatient.service.InfirmierService;
import com.example.nursepatient.service.PatientService;
import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.springframework.stereotype.Component;


import javax.sound.midi.SysexMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu {
    private final AuthService authService;
    private final PatientService patientService;
    private final InfirmierService infirmierService;
    private final Scanner scanner;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;


    public Menu(AuthService authService, PatientService patientService, InfirmierService infirmierService, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.authService = authService;
        this.patientService = patientService;
        this.infirmierService = infirmierService;
        this.scanner = new Scanner(System.in);
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }
    public void start() {
        while (true) {
            System.out.println("\n*** APP INFIMIER-PATIENT (Houda Belhad)***");
            System.out.println("1. Se connecter");
            System.out.println("2. Quitter");
            System.out.print("Votre choix:");

            int choix = scanner.nextInt();
            scanner.nextLine();
            switch(choix){
                case 1 -> login();
                case 2 -> {
                    System.out.println("Au revoir ! ");
                    return;
                }
                default -> System.out.println("Votre choix invalide");
            }
        }
    }
    private void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if(authService.login(email, password)){
            User currentUser = authService.getCurrentUser();
            if(currentUser.getRole() == Role.PATIENT){
                showPatientMenu();
            }else{
                showInfirmierMenu();
            }
        }else{
            System.out.println("Identifiants incorrects");
        }
    }
    private void showPatientMenu() {
        while (true) {
            System.out.println("\n*** PATIENT MENU ***");
            System.out.println("1. Rechercher infirmiers dispo");
            System.out.println("2. Réserver un crénau ");
            System.out.println("3. Voir mes réservations");
            System.out.println("4. Modifier mon profil");
            System.out.println("5. Déconnecion");
            System.out.print("Votre choix");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix){
                case 1 -> rechercherInfirmiers();
                case 2 -> reserverCreneau();
                case 3 -> afficherReservations();
                case 4 -> modifierProfil();
                case 5 -> {return;}
                default -> System.out.println("Votre choix invalide");
            }

        }
    }
    private void rechercherInfirmiers() {
        System.out.print("Ville:");
        String ville = scanner.nextLine();
        System.out.print("Date (AAAA-MM-JJ) : ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        List<User> infirmiers = patientService.rechercherInfirmiersDisponibles(ville,date);
        if(infirmiers.isEmpty()){
            System.out.println("Aucun infimier dispo");
        }else{
            infirmiers.forEach(i -> System.out.println(
                    "ID:"+i.getId()+"-"+i.getNom()+"("+i.getEmail()+")"
            ));
        }
    }
    private void reserverCreneau(){
        System.out.print("ID de l'infimier");
        Long idInfirmier= scanner.nextLong();
        scanner.nextLine();
        System.out.print("Date (AAAA-MM-JJ) : ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Heure (HH:MM) : ");
        LocalTime heure = LocalTime.parse(scanner.nextLine());
        try{
            User infirmier = userRepository.findById(idInfirmier)
                    .orElseThrow(() -> new RuntimeException("Infirmier non trouvé"));
            Reservation resa = patientService.reserverCreneau(infirmier, date, heure);
            System.out.println("Réservation créée avec l'ID:" +resa.getId());
        }catch(Exception e){
            System.out.println("Erreur: "+e.getMessage());
        }
    }
    private void afficherReservations(){
        List<Reservation> reservations = patientService.getReservationsPatient();
        if(reservations.isEmpty()){
            System.out.println("\nAucune réservation trouvée.");
            return;
        }
        System.out.println("\n*** MES RESERVATIOS ***");
        System.out.println("---------------------------");
        System.out.printf("|%-4s | %-15s | %-12s | %-9s | %n","ID","Infirmier","Date","Statut");
        System.out.println("---------------------------");
        for (Reservation r : reservations){
            User infirmier = userRepository
                    .findById(r.getInfirmier().getId())
                            .orElseThrow(() -> new IllegalStateException(
                                    "Infirmier non trouvé pour l'ID" +r.getInfirmier().getId()
                            ));
            System.out.printf("|%-4s | %-15s | %-12s | %-9s | %n",
                    r.getId(),
                    r.getInfirmier().getNom(),
                    r.getDate().toString(),
                    r.getStatut().toString());
        }
        System.out.println("---------------------------");
        System.out.println("\n Options:");
        System.out.println("1. Rafraichir la liste");
        System.out.println("2. Retour au menu principal");
        System.out.print("Votre choix:");
        int choix = scanner.nextInt();
        scanner.nextLine();
        if(choix == 1){
            afficherReservations();
        }
    }
    private void showInfirmierMenu(){
        while(true){
            System.out.println("\n***MENU INFIRMIER***");
            System.out.println("1. Voir mon planning");
            System.out.println("2. Mettre à jour mes dispo");
            System.out.println("3.Traiter les réservations");
            System.out.println("4.Déconnexion");
            System.out.println("Votre choix");

            int choix = scanner.nextInt();
            scanner.nextLine();
            switch(choix){
                case 1 -> voirPlanning();
                case 2 -> mettreAJourDispo();
                case 3 -> traiterReservations();
                case 4 ->{return;}
                default -> System.out.println("choix invalide");
            }
        }
    }
    private void voirPlanning(){
        List<Reservation> planning= infirmierService.getPlanning();
        if(planning.isEmpty()){
            System.out.println("Aucune rendez-vous prévu");
        }else{
            planning.forEach(r -> System.out.println(
                    "ID:" + r.getId() +
                            "| Patient:"+r.getPatient().getNom()+
                            "|Date:" +r.getDate()+
                            " " +r.getHeure()+
                            "| Statut : "+r.getStatut()
            ));
        }
    }
    private void mettreAJourDispo(){
        System.out.print("Jour (AAAA-MM-JJ):");
        LocalDate jour = LocalDate.parse(scanner.nextLine());
        System.out.print("Heure de début (HH:MM):");
        LocalTime debut = LocalTime.parse(scanner.nextLine());
        System.out.print("Heure de fin (HH:MM):");
        LocalTime fin = LocalTime.parse(scanner.nextLine());
        infirmierService.updateDisponibilities(jour,debut,fin);
        System.out.println("Dispo màj");
    }
    private void traiterReservations(){
        List<Reservation> enAttente = reservationRepository.findByInfirmierAndStatut(
                authService.getCurrentUser(),
                StatutReservation.EN_ATTENTE
        );
        if(enAttente.isEmpty()){
            System.out.println("Aucune réservation en attente");
            return;
        }
        enAttente.forEach(r -> System.out.println(
                "ID" + r.getId()+
                        "|Patient" +r.getPatient().getNom()+
                        "|Date" +r.getDate()+
                        " "+r.getHeure()
        ));
        System.out.print("ID de la réservation à traiter");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("1. Accepter\n2.Refuser)");
        int choix = scanner.nextInt();
        scanner.nextLine();
        try {
            StatutReservation statut = (choix == 1)
                    ? StatutReservation.CONFIRMEE
                    : StatutReservation.REFUSEE;
            infirmierService.traiterReservation(id, statut);
            System.out.println("Réservation màj");
        }catch (Exception e){
            System.out.println("Erreur" +e.getMessage());
        }
    }
    private void modifierProfil(){
        User current = authService.getCurrentUser();
        System.out.println("Profil actuel:");
        System.out.println("Nom:" +current.getNom());
        System.out.println("Ville" +current.getVille());
        System.out.print("Nouveau nom (laisser vide ne pas changer):");
        String nom = scanner.nextLine();
        System.out.print("Nouvelle ville (laisser vide ne pas changer):");
        String ville = scanner.nextLine();
        if(!nom.isEmpty()) current.setNom(nom);
        if(!ville.isEmpty()) current.setVille(ville);
        userRepository.save(current);
        System.out.println("Profil màj");
    }
}