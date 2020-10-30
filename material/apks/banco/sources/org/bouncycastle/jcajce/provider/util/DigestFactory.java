package org.bouncycastle.jcajce.provider.util;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2ParameterSpec;
import org.bouncycastle.util.Strings;

public class DigestFactory {
    private static Set a = new HashSet();
    private static Set b = new HashSet();
    private static Set c = new HashSet();
    private static Set d = new HashSet();
    private static Set e = new HashSet();
    private static Set f = new HashSet();
    private static Map g = new HashMap();

    static {
        a.add(CommonUtils.MD5_INSTANCE);
        a.add(PKCSObjectIdentifiers.md5.getId());
        b.add("SHA1");
        b.add(CommonUtils.SHA1_INSTANCE);
        b.add(OIWObjectIdentifiers.idSHA1.getId());
        c.add("SHA224");
        c.add("SHA-224");
        c.add(NISTObjectIdentifiers.id_sha224.getId());
        d.add(McElieceCCA2ParameterSpec.DEFAULT_MD);
        d.add("SHA-256");
        d.add(NISTObjectIdentifiers.id_sha256.getId());
        e.add("SHA384");
        e.add("SHA-384");
        e.add(NISTObjectIdentifiers.id_sha384.getId());
        f.add("SHA512");
        f.add("SHA-512");
        f.add(NISTObjectIdentifiers.id_sha512.getId());
        g.put(CommonUtils.MD5_INSTANCE, PKCSObjectIdentifiers.md5);
        g.put(PKCSObjectIdentifiers.md5.getId(), PKCSObjectIdentifiers.md5);
        g.put("SHA1", OIWObjectIdentifiers.idSHA1);
        g.put(CommonUtils.SHA1_INSTANCE, OIWObjectIdentifiers.idSHA1);
        g.put(OIWObjectIdentifiers.idSHA1.getId(), OIWObjectIdentifiers.idSHA1);
        g.put("SHA224", NISTObjectIdentifiers.id_sha224);
        g.put("SHA-224", NISTObjectIdentifiers.id_sha224);
        g.put(NISTObjectIdentifiers.id_sha224.getId(), NISTObjectIdentifiers.id_sha224);
        g.put(McElieceCCA2ParameterSpec.DEFAULT_MD, NISTObjectIdentifiers.id_sha256);
        g.put("SHA-256", NISTObjectIdentifiers.id_sha256);
        g.put(NISTObjectIdentifiers.id_sha256.getId(), NISTObjectIdentifiers.id_sha256);
        g.put("SHA384", NISTObjectIdentifiers.id_sha384);
        g.put("SHA-384", NISTObjectIdentifiers.id_sha384);
        g.put(NISTObjectIdentifiers.id_sha384.getId(), NISTObjectIdentifiers.id_sha384);
        g.put("SHA512", NISTObjectIdentifiers.id_sha512);
        g.put("SHA-512", NISTObjectIdentifiers.id_sha512);
        g.put(NISTObjectIdentifiers.id_sha512.getId(), NISTObjectIdentifiers.id_sha512);
    }

    public static Digest getDigest(String str) {
        String upperCase = Strings.toUpperCase(str);
        if (b.contains(upperCase)) {
            return new SHA1Digest();
        }
        if (a.contains(upperCase)) {
            return new MD5Digest();
        }
        if (c.contains(upperCase)) {
            return new SHA224Digest();
        }
        if (d.contains(upperCase)) {
            return new SHA256Digest();
        }
        if (e.contains(upperCase)) {
            return new SHA384Digest();
        }
        if (f.contains(upperCase)) {
            return new SHA512Digest();
        }
        return null;
    }

    public static ASN1ObjectIdentifier getOID(String str) {
        return (ASN1ObjectIdentifier) g.get(str);
    }

    public static boolean isSameDigest(String str, String str2) {
        return (b.contains(str) && b.contains(str2)) || (c.contains(str) && c.contains(str2)) || ((d.contains(str) && d.contains(str2)) || ((e.contains(str) && e.contains(str2)) || ((f.contains(str) && f.contains(str2)) || (a.contains(str) && a.contains(str2)))));
    }
}
