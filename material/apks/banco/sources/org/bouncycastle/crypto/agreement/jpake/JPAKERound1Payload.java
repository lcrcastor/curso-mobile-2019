package org.bouncycastle.crypto.agreement.jpake;

import java.math.BigInteger;
import org.bouncycastle.util.Arrays;

public class JPAKERound1Payload {
    private final String a;
    private final BigInteger b;
    private final BigInteger c;
    private final BigInteger[] d;
    private final BigInteger[] e;

    public JPAKERound1Payload(String str, BigInteger bigInteger, BigInteger bigInteger2, BigInteger[] bigIntegerArr, BigInteger[] bigIntegerArr2) {
        JPAKEUtil.validateNotNull(str, "participantId");
        JPAKEUtil.validateNotNull(bigInteger, "gx1");
        JPAKEUtil.validateNotNull(bigInteger2, "gx2");
        JPAKEUtil.validateNotNull(bigIntegerArr, "knowledgeProofForX1");
        JPAKEUtil.validateNotNull(bigIntegerArr2, "knowledgeProofForX2");
        this.a = str;
        this.b = bigInteger;
        this.c = bigInteger2;
        this.d = Arrays.copyOf(bigIntegerArr, bigIntegerArr.length);
        this.e = Arrays.copyOf(bigIntegerArr2, bigIntegerArr2.length);
    }

    public BigInteger getGx1() {
        return this.b;
    }

    public BigInteger getGx2() {
        return this.c;
    }

    public BigInteger[] getKnowledgeProofForX1() {
        return Arrays.copyOf(this.d, this.d.length);
    }

    public BigInteger[] getKnowledgeProofForX2() {
        return Arrays.copyOf(this.e, this.e.length);
    }

    public String getParticipantId() {
        return this.a;
    }
}
