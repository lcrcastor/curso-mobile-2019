package org.bouncycastle.jce.provider;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.CTSBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Strings;

public class BrokenJCEBlockCipher implements BrokenPBE {
    private Class[] a = {IvParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class};
    private BufferedBlockCipher b;
    private ParametersWithIV c;
    private int d = 2;
    private int e = 1;
    private int f;
    private int g;
    private int h = 0;
    private AlgorithmParameters i = null;

    public static class BrokePBEWithMD5AndDES extends BrokenJCEBlockCipher {
        public BrokePBEWithMD5AndDES() {
            CBCBlockCipher cBCBlockCipher = new CBCBlockCipher(new DESEngine());
            super(cBCBlockCipher, 0, 0, 64, 64);
        }
    }

    public static class BrokePBEWithSHA1AndDES extends BrokenJCEBlockCipher {
        public BrokePBEWithSHA1AndDES() {
            CBCBlockCipher cBCBlockCipher = new CBCBlockCipher(new DESEngine());
            super(cBCBlockCipher, 0, 1, 64, 64);
        }
    }

    public static class BrokePBEWithSHAAndDES2Key extends BrokenJCEBlockCipher {
        public BrokePBEWithSHAAndDES2Key() {
            CBCBlockCipher cBCBlockCipher = new CBCBlockCipher(new DESedeEngine());
            super(cBCBlockCipher, 2, 1, 128, 64);
        }
    }

    public static class BrokePBEWithSHAAndDES3Key extends BrokenJCEBlockCipher {
        public BrokePBEWithSHAAndDES3Key() {
            CBCBlockCipher cBCBlockCipher = new CBCBlockCipher(new DESedeEngine());
            super(cBCBlockCipher, 2, 1, 192, 64);
        }
    }

    public static class OldPBEWithSHAAndDES3Key extends BrokenJCEBlockCipher {
        public OldPBEWithSHAAndDES3Key() {
            CBCBlockCipher cBCBlockCipher = new CBCBlockCipher(new DESedeEngine());
            super(cBCBlockCipher, 3, 1, 192, 64);
        }
    }

    public static class OldPBEWithSHAAndTwofish extends BrokenJCEBlockCipher {
        public OldPBEWithSHAAndTwofish() {
            CBCBlockCipher cBCBlockCipher = new CBCBlockCipher(new TwofishEngine());
            super(cBCBlockCipher, 3, 1, 256, 128);
        }
    }

    protected BrokenJCEBlockCipher(BlockCipher blockCipher) {
        this.b = new PaddedBufferedBlockCipher(blockCipher);
    }

