package dev.astranfalio.teioc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name="intern")
@Data
public class InternEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    private String company;

    private String contactDetails;

    private Date creationDate;

    private boolean status; // todo: to test
}
