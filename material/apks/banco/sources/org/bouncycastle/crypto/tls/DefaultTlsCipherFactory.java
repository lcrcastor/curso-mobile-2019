package org.bouncycastle.crypto.tls;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.CamelliaEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.engines.SEEDEngine;
import org.bouncycastle.crypto.engines.Salsa20Engine;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;

public class DefaultTlsCipherFactory extends AbstractTlsCipherFactory {
    /* access modifiers changed from: protected */
    public AEADBlockCipher createAEADBlockCipher_AES_CCM() {
        return new CCMBlockCipher(createAESEngine());
    }

    /* access modifiers changed from: protected */
    public AEADBlockCipher createAEADBlockCipher_AES_GCM() {
        return new GCMBlockCipher(createAESEngine());
    }

    /* access modifiers changed from: protected */
    public AEADBlockCipher createAEADBlockCipher_Camellia_GCM() {
        return new GCMBlockCipher(createCamelliaEngine());
    }

    /* access modifiers changed from: protected */
    public BlockCipher createAESBlockCipher() {
        return new CBCBlockCipher(createAESEngine());
    }

    /* access modifiers changed from: protected */
    public TlsBlockCipher createAESCipher(TlsContext tlsContext, int i, int i2) {
        TlsBlockCipher tlsBlockCipher = new TlsBlockCipher(tlsContext, createAESBlockCipher(), createAESBlockCipher(), createHMACDigest(i2), createHMACDigest(i2), i);
        return tlsBlockCipher;
    }

    /* access modifiers changed from: protected */
    public BlockCipher createAESEngine() {
        return new AESEngine();
    }

    /* access modifiers changed from: protected */
    public BlockCipher createCamelliaBlockCipher() {
        return new CBCBlockCipher(createCamelliaEngine());
    }

    /* access modifiers changed from: protected */
    public TlsBlockCipher createCamelliaCipher(TlsContext tlsContext, int i, int i2) {
        TlsBlockCipher tlsBlockCipher = new TlsBlockCipher(tlsContext, createCamelliaBlockCipher(), createCamelliaBlockCipher(), createHMACDigest(i2), createHMACDigest(i2), i);
        return tlsBlockCipher;
    }

    /* access modifiers changed from: protected */
    public BlockCipher createCamelliaEngine() {
        return new CamelliaEngine();
    }

    /* access modifiers changed from: protected */
    public TlsCipher createChaCha20Poly1305(TlsContext tlsContext) {
        return new Chacha20Poly1305(tlsContext);
    }

    public TlsCipher createCipher(TlsContext tlsContext, int i, int i2) {
        int i3;
        if (i == 0) {
            return createNullCipher(tlsContext, i2);
        }
        if (i == 2) {
            return createRC4Cipher(tlsContext, 16, i2);
        }
        switch (i) {
            case 7:
                return createDESedeCipher(tlsContext, i2);
            case 8:
                return createAESCipher(tlsContext, 16, i2);
            case 9:
                return createAESCipher(tlsContext, 32, i2);
            case 10:
                return createCipher_AES_GCM(tlsContext, 16, 16);
            case 11:
                return createCipher_AES_GCM(tlsContext, 32, 16);
            case 12:
                return createCamelliaCipher(tlsContext, 16, i2);
            case 13:
                return createCamelliaCipher(tlsContext, 32, i2);
            case 14:
                return createSEEDCipher(tlsContext, i2);
            case 15:
                return createCipher_AES_CCM(tlsContext, 16, 16);
            case 16:
                return createCipher_AES_CCM(tlsContext, 16, 8);
            case 17:
                return createCipher_AES_CCM(tlsContext, 32, 16);
            case 18:
                return createCipher_AES_CCM(tlsContext, 32, 8);
            case 19:
                return createCipher_Camellia_GCM(tlsContext, 16, 16);
            case 20:
                return createCipher_Camellia_GCM(tlsContext, 32, 16);
            default:
                switch (i) {
                    case 100:
                        i3 = 12;
                        break;
                    case 101:
                        i3 = 20;
                        break;
                    case 102:
                        return createChaCha20Poly1305(tlsContext);
                    default:
                        throw new TlsFatalAlert(80);
                }
                return createSalsa20Cipher(tlsContext, i3, 32, i2);
        }
    }

