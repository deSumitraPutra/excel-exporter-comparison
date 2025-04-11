package org.jxlsexporter.util;

import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class FileUtil {
    public static HttpHeaders generateExcelHeaders(String filename) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", String.format("attachment; filename=%s;", filename));
        httpHeaders.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpHeaders.add("Access-Control-Expose-Headers", "Content-Disposition");

        return httpHeaders;
    }

    public static byte[] generateByteArrayFile(byte[] template, HashMap<String, Object> data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(template)) {
            JxlsOutputByteArray jxlsOutputByteArray = new JxlsOutputByteArray(outputStream);
            JxlsPoiTemplateFillerBuilder.newInstance()
                .withTemplate(inputStream)
                .buildAndFill(data, jxlsOutputByteArray);
        }

        return outputStream.toByteArray();
    }
}
