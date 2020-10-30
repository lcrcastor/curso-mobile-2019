package org.bouncycastle.crypto.tls;

import java.security.SecureRandom;
import org.bouncycastle.crypto.prng.DigestRandomGenerator;
import org.bouncycastle.crypto.prng.RandomGenerator;
import org.bouncycastle.util.Times;

abstract class AbstractTlsContext implements TlsContext {
    private static long a = Times.nanoTime();
    private RandomGenerator b;
    private SecureRandom c;
    private SecurityParameters d;
    private ProtocolVersion e = null;
    private ProtocolVersion f = null;
    private TlsSession g = null;
    private Object h = null;

    AbstractTlsContext(SecureRandom secureRandom, SecurityParameters securityParameters) {
        secureRandom.setSeed(a());
        secureRandom.setSeed(Times.nanoTime());
        this.b = new DigestRandomGenerator(TlsUtils.createHash(4));
        this.b.addSeedMaterial(secureRandom.generateSeed(32));
        this.c = secureRandom;
        this.d = securityParameters;
    }

    private static synchronized long a() {
        long j;
        synchronized (AbstractTlsContext.class) {
            j = a + 1;
            a = j;
        }
        return j;
    }

    /* access modifiers changed from: 0000 */
    public void a(ProtocolVersion protocolVersion) {
        this.e = protocolVersion;
    }

    /* access modifiers changed from: 0000 */
    public void a(TlsSession tlsSession) {
        this.g = tlsSession;
    }

    /* access modifiers changed from: 0000 */
    public void b(ProtocolVersion protocolVersion) {
        this.f = protocolVersion;
    }

    public byte[] exportKeyingMaterial(String str, byte[] bArr, int i) {
        if (bArr == null || TlsUtils.isValidUint16(bArr.length)) {
            SecurityParameters securityParameters = getSecurityParameters();
            byte[] clientRandom = securityParameters.getClientRandom();
            byte[] serverRandom = securityParameters.getServerRandom();
            int length = clientRandom.length + serverRandom.length;
            if (bArr != null) {
                length += bArr.length + 2;
            }
            byte[] bArr2 = new byte[length];
            System.arraycopy(clientRandom, 0, bArr2, 0, clientRandom.length);
            int length2 = clientRandom.length + 0;
            System.arraycopy(serverRandom, 0, bArr2, length2, serverRandom.length);
            int length3 = length2 + serverRandom.length;
            if (bArr != null) {
                TlsUtils.writeUint16(bArr.length, bArr2, length3);
                int i2 = length3 + 2;
                System.arraycopy(bArr, 0, bArr2, i2, bArr.length);
                length3 = i2 + bArr.length;
            }
            if (length3 == length) {
                return TlsUtils.PRF(this, securityParameters.getMasterSecret(), str, bArr2, i);
            }
            throw new IllegalStateException("error in calculation of seed for export");
        }
        throw new IllegalArgumentException("'context_value' must have length less than 2^16 (or be null)");
    }

    public ProtocolVersion getClientVersion() {
        return this.e;
    }

    public RandomGenerator getNonceRandomGenerator() {
        return this.b;
    }

    public TlsSession getResumableSession() {
        return this.g;
    }

    public SecureRandom getSecureRandom() {
        return this.c;
    }

    public SecurityParameters getSecurityParameters() {
        return this.d;
    }

    public ProtocolVersion getServerVersion() {
        return this.f;
    }

    public Object getUserObject() {
        return this.h;
    }

    public void setUserObject(Object obj) {
        this.h = obj;
    }
}
