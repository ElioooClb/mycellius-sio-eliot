package fr.mycellius.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagRequest(
        @NotBlank(message = "Le tag est obligatoire")
        @Size(min = 1, max = 30, message = "Le tag doit faire entre 1 et 30 caractères")
        String name
) {}
