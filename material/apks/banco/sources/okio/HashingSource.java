package okio;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class HashingSource extends ForwardingSource {
    private final MessageDigest a;
    private final Mac b;

    public static HashingSource md5(Source source) {
        return new HashingSource(source, CommonUtils.MD5_INSTANCE);
    }

    public static HashingSource sha1(Source source) {
        return new HashingSource(source, CommonUtils.SHA1_INSTANCE);
    }

    public static HashingSource sha256(Source source) {
        return new HashingSource(source, "SHA-256");
    }

    public static HashingSource hmacSha1(Source source, ByteString byteString) {
        return new HashingSource(source, byteString, "HmacSHA1");
    }

    public static HashingSource hmacSha256(Source source, ByteString byteString) {
        return new HashingSource(source, byteString, "HmacSHA256");
    }

    private HashingSource(Source source, String str) {
        super(source);
        try {
            this.a = MessageDigest.getInstance(str);
            this.b = null;
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    private HashingSource(Source source, ByteString byteString, String str) {
        super(source);
        try {
            this.b = Mac.getInstance(str);
            this.b.init(new SecretKeySpec(byteString.toByteArray(), str));
            this.a = null;
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public long read(Buffer buffer, long j) {
        long read = super.read(buffer, j);
        if (read != -1) {
            long j2 = buffer.b - read;
            long j3 = buffer.b;
            Segment segment = buffer.a;
            while (j3 > j2) {
                segment = segment.g;
                j3 -= (long) (segment.c - segment.b);
            }
            while (j3 < buffer.b) {
                int i = (int) ((((long) segment.b) + j2) - j3);
                if (this.a != null) {
                    this.a.update(segment.a, i, segment.c - i);
                } else {
                    this.b.update(segment.a, i, segment.c - i);
                }
                long j4 = j3 + ((long) (segment.c - segment.b));
                segment = segment.f;
                j3 = j4;
                j2 = j3;
            }
        }
        return read;
    }

    public ByteString hash() {
        return ByteString.of(this.a != null ? this.a.digest() : this.b.doFinal());
    }
}
