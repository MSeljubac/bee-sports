package io.beesports.domain.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerResponseDTO {

    private Integer id;

    private String first_name;

    private String last_name;

    private String name;

    private String nationality;

    private String role;

    private String image_url;

}
