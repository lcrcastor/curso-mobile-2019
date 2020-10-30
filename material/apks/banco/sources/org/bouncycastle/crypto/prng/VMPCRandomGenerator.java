package org.bouncycastle.crypto.prng;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.util.Pack;

public class VMPCRandomGenerator implements RandomGenerator {
    private byte a = 0;
    private byte[] b = {-69, 44, 98, Ascii.DEL, -75, -86, -44, Ascii.CR, -127, -2, -78, -126, -53, -96, -95, 8, Ascii.CAN, 113, 86, -24, 73, 2, Ascii.DLE, -60, -34, 53, -91, -20, UnsignedBytes.MAX_POWER_OF_TWO, Ascii.DC2, -72, 105, -38, 47, 117, -52, -94, 9, 54, 3, 97, 45, -3, -32, -35, 5, 67, -112, -83, -56, -31, -81, 87, -101, 76, -40, 81, -82, 80, -123, 60, 10, -28, -13, -100, 38, 35, 83, -55, -125, -105, 70, -79, -103, 100, 49, 119, -43, Ascii.GS, -42, 120, -67, 94, -80, -118, 34, 56, -8, 104, 43, 42, -59, -45, -9, PSSSigner.TRAILER_IMPLICIT, 111, -33, 4, -27, -107, 62, 37, -122, -90, Ascii.VT, -113, -15, 36, Ascii.SO, -41, SignedBytes.MAX_POWER_OF_TWO, -77, -49, 126, 6, Ascii.NAK, -102, 77, Ascii.FS, -93, -37, 50, -110, 88, 17, 39, -12, 89, -48, 78, 106, Ascii.ETB, 91, -84, -1, 7, -64, 101, 121, -4, -57, -51, 118, 66, 93, -25, 58, 52, 122, 48, 40, Ascii.SI, 115, 1, -7, -47, -46, Ascii.EM, -23, -111, -71, 90, -19, 65, 109, -76, -61, -98, -65, 99, -6, Ascii.US, 51, 96, 71, -119, -16, -106, Ascii.SUB, 95, -109, 61, 55, 75, -39, -88, -63, Ascii.ESC, -10, 57, -117, -73, Ascii.FF, 32, -50, -120, 110, -74, 116, -114, -115, Ascii.SYN, 41, -14, -121, -11, -21, 112, -29, -5, 85, -97, -58, 68, 74, 69, 125, -30, 107, 92, 108, 102, -87, -116, -18, -124, 19, -89, Ascii.RS, -99, -36, 103, 72, -70, 46, -26, -92, -85, 124, -108, 0, 33, -17, -22, -66, -54, 114, 79, 82, -104, 63, -62, Ascii.DC4, 123, 59, 84};
    private byte c = -66;

    public void addSeedMaterial(long j) {
        addSeedMaterial(Pack.longToBigEndian(j));
    }

    public void addSeedMaterial(byte[] bArr) {
        for (byte b2 : bArr) {
            this.c = this.b[(this.c + this.b[this.a & UnsignedBytes.MAX_VALUE] + b2) & 255];
            byte b3 = this.b[this.a & UnsignedBytes.MAX_VALUE];
            this.b[this.a & UnsignedBytes.MAX_VALUE] = this.b[this.c & UnsignedBytes.MAX_VALUE];
            this.b[this.c & UnsignedBytes.MAX_VALUE] = b3;
            this.a = (byte) ((this.a + 1) & 255);
        }
    }

    public void nextBytes(byte[] bArr) {
        nextBytes(bArr, 0, bArr.length);
    }

    public void nextBytes(byte[] bArr, int i, int i2) {
        synchronized (this.b) {
            int i3 = i2 + i;
            while (i != i3) {
                try {
                    this.c = this.b[(this.c + this.b[this.a & UnsignedBytes.MAX_VALUE]) & 255];
                    bArr[i] = this.b[(this.b[this.b[this.c & UnsignedBytes.MAX_VALUE] & UnsignedBytes.MAX_VALUE] + 1) & 255];
                    byte b2 = this.b[this.a & UnsignedBytes.MAX_VALUE];
                    this.b[this.a & UnsignedBytes.MAX_VALUE] = this.b[this.c & UnsignedBytes.MAX_VALUE];
                    this.b[this.c & UnsignedBytes.MAX_VALUE] = b2;
                    this.a = (byte) ((this.a + 1) & 255);
                    i++;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
