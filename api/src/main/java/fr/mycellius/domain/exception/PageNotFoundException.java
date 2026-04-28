package fr.mycellius.domain.exception;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String id) {
        super("Page introuvable pour l'id: " + id);
    }
}