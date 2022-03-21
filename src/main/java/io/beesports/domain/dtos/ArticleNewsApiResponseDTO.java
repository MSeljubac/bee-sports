package io.beesports.domain.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleNewsApiResponseDTO {

    private String status;
    private Integer totalResults;
    private List<ArticleDTO> articles;

}
