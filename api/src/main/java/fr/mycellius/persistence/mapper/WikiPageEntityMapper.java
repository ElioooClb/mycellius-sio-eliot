package fr.mycellius.persistence.mapper;
import fr.mycellius.domain.Tag;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.persistence.entity.TagEntity;
import fr.mycellius.persistence.entity.WikiPageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.Set;
@Mapper(componentModel = "spring")
public interface WikiPageEntityMapper {
    @Mapping(target = "tags", expression = "java(toDomainTags(entity.getTags()))")
    WikiPage toDomain(WikiPageEntity entity);
    default List<Tag> toDomainTags(Set<TagEntity> tags) {
        if (tags == null) return List.of();
        return tags.stream().map(t -> new Tag(t.getValue())).toList();
    }
    @Mapping(target = "tags", ignore = true)
    WikiPageEntity toEntity(WikiPage page);
}