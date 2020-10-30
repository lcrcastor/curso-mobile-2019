package io.fabric.sdk.android.services.common;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueueFile implements Closeable {
    private static final Logger b = Logger.getLogger(QueueFile.class.getName());
    int a;
    /* access modifiers changed from: private */
    public final RandomAccessFile c;
    private int d;
    private Element e;
    private Element f;
    private final byte[] g = new byte[16];

    static class Element {
        static final Element a = new Element(0, 0);
        final int b;
        final int c;

        Element(int i, int i2) {
            this.b = i;
            this.c = i2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName());
            sb.append("[");
            sb.append("position = ");
            sb.append(this.b);
            sb.append(", length = ");
            sb.append(this.c);
            sb.append("]");
            return sb.toString();
        }
    }

    final class ElementInputStream extends InputStream {
        private int b;
        private int c;

        private ElementInputStream(Element element) {
            this.b = QueueFile.this.b(element.b + 4);
            this.c = element.c;
        }

        public int read(byte[] bArr, int i, int i2) {
            QueueFile.b(bArr, "buffer");
            if ((i | i2) < 0 || i2 > bArr.length - i) {
                throw new ArrayIndexOutOfBoundsException();
            } else if (this.c <= 0) {
                return -1;
            } else {
                if (i2 > this.c) {
                    i2 = this.c;
                }
                QueueFile.this.b(this.b, bArr, i, i2);
                this.b = QueueFile.this.b(this.b + i2);
                this.c -= i2;
                return i2;
            }
        }

        public int read() {
            if (this.c == 0) {
                return -1;
            }
            QueueFile.this.c.seek((long) this.b);
            int read = QueueFile.this.c.read();
            this.b = QueueFile.this.b(this.b + 1);
            this.c--;
            return read;
        }
    }

    public interface ElementReader {
        void read(InputStream inputStream, int i);
    }

    public QueueFile(File file) {
        if (!file.exists()) {
            a(file);
        }
        this.c = b(file);
        a();
    }

    private static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >> 24);
        bArr[i + 1] = (byte) (i2 >> 16);
        bArr[i + 2] = (byte) (i2 >> 8);
        bArr[i + 3] = (byte) i2;
    }

    private static void a(byte[] bArr, int... iArr) {
        int i = 0;
        for (int a2 : iArr) {
            a(bArr, i, a2);
            i += 4;
        }
    }

    private static int a(byte[] bArr, int i) {
        return ((bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) + ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) + ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 3] & UnsignedBytes.MAX_VALUE);
    }

    private void a() {
        this.c.seek(0);
        this.c.readFully(this.g);
        this.a = a(this.g, 0);
        if (((long) this.a) > this.c.length()) {
            StringBuilder sb = new StringBuilder();
            sb.append("File is truncated. Expected length: ");
            sb.append(this.a);
            sb.append(", Actual length: ");
            sb.append(this.c.length());
            throw new IOException(sb.toString());
        }
        this.d = a(this.g, 4);
        int a2 = a(this.g, 8);
        int a3 = a(this.g, 12);
        this.e = a(a2);
        this.f = a(a3);
    }

    private void a(int i, int i2, int i3, int i4) {
        a(this.g, i, i2, i3, i4);
        this.c.seek(0);
        this.c.write(this.g);
    }

    private Element a(int i) {
        if (i == 0) {
            return Element.a;
        }
        this.c.seek((long) i);
        return new Element(i, this.c.readInt());
    }

    /* JADX INFO: finally extract failed */
    private static void a(File file) {
        StringBuilder sb = new StringBuilder();
        sb.append(file.getPath());
        sb.append(".tmp");
        File file2 = new File(sb.toString());
        RandomAccessFile b2 = b(file2);
        try {
            b2.setLength(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
            b2.seek(0);
            byte[] bArr = new byte[16];
            a(bArr, 4096, 0, 0, 0);
            b2.write(bArr);
            b2.close();
            if (!file2.renameTo(file)) {
                throw new IOException("Rename failed!");
            }
        } catch (Throwable th) {
            b2.close();
            throw th;
        }
    }

    private static RandomAccessFile b(File file) {
        return new RandomAccessFile(file, "rwd");
    }

    /* access modifiers changed from: private */
    public int b(int i) {
        return i < this.a ? i : (i + 16) - this.a;
    }

    private void a(int i, byte[] bArr, int i2, int i3) {
        int b2 = b(i);
        if (b2 + i3 <= this.a) {
            this.c.seek((long) b2);
            this.c.write(bArr, i2, i3);
            return;
        }
        int i4 = this.a - b2;
        this.c.seek((long) b2);
        this.c.write(bArr, i2, i4);
        this.c.seek(16);
        this.c.write(bArr, i2 + i4, i3 - i4);
    }

    /* access modifiers changed from: private */
    public void b(int i, byte[] bArr, int i2, int i3) {
        int b2 = b(i);
        if (b2 + i3 <= this.a) {
            this.c.seek((long) b2);
            this.c.readFully(bArr, i2, i3);
            return;
        }
        int i4 = this.a - b2;
        this.c.seek((long) b2);
        this.c.readFully(bArr, i2, i4);
        this.c.seek(16);
        this.c.readFully(bArr, i2 + i4, i3 - i4);
    }

    public void add(byte[] bArr) {
        add(bArr, 0, bArr.length);
    }

    public synchronized void add(byte[] bArr, int i, int i2) {
        int i3;
        b(bArr, "buffer");
        if ((i | i2) >= 0) {
            if (i2 <= bArr.length - i) {
                c(i2);
                boolean isEmpty = isEmpty();
                if (isEmpty) {
                    i3 = 16;
                } else {
                    i3 = b(this.f.b + 4 + this.f.c);
                }
                Element element = new Element(i3, i2);
                a(this.g, 0, i2);
                a(element.b, this.g, 0, 4);
                a(element.b + 4, bArr, i, i2);
                a(this.a, this.d + 1, isEmpty ? element.b : this.e.b, element.b);
                this.f = element;
                this.d++;
                if (isEmpty) {
                    this.e = this.f;
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public int usedBytes() {
        if (this.d == 0) {
            return 16;
        }
        if (this.f.b >= this.e.b) {
            return (this.f.b - this.e.b) + 4 + this.f.c + 16;
        }
        return (((this.f.b + 4) + this.f.c) + this.a) - this.e.b;
    }

    private int b() {
        return this.a - usedBytes();
    }

    public synchronized boolean isEmpty() {
        return this.d == 0;
    }

    private void c(int i) {
        int i2 = i + 4;
        int b2 = b();
        if (b2 < i2) {
            int i3 = this.a;
            do {
                b2 += i3;
                i3 <<= 1;
            } while (b2 < i2);
            d(i3);
            int b3 = b(this.f.b + 4 + this.f.c);
            if (b3 < this.e.b) {
                FileChannel channel = this.c.getChannel();
                channel.position((long) this.a);
                long j = (long) (b3 - 4);
                if (channel.transferTo(16, j, channel) != j) {
                    throw new AssertionError("Copied insufficient number of bytes!");
                }
            }
            if (this.f.b < this.e.b) {
                int i4 = (this.a + this.f.b) - 16;
                a(i3, this.d, this.e.b, i4);
                this.f = new Element(i4, this.f.c);
            } else {
                a(i3, this.d, this.e.b, this.f.b);
            }
            this.a = i3;
        }
    }

    private void d(int i) {
        this.c.setLength((long) i);
        this.c.getChannel().force(true);
    }

    public synchronized byte[] peek() {
        if (isEmpty()) {
            return null;
        }
        int i = this.e.c;
        byte[] bArr = new byte[i];
        b(this.e.b + 4, bArr, 0, i);
        return bArr;
    }

    public synchronized void peek(ElementReader elementReader) {
        if (this.d > 0) {
            elementReader.read(new ElementInputStream(this.e), this.e.c);
        }
    }

    public synchronized void forEach(ElementReader elementReader) {
        int i = this.e.b;
        for (int i2 = 0; i2 < this.d; i2++) {
            Element a2 = a(i);
            elementReader.read(new ElementInputStream(a2), a2.c);
            i = b(a2.b + 4 + a2.c);
        }
    }

    /* access modifiers changed from: private */
    public static <T> T b(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public synchronized int size() {
        return this.d;
    }

    public synchronized void remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else if (this.d == 1) {
            clear();
        } else {
            int b2 = b(this.e.b + 4 + this.e.c);
            b(b2, this.g, 0, 4);
            int a2 = a(this.g, 0);
            a(this.a, this.d - 1, b2, this.f.b);
            this.d--;
            this.e = new Element(b2, a2);
        }
    }

    public synchronized void clear() {
        a(4096, 0, 0, 0);
        this.d = 0;
        this.e = Element.a;
        this.f = Element.a;
        if (this.a > 4096) {
            d(4096);
        }
        this.a = 4096;
    }

    public synchronized void close() {
        this.c.close();
    }

    public boolean hasSpaceFor(int i, int i2) {
        return (usedBytes() + 4) + i <= i2;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append('[');
        sb.append("fileLength=");
        sb.append(this.a);
        sb.append(", size=");
        sb.append(this.d);
        sb.append(", first=");
        sb.append(this.e);
        sb.append(", last=");
        sb.append(this.f);
        sb.append(", element lengths=[");
        try {
            forEach(new ElementReader() {
                boolean a = true;

                public void read(InputStream inputStream, int i) {
                    if (this.a) {
                        this.a = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(i);
                }
            });
        } catch (IOException e2) {
            b.log(Level.WARNING, "read error", e2);
        }
        sb.append("]]");
        return sb.toString();
    }
}
