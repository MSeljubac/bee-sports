package io.beesports.mappers;

import com.rometools.rome.feed.synd.SyndEntry;
import io.beesports.domain.dtos.ArticleDTO;
import io.beesports.domain.dtos.responses.ArticleResponseDTO;
import io.beesports.domain.entities.Article;
import io.beesports.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ArticleMapper {

    private final IArticleRepository articleRepository;

    @Autowired
    public ArticleMapper(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article rssSyndEntryToEntity(SyndEntry entry, String source) {
        Article article = articleRepository.findFirstByTitleAndSourceName(entry.getTitle(), source);

        if (article == null) {
            article = new Article();
            article.setId(UUID.randomUUID());
        }

        article.setTitle(entry.getTitle());
        article.setDescription(entry.getDescription().getValue());
        article.setSourceName(source);
        article.setUrlToArticle(entry.getLink());
        article.setAuthor(entry.getAuthor());
        article.setPublishedAt(entry.getPublishedDate() == null ? entry.getUpdatedDate() : entry.getPublishedDate());

        return article;
    }

    public Article dtoToEntity(ArticleDTO articleDTO) {
        Article article = articleRepository.findFirstByTitleAndSourceName(articleDTO.getTitle(), articleDTO.getSource().getName());

        if (article == null) {
            article = new Article();
            article.setId(UUID.randomUUID());
        }

        article.setTitle(articleDTO.getTitle());
        article.setDescription(articleDTO.getDescription());
        article.setSourceName(articleDTO.getSource().getName());
        article.setImageUrl(articleDTO.getUrlToImage());
        article.setUrlToArticle(articleDTO.getUrl());
        article.setPublishedAt(articleDTO.getPublishedAt());

        return article;
    }

    public ArticleResponseDTO entityToResponseDto(Article article) {
        ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO();

        articleResponseDTO.setId(article.getId());
        articleResponseDTO.setTitle(article.getTitle());
        articleResponseDTO.setAuthor(article.getAuthor());
        articleResponseDTO.setDescription(article.getDescription());
        articleResponseDTO.setUrlToArticle(article.getUrlToArticle());
        articleResponseDTO.setImageUrl(article.getImageUrl());
        articleResponseDTO.setImageAttribution(article.getImageAttribution());
        articleResponseDTO.setSourceName(article.getSourceName());
        articleResponseDTO.setPublishedAt(article.getPublishedAt());

        return articleResponseDTO;
    }

}
