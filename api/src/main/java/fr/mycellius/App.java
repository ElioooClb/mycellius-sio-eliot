package fr.mycellius;

import fr.mycellius.domain.Tag;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.domain.exception.PageNotFoundException;
import fr.mycellius.repository.InMemoryWiki;
import fr.mycellius.service.WikiService;

public class App {
    public static void main(String[] args) {
        int nombreDePages = 0;
        double tempsMoyenLecture = 3.5;
        boolean wikiActif = true;
        String nomProjet = "Mycellius";
        System.out.println("Mycellius démarre !");
        System.out.println("Bienvenue dans la séance 2 (bases Java).");
        System.out.println("Nom du projet : " + nomProjet);
        System.out.println("Nombre de pages : " + nombreDePages);
        System.out.println("Temps moyen de lecture : " + tempsMoyenLecture + " minutes");
                System.out.println("Wiki actif ? " + wikiActif);

        System.out.println("--- Mise à jour des statistiques ---");
        nombreDePages = nombreDePages + 5;
        tempsMoyenLecture = tempsMoyenLecture + 0.2;
        System.out.println("Nouveau nombre de pages : " + nombreDePages);
        System.out.println("Nouveau temps moyen de lecture : " + tempsMoyenLecture + " minutes");

        if (nombreDePages == 0) {
            System.out.println("Le Wiki est vide pour l'instant.");
        } else {
            System.out.println("Le wiki contient déjà des pages");
        }

        System.out.println("--- Simulation de chargement ---");
        for (int i = 1; i <= 3; i++) {
            System.out.println("Chargement... étape " + i);
        }

        System.out.println("--- Démo POO avec encapsulation ---");
        Person bob = new Person("Bob", 30);
        bob.sayHello();
        System.out.println("Nom (via getter) : " + bob.getName());


        System.out.println("--- Démo exceptions Mycellius ---");
        try {
// Simulation d'erreur
            throw new PageNotFoundException("PAGE-XXX");
        } catch (PageNotFoundException e) {
            System.out.println("Erreur fonctionnelle : " + e.getMessage());
        }
        System.out.println("Le programme continue après la gestion de l'erreur.");

        System.out.println("--- Démo InMemoryWiki ---"); InMemoryWiki repo = new InMemoryWiki();
        WikiPage p1 = new WikiPage("PAGE-001", "Installation Docker", "...");
        WikiPage p2 = new WikiPage("PAGE-002", "Installation SSH", "...");
        repo.save(p1);
        repo.save(p2);
        System.out.println("Nombre de pages : " + repo.findAll().size());
        System.out.println("Recherche 'docker' :");
        for (WikiPage page : repo.searchByTitleContaining("docker")) {
            System.out.println(" - " + page.getId() + " : " + page.getTitle());
        }

        System.out.println("--- Démo WikiService ---");
        InMemoryWiki repository = new InMemoryWiki();
        WikiService service = new WikiService(repository);
        service.createPage("PAGE-001", "Installation Docker", "Étapes pour installer Docker sur Ubuntu.");
                service.createPage("PAGE-002", "Installation SSH", "Configurer l'accès SSH sécurisé.");
// Cas nominal : recherche
                        System.out.println("Pages contenant 'docker' :");
        for (WikiPage page : service.searchByTitle("docker")) {
            System.out.println(" - " + page.getId() + " : " + page.getTitle());
        }
// Cas d'erreur : doublon
        try {
            service.createPage("PAGE-001", "Autre titre", "Contenu");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur attendue (doublon id) : " + e.getMessage());
        }
// Cas d'erreur : page introuvable
        try {
            service.getPageById("PAGE-999");
        } catch (PageNotFoundException e) {
            System.out.println("Erreur attendue (page introuvable) : " + e.getMessage());
        }

        System.out.println("--- Démo Mycellius (Tag + WikiPage) ---");
        Tag t1 = new Tag(" Docker ");
        System.out.println("Tag normalisé : " + t1.getValue());
        WikiPage page = new WikiPage("PAGE-001", "Installation Docker","Étapes pour installer Docker " +
                "sur Ubuntu.");
                System.out.println("Page créée : " + page.getId() + " - " + page.getTitle());


    }
}
