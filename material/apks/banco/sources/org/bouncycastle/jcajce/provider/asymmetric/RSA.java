package org.bouncycastle.jcajce.provider.asymmetric;

import io.fabric.sdk.android.services.common.CommonUtils;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2ParameterSpec;

public class RSA {

    public static class Mappings extends AsymmetricAlgorithmProvider {
        private void a(ConfigurableProvider configurableProvider, String str, String str2, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("WITHRSA");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("withRSA");
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append("WithRSA");
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(str);
            sb7.append("/");
            sb7.append("RSA");
            String sb8 = sb7.toString();
            StringBuilder sb9 = new StringBuilder();
            sb9.append(str);
            sb9.append("WITHRSAENCRYPTION");
            String sb10 = sb9.toString();
            StringBuilder sb11 = new StringBuilder();
            sb11.append(str);
            sb11.append("withRSAEncryption");
            String sb12 = sb11.toString();
            StringBuilder sb13 = new StringBuilder();
            sb13.append(str);
            sb13.append("WithRSAEncryption");
            String sb14 = sb13.toString();
            StringBuilder sb15 = new StringBuilder();
            sb15.append("Signature.");
            sb15.append(sb2);
            configurableProvider.addAlgorithm(sb15.toString(), str2);
            StringBuilder sb16 = new StringBuilder();
            sb16.append("Alg.Alias.Signature.");
            sb16.append(sb4);
            configurableProvider.addAlgorithm(sb16.toString(), sb2);
            StringBuilder sb17 = new StringBuilder();
            sb17.append("Alg.Alias.Signature.");
            sb17.append(sb6);
            configurableProvider.addAlgorithm(sb17.toString(), sb2);
            StringBuilder sb18 = new StringBuilder();
            sb18.append("Alg.Alias.Signature.");
            sb18.append(sb10);
            configurableProvider.addAlgorithm(sb18.toString(), sb2);
            StringBuilder sb19 = new StringBuilder();
            sb19.append("Alg.Alias.Signature.");
            sb19.append(sb12);
            configurableProvider.addAlgorithm(sb19.toString(), sb2);
            StringBuilder sb20 = new StringBuilder();
            sb20.append("Alg.Alias.Signature.");
            sb20.append(sb14);
            configurableProvider.addAlgorithm(sb20.toString(), sb2);
            StringBuilder sb21 = new StringBuilder();
            sb21.append("Alg.Alias.Signature.");
            sb21.append(sb8);
            configurableProvider.addAlgorithm(sb21.toString(), sb2);
            if (aSN1ObjectIdentifier != null) {
                StringBuilder sb22 = new StringBuilder();
                sb22.append("Alg.Alias.Signature.");
                sb22.append(aSN1ObjectIdentifier);
                configurableProvider.addAlgorithm(sb22.toString(), sb2);
                StringBuilder sb23 = new StringBuilder();
                sb23.append("Alg.Alias.Signature.OID.");
                sb23.append(aSN1ObjectIdentifier);
                configurableProvider.addAlgorithm(sb23.toString(), sb2);
            }
        }

        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("AlgorithmParameters.OAEP", "org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$OAEP");
            configurableProvider.addAlgorithm("AlgorithmParameters.PSS", "org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.RSAPSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.RSASSA-PSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA224withRSA/PSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA256withRSA/PSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA384withRSA/PSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA512withRSA/PSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA224WITHRSAANDMGF1", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA256WITHRSAANDMGF1", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA384WITHRSAANDMGF1", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA512WITHRSAANDMGF1", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.RAWRSAPSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSAPSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSASSA-PSS", "PSS");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSAANDMGF1", "PSS");
            configurableProvider.addAlgorithm("Cipher.RSA", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
            configurableProvider.addAlgorithm("Cipher.RSA/RAW", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
            configurableProvider.addAlgorithm("Cipher.RSA/PKCS1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
            configurableProvider.addAlgorithm("Cipher.1.2.840.113549.1.1.1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
            configurableProvider.addAlgorithm("Cipher.2.5.8.1.1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
            configurableProvider.addAlgorithm("Cipher.RSA/1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding_PrivateOnly");
            configurableProvider.addAlgorithm("Cipher.RSA/2", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding_PublicOnly");
            configurableProvider.addAlgorithm("Cipher.RSA/OAEP", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$OAEPPadding");
            StringBuilder sb = new StringBuilder();
            sb.append("Cipher.");
            sb.append(PKCSObjectIdentifiers.id_RSAES_OAEP);
            configurableProvider.addAlgorithm(sb.toString(), "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$OAEPPadding");
            configurableProvider.addAlgorithm("Cipher.RSA/ISO9796-1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$ISO9796d1Padding");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//RAW", "RSA");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//NOPADDING", "RSA");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//PKCS1PADDING", "RSA/PKCS1");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//OAEPPADDING", "RSA/OAEP");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//ISO9796-1PADDING", "RSA/ISO9796-1");
            configurableProvider.addAlgorithm("KeyFactory.RSA", "org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi");
            configurableProvider.addAlgorithm("KeyPairGenerator.RSA", "org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi");
            KeyFactorySpi keyFactorySpi = new KeyFactorySpi();
            registerOid(configurableProvider, PKCSObjectIdentifiers.rsaEncryption, "RSA", keyFactorySpi);
            registerOid(configurableProvider, X509ObjectIdentifiers.id_ea_rsa, "RSA", keyFactorySpi);
            registerOid(configurableProvider, PKCSObjectIdentifiers.id_RSAES_OAEP, "RSA", keyFactorySpi);
            registerOid(configurableProvider, PKCSObjectIdentifiers.id_RSASSA_PSS, "RSA", keyFactorySpi);
            registerOidAlgorithmParameters(configurableProvider, PKCSObjectIdentifiers.rsaEncryption, "RSA");
            registerOidAlgorithmParameters(configurableProvider, X509ObjectIdentifiers.id_ea_rsa, "RSA");
            registerOidAlgorithmParameters(configurableProvider, PKCSObjectIdentifiers.id_RSAES_OAEP, "OAEP");
            registerOidAlgorithmParameters(configurableProvider, PKCSObjectIdentifiers.id_RSASSA_PSS, "PSS");
            configurableProvider.addAlgorithm("Signature.RSASSA-PSS", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Signature.");
            sb2.append(PKCSObjectIdentifiers.id_RSASSA_PSS);
            configurableProvider.addAlgorithm(sb2.toString(), "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Signature.OID.");
            sb3.append(PKCSObjectIdentifiers.id_RSASSA_PSS);
            configurableProvider.addAlgorithm(sb3.toString(), "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
            configurableProvider.addAlgorithm("Signature.SHA224WITHRSAANDMGF1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA224withRSA");
            configurableProvider.addAlgorithm("Signature.SHA256WITHRSAANDMGF1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA256withRSA");
            configurableProvider.addAlgorithm("Signature.SHA384WITHRSAANDMGF1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA384withRSA");
            configurableProvider.addAlgorithm("Signature.SHA512WITHRSAANDMGF1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA512withRSA");
            configurableProvider.addAlgorithm("Signature.SHA224withRSA/PSS", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA224withRSA");
            configurableProvider.addAlgorithm("Signature.SHA256withRSA/PSS", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA256withRSA");
            configurableProvider.addAlgorithm("Signature.SHA384withRSA/PSS", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA384withRSA");
            configurableProvider.addAlgorithm("Signature.SHA512withRSA/PSS", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA512withRSA");
            configurableProvider.addAlgorithm("Signature.RSA", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$noneRSA");
            configurableProvider.addAlgorithm("Signature.RAWRSASSA-PSS", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$nonePSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.RAWRSA", "RSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSA", "RSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.RAWRSAPSS", "RAWRSASSA-PSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSAPSS", "RAWRSASSA-PSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSASSA-PSS", "RAWRSASSA-PSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSAANDMGF1", "RAWRSASSA-PSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.RSAPSS", "RSASSA-PSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA224withRSAandMGF1", "SHA224withRSA/PSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA256withRSAandMGF1", "SHA256withRSA/PSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA384withRSAandMGF1", "SHA384withRSA/PSS");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA512withRSAandMGF1", "SHA512withRSA/PSS");
            if (configurableProvider.hasAlgorithm("MessageDigest", "MD2")) {
                a(configurableProvider, "MD2", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD2", PKCSObjectIdentifiers.md2WithRSAEncryption);
            }
            if (configurableProvider.hasAlgorithm("MessageDigest", "MD4")) {
                a(configurableProvider, "MD4", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD4", PKCSObjectIdentifiers.md4WithRSAEncryption);
            }
            if (configurableProvider.hasAlgorithm("MessageDigest", CommonUtils.MD5_INSTANCE)) {
                a(configurableProvider, CommonUtils.MD5_INSTANCE, "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD5", PKCSObjectIdentifiers.md5WithRSAEncryption);
                configurableProvider.addAlgorithm("Signature.MD5withRSA/ISO9796-2", "org.bouncycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$MD5WithRSAEncryption");
                configurableProvider.addAlgorithm("Alg.Alias.Signature.MD5WithRSA/ISO9796-2", "MD5withRSA/ISO9796-2");
            }
            if (configurableProvider.hasAlgorithm("MessageDigest", "SHA1")) {
                configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA1withRSA/PSS", "PSS");
                configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA1WITHRSAANDMGF1", "PSS");
                configurableProvider.addAlgorithm("Signature.SHA1withRSA/PSS", "org.bouncycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA1withRSA");
                configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1withRSAandMGF1", "SHA1withRSA/PSS");
                configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WITHRSAANDMGF1", "SHA1withRSA/PSS");
                a(configurableProvider, "SHA1", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA1", PKCSObjectIdentifiers.sha1WithRSAEncryption);
                configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WithRSA/ISO9796-2", "SHA1withRSA/ISO9796-2");
                configurableProvider.addAlgorithm("Signature.SHA1withRSA/ISO9796-2", "org.bouncycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA1WithRSAEncryption");
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Alg.Alias.Signature.");
                sb4.append(OIWObjectIdentifiers.sha1WithRSA);
                configurableProvider.addAlgorithm(sb4.toString(), "SHA1WITHRSA");
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Alg.Alias.Signature.OID.");
                sb5.append(OIWObjectIdentifiers.sha1WithRSA);
                configurableProvider.addAlgorithm(sb5.toString(), "SHA1WITHRSA");
            }
            a(configurableProvider, "SHA224", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA224", PKCSObjectIdentifiers.sha224WithRSAEncryption);
            a(configurableProvider, McElieceCCA2ParameterSpec.DEFAULT_MD, "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA256", PKCSObjectIdentifiers.sha256WithRSAEncryption);
            a(configurableProvider, "SHA384", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA384", PKCSObjectIdentifiers.sha384WithRSAEncryption);
            a(configurableProvider, "SHA512", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA512", PKCSObjectIdentifiers.sha512WithRSAEncryption);
            if (configurableProvider.hasAlgorithm("MessageDigest", "RIPEMD128")) {
                a(configurableProvider, "RIPEMD128", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD128", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
                a(configurableProvider, "RMD128", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD128", null);
            }
            if (configurableProvider.hasAlgorithm("MessageDigest", "RIPEMD160")) {
                a(configurableProvider, "RIPEMD160", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD160", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
                a(configurableProvider, "RMD160", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD160", null);
                configurableProvider.addAlgorithm("Alg.Alias.Signature.RIPEMD160WithRSA/ISO9796-2", "RIPEMD160withRSA/ISO9796-2");
                configurableProvider.addAlgorithm("Signature.RIPEMD160withRSA/ISO9796-2", "org.bouncycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$RIPEMD160WithRSAEncryption");
            }
            if (configurableProvider.hasAlgorithm("MessageDigest", "RIPEMD256")) {
                a(configurableProvider, "RIPEMD256", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD256", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
                a(configurableProvider, "RMD256", "org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD256", null);
            }
        }
    }
}
