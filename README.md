# Vérification d'opposition véhicule

Service SOAP qui indique si un véhicule (par immatriculation) fait l'objet d'une opposition.

**Stack :** Java 17, Spring Boot 3, Spring-WS, JAXB, Spring Data JPA, MySQL

## Lancer

```bash
sudo mysql < sql/init.sql            # remplacer 'change_me' par votre mot de passe
export DB_PASSWORD=votre_mot_de_passe
mvn spring-boot:run
```

WSDL : http://localhost:8080/ws/verifOpposition.wsdl

## Tester

```bash
mvn test
curl -s -H "Content-Type: text/xml" -d @requete-1234.xml http://localhost:8080/ws
```
