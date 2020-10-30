package org.bouncycastle.crypto.commitments;

import java.security.SecureRandom;
import org.bouncycastle.crypto.Commitment;
import org.bouncycastle.crypto.Committer;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Arrays;

public class HashCommitter implements Committer {
    private final Digest a;
    private final int b;
    private final SecureRandom c;

    public HashCommitter(ExtendedDigest extendedDigest, SecureRandom secureRandom) {
        this.a = extendedDigest;
        this.b = extendedDigest.getByteLength();
        this.c = secureRandom;
    }

    private byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[this.a.getDigestSize()];
        this.a.update(bArr, 0, bArr.length);
        this.a.update(bArr2, 0, bArr2.length);
        this.a.doFinal(bArr3, 0);
        return bArr3;
    }

    public Commitment commit(byte[] bArr) {
        if (bArr.length > this.b / 2) {
            throw new DataLengthException("Message to be committed to too large for digest.");
        }
        byte[] bArr2 = new byte[(this.b - bArr.length)];
        this.c.nextBytes(bArr2);
        return new Commitment(bArr2, a(bArr2, bArr));
    }

    public boolean isRevealed(Commitment commitment, byte[] bArr) {
        if (bArr.length + commitment.getSecret().length != this.b) {
            throw new DataLengthException("Message and witness secret lengths do not match.");
        }
        return Arrays.constantTimeAreEqual(commitment.getCommitment(), a(commitment.getSecret(), bArr));
    }
}
