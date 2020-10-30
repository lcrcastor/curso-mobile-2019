package org.bouncycastle.crypto.tls;

import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;

public class TlsAEADCipher implements TlsCipher {
    protected TlsContext context;
    protected AEADBlockCipher decryptCipher;
    protected byte[] decryptImplicitNonce;
    protected AEADBlockCipher encryptCipher;
    protected byte[] encryptImplicitNonce;
    protected int macSize;
    protected int nonce_explicit_length;

    public TlsAEADCipher(TlsContext tlsContext, AEADBlockCipher aEADBlockCipher, AEADBlockCipher aEADBlockCipher2, int i, int i2) {
        if (!TlsUtils.isTLSv12(tlsContext)) {
            throw new TlsFatalAlert(80);
        }
        this.context = tlsContext;
        this.macSize = i2;
        this.nonce_explicit_length = 8;
        int i3 = (i * 2) + 8;
        byte[] a = TlsUtils.a(tlsContext, i3);
        KeyParameter keyParameter = new KeyParameter(a, 0, i);
        int i4 = i + 0;
        KeyParameter keyParameter2 = new KeyParameter(a, i4, i);
        int i5 = i4 + i;
        int i6 = i5 + 4;
        byte[] copyOfRange = Arrays.copyOfRange(a, i5, i6);
        int i7 = i6 + 4;
        byte[] copyOfRange2 = Arrays.copyOfRange(a, i6, i7);
        if (i7 != i3) {
            throw new TlsFatalAlert(80);
        }
        if (tlsContext.isServer()) {
            this.encryptCipher = aEADBlockCipher2;
            this.decryptCipher = aEADBlockCipher;
            this.encryptImplicitNonce = copyOfRange2;
            this.decryptImplicitNonce = copyOfRange;
            KeyParameter keyParameter3 = keyParameter2;
            keyParameter2 = keyParameter;
            keyParameter = keyParameter3;
        } else {
            this.encryptCipher = aEADBlockCipher;
            this.decryptCipher = aEADBlockCipher2;
            this.encryptImplicitNonce = copyOfRange;
            this.decryptImplicitNonce = copyOfRange2;
        }
        byte[] bArr = new byte[(this.nonce_explicit_length + 4)];
        int i8 = i2 * 8;
        this.encryptCipher.init(true, new AEADParameters(keyParameter, i8, bArr));
        this.decryptCipher.init(false, new AEADParameters(keyParameter2, i8, bArr));
    }

    public byte[] decodeCiphertext(long j, short s, byte[] bArr, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        if (getPlaintextLimit(i4) < 0) {
            throw new TlsFatalAlert(50);
        }
        byte[] bArr2 = new byte[(this.decryptImplicitNonce.length + this.nonce_explicit_length)];
        System.arraycopy(this.decryptImplicitNonce, 0, bArr2, 0, this.decryptImplicitNonce.length);
        byte[] bArr3 = bArr;
        System.arraycopy(bArr3, i3, bArr2, this.decryptImplicitNonce.length, this.nonce_explicit_length);
        int i5 = i3 + this.nonce_explicit_length;
        int i6 = i4 - this.nonce_explicit_length;
        int outputSize = this.decryptCipher.getOutputSize(i6);
        byte[] bArr4 = new byte[outputSize];
        try {
            this.decryptCipher.init(false, new AEADParameters(null, this.macSize * 8, bArr2, getAdditionalData(j, s, outputSize)));
            int processBytes = this.decryptCipher.processBytes(bArr3, i5, i6, bArr4, 0) + 0;
            if (processBytes + this.decryptCipher.doFinal(bArr4, processBytes) == bArr4.length) {
                return bArr4;
            }
            throw new TlsFatalAlert(80);
        } catch (Exception unused) {
            throw new TlsFatalAlert(20);
        }
    }

    public byte[] encodePlaintext(long j, short s, byte[] bArr, int i, int i2) {
        long j2 = j;
        int i3 = i2;
        byte[] bArr2 = new byte[(this.encryptImplicitNonce.length + this.nonce_explicit_length)];
        System.arraycopy(this.encryptImplicitNonce, 0, bArr2, 0, this.encryptImplicitNonce.length);
        TlsUtils.writeUint64(j2, bArr2, this.encryptImplicitNonce.length);
        byte[] bArr3 = new byte[(this.nonce_explicit_length + this.encryptCipher.getOutputSize(i3))];
        System.arraycopy(bArr2, this.encryptImplicitNonce.length, bArr3, 0, this.nonce_explicit_length);
        int i4 = this.nonce_explicit_length;
        try {
            this.encryptCipher.init(true, new AEADParameters(null, this.macSize * 8, bArr2, getAdditionalData(j2, s, i3)));
            int processBytes = i4 + this.encryptCipher.processBytes(bArr, i, i3, bArr3, i4);
            if (processBytes + this.encryptCipher.doFinal(bArr3, processBytes) == bArr3.length) {
                return bArr3;
            }
            throw new TlsFatalAlert(80);
        } catch (Exception unused) {
            throw new TlsFatalAlert(80);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] getAdditionalData(long j, short s, int i) {
        byte[] bArr = new byte[13];
        TlsUtils.writeUint64(j, bArr, 0);
        TlsUtils.writeUint8(s, bArr, 8);
        TlsUtils.writeVersion(this.context.getServerVersion(), bArr, 9);
        TlsUtils.writeUint16(i, bArr, 11);
        return bArr;
    }

    public int getPlaintextLimit(int i) {
        return (i - this.macSize) - this.nonce_explicit_length;
    }
}
