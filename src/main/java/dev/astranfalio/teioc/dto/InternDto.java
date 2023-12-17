package dev.astranfalio.teioc.dto;

import dev.astranfalio.teioc.entity.InternEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternDto {

    private Integer id;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Company cannot be null")
    private String company;

    private String contactDetails;
    private Date creationDate;
    private boolean status;
    private Date lastConnection;


    public static InternDto convertToDto(InternEntity internEntity) {
        return InternDto.builder()
                .id(internEntity.getId())
                .email(internEntity.getEmail())
                .password(internEntity.getPassword())
                .name(internEntity.getName())
                .company(internEntity.getCompany())
                .contactDetails(internEntity.getContactDetails())
                .creationDate(internEntity.getCreationDate())
                .status(internEntity.isStatus())
                .lastConnection(internEntity.getLastConnection())
                .build();
    }

    public static InternEntity convertToEntity(InternDto internDto) {
        return InternEntity.builder()
                .id(internDto.getId())
                .email(internDto.getEmail())
                .password(internDto.getPassword())
                .name(internDto.getName())
                .company(internDto.getCompany())
                .contactDetails(internDto.getContactDetails())
                .creationDate(internDto.getCreationDate())
                .status(internDto.isStatus())
                .lastConnection(internDto.getLastConnection())
                .build();
    }

}
