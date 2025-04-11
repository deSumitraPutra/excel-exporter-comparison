package org.jxlsexporter.util;

import lombok.RequiredArgsConstructor;
import org.jxls.builder.JxlsOutput;

import java.io.OutputStream;

@RequiredArgsConstructor
public class JxlsOutputStream implements JxlsOutput {
    private final OutputStream outputStream;

    @Override
    public OutputStream getOutputStream() {
        return this.outputStream;
    }
}
