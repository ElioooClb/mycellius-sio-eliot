package fr.mycellius.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateWikiPageRequest(

                @NotBlank(message = "L'id est obligatoire") @Pattern(regexp = "PAGE-\\d{3}", message = "Format d'id attendu : PAGE-001") String id,

                @NotBlank(message = "Le titre est obligatoire") @Size(min = 3, max = 120, message = "Le titre doit faire entre 3 et 120 caractères") String title,

                @NotBlank(message = "Le contenu est obligatoire") @Size(max = 10000, message = "Le contenu est trop long (max 10000)") String content,

                boolean confidential,

                @Valid List<TagRequest> tags) {
}