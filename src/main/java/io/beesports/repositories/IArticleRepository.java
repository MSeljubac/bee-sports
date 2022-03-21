package io.beesports.repositories;

import io.beesports.domain.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IArticleRepository extends JpaRepository<Article, UUID> {

    Article findFirstByTitleAndSourceName(String title, String sourceName);

    List<Article> findTop10ByPublishedOrderByPublishedAtDesc(Boolean published);
}
