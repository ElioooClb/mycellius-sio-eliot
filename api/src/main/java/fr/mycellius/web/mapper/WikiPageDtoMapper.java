package fr.mycellius.web.mapper;
import fr.mycellius.domain.Tag;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.web.dto.CreateWikiPageRequest;
import fr.mycellius.web.dto.TagRequest;
import fr.mycellius.web.dto.WikiPageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.Objects;
@Mapper(componentModel = "spring")
public interface WikiPageDtoMapper {
    // DTO -> Domaine (id/title/content mappés automatiquement si mêmes noms)
    WikiPage toDomain(CreateWikiPageRequest request);
    // DTO -> Domaine (Tag immuable : on construit un nouvel objet)
    default Tag toDomain(TagRequest request) {
        if (request == null) return null;
        return new Tag(request.name());
    }
    // DTO -> Domaine (liste de tags)
    default List<Tag> toDomainTags(List<TagRequest> tags) {
        if (tags == null) return List.of();
        return tags.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .toList();
    }
    // Domaine -> DTO
    // createdAt : ignoré en séance 6 (le domaine ne le porte pas encore)
    // tags : renvoyés vides en séance 6 (WikiPage ne les expose pas encore)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tags", expression = "java(java.util.List.of())")
    WikiPageResponse toResponse(WikiPage page);
}