package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;
@Entity
@Table(name="intern")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InternEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="company")
    private String company;

    @Column(name="contactDetails")
    private String contactDetails;

    @Column(name="creationDate")
    private Date creationDate;

    @Column(name="status")
    private boolean status;
}