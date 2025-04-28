package org.fastexcelexporter.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dhatim.fastexcel.Worksheet;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

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

    public void fillWorksheet(Worksheet worksheet, int rowIndex) {
        worksheet.value(rowIndex, 0, this.fullName);
        worksheet.value(rowIndex, 1, this.nickName);
        worksheet.value(rowIndex, 2, this.email);
        worksheet.value(rowIndex, 3, this.number);
        worksheet.style(rowIndex, 3).format("0.000").set();
        worksheet.value(rowIndex, 4, this.initialBalance);
        worksheet.style(rowIndex, 4).format("#,###").set();
        worksheet.value(rowIndex, 5, this.remainingBalance);
        worksheet.style(rowIndex, 5).format("#,###").set();
        worksheet.value(rowIndex, 6, LocalDateTime.ofInstant(this.createdAt, ZoneId.of("Asia/Jakarta")));
        worksheet.style(rowIndex, 6).format("YYYY-MM-DD HH:mm:ss").set();
        worksheet.value(rowIndex, 7, LocalDateTime.ofInstant(this.updatedAt, ZoneId.of("Asia/Jakarta")));
        worksheet.style(rowIndex, 7).format("YYYY-MM-DD HH:mm:ss").set();
    }
}
