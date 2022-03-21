package io.beesports.domain.dtos.responses;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleResponseDTO {

    private UUID id;

    private String title;

    private String author;

    private String description;

    private String urlToArticle;

    private String imageUrl;

    private String imageAttribution;

    private String sourceName;

    private Date publishedAt;

}
