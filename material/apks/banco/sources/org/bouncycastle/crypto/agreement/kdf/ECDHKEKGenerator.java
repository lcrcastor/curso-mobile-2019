package org.bouncycastle.crypto.agreement.kdf;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.DigestDerivationFunction;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.util.Pack;

public class ECDHKEKGenerator implements DigestDerivationFunction {
    private DigestDerivationFunction a;
    private ASN1ObjectIdentifier b;
    private int c;
    private byte[] d;

    public ECDHKEKGenerator(Digest digest) {
        this.a = new KDF2BytesGenerator(digest);
    }

    public int generateBytes(byte[] bArr, int i, int i2) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new AlgorithmIdentifier(this.b, DERNull.INSTANCE));
        aSN1EncodableVector.add(new DERTaggedObject(true, 2, new DEROctetString(Pack.intToBigEndian(this.c))));
        try {
            this.a.init(new KDFParameters(this.d, new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER)));
            return this.a.generateBytes(bArr, i, i2);
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable to initialise kdf: ");
            sb.append(e.getMessage());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public Digest getDigest() {
        return this.a.getDigest();
    }

    public void init(DerivationParameters derivationParameters) {
        DHKDFParameters dHKDFParameters = (DHKDFParameters) derivationParameters;
        this.b = dHKDFParameters.getAlgorithm();
        this.c = dHKDFParameters.getKeySize();
        this.d = dHKDFParameters.getZ();
    }
}
