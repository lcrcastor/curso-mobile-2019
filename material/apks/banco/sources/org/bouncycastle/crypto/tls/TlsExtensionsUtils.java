package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import org.bouncycastle.util.Integers;

public class TlsExtensionsUtils {
    public static final Integer EXT_encrypt_then_mac = Integers.valueOf(22);
    public static final Integer EXT_heartbeat = Integers.valueOf(15);
    public static final Integer EXT_max_fragment_length = Integers.valueOf(1);
    public static final Integer EXT_server_name = Integers.valueOf(0);
    public static final Integer EXT_status_request = Integers.valueOf(5);
    public static final Integer EXT_truncated_hmac = Integers.valueOf(4);

    private static boolean a(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        } else if (bArr.length == 0) {
            return true;
        } else {
            throw new TlsFatalAlert(47);
        }
    }

    public static void addEncryptThenMACExtension(Hashtable hashtable) {
        hashtable.put(EXT_encrypt_then_mac, createEncryptThenMACExtension());
    }

    public static void addHeartbeatExtension(Hashtable hashtable, HeartbeatExtension heartbeatExtension) {
        hashtable.put(EXT_heartbeat, createHeartbeatExtension(heartbeatExtension));
    }

    public static void addMaxFragmentLengthExtension(Hashtable hashtable, short s) {
        hashtable.put(EXT_max_fragment_length, createMaxFragmentLengthExtension(s));
    }

    public static void addServerNameExtension(Hashtable hashtable, ServerNameList serverNameList) {
        hashtable.put(EXT_server_name, createServerNameExtension(serverNameList));
    }

    public static void addStatusRequestExtension(Hashtable hashtable, CertificateStatusRequest certificateStatusRequest) {
        hashtable.put(EXT_status_request, createStatusRequestExtension(certificateStatusRequest));
    }

    public static void addTruncatedHMacExtension(Hashtable hashtable) {
        hashtable.put(EXT_truncated_hmac, createTruncatedHMacExtension());
    }

    public static byte[] createEmptyExtensionData() {
        return TlsUtils.EMPTY_BYTES;
    }

    public static byte[] createEncryptThenMACExtension() {
        return createEmptyExtensionData();
    }

    public static byte[] createHeartbeatExtension(HeartbeatExtension heartbeatExtension) {
        if (heartbeatExtension == null) {
            throw new TlsFatalAlert(80);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        heartbeatExtension.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] createMaxFragmentLengthExtension(short s) {
        if (!MaxFragmentLength.isValid(s)) {
            throw new TlsFatalAlert(80);
        }
        return new byte[]{(byte) s};
    }

    public static byte[] createServerNameExtension(ServerNameList serverNameList) {
        if (serverNameList == null) {
            throw new TlsFatalAlert(80);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        serverNameList.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] createStatusRequestExtension(CertificateStatusRequest certificateStatusRequest) {
        if (certificateStatusRequest == null) {
            throw new TlsFatalAlert(80);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        certificateStatusRequest.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] createTruncatedHMacExtension() {
        return createEmptyExtensionData();
    }

    public static Hashtable ensureExtensionsInitialised(Hashtable hashtable) {
        return hashtable == null ? new Hashtable() : hashtable;
    }

    public static HeartbeatExtension getHeartbeatExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_heartbeat);
        if (extensionData == null) {
            return null;
        }
        return readHeartbeatExtension(extensionData);
    }

    public static short getMaxFragmentLengthExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_max_fragment_length);
        if (extensionData == null) {
            return -1;
        }
        return readMaxFragmentLengthExtension(extensionData);
    }

    public static ServerNameList getServerNameExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_server_name);
        if (extensionData == null) {
            return null;
        }
        return readServerNameExtension(extensionData);
    }

    public static CertificateStatusRequest getStatusRequestExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_status_request);
        if (extensionData == null) {
            return null;
        }
        return readStatusRequestExtension(extensionData);
    }

    public static boolean hasEncryptThenMACExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_encrypt_then_mac);
        if (extensionData == null) {
            return false;
        }
        return readEncryptThenMACExtension(extensionData);
    }

    public static boolean hasTruncatedHMacExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_truncated_hmac);
        if (extensionData == null) {
            return false;
        }
        return readTruncatedHMacExtension(extensionData);
    }

    public static boolean readEncryptThenMACExtension(byte[] bArr) {
        return a(bArr);
    }

    public static HeartbeatExtension readHeartbeatExtension(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        HeartbeatExtension parse = HeartbeatExtension.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        return parse;
    }

    public static short readMaxFragmentLengthExtension(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        } else if (bArr.length != 1) {
            throw new TlsFatalAlert(50);
        } else {
            short s = (short) bArr[0];
            if (MaxFragmentLength.isValid(s)) {
                return s;
            }
            throw new TlsFatalAlert(47);
        }
    }

    public static ServerNameList readServerNameExtension(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ServerNameList parse = ServerNameList.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        return parse;
    }

    public static CertificateStatusRequest readStatusRequestExtension(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        CertificateStatusRequest parse = CertificateStatusRequest.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        return parse;
    }

    public static boolean readTruncatedHMacExtension(byte[] bArr) {
        return a(bArr);
    }
}
