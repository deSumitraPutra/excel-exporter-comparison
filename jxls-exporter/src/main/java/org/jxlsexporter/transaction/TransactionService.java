package org.jxlsexporter.transaction;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jxlsexporter.filetemplate.FileTemplate;
import org.jxlsexporter.filetemplate.FileTemplateService;
import org.jxlsexporter.transaction.dto.TransactionFileDTO;
import org.jxlsexporter.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final FileTemplateService fileTemplateService;

    public byte[] generateFileAsByteArray(int limit, String templateName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.generateToOutputStream(outputStream, limit, templateName);

        return outputStream.toByteArray();
    }

    public void generateToResponseStream(HttpServletResponse response, int limit, String templateName) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        this.generateToOutputStream(outputStream, limit, templateName);
    }

    private void generateToOutputStream(OutputStream outputStream, int limit, String templateName) throws IOException {
        PageRequest pageRequest = PageRequest.of(0, limit);
        Page<Transaction> allTransaction = this.repository.findAll(pageRequest);
        if (allTransaction.isEmpty()) {
            throw new RuntimeException("No transactions found");
        }

        FileTemplate template = this.fileTemplateService.getTemplateByCode(templateName);
        List<TransactionFileDTO> fileContents = allTransaction.get()
            .map(Transaction::toFileDTO)
            .toList();
        HashMap<String, Object> data = new HashMap<>() {{
            put("transactions", fileContents);
        }};

        FileUtil.processTemplate(outputStream, template.getContent(), data);
    }
}
