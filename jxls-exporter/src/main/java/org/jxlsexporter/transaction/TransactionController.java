package org.jxlsexporter.transaction;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jxlsexporter.util.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
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

    @GetMapping("/outstream")
    public void downloadStream(HttpServletResponse response) throws IOException {
        String filename = "Transactions-stream.xlsx";
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s;", filename));
        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        this.service.generateToResponseStream(response);
    }
}
