package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name="intern")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InternEntity implements Activatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Column(name="email", nullable = false)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(name="password", nullable = false)
    private String password;

    @NotNull(message = "Company cannot be null")
    @Column(name="company")
    private String company;

    @Column(name="contactDetails")
    private String contactDetails;

    @Column(name="creationDate")
    private Date creationDate;

    @Column(name="status")
    private boolean status;
    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }
}