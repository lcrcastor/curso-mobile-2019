package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.annotation.Immutable;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

@Immutable
class IOUtils {
    IOUtils() {
    }

    static void a(HttpEntity httpEntity) {
        if (httpEntity != null && httpEntity.isStreaming()) {
            InputStream content = httpEntity.getContent();
            if (content != null) {
                content.close();
            }
        }
    }

    static void a(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[2048];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    static void a(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    static void b(InputStream inputStream, OutputStream outputStream) {
        try {
            a(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            a((Closeable) inputStream);
            a((Closeable) outputStream);
            throw e;
        }
    }

    static void a(File file, File file2) {
        FileChannel channel;
        FileChannel channel2;
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(file2, "rw");
        try {
            channel = randomAccessFile.getChannel();
            channel2 = randomAccessFile2.getChannel();
            channel.transferTo(0, randomAccessFile.length(), channel2);
            channel.close();
            channel2.close();
            randomAccessFile.close();
            randomAccessFile2.close();
        } catch (IOException e) {
            a((Closeable) channel);
            a((Closeable) channel2);
            throw e;
        } catch (IOException e2) {
            a((Closeable) randomAccessFile);
            a((Closeable) randomAccessFile2);
            throw e2;
        }
    }
}
