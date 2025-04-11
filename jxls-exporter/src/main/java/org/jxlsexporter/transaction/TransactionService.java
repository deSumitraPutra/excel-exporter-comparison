package org.jxlsexporter.transaction;

import lombok.RequiredArgsConstructor;
import org.jxlsexporter.filetemplate.FileTemplate;
import org.jxlsexporter.filetemplate.FileTemplateService;
import org.jxlsexporter.transaction.dto.TransactionFileDTO;
import org.jxlsexporter.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;

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

        List<TransactionFileDTO> fileContents = allTransaction.stream()
                .map(Transaction::toFileDTO)
                .toList();
        FileTemplate template = this.fileTemplateService.getTemplateByCode("TRANSACTION");
        Map<String, Object> data = Map.of("transactions", fileContents);
        return FileUtil.generateByteArrayFile(template.getContent(), data);
    }
}
