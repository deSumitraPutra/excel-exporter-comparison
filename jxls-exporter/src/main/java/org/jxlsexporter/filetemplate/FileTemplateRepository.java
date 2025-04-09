package org.jxlsexporter.filetemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FileTemplateRepository extends JpaRepository<FileTemplate, Long> {
    FileTemplate findByCode(String code);
}
