package org.bouncycastle.jcajce.provider.symmetric.util;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.bouncycastle.asn1.cms.GCMParameters;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.CTSBlockCipher;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.modes.GCFBBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.GOFBBlockCipher;
import org.bouncycastle.crypto.modes.OCBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.modes.OpenPGPCFBBlockCipher;
import org.bouncycastle.crypto.modes.PGPCFBBlockCipher;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.ISO10126d2Padding;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.TBCPadding;
import org.bouncycastle.crypto.paddings.X923Padding;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Strings;

public class BaseBlockCipher extends BaseWrapCipher implements PBE {
    private static final Class a = b("javax.crypto.spec.GCMParameterSpec");
    private Class[] b = {RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class, a};
    private BlockCipher c;
    private BlockCipherProvider d;
    private GenericBlockCipher e;
    private ParametersWithIV f;
    private AEADParameters g;
    private int h = 0;
    private boolean i;
    private PBEParameterSpec j = null;
    private String k = null;
    private String l = null;

    static class AEADGenericBlockCipher implements GenericBlockCipher {
        private static final Constructor a;
        private AEADBlockCipher b;

        static {
            Class a2 = BaseBlockCipher.b("javax.crypto.AEADBadTagException");
            a = a2 != null ? a(a2) : null;
        }

        AEADGenericBlockCipher(AEADBlockCipher aEADBlockCipher) {
            this.b = aEADBlockCipher;
        }

        private static Constructor a(Class cls) {
            try {
                return cls.getConstructor(new Class[]{String.class});
            } catch (Exception unused) {
                return null;
            }
        }

        public int a(int i) {
            return this.b.getOutputSize(i);
        }

        public int a(byte[] bArr, int i) {
            try {
                return this.b.doFinal(bArr, i);
            } catch (InvalidCipherTextException e) {
                if (a != null) {
                    Throwable th = null;
                    try {
                        th = (BadPaddingException) a.newInstance(new Object[]{e.getMessage()});
                    } catch (Exception unused) {
                    }
                    if (th != null) {
                        throw th;
                    }
                }
                throw new BadPaddingException(e.getMessage());
            }
        }

        public int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
            return this.b.processBytes(bArr, i, i2, bArr2, i3);
        }

        public void a(boolean z, CipherParameters cipherParameters) {
            this.b.init(z, cipherParameters);
        }

        public void a(byte[] bArr, int i, int i2) {
            this.b.processAADBytes(bArr, i, i2);
        }

        public boolean a() {
            return false;
        }

        public int b(int i) {
            return this.b.getUpdateOutputSize(i);
        }

