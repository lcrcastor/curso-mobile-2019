package org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util;

public class PBESecretKeyFactory extends BaseSecretKeyFactory implements PBE {
    private boolean a;
    private int b;
    private int c;
    private int d;
    private int e;

    public PBESecretKeyFactory(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, int i, int i2, int i3, int i4) {
        super(str, aSN1ObjectIdentifier);
        this.a = z;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(KeySpec keySpec) {
        if (keySpec instanceof PBEKeySpec) {
            PBEKeySpec pBEKeySpec = (PBEKeySpec) keySpec;
            if (pBEKeySpec.getSalt() == null) {
                BCPBEKey bCPBEKey = new BCPBEKey(this.algName, this.algOid, this.b, this.c, this.d, this.e, pBEKeySpec, null);
                return bCPBEKey;
            }
            BCPBEKey bCPBEKey2 = new BCPBEKey(this.algName, this.algOid, this.b, this.c, this.d, this.e, pBEKeySpec, this.a ? Util.makePBEParameters(pBEKeySpec, this.b, this.c, this.d, this.e) : Util.makePBEMacParameters(pBEKeySpec, this.b, this.c, this.d));
            return bCPBEKey2;
        }
        throw new InvalidKeySpecException("Invalid KeySpec");
    }
}
