package org.jxlsexporter.transaction;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jxlsexporter.util.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    @GetMapping("/bytes-array")
    public ResponseEntity<byte[]> downloadAsBytesArray(
        @RequestParam(defaultValue = "1000") int limit,
        @RequestParam(defaultValue = "TRANSACTION") String templateName
    ) throws IOException {
        String filename = String.format("%s_%s.xlsx", templateName, limit);
        HttpHeaders responseHeader = FileUtil.generateExcelHeaders(filename);
        responseHeader.add("Start-Time", Instant.now().toString());
        byte[] generatedFile = this.service.generateFileAsByteArray(limit, templateName);

        return ResponseEntity.ok()
            .headers(responseHeader)
            .body(generatedFile);
    }

    @GetMapping("/outstream")
    public void downloadStream(
        HttpServletResponse response,
        @RequestParam(defaultValue = "1000") int limit,
        @RequestParam(defaultValue = "TRANSACTION") String templateName
    ) throws IOException {
        String filename = String.format("OutStream_%s_%s.xlsx", templateName, limit);
        response.setHeader("Start-Time", Instant.now().toString());
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s;", filename));
        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        this.service.generateToResponseStream(response, limit, templateName);
    }
}