    protected BrokenJCEBlockCipher(BlockCipher blockCipher, int i2, int i3, int i4, int i5) {
        this.b = new PaddedBufferedBlockCipher(blockCipher);
        this.d = i2;
        this.e = i3;
        this.f = i4;
        this.g = i5;
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int processBytes = i3 != 0 ? this.b.processBytes(bArr, i2, i3, bArr2, i4) : 0;
        try {
            return processBytes + this.b.doFinal(bArr2, i4 + processBytes);
        } catch (DataLengthException e2) {
            throw new IllegalBlockSizeException(e2.getMessage());
        } catch (InvalidCipherTextException e3) {
            throw new BadPaddingException(e3.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[engineGetOutputSize(i3)];
        int processBytes = i3 != 0 ? this.b.processBytes(bArr, i2, i3, bArr2, 0) : 0;
        try {
            int doFinal = processBytes + this.b.doFinal(bArr2, processBytes);
            byte[] bArr3 = new byte[doFinal];
            System.arraycopy(bArr2, 0, bArr3, 0, doFinal);
            return bArr3;
        } catch (DataLengthException e2) {
            throw new IllegalBlockSizeException(e2.getMessage());
        } catch (InvalidCipherTextException e3) {
            throw new BadPaddingException(e3.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return this.b.getBlockSize();
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        if (this.c != null) {
            return this.c.getIV();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length;
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int i2) {
        return this.b.getOutputSize(i2);
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.i == null && this.c != null) {
            String algorithmName = this.b.getUnderlyingCipher().getAlgorithmName();
            if (algorithmName.indexOf(47) >= 0) {
                algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
            }
            try {
                this.i = AlgorithmParameters.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
                this.i.init(this.c.getIV());
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.i;
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        AlgorithmParameterSpec algorithmParameterSpec = null;
        if (algorithmParameters != null) {
            int i3 = 0;
            while (true) {
                if (i3 == this.a.length) {
                    break;
                }
                try {
                    algorithmParameterSpec = algorithmParameters.getParameterSpec(this.a[i3]);
                    break;
                } catch (Exception unused) {
                    i3++;
                }
            }
            if (algorithmParameterSpec == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("can't handle parameter ");
                sb.append(algorithmParameters.toString());
                throw new InvalidAlgorithmParameterException(sb.toString());
            }
        }
        this.i = algorithmParameters;
        engineInit(i2, key, algorithmParameterSpec, secureRandom);
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i2, Key key, SecureRandom secureRandom) {
        try {
            engineInit(i2, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0020, code lost:
        if (r8.g != 0) goto L_0x0022;
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d5  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0105  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineInit(int r9, java.security.Key r10, java.security.spec.AlgorithmParameterSpec r11, java.security.SecureRandom r12) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey
            if (r0 == 0) goto L_0x0029
            r1 = r10
            org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey r1 = (org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) r1
            int r3 = r8.d
            int r4 = r8.e
            org.bouncycastle.crypto.BufferedBlockCipher r10 = r8.b
            org.bouncycastle.crypto.BlockCipher r10 = r10.getUnderlyingCipher()
            java.lang.String r5 = r10.getAlgorithmName()
            int r6 = r8.f
            int r7 = r8.g
            r2 = r11
            org.bouncycastle.crypto.CipherParameters r10 = org.bouncycastle.jce.provider.BrokenPBE.Util.a(r1, r2, r3, r4, r5, r6, r7)
            int r11 = r8.g
            if (r11 == 0) goto L_0x00c3
        L_0x0022:
            r11 = r10
            org.bouncycastle.crypto.params.ParametersWithIV r11 = (org.bouncycastle.crypto.params.ParametersWithIV) r11
            r8.c = r11
            goto L_0x00c3
        L_0x0029:
            if (r11 != 0) goto L_0x0037
            org.bouncycastle.crypto.params.KeyParameter r11 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r10 = r10.getEncoded()
            r11.<init>(r10)
        L_0x0034:
            r10 = r11
            goto L_0x00c3
        L_0x0037:
            boolean r0 = r11 instanceof javax.crypto.spec.IvParameterSpec
            if (r0 == 0) goto L_0x0064
            int r0 = r8.h
            if (r0 == 0) goto L_0x005a
            org.bouncycastle.crypto.params.ParametersWithIV r0 = new org.bouncycastle.crypto.params.ParametersWithIV
            org.bouncycastle.crypto.params.KeyParameter r1 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r10 = r10.getEncoded()
            r1.<init>(r10)
            javax.crypto.spec.IvParameterSpec r11 = (javax.crypto.spec.IvParameterSpec) r11
            byte[] r10 = r11.getIV()
            r0.<init>(r1, r10)
            r10 = r0
            org.bouncycastle.crypto.params.ParametersWithIV r10 = (org.bouncycastle.crypto.params.ParametersWithIV) r10
            r8.c = r10
        L_0x0058:
            r10 = r0
            goto L_0x00c3
        L_0x005a:
            org.bouncycastle.crypto.params.KeyParameter r11 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r10 = r10.getEncoded()
            r11.<init>(r10)
            goto L_0x0034
        L_0x0064:
            boolean r0 = r11 instanceof javax.crypto.spec.RC2ParameterSpec
            if (r0 == 0) goto L_0x008b
            javax.crypto.spec.RC2ParameterSpec r11 = (javax.crypto.spec.RC2ParameterSpec) r11
            org.bouncycastle.crypto.params.RC2Parameters r0 = new org.bouncycastle.crypto.params.RC2Parameters
            byte[] r10 = r10.getEncoded()
            int r1 = r11.getEffectiveKeyBits()
            r0.<init>(r10, r1)
            byte[] r10 = r11.getIV()
            if (r10 == 0) goto L_0x0058
            int r10 = r8.h
            if (r10 == 0) goto L_0x0058
            org.bouncycastle.crypto.params.ParametersWithIV r10 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r11 = r11.getIV()
            r10.<init>(r0, r11)
            goto L_0x0022
        L_0x008b:
            boolean r0 = r11 instanceof javax.crypto.spec.RC5ParameterSpec
            if (r0 == 0) goto L_0x010b
            javax.crypto.spec.RC5ParameterSpec r11 = (javax.crypto.spec.RC5ParameterSpec) r11
            org.bouncycastle.crypto.params.RC5Parameters r0 = new org.bouncycastle.crypto.params.RC5Parameters
            byte[] r10 = r10.getEncoded()
            int r1 = r11.getRounds()
            r0.<init>(r10, r1)
            int r10 = r11.getWordSize()
            r1 = 32
            if (r10 == r1) goto L_0x00ae
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "can only accept RC5 word size 32 (at the moment...)"
            r9.<init>(r10)
            throw r9
        L_0x00ae:
            byte[] r10 = r11.getIV()
            if (r10 == 0) goto L_0x0058
            int r10 = r8.h
            if (r10 == 0) goto L_0x0058
            org.bouncycastle.crypto.params.ParametersWithIV r10 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r11 = r11.getIV()
            r10.<init>(r0, r11)
            goto L_0x0022
        L_0x00c3:
            int r11 = r8.h
            r0 = 1
            if (r11 == 0) goto L_0x00f3
            boolean r11 = r10 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r11 != 0) goto L_0x00f3
            if (r12 != 0) goto L_0x00d3
            java.security.SecureRandom r12 = new java.security.SecureRandom
            r12.<init>()
        L_0x00d3:
            if (r9 == r0) goto L_0x00e1
            r11 = 3
            if (r9 != r11) goto L_0x00d9
            goto L_0x00e1
        L_0x00d9:
            java.security.InvalidAlgorithmParameterException r9 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r10 = "no IV set when one expected"
            r9.<init>(r10)
            throw r9
        L_0x00e1:
            int r11 = r8.h
            byte[] r11 = new byte[r11]
            r12.nextBytes(r11)
            org.bouncycastle.crypto.params.ParametersWithIV r12 = new org.bouncycastle.crypto.params.ParametersWithIV
            r12.<init>(r10, r11)
            r10 = r12
            org.bouncycastle.crypto.params.ParametersWithIV r10 = (org.bouncycastle.crypto.params.ParametersWithIV) r10
            r8.c = r10
            r10 = r12
        L_0x00f3:
            switch(r9) {
                case 1: goto L_0x0105;
                case 2: goto L_0x00fe;
                case 3: goto L_0x0105;
                case 4: goto L_0x00fe;
                default: goto L_0x00f6;
            }
        L_0x00f6:
            java.io.PrintStream r9 = java.lang.System.out
            java.lang.String r10 = "eeek!"
            r9.println(r10)
            return
        L_0x00fe:
            org.bouncycastle.crypto.BufferedBlockCipher r9 = r8.b
            r11 = 0
            r9.init(r11, r10)
            return
        L_0x0105:
            org.bouncycastle.crypto.BufferedBlockCipher r9 = r8.b
            r9.init(r0, r10)
            return
        L_0x010b:
            java.security.InvalidAlgorithmParameterException r9 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r10 = "unknown parameter type."
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.BrokenJCEBlockCipher.engineInit(int, java.security.Key, java.security.spec.AlgorithmParameterSpec, java.security.SecureRandom):void");
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String str) {
        PaddedBufferedBlockCipher paddedBufferedBlockCipher;
        PaddedBufferedBlockCipher paddedBufferedBlockCipher2;
        String upperCase = Strings.toUpperCase(str);
        if (upperCase.equals("ECB")) {
            this.h = 0;
            paddedBufferedBlockCipher = new PaddedBufferedBlockCipher(this.b.getUnderlyingCipher());
        } else if (upperCase.equals("CBC")) {
            this.h = this.b.getUnderlyingCipher().getBlockSize();
            paddedBufferedBlockCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(this.b.getUnderlyingCipher()));
        } else {
            if (upperCase.startsWith("OFB")) {
                this.h = this.b.getUnderlyingCipher().getBlockSize();
                if (upperCase.length() != 3) {
                    paddedBufferedBlockCipher2 = new PaddedBufferedBlockCipher(new OFBBlockCipher(this.b.getUnderlyingCipher(), Integer.parseInt(upperCase.substring(3))));
                } else {
                    paddedBufferedBlockCipher = new PaddedBufferedBlockCipher(new OFBBlockCipher(this.b.getUnderlyingCipher(), this.b.getBlockSize() * 8));
                }
            } else if (upperCase.startsWith("CFB")) {
                this.h = this.b.getUnderlyingCipher().getBlockSize();
                if (upperCase.length() != 3) {
                    paddedBufferedBlockCipher2 = new PaddedBufferedBlockCipher(new CFBBlockCipher(this.b.getUnderlyingCipher(), Integer.parseInt(upperCase.substring(3))));
                } else {
                    paddedBufferedBlockCipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(this.b.getUnderlyingCipher(), this.b.getBlockSize() * 8));
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("can't support mode ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
            this.b = paddedBufferedBlockCipher2;
            return;
        }
        this.b = paddedBufferedBlockCipher;
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String str) {
        BufferedBlockCipher paddedBufferedBlockCipher;
        String upperCase = Strings.toUpperCase(str);
        if (upperCase.equals("NOPADDING")) {
            paddedBufferedBlockCipher = new BufferedBlockCipher(this.b.getUnderlyingCipher());
        } else if (upperCase.equals("PKCS5PADDING") || upperCase.equals("PKCS7PADDING") || upperCase.equals("ISO10126PADDING")) {
            paddedBufferedBlockCipher = new PaddedBufferedBlockCipher(this.b.getUnderlyingCipher());
        } else if (upperCase.equals("WITHCTS")) {
            paddedBufferedBlockCipher = new CTSBlockCipher(this.b.getUnderlyingCipher());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Padding ");
            sb.append(str);
            sb.append(" unknown.");
            throw new NoSuchPaddingException(sb.toString());
        }
        this.b = paddedBufferedBlockCipher;
    }

    /* access modifiers changed from: protected */
    public Key engineUnwrap(byte[] bArr, String str, int i2) {
        try {
            byte[] engineDoFinal = engineDoFinal(bArr, 0, bArr.length);
            if (i2 == 3) {
                return new SecretKeySpec(engineDoFinal, str);
            }
            try {
                KeyFactory instance = KeyFactory.getInstance(str, BouncyCastleProvider.PROVIDER_NAME);
                if (i2 == 1) {
                    return instance.generatePublic(new X509EncodedKeySpec(engineDoFinal));
                }
                if (i2 == 2) {
                    return instance.generatePrivate(new PKCS8EncodedKeySpec(engineDoFinal));
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown key type ");
                sb.append(i2);
                throw new InvalidKeyException(sb.toString());
            } catch (NoSuchProviderException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Unknown key type ");
                sb2.append(e2.getMessage());
                throw new InvalidKeyException(sb2.toString());
            } catch (NoSuchAlgorithmException e3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Unknown key type ");
                sb3.append(e3.getMessage());
                throw new InvalidKeyException(sb3.toString());
            } catch (InvalidKeySpecException e4) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Unknown key type ");
                sb4.append(e4.getMessage());
                throw new InvalidKeyException(sb4.toString());
            }
        } catch (BadPaddingException e5) {
            throw new InvalidKeyException(e5.getMessage());
        } catch (IllegalBlockSizeException e6) {
            throw new InvalidKeyException(e6.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        return this.b.processBytes(bArr, i2, i3, bArr2, i4);
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] bArr, int i2, int i3) {
        int updateOutputSize = this.b.getUpdateOutputSize(i3);
        if (updateOutputSize > 0) {
            byte[] bArr2 = new byte[updateOutputSize];
            this.b.processBytes(bArr, i2, i3, bArr2, 0);
            return bArr2;
        }
        this.b.processBytes(bArr, i2, i3, null, 0);
        return null;
    }

    /* access modifiers changed from: protected */
    public byte[] engineWrap(Key key) {
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("Cannot wrap key, null encoding.");
        }
        try {
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (BadPaddingException e2) {
            throw new IllegalBlockSizeException(e2.getMessage());
        }
    }
}
