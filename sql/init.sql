CREATE DATABASE IF NOT EXISTS verif_vehicules CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE verif_vehicules;

CREATE TABLE IF NOT EXISTS oppositions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_immatriculation VARCHAR(20) NOT NULL UNIQUE,
    en_opposition BOOLEAN NOT NULL,
    message TEXT NOT NULL
);

INSERT INTO oppositions (numero_immatriculation, en_opposition, message) VALUES
('1234-AB-5', TRUE,  'Véhicule en opposition judiciaire. Vente impossible.'),
('5678-CD-9', FALSE, 'Aucune opposition détectée.');

-- Utilisateur dédié à l'application
CREATE USER IF NOT EXISTS 'verif_user'@'localhost' IDENTIFIED BY 'change_me';
GRANT ALL PRIVILEGES ON verif_vehicules.* TO 'verif_user'@'localhost';
FLUSH PRIVILEGES;
