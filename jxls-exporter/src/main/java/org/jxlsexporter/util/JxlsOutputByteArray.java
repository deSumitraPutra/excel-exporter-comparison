package org.jxlsexporter.util;

import lombok.RequiredArgsConstructor;
import org.jxls.builder.JxlsOutput;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@RequiredArgsConstructor
public class JxlsOutputByteArray implements JxlsOutput {
    private final ByteArrayOutputStream outputStream;

    @Override
    public OutputStream getOutputStream() {
        return this.outputStream;
    }
}
