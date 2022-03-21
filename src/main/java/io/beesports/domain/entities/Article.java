package io.beesports.domain.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {

    @Id
    private UUID id;

    @Column(length = 5000)
    private String sourceName;

    @Column(length = 5000)
    private String title;

    @Column(length = 5000)
    private String author;

    @Column(length = 5000)
    private String urlToArticle;

    @Column(length = 5000)
    private String imageUrl;
    
    @Column(length = 5000)
    private String imageAttribution;

    private Date publishedAt;

    @Column(length = 5000)
    private String description;

    private Boolean published = false;

}
