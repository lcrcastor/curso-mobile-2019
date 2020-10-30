package org.bouncycastle.asn1;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import org.bouncycastle.asn1.eac.CertificateBody;

class StreamUtil {
    private static final long a = Runtime.getRuntime().maxMemory();

    StreamUtil() {
    }

    static int a(int i) {
        int i2 = 1;
        if (i > 127) {
            int i3 = 1;
            while (true) {
                i >>>= 8;
                if (i == 0) {
                    break;
                }
                i3++;
            }
            for (int i4 = (i3 - 1) * 8; i4 >= 0; i4 -= 8) {
                i2++;
            }
        }
        return i2;
    }

    static int a(InputStream inputStream) {
        if (inputStream instanceof LimitedInputStream) {
            return ((LimitedInputStream) inputStream).a();
        }
        if (inputStream instanceof ASN1InputStream) {
            return ((ASN1InputStream) inputStream).a();
        }
        if (inputStream instanceof ByteArrayInputStream) {
            return ((ByteArrayInputStream) inputStream).available();
        }
        if (inputStream instanceof FileInputStream) {
            try {
                FileChannel channel = ((FileInputStream) inputStream).getChannel();
                long size = channel != null ? channel.size() : 2147483647L;
                if (size < 2147483647L) {
                    return (int) size;
                }
            } catch (IOException unused) {
            }
        }
        return a > 2147483647L ? SubsamplingScaleImageView.TILE_SIZE_AUTO : (int) a;
    }

    static int b(int i) {
        int i2 = 1;
        if (i >= 31) {
            if (i < 128) {
                return 2;
            }
            byte[] bArr = new byte[5];
            int length = bArr.length - 1;
            bArr[length] = (byte) (i & CertificateBody.profileType);
            do {
                i >>= 7;
                length--;
                bArr[length] = (byte) ((i & CertificateBody.profileType) | 128);
            } while (i > 127);
            i2 = 1 + (bArr.length - length);
        }
        return i2;
    }
}
