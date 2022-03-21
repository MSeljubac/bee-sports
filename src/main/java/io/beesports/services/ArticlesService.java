package io.beesports.services;

import io.beesports.domain.dtos.responses.ArticleResponseDTO;
import io.beesports.mappers.ArticleMapper;
import io.beesports.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticlesService {

    private final IArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticlesService(IArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public List<ArticleResponseDTO> getLatestArticles() {
        return articleRepository.findTop10ByPublishedOrderByPublishedAtDesc(true).stream()
                .map(articleMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

}
