package org.fastexcelexporter.account;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.fastexcelexporter.filehelper.AccountFileHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;

    public byte[] generateFileAsByteArray(int limit) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.generateToOutputStream(outputStream, limit);

        return outputStream.toByteArray();
    }

    public void generateToResponseStream(HttpServletResponse response, int limit) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        this.generateToOutputStream(outputStream, limit);
    }

    private void generateToOutputStream(OutputStream outputStream, int limit) throws IOException {
        PageRequest pageRequest = PageRequest.of(0, limit);
        Page<Account> allAccounts = this.repository.findAll(pageRequest);
        if (allAccounts.isEmpty()) {
            throw new RuntimeException("No accounts found!");
        }

        AccountFileHelper.generateWorkbook(outputStream, allAccounts.getContent());
    }
}
