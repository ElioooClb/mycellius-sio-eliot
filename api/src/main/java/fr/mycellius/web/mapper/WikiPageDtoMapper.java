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

    // DTO -> Domaine
    @Mapping(target = "createdAt", ignore = true)
    WikiPage toDomain(CreateWikiPageRequest request);

    default Tag toDomain(TagRequest request) {
        if (request == null) return null;
        return new Tag(request.name());
    }

    default List<Tag> toDomainTags(List<TagRequest> tags) {
        if (tags == null) return List.of();
        return tags.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .toList();
    }

    // Domaine -> DTO
    @Mapping(target = "tags", expression = "java(mapTags(page.getTags()))")
    WikiPageResponse toResponse(WikiPage page);

    default List<String> mapTags(List<Tag> tags) {
        if (tags == null) return List.of();
        return tags.stream()
                .map(Tag::getValue) // ou getName() selon ton Tag
                .toList();
    }
}