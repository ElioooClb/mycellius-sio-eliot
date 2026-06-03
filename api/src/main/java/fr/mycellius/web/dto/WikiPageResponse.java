package fr.mycellius.web.dto;

import java.time.Instant;
import java.util.List;

public record WikiPageResponse(
                String id,
                String title,
                String content,
                boolean confidential,
                List<String> tags,
                Instant createdAt) {
}