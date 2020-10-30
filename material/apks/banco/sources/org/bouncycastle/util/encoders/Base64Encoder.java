package org.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

public class Base64Encoder implements Encoder {
    protected final byte[] decodingTable = new byte[128];
    protected final byte[] encodingTable = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    protected byte padding = 61;

    public Base64Encoder() {
        initialiseDecodingTable();
    }

    private int a(OutputStream outputStream, char c, char c2, char c3, char c4) {
        if (c3 == this.padding) {
            byte b = this.decodingTable[c];
            byte b2 = this.decodingTable[c2];
            if ((b | b2) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b << 2) | (b2 >> 4));
            return 1;
        } else if (c4 == this.padding) {
            byte b3 = this.decodingTable[c];
            byte b4 = this.decodingTable[c2];
            byte b5 = this.decodingTable[c3];
            if ((b3 | b4 | b5) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b3 << 2) | (b4 >> 4));
            outputStream.write((b4 << 4) | (b5 >> 2));
            return 2;
        } else {
            byte b6 = this.decodingTable[c];
            byte b7 = this.decodingTable[c2];
            byte b8 = this.decodingTable[c3];
            byte b9 = this.decodingTable[c4];
            if ((b6 | b7 | b8 | b9) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b6 << 2) | (b7 >> 4));
            outputStream.write((b7 << 4) | (b8 >> 2));
            outputStream.write((b8 << 6) | b9);
            return 3;
        }
    }

    private int a(String str, int i, int i2) {
        while (i < i2 && a(str.charAt(i))) {
            i++;
        }
        return i;
    }

    private int a(byte[] bArr, int i, int i2) {
        while (i < i2 && a((char) bArr[i])) {
            i++;
        }
        return i;
    }

    private boolean a(char c) {
        return c == 10 || c == 13 || c == 9 || c == ' ';
    }

    public int decode(String str, OutputStream outputStream) {
        int length = str.length();
        while (length > 0 && a(str.charAt(length - 1))) {
            length--;
        }
        int i = length - 4;
        int i2 = 0;
        int a = a(str, 0, i);
        while (a < i) {
            int i3 = a + 1;
            byte b = this.decodingTable[str.charAt(a)];
            int a2 = a(str, i3, i);
            int i4 = a2 + 1;
            byte b2 = this.decodingTable[str.charAt(a2)];
            int a3 = a(str, i4, i);
            int i5 = a3 + 1;
            byte b3 = this.decodingTable[str.charAt(a3)];
            int a4 = a(str, i5, i);
            int i6 = a4 + 1;
            byte b4 = this.decodingTable[str.charAt(a4)];
            if ((b | b2 | b3 | b4) < 0) {
                throw new IOException("invalid characters encountered in base64 data");
            }
            outputStream.write((b << 2) | (b2 >> 4));
            outputStream.write((b2 << 4) | (b3 >> 2));
            outputStream.write((b3 << 6) | b4);
            i2 += 3;
            a = a(str, i6, i);
        }
        return i2 + a(outputStream, str.charAt(i), str.charAt(length - 3), str.charAt(length - 2), str.charAt(length - 1));
    }

    public int decode(byte[] bArr, int i, int i2, OutputStream outputStream) {
        int i3 = i2 + i;
        while (i3 > i && a((char) bArr[i3 - 1])) {
            i3--;
        }
        int i4 = i3 - 4;
        int a = a(bArr, i, i4);
        int i5 = 0;
        while (a < i4) {
            int i6 = a + 1;
            byte b = this.decodingTable[bArr[a]];
            int a2 = a(bArr, i6, i4);
            int i7 = a2 + 1;
            byte b2 = this.decodingTable[bArr[a2]];
            int a3 = a(bArr, i7, i4);
            int i8 = a3 + 1;
            byte b3 = this.decodingTable[bArr[a3]];
            int a4 = a(bArr, i8, i4);
            int i9 = a4 + 1;
            byte b4 = this.decodingTable[bArr[a4]];
            if ((b | b2 | b3 | b4) < 0) {
                throw new IOException("invalid characters encountered in base64 data");
            }
            outputStream.write((b << 2) | (b2 >> 4));
            outputStream.write((b2 << 4) | (b3 >> 2));
            outputStream.write((b3 << 6) | b4);
            i5 += 3;
            a = a(bArr, i9, i4);
        }
        return i5 + a(outputStream, (char) bArr[i4], (char) bArr[i3 - 3], (char) bArr[i3 - 2], (char) bArr[i3 - 1]);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x00a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int encode(byte[] r9, int r10, int r11, java.io.OutputStream r12) {
        /*
            r8 = this;
            int r0 = r11 % 3
            int r11 = r11 - r0
            r1 = r10
        L_0x0004:
            int r2 = r10 + r11
            r3 = 4
            if (r1 >= r2) goto L_0x004b
            byte r2 = r9[r1]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r4 = r1 + 1
            byte r4 = r9[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r5 = r1 + 2
            byte r5 = r9[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte[] r6 = r8.encodingTable
            int r7 = r2 >>> 2
            r7 = r7 & 63
            byte r6 = r6[r7]
            r12.write(r6)
            byte[] r6 = r8.encodingTable
            int r2 = r2 << r3
            int r3 = r4 >>> 4
            r2 = r2 | r3
            r2 = r2 & 63
            byte r2 = r6[r2]
            r12.write(r2)
            byte[] r2 = r8.encodingTable
            int r3 = r4 << 2
            int r4 = r5 >>> 6
            r3 = r3 | r4
            r3 = r3 & 63
            byte r2 = r2[r3]
            r12.write(r2)
            byte[] r2 = r8.encodingTable
            r3 = r5 & 63
            byte r2 = r2[r3]
            r12.write(r2)
            int r1 = r1 + 3
            goto L_0x0004
        L_0x004b:
            switch(r0) {
                case 0: goto L_0x009d;
                case 1: goto L_0x007a;
                case 2: goto L_0x004f;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x009d
        L_0x004f:
            byte r10 = r9[r2]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r2 = r2 + 1
            byte r9 = r9[r2]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r1 = r10 >>> 2
            r1 = r1 & 63
            int r10 = r10 << r3
            int r2 = r9 >>> 4
            r10 = r10 | r2
            r10 = r10 & 63
            int r9 = r9 << 2
            r9 = r9 & 63
            byte[] r2 = r8.encodingTable
            byte r1 = r2[r1]
            r12.write(r1)
            byte[] r1 = r8.encodingTable
            byte r10 = r1[r10]
            r12.write(r10)
            byte[] r10 = r8.encodingTable
            byte r9 = r10[r9]
            goto L_0x0095
        L_0x007a:
            byte r9 = r9[r2]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r10 = r9 >>> 2
            r10 = r10 & 63
            int r9 = r9 << r3
            r9 = r9 & 63
            byte[] r1 = r8.encodingTable
            byte r10 = r1[r10]
            r12.write(r10)
            byte[] r10 = r8.encodingTable
            byte r9 = r10[r9]
            r12.write(r9)
            byte r9 = r8.padding
        L_0x0095:
            r12.write(r9)
            byte r9 = r8.padding
            r12.write(r9)
        L_0x009d:
            int r11 = r11 / 3
            int r11 = r11 * 4
            if (r0 != 0) goto L_0x00a4
            r3 = 0
        L_0x00a4:
            int r11 = r11 + r3
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.util.encoders.Base64Encoder.encode(byte[], int, int, java.io.OutputStream):int");
    }

    /* access modifiers changed from: protected */
    public void initialiseDecodingTable() {
        for (int i = 0; i < this.decodingTable.length; i++) {
            this.decodingTable[i] = -1;
        }
        for (int i2 = 0; i2 < this.encodingTable.length; i2++) {
            this.decodingTable[this.encodingTable[i2]] = (byte) i2;
        }
    }
}
