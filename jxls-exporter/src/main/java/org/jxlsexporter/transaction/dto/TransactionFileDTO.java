package org.jxlsexporter.transaction.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode
public class TransactionFileDTO {
    private Long id;
    private String timestamp;
    private String fromAccount;
    private String fromName;
    private String toAccount;
    private String toName;
    private BigDecimal amount;
}
