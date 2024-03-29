package org.bouncycastle.crypto.parsers;

import java.io.InputStream;
import java.math.BigInteger;
import org.bouncycastle.crypto.KeyParser;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;

public class DHIESPublicKeyParser implements KeyParser {
    private DHParameters a;

    public DHIESPublicKeyParser(DHParameters dHParameters) {
        this.a = dHParameters;
    }

    public AsymmetricKeyParameter readKey(InputStream inputStream) {
        byte[] bArr = new byte[((this.a.getP().bitLength() + 7) / 8)];
        inputStream.read(bArr, 0, bArr.length);
        return new DHPublicKeyParameters(new BigInteger(1, bArr), this.a);
    }
}
