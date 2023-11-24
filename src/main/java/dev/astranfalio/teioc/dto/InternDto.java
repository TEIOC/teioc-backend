package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.InternEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Company cannot be null")
    private String company;

    private String contactDetails;
    private Date creationDate;
    private boolean status;

    public static InternDto convertToDto(InternEntity internEntity) {
        return InternDto.builder()
                .id(internEntity.getId())
                .email(internEntity.getEmail())
                .password(internEntity.getPassword())
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
                .password(internDto.getPassword())
                .company(internDto.getCompany())
                .contactDetails(internDto.getContactDetails())
                .creationDate(internDto.getCreationDate())
                .status(internDto.isStatus())
                .build();
    }

}
