package org.bouncycastle.crypto.util;

import java.io.InputStream;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class PublicKeyFactory {
    public static AsymmetricKeyParameter createKey(InputStream inputStream) {
        return createKey(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(inputStream).readObject()));
    }

    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r3v3, types: [org.bouncycastle.crypto.params.DSAParameters] */
    /* JADX WARNING: type inference failed for: r3v4, types: [org.bouncycastle.crypto.params.DSAParameters] */
    /* JADX WARNING: type inference failed for: r7v0, types: [org.bouncycastle.crypto.params.ECDomainParameters] */
    /* JADX WARNING: type inference failed for: r1v20, types: [org.bouncycastle.crypto.params.ECDomainParameters] */
    /* JADX WARNING: type inference failed for: r1v21, types: [org.bouncycastle.crypto.params.ECNamedDomainParameters] */
    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r9v0, types: [org.bouncycastle.crypto.params.DHValidationParameters] */
    /* JADX WARNING: type inference failed for: r3v9, types: [org.bouncycastle.crypto.params.DHValidationParameters] */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r1v34, types: [org.bouncycastle.crypto.params.ECDomainParameters] */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v2
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], org.bouncycastle.crypto.params.DHValidationParameters, org.bouncycastle.crypto.params.DSAParameters]
      uses: [org.bouncycastle.crypto.params.DSAParameters, ?[OBJECT, ARRAY]]
      mth insns count: 146
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo r10) {
        /*
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r10.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption
            boolean r1 = r1.equals(r2)
            r2 = 0
            if (r1 != 0) goto L_0x01e0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_ea_rsa
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x001f
            goto L_0x01e0
        L_0x001f:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber
            boolean r1 = r1.equals(r3)
            r3 = 0
            if (r1 == 0) goto L_0x0099
            org.bouncycastle.asn1.ASN1Primitive r10 = r10.parsePublicKey()
            org.bouncycastle.asn1.x9.DHPublicKey r10 = org.bouncycastle.asn1.x9.DHPublicKey.getInstance(r10)
            org.bouncycastle.asn1.ASN1Integer r10 = r10.getY()
            java.math.BigInteger r10 = r10.getValue()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.DHDomainParameters r0 = org.bouncycastle.asn1.x9.DHDomainParameters.getInstance(r0)
            org.bouncycastle.asn1.ASN1Integer r1 = r0.getP()
            java.math.BigInteger r5 = r1.getValue()
            org.bouncycastle.asn1.ASN1Integer r1 = r0.getG()
            java.math.BigInteger r6 = r1.getValue()
            org.bouncycastle.asn1.ASN1Integer r1 = r0.getQ()
            java.math.BigInteger r7 = r1.getValue()
            org.bouncycastle.asn1.ASN1Integer r1 = r0.getJ()
            if (r1 == 0) goto L_0x006c
            org.bouncycastle.asn1.ASN1Integer r1 = r0.getJ()
            java.math.BigInteger r1 = r1.getValue()
            r8 = r1
            goto L_0x006d
        L_0x006c:
            r8 = r3
        L_0x006d:
            org.bouncycastle.asn1.x9.DHValidationParms r0 = r0.getValidationParms()
            if (r0 == 0) goto L_0x008c
            org.bouncycastle.asn1.DERBitString r1 = r0.getSeed()
            byte[] r1 = r1.getBytes()
            org.bouncycastle.asn1.ASN1Integer r0 = r0.getPgenCounter()
            java.math.BigInteger r0 = r0.getValue()
            org.bouncycastle.crypto.params.DHValidationParameters r3 = new org.bouncycastle.crypto.params.DHValidationParameters
            int r0 = r0.intValue()
            r3.<init>(r1, r0)
        L_0x008c:
            r9 = r3
            org.bouncycastle.crypto.params.DHPublicKeyParameters r0 = new org.bouncycastle.crypto.params.DHPublicKeyParameters
            org.bouncycastle.crypto.params.DHParameters r1 = new org.bouncycastle.crypto.params.DHParameters
            r4 = r1
            r4.<init>(r5, r6, r7, r8, r9)
            r0.<init>(r10, r1)
            return r0
        L_0x0099:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x00d5
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.pkcs.DHParameter r0 = org.bouncycastle.asn1.pkcs.DHParameter.getInstance(r0)
            org.bouncycastle.asn1.ASN1Primitive r10 = r10.parsePublicKey()
            org.bouncycastle.asn1.ASN1Integer r10 = (org.bouncycastle.asn1.ASN1Integer) r10
            java.math.BigInteger r1 = r0.getL()
            if (r1 != 0) goto L_0x00ba
            goto L_0x00be
        L_0x00ba:
            int r2 = r1.intValue()
        L_0x00be:
            org.bouncycastle.crypto.params.DHParameters r1 = new org.bouncycastle.crypto.params.DHParameters
            java.math.BigInteger r4 = r0.getP()
            java.math.BigInteger r0 = r0.getG()
            r1.<init>(r4, r0, r3, r2)
            org.bouncycastle.crypto.params.DHPublicKeyParameters r0 = new org.bouncycastle.crypto.params.DHPublicKeyParameters
            java.math.BigInteger r10 = r10.getValue()
            r0.<init>(r10, r1)
            return r0
        L_0x00d5:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.elGamalAlgorithm
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0106
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.oiw.ElGamalParameter r0 = org.bouncycastle.asn1.oiw.ElGamalParameter.getInstance(r0)
            org.bouncycastle.asn1.ASN1Primitive r10 = r10.parsePublicKey()
            org.bouncycastle.asn1.ASN1Integer r10 = (org.bouncycastle.asn1.ASN1Integer) r10
            org.bouncycastle.crypto.params.ElGamalPublicKeyParameters r1 = new org.bouncycastle.crypto.params.ElGamalPublicKeyParameters
            java.math.BigInteger r10 = r10.getValue()
            org.bouncycastle.crypto.params.ElGamalParameters r2 = new org.bouncycastle.crypto.params.ElGamalParameters
            java.math.BigInteger r3 = r0.getP()
            java.math.BigInteger r0 = r0.getG()
            r2.<init>(r3, r0)
            r1.<init>(r10, r2)
            return r1
        L_0x0106:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x01b1
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.dsaWithSHA1
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0120
            goto L_0x01b1
        L_0x0120:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x01a9
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X962Parameters r0 = org.bouncycastle.asn1.x9.X962Parameters.getInstance(r0)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L_0x0167
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            r2 = r0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r2
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.crypto.ec.CustomNamedCurves.getByOID(r2)
            if (r0 != 0) goto L_0x014b
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(r2)
        L_0x014b:
            org.bouncycastle.crypto.params.ECNamedDomainParameters r8 = new org.bouncycastle.crypto.params.ECNamedDomainParameters
            org.bouncycastle.math.ec.ECCurve r3 = r0.getCurve()
            org.bouncycastle.math.ec.ECPoint r4 = r0.getG()
            java.math.BigInteger r5 = r0.getN()
            java.math.BigInteger r6 = r0.getH()
            byte[] r7 = r0.getSeed()
            r1 = r8
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r7 = r8
            goto L_0x0189
        L_0x0167:
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.asn1.x9.X9ECParameters.getInstance(r0)
            org.bouncycastle.crypto.params.ECDomainParameters r7 = new org.bouncycastle.crypto.params.ECDomainParameters
            org.bouncycastle.math.ec.ECCurve r2 = r0.getCurve()
            org.bouncycastle.math.ec.ECPoint r3 = r0.getG()
            java.math.BigInteger r4 = r0.getN()
            java.math.BigInteger r5 = r0.getH()
            byte[] r6 = r0.getSeed()
            r1 = r7
            r1.<init>(r2, r3, r4, r5, r6)
        L_0x0189:
            org.bouncycastle.asn1.DEROctetString r1 = new org.bouncycastle.asn1.DEROctetString
            org.bouncycastle.asn1.DERBitString r10 = r10.getPublicKeyData()
            byte[] r10 = r10.getBytes()
            r1.<init>(r10)
            org.bouncycastle.asn1.x9.X9ECPoint r10 = new org.bouncycastle.asn1.x9.X9ECPoint
            org.bouncycastle.math.ec.ECCurve r0 = r0.getCurve()
            r10.<init>(r0, r1)
            org.bouncycastle.crypto.params.ECPublicKeyParameters r0 = new org.bouncycastle.crypto.params.ECPublicKeyParameters
            org.bouncycastle.math.ec.ECPoint r10 = r10.getPoint()
            r0.<init>(r10, r7)
            return r0
        L_0x01a9:
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            java.lang.String r0 = "algorithm identifier in key not recognised"
            r10.<init>(r0)
            throw r10
        L_0x01b1:
            org.bouncycastle.asn1.ASN1Primitive r10 = r10.parsePublicKey()
            org.bouncycastle.asn1.ASN1Integer r10 = (org.bouncycastle.asn1.ASN1Integer) r10
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            if (r0 == 0) goto L_0x01d6
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.toASN1Primitive()
            org.bouncycastle.asn1.x509.DSAParameter r0 = org.bouncycastle.asn1.x509.DSAParameter.getInstance(r0)
            org.bouncycastle.crypto.params.DSAParameters r3 = new org.bouncycastle.crypto.params.DSAParameters
            java.math.BigInteger r1 = r0.getP()
            java.math.BigInteger r2 = r0.getQ()
            java.math.BigInteger r0 = r0.getG()
            r3.<init>(r1, r2, r0)
        L_0x01d6:
            org.bouncycastle.crypto.params.DSAPublicKeyParameters r0 = new org.bouncycastle.crypto.params.DSAPublicKeyParameters
            java.math.BigInteger r10 = r10.getValue()
            r0.<init>(r10, r3)
            return r0
        L_0x01e0:
            org.bouncycastle.asn1.ASN1Primitive r10 = r10.parsePublicKey()
            org.bouncycastle.asn1.pkcs.RSAPublicKey r10 = org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(r10)
            org.bouncycastle.crypto.params.RSAKeyParameters r0 = new org.bouncycastle.crypto.params.RSAKeyParameters
            java.math.BigInteger r1 = r10.getModulus()
            java.math.BigInteger r10 = r10.getPublicExponent()
            r0.<init>(r2, r1, r10)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.util.PublicKeyFactory.createKey(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo):org.bouncycastle.crypto.params.AsymmetricKeyParameter");
    }

    public static AsymmetricKeyParameter createKey(byte[] bArr) {
        return createKey(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(bArr)));
    }
}
