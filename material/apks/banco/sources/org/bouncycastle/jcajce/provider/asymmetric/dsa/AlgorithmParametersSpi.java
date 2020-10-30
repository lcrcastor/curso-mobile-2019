package org.bouncycastle.jcajce.provider.asymmetric.dsa;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.DSAParameter;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    DSAParameterSpec a;

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded() {
        try {
            return new DSAParameter(this.a.getP(), this.a.getQ(), this.a.getG()).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            throw new RuntimeException("Error encoding DSAParameters");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String str) {
        if (isASN1FormatString(str)) {
            return engineGetEncoded();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec engineGetParameterSpec(Class cls) {
        if (cls != null) {
            return localEngineGetParameterSpec(cls);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof DSAParameterSpec)) {
            throw new InvalidParameterSpecException("DSAParameterSpec required to initialise a DSA algorithm parameters object");
        }
        this.a = (DSAParameterSpec) algorithmParameterSpec;
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr) {
        try {
            DSAParameter instance = DSAParameter.getInstance(ASN1Primitive.fromByteArray(bArr));
            this.a = new DSAParameterSpec(instance.getP(), instance.getQ(), instance.getG());
        } catch (ClassCastException unused) {
            throw new IOException("Not a valid DSA Parameter encoding.");
        } catch (ArrayIndexOutOfBoundsException unused2) {
            throw new IOException("Not a valid DSA Parameter encoding.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr, String str) {
        if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
            engineInit(bArr);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown parameter format ");
        sb.append(str);
        throw new IOException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public String engineToString() {
        return "DSA Parameters";
    }

    /* access modifiers changed from: protected */
    public boolean isASN1FormatString(String str) {
        return str == null || str.equals("ASN.1");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
        if (cls == DSAParameterSpec.class) {
            return this.a;
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to DSA parameters object.");
    }
}
