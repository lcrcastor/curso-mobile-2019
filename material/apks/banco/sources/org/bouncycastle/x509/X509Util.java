package org.bouncycastle.x509;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.util.Strings;

class X509Util {
    private static Hashtable a = new Hashtable();
    private static Hashtable b = new Hashtable();
    private static Set c = new HashSet();

    static class Implementation {
        Object a;
        Provider b;

        Implementation(Object obj, Provider provider) {
            this.a = obj;
            this.b = provider;
        }

        /* access modifiers changed from: 0000 */
        public Object a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public Provider b() {
            return this.b;
        }
    }

    static {
        a.put("MD2WITHRSAENCRYPTION", PKCSObjectIdentifiers.md2WithRSAEncryption);
        a.put("MD2WITHRSA", PKCSObjectIdentifiers.md2WithRSAEncryption);
        a.put("MD5WITHRSAENCRYPTION", PKCSObjectIdentifiers.md5WithRSAEncryption);
        a.put("MD5WITHRSA", PKCSObjectIdentifiers.md5WithRSAEncryption);
        a.put("SHA1WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha1WithRSAEncryption);
        a.put("SHA1WITHRSA", PKCSObjectIdentifiers.sha1WithRSAEncryption);
        a.put("SHA224WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        a.put("SHA224WITHRSA", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        a.put("SHA256WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        a.put("SHA256WITHRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        a.put("SHA384WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        a.put("SHA384WITHRSA", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        a.put("SHA512WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        a.put("SHA512WITHRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        a.put("SHA1WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("SHA224WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("SHA256WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("SHA384WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("SHA512WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("RIPEMD160WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
        a.put("RIPEMD160WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
        a.put("RIPEMD128WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
        a.put("RIPEMD128WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
        a.put("RIPEMD256WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
        a.put("RIPEMD256WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
        a.put("SHA1WITHDSA", X9ObjectIdentifiers.id_dsa_with_sha1);
        a.put("DSAWITHSHA1", X9ObjectIdentifiers.id_dsa_with_sha1);
        a.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
        a.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
        a.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
        a.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
        a.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
        a.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
        a.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
        a.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
        a.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
        a.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
        a.put("GOST3411WITHGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
        a.put("GOST3411WITHGOST3410-94", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
        a.put("GOST3411WITHECGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        a.put("GOST3411WITHECGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        a.put("GOST3411WITHGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        c.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
        c.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
        c.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
        c.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
        c.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
        c.add(X9ObjectIdentifiers.id_dsa_with_sha1);
        c.add(NISTObjectIdentifiers.dsa_with_sha224);
        c.add(NISTObjectIdentifiers.dsa_with_sha256);
        c.add(NISTObjectIdentifiers.dsa_with_sha384);
        c.add(NISTObjectIdentifiers.dsa_with_sha512);
        c.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
        c.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        b.put("SHA1WITHRSAANDMGF1", a(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE), 20));
        b.put("SHA224WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE), 28));
        b.put("SHA256WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE), 32));
        b.put("SHA384WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE), 48));
        b.put("SHA512WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE), 64));
    }

    X509Util() {
    }

    static Signature a(String str, String str2) {
        return str2 != null ? Signature.getInstance(str, str2) : Signature.getInstance(str);
    }

    static Iterator a() {
        Enumeration keys = a.keys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasMoreElements()) {
            arrayList.add(keys.nextElement());
        }
        return arrayList.iterator();
    }

    static ASN1ObjectIdentifier a(String str) {
        String upperCase = Strings.toUpperCase(str);
        return a.containsKey(upperCase) ? (ASN1ObjectIdentifier) a.get(upperCase) : new ASN1ObjectIdentifier(upperCase);
    }

    private static RSASSAPSSparams a(AlgorithmIdentifier algorithmIdentifier, int i) {
        return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new ASN1Integer((long) i), new ASN1Integer(1));
    }

    static AlgorithmIdentifier a(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        if (c.contains(aSN1ObjectIdentifier)) {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier);
        }
        String upperCase = Strings.toUpperCase(str);
        return b.containsKey(upperCase) ? new AlgorithmIdentifier(aSN1ObjectIdentifier, (ASN1Encodable) b.get(upperCase)) : new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }

    static X509Principal a(X500Principal x500Principal) {
        try {
            return new X509Principal(x500Principal.getEncoded());
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot convert principal");
        }
    }

    static Implementation a(String str, String str2, Provider provider) {
        String upperCase = Strings.toUpperCase(str2);
        while (true) {
            StringBuilder sb = new StringBuilder();
            sb.append("Alg.Alias.");
            sb.append(str);
            sb.append(".");
            sb.append(upperCase);
            String property = provider.getProperty(sb.toString());
            if (property == null) {
                break;
            }
            upperCase = property;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(".");
        sb2.append(upperCase);
        String property2 = provider.getProperty(sb2.toString());
        if (property2 != null) {
            try {
                ClassLoader classLoader = provider.getClass().getClassLoader();
                return new Implementation((classLoader != null ? classLoader.loadClass(property2) : Class.forName(property2)).newInstance(), provider);
            } catch (ClassNotFoundException unused) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("algorithm ");
                sb3.append(upperCase);
                sb3.append(" in provider ");
                sb3.append(provider.getName());
                sb3.append(" but no class \"");
                sb3.append(property2);
                sb3.append("\" found!");
                throw new IllegalStateException(sb3.toString());
            } catch (Exception unused2) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("algorithm ");
                sb4.append(upperCase);
                sb4.append(" in provider ");
                sb4.append(provider.getName());
                sb4.append(" but class \"");
                sb4.append(property2);
                sb4.append("\" inaccessible!");
                throw new IllegalStateException(sb4.toString());
            }
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("cannot find implementation ");
            sb5.append(upperCase);
            sb5.append(" for provider ");
            sb5.append(provider.getName());
            throw new NoSuchAlgorithmException(sb5.toString());
        }
    }

    static byte[] a(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str, String str2, PrivateKey privateKey, SecureRandom secureRandom, ASN1Encodable aSN1Encodable) {
        if (aSN1ObjectIdentifier == null) {
            throw new IllegalStateException("no signature algorithm specified");
        }
        Signature a2 = a(str, str2);
        if (secureRandom != null) {
            a2.initSign(privateKey, secureRandom);
        } else {
            a2.initSign(privateKey);
        }
        a2.update(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
        return a2.sign();
    }

    static byte[] a(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str, PrivateKey privateKey, SecureRandom secureRandom, ASN1Encodable aSN1Encodable) {
        if (aSN1ObjectIdentifier == null) {
            throw new IllegalStateException("no signature algorithm specified");
        }
        Signature b2 = b(str);
        if (secureRandom != null) {
            b2.initSign(privateKey, secureRandom);
        } else {
            b2.initSign(privateKey);
        }
        b2.update(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
        return b2.sign();
    }

    static Signature b(String str) {
        return Signature.getInstance(str);
    }

    static Implementation b(String str, String str2) {
        Provider[] providers = Security.getProviders();
        for (int i = 0; i != providers.length; i++) {
            Implementation a2 = a(str, Strings.toUpperCase(str2), providers[i]);
            if (a2 != null) {
                return a2;
            }
            try {
                a(str, str2, providers[i]);
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("cannot find implementation ");
        sb.append(str2);
        throw new NoSuchAlgorithmException(sb.toString());
    }

    static Provider c(String str) {
        Provider provider = Security.getProvider(str);
        if (provider != null) {
            return provider;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Provider ");
        sb.append(str);
        sb.append(" not found");
        throw new NoSuchProviderException(sb.toString());
    }
}
