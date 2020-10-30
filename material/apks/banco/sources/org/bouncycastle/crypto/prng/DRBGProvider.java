package org.bouncycastle.crypto.prng;

import org.bouncycastle.crypto.prng.drbg.SP80090DRBG;

interface DRBGProvider {
    SP80090DRBG a(EntropySource entropySource);
}
