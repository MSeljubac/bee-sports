package io.beesports.domain.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamPlayerDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String name;

    private String nationality;

    private String role;

    private String imageUrl;

    private String flagImageUrl;

}
