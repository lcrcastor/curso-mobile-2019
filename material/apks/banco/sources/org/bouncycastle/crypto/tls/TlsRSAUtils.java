package org.bouncycastle.crypto.tls;

import com.google.common.primitives.UnsignedBytes;
import java.io.OutputStream;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.Arrays;

public class TlsRSAUtils {
    public static byte[] generateEncryptedPreMasterSecret(TlsContext tlsContext, RSAKeyParameters rSAKeyParameters, OutputStream outputStream) {
        byte[] bArr = new byte[48];
        tlsContext.getSecureRandom().nextBytes(bArr);
        TlsUtils.writeVersion(tlsContext.getClientVersion(), bArr, 0);
        PKCS1Encoding pKCS1Encoding = new PKCS1Encoding(new RSABlindedEngine());
        pKCS1Encoding.init(true, new ParametersWithRandom(rSAKeyParameters, tlsContext.getSecureRandom()));
        try {
            byte[] processBlock = pKCS1Encoding.processBlock(bArr, 0, bArr.length);
            if (TlsUtils.isSSL(tlsContext)) {
                outputStream.write(processBlock);
                return bArr;
            }
            TlsUtils.writeOpaque16(processBlock, outputStream);
            return bArr;
        } catch (InvalidCipherTextException unused) {
            throw new TlsFatalAlert(80);
        }
    }

    public static byte[] safeDecryptPreMasterSecret(TlsContext tlsContext, RSAKeyParameters rSAKeyParameters, byte[] bArr) {
        byte[] bArr2;
        ProtocolVersion clientVersion = tlsContext.getClientVersion();
        byte[] bArr3 = new byte[48];
        tlsContext.getSecureRandom().nextBytes(bArr3);
        try {
            PKCS1Encoding pKCS1Encoding = new PKCS1Encoding((AsymmetricBlockCipher) new RSABlindedEngine(), bArr3);
            pKCS1Encoding.init(false, new ParametersWithRandom(rSAKeyParameters, tlsContext.getSecureRandom()));
            bArr2 = pKCS1Encoding.processBlock(bArr, 0, bArr.length);
        } catch (Exception unused) {
            bArr2 = Arrays.clone(bArr3);
        }
        byte majorVersion = (clientVersion.getMajorVersion() ^ (bArr2[0] & UnsignedBytes.MAX_VALUE)) | (clientVersion.getMinorVersion() ^ (bArr2[1] & UnsignedBytes.MAX_VALUE));
        byte b = majorVersion | (majorVersion >> 1);
        byte b2 = b | (b >> 2);
        byte b3 = (((b2 | (b2 >> 4)) & 1) - 1) ^ -1;
        for (int i = 0; i < 48; i++) {
            bArr2[i] = (byte) ((bArr2[i] & (b3 ^ -1)) | (bArr3[i] & b3));
        }
        return bArr2;
    }

    public static byte[] safeDecryptPreMasterSecret(TlsContext tlsContext, TlsEncryptionCredentials tlsEncryptionCredentials, byte[] bArr) {
        return tlsEncryptionCredentials.decryptPreMasterSecret(bArr);
    }
}
