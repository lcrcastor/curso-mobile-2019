package com.grab.android.vending.billing.util;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.base.Ascii;

public class Base64 {
    public static final boolean DECODE = false;
    public static final boolean ENCODE = true;
    static final /* synthetic */ boolean a = true;
    private static final byte[] b = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] d = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Ascii.VT, Ascii.FF, Ascii.CR, Ascii.SO, Ascii.SI, Ascii.DLE, 17, Ascii.DC2, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, -9, -9, -9, -9, -9, -9, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};
    private static final byte[] e = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, Ascii.VT, Ascii.FF, Ascii.CR, Ascii.SO, Ascii.SI, Ascii.DLE, 17, Ascii.DC2, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, -9, -9, -9, -9, 63, -9, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};

    private Base64() {
    }

    private static byte[] a(byte[] bArr, int i, int i2, byte[] bArr2, int i3, byte[] bArr3) {
        int i4 = 0;
        int i5 = (i2 > 0 ? (bArr[i] << Ascii.CAN) >>> 8 : 0) | (i2 > 1 ? (bArr[i + 1] << Ascii.CAN) >>> 16 : 0);
        if (i2 > 2) {
            i4 = (bArr[i + 2] << Ascii.CAN) >>> 24;
        }
        int i6 = i5 | i4;
        switch (i2) {
            case 1:
                bArr2[i3] = bArr3[i6 >>> 18];
                bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                bArr2[i3 + 2] = 61;
                bArr2[i3 + 3] = 61;
                return bArr2;
            case 2:
                bArr2[i3] = bArr3[i6 >>> 18];
                bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i6 >>> 6) & 63];
                bArr2[i3 + 3] = 61;
                return bArr2;
            case 3:
                bArr2[i3] = bArr3[i6 >>> 18];
                bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i6 >>> 6) & 63];
                bArr2[i3 + 3] = bArr3[i6 & 63];
                return bArr2;
            default:
                return bArr2;
        }
    }

    public static String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length, b, true);
    }

    public static String encodeWebSafe(byte[] bArr, boolean z) {
        return encode(bArr, 0, bArr.length, c, z);
    }

    public static String encode(byte[] bArr, int i, int i2, byte[] bArr2, boolean z) {
        byte[] encode = encode(bArr, i, i2, bArr2, (int) SubsamplingScaleImageView.TILE_SIZE_AUTO);
        int length = encode.length;
        while (!z && length > 0 && encode[length - 1] == 61) {
            length--;
        }
        return new String(encode, 0, length);
    }

    public static byte[] encode(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = ((i2 + 2) / 3) * 4;
        byte[] bArr3 = new byte[(i4 + (i4 / i3))];
        int i5 = i2 - 2;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < i5) {
            int i9 = ((bArr[i6 + i] << Ascii.CAN) >>> 8) | ((bArr[(i6 + 1) + i] << Ascii.CAN) >>> 16) | ((bArr[(i6 + 2) + i] << Ascii.CAN) >>> 24);
            bArr3[i7] = bArr2[i9 >>> 18];
            int i10 = i7 + 1;
            bArr3[i10] = bArr2[(i9 >>> 12) & 63];
            bArr3[i7 + 2] = bArr2[(i9 >>> 6) & 63];
            bArr3[i7 + 3] = bArr2[i9 & 63];
            i8 += 4;
            if (i8 == i3) {
                bArr3[i7 + 4] = 10;
                i8 = 0;
            } else {
                i10 = i7;
            }
            i6 += 3;
            i7 = i10 + 4;
        }
        if (i6 < i2) {
            a(bArr, i6 + i, i2 - i6, bArr3, i7, bArr2);
            if (i8 + 4 == i3) {
                bArr3[i7 + 4] = 10;
                i7++;
            }
            i7 += 4;
        }
        if (a || i7 == bArr3.length) {
            return bArr3;
        }
        throw new AssertionError();
    }

    private static int a(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3) {
        int i3 = i + 2;
        if (bArr[i3] == 61) {
            bArr2[i2] = (byte) ((((bArr3[bArr[i + 1]] << Ascii.CAN) >>> 12) | ((bArr3[bArr[i]] << Ascii.CAN) >>> 6)) >>> 16);
            return 1;
        }
        int i4 = i + 3;
        if (bArr[i4] == 61) {
            int i5 = ((bArr3[bArr[i3]] << Ascii.CAN) >>> 18) | ((bArr3[bArr[i + 1]] << Ascii.CAN) >>> 12) | ((bArr3[bArr[i]] << Ascii.CAN) >>> 6);
            bArr2[i2] = (byte) (i5 >>> 16);
            bArr2[i2 + 1] = (byte) (i5 >>> 8);
            return 2;
        }
        int i6 = ((bArr3[bArr[i4]] << Ascii.CAN) >>> 24) | ((bArr3[bArr[i + 1]] << Ascii.CAN) >>> 12) | ((bArr3[bArr[i]] << Ascii.CAN) >>> 6) | ((bArr3[bArr[i3]] << Ascii.CAN) >>> 18);
        bArr2[i2] = (byte) (i6 >> 16);
        bArr2[i2 + 1] = (byte) (i6 >> 8);
        bArr2[i2 + 2] = (byte) i6;
        return 3;
    }

    public static byte[] decode(String str) {
        byte[] bytes = str.getBytes();
        return decode(bytes, 0, bytes.length);
    }

    public static byte[] decodeWebSafe(String str) {
        byte[] bytes = str.getBytes();
        return decodeWebSafe(bytes, 0, bytes.length);
    }

    public static byte[] decode(byte[] bArr) {
        return decode(bArr, 0, bArr.length);
    }

    public static byte[] decodeWebSafe(byte[] bArr) {
        return decodeWebSafe(bArr, 0, bArr.length);
    }

    public static byte[] decode(byte[] bArr, int i, int i2) {
        return decode(bArr, i, i2, d);
    }

    public static byte[] decodeWebSafe(byte[] bArr, int i, int i2) {
        return decode(bArr, i, i2, e);
    }

    public static byte[] decode(byte[] bArr, int i, int i2, byte[] bArr2) {
        int i3;
        int i4 = i2;
        byte[] bArr3 = bArr2;
        byte[] bArr4 = new byte[(((i4 * 3) / 4) + 2)];
        byte[] bArr5 = new byte[4];
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            if (i5 >= i4) {
                break;
            }
            int i8 = i5 + i;
            byte b2 = (byte) (bArr[i8] & Ascii.DEL);
            byte b3 = bArr3[b2];
            if (b3 >= -5) {
                if (b3 >= -1) {
                    if (b2 == 61) {
                        int i9 = i4 - i5;
                        byte b4 = (byte) (bArr[(i4 - 1) + i] & Ascii.DEL);
                        if (i6 == 0 || i6 == 1) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("invalid padding byte '=' at byte offset ");
                            sb.append(i5);
                            throw new Base64DecoderException(sb.toString());
                        } else if ((i6 == 3 && i9 > 2) || (i6 == 4 && i9 > 1)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("padding byte '=' falsely signals end of encoded value at offset ");
                            sb2.append(i5);
                            throw new Base64DecoderException(sb2.toString());
                        } else if (b4 != 61 && b4 != 10) {
                            throw new Base64DecoderException("encoded value has invalid trailing byte");
                        }
                    } else {
                        int i10 = i6 + 1;
                        bArr5[i6] = b2;
                        if (i10 == 4) {
                            i7 += a(bArr5, 0, bArr4, i7, bArr3);
                            i6 = 0;
                        } else {
                            i6 = i10;
                        }
                    }
                }
                i5++;
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Bad Base64 input character at ");
                sb3.append(i5);
                sb3.append(": ");
                sb3.append(bArr[i8]);
                sb3.append("(decimal)");
                throw new Base64DecoderException(sb3.toString());
            }
        }
        if (i6 == 0) {
            i3 = 0;
        } else if (i6 == 1) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("single trailing character at offset ");
            sb4.append(i4 - 1);
            throw new Base64DecoderException(sb4.toString());
        } else {
            bArr5[i6] = 61;
            i3 = 0;
            i7 += a(bArr5, 0, bArr4, i7, bArr3);
        }
        byte[] bArr6 = new byte[i7];
        System.arraycopy(bArr4, i3, bArr6, i3, i7);
        return bArr6;
    }
}
