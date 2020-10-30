package org.bouncycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.oiw.ElGamalParameter;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;

public class AlgorithmParametersSpi extends BaseAlgorithmParameters {
    ElGamalParameterSpec a;

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded() {
        try {
            return new ElGamalParameter(this.a.getP(), this.a.getG()).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            throw new RuntimeException("Error encoding ElGamalParameters");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String str) {
        if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
            return engineGetEncoded();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        boolean z = algorithmParameterSpec instanceof ElGamalParameterSpec;
        if (!z && !(algorithmParameterSpec instanceof DHParameterSpec)) {
            throw new InvalidParameterSpecException("DHParameterSpec required to initialise a ElGamal algorithm parameters object");
        } else if (z) {
            this.a = (ElGamalParameterSpec) algorithmParameterSpec;
        } else {
            DHParameterSpec dHParameterSpec = (DHParameterSpec) algorithmParameterSpec;
            this.a = new ElGamalParameterSpec(dHParameterSpec.getP(), dHParameterSpec.getG());
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr) {
        try {
            ElGamalParameter instance = ElGamalParameter.getInstance(ASN1Primitive.fromByteArray(bArr));
            this.a = new ElGamalParameterSpec(instance.getP(), instance.getG());
        } catch (ClassCastException unused) {
            throw new IOException("Not a valid ElGamal Parameter encoding.");
        } catch (ArrayIndexOutOfBoundsException unused2) {
            throw new IOException("Not a valid ElGamal Parameter encoding.");
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
        return "ElGamal Parameters";
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
        if (cls == ElGamalParameterSpec.class) {
            return this.a;
        }
        if (cls == DHParameterSpec.class) {
            return new DHParameterSpec(this.a.getP(), this.a.getG());
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
    }
}
