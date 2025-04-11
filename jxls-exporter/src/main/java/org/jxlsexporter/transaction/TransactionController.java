package org.jxlsexporter.transaction;

import lombok.AllArgsConstructor;
import org.jxlsexporter.util.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    @GetMapping("/bytes-array")
    public ResponseEntity<byte[]> downloadAsBytesArray() throws IOException {
        byte[] generatedFile = this.service.generateFileAsByteArray();
        String filename = "Transactions.xlsx";
        HttpHeaders responseHeader = FileUtil.generateExcelHeaders(filename);

        return ResponseEntity.ok()
                .headers(responseHeader)
                .body(generatedFile);
    }
}
