package org.jxlsexporter.transaction;

import lombok.AllArgsConstructor;
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

    @GetMapping
    public ResponseEntity<byte[]> downloadTransactions() throws IOException {
        byte[] generatedFile = this.service.generateFile();
        String filename = "Transactions.xlsx";
        String contentDisposition = "attachment; filename=" + filename + ";";
        String contentType = "application/vnd.ms-excel";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", contentDisposition);
        httpHeaders.add("Content-Type", contentType);
        httpHeaders.add("Content-Length", String.valueOf(generatedFile.length));
        httpHeaders.add("Access-Control-Expose-Headers", "Content-Disposition");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(generatedFile);
    }
}
