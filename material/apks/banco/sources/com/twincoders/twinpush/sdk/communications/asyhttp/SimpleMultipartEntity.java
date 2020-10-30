package com.twincoders.twinpush.sdk.communications.asyhttp;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

class SimpleMultipartEntity implements HttpEntity {
    private static final char[] d = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    ByteArrayOutputStream a = new ByteArrayOutputStream();
    boolean b;
    boolean c;
    private String e = null;

    public Header getContentEncoding() {
        return null;
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return false;
    }

    public boolean isStreaming() {
        return false;
    }

    public SimpleMultipartEntity() {
        this.b = false;
        this.c = false;
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            stringBuffer.append(d[random.nextInt(d.length)]);
        }
        this.e = stringBuffer.toString();
    }

    public void a() {
        if (!this.c) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(this.e);
                sb.append("\r\n");
                byteArrayOutputStream.write(sb.toString().getBytes());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        this.c = true;
    }

    public void b() {
        if (!this.b) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("\r\n--");
                sb.append(this.e);
                sb.append("--\r\n");
                byteArrayOutputStream.write(sb.toString().getBytes());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.b = true;
        }
    }

    public void a(String str, String str2) {
        a();
        try {
            ByteArrayOutputStream byteArrayOutputStream = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append("Content-Disposition: form-data; name=\"");
            sb.append(str);
            sb.append("\"\r\n\r\n");
            byteArrayOutputStream.write(sb.toString().getBytes());
            this.a.write(str2.getBytes());
            ByteArrayOutputStream byteArrayOutputStream2 = this.a;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("\r\n--");
            sb2.append(this.e);
            sb2.append("\r\n");
            byteArrayOutputStream2.write(sb2.toString().getBytes());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2, InputStream inputStream, boolean z) {
        a(str, str2, inputStream, "application/octet-stream", z);
    }

    public void a(String str, String str2, InputStream inputStream, String str3, boolean z) {
        a();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Content-Type: ");
            sb.append(str3);
            sb.append("\r\n");
            String sb2 = sb.toString();
            ByteArrayOutputStream byteArrayOutputStream = this.a;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Content-Disposition: form-data; name=\"");
            sb3.append(str);
            sb3.append("\"; filename=\"");
            sb3.append(str2);
            sb3.append("\"\r\n");
            byteArrayOutputStream.write(sb3.toString().getBytes());
            this.a.write(sb2.getBytes());
            this.a.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                this.a.write(bArr, 0, read);
            }
            if (!z) {
                ByteArrayOutputStream byteArrayOutputStream2 = this.a;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("\r\n--");
                sb4.append(this.e);
                sb4.append("\r\n");
                byteArrayOutputStream2.write(sb4.toString().getBytes());
            }
            this.a.flush();
            try {
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }
    }

    public long getContentLength() {
        b();
        return (long) this.a.toByteArray().length;
    }

    public Header getContentType() {
        StringBuilder sb = new StringBuilder();
        sb.append("multipart/form-data; boundary=");
        sb.append(this.e);
        return new BasicHeader("Content-Type", sb.toString());
    }

    public void writeTo(OutputStream outputStream) {
        outputStream.write(this.a.toByteArray());
    }

    public void consumeContent() {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() {
        return new ByteArrayInputStream(this.a.toByteArray());
    }
}
