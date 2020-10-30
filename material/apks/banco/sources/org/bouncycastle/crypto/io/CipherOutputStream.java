package org.bouncycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.modes.AEADBlockCipher;

public class CipherOutputStream extends FilterOutputStream {
    private BufferedBlockCipher a;
    private StreamCipher b;
    private AEADBlockCipher c;
    private final byte[] d = new byte[1];
    private byte[] e;

    public CipherOutputStream(OutputStream outputStream, BufferedBlockCipher bufferedBlockCipher) {
        super(outputStream);
        this.a = bufferedBlockCipher;
    }

    public CipherOutputStream(OutputStream outputStream, StreamCipher streamCipher) {
        super(outputStream);
        this.b = streamCipher;
    }

    public CipherOutputStream(OutputStream outputStream, AEADBlockCipher aEADBlockCipher) {
        super(outputStream);
        this.c = aEADBlockCipher;
    }

    private void a(int i, boolean z) {
        if (z) {
            if (this.a != null) {
                i = this.a.getOutputSize(i);
            } else if (this.c != null) {
                i = this.c.getOutputSize(i);
            }
        } else if (this.a != null) {
            i = this.a.getUpdateOutputSize(i);
        } else if (this.c != null) {
            i = this.c.getUpdateOutputSize(i);
        }
        if (this.e == null || this.e.length < i) {
            this.e = new byte[i];
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004c, code lost:
        if (r1 != null) goto L_0x004f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0053 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r4 = this;
            r0 = 0
            r1 = 1
            r4.a(r0, r1)
            org.bouncycastle.crypto.BufferedBlockCipher r1 = r4.a     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            if (r1 == 0) goto L_0x001b
            org.bouncycastle.crypto.BufferedBlockCipher r1 = r4.a     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            byte[] r2 = r4.e     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            int r1 = r1.doFinal(r2, r0)     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            if (r1 == 0) goto L_0x002e
            java.io.OutputStream r2 = r4.out     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            byte[] r3 = r4.e     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
        L_0x0017:
            r2.write(r3, r0, r1)     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            goto L_0x002e
        L_0x001b:
            org.bouncycastle.crypto.modes.AEADBlockCipher r1 = r4.c     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            if (r1 == 0) goto L_0x002e
            org.bouncycastle.crypto.modes.AEADBlockCipher r1 = r4.c     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            byte[] r2 = r4.e     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            int r1 = r1.doFinal(r2, r0)     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            if (r1 == 0) goto L_0x002e
            java.io.OutputStream r2 = r4.out     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            byte[] r3 = r4.e     // Catch:{ InvalidCipherTextException -> 0x003a, Exception -> 0x0031 }
            goto L_0x0017
        L_0x002e:
            r0 = 0
            r1 = r0
            goto L_0x0042
        L_0x0031:
            r0 = move-exception
            org.bouncycastle.crypto.io.CipherIOException r1 = new org.bouncycastle.crypto.io.CipherIOException
            java.lang.String r2 = "Error closing stream: "
            r1.<init>(r2, r0)
            goto L_0x0042
        L_0x003a:
            r0 = move-exception
            org.bouncycastle.crypto.io.InvalidCipherTextIOException r1 = new org.bouncycastle.crypto.io.InvalidCipherTextIOException
            java.lang.String r2 = "Error finalising cipher data"
            r1.<init>(r2, r0)
        L_0x0042:
            r4.flush()     // Catch:{ IOException -> 0x004b }
            java.io.OutputStream r0 = r4.out     // Catch:{ IOException -> 0x004b }
            r0.close()     // Catch:{ IOException -> 0x004b }
            goto L_0x004f
        L_0x004b:
            r0 = move-exception
            if (r1 != 0) goto L_0x004f
            goto L_0x0050
        L_0x004f:
            r0 = r1
        L_0x0050:
            if (r0 == 0) goto L_0x0053
            throw r0
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.io.CipherOutputStream.close():void");
    }

    public void flush() {
        this.out.flush();
    }

    public void write(int i) {
        byte b2 = (byte) i;
        this.d[0] = b2;
        if (this.b != null) {
            this.out.write(this.b.returnByte(b2));
        } else {
            write(this.d, 0, 1);
        }
    }

    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0014, code lost:
        if (r9 != 0) goto L_0x0016;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002e, code lost:
        if (r9 != 0) goto L_0x0016;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(byte[] r9, int r10, int r11) {
        /*
            r8 = this;
            r0 = 0
            r8.a(r11, r0)
            org.bouncycastle.crypto.BufferedBlockCipher r1 = r8.a
            if (r1 == 0) goto L_0x001e
            org.bouncycastle.crypto.BufferedBlockCipher r2 = r8.a
            byte[] r6 = r8.e
            r7 = 0
            r3 = r9
            r4 = r10
            r5 = r11
            int r9 = r2.processBytes(r3, r4, r5, r6, r7)
            if (r9 == 0) goto L_0x0043
        L_0x0016:
            java.io.OutputStream r10 = r8.out
            byte[] r11 = r8.e
            r10.write(r11, r0, r9)
            return
        L_0x001e:
            org.bouncycastle.crypto.modes.AEADBlockCipher r1 = r8.c
            if (r1 == 0) goto L_0x0031
            org.bouncycastle.crypto.modes.AEADBlockCipher r2 = r8.c
            byte[] r6 = r8.e
            r7 = 0
            r3 = r9
            r4 = r10
            r5 = r11
            int r9 = r2.processBytes(r3, r4, r5, r6, r7)
            if (r9 == 0) goto L_0x0043
            goto L_0x0016
        L_0x0031:
            org.bouncycastle.crypto.StreamCipher r1 = r8.b
            byte[] r5 = r8.e
            r6 = 0
            r2 = r9
            r3 = r10
            r4 = r11
            r1.processBytes(r2, r3, r4, r5, r6)
            java.io.OutputStream r9 = r8.out
            byte[] r10 = r8.e
            r9.write(r10, r0, r11)
        L_0x0043:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.io.CipherOutputStream.write(byte[], int, int):void");
    }
}
