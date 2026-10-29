package com.lde.clientaccountingservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "client_accounts")
@NoArgsConstructor
@Data
public class ClientAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ClientStatus status;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(name = "create_dttm", updatable = false, nullable = false)
    private Instant createDttm;

    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    @Column(name = "modify_dttm", nullable = false)
    private Instant modifyDttm;
}
