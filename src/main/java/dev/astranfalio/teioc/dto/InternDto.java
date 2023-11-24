package dev.astranfalio.teioc.dto;
import dev.astranfalio.teioc.entity.InternEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternDto {

    private Long id;
    private String email;
    private String company;
    private String contactDetails;
    private Date creationDate;
    private boolean status;

    public static InternDto convertToDto(InternEntity internEntity) {
        return InternDto.builder()
                .id(internEntity.getId())
                .email(internEntity.getEmail())
                .company(internEntity.getCompany())
                .contactDetails(internEntity.getContactDetails())
                .creationDate(internEntity.getCreationDate())
                .status(internEntity.isStatus())
                .build();
    }

    public static InternEntity convertToEntity(InternDto internDto) {
        return InternEntity.builder()
                .id(internDto.getId())
                .email(internDto.getEmail())
                .company(internDto.getCompany())
                .contactDetails(internDto.getContactDetails())
                .creationDate(internDto.getCreationDate())
                .status(internDto.isStatus())
                .password("test") // mot de passe par défaut
                .build();
    }

}
