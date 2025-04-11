package org.jxlsexporter.util;

import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class FileUtil {
    public static byte[] generateByteArrayFile(byte[] template, Map<String, Object> data) throws IOException {
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
