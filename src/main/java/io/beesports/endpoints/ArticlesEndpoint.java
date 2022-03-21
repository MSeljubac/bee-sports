package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.ArticleResponseDTO;
import io.beesports.services.ArticlesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticlesEndpoint {

    private final ArticlesService articlesService;

    @Autowired
    public ArticlesEndpoint(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @Operation(summary = "Gets latest articles on LoL and Leagues")
    @GetMapping(value = "/latest", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<ArticleResponseDTO> getLatestNewsArticles(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion) {
        return articlesService.getLatestArticles();
    }

}
