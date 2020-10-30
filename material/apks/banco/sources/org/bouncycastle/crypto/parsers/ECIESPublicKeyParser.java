package org.bouncycastle.crypto.parsers;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.crypto.KeyParser;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

public class ECIESPublicKeyParser implements KeyParser {
    private ECDomainParameters a;

    public ECIESPublicKeyParser(ECDomainParameters eCDomainParameters) {
        this.a = eCDomainParameters;
    }

    public AsymmetricKeyParameter readKey(InputStream inputStream) {
        int i;
        int read = inputStream.read();
        switch (read) {
            case 0:
                throw new IOException("Sender's public key invalid.");
            case 2:
            case 3:
                i = (this.a.getCurve().getFieldSize() + 7) / 8;
                break;
            case 4:
            case 6:
            case 7:
                i = ((this.a.getCurve().getFieldSize() + 7) / 8) * 2;
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Sender's public key has invalid point encoding 0x");
                sb.append(Integer.toString(read, 16));
                throw new IOException(sb.toString());
        }
        byte[] bArr = new byte[(i + 1)];
        bArr[0] = (byte) read;
        inputStream.read(bArr, 1, bArr.length - 1);
        return new ECPublicKeyParameters(this.a.getCurve().decodePoint(bArr), this.a);
    }
}
