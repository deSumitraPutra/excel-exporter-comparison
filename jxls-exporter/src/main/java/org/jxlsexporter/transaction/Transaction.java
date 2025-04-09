package org.jxlsexporter.transaction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jxlsexporter.account.Account;
import org.jxlsexporter.transaction.dto.TransactionFileDTO;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@EqualsAndHashCode
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    private Instant createdAt;

    public TransactionFileDTO toFileDTO() {
        return TransactionFileDTO.builder()
                .timestamp(this.createdAt.toString())
                .fromAccount(this.sourceAccount.getNumber())
                .fromName(this.sourceAccount.getNickName())
                .toAccount(this.destinationAccount.getNumber())
                .toName(this.destinationAccount.getFullName())
                .amount(this.amount)
                .build();
    }
}
