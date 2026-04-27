GitHub Issues est utilisé comme outil de gestion de tickets afin d’assurer le suivi des demandes, des anomalies et des preuves dans le cadre du projet E6.

Workflow GitHub Issue :
issue → modif → commit → PR → fermeture auto

## Tests API (à push sur feature/jwt-auth créé au préalable)

Les tests de l’API ont été réalisés avec Postman.

Endpoints testés :

- POST /api/auth/login
- Routes sécurisées avec JWT

Résultats :

- Authentification fonctionnelle
- Accès refusé sans token valide
