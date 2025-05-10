-- src/main/resources/import.sql

-- 1) Utilisateurs
INSERT INTO users (email, nom, password, role, ville) VALUES
                                                          ('houda@gmail.com', 'Houda', 'pass1', 'PATIENT', 'Rabat'),
                                                          ('belhad@gmail.com', 'Belhad', 'pass2', 'INFIRMIER', 'Rabat');

-- 2) Disponibilités pour l’infirmier (dernier utilisateur inséré)
INSERT INTO disponibilite (jour, heure_debut, heure_fin, infirmier_id) VALUES
                                                                           ('2025-05-11', '09:00:00', '12:00:00', 2),
                                                                           ('2025-05-12', '14:00:00', '17:00:00', 2);

-- 3) Réservation initiale
INSERT INTO reservation (date, heure, statut, infirmier_id, patient_id) VALUES
    ('2025-05-11', '10:00:00', 'EN_ATTENTE', 2, 1);