    /* access modifiers changed from: protected */
    public TlsAEADCipher createCipher_AES_CCM(TlsContext tlsContext, int i, int i2) {
        TlsAEADCipher tlsAEADCipher = new TlsAEADCipher(tlsContext, createAEADBlockCipher_AES_CCM(), createAEADBlockCipher_AES_CCM(), i, i2);
        return tlsAEADCipher;
    }

    /* access modifiers changed from: protected */
    public TlsAEADCipher createCipher_AES_GCM(TlsContext tlsContext, int i, int i2) {
        TlsAEADCipher tlsAEADCipher = new TlsAEADCipher(tlsContext, createAEADBlockCipher_AES_GCM(), createAEADBlockCipher_AES_GCM(), i, i2);
        return tlsAEADCipher;
    }

    /* access modifiers changed from: protected */
    public TlsAEADCipher createCipher_Camellia_GCM(TlsContext tlsContext, int i, int i2) {
        TlsAEADCipher tlsAEADCipher = new TlsAEADCipher(tlsContext, createAEADBlockCipher_Camellia_GCM(), createAEADBlockCipher_Camellia_GCM(), i, i2);
        return tlsAEADCipher;
    }

    /* access modifiers changed from: protected */
    public BlockCipher createDESedeBlockCipher() {
        return new CBCBlockCipher(new DESedeEngine());
    }

    /* access modifiers changed from: protected */
    public TlsBlockCipher createDESedeCipher(TlsContext tlsContext, int i) {
        TlsBlockCipher tlsBlockCipher = new TlsBlockCipher(tlsContext, createDESedeBlockCipher(), createDESedeBlockCipher(), createHMACDigest(i), createHMACDigest(i), 24);
        return tlsBlockCipher;
    }

    /* access modifiers changed from: protected */
    public Digest createHMACDigest(int i) {
        short s;
        switch (i) {
            case 0:
                return null;
            case 1:
                s = 1;
                break;
            case 2:
                s = 2;
                break;
            case 3:
                s = 4;
                break;
            case 4:
                s = 5;
                break;
            case 5:
                s = 6;
                break;
            default:
                throw new TlsFatalAlert(80);
        }
        return TlsUtils.createHash(s);
    }

    /* access modifiers changed from: protected */
    public TlsNullCipher createNullCipher(TlsContext tlsContext, int i) {
        return new TlsNullCipher(tlsContext, createHMACDigest(i), createHMACDigest(i));
    }

    /* access modifiers changed from: protected */
    public TlsStreamCipher createRC4Cipher(TlsContext tlsContext, int i, int i2) {
        TlsStreamCipher tlsStreamCipher = new TlsStreamCipher(tlsContext, createRC4StreamCipher(), createRC4StreamCipher(), createHMACDigest(i2), createHMACDigest(i2), i, false);
        return tlsStreamCipher;
    }

    /* access modifiers changed from: protected */
    public StreamCipher createRC4StreamCipher() {
        return new RC4Engine();
    }

    /* access modifiers changed from: protected */
    public BlockCipher createSEEDBlockCipher() {
        return new CBCBlockCipher(new SEEDEngine());
    }

    /* access modifiers changed from: protected */
    public TlsBlockCipher createSEEDCipher(TlsContext tlsContext, int i) {
        TlsBlockCipher tlsBlockCipher = new TlsBlockCipher(tlsContext, createSEEDBlockCipher(), createSEEDBlockCipher(), createHMACDigest(i), createHMACDigest(i), 16);
        return tlsBlockCipher;
    }

    /* access modifiers changed from: protected */
    public TlsStreamCipher createSalsa20Cipher(TlsContext tlsContext, int i, int i2, int i3) {
        TlsStreamCipher tlsStreamCipher = new TlsStreamCipher(tlsContext, createSalsa20StreamCipher(i), createSalsa20StreamCipher(i), createHMACDigest(i3), createHMACDigest(i3), i2, true);
        return tlsStreamCipher;
    }

    /* access modifiers changed from: protected */
    public StreamCipher createSalsa20StreamCipher(int i) {
        return new Salsa20Engine(i);
    }
}
