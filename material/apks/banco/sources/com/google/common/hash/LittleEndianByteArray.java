package com.google.common.hash;

import com.google.common.base.Ascii;
import com.google.common.primitives.Longs;
import com.google.common.primitives.UnsignedBytes;
import java.nio.ByteOrder;
import sun.misc.Unsafe;

final class LittleEndianByteArray {
    static final /* synthetic */ boolean a = true;
    private static final LittleEndianBytes b;

    enum JavaLittleEndianBytes implements LittleEndianBytes {
        INSTANCE {
            public long a(byte[] bArr, int i) {
                return Longs.fromBytes(bArr[i + 7], bArr[i + 6], bArr[i + 5], bArr[i + 4], bArr[i + 3], bArr[i + 2], bArr[i + 1], bArr[i]);
            }
        }
    }

    interface LittleEndianBytes {
        long a(byte[] bArr, int i);
    }

    enum UnsafeByteArray implements LittleEndianBytes {
        UNSAFE_LITTLE_ENDIAN {
            public long a(byte[] bArr, int i) {
                return UnsafeByteArray.c.getLong(bArr, ((long) i) + ((long) UnsafeByteArray.d));
            }
        },
        UNSAFE_BIG_ENDIAN {
            public long a(byte[] bArr, int i) {
                return Long.reverseBytes(UnsafeByteArray.c.getLong(bArr, ((long) i) + ((long) UnsafeByteArray.d)));
            }
        };
        
        /* access modifiers changed from: private */
        public static final Unsafe c = null;
        /* access modifiers changed from: private */
        public static final int d = 0;

        static {
            c = c();
            d = c.arrayBaseOffset(byte[].class);
            if (c.arrayIndexScale(byte[].class) != 1) {
                throw new AssertionError();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0010, code lost:
            return (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new com.google.common.hash.LittleEndianByteArray.UnsafeByteArray.AnonymousClass3());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0011, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
            throw new java.lang.RuntimeException("Could not initialize intrinsics", r0.getCause());
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0005 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static sun.misc.Unsafe c() {
            /*
                sun.misc.Unsafe r0 = sun.misc.Unsafe.getUnsafe()     // Catch:{ SecurityException -> 0x0005 }
                return r0
            L_0x0005:
                com.google.common.hash.LittleEndianByteArray$UnsafeByteArray$3 r0 = new com.google.common.hash.LittleEndianByteArray$UnsafeByteArray$3     // Catch:{ PrivilegedActionException -> 0x0011 }
                r0.<init>()     // Catch:{ PrivilegedActionException -> 0x0011 }
                java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)     // Catch:{ PrivilegedActionException -> 0x0011 }
                sun.misc.Unsafe r0 = (sun.misc.Unsafe) r0     // Catch:{ PrivilegedActionException -> 0x0011 }
                return r0
            L_0x0011:
                r0 = move-exception
                java.lang.RuntimeException r1 = new java.lang.RuntimeException
                java.lang.String r2 = "Could not initialize intrinsics"
                java.lang.Throwable r0 = r0.getCause()
                r1.<init>(r2, r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.LittleEndianByteArray.UnsafeByteArray.c():sun.misc.Unsafe");
        }
    }

    static long a(byte[] bArr, int i) {
        if (a || bArr.length >= i + 8) {
            return b.a(bArr, i);
        }
        throw new AssertionError();
    }

    static int b(byte[] bArr, int i) {
        return ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    static {
        LittleEndianBytes littleEndianBytes = JavaLittleEndianBytes.INSTANCE;
        try {
            String property = System.getProperty("os.arch");
            if ("amd64".equals(property) || "aarch64".equals(property)) {
                littleEndianBytes = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN) ? UnsafeByteArray.UNSAFE_LITTLE_ENDIAN : UnsafeByteArray.UNSAFE_BIG_ENDIAN;
            }
        } catch (Throwable unused) {
        }
        b = littleEndianBytes;
    }

    private LittleEndianByteArray() {
    }
}
