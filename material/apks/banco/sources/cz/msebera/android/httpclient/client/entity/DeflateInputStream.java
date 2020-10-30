package cz.msebera.android.httpclient.client.entity;

import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class DeflateInputStream extends InputStream {
    private InputStream a;

    static class DeflateStream extends InflaterInputStream {
        private boolean a = false;

        public DeflateStream(InputStream inputStream, Inflater inflater) {
            super(inputStream, inflater);
        }

        public void close() {
            if (!this.a) {
                this.a = true;
                this.inf.end();
                super.close();
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:24|25) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0063, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r1.unread(r0, 0, r10);
        r9.a = new cz.msebera.android.httpclient.client.entity.DeflateInputStream.DeflateStream(r1, new java.util.zip.Inflater(true));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0078, code lost:
        r5.end();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007b, code lost:
        throw r10;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0065 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DeflateInputStream(java.io.InputStream r10) {
        /*
            r9 = this;
            r9.<init>()
            r0 = 6
            byte[] r0 = new byte[r0]
            java.io.PushbackInputStream r1 = new java.io.PushbackInputStream
            int r2 = r0.length
            r1.<init>(r10, r2)
            int r10 = r1.read(r0)
            r2 = -1
            if (r10 != r2) goto L_0x001b
            java.io.IOException r10 = new java.io.IOException
            java.lang.String r0 = "Unable to read the response"
            r10.<init>(r0)
            throw r10
        L_0x001b:
            r3 = 1
            byte[] r4 = new byte[r3]
            java.util.zip.Inflater r5 = new java.util.zip.Inflater
            r5.<init>()
        L_0x0023:
            r6 = 0
            int r7 = r5.inflate(r4)     // Catch:{ DataFormatException -> 0x0065 }
            if (r7 != 0) goto L_0x0049
            boolean r8 = r5.finished()     // Catch:{ DataFormatException -> 0x0065 }
            if (r8 == 0) goto L_0x0038
            java.io.IOException r2 = new java.io.IOException     // Catch:{ DataFormatException -> 0x0065 }
            java.lang.String r4 = "Unable to read the response"
            r2.<init>(r4)     // Catch:{ DataFormatException -> 0x0065 }
            throw r2     // Catch:{ DataFormatException -> 0x0065 }
        L_0x0038:
            boolean r8 = r5.needsDictionary()     // Catch:{ DataFormatException -> 0x0065 }
            if (r8 == 0) goto L_0x003f
            goto L_0x0049
        L_0x003f:
            boolean r7 = r5.needsInput()     // Catch:{ DataFormatException -> 0x0065 }
            if (r7 == 0) goto L_0x0023
            r5.setInput(r0)     // Catch:{ DataFormatException -> 0x0065 }
            goto L_0x0023
        L_0x0049:
            if (r7 != r2) goto L_0x0053
            java.io.IOException r2 = new java.io.IOException     // Catch:{ DataFormatException -> 0x0065 }
            java.lang.String r4 = "Unable to read the response"
            r2.<init>(r4)     // Catch:{ DataFormatException -> 0x0065 }
            throw r2     // Catch:{ DataFormatException -> 0x0065 }
        L_0x0053:
            r1.unread(r0, r6, r10)     // Catch:{ DataFormatException -> 0x0065 }
            cz.msebera.android.httpclient.client.entity.DeflateInputStream$DeflateStream r2 = new cz.msebera.android.httpclient.client.entity.DeflateInputStream$DeflateStream     // Catch:{ DataFormatException -> 0x0065 }
            java.util.zip.Inflater r4 = new java.util.zip.Inflater     // Catch:{ DataFormatException -> 0x0065 }
            r4.<init>()     // Catch:{ DataFormatException -> 0x0065 }
            r2.<init>(r1, r4)     // Catch:{ DataFormatException -> 0x0065 }
            r9.a = r2     // Catch:{ DataFormatException -> 0x0065 }
            goto L_0x0074
        L_0x0063:
            r10 = move-exception
            goto L_0x0078
        L_0x0065:
            r1.unread(r0, r6, r10)     // Catch:{ all -> 0x0063 }
            cz.msebera.android.httpclient.client.entity.DeflateInputStream$DeflateStream r10 = new cz.msebera.android.httpclient.client.entity.DeflateInputStream$DeflateStream     // Catch:{ all -> 0x0063 }
            java.util.zip.Inflater r0 = new java.util.zip.Inflater     // Catch:{ all -> 0x0063 }
            r0.<init>(r3)     // Catch:{ all -> 0x0063 }
            r10.<init>(r1, r0)     // Catch:{ all -> 0x0063 }
            r9.a = r10     // Catch:{ all -> 0x0063 }
        L_0x0074:
            r5.end()
            return
        L_0x0078:
            r5.end()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.client.entity.DeflateInputStream.<init>(java.io.InputStream):void");
    }

    public int read() {
        return this.a.read();
    }

    public int read(byte[] bArr) {
        return this.a.read(bArr);
    }

    public int read(byte[] bArr, int i, int i2) {
        return this.a.read(bArr, i, i2);
    }

    public long skip(long j) {
        return this.a.skip(j);
    }

    public int available() {
        return this.a.available();
    }

    public void mark(int i) {
        this.a.mark(i);
    }

    public void reset() {
        this.a.reset();
    }

    public boolean markSupported() {
        return this.a.markSupported();
    }

    public void close() {
        this.a.close();
    }
}
