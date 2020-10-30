package org.bouncycastle.util;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

public final class Strings {
    public static char[] asCharArray(byte[] bArr) {
        char[] cArr = new char[bArr.length];
        for (int i = 0; i != cArr.length; i++) {
            cArr[i] = (char) (bArr[i] & UnsignedBytes.MAX_VALUE);
        }
        return cArr;
    }

    public static String fromByteArray(byte[] bArr) {
        return new String(asCharArray(bArr));
    }

    public static String fromUTF8ByteArray(byte[] bArr) {
        char c;
        int i;
        byte b;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < bArr.length) {
            i4++;
            if ((bArr[i3] & 240) == 240) {
                i4++;
                i3 += 4;
            } else {
                i3 = (bArr[i3] & 224) == 224 ? i3 + 3 : (bArr[i3] & 192) == 192 ? i3 + 2 : i3 + 1;
            }
        }
        char[] cArr = new char[i4];
        int i5 = 0;
        while (i2 < bArr.length) {
            if ((bArr[i2] & 240) == 240) {
                int i6 = (((((bArr[i2] & 3) << Ascii.DC2) | ((bArr[i2 + 1] & 63) << Ascii.FF)) | ((bArr[i2 + 2] & 63) << 6)) | (bArr[i2 + 3] & 63)) - Ascii.NUL;
                c = (char) ((i6 & 1023) | 56320);
                int i7 = i5 + 1;
                cArr[i5] = (char) (55296 | (i6 >> 10));
                i2 += 4;
                i5 = i7;
            } else if ((bArr[i2] & 224) == 224) {
                c = (char) (((bArr[i2] & Ascii.SI) << Ascii.FF) | ((bArr[i2 + 1] & 63) << 6) | (bArr[i2 + 2] & 63));
                i2 += 3;
            } else {
                if ((bArr[i2] & 208) == 208) {
                    i = (bArr[i2] & Ascii.US) << 6;
                    b = bArr[i2 + 1];
                } else if ((bArr[i2] & 192) == 192) {
                    i = (bArr[i2] & Ascii.US) << 6;
                    b = bArr[i2 + 1];
                } else {
                    c = (char) (bArr[i2] & UnsignedBytes.MAX_VALUE);
                    i2++;
                }
                c = (char) (i | (b & 63));
                i2 += 2;
            }
            int i8 = i5 + 1;
            cArr[i5] = c;
            i5 = i8;
        }
        return new String(cArr);
    }

    public static String[] split(String str, char c) {
        int i;
        Vector vector = new Vector();
        boolean z = true;
        while (true) {
            if (!z) {
                break;
            }
            int indexOf = str.indexOf(c);
            if (indexOf > 0) {
                vector.addElement(str.substring(0, indexOf));
                str = str.substring(indexOf + 1);
            } else {
                vector.addElement(str);
                z = false;
            }
        }
        String[] strArr = new String[vector.size()];
        for (i = 0; i != strArr.length; i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    public static int toByteArray(String str, byte[] bArr, int i) {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i + i2] = (byte) str.charAt(i2);
        }
        return length;
    }

    public static byte[] toByteArray(String str) {
        byte[] bArr = new byte[str.length()];
        for (int i = 0; i != bArr.length; i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        return bArr;
    }

    public static byte[] toByteArray(char[] cArr) {
        byte[] bArr = new byte[cArr.length];
        for (int i = 0; i != bArr.length; i++) {
            bArr[i] = (byte) cArr[i];
        }
        return bArr;
    }

    public static String toLowerCase(String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i != charArray.length; i++) {
            char c = charArray[i];
            if ('A' <= c && 'Z' >= c) {
                charArray[i] = (char) ((c - 'A') + 97);
                z = true;
            }
        }
        return z ? new String(charArray) : str;
    }

    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r1v1, types: [int, char] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void toUTF8ByteArray(char[] r5, java.io.OutputStream r6) {
        /*
            r0 = 0
        L_0x0001:
            int r1 = r5.length
            if (r0 >= r1) goto L_0x0075
            char r1 = r5[r0]
            r2 = 128(0x80, float:1.794E-43)
            if (r1 >= r2) goto L_0x000b
            goto L_0x0061
        L_0x000b:
            r3 = 2048(0x800, float:2.87E-42)
            if (r1 >= r3) goto L_0x0017
            int r3 = r1 >> 6
            r3 = r3 | 192(0xc0, float:2.69E-43)
        L_0x0013:
            r6.write(r3)
            goto L_0x005e
        L_0x0017:
            r3 = 55296(0xd800, float:7.7486E-41)
            if (r1 < r3) goto L_0x0065
            r3 = 57343(0xdfff, float:8.0355E-41)
            if (r1 > r3) goto L_0x0065
            int r0 = r0 + 1
            int r3 = r5.length
            if (r0 < r3) goto L_0x002e
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "invalid UTF-16 codepoint"
            r5.<init>(r6)
            throw r5
        L_0x002e:
            char r3 = r5[r0]
            r4 = 56319(0xdbff, float:7.892E-41)
            if (r1 <= r4) goto L_0x003d
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "invalid UTF-16 codepoint"
            r5.<init>(r6)
            throw r5
        L_0x003d:
            r1 = r1 & 1023(0x3ff, float:1.434E-42)
            int r1 = r1 << 10
            r3 = r3 & 1023(0x3ff, float:1.434E-42)
            r1 = r1 | r3
            r3 = 65536(0x10000, float:9.18355E-41)
            int r1 = r1 + r3
            int r3 = r1 >> 18
            r3 = r3 | 240(0xf0, float:3.36E-43)
            r6.write(r3)
            int r3 = r1 >> 12
            r3 = r3 & 63
            r3 = r3 | r2
            r6.write(r3)
            int r3 = r1 >> 6
            r3 = r3 & 63
            r3 = r3 | r2
            r6.write(r3)
        L_0x005e:
            r1 = r1 & 63
            r1 = r1 | r2
        L_0x0061:
            r6.write(r1)
            goto L_0x0072
        L_0x0065:
            int r3 = r1 >> 12
            r3 = r3 | 224(0xe0, float:3.14E-43)
            r6.write(r3)
            int r3 = r1 >> 6
            r3 = r3 & 63
            r3 = r3 | r2
            goto L_0x0013
        L_0x0072:
            int r0 = r0 + 1
            goto L_0x0001
        L_0x0075:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.util.Strings.toUTF8ByteArray(char[], java.io.OutputStream):void");
    }

    public static byte[] toUTF8ByteArray(String str) {
        return toUTF8ByteArray(str.toCharArray());
    }

    public static byte[] toUTF8ByteArray(char[] cArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            toUTF8ByteArray(cArr, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new IllegalStateException("cannot encode string to byte array!");
        }
    }

    public static String toUpperCase(String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i != charArray.length; i++) {
            char c = charArray[i];
            if ('a' <= c && 'z' >= c) {
                charArray[i] = (char) ((c - 'a') + 65);
                z = true;
            }
        }
        return z ? new String(charArray) : str;
    }
}
