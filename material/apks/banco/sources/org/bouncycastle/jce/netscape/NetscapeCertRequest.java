package org.bouncycastle.jce.netscape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class NetscapeCertRequest extends ASN1Object {
    AlgorithmIdentifier a;
    AlgorithmIdentifier b;
    byte[] c;
    String d;
    DERBitString e;
    PublicKey f;

    public NetscapeCertRequest(String str, AlgorithmIdentifier algorithmIdentifier, PublicKey publicKey) {
        this.d = str;
        this.a = algorithmIdentifier;
        this.f = publicKey;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(a());
        aSN1EncodableVector.add(new DERIA5String(str));
        try {
            this.e = new DERBitString((ASN1Encodable) new DERSequence(aSN1EncodableVector));
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception encoding key: ");
            sb.append(e2.toString());
            throw new InvalidKeySpecException(sb.toString());
        }
    }

    public NetscapeCertRequest(ASN1Sequence aSN1Sequence) {
        try {
            if (aSN1Sequence.size() != 3) {
                StringBuilder sb = new StringBuilder();
                sb.append("invalid SPKAC (size):");
                sb.append(aSN1Sequence.size());
                throw new IllegalArgumentException(sb.toString());
            }
            this.a = new AlgorithmIdentifier((ASN1Sequence) aSN1Sequence.getObjectAt(1));
            this.c = ((DERBitString) aSN1Sequence.getObjectAt(2)).getBytes();
            ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(0);
            if (aSN1Sequence2.size() != 2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("invalid PKAC (len): ");
                sb2.append(aSN1Sequence2.size());
                throw new IllegalArgumentException(sb2.toString());
            }
            this.d = ((DERIA5String) aSN1Sequence2.getObjectAt(1)).getString();
            this.e = new DERBitString((ASN1Encodable) aSN1Sequence2);
            SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo((ASN1Sequence) aSN1Sequence2.getObjectAt(0));
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(new DERBitString((ASN1Encodable) subjectPublicKeyInfo).getBytes());
            this.b = subjectPublicKeyInfo.getAlgorithmId();
            this.f = KeyFactory.getInstance(this.b.getObjectId().getId(), BouncyCastleProvider.PROVIDER_NAME).generatePublic(x509EncodedKeySpec);
        } catch (Exception e2) {
            throw new IllegalArgumentException(e2.toString());
        }
    }

    public NetscapeCertRequest(byte[] bArr) {
        this(a(bArr));
    }

    private ASN1Primitive a() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.f.getEncoded());
            byteArrayOutputStream.close();
            return new ASN1InputStream((InputStream) new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).readObject();
        } catch (IOException e2) {
            throw new InvalidKeySpecException(e2.getMessage());
        }
    }

    private static ASN1Sequence a(byte[] bArr) {
        return ASN1Sequence.getInstance(new ASN1InputStream((InputStream) new ByteArrayInputStream(bArr)).readObject());
    }

    public String getChallenge() {
        return this.d;
    }

    public AlgorithmIdentifier getKeyAlgorithm() {
        return this.b;
    }

    public PublicKey getPublicKey() {
        return this.f;
    }

    public AlgorithmIdentifier getSigningAlgorithm() {
        return this.a;
    }

    public void setChallenge(String str) {
        this.d = str;
    }

    public void setKeyAlgorithm(AlgorithmIdentifier algorithmIdentifier) {
        this.b = algorithmIdentifier;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.f = publicKey;
    }

    public void setSigningAlgorithm(AlgorithmIdentifier algorithmIdentifier) {
        this.a = algorithmIdentifier;
    }

    public void sign(PrivateKey privateKey) {
        sign(privateKey, null);
    }

    public void sign(PrivateKey privateKey, SecureRandom secureRandom) {
        Signature instance = Signature.getInstance(this.a.getAlgorithm().getId(), BouncyCastleProvider.PROVIDER_NAME);
        if (secureRandom != null) {
            instance.initSign(privateKey, secureRandom);
        } else {
            instance.initSign(privateKey);
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(a());
        aSN1EncodableVector.add(new DERIA5String(this.d));
        try {
            instance.update(new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER));
            this.c = instance.sign();
        } catch (IOException e2) {
            throw new SignatureException(e2.getMessage());
        }
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        try {
            aSN1EncodableVector2.add(a());
        } catch (Exception unused) {
        }
        aSN1EncodableVector2.add(new DERIA5String(this.d));
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(new DERBitString(this.c));
        return new DERSequence(aSN1EncodableVector);
    }

    public boolean verify(String str) {
        if (!str.equals(this.d)) {
            return false;
        }
        Signature instance = Signature.getInstance(this.a.getObjectId().getId(), BouncyCastleProvider.PROVIDER_NAME);
        instance.initVerify(this.f);
        instance.update(this.e.getBytes());
        return instance.verify(this.c);
    }
}
