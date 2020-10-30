package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSource;
import okio.InflaterSource;
import okio.Okio;
import okio.Source;

class NameValueBlockReader {
    private final InflaterSource a;
    /* access modifiers changed from: private */
    public int b;
    private final BufferedSource c = Okio.buffer((Source) this.a);

    public NameValueBlockReader(BufferedSource bufferedSource) {
        this.a = new InflaterSource((Source) new ForwardingSource(bufferedSource) {
            public long read(Buffer buffer, long j) {
                if (NameValueBlockReader.this.b == 0) {
                    return -1;
                }
                long read = super.read(buffer, Math.min(j, (long) NameValueBlockReader.this.b));
                if (read == -1) {
                    return -1;
                }
                NameValueBlockReader.this.b = (int) (((long) NameValueBlockReader.this.b) - read);
                return read;
            }
        }, (Inflater) new Inflater() {
            public int inflate(byte[] bArr, int i, int i2) {
                int inflate = super.inflate(bArr, i, i2);
                if (inflate != 0 || !needsDictionary()) {
                    return inflate;
                }
                setDictionary(Spdy3.a);
                return super.inflate(bArr, i, i2);
            }
        });
    }

    public List<Header> a(int i) {
        this.b += i;
        int readInt = this.c.readInt();
        if (readInt < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("numberOfPairs < 0: ");
            sb.append(readInt);
            throw new IOException(sb.toString());
        } else if (readInt > 1024) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("numberOfPairs > 1024: ");
            sb2.append(readInt);
            throw new IOException(sb2.toString());
        } else {
            ArrayList arrayList = new ArrayList(readInt);
            for (int i2 = 0; i2 < readInt; i2++) {
                ByteString asciiLowercase = b().toAsciiLowercase();
                ByteString b2 = b();
                if (asciiLowercase.size() == 0) {
                    throw new IOException("name.size == 0");
                }
                arrayList.add(new Header(asciiLowercase, b2));
            }
            c();
            return arrayList;
        }
    }

    private ByteString b() {
        return this.c.readByteString((long) this.c.readInt());
    }

    private void c() {
        if (this.b > 0) {
            this.a.refill();
            if (this.b != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("compressedLimit > 0: ");
                sb.append(this.b);
                throw new IOException(sb.toString());
            }
        }
    }

    public void a() {
        this.c.close();
    }
}
