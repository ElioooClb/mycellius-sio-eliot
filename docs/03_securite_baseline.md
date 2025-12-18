# Séance 1 — Baseline cybersécurité (Bloc 3) : CIA + registre des risques

## 1) CIA (Confidentialité, Intégrité, Disponibilité)

### Confidentialité (C)

Objectif : empêcher l’accès aux données par des personnes non autorisées.
Mesures minimales (Sprint 0 / à venir) :

- Authentification (JWT) et rôles (RBAC) à partir de la séance sécurité.
- Secrets hors dépôt Git (pas de mots de passe dans le code, .env non commité).
- Accès serveur via SSH par clé (pas de mot de passe faible).
- HTTPS en production (plus tard).
  Preuves :
- Clés SSH en place sur les VM.
- `.gitignore` + `.env.example` (si utilisé) et aucun secret commité.

### Intégrité (I)

Objectif : empêcher la modification non autorisée et garantir un état cohérent.
Mesures minimales :

- Validation côté serveur (à venir avec Spring).
- Migrations de base de données versionnées (Flyway/Liquibase, à venir).
- CI qui vérifie la structure et qui échouera si la base manque.
- Historique Git + PR dev→test→prod.
  Preuves :
- Workflow Git (PR) actif.
- GitHub Actions “CI” en vert.

### Disponibilité (A)

Objectif : garder le service accessible et récupérable après incident.
Mesures minimales :

- 3 environnements séparés (dev/test/prod) : une casse n’impacte pas prod.
- Procédure de redémarrage services (nginx, docker).
- Sauvegarde/restauration (à venir avec MySQL).
  Preuves :
- 3 VM accessibles et pages DEV/TEST/PROD.
- Commandes de restart connues/documentées.

---

## 2) Registre des risques (mini)

## 2) Registre des risques (mini)

| Risque                                  | Impact | Vraisemblance | Mesures de réduction                                           | Preuve / test               |
| --------------------------------------- | :----: | :-----------: | -------------------------------------------------------------- | --------------------------- |
| Secrets committés (token, mdp)          |  Fort  |     Moyen     | `.env` non versionné, `.gitignore`, revue PR, secret scanning  | PR review, scan CI          |
| Compromission SSH (mot de passe faible) |  Fort  |     Moyen     | Auth par clé, désactiver password login                        | Test connexion par clé      |
| Perte de données DB                     |  Fort  |     Moyen     | Backups réguliers + tests de restauration                      | Procédure + test de restore |
| Injection / XSS via entrées utilisateur |  Fort  |     Moyen     | Validation serveur, encodage, tests (OWASP ZAP ultérieurement) | Tests automatisés           |
| Token JWT volé                          |  Fort  |     Moyen     | Durée courte, rotation, stockage sécurisé (mobile)             | Revue config JWT            |

## 3) Décisions prises en séance 1 (Sprint 0)

- Branches : dev/test/prod avec PR obligatoires vers test/prod.
- 3 VM : 192.168.1.137 (DEV), 192.168.1.150 (TEST), 192.168.1.138 (PROD).
- “Preuve de déploiement” : nginx sert une page différente selon la branche.
