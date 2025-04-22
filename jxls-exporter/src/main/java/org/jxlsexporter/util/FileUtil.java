package org.jxlsexporter.util;

import org.jxls.builder.JxlsStreaming;
import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class FileUtil {
    public static HttpHeaders generateExcelHeaders(String filename) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", String.format("attachment; filename=%s;", filename));
        httpHeaders.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpHeaders.add("Access-Control-Expose-Headers", "Content-Disposition");

        return httpHeaders;
    }

    public static void processTemplate(OutputStream outputStream, byte[] template, HashMap<String, Object> data) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(template)) {
            JxlsOutputStream jxlsOutputStream = new JxlsOutputStream(outputStream);
            JxlsPoiTemplateFillerBuilder.newInstance()
                .withStreaming(JxlsStreaming.AUTO_DETECT)
                .withTemplate(inputStream)
                .buildAndFill(data, jxlsOutputStream);
        }
    }
}
