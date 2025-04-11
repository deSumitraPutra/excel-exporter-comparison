package org.jxlsexporter.transaction;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jxlsexporter.filetemplate.FileTemplate;
import org.jxlsexporter.filetemplate.FileTemplateService;
import org.jxlsexporter.transaction.dto.TransactionFileDTO;
import org.jxlsexporter.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final FileTemplateService fileTemplateService;

    public byte[] generateFileAsByteArray() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.generateToOutputStream(outputStream);

        return outputStream.toByteArray();
    }

    public void generateToResponseStream(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        this.generateToOutputStream(outputStream);
    }

    private void generateToOutputStream(OutputStream outputStream) throws IOException {
        List<Transaction> allTransaction = this.repository.findAll();
        if (allTransaction.isEmpty()) {
            throw new RuntimeException("No transactions found");
        }

        FileTemplate template = this.fileTemplateService.getTemplateByCode("TRANSACTION");
        List<TransactionFileDTO> fileContents = allTransaction.stream()
            .map(Transaction::toFileDTO)
            .toList();
        HashMap<String, Object> data = new HashMap<>() {{
            put("transactions", fileContents);
        }};

        FileUtil.processTemplate(outputStream, template.getContent(), data);
    }
}
