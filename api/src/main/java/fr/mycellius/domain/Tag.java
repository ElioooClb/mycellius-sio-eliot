package fr.mycellius.domain;

import java.util.Locale;

public class Tag {
    private final String value;
    public Tag(String raw) {
        this.value = normalize(raw);
    }
    public static String normalize(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("tag null");
        }
        String v = raw.trim().toLowerCase(Locale.ROOT);
        if (v.isEmpty()) {
            throw new IllegalArgumentException("tag vide");
        }
        return v;
    }
    public String getValue() {
        return value;
    }
}
