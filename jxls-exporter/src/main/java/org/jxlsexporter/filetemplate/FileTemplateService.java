package org.jxlsexporter.filetemplate;

import lombok.AllArgsConstructor;
import org.jxlsexporter.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileTemplateService {
    private final FileTemplateRepository repository;

    public FileTemplate getTemplateByCode(String code) {
        return this.repository.findByCode(code);
    }
}
