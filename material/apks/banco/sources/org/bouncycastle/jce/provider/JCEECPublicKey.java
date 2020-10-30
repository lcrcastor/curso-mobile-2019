package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ECPoint;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class JCEECPublicKey implements ECPublicKey, ECPointEncoder, org.bouncycastle.jce.interfaces.ECPublicKey {
    private String a = "EC";
    private ECPoint b;
    private ECParameterSpec c;
    private boolean d;
    private GOST3410PublicKeyAlgParameters e;

    public JCEECPublicKey(String str, ECPublicKeySpec eCPublicKeySpec) {
        this.a = str;
        this.c = eCPublicKeySpec.getParams();
        this.b = EC5Util.convertPoint(this.c, eCPublicKeySpec.getW(), false);
    }

    public JCEECPublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters) {
        this.a = str;
        this.b = eCPublicKeyParameters.getQ();
        this.c = null;
    }

    public JCEECPublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.a = str;
        this.b = eCPublicKeyParameters.getQ();
        if (eCParameterSpec == null) {
            this.c = a(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters);
        } else {
            this.c = eCParameterSpec;
        }
    }

    public JCEECPublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.a = str;
        this.b = eCPublicKeyParameters.getQ();
        this.c = eCParameterSpec == null ? a(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters) : EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
    }

    public JCEECPublicKey(String str, JCEECPublicKey jCEECPublicKey) {
        this.a = str;
        this.b = jCEECPublicKey.b;
        this.c = jCEECPublicKey.c;
        this.d = jCEECPublicKey.d;
        this.e = jCEECPublicKey.e;
    }

    public JCEECPublicKey(String str, org.bouncycastle.jce.spec.ECPublicKeySpec eCPublicKeySpec) {
        ECParameterSpec eCParameterSpec;
        this.a = str;
        this.b = eCPublicKeySpec.getQ();
        if (eCPublicKeySpec.getParams() != null) {
            eCParameterSpec = EC5Util.convertSpec(EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed()), eCPublicKeySpec.getParams());
        } else {
            if (this.b.getCurve() == null) {
                this.b = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve().createPoint(this.b.getAffineXCoord().toBigInteger(), this.b.getAffineYCoord().toBigInteger(), false);
            }
            eCParameterSpec = null;
        }
        this.c = eCParameterSpec;
    }

    public JCEECPublicKey(ECPublicKey eCPublicKey) {
        this.a = eCPublicKey.getAlgorithm();
        this.c = eCPublicKey.getParams();
        this.b = EC5Util.convertPoint(this.c, eCPublicKey.getW(), false);
    }

    private ECParameterSpec a(EllipticCurve ellipticCurve, ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(eCDomainParameters.getG().getAffineXCoord().toBigInteger(), eCDomainParameters.getG().getAffineYCoord().toBigInteger()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [java.security.spec.ECParameterSpec] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo r13) {
        /*
            r12 = this;
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r13.getAlgorithmId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r0.getObjectId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers.gostR3410_2001
            boolean r0 = r0.equals(r1)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x00c3
            org.bouncycastle.asn1.DERBitString r0 = r13.getPublicKeyData()
            java.lang.String r3 = "ECGOST3410"
            r12.a = r3
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x00bb }
            org.bouncycastle.asn1.ASN1Primitive r0 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r0)     // Catch:{ IOException -> 0x00bb }
            org.bouncycastle.asn1.ASN1OctetString r0 = (org.bouncycastle.asn1.ASN1OctetString) r0     // Catch:{ IOException -> 0x00bb }
            byte[] r0 = r0.getOctets()
            r3 = 32
            byte[] r4 = new byte[r3]
            byte[] r3 = new byte[r3]
            r5 = 0
        L_0x002f:
            int r6 = r4.length
            if (r5 == r6) goto L_0x003b
            int r6 = 31 - r5
            byte r6 = r0[r6]
            r4[r5] = r6
            int r5 = r5 + 1
            goto L_0x002f
        L_0x003b:
            r5 = 0
        L_0x003c:
            int r6 = r3.length
            if (r5 == r6) goto L_0x0048
            int r6 = 63 - r5
            byte r6 = r0[r6]
            r3[r5] = r6
            int r5 = r5 + 1
            goto L_0x003c
        L_0x0048:
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r0 = new org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r13 = r13.getAlgorithmId()
            org.bouncycastle.asn1.ASN1Encodable r13 = r13.getParameters()
            org.bouncycastle.asn1.ASN1Sequence r13 = (org.bouncycastle.asn1.ASN1Sequence) r13
            r0.<init>(r13)
            r12.e = r0
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r13 = r12.e
            org.bouncycastle.asn1.ASN1ObjectIdentifier r13 = r13.getPublicKeyParamSet()
            java.lang.String r13 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r13)
            org.bouncycastle.jce.spec.ECNamedCurveParameterSpec r13 = org.bouncycastle.jce.ECGOST3410NamedCurveTable.getParameterSpec(r13)
            org.bouncycastle.math.ec.ECCurve r0 = r13.getCurve()
            byte[] r5 = r13.getSeed()
            java.security.spec.EllipticCurve r8 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r0, r5)
            java.math.BigInteger r5 = new java.math.BigInteger
            r5.<init>(r2, r4)
            java.math.BigInteger r4 = new java.math.BigInteger
            r4.<init>(r2, r3)
            org.bouncycastle.math.ec.ECPoint r0 = r0.createPoint(r5, r4, r1)
            r12.b = r0
            org.bouncycastle.jce.spec.ECNamedCurveSpec r0 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r1 = r12.e
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getPublicKeyParamSet()
            java.lang.String r7 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r1)
            java.security.spec.ECPoint r9 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r1 = r13.getG()
            org.bouncycastle.math.ec.ECFieldElement r1 = r1.getAffineXCoord()
            java.math.BigInteger r1 = r1.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r2 = r13.getG()
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getAffineYCoord()
            java.math.BigInteger r2 = r2.toBigInteger()
            r9.<init>(r1, r2)
            java.math.BigInteger r10 = r13.getN()
            java.math.BigInteger r11 = r13.getH()
            r6 = r0
            r6.<init>(r7, r8, r9, r10, r11)
            r12.c = r0
            return
        L_0x00bb:
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "error recovering public key"
            r13.<init>(r0)
            throw r13
        L_0x00c3:
            org.bouncycastle.asn1.x9.X962Parameters r0 = new org.bouncycastle.asn1.x9.X962Parameters
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r3 = r13.getAlgorithmId()
            org.bouncycastle.asn1.ASN1Encodable r3 = r3.getParameters()
            org.bouncycastle.asn1.ASN1Primitive r3 = (org.bouncycastle.asn1.ASN1Primitive) r3
            r0.<init>(r3)
            boolean r3 = r0.isNamedCurve()
            if (r3 == 0) goto L_0x0120
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r0
            org.bouncycastle.asn1.x9.X9ECParameters r3 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(r0)
            org.bouncycastle.math.ec.ECCurve r4 = r3.getCurve()
            byte[] r5 = r3.getSeed()
            java.security.spec.EllipticCurve r8 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r4, r5)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r5 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r7 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getCurveName(r0)
            java.security.spec.ECPoint r9 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r0 = r3.getG()
            org.bouncycastle.math.ec.ECFieldElement r0 = r0.getAffineXCoord()
            java.math.BigInteger r0 = r0.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r6 = r3.getG()
            org.bouncycastle.math.ec.ECFieldElement r6 = r6.getAffineYCoord()
            java.math.BigInteger r6 = r6.toBigInteger()
            r9.<init>(r0, r6)
            java.math.BigInteger r10 = r3.getN()
            java.math.BigInteger r11 = r3.getH()
            r6 = r5
            r6.<init>(r7, r8, r9, r10, r11)
        L_0x011d:
            r12.c = r5
            goto L_0x0177
        L_0x0120:
            boolean r3 = r0.isImplicitlyCA()
            if (r3 == 0) goto L_0x0134
            r0 = 0
            r12.c = r0
            org.bouncycastle.jcajce.provider.config.ProviderConfiguration r0 = org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION
            org.bouncycastle.jce.spec.ECParameterSpec r0 = r0.getEcImplicitlyCa()
            org.bouncycastle.math.ec.ECCurve r4 = r0.getCurve()
            goto L_0x0177
        L_0x0134:
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.asn1.x9.X9ECParameters.getInstance(r0)
            org.bouncycastle.math.ec.ECCurve r4 = r0.getCurve()
            byte[] r3 = r0.getSeed()
            java.security.spec.EllipticCurve r3 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r4, r3)
            java.security.spec.ECParameterSpec r5 = new java.security.spec.ECParameterSpec
            java.security.spec.ECPoint r6 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r7 = r0.getG()
            org.bouncycastle.math.ec.ECFieldElement r7 = r7.getAffineXCoord()
            java.math.BigInteger r7 = r7.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r8 = r0.getG()
            org.bouncycastle.math.ec.ECFieldElement r8 = r8.getAffineYCoord()
            java.math.BigInteger r8 = r8.toBigInteger()
            r6.<init>(r7, r8)
            java.math.BigInteger r7 = r0.getN()
            java.math.BigInteger r0 = r0.getH()
            int r0 = r0.intValue()
            r5.<init>(r3, r6, r7, r0)
            goto L_0x011d
        L_0x0177:
            org.bouncycastle.asn1.DERBitString r13 = r13.getPublicKeyData()
            byte[] r13 = r13.getBytes()
            org.bouncycastle.asn1.DEROctetString r0 = new org.bouncycastle.asn1.DEROctetString
            r0.<init>(r13)
            byte r1 = r13[r1]
            r3 = 4
            if (r1 != r3) goto L_0x01b6
            byte r1 = r13[r2]
            int r2 = r13.length
            r3 = 2
            int r2 = r2 - r3
            if (r1 != r2) goto L_0x01b6
            byte r1 = r13[r3]
            r2 = 3
            if (r1 == r3) goto L_0x0199
            byte r1 = r13[r3]
            if (r1 != r2) goto L_0x01b6
        L_0x0199:
            org.bouncycastle.asn1.x9.X9IntegerConverter r1 = new org.bouncycastle.asn1.x9.X9IntegerConverter
            r1.<init>()
            int r1 = r1.getByteLength(r4)
            int r3 = r13.length
            int r3 = r3 - r2
            if (r1 < r3) goto L_0x01b6
            org.bouncycastle.asn1.ASN1Primitive r13 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r13)     // Catch:{ IOException -> 0x01ae }
            r0 = r13
            org.bouncycastle.asn1.ASN1OctetString r0 = (org.bouncycastle.asn1.ASN1OctetString) r0     // Catch:{ IOException -> 0x01ae }
            goto L_0x01b6
        L_0x01ae:
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "error recovering public key"
            r13.<init>(r0)
            throw r13
        L_0x01b6:
            org.bouncycastle.asn1.x9.X9ECPoint r13 = new org.bouncycastle.asn1.x9.X9ECPoint
            r13.<init>(r4, r0)
            org.bouncycastle.math.ec.ECPoint r13 = r13.getPoint()
            r12.b = r13
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.JCEECPublicKey.a(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo):void");
    }

    private void a(byte[] bArr, int i, BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length < 32) {
            byte[] bArr2 = new byte[32];
            System.arraycopy(byteArray, 0, bArr2, bArr2.length - byteArray.length, byteArray.length);
            byteArray = bArr2;
        }
        for (int i2 = 0; i2 != 32; i2++) {
            bArr[i + i2] = byteArray[(byteArray.length - 1) - i2];
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        a(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.a = (String) objectInputStream.readObject();
        this.d = objectInputStream.readBoolean();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.writeObject(getEncoded());
        objectOutputStream.writeObject(this.a);
        objectOutputStream.writeBoolean(this.d);
    }

    /* access modifiers changed from: 0000 */
    public org.bouncycastle.jce.spec.ECParameterSpec a() {
        return this.c != null ? EC5Util.convertSpec(this.c, this.d) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public ECPoint engineGetQ() {
        return this.b;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof JCEECPublicKey)) {
            return false;
        }
        JCEECPublicKey jCEECPublicKey = (JCEECPublicKey) obj;
        if (engineGetQ().equals(jCEECPublicKey.engineGetQ()) && a().equals(jCEECPublicKey.a())) {
            z = true;
        }
        return z;
    }

    public String getAlgorithm() {
        return this.a;
    }

    public byte[] getEncoded() {
        SubjectPublicKeyInfo subjectPublicKeyInfo;
        X962Parameters x962Parameters;
        ASN1Encodable aSN1Encodable;
        if (this.a.equals("ECGOST3410")) {
            if (this.e != null) {
                aSN1Encodable = this.e;
            } else if (this.c instanceof ECNamedCurveSpec) {
                aSN1Encodable = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.getOID(((ECNamedCurveSpec) this.c).getName()), CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet);
            } else {
                ECCurve convertCurve = EC5Util.convertCurve(this.c.getCurve());
                X9ECParameters x9ECParameters = new X9ECParameters(convertCurve, EC5Util.convertPoint(convertCurve, this.c.getGenerator(), this.d), this.c.getOrder(), BigInteger.valueOf((long) this.c.getCofactor()), this.c.getCurve().getSeed());
                aSN1Encodable = new X962Parameters(x9ECParameters);
            }
            BigInteger bigInteger = this.b.getAffineXCoord().toBigInteger();
            BigInteger bigInteger2 = this.b.getAffineYCoord().toBigInteger();
            byte[] bArr = new byte[64];
            a(bArr, 0, bigInteger);
            a(bArr, 32, bigInteger2);
            try {
                subjectPublicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, aSN1Encodable), (ASN1Encodable) new DEROctetString(bArr));
            } catch (IOException unused) {
                return null;
            }
        } else {
            if (this.c instanceof ECNamedCurveSpec) {
                ASN1ObjectIdentifier namedCurveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec) this.c).getName());
                if (namedCurveOid == null) {
                    namedCurveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.c).getName());
                }
                x962Parameters = new X962Parameters(namedCurveOid);
            } else if (this.c == null) {
                x962Parameters = new X962Parameters((ASN1Primitive) DERNull.INSTANCE);
            } else {
                ECCurve convertCurve2 = EC5Util.convertCurve(this.c.getCurve());
                X9ECParameters x9ECParameters2 = new X9ECParameters(convertCurve2, EC5Util.convertPoint(convertCurve2, this.c.getGenerator(), this.d), this.c.getOrder(), BigInteger.valueOf((long) this.c.getCofactor()), this.c.getCurve().getSeed());
                x962Parameters = new X962Parameters(x9ECParameters2);
            }
            subjectPublicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, x962Parameters), ((ASN1OctetString) new X9ECPoint(engineGetQ().getCurve().createPoint(getQ().getAffineXCoord().toBigInteger(), getQ().getAffineYCoord().toBigInteger(), this.d)).toASN1Primitive()).getOctets());
        }
        return KeyUtil.getEncodedSubjectPublicKeyInfo(subjectPublicKeyInfo);
    }

    public String getFormat() {
        return "X.509";
    }

    public org.bouncycastle.jce.spec.ECParameterSpec getParameters() {
        if (this.c == null) {
            return null;
        }
        return EC5Util.convertSpec(this.c, this.d);
    }

    public ECParameterSpec getParams() {
        return this.c;
    }

    public ECPoint getQ() {
        return this.c == null ? this.b.getDetachedPoint() : this.b;
    }

    public java.security.spec.ECPoint getW() {
        return new java.security.spec.ECPoint(this.b.getAffineXCoord().toBigInteger(), this.b.getAffineYCoord().toBigInteger());
    }

    public int hashCode() {
        return engineGetQ().hashCode() ^ a().hashCode();
    }

    public void setPointFormat(String str) {
        this.d = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("EC Public Key");
        stringBuffer.append(property);
        stringBuffer.append("            X: ");
        stringBuffer.append(this.b.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(property);
        stringBuffer.append("            Y: ");
        stringBuffer.append(this.b.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(property);
        return stringBuffer.toString();
    }
}
