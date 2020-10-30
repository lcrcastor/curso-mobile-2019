package org.bouncycastle.crypto.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class DESExample {
    private boolean a = true;
    private PaddedBufferedBlockCipher b = null;
    private BufferedInputStream c = null;
    private BufferedOutputStream d = null;
    private byte[] e = null;

    public DESExample() {
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x007a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DESExample(java.lang.String r6, java.lang.String r7, java.lang.String r8, boolean r9) {
        /*
            r5 = this;
            r5.<init>()
            r0 = 1
            r5.a = r0
            r1 = 0
            r5.b = r1
            r5.c = r1
            r5.d = r1
            r5.e = r1
            r5.a = r9
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ FileNotFoundException -> 0x001e }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x001e }
            r3.<init>(r6)     // Catch:{ FileNotFoundException -> 0x001e }
            r2.<init>(r3)     // Catch:{ FileNotFoundException -> 0x001e }
            r5.c = r2     // Catch:{ FileNotFoundException -> 0x001e }
            goto L_0x003c
        L_0x001e:
            java.io.PrintStream r2 = java.lang.System.err
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Input file not found ["
            r3.append(r4)
            r3.append(r6)
            java.lang.String r6 = "]"
            r3.append(r6)
            java.lang.String r6 = r3.toString()
            r2.println(r6)
            java.lang.System.exit(r0)
        L_0x003c:
            java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0049 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0049 }
            r2.<init>(r7)     // Catch:{ IOException -> 0x0049 }
            r6.<init>(r2)     // Catch:{ IOException -> 0x0049 }
            r5.d = r6     // Catch:{ IOException -> 0x0049 }
            goto L_0x0067
        L_0x0049:
            java.io.PrintStream r6 = java.lang.System.err
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Output file not created ["
            r2.append(r3)
            r2.append(r7)
            java.lang.String r7 = "]"
            r2.append(r7)
            java.lang.String r7 = r2.toString()
            r6.println(r7)
            java.lang.System.exit(r0)
        L_0x0067:
            r6 = 0
            if (r9 == 0) goto L_0x00be
            java.security.SecureRandom r7 = new java.security.SecureRandom     // Catch:{ Exception -> 0x0079 }
            r7.<init>()     // Catch:{ Exception -> 0x0079 }
            java.lang.String r9 = "www.bouncycastle.org"
            byte[] r9 = r9.getBytes()     // Catch:{ Exception -> 0x007a }
            r7.setSeed(r9)     // Catch:{ Exception -> 0x007a }
            goto L_0x0084
        L_0x0079:
            r7 = r1
        L_0x007a:
            java.io.PrintStream r9 = java.lang.System.err     // Catch:{ IOException -> 0x00b4 }
            java.lang.String r1 = "Hmmm, no SHA1PRNG, you need the Sun implementation"
            r9.println(r1)     // Catch:{ IOException -> 0x00b4 }
            java.lang.System.exit(r0)     // Catch:{ IOException -> 0x00b4 }
        L_0x0084:
            org.bouncycastle.crypto.KeyGenerationParameters r9 = new org.bouncycastle.crypto.KeyGenerationParameters     // Catch:{ IOException -> 0x00b4 }
            r1 = 192(0xc0, float:2.69E-43)
            r9.<init>(r7, r1)     // Catch:{ IOException -> 0x00b4 }
            org.bouncycastle.crypto.generators.DESedeKeyGenerator r7 = new org.bouncycastle.crypto.generators.DESedeKeyGenerator     // Catch:{ IOException -> 0x00b4 }
            r7.<init>()     // Catch:{ IOException -> 0x00b4 }
            r7.init(r9)     // Catch:{ IOException -> 0x00b4 }
            byte[] r7 = r7.generateKey()     // Catch:{ IOException -> 0x00b4 }
            r5.e = r7     // Catch:{ IOException -> 0x00b4 }
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x00b4 }
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00b4 }
            r9.<init>(r8)     // Catch:{ IOException -> 0x00b4 }
            r7.<init>(r9)     // Catch:{ IOException -> 0x00b4 }
            byte[] r9 = r5.e     // Catch:{ IOException -> 0x00b4 }
            byte[] r9 = org.bouncycastle.util.encoders.Hex.encode(r9)     // Catch:{ IOException -> 0x00b4 }
            int r1 = r9.length     // Catch:{ IOException -> 0x00b4 }
            r7.write(r9, r6, r1)     // Catch:{ IOException -> 0x00b4 }
            r7.flush()     // Catch:{ IOException -> 0x00b4 }
            r7.close()     // Catch:{ IOException -> 0x00b4 }
            return
        L_0x00b4:
            java.io.PrintStream r6 = java.lang.System.err
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r9 = "Could not decryption create key file ["
            goto L_0x00e1
        L_0x00be:
            java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x00d8 }
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00d8 }
            r9.<init>(r8)     // Catch:{ IOException -> 0x00d8 }
            r7.<init>(r9)     // Catch:{ IOException -> 0x00d8 }
            int r9 = r7.available()     // Catch:{ IOException -> 0x00d8 }
            byte[] r1 = new byte[r9]     // Catch:{ IOException -> 0x00d8 }
            r7.read(r1, r6, r9)     // Catch:{ IOException -> 0x00d8 }
            byte[] r6 = org.bouncycastle.util.encoders.Hex.decode(r1)     // Catch:{ IOException -> 0x00d8 }
            r5.e = r6     // Catch:{ IOException -> 0x00d8 }
            return
        L_0x00d8:
            java.io.PrintStream r6 = java.lang.System.err
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r9 = "Decryption key file not found, or not valid ["
        L_0x00e1:
            r7.append(r9)
            r7.append(r8)
            java.lang.String r8 = "]"
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.println(r7)
            java.lang.System.exit(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.examples.DESExample.<init>(java.lang.String, java.lang.String, java.lang.String, boolean):void");
    }

    private void a() {
        this.b = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
        if (this.a) {
            a(this.e);
        } else {
            b(this.e);
        }
        try {
            this.c.close();
            this.d.flush();
            this.d.close();
        } catch (IOException unused) {
        }
    }

    private void a(byte[] bArr) {
        this.b.init(true, new KeyParameter(bArr));
        byte[] bArr2 = new byte[47];
        byte[] bArr3 = new byte[this.b.getOutputSize(47)];
        while (true) {
            try {
                int read = this.c.read(bArr2, 0, 47);
                if (read > 0) {
                    int processBytes = this.b.processBytes(bArr2, 0, read, bArr3, 0);
                    if (processBytes > 0) {
                        byte[] encode = Hex.encode(bArr3, 0, processBytes);
                        this.d.write(encode, 0, encode.length);
                        this.d.write(10);
                    }
                } else {
                    try {
                        break;
                    } catch (CryptoException unused) {
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        int doFinal = this.b.doFinal(bArr3, 0);
        if (doFinal > 0) {
            byte[] encode2 = Hex.encode(bArr3, 0, doFinal);
            this.d.write(encode2, 0, encode2.length);
            this.d.write(10);
        }
    }

    private void b(byte[] bArr) {
        this.b.init(false, new KeyParameter(bArr));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.c));
        byte[] bArr2 = null;
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    byte[] decode = Hex.decode(readLine);
                    bArr2 = new byte[this.b.getOutputSize(decode.length)];
                    int processBytes = this.b.processBytes(decode, 0, decode.length, bArr2, 0);
                    if (processBytes > 0) {
                        this.d.write(bArr2, 0, processBytes);
                    }
                } else {
                    try {
                        break;
                    } catch (CryptoException unused) {
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        int doFinal = this.b.doFinal(bArr2, 0);
        if (doFinal > 0) {
            this.d.write(bArr2, 0, doFinal);
        }
    }

    public static void main(String[] strArr) {
        boolean z = true;
        if (strArr.length < 2) {
            DESExample dESExample = new DESExample();
            PrintStream printStream = System.err;
            StringBuilder sb = new StringBuilder();
            sb.append("Usage: java ");
            sb.append(dESExample.getClass().getName());
            sb.append(" infile outfile [keyfile]");
            printStream.println(sb.toString());
            System.exit(1);
        }
        String str = "deskey.dat";
        String str2 = strArr[0];
        String str3 = strArr[1];
        if (strArr.length > 2) {
            str = strArr[2];
            z = false;
        }
        new DESExample(str2, str3, str, z).a();
    }
}
