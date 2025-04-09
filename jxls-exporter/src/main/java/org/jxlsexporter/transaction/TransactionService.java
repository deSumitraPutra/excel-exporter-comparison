package org.jxlsexporter.transaction;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jxls.builder.JxlsOutput;
import org.jxls.builder.JxlsOutputFile;
import org.jxls.common.Context;
import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder;
import org.jxlsexporter.filetemplate.FileTemplate;
import org.jxlsexporter.filetemplate.FileTemplateService;
import org.jxlsexporter.transaction.dto.TransactionFileDTO;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final FileTemplateService fileTemplateService;

    public byte[] generateFile() throws IOException {
        List<Transaction> allTransaction = this.repository.findAll();
        if (allTransaction.isEmpty()) {
            throw new RuntimeException("No transactions found");
        }

        List<TransactionFileDTO> fileContents = allTransaction.stream()
                .map(Transaction::toFileDTO)
                .toList();
        FileTemplate template = this.fileTemplateService.getTemplateByCode("TRANSACTION");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(template.getContent())) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("transactions", fileContents);
            JxlsOutputFile jxlsOutputFile = new JxlsOutputFile(new File("lol.xlsx"));
            JxlsPoiTemplateFillerBuilder.newInstance()
                    .withTemplate(inputStream)
                    .build()
                    .fill(data, jxlsOutputFile);
            outputStream.writeTo(jxlsOutputFile.getOutputStream());
        }
        return outputStream.toByteArray();
    }
}
