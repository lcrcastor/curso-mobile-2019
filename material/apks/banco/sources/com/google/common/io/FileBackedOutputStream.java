package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@GwtIncompatible
@Beta
public final class FileBackedOutputStream extends OutputStream {
    private final int a;
    private final boolean b;
    private final ByteSource c;
    private OutputStream d;
    private MemoryOutput e;
    private File f;

    static class MemoryOutput extends ByteArrayOutputStream {
        private MemoryOutput() {
        }

        /* access modifiers changed from: 0000 */
        public byte[] a() {
            return this.buf;
        }

        /* access modifiers changed from: 0000 */
        public int b() {
            return this.count;
        }
    }

    public FileBackedOutputStream(int i) {
        this(i, false);
    }

    public FileBackedOutputStream(int i, boolean z) {
        this.a = i;
        this.b = z;
        this.e = new MemoryOutput();
        this.d = this.e;
        if (z) {
            this.c = new ByteSource() {
                public InputStream openStream() {
                    return FileBackedOutputStream.this.a();
                }

                /* access modifiers changed from: protected */
                public void finalize() {
                    try {
                        FileBackedOutputStream.this.reset();
                    } catch (Throwable th) {
                        th.printStackTrace(System.err);
                    }
                }
            };
        } else {
            this.c = new ByteSource() {
                public InputStream openStream() {
                    return FileBackedOutputStream.this.a();
                }
            };
        }
    }

    public ByteSource asByteSource() {
        return this.c;
    }

    /* access modifiers changed from: private */
    public synchronized InputStream a() {
        if (this.f != null) {
            return new FileInputStream(this.f);
        }
        return new ByteArrayInputStream(this.e.a(), 0, this.e.b());
    }

    public synchronized void reset() {
        try {
            close();
            if (this.e == null) {
                this.e = new MemoryOutput();
            } else {
                this.e.reset();
            }
            this.d = this.e;
            if (this.f != null) {
                File file = this.f;
                this.f = null;
                if (!file.delete()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Could not delete: ");
                    sb.append(file);
                    throw new IOException(sb.toString());
                }
            }
        } catch (Throwable th) {
            if (this.e == null) {
                this.e = new MemoryOutput();
            } else {
                this.e.reset();
            }
            this.d = this.e;
            if (this.f != null) {
                File file2 = this.f;
                this.f = null;
                if (!file2.delete()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Could not delete: ");
                    sb2.append(file2);
                    throw new IOException(sb2.toString());
                }
            }
            throw th;
        }
    }

    public synchronized void write(int i) {
        a(1);
        this.d.write(i);
    }

    public synchronized void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    public synchronized void write(byte[] bArr, int i, int i2) {
        a(i2);
        this.d.write(bArr, i, i2);
    }

    public synchronized void close() {
        this.d.close();
    }

    public synchronized void flush() {
        this.d.flush();
    }

    private void a(int i) {
        if (this.f == null && this.e.b() + i > this.a) {
            File createTempFile = File.createTempFile("FileBackedOutputStream", null);
            if (this.b) {
                createTempFile.deleteOnExit();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            fileOutputStream.write(this.e.a(), 0, this.e.b());
            fileOutputStream.flush();
            this.d = fileOutputStream;
            this.f = createTempFile;
            this.e = null;
        }
    }
}
