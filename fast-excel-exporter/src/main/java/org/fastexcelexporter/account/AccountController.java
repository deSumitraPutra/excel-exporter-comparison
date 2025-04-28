package org.fastexcelexporter.account;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.fastexcelexporter.filehelper.AccountFileHelper;
import org.fastexcelexporter.util.HttpUtils;
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
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;

    @GetMapping("/bytes-array")
    public ResponseEntity<byte[]> downloadAsBytesArray(@RequestParam(defaultValue = "1000") int limit) throws IOException {
        String filename = String.format("FastExcel_ByteArray_%s.xlsx", limit);
        HttpHeaders responseHeader = HttpUtils.generateExcelHeaders(filename);
        responseHeader.add("Start-Time", Instant.now().toString());
        byte[] generatedFile = this.service.generateFileAsByteArray(limit);

        return ResponseEntity.ok()
            .headers(responseHeader)
            .body(generatedFile);
    }

    @GetMapping("/outstream")
    public void downloadStream(HttpServletResponse response, @RequestParam(defaultValue = "1000") int limit) throws IOException {
        String filename = String.format("FastExcel_OutStream_%s.xlsx", limit);
        response.setHeader("Start-Time", Instant.now().toString());
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s;", filename));
        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        this.service.generateToResponseStream(response, limit);
    }
}
