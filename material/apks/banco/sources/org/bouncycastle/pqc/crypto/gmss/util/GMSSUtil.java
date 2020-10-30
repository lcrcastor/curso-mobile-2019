package org.bouncycastle.pqc.crypto.gmss.util;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.PrintStream;

public class GMSSUtil {
    public int bytesToIntLittleEndian(byte[] bArr) {
        return ((bArr[3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[0] & UnsignedBytes.MAX_VALUE) | ((bArr[1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    public int bytesToIntLittleEndian(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        byte b = (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << 8);
        return ((bArr[i3 + 1] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | b | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    public byte[] concatenateArray(byte[][] bArr) {
        byte[] bArr2 = new byte[(bArr.length * bArr[0].length)];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            System.arraycopy(bArr[i2], 0, bArr2, i, bArr[i2].length);
            i += bArr[i2].length;
        }
        return bArr2;
    }

    public int getLog(int i) {
        int i2 = 1;
        int i3 = 2;
        while (i3 < i) {
            i3 <<= 1;
            i2++;
        }
        return i2;
    }

    public byte[] intToBytesLittleEndian(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public void printArray(String str, byte[] bArr) {
        System.out.println(str);
        int i = 0;
        for (byte append : bArr) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("; ");
            sb.append(append);
            printStream.println(sb.toString());
            i++;
        }
    }

    public void printArray(String str, byte[][] bArr) {
        System.out.println(str);
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = i2;
            for (int i4 = 0; i4 < bArr[0].length; i4++) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(i3);
                sb.append("; ");
                sb.append(bArr[i][i4]);
                printStream.println(sb.toString());
                i3++;
            }
            i++;
            i2 = i3;
        }
    }

    public boolean testPowerOfTwo(int i) {
        int i2 = 1;
        while (i2 < i) {
            i2 <<= 1;
        }
        return i == i2;
    }
}
