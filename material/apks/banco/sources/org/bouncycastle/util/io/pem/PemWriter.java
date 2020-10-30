package org.bouncycastle.util.io.pem;

import java.io.BufferedWriter;
import java.io.Writer;
import org.bouncycastle.util.encoders.Base64;

public class PemWriter extends BufferedWriter {
    private final int a;
    private char[] b = new char[64];

    public PemWriter(Writer writer) {
        super(writer);
        String property = System.getProperty("line.separator");
        this.a = property != null ? property.length() : 2;
    }

    private void a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN ");
        sb.append(str);
        sb.append("-----");
        write(sb.toString());
        newLine();
    }

    private void a(byte[] bArr) {
        byte[] encode = Base64.encode(bArr);
        int i = 0;
        while (i < encode.length) {
            int i2 = 0;
            while (i2 != this.b.length) {
                int i3 = i + i2;
                if (i3 >= encode.length) {
                    break;
                }
                this.b[i2] = (char) encode[i3];
                i2++;
            }
            write(this.b, 0, i2);
            newLine();
            i += this.b.length;
        }
    }

    private void b(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----END ");
        sb.append(str);
        sb.append("-----");
        write(sb.toString());
        newLine();
    }

    public int getOutputSize(PemObject pemObject) {
        int length = ((pemObject.getType().length() + 10 + this.a) * 2) + 6 + 4;
        if (!pemObject.getHeaders().isEmpty()) {
            for (PemHeader pemHeader : pemObject.getHeaders()) {
                length += pemHeader.getName().length() + ": ".length() + pemHeader.getValue().length() + this.a;
            }
            length += this.a;
        }
        int length2 = ((pemObject.getContent().length + 2) / 3) * 4;
        return length + length2 + ((((length2 + 64) - 1) / 64) * this.a);
    }

    public void writeObject(PemObjectGenerator pemObjectGenerator) {
        PemObject generate = pemObjectGenerator.generate();
        a(generate.getType());
        if (!generate.getHeaders().isEmpty()) {
            for (PemHeader pemHeader : generate.getHeaders()) {
                write(pemHeader.getName());
                write(": ");
                write(pemHeader.getValue());
                newLine();
            }
            newLine();
        }
        a(generate.getContent());
        b(generate.getType());
    }
}
