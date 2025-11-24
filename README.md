## TP – Sécurisation d’une API REST avec JWT (Spring Boot 3 + Spring Security 6)


**Objectif du TP**

- L’objectif de ce TP est de sécuriser une API REST grâce à un système d’authentification stateless basé sur les JSON Web Tokens (JWT).

**À la fin du TP, vous serez capable de :**

- Créer une API stateless avec Spring Boot

- Mettre en place une authentification avec JWT

- Créer un filtre personnalisé d’autorisation

- Protéger des routes selon les rôles (ROLE_USER, ROLE_ADMIN)

- Tester l’API avec Postman

## Technologies utilisées

- Java 21 ou +

- Spring Boot 3.x

- Spring Security 6

- Spring Web

- Spring Data JPA

- MySQL

- Lombok

- JJWT (Java JWT - io.jsonwebtoken)

## Structure du projet


```text
ma.ens.security
 ├── config/
 │     └── SecurityConfig.java
 ├── jwt/
 │     ├── JwtUtil.java
 │     └── JwtAuthorizationFilter.java
 ├── entities/
 │     ├── User.java
 │     └── Role.java
 ├── repositories/
 │     ├── UserRepository.java
 │     └── RoleRepository.java
 ├── services/
 │     └── CustomUserDetailsService.java
 ├── web/
 │     ├── AuthController.java
 │     └── TestController.java
 └── SpringJwtApiApplication.java
```




<img width="677" height="859" alt="image" src="https://github.com/user-attachments/assets/e406e5b0-42d7-4139-92bc-009a4cda36db" />



## Configuration de la base de données

**Fichier application.properties :**

- Configuration MySQL

- Activation de JPA

- Configuration du secret JWT

- Durée d’expiration du token

**La base utilisée :**


```tex
jdbc:mysql://localhost:3306/security_jwt
```


## Modèle de données


**Entités :**

- User

- id

- username

- password

- active

- roles

- Role

- id

- name

**Relation :**


```text
User (Many) ↔ (Many) Role
```

## Fonctionnement du JWT

**Chaque utilisateur authentifié reçoit un token contenant :**

- son username

- sa date d’expiration

- une signature cryptée

- Le serveur ne stocke rien : toute la vérification se fait sur le token reçu.

**Le token doit être transmis dans chaque requête sécurisée :**

```text
Authorization: Bearer <token>
```



## Flow d’authentification

**Le client appelle**

```text
POST /api/auth/login
```



avec :


- username

 password

- Spring Security vérifie l’utilisateur

- Le serveur renvoie un token JWT

- Le client utilise ce token pour accéder aux routes sécurisées

**Le filtre JwtAuthorizationFilter :**

- extrait le token

- vérifie la signature

- reconstruit l’utilisateur dans Spring Security

## Protection des routes

**Routes publiques :**

```text
/api/auth/**
```

**Routes protégées :**


```text
/api/user/**          → rôle USER ou ADMIN
/api/admin/**         → rôle ADMIN
```



- L’API est totalement stateless (aucune session enregistrée).

## Tests avec Postman
 1. Authentification

- POST


```text
http://localhost:8080/api/auth/login
```

**Body JSON :**


```text
{
  "username": "admin",
  "password": "1234"
}
```


**Réponse attendue :**



```text
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin",
  "type": "Bearer"
}
```

<img width="1393" height="752" alt="tp11 1" src="https://github.com/user-attachments/assets/2bd1ec7c-2be7-4f5d-a32a-24312d06e820" />




 2. Accès à une route protégée

- GET


```text
http://localhost:8080/api/user/profile
```

**Headers :**

```text
Authorization: Bearer <TOKEN_COPIÉ>
```

**Réponse attendue :**


```text
{
  "username": "admin",
  "roles": ["ROLE_ADMIN"]
}
```

<img width="1918" height="800" alt="TP11 2" src="https://github.com/user-attachments/assets/147efc85-d31d-4241-89c6-2a49a5a04952" />



 3. Cas d’erreur : Token manquant ou invalide



**Réponse :**

```text
404
```

<img width="1440" height="546" alt="TP11 3" src="https://github.com/user-attachments/assets/3b7eb120-7972-4f38-8fd3-68c94d972d0e" />


## Points clés à comprendre

- HTTP est stateless ⇒ JWT permet de vérifier l’identité sans session

- Un token expiré doit être régénéré

- Le secret doit être long et sécurisé

- Le filtre JWT remplace totalement les sessions classiques

- Spring Security bloque automatiquement les utilisateurs non authentifiés




