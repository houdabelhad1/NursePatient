# NursePatient
# NursePatient

**NursePatient** est une application console en Java (Spring Boot) permettant la gestion des réservations de créneaux entre patients et infirmiers.

## Fonctionnalités

### Authentification

* Connexion par email et mot de passe
* Différenciation des rôles : **PATIENT** et **INFIRMIER**

### Espace Patient

1. **Rechercher infirmiers disponibles** par ville et date
2. **Réserver un créneau** : choix de l’infirmier, date et heure
3. **Voir mes réservations** : affichage des réservations du patient avec statut
4. **Déconnexion**

### Espace Infirmier

1. **Voir mon planning** : liste des rendez-vous réservés (avec patient, date, heure, statut)
2. **Mettre à jour mes disponibilités** : ajouter des créneaux (jour, heure de début, heure de fin)
3. **Traiter les réservations** : accepter ou refuser les demandes en attente
4. **Déconnexion**

## Architecture & Technologies

* **Java 23**
* **Spring Boot 3.4.5** (Spring Data JPA, Spring JDBC)
* **Hibernate ORM 6.6.13.Final**
* **H2 (in-memory)** pour la base de données
* **Lombok** pour builder et getters/setters
* **Console Java** avec `Scanner` pour l’IHM texte

## Structure du projet

```
src/
 ├─ main/
 │   ├─ java/com/example/nursepatient/
 │   │   ├─ entity/         # Entités JPA (User, Reservation, Disponibilite)
 │   │   ├─ enums/          # Énumérations (Role, StatutReservation)
 │   │   ├─ repository/     # Interfaces Spring Data JPA
 │   │   ├─ service/        # Services métiers (AuthService, PatientService, InfirmierService)
 │   │   ├─ console/        # Classe Menu (IHM console)
 │   │   └─ NursepatientApplication.java
 │   └─ resources/
 │       ├─ application.properties
 │       └─ data.sql      # Données de test
 └─ test/                 # Tests unitaires (si présents)
```

## Installation et exécution

1. Cloner le dépôt :

   ```bash
   git clone git@github.com:houdabelhad1/NursePatient.git
   cd NursePatient
   ```

2. Compiler et lancer l’application :

   ```bash
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```

   ou avec la classe principale :

   ```bash
   mvn clean package
   java -jar target/nursepatient-0.0.1-SNAPSHOT.jar
   ```

3. Interagir via la console :

   * Choisir **1** pour se connecter (email/pass) ou **2** pour quitter
   * Suivre les menus selon le rôle sélectionné

## Données de test (import.sql)

* **Patient** : `houda@gmail.com` / `pass1`
* **Infirmier** : `belhad@gmail.com` / `pass2`
* 2 disponibilités pour l’infirmier les 2025-05-11 (09:00–12:00) et 2025-05-12 (14:00–17:00)
* 1 réservation initiale : 2025-05-11 à 10:00 pour tester le planning

## Personnalisation

* Changez le mode **H2** en mémoire ou persisté via `application.properties`
* Modifiez le script `import.sql` pour adapter les données de test

## Contributions

Les contributions sont les bienvenues !

1. Faites un *fork*
2. Créez une *feature branch* (`git checkout -b feature/ma-fonctionnalite`)
3. Committez vos modifications (`git commit -m 'Ajout de ...'`)
4. Pushez sur la branche (`git push origin feature/ma-fonctionnalite`)
5. Ouvrez une *pull request*

By Houda Belhad
