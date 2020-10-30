package okio;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ByteString implements Serializable, Comparable<ByteString> {
    public static final ByteString EMPTY = of(new byte[0]);
    static final char[] a = {TarjetasConstants.ULT_NUM_AMEX, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final long serialVersionUID = 1;
    final byte[] b;
    transient int c;
    transient String d;

    ByteString(byte[] bArr) {
        this.b = bArr;
    }

    public static ByteString of(byte... bArr) {
        if (bArr != null) {
            return new ByteString((byte[]) bArr.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    public static ByteString of(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("data == null");
        }
        Util.a((long) bArr.length, (long) i, (long) i2);
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new ByteString(bArr2);
    }

    public static ByteString of(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("data == null");
        }
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.get(bArr);
        return new ByteString(bArr);
    }

    public static ByteString encodeUtf8(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        ByteString byteString = new ByteString(str.getBytes(Util.a));
        byteString.d = str;
        return byteString;
    }

    public static ByteString encodeString(String str, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        } else if (charset != null) {
            return new ByteString(str.getBytes(charset));
        } else {
            throw new IllegalArgumentException("charset == null");
        }
    }

    public String utf8() {
        String str = this.d;
        if (str != null) {
            return str;
        }
        String str2 = new String(this.b, Util.a);
        this.d = str2;
        return str2;
    }

    public String string(Charset charset) {
        if (charset != null) {
            return new String(this.b, charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    public String base64() {
        return Base64.a(this.b);
    }

    public ByteString md5() {
        return a(CommonUtils.MD5_INSTANCE);
    }

    public ByteString sha1() {
        return a(CommonUtils.SHA1_INSTANCE);
    }

    public ByteString sha256() {
        return a("SHA-256");
    }

    public ByteString sha512() {
        return a("SHA-512");
    }

    private ByteString a(String str) {
        try {
            return of(MessageDigest.getInstance(str).digest(this.b));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public ByteString hmacSha1(ByteString byteString) {
        return a("HmacSHA1", byteString);
    }

    public ByteString hmacSha256(ByteString byteString) {
        return a("HmacSHA256", byteString);
    }

    public ByteString hmacSha512(ByteString byteString) {
        return a("HmacSHA512", byteString);
    }

    private ByteString a(String str, ByteString byteString) {
        try {
            Mac instance = Mac.getInstance(str);
            instance.init(new SecretKeySpec(byteString.toByteArray(), str));
            return of(instance.doFinal(this.b));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (InvalidKeyException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public String base64Url() {
        return Base64.b(this.b);
    }

    @Nullable
    public static ByteString decodeBase64(String str) {
        if (str == null) {
            throw new IllegalArgumentException("base64 == null");
        }
        byte[] a2 = Base64.a(str);
        if (a2 != null) {
            return new ByteString(a2);
        }
        return null;
    }

    public String hex() {
        byte[] bArr;
        char[] cArr = new char[(this.b.length * 2)];
        int i = 0;
        for (byte b2 : this.b) {
            int i2 = i + 1;
            cArr[i] = a[(b2 >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = a[b2 & Ascii.SI];
        }
        return new String(cArr);
    }

    public static ByteString decodeHex(String str) {
        if (str == null) {
            throw new IllegalArgumentException("hex == null");
        } else if (str.length() % 2 != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected hex string: ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        } else {
            byte[] bArr = new byte[(str.length() / 2)];
            for (int i = 0; i < bArr.length; i++) {
                int i2 = i * 2;
                bArr[i] = (byte) ((a(str.charAt(i2)) << 4) + a(str.charAt(i2 + 1)));
            }
            return of(bArr);
        }
    }

    private static int a(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - TarjetasConstants.ULT_NUM_AMEX;
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return (c2 - 'a') + 10;
        }
        if (c2 >= 'A' && c2 <= 'F') {
            return (c2 - 'A') + 10;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected hex digit: ");
        sb.append(c2);
        throw new IllegalArgumentException(sb.toString());
    }

    public static ByteString read(InputStream inputStream, int i) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        } else if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount < 0: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else {
            byte[] bArr = new byte[i];
            int i2 = 0;
            while (i2 < i) {
                int read = inputStream.read(bArr, i2, i - i2);
                if (read == -1) {
                    throw new EOFException();
                }
                i2 += read;
            }
            return new ByteString(bArr);
        }
    }

    public ByteString toAsciiLowercase() {
        int i = 0;
        while (i < this.b.length) {
            byte b2 = this.b[i];
            if (b2 < 65 || b2 > 90) {
                i++;
            } else {
                byte[] bArr = (byte[]) this.b.clone();
                bArr[i] = (byte) (b2 + 32);
                for (int i2 = i + 1; i2 < bArr.length; i2++) {
                    byte b3 = bArr[i2];
                    if (b3 >= 65 && b3 <= 90) {
                        bArr[i2] = (byte) (b3 + 32);
                    }
                }
                return new ByteString(bArr);
            }
        }
        return this;
    }

    public ByteString toAsciiUppercase() {
        int i = 0;
        while (i < this.b.length) {
            byte b2 = this.b[i];
            if (b2 < 97 || b2 > 122) {
                i++;
            } else {
                byte[] bArr = (byte[]) this.b.clone();
                bArr[i] = (byte) (b2 - 32);
                for (int i2 = i + 1; i2 < bArr.length; i2++) {
                    byte b3 = bArr[i2];
                    if (b3 >= 97 && b3 <= 122) {
                        bArr[i2] = (byte) (b3 - 32);
                    }
                }
                return new ByteString(bArr);
            }
        }
        return this;
    }

    public ByteString substring(int i) {
        return substring(i, this.b.length);
    }

    public ByteString substring(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0");
        } else if (i2 > this.b.length) {
            StringBuilder sb = new StringBuilder();
            sb.append("endIndex > length(");
            sb.append(this.b.length);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        } else {
            int i3 = i2 - i;
            if (i3 < 0) {
                throw new IllegalArgumentException("endIndex < beginIndex");
            } else if (i == 0 && i2 == this.b.length) {
                return this;
            } else {
                byte[] bArr = new byte[i3];
                System.arraycopy(this.b, i, bArr, 0, i3);
                return new ByteString(bArr);
            }
        }
    }

    public byte getByte(int i) {
        return this.b[i];
    }

    public int size() {
        return this.b.length;
    }

    public byte[] toByteArray() {
        return (byte[]) this.b.clone();
    }

    /* access modifiers changed from: 0000 */
    public byte[] a() {
        return this.b;
    }

    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(this.b).asReadOnlyBuffer();
    }

    public void write(OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        outputStream.write(this.b);
    }

    /* access modifiers changed from: 0000 */
    public void a(Buffer buffer) {
        buffer.write(this.b, 0, this.b.length);
    }

    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        return byteString.rangeEquals(i2, this.b, i, i3);
    }

    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        return i >= 0 && i <= this.b.length - i3 && i2 >= 0 && i2 <= bArr.length - i3 && Util.a(this.b, i, bArr, i2, i3);
    }

    public final boolean startsWith(ByteString byteString) {
        return rangeEquals(0, byteString, 0, byteString.size());
    }

    public final boolean startsWith(byte[] bArr) {
        return rangeEquals(0, bArr, 0, bArr.length);
    }

    public final boolean endsWith(ByteString byteString) {
        return rangeEquals(size() - byteString.size(), byteString, 0, byteString.size());
    }

    public final boolean endsWith(byte[] bArr) {
        return rangeEquals(size() - bArr.length, bArr, 0, bArr.length);
    }

    public final int indexOf(ByteString byteString) {
        return indexOf(byteString.a(), 0);
    }

    public final int indexOf(ByteString byteString, int i) {
        return indexOf(byteString.a(), i);
    }

    public final int indexOf(byte[] bArr) {
        return indexOf(bArr, 0);
    }

    public int indexOf(byte[] bArr, int i) {
        int length = this.b.length - bArr.length;
        for (int max = Math.max(i, 0); max <= length; max++) {
            if (Util.a(this.b, max, bArr, 0, bArr.length)) {
                return max;
            }
        }
        return -1;
    }

    public final int lastIndexOf(ByteString byteString) {
        return lastIndexOf(byteString.a(), size());
    }

    public final int lastIndexOf(ByteString byteString, int i) {
        return lastIndexOf(byteString.a(), i);
    }

    public final int lastIndexOf(byte[] bArr) {
        return lastIndexOf(bArr, size());
    }

    public int lastIndexOf(byte[] bArr, int i) {
        for (int min = Math.min(i, this.b.length - bArr.length); min >= 0; min--) {
            if (Util.a(this.b, min, bArr, 0, bArr.length)) {
                return min;
            }
        }
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
        if (r5.rangeEquals(0, r4.b, 0, r4.b.length) != false) goto L_0x0021;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof okio.ByteString
            r2 = 0
            if (r1 == 0) goto L_0x0020
            okio.ByteString r5 = (okio.ByteString) r5
            int r1 = r5.size()
            byte[] r3 = r4.b
            int r3 = r3.length
            if (r1 != r3) goto L_0x0020
            byte[] r1 = r4.b
            byte[] r3 = r4.b
            int r3 = r3.length
            boolean r5 = r5.rangeEquals(r2, r1, r2, r3)
            if (r5 == 0) goto L_0x0020
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.ByteString.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = this.c;
        if (i != 0) {
            return i;
        }
        int hashCode = Arrays.hashCode(this.b);
        this.c = hashCode;
        return hashCode;
    }

    public int compareTo(ByteString byteString) {
        int size = size();
        int size2 = byteString.size();
        int min = Math.min(size, size2);
        int i = 0;
        while (true) {
            int i2 = -1;
            if (i < min) {
                byte b2 = getByte(i) & UnsignedBytes.MAX_VALUE;
                byte b3 = byteString.getByte(i) & UnsignedBytes.MAX_VALUE;
                if (b2 == b3) {
                    i++;
                } else {
                    if (b2 >= b3) {
                        i2 = 1;
                    }
                    return i2;
                }
            } else if (size == size2) {
                return 0;
            } else {
                if (size >= size2) {
                    i2 = 1;
                }
                return i2;
            }
        }
    }

    public String toString() {
        String str;
        String str2;
        if (this.b.length == 0) {
            return "[size=0]";
        }
        String utf8 = utf8();
        int a2 = a(utf8, 64);
        if (a2 == -1) {
            if (this.b.length <= 64) {
                StringBuilder sb = new StringBuilder();
                sb.append("[hex=");
                sb.append(hex());
                sb.append("]");
                str2 = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("[size=");
                sb2.append(this.b.length);
                sb2.append(" hex=");
                sb2.append(substring(0, 64).hex());
                sb2.append("…]");
                str2 = sb2.toString();
            }
            return str2;
        }
        String replace = utf8.substring(0, a2).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
        if (a2 < utf8.length()) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("[size=");
            sb3.append(this.b.length);
            sb3.append(" text=");
            sb3.append(replace);
            sb3.append("…]");
            str = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("[text=");
            sb4.append(replace);
            sb4.append("]");
            str = sb4.toString();
        }
        return str;
    }

    static int a(String str, int i) {
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            if (i3 == i) {
                return i2;
            }
            int codePointAt = str.codePointAt(i2);
            if ((Character.isISOControl(codePointAt) && codePointAt != 10 && codePointAt != 13) || codePointAt == 65533) {
                return -1;
            }
            i3++;
            i2 += Character.charCount(codePointAt);
        }
        return str.length();
    }

    private void readObject(ObjectInputStream objectInputStream) {
        ByteString read = read(objectInputStream, objectInputStream.readInt());
        try {
            Field declaredField = ByteString.class.getDeclaredField("b");
            declaredField.setAccessible(true);
            declaredField.set(this, read.b);
        } catch (NoSuchFieldException unused) {
            throw new AssertionError();
        } catch (IllegalAccessException unused2) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.writeInt(this.b.length);
        objectOutputStream.write(this.b);
    }
}
