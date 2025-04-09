package org.jxlsexporter.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@EqualsAndHashCode
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String nickName;

    private String email;

    private String number;

    private BigDecimal initialBalance;

    private BigDecimal remainingBalance;

    private Instant createdAt;

    private Instant updatedAt;
}
