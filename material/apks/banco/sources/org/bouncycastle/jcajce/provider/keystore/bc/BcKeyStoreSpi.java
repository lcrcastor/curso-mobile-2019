package org.bouncycastle.jcajce.provider.keystore.bc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import org.bouncycastle.crypto.io.DigestInputStream;
import org.bouncycastle.crypto.io.DigestOutputStream;
import org.bouncycastle.crypto.io.MacInputStream;
import org.bouncycastle.crypto.io.MacOutputStream;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.jce.interfaces.BCKeyStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;
import org.bouncycastle.util.io.TeeOutputStream;

public class BcKeyStoreSpi extends KeyStoreSpi implements BCKeyStore {
    protected SecureRandom random = new SecureRandom();
    protected Hashtable table = new Hashtable();
    protected int version;

    public static class BouncyCastleStore extends BcKeyStoreSpi {
        public BouncyCastleStore() {
            super(1);
        }

        public void engineLoad(InputStream inputStream, char[] cArr) {
            this.table.clear();
            if (inputStream != null) {
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                int readInt = dataInputStream.readInt();
                if (readInt == 2 || readInt == 0 || readInt == 1) {
                    byte[] bArr = new byte[dataInputStream.readInt()];
                    if (bArr.length != 20) {
                        throw new IOException("Key store corrupted.");
                    }
                    dataInputStream.readFully(bArr);
                    int readInt2 = dataInputStream.readInt();
                    if (readInt2 < 0 || readInt2 > 4096) {
                        throw new IOException("Key store corrupted.");
                    }
                    CipherInputStream cipherInputStream = new CipherInputStream(dataInputStream, makePBECipher(readInt == 0 ? "OldPBEWithSHAAndTwofish-CBC" : "PBEWithSHAAndTwofish-CBC", 2, cArr, bArr, readInt2));
                    SHA1Digest sHA1Digest = new SHA1Digest();
                    loadStore(new DigestInputStream(cipherInputStream, sHA1Digest));
                    byte[] bArr2 = new byte[sHA1Digest.getDigestSize()];
                    sHA1Digest.doFinal(bArr2, 0);
                    byte[] bArr3 = new byte[sHA1Digest.getDigestSize()];
                    Streams.readFully(cipherInputStream, bArr3);
                    if (!Arrays.constantTimeAreEqual(bArr2, bArr3)) {
                        this.table.clear();
                        throw new IOException("KeyStore integrity check failed.");
                    }
                    return;
                }
                throw new IOException("Wrong version of key store.");
            }
        }

        public void engineStore(OutputStream outputStream, char[] cArr) {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            byte[] bArr = new byte[20];
            int nextInt = (this.random.nextInt() & 1023) + 1024;
            this.random.nextBytes(bArr);
            dataOutputStream.writeInt(this.version);
            dataOutputStream.writeInt(bArr.length);
            dataOutputStream.write(bArr);
            dataOutputStream.writeInt(nextInt);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(dataOutputStream, makePBECipher("PBEWithSHAAndTwofish-CBC", 1, cArr, bArr, nextInt));
            DigestOutputStream digestOutputStream = new DigestOutputStream(new SHA1Digest());
            saveStore(new TeeOutputStream(cipherOutputStream, digestOutputStream));
            cipherOutputStream.write(digestOutputStream.getDigest());
            cipherOutputStream.close();
        }
    }

    public static class Std extends BcKeyStoreSpi {
        public Std() {
            super(2);
        }
    }

    class StoreEntry {
        int a;
        String b;
        Object c;
        Certificate[] d;
        Date e;

        StoreEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
            this.e = new Date();
            this.a = 4;
            this.b = str;
            this.d = certificateArr;
            byte[] bArr = new byte[20];
            BcKeyStoreSpi.this.random.setSeed(System.currentTimeMillis());
            BcKeyStoreSpi.this.random.nextBytes(bArr);
            int nextInt = (BcKeyStoreSpi.this.random.nextInt() & 1023) + 1024;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(bArr.length);
            dataOutputStream.write(bArr);
            dataOutputStream.writeInt(nextInt);
            DataOutputStream dataOutputStream2 = new DataOutputStream(new CipherOutputStream(dataOutputStream, BcKeyStoreSpi.this.makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, cArr, bArr, nextInt)));
            BcKeyStoreSpi.this.a(key, dataOutputStream2);
            dataOutputStream2.close();
            this.c = byteArrayOutputStream.toByteArray();
        }

        StoreEntry(String str, Certificate certificate) {
            this.e = new Date();
            this.a = 1;
            this.b = str;
            this.c = certificate;
            this.d = null;
        }

        StoreEntry(String str, Date date, int i, Object obj) {
            this.e = new Date();
            this.b = str;
            this.e = date;
            this.a = i;
            this.c = obj;
        }

        StoreEntry(String str, Date date, int i, Object obj, Certificate[] certificateArr) {
            this.e = new Date();
            this.b = str;
            this.e = date;
            this.a = i;
            this.c = obj;
            this.d = certificateArr;
        }

        StoreEntry(String str, byte[] bArr, Certificate[] certificateArr) {
            this.e = new Date();
            this.a = 3;
            this.b = str;
            this.c = bArr;
            this.d = certificateArr;
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Can't wrap try/catch for region: R(6:15|16|17|18|19|(2:23|24)(2:25|26)) */
        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r1 = new java.io.DataInputStream(new java.io.ByteArrayInputStream((byte[]) r9.c));
            r0 = new byte[r1.readInt()];
            r1.readFully(r0);
            r8 = r1.readInt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            r1 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.a(r9.f, new java.io.DataInputStream(new javax.crypto.CipherInputStream(r1, r9.f.makePBECipher("BrokenPBEWithSHAAnd3-KeyTripleDES-CBC", 2, r10, r0, r8))));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0080, code lost:
            r6 = r0;
            r7 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r1 = new java.io.DataInputStream(new java.io.ByteArrayInputStream((byte[]) r9.c));
            r0 = new byte[r1.readInt()];
            r1.readFully(r0);
            r8 = r1.readInt();
            r1 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.a(r9.f, new java.io.DataInputStream(new javax.crypto.CipherInputStream(r1, r9.f.makePBECipher("OldPBEWithSHAAnd3-KeyTripleDES-CBC", 2, r10, r0, r8))));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x00bb, code lost:
            if (r1 != null) goto L_0x00bd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00bd, code lost:
            r0 = new java.io.ByteArrayOutputStream();
            r8 = new java.io.DataOutputStream(r0);
            r8.writeInt(r6.length);
            r8.write(r6);
            r8.writeInt(r7);
            r10 = new java.io.DataOutputStream(new javax.crypto.CipherOutputStream(r8, r9.f.makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, r10, r6, r7)));
            org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.a(r9.f, r1, r10);
            r10.close();
            r9.c = r0.toByteArray();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x00f3, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00fb, code lost:
            throw new java.security.UnrecoverableKeyException("no match");
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0083 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object a(char[] r10) {
            /*
                r9 = this;
                if (r10 == 0) goto L_0x0005
                int r0 = r10.length
                if (r0 != 0) goto L_0x000e
            L_0x0005:
                java.lang.Object r0 = r9.c
                boolean r0 = r0 instanceof java.security.Key
                if (r0 == 0) goto L_0x000e
                java.lang.Object r10 = r9.c
                return r10
            L_0x000e:
                int r0 = r9.a
                r1 = 4
                if (r0 != r1) goto L_0x0104
                java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
                java.lang.Object r1 = r9.c
                byte[] r1 = (byte[]) r1
                r0.<init>(r1)
                java.io.DataInputStream r1 = new java.io.DataInputStream
                r1.<init>(r0)
                int r0 = r1.readInt()     // Catch:{ Exception -> 0x00fc }
                byte[] r6 = new byte[r0]     // Catch:{ Exception -> 0x00fc }
                r1.readFully(r6)     // Catch:{ Exception -> 0x00fc }
                int r7 = r1.readInt()     // Catch:{ Exception -> 0x00fc }
                org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi r2 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this     // Catch:{ Exception -> 0x00fc }
                java.lang.String r3 = "PBEWithSHAAnd3-KeyTripleDES-CBC"
                r4 = 2
                r5 = r10
                javax.crypto.Cipher r0 = r2.makePBECipher(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00fc }
                javax.crypto.CipherInputStream r2 = new javax.crypto.CipherInputStream     // Catch:{ Exception -> 0x00fc }
                r2.<init>(r1, r0)     // Catch:{ Exception -> 0x00fc }
                org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi r0 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this     // Catch:{ Exception -> 0x0049 }
                java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch:{ Exception -> 0x0049 }
                r1.<init>(r2)     // Catch:{ Exception -> 0x0049 }
                java.security.Key r0 = r0.b(r1)     // Catch:{ Exception -> 0x0049 }
                return r0
            L_0x0049:
                java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x00fc }
                java.lang.Object r1 = r9.c     // Catch:{ Exception -> 0x00fc }
                byte[] r1 = (byte[]) r1     // Catch:{ Exception -> 0x00fc }
                r0.<init>(r1)     // Catch:{ Exception -> 0x00fc }
                java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch:{ Exception -> 0x00fc }
                r1.<init>(r0)     // Catch:{ Exception -> 0x00fc }
                int r0 = r1.readInt()     // Catch:{ Exception -> 0x00fc }
                byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x00fc }
                r1.readFully(r0)     // Catch:{ Exception -> 0x00fc }
                int r8 = r1.readInt()     // Catch:{ Exception -> 0x00fc }
                org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi r2 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this     // Catch:{ Exception -> 0x00fc }
                java.lang.String r3 = "BrokenPBEWithSHAAnd3-KeyTripleDES-CBC"
                r4 = 2
                r5 = r10
                r6 = r0
                r7 = r8
                javax.crypto.Cipher r2 = r2.makePBECipher(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00fc }
                javax.crypto.CipherInputStream r3 = new javax.crypto.CipherInputStream     // Catch:{ Exception -> 0x00fc }
                r3.<init>(r1, r2)     // Catch:{ Exception -> 0x00fc }
                org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi r1 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this     // Catch:{ Exception -> 0x0083 }
                java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ Exception -> 0x0083 }
                r2.<init>(r3)     // Catch:{ Exception -> 0x0083 }
                java.security.Key r1 = r1.b(r2)     // Catch:{ Exception -> 0x0083 }
            L_0x0080:
                r6 = r0
                r7 = r8
                goto L_0x00bb
            L_0x0083:
                java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x00fc }
                java.lang.Object r1 = r9.c     // Catch:{ Exception -> 0x00fc }
                byte[] r1 = (byte[]) r1     // Catch:{ Exception -> 0x00fc }
                r0.<init>(r1)     // Catch:{ Exception -> 0x00fc }
                java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch:{ Exception -> 0x00fc }
                r1.<init>(r0)     // Catch:{ Exception -> 0x00fc }
                int r0 = r1.readInt()     // Catch:{ Exception -> 0x00fc }
                byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x00fc }
                r1.readFully(r0)     // Catch:{ Exception -> 0x00fc }
                int r8 = r1.readInt()     // Catch:{ Exception -> 0x00fc }
                org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi r2 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this     // Catch:{ Exception -> 0x00fc }
                java.lang.String r3 = "OldPBEWithSHAAnd3-KeyTripleDES-CBC"
                r4 = 2
                r5 = r10
                r6 = r0
                r7 = r8
                javax.crypto.Cipher r2 = r2.makePBECipher(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00fc }
                javax.crypto.CipherInputStream r3 = new javax.crypto.CipherInputStream     // Catch:{ Exception -> 0x00fc }
                r3.<init>(r1, r2)     // Catch:{ Exception -> 0x00fc }
                org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi r1 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this     // Catch:{ Exception -> 0x00fc }
                java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ Exception -> 0x00fc }
                r2.<init>(r3)     // Catch:{ Exception -> 0x00fc }
                java.security.Key r1 = r1.b(r2)     // Catch:{ Exception -> 0x00fc }
                goto L_0x0080
            L_0x00bb:
                if (r1 == 0) goto L_0x00f4
                java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00fc }
                r0.<init>()     // Catch:{ Exception -> 0x00fc }
                java.io.DataOutputStream r8 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x00fc }
                r8.<init>(r0)     // Catch:{ Exception -> 0x00fc }
                int r2 = r6.length     // Catch:{ Exception -> 0x00fc }
                r8.writeInt(r2)     // Catch:{ Exception -> 0x00fc }
                r8.write(r6)     // Catch:{ Exception -> 0x00fc }
                r8.writeInt(r7)     // Catch:{ Exception -> 0x00fc }
                org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi r2 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this     // Catch:{ Exception -> 0x00fc }
                java.lang.String r3 = "PBEWithSHAAnd3-KeyTripleDES-CBC"
                r4 = 1
                r5 = r10
                javax.crypto.Cipher r10 = r2.makePBECipher(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00fc }
                javax.crypto.CipherOutputStream r2 = new javax.crypto.CipherOutputStream     // Catch:{ Exception -> 0x00fc }
                r2.<init>(r8, r10)     // Catch:{ Exception -> 0x00fc }
                java.io.DataOutputStream r10 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x00fc }
                r10.<init>(r2)     // Catch:{ Exception -> 0x00fc }
                org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi r2 = org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.this     // Catch:{ Exception -> 0x00fc }
                r2.a(r1, r10)     // Catch:{ Exception -> 0x00fc }
                r10.close()     // Catch:{ Exception -> 0x00fc }
                byte[] r10 = r0.toByteArray()     // Catch:{ Exception -> 0x00fc }
                r9.c = r10     // Catch:{ Exception -> 0x00fc }
                return r1
            L_0x00f4:
                java.security.UnrecoverableKeyException r10 = new java.security.UnrecoverableKeyException     // Catch:{ Exception -> 0x00fc }
                java.lang.String r0 = "no match"
                r10.<init>(r0)     // Catch:{ Exception -> 0x00fc }
                throw r10     // Catch:{ Exception -> 0x00fc }
            L_0x00fc:
                java.security.UnrecoverableKeyException r10 = new java.security.UnrecoverableKeyException
                java.lang.String r0 = "no match"
                r10.<init>(r0)
                throw r10
            L_0x0104:
                java.lang.RuntimeException r10 = new java.lang.RuntimeException
                java.lang.String r0 = "forget something!"
                r10.<init>(r0)
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi.StoreEntry.a(char[]):java.lang.Object");
        }

        /* access modifiers changed from: 0000 */
        public String b() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public Object c() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public Certificate[] d() {
            return this.d;
        }

        /* access modifiers changed from: 0000 */
        public Date e() {
            return this.e;
        }
    }

    public static class Version1 extends BcKeyStoreSpi {
        public Version1() {
            super(1);
        }
    }

    public BcKeyStoreSpi(int i) {
        this.version = i;
    }

    private Certificate a(DataInputStream dataInputStream) {
        String readUTF = dataInputStream.readUTF();
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bArr);
        try {
            return CertificateFactory.getInstance(readUTF, BouncyCastleProvider.PROVIDER_NAME).generateCertificate(new ByteArrayInputStream(bArr));
        } catch (NoSuchProviderException e) {
            throw new IOException(e.toString());
        } catch (CertificateException e2) {
            throw new IOException(e2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void a(Key key, DataOutputStream dataOutputStream) {
        byte[] encoded = key.getEncoded();
        int i = key instanceof PrivateKey ? 0 : key instanceof PublicKey ? 1 : 2;
        dataOutputStream.write(i);
        dataOutputStream.writeUTF(key.getFormat());
        dataOutputStream.writeUTF(key.getAlgorithm());
        dataOutputStream.writeInt(encoded.length);
        dataOutputStream.write(encoded);
    }

    private void a(Certificate certificate, DataOutputStream dataOutputStream) {
        try {
            byte[] encoded = certificate.getEncoded();
            dataOutputStream.writeUTF(certificate.getType());
            dataOutputStream.writeInt(encoded.length);
            dataOutputStream.write(encoded);
        } catch (CertificateEncodingException e) {
            throw new IOException(e.toString());
        }
    }

    /* access modifiers changed from: private */
    public Key b(DataInputStream dataInputStream) {
        KeySpec keySpec;
        int read = dataInputStream.read();
        String readUTF = dataInputStream.readUTF();
        String readUTF2 = dataInputStream.readUTF();
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bArr);
        if (readUTF.equals("PKCS#8") || readUTF.equals("PKCS8")) {
            keySpec = new PKCS8EncodedKeySpec(bArr);
        } else if (readUTF.equals("X.509") || readUTF.equals("X509")) {
            keySpec = new X509EncodedKeySpec(bArr);
        } else if (readUTF.equals("RAW")) {
            return new SecretKeySpec(bArr, readUTF2);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Key format ");
            sb.append(readUTF);
            sb.append(" not recognised!");
            throw new IOException(sb.toString());
        }
        switch (read) {
            case 0:
                return KeyFactory.getInstance(readUTF2, BouncyCastleProvider.PROVIDER_NAME).generatePrivate(keySpec);
            case 1:
                return KeyFactory.getInstance(readUTF2, BouncyCastleProvider.PROVIDER_NAME).generatePublic(keySpec);
            case 2:
                return SecretKeyFactory.getInstance(readUTF2, BouncyCastleProvider.PROVIDER_NAME).generateSecret(keySpec);
            default:
                try {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Key type ");
                    sb2.append(read);
                    sb2.append(" not recognised!");
                    throw new IOException(sb2.toString());
                } catch (Exception e) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Exception creating key: ");
                    sb3.append(e.toString());
                    throw new IOException(sb3.toString());
                }
        }
    }

    public Enumeration engineAliases() {
        return this.table.keys();
    }

    public boolean engineContainsAlias(String str) {
        return this.table.get(str) != null;
    }

    public void engineDeleteEntry(String str) {
        if (this.table.get(str) != null) {
            this.table.remove(str);
        }
    }

    public Certificate engineGetCertificate(String str) {
        StoreEntry storeEntry = (StoreEntry) this.table.get(str);
        if (storeEntry != null) {
            if (storeEntry.a() == 1) {
                return (Certificate) storeEntry.c();
            }
            Certificate[] d = storeEntry.d();
            if (d != null) {
                return d[0];
            }
        }
        return null;
    }

    public String engineGetCertificateAlias(Certificate certificate) {
        Enumeration elements = this.table.elements();
        while (elements.hasMoreElements()) {
            StoreEntry storeEntry = (StoreEntry) elements.nextElement();
            if (!(storeEntry.c() instanceof Certificate)) {
                Certificate[] d = storeEntry.d();
                if (d != null && d[0].equals(certificate)) {
                    return storeEntry.b();
                }
            } else if (((Certificate) storeEntry.c()).equals(certificate)) {
                return storeEntry.b();
            }
        }
        return null;
    }

    public Certificate[] engineGetCertificateChain(String str) {
        StoreEntry storeEntry = (StoreEntry) this.table.get(str);
        if (storeEntry != null) {
            return storeEntry.d();
        }
        return null;
    }

    public Date engineGetCreationDate(String str) {
        StoreEntry storeEntry = (StoreEntry) this.table.get(str);
        if (storeEntry != null) {
            return storeEntry.e();
        }
        return null;
    }

    public Key engineGetKey(String str, char[] cArr) {
        StoreEntry storeEntry = (StoreEntry) this.table.get(str);
        if (storeEntry == null || storeEntry.a() == 1) {
            return null;
        }
        return (Key) storeEntry.a(cArr);
    }

    public boolean engineIsCertificateEntry(String str) {
        StoreEntry storeEntry = (StoreEntry) this.table.get(str);
        return storeEntry != null && storeEntry.a() == 1;
    }

    public boolean engineIsKeyEntry(String str) {
        StoreEntry storeEntry = (StoreEntry) this.table.get(str);
        return (storeEntry == null || storeEntry.a() == 1) ? false : true;
    }

    public void engineLoad(InputStream inputStream, char[] cArr) {
        this.table.clear();
        if (inputStream != null) {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            int readInt = dataInputStream.readInt();
            if (readInt == 2 || readInt == 0 || readInt == 1) {
                int readInt2 = dataInputStream.readInt();
                if (readInt2 <= 0) {
                    throw new IOException("Invalid salt detected");
                }
                byte[] bArr = new byte[readInt2];
                dataInputStream.readFully(bArr);
                int readInt3 = dataInputStream.readInt();
                HMac hMac = new HMac(new SHA1Digest());
                if (cArr == null || cArr.length == 0) {
                    loadStore(dataInputStream);
                    dataInputStream.readFully(new byte[hMac.getMacSize()]);
                } else {
                    byte[] PKCS12PasswordToBytes = PBEParametersGenerator.PKCS12PasswordToBytes(cArr);
                    PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(new SHA1Digest());
                    pKCS12ParametersGenerator.init(PKCS12PasswordToBytes, bArr, readInt3);
                    CipherParameters generateDerivedMacParameters = pKCS12ParametersGenerator.generateDerivedMacParameters(readInt != 2 ? hMac.getMacSize() : hMac.getMacSize() * 8);
                    Arrays.fill(PKCS12PasswordToBytes, 0);
                    hMac.init(generateDerivedMacParameters);
                    loadStore(new MacInputStream(dataInputStream, hMac));
                    byte[] bArr2 = new byte[hMac.getMacSize()];
                    hMac.doFinal(bArr2, 0);
                    byte[] bArr3 = new byte[hMac.getMacSize()];
                    dataInputStream.readFully(bArr3);
                    if (!Arrays.constantTimeAreEqual(bArr2, bArr3)) {
                        this.table.clear();
                        throw new IOException("KeyStore integrity check failed.");
                    }
                }
                return;
            }
            throw new IOException("Wrong version of key store.");
        }
    }

    public void engineSetCertificateEntry(String str, Certificate certificate) {
        StoreEntry storeEntry = (StoreEntry) this.table.get(str);
        if (storeEntry == null || storeEntry.a() == 1) {
            this.table.put(str, new StoreEntry(str, certificate));
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("key store already has a key entry with alias ");
        sb.append(str);
        throw new KeyStoreException(sb.toString());
    }

    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
        if (!(key instanceof PrivateKey) || certificateArr != null) {
            try {
                Hashtable hashtable = this.table;
                StoreEntry storeEntry = new StoreEntry(str, key, cArr, certificateArr);
                hashtable.put(str, storeEntry);
            } catch (Exception e) {
                throw new KeyStoreException(e.toString());
            }
        } else {
            throw new KeyStoreException("no certificate chain for private key");
        }
    }

    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
        this.table.put(str, new StoreEntry(str, bArr, certificateArr));
    }

    public int engineSize() {
        return this.table.size();
    }

    public void engineStore(OutputStream outputStream, char[] cArr) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        byte[] bArr = new byte[20];
        int nextInt = (this.random.nextInt() & 1023) + 1024;
        this.random.nextBytes(bArr);
        dataOutputStream.writeInt(this.version);
        dataOutputStream.writeInt(bArr.length);
        dataOutputStream.write(bArr);
        dataOutputStream.writeInt(nextInt);
        HMac hMac = new HMac(new SHA1Digest());
        MacOutputStream macOutputStream = new MacOutputStream(hMac);
        PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(new SHA1Digest());
        byte[] PKCS12PasswordToBytes = PBEParametersGenerator.PKCS12PasswordToBytes(cArr);
        pKCS12ParametersGenerator.init(PKCS12PasswordToBytes, bArr, nextInt);
        hMac.init(pKCS12ParametersGenerator.generateDerivedMacParameters(this.version < 2 ? hMac.getMacSize() : hMac.getMacSize() * 8));
        for (int i = 0; i != PKCS12PasswordToBytes.length; i++) {
            PKCS12PasswordToBytes[i] = 0;
        }
        saveStore(new TeeOutputStream(dataOutputStream, macOutputStream));
        byte[] bArr2 = new byte[hMac.getMacSize()];
        hMac.doFinal(bArr2, 0);
        dataOutputStream.write(bArr2);
        dataOutputStream.close();
    }

    /* access modifiers changed from: protected */
    public void loadStore(InputStream inputStream) {
        StoreEntry storeEntry;
        Hashtable hashtable;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        for (int read = dataInputStream.read(); read > 0; read = dataInputStream.read()) {
            String readUTF = dataInputStream.readUTF();
            Date date = new Date(dataInputStream.readLong());
            int readInt = dataInputStream.readInt();
            Certificate[] certificateArr = null;
            if (readInt != 0) {
                certificateArr = new Certificate[readInt];
                for (int i = 0; i != readInt; i++) {
                    certificateArr[i] = a(dataInputStream);
                }
            }
            Certificate[] certificateArr2 = certificateArr;
            switch (read) {
                case 1:
                    Certificate a = a(dataInputStream);
                    hashtable = this.table;
                    storeEntry = new StoreEntry(readUTF, date, 1, (Object) a);
                    break;
                case 2:
                    Key b = b(dataInputStream);
                    hashtable = this.table;
                    storeEntry = new StoreEntry(readUTF, date, 2, b, certificateArr2);
                    break;
                case 3:
                case 4:
                    byte[] bArr = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr);
                    Hashtable hashtable2 = this.table;
                    StoreEntry storeEntry2 = new StoreEntry(readUTF, date, read, bArr, certificateArr2);
                    hashtable2.put(readUTF, storeEntry2);
                    continue;
                default:
                    throw new RuntimeException("Unknown object type in store.");
            }
            hashtable.put(readUTF, storeEntry);
        }
    }

    /* access modifiers changed from: protected */
    public Cipher makePBECipher(String str, int i, char[] cArr, byte[] bArr, int i2) {
        try {
            PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
            SecretKeyFactory instance = SecretKeyFactory.getInstance(str, BouncyCastleProvider.PROVIDER_NAME);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, i2);
            Cipher instance2 = Cipher.getInstance(str, BouncyCastleProvider.PROVIDER_NAME);
            instance2.init(i, instance.generateSecret(pBEKeySpec), pBEParameterSpec);
            return instance2;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error initialising store of key store: ");
            sb.append(e);
            throw new IOException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void saveStore(OutputStream outputStream) {
        Enumeration elements = this.table.elements();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        while (true) {
            if (elements.hasMoreElements()) {
                StoreEntry storeEntry = (StoreEntry) elements.nextElement();
                dataOutputStream.write(storeEntry.a());
                dataOutputStream.writeUTF(storeEntry.b());
                dataOutputStream.writeLong(storeEntry.e().getTime());
                Certificate[] d = storeEntry.d();
                if (d == null) {
                    dataOutputStream.writeInt(0);
                } else {
                    dataOutputStream.writeInt(d.length);
                    for (int i = 0; i != d.length; i++) {
                        a(d[i], dataOutputStream);
                    }
                }
                switch (storeEntry.a()) {
                    case 1:
                        a((Certificate) storeEntry.c(), dataOutputStream);
                        break;
                    case 2:
                        a((Key) storeEntry.c(), dataOutputStream);
                        break;
                    case 3:
                    case 4:
                        byte[] bArr = (byte[]) storeEntry.c();
                        dataOutputStream.writeInt(bArr.length);
                        dataOutputStream.write(bArr);
                        break;
                    default:
                        throw new RuntimeException("Unknown object type in store.");
                }
            } else {
                dataOutputStream.write(0);
                return;
            }
        }
    }

    public void setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
    }
}
