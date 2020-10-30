package org.bouncycastle.crypto.agreement.jpake;

import java.math.BigInteger;
import org.bouncycastle.util.Arrays;

public class JPAKERound2Payload {
    private final String a;
    private final BigInteger b;
    private final BigInteger[] c;

    public JPAKERound2Payload(String str, BigInteger bigInteger, BigInteger[] bigIntegerArr) {
        JPAKEUtil.validateNotNull(str, "participantId");
        JPAKEUtil.validateNotNull(bigInteger, "a");
        JPAKEUtil.validateNotNull(bigIntegerArr, "knowledgeProofForX2s");
        this.a = str;
        this.b = bigInteger;
        this.c = Arrays.copyOf(bigIntegerArr, bigIntegerArr.length);
    }

    public BigInteger getA() {
        return this.b;
    }

    public BigInteger[] getKnowledgeProofForX2s() {
        return Arrays.copyOf(this.c, this.c.length);
    }

    public String getParticipantId() {
        return this.a;
    }
}
