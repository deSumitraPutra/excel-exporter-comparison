package org.jxlsexporter.transaction;

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

        return FileUtil.generateByteArrayFile(template.getContent(), data);
    }
}