        public BlockCipher b() {
            return this.b.getUnderlyingCipher();
        }
    }

    static class BufferedGenericBlockCipher implements GenericBlockCipher {
        private BufferedBlockCipher a;

        BufferedGenericBlockCipher(BlockCipher blockCipher) {
            this.a = new PaddedBufferedBlockCipher(blockCipher);
        }

        BufferedGenericBlockCipher(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
            this.a = new PaddedBufferedBlockCipher(blockCipher, blockCipherPadding);
        }

        BufferedGenericBlockCipher(BufferedBlockCipher bufferedBlockCipher) {
            this.a = bufferedBlockCipher;
        }

        public int a(int i) {
            return this.a.getOutputSize(i);
        }

        public int a(byte[] bArr, int i) {
            try {
                return this.a.doFinal(bArr, i);
            } catch (InvalidCipherTextException e) {
                throw new BadPaddingException(e.getMessage());
            }
        }

        public int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
            return this.a.processBytes(bArr, i, i2, bArr2, i3);
        }

        public void a(boolean z, CipherParameters cipherParameters) {
            this.a.init(z, cipherParameters);
        }

        public void a(byte[] bArr, int i, int i2) {
            throw new UnsupportedOperationException("AAD is not supported in the current mode.");
        }

        public boolean a() {
            return !(this.a instanceof CTSBlockCipher);
        }

        public int b(int i) {
            return this.a.getUpdateOutputSize(i);
        }

        public BlockCipher b() {
            return this.a.getUnderlyingCipher();
        }
    }

    interface GenericBlockCipher {
        int a(int i);

        int a(byte[] bArr, int i);

        int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3);

        void a(boolean z, CipherParameters cipherParameters);

        void a(byte[] bArr, int i, int i2);

        boolean a();

        int b(int i);

        BlockCipher b();
    }

    protected BaseBlockCipher(BlockCipher blockCipher) {
        this.c = blockCipher;
        this.e = new BufferedGenericBlockCipher(blockCipher);
    }

    protected BaseBlockCipher(BlockCipher blockCipher, int i2) {
        this.c = blockCipher;
        this.e = new BufferedGenericBlockCipher(blockCipher);
        this.h = i2 / 8;
    }

    protected BaseBlockCipher(BufferedBlockCipher bufferedBlockCipher, int i2) {
        this.c = bufferedBlockCipher.getUnderlyingCipher();
        this.e = new BufferedGenericBlockCipher(bufferedBlockCipher);
        this.h = i2 / 8;
    }

    protected BaseBlockCipher(AEADBlockCipher aEADBlockCipher) {
        this.c = aEADBlockCipher.getUnderlyingCipher();
        this.h = this.c.getBlockSize();
        this.e = new AEADGenericBlockCipher(aEADBlockCipher);
    }

    protected BaseBlockCipher(BlockCipherProvider blockCipherProvider) {
        this.c = blockCipherProvider.get();
        this.d = blockCipherProvider;
        this.e = new BufferedGenericBlockCipher(blockCipherProvider.get());
    }

    /* access modifiers changed from: private */
    public static Class b(String str) {
        try {
            return BaseBlockCipher.class.getClassLoader().loadClass(str);
        } catch (Exception unused) {
            return null;
        }
    }

    private boolean c(String str) {
        return "CCM".equals(str) || "EAX".equals(str) || GoogleCloudMessaging.INSTANCE_ID_SCOPE.equals(str) || "OCB".equals(str);
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5 = 0;
        if (i3 != 0) {
            try {
                i5 = this.e.a(bArr, i2, i3, bArr2, i4);
            } catch (OutputLengthException e2) {
                throw new ShortBufferException(e2.getMessage());
            } catch (DataLengthException e3) {
                throw new IllegalBlockSizeException(e3.getMessage());
            }
        }
        return i5 + this.e.a(bArr2, i4 + i5);
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[engineGetOutputSize(i3)];
        int a2 = i3 != 0 ? this.e.a(bArr, i2, i3, bArr2, 0) : 0;
        try {
            int a3 = a2 + this.e.a(bArr2, a2);
            if (a3 == bArr2.length) {
                return bArr2;
            }
            byte[] bArr3 = new byte[a3];
            System.arraycopy(bArr2, 0, bArr3, 0, a3);
            return bArr3;
        } catch (DataLengthException e2) {
            throw new IllegalBlockSizeException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return this.c.getBlockSize();
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        if (this.g != null) {
            return this.g.getNonce();
        }
        if (this.f != null) {
            return this.f.getIV();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int i2) {
        return this.e.a(i2);
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null) {
            if (this.j != null) {
                try {
                    this.engineParams = AlgorithmParameters.getInstance(this.k, BouncyCastleProvider.PROVIDER_NAME);
                    this.engineParams.init(this.j);
                } catch (Exception unused) {
                    return null;
                }
            } else if (this.f != null) {
                String algorithmName = this.e.b().getAlgorithmName();
                if (algorithmName.indexOf(47) >= 0) {
                    algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
                }
                try {
                    this.engineParams = AlgorithmParameters.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
                    this.engineParams.init(this.f.getIV());
                } catch (Exception e2) {
                    throw new RuntimeException(e2.toString());
                }
            } else if (this.g != null) {
                try {
                    this.engineParams = AlgorithmParameters.getInstance(GoogleCloudMessaging.INSTANCE_ID_SCOPE, BouncyCastleProvider.PROVIDER_NAME);
                    this.engineParams.init(new GCMParameters(this.g.getNonce(), this.g.getMacSize()).getEncoded());
                } catch (Exception e3) {
                    throw new RuntimeException(e3.toString());
                }
            }
        }
        return this.engineParams;
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        AlgorithmParameterSpec algorithmParameterSpec = null;
        if (algorithmParameters != null) {
            int i3 = 0;
            while (true) {
                if (i3 == this.b.length) {
                    break;
                }
                if (this.b[i3] != null) {
                    try {
                        algorithmParameterSpec = algorithmParameters.getParameterSpec(this.b[i3]);
                        break;
                    } catch (Exception unused) {
                    }
                }
                i3++;
            }
            if (algorithmParameterSpec == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("can't handle parameter ");
                sb.append(algorithmParameters.toString());
                throw new InvalidAlgorithmParameterException(sb.toString());
            }
        }
        engineInit(i2, key, algorithmParameterSpec, secureRandom);
        this.engineParams = algorithmParameters;
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i2, Key key, SecureRandom secureRandom) {
        try {
            engineInit(i2, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException(e2.getMessage());
        }
    }

    /* JADX WARNING: type inference failed for: r8v1, types: [org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r8v2, types: [org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r8v3, types: [org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r9v4, types: [org.bouncycastle.crypto.params.ParametersWithRandom] */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r8v30, types: [org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARNING: type inference failed for: r8v43, types: [org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARNING: type inference failed for: r8v48, types: [org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r8v49 */
    /* JADX WARNING: type inference failed for: r9v30, types: [org.bouncycastle.crypto.params.KeyParameter] */
    /* JADX WARNING: type inference failed for: r0v8, types: [org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARNING: type inference failed for: r8v54 */
    /* JADX WARNING: type inference failed for: r8v56, types: [org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARNING: type inference failed for: r9v38 */
    /* JADX WARNING: type inference failed for: r8v59 */
    /* JADX WARNING: type inference failed for: r9v39, types: [org.bouncycastle.crypto.params.KeyParameter] */
    /* JADX WARNING: type inference failed for: r8v61 */
    /* JADX WARNING: type inference failed for: r9v40 */
    /* JADX WARNING: type inference failed for: r8v63 */
    /* JADX WARNING: type inference failed for: r0v21, types: [org.bouncycastle.crypto.params.ParametersWithSBox, org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: type inference failed for: r0v23, types: [org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARNING: type inference failed for: r8v75 */
    /* JADX WARNING: type inference failed for: r8v76 */
    /* JADX WARNING: type inference failed for: r8v77 */
    /* JADX WARNING: type inference failed for: r8v78 */
    /* JADX WARNING: type inference failed for: r9v48 */
    /* JADX WARNING: type inference failed for: r8v79 */
    /* JADX WARNING: type inference failed for: r9v49 */
    /* JADX WARNING: type inference failed for: r8v80 */
    /* JADX WARNING: type inference failed for: r0v28 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b9, code lost:
        if ((r8 instanceof org.bouncycastle.crypto.params.ParametersWithIV) != false) goto L_0x00bb;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x02e4  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x02ea  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x02ed  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x032c A[SYNTHETIC, Splitter:B:136:0x032c] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x032f A[Catch:{ Exception -> 0x0355 }] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0335 A[Catch:{ Exception -> 0x0355 }] */
    /* JADX WARNING: Unknown variable types count: 13 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineInit(int r7, java.security.Key r8, java.security.spec.AlgorithmParameterSpec r9, java.security.SecureRandom r10) {
        /*
            r6 = this;
            r0 = 0
            r6.j = r0
            r6.k = r0
            r6.engineParams = r0
            r6.g = r0
            boolean r1 = r8 instanceof javax.crypto.SecretKey
            if (r1 != 0) goto L_0x002d
            java.security.InvalidKeyException r7 = new java.security.InvalidKeyException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Key for algorithm "
            r9.append(r10)
            java.lang.String r8 = r8.getAlgorithm()
            r9.append(r8)
            java.lang.String r8 = " not suitable for symmetric enryption."
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            r7.<init>(r8)
            throw r7
        L_0x002d:
            if (r9 != 0) goto L_0x0045
            org.bouncycastle.crypto.BlockCipher r1 = r6.c
            java.lang.String r1 = r1.getAlgorithmName()
            java.lang.String r2 = "RC5-64"
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L_0x0045
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "RC5 requires an RC5ParametersSpec to be passed in."
            r7.<init>(r8)
            throw r7
        L_0x0045:
            boolean r1 = r8 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey
            r2 = 0
            if (r1 == 0) goto L_0x00ca
            org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey r8 = (org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) r8
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r8.getOID()
            if (r0 == 0) goto L_0x005d
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r8.getOID()
            java.lang.String r0 = r0.getId()
        L_0x005a:
            r6.k = r0
            goto L_0x0062
        L_0x005d:
            java.lang.String r0 = r8.getAlgorithm()
            goto L_0x005a
        L_0x0062:
            org.bouncycastle.crypto.CipherParameters r0 = r8.getParam()
            if (r0 == 0) goto L_0x00a0
            org.bouncycastle.crypto.CipherParameters r8 = r8.getParam()
            boolean r0 = r9 instanceof javax.crypto.spec.IvParameterSpec
            if (r0 == 0) goto L_0x007d
            javax.crypto.spec.IvParameterSpec r9 = (javax.crypto.spec.IvParameterSpec) r9
            org.bouncycastle.crypto.params.ParametersWithIV r0 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r9 = r9.getIV()
            r0.<init>(r8, r9)
        L_0x007b:
            r8 = r0
            goto L_0x00b7
        L_0x007d:
            boolean r0 = r9 instanceof org.bouncycastle.jcajce.spec.GOST28147ParameterSpec
            if (r0 == 0) goto L_0x00b7
            org.bouncycastle.jcajce.spec.GOST28147ParameterSpec r9 = (org.bouncycastle.jcajce.spec.GOST28147ParameterSpec) r9
            org.bouncycastle.crypto.params.ParametersWithSBox r0 = new org.bouncycastle.crypto.params.ParametersWithSBox
            byte[] r1 = r9.getSbox()
            r0.<init>(r8, r1)
            byte[] r8 = r9.getIV()
            if (r8 == 0) goto L_0x007b
            int r8 = r6.h
            if (r8 == 0) goto L_0x007b
            org.bouncycastle.crypto.params.ParametersWithIV r8 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r9 = r9.getIV()
            r8.<init>(r0, r9)
            goto L_0x00b7
        L_0x00a0:
            boolean r0 = r9 instanceof javax.crypto.spec.PBEParameterSpec
            if (r0 == 0) goto L_0x00c2
            r0 = r9
            javax.crypto.spec.PBEParameterSpec r0 = (javax.crypto.spec.PBEParameterSpec) r0
            r6.j = r0
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r0 = r6.e
            org.bouncycastle.crypto.BlockCipher r0 = r0.b()
            java.lang.String r0 = r0.getAlgorithmName()
            org.bouncycastle.crypto.CipherParameters r8 = org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(r8, r9, r0)
        L_0x00b7:
            boolean r9 = r8 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r9 == 0) goto L_0x02d5
        L_0x00bb:
            r9 = r8
            org.bouncycastle.crypto.params.ParametersWithIV r9 = (org.bouncycastle.crypto.params.ParametersWithIV) r9
            r6.f = r9
            goto L_0x02d5
        L_0x00c2:
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "PBE requires PBE parameters to be set."
            r7.<init>(r8)
            throw r7
        L_0x00ca:
            if (r9 != 0) goto L_0x00d8
            org.bouncycastle.crypto.params.KeyParameter r9 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r8 = r8.getEncoded()
            r9.<init>(r8)
        L_0x00d5:
            r8 = r9
            goto L_0x02d5
        L_0x00d8:
            boolean r1 = r9 instanceof javax.crypto.spec.IvParameterSpec
            if (r1 == 0) goto L_0x015a
            int r1 = r6.h
            if (r1 == 0) goto L_0x0139
            javax.crypto.spec.IvParameterSpec r9 = (javax.crypto.spec.IvParameterSpec) r9
            byte[] r1 = r9.getIV()
            int r1 = r1.length
            int r3 = r6.h
            if (r1 == r3) goto L_0x0111
            java.lang.String r1 = r6.l
            boolean r1 = r6.c(r1)
            if (r1 != 0) goto L_0x0111
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "IV must be "
            r8.append(r9)
            int r9 = r6.h
            r8.append(r9)
            java.lang.String r9 = " bytes long."
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x0111:
            boolean r1 = r8 instanceof org.bouncycastle.jcajce.spec.RepeatedSecretKeySpec
            if (r1 == 0) goto L_0x011f
            org.bouncycastle.crypto.params.ParametersWithIV r8 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r9 = r9.getIV()
            r8.<init>(r0, r9)
            goto L_0x00bb
        L_0x011f:
            org.bouncycastle.crypto.params.ParametersWithIV r0 = new org.bouncycastle.crypto.params.ParametersWithIV
            org.bouncycastle.crypto.params.KeyParameter r1 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r8 = r8.getEncoded()
            r1.<init>(r8)
            byte[] r8 = r9.getIV()
            r0.<init>(r1, r8)
            r8 = r0
            org.bouncycastle.crypto.params.ParametersWithIV r8 = (org.bouncycastle.crypto.params.ParametersWithIV) r8
            r6.f = r8
        L_0x0136:
            r8 = r0
            goto L_0x02d5
        L_0x0139:
            java.lang.String r9 = r6.l
            if (r9 == 0) goto L_0x014f
            java.lang.String r9 = r6.l
            java.lang.String r0 = "ECB"
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x014f
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "ECB mode does not use an IV"
            r7.<init>(r8)
            throw r7
        L_0x014f:
            org.bouncycastle.crypto.params.KeyParameter r9 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r8 = r8.getEncoded()
            r9.<init>(r8)
            goto L_0x00d5
        L_0x015a:
            boolean r1 = r9 instanceof org.bouncycastle.jcajce.spec.GOST28147ParameterSpec
            if (r1 == 0) goto L_0x0187
            org.bouncycastle.jcajce.spec.GOST28147ParameterSpec r9 = (org.bouncycastle.jcajce.spec.GOST28147ParameterSpec) r9
            org.bouncycastle.crypto.params.ParametersWithSBox r0 = new org.bouncycastle.crypto.params.ParametersWithSBox
            org.bouncycastle.crypto.params.KeyParameter r1 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r8 = r8.getEncoded()
            r1.<init>(r8)
            byte[] r8 = r9.getSbox()
            r0.<init>(r1, r8)
            byte[] r8 = r9.getIV()
            if (r8 == 0) goto L_0x0136
            int r8 = r6.h
            if (r8 == 0) goto L_0x0136
            org.bouncycastle.crypto.params.ParametersWithIV r8 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r9 = r9.getIV()
            r8.<init>(r0, r9)
            goto L_0x00bb
        L_0x0187:
            boolean r1 = r9 instanceof javax.crypto.spec.RC2ParameterSpec
            if (r1 == 0) goto L_0x01af
            javax.crypto.spec.RC2ParameterSpec r9 = (javax.crypto.spec.RC2ParameterSpec) r9
            org.bouncycastle.crypto.params.RC2Parameters r0 = new org.bouncycastle.crypto.params.RC2Parameters
            byte[] r8 = r8.getEncoded()
            int r1 = r9.getEffectiveKeyBits()
            r0.<init>(r8, r1)
            byte[] r8 = r9.getIV()
            if (r8 == 0) goto L_0x0136
            int r8 = r6.h
            if (r8 == 0) goto L_0x0136
            org.bouncycastle.crypto.params.ParametersWithIV r8 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r9 = r9.getIV()
            r8.<init>(r0, r9)
            goto L_0x00bb
        L_0x01af:
            boolean r1 = r9 instanceof javax.crypto.spec.RC5ParameterSpec
            if (r1 == 0) goto L_0x0259
            javax.crypto.spec.RC5ParameterSpec r9 = (javax.crypto.spec.RC5ParameterSpec) r9
            org.bouncycastle.crypto.params.RC5Parameters r0 = new org.bouncycastle.crypto.params.RC5Parameters
            byte[] r8 = r8.getEncoded()
            int r1 = r9.getRounds()
            r0.<init>(r8, r1)
            org.bouncycastle.crypto.BlockCipher r8 = r6.c
            java.lang.String r8 = r8.getAlgorithmName()
            java.lang.String r1 = "RC5"
            boolean r8 = r8.startsWith(r1)
            if (r8 == 0) goto L_0x0251
            org.bouncycastle.crypto.BlockCipher r8 = r6.c
            java.lang.String r8 = r8.getAlgorithmName()
            java.lang.String r1 = "RC5-32"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0206
            int r8 = r9.getWordSize()
            r1 = 32
            if (r8 == r1) goto L_0x023c
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r10 = "RC5 already set up for a word size of 32 not "
            r8.append(r10)
            int r9 = r9.getWordSize()
            r8.append(r9)
            java.lang.String r9 = "."
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x0206:
            org.bouncycastle.crypto.BlockCipher r8 = r6.c
            java.lang.String r8 = r8.getAlgorithmName()
            java.lang.String r1 = "RC5-64"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x023c
            int r8 = r9.getWordSize()
            r1 = 64
            if (r8 == r1) goto L_0x023c
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r10 = "RC5 already set up for a word size of 64 not "
            r8.append(r10)
            int r9 = r9.getWordSize()
            r8.append(r9)
            java.lang.String r9 = "."
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x023c:
            byte[] r8 = r9.getIV()
            if (r8 == 0) goto L_0x0136
            int r8 = r6.h
            if (r8 == 0) goto L_0x0136
            org.bouncycastle.crypto.params.ParametersWithIV r8 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r9 = r9.getIV()
            r8.<init>(r0, r9)
            goto L_0x00bb
        L_0x0251:
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "RC5 parameters passed to a cipher that is not RC5."
            r7.<init>(r8)
            throw r7
        L_0x0259:
            java.lang.Class r1 = a
            if (r1 == 0) goto L_0x0368
            java.lang.Class r1 = a
            boolean r1 = r1.isInstance(r9)
            if (r1 == 0) goto L_0x0368
            java.lang.String r1 = r6.l
            boolean r1 = r6.c(r1)
            if (r1 != 0) goto L_0x027b
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r1 = r6.e
            boolean r1 = r1 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher
            if (r1 != 0) goto L_0x027b
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "GCMParameterSpec can only be used with AEAD modes."
            r7.<init>(r8)
            throw r7
        L_0x027b:
            java.lang.Class r1 = a     // Catch:{ Exception -> 0x0360 }
            java.lang.String r3 = "getTLen"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0360 }
            java.lang.reflect.Method r1 = r1.getDeclaredMethod(r3, r4)     // Catch:{ Exception -> 0x0360 }
            java.lang.Class r3 = a     // Catch:{ Exception -> 0x0360 }
            java.lang.String r4 = "getIV"
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0360 }
            java.lang.reflect.Method r3 = r3.getDeclaredMethod(r4, r5)     // Catch:{ Exception -> 0x0360 }
            boolean r4 = r8 instanceof org.bouncycastle.jcajce.spec.RepeatedSecretKeySpec     // Catch:{ Exception -> 0x0360 }
            if (r4 == 0) goto L_0x02af
            org.bouncycastle.crypto.params.AEADParameters r8 = new org.bouncycastle.crypto.params.AEADParameters     // Catch:{ Exception -> 0x0360 }
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0360 }
            java.lang.Object r1 = r1.invoke(r9, r4)     // Catch:{ Exception -> 0x0360 }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ Exception -> 0x0360 }
            int r1 = r1.intValue()     // Catch:{ Exception -> 0x0360 }
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0360 }
            java.lang.Object r9 = r3.invoke(r9, r4)     // Catch:{ Exception -> 0x0360 }
            byte[] r9 = (byte[]) r9     // Catch:{ Exception -> 0x0360 }
            r8.<init>(r0, r1, r9)     // Catch:{ Exception -> 0x0360 }
            r6.g = r8     // Catch:{ Exception -> 0x0360 }
            goto L_0x02d5
        L_0x02af:
            org.bouncycastle.crypto.params.AEADParameters r0 = new org.bouncycastle.crypto.params.AEADParameters     // Catch:{ Exception -> 0x0360 }
            org.bouncycastle.crypto.params.KeyParameter r4 = new org.bouncycastle.crypto.params.KeyParameter     // Catch:{ Exception -> 0x0360 }
            byte[] r8 = r8.getEncoded()     // Catch:{ Exception -> 0x0360 }
            r4.<init>(r8)     // Catch:{ Exception -> 0x0360 }
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0360 }
            java.lang.Object r8 = r1.invoke(r9, r8)     // Catch:{ Exception -> 0x0360 }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ Exception -> 0x0360 }
            int r8 = r8.intValue()     // Catch:{ Exception -> 0x0360 }
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0360 }
            java.lang.Object r9 = r3.invoke(r9, r1)     // Catch:{ Exception -> 0x0360 }
            byte[] r9 = (byte[]) r9     // Catch:{ Exception -> 0x0360 }
            r0.<init>(r4, r8, r9)     // Catch:{ Exception -> 0x0360 }
            r6.g = r0     // Catch:{ Exception -> 0x0360 }
            goto L_0x0136
        L_0x02d5:
            int r9 = r6.h
            r0 = 1
            if (r9 == 0) goto L_0x031d
            boolean r9 = r8 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r9 != 0) goto L_0x031d
            boolean r9 = r8 instanceof org.bouncycastle.crypto.params.AEADParameters
            if (r9 != 0) goto L_0x031d
            if (r10 != 0) goto L_0x02ea
            java.security.SecureRandom r9 = new java.security.SecureRandom
            r9.<init>()
            goto L_0x02eb
        L_0x02ea:
            r9 = r10
        L_0x02eb:
            if (r7 == r0) goto L_0x030b
            r1 = 3
            if (r7 != r1) goto L_0x02f1
            goto L_0x030b
        L_0x02f1:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r9 = r6.e
            org.bouncycastle.crypto.BlockCipher r9 = r9.b()
            java.lang.String r9 = r9.getAlgorithmName()
            java.lang.String r1 = "PGPCFB"
            int r9 = r9.indexOf(r1)
            if (r9 >= 0) goto L_0x031d
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "no IV set when one expected"
            r7.<init>(r8)
            throw r7
        L_0x030b:
            int r1 = r6.h
            byte[] r1 = new byte[r1]
            r9.nextBytes(r1)
            org.bouncycastle.crypto.params.ParametersWithIV r9 = new org.bouncycastle.crypto.params.ParametersWithIV
            r9.<init>(r8, r1)
            r8 = r9
            org.bouncycastle.crypto.params.ParametersWithIV r8 = (org.bouncycastle.crypto.params.ParametersWithIV) r8
            r6.f = r8
            r8 = r9
        L_0x031d:
            if (r10 == 0) goto L_0x0329
            boolean r9 = r6.i
            if (r9 == 0) goto L_0x0329
            org.bouncycastle.crypto.params.ParametersWithRandom r9 = new org.bouncycastle.crypto.params.ParametersWithRandom
            r9.<init>(r8, r10)
            r8 = r9
        L_0x0329:
            switch(r7) {
                case 1: goto L_0x0335;
                case 2: goto L_0x032f;
                case 3: goto L_0x0335;
                case 4: goto L_0x032f;
                default: goto L_0x032c;
            }
        L_0x032c:
            java.security.InvalidParameterException r8 = new java.security.InvalidParameterException     // Catch:{ Exception -> 0x0355 }
            goto L_0x033b
        L_0x032f:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r6.e     // Catch:{ Exception -> 0x0355 }
            r7.a(r2, r8)     // Catch:{ Exception -> 0x0355 }
            return
        L_0x0335:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r6.e     // Catch:{ Exception -> 0x0355 }
            r7.a(r0, r8)     // Catch:{ Exception -> 0x0355 }
            return
        L_0x033b:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0355 }
            r9.<init>()     // Catch:{ Exception -> 0x0355 }
            java.lang.String r10 = "unknown opmode "
            r9.append(r10)     // Catch:{ Exception -> 0x0355 }
            r9.append(r7)     // Catch:{ Exception -> 0x0355 }
            java.lang.String r7 = " passed"
            r9.append(r7)     // Catch:{ Exception -> 0x0355 }
            java.lang.String r7 = r9.toString()     // Catch:{ Exception -> 0x0355 }
            r8.<init>(r7)     // Catch:{ Exception -> 0x0355 }
            throw r8     // Catch:{ Exception -> 0x0355 }
        L_0x0355:
            r7 = move-exception
            java.security.InvalidKeyException r8 = new java.security.InvalidKeyException
            java.lang.String r7 = r7.getMessage()
            r8.<init>(r7)
            throw r8
        L_0x0360:
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "Cannot process GCMParameterSpec."
            r7.<init>(r8)
            throw r7
        L_0x0368:
            java.security.InvalidAlgorithmParameterException r7 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "unknown parameter type."
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.engineInit(int, java.security.Key, java.security.spec.AlgorithmParameterSpec, java.security.SecureRandom):void");
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String str) {
        GenericBlockCipher aEADGenericBlockCipher;
        BufferedGenericBlockCipher bufferedGenericBlockCipher;
        this.l = Strings.toUpperCase(str);
        if (this.l.equals("ECB")) {
            this.h = 0;
            aEADGenericBlockCipher = new BufferedGenericBlockCipher(this.c);
        } else if (this.l.equals("CBC")) {
            this.h = this.c.getBlockSize();
            aEADGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new CBCBlockCipher(this.c));
        } else {
            if (this.l.startsWith("OFB")) {
                this.h = this.c.getBlockSize();
                if (this.l.length() != 3) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new OFBBlockCipher(this.c, Integer.parseInt(this.l.substring(3))));
                } else {
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new OFBBlockCipher(this.c, this.c.getBlockSize() * 8));
                }
            } else if (this.l.startsWith("CFB")) {
                this.h = this.c.getBlockSize();
                if (this.l.length() != 3) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new CFBBlockCipher(this.c, Integer.parseInt(this.l.substring(3))));
                } else {
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new CFBBlockCipher(this.c, this.c.getBlockSize() * 8));
                }
            } else if (this.l.startsWith("PGP")) {
                boolean equalsIgnoreCase = this.l.equalsIgnoreCase("PGPCFBwithIV");
                this.h = this.c.getBlockSize();
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new PGPCFBBlockCipher(this.c, equalsIgnoreCase));
            } else if (this.l.equalsIgnoreCase("OpenPGPCFB")) {
                this.h = 0;
                aEADGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new OpenPGPCFBBlockCipher(this.c));
            } else if (this.l.startsWith("SIC")) {
                this.h = this.c.getBlockSize();
                if (this.h < 16) {
                    throw new IllegalArgumentException("Warning: SIC-Mode can become a twotime-pad if the blocksize of the cipher is too small. Use a cipher with a block size of at least 128 bits (e.g. AES)");
                }
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.c)));
            } else if (this.l.startsWith("CTR")) {
                this.h = this.c.getBlockSize();
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.c)));
            } else if (this.l.startsWith("GOFB")) {
                this.h = this.c.getBlockSize();
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GOFBBlockCipher(this.c)));
            } else if (this.l.startsWith("GCFB")) {
                this.h = this.c.getBlockSize();
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GCFBBlockCipher(this.c)));
            } else if (this.l.startsWith("CTS")) {
                this.h = this.c.getBlockSize();
                aEADGenericBlockCipher = new BufferedGenericBlockCipher((BufferedBlockCipher) new CTSBlockCipher(new CBCBlockCipher(this.c)));
            } else if (this.l.startsWith("CCM")) {
                this.h = 13;
                aEADGenericBlockCipher = new AEADGenericBlockCipher(new CCMBlockCipher(this.c));
            } else if (this.l.startsWith("OCB")) {
                if (this.d != null) {
                    this.h = 15;
                    aEADGenericBlockCipher = new AEADGenericBlockCipher(new OCBBlockCipher(this.c, this.d.get()));
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("can't support mode ");
                    sb.append(str);
                    throw new NoSuchAlgorithmException(sb.toString());
                }
            } else if (this.l.startsWith("EAX")) {
                this.h = this.c.getBlockSize();
                aEADGenericBlockCipher = new AEADGenericBlockCipher(new EAXBlockCipher(this.c));
            } else if (this.l.startsWith(GoogleCloudMessaging.INSTANCE_ID_SCOPE)) {
                this.h = this.c.getBlockSize();
                aEADGenericBlockCipher = new AEADGenericBlockCipher(new GCMBlockCipher(this.c));
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("can't support mode ");
                sb2.append(str);
                throw new NoSuchAlgorithmException(sb2.toString());
            }
            this.e = bufferedGenericBlockCipher;
            return;
        }
        this.e = aEADGenericBlockCipher;
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String str) {
        BufferedGenericBlockCipher bufferedGenericBlockCipher;
        String upperCase = Strings.toUpperCase(str);
        if (upperCase.equals("NOPADDING")) {
            if (this.e.a()) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(this.e.b()));
            } else {
                return;
            }
        } else if (upperCase.equals("WITHCTS")) {
            bufferedGenericBlockCipher = new BufferedGenericBlockCipher((BufferedBlockCipher) new CTSBlockCipher(this.e.b()));
        } else {
            this.i = true;
            if (c(this.l)) {
                throw new NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
            } else if (upperCase.equals("PKCS5PADDING") || upperCase.equals("PKCS7PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.e.b());
            } else if (upperCase.equals("ZEROBYTEPADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.e.b(), new ZeroBytePadding());
            } else if (upperCase.equals("ISO10126PADDING") || upperCase.equals("ISO10126-2PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.e.b(), new ISO10126d2Padding());
            } else if (upperCase.equals("X9.23PADDING") || upperCase.equals("X923PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.e.b(), new X923Padding());
            } else if (upperCase.equals("ISO7816-4PADDING") || upperCase.equals("ISO9797-1PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.e.b(), new ISO7816d4Padding());
            } else if (upperCase.equals("TBCPADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.e.b(), new TBCPadding());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Padding ");
                sb.append(str);
                sb.append(" unknown.");
                throw new NoSuchPaddingException(sb.toString());
            }
        }
        this.e = bufferedGenericBlockCipher;
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        try {
            return this.e.a(bArr, i2, i3, bArr2, i4);
        } catch (DataLengthException e2) {
            throw new ShortBufferException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] bArr, int i2, int i3) {
        int b2 = this.e.b(i3);
        if (b2 > 0) {
            byte[] bArr2 = new byte[b2];
            int a2 = this.e.a(bArr, i2, i3, bArr2, 0);
            if (a2 == 0) {
                return null;
            }
            if (a2 == bArr2.length) {
                return bArr2;
            }
            byte[] bArr3 = new byte[a2];
            System.arraycopy(bArr2, 0, bArr3, 0, a2);
            return bArr3;
        }
        this.e.a(bArr, i2, i3, null, 0);
        return null;
    }

    /* access modifiers changed from: protected */
    public void engineUpdateAAD(ByteBuffer byteBuffer) {
        engineUpdateAAD(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.limit() - byteBuffer.position());
    }

    /* access modifiers changed from: protected */
    public void engineUpdateAAD(byte[] bArr, int i2, int i3) {
        this.e.a(bArr, i2, i3);
    }
}
