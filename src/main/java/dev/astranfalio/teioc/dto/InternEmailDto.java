package dev.astranfalio.teioc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternEmailDto {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;
}
