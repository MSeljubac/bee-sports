package io.beesports.domain.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleDTO {

    private ArticleSourceDTO source;

    private String title;

    private String url;

    private String urlToImage;

    private Date publishedAt;

    private String description;

}
