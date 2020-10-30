package org.bouncycastle.jcajce.provider.symmetric.util;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.util.Arrays;

public class IvAlgorithmParameters extends BaseAlgorithmParameters {
    private byte[] a;

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded() {
        return engineGetEncoded("ASN.1");
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String str) {
        if (isASN1FormatString(str)) {
            return new DEROctetString(engineGetEncoded("RAW")).getEncoded();
        }
        if (str.equals("RAW")) {
            return Arrays.clone(this.a);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
            throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
        }
        this.a = ((IvParameterSpec) algorithmParameterSpec).getIV();
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr) {
        if (bArr.length % 8 != 0 && bArr[0] == 4 && bArr[1] == bArr.length - 2) {
            bArr = ((ASN1OctetString) ASN1Primitive.fromByteArray(bArr)).getOctets();
        }
        this.a = Arrays.clone(bArr);
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr, String str) {
        if (isASN1FormatString(str)) {
            try {
                engineInit(((ASN1OctetString) ASN1Primitive.fromByteArray(bArr)).getOctets());
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Exception decoding: ");
                sb.append(e);
                throw new IOException(sb.toString());
            }
        } else if (str.equals("RAW")) {
            engineInit(bArr);
        } else {
            throw new IOException("Unknown parameters format in IV parameters object");
        }
    }

    /* access modifiers changed from: protected */
    public String engineToString() {
        return "IV Parameters";
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
        if (cls == IvParameterSpec.class) {
            return new IvParameterSpec(this.a);
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to IV parameters object.");
    }
}
