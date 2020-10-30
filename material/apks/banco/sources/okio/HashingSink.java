package okio;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class HashingSink extends ForwardingSink {
    @Nullable
    private final MessageDigest a;
    @Nullable
    private final Mac b;

    public static HashingSink md5(Sink sink) {
        return new HashingSink(sink, CommonUtils.MD5_INSTANCE);
    }

    public static HashingSink sha1(Sink sink) {
        return new HashingSink(sink, CommonUtils.SHA1_INSTANCE);
    }

    public static HashingSink sha256(Sink sink) {
        return new HashingSink(sink, "SHA-256");
    }

    public static HashingSink sha512(Sink sink) {
        return new HashingSink(sink, "SHA-512");
    }

    public static HashingSink hmacSha1(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA1");
    }

    public static HashingSink hmacSha256(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA256");
    }

    public static HashingSink hmacSha512(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA512");
    }

    private HashingSink(Sink sink, String str) {
        super(sink);
        try {
            this.a = MessageDigest.getInstance(str);
            this.b = null;
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    private HashingSink(Sink sink, ByteString byteString, String str) {
        super(sink);
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

    public void write(Buffer buffer, long j) {
        Util.a(buffer.b, 0, j);
        Segment segment = buffer.a;
        long j2 = 0;
        while (j2 < j) {
            int min = (int) Math.min(j - j2, (long) (segment.c - segment.b));
            if (this.a != null) {
                this.a.update(segment.a, segment.b, min);
            } else {
                this.b.update(segment.a, segment.b, min);
            }
            long j3 = j2 + ((long) min);
            segment = segment.f;
            j2 = j3;
        }
        super.write(buffer, j);
    }

    public ByteString hash() {
        return ByteString.of(this.a != null ? this.a.digest() : this.b.doFinal());
    }
}
