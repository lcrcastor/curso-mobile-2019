package org.bouncycastle.crypto.agreement.jpake;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.Arrays;

public class JPAKEParticipant {
    public static final int STATE_INITIALIZED = 0;
    public static final int STATE_KEY_CALCULATED = 50;
    public static final int STATE_ROUND_1_CREATED = 10;
    public static final int STATE_ROUND_1_VALIDATED = 20;
    public static final int STATE_ROUND_2_CREATED = 30;
    public static final int STATE_ROUND_2_VALIDATED = 40;
    public static final int STATE_ROUND_3_CREATED = 60;
    public static final int STATE_ROUND_3_VALIDATED = 70;
    private final String a;
    private char[] b;
    private final Digest c;
    private final SecureRandom d;
    private final BigInteger e;
    private final BigInteger f;
    private final BigInteger g;
    private String h;
    private BigInteger i;
    private BigInteger j;
    private BigInteger k;
    private BigInteger l;
    private BigInteger m;
    private BigInteger n;
    private BigInteger o;
    private int p;

    public JPAKEParticipant(String str, char[] cArr) {
        this(str, cArr, JPAKEPrimeOrderGroups.NIST_3072);
    }

    public JPAKEParticipant(String str, char[] cArr, JPAKEPrimeOrderGroup jPAKEPrimeOrderGroup) {
        this(str, cArr, jPAKEPrimeOrderGroup, new SHA256Digest(), new SecureRandom());
    }

    public JPAKEParticipant(String str, char[] cArr, JPAKEPrimeOrderGroup jPAKEPrimeOrderGroup, Digest digest, SecureRandom secureRandom) {
        JPAKEUtil.validateNotNull(str, "participantId");
        JPAKEUtil.validateNotNull(cArr, "password");
        JPAKEUtil.validateNotNull(jPAKEPrimeOrderGroup, "p");
        JPAKEUtil.validateNotNull(digest, "digest");
        JPAKEUtil.validateNotNull(secureRandom, "random");
        if (cArr.length == 0) {
            throw new IllegalArgumentException("Password must not be empty.");
        }
        this.a = str;
        this.b = Arrays.copyOf(cArr, cArr.length);
        this.e = jPAKEPrimeOrderGroup.getP();
        this.f = jPAKEPrimeOrderGroup.getQ();
        this.g = jPAKEPrimeOrderGroup.getG();
        this.c = digest;
        this.d = secureRandom;
        this.p = 0;
    }

    public BigInteger calculateKeyingMaterial() {
        if (this.p >= 50) {
            StringBuilder sb = new StringBuilder();
            sb.append("Key already calculated for ");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        } else if (this.p < 40) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Round2 payload must be validated prior to creating key for ");
            sb2.append(this.a);
            throw new IllegalStateException(sb2.toString());
        } else {
            BigInteger calculateS = JPAKEUtil.calculateS(this.b);
            Arrays.fill(this.b, 0);
            this.b = null;
            BigInteger calculateKeyingMaterial = JPAKEUtil.calculateKeyingMaterial(this.e, this.f, this.n, this.j, calculateS, this.o);
            this.i = null;
            this.j = null;
            this.o = null;
            this.p = 50;
            return calculateKeyingMaterial;
        }
    }

    public JPAKERound1Payload createRound1PayloadToSend() {
        if (this.p >= 10) {
            StringBuilder sb = new StringBuilder();
            sb.append("Round1 payload already created for ");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        }
        this.i = JPAKEUtil.generateX1(this.f, this.d);
        this.j = JPAKEUtil.generateX2(this.f, this.d);
        this.k = JPAKEUtil.calculateGx(this.e, this.g, this.i);
        this.l = JPAKEUtil.calculateGx(this.e, this.g, this.j);
        BigInteger[] calculateZeroKnowledgeProof = JPAKEUtil.calculateZeroKnowledgeProof(this.e, this.f, this.g, this.k, this.i, this.a, this.c, this.d);
        BigInteger[] calculateZeroKnowledgeProof2 = JPAKEUtil.calculateZeroKnowledgeProof(this.e, this.f, this.g, this.l, this.j, this.a, this.c, this.d);
        this.p = 10;
        JPAKERound1Payload jPAKERound1Payload = new JPAKERound1Payload(this.a, this.k, this.l, calculateZeroKnowledgeProof, calculateZeroKnowledgeProof2);
        return jPAKERound1Payload;
    }

    public JPAKERound2Payload createRound2PayloadToSend() {
        if (this.p >= 30) {
            StringBuilder sb = new StringBuilder();
            sb.append("Round2 payload already created for ");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        } else if (this.p < 20) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Round1 payload must be validated prior to creating Round2 payload for ");
            sb2.append(this.a);
            throw new IllegalStateException(sb2.toString());
        } else {
            BigInteger calculateGA = JPAKEUtil.calculateGA(this.e, this.k, this.m, this.n);
            BigInteger calculateX2s = JPAKEUtil.calculateX2s(this.f, this.j, JPAKEUtil.calculateS(this.b));
            BigInteger calculateA = JPAKEUtil.calculateA(this.e, this.f, calculateGA, calculateX2s);
            BigInteger[] calculateZeroKnowledgeProof = JPAKEUtil.calculateZeroKnowledgeProof(this.e, this.f, calculateGA, calculateA, calculateX2s, this.a, this.c, this.d);
            this.p = 30;
            return new JPAKERound2Payload(this.a, calculateA, calculateZeroKnowledgeProof);
        }
    }

    public JPAKERound3Payload createRound3PayloadToSend(BigInteger bigInteger) {
        if (this.p >= 60) {
            StringBuilder sb = new StringBuilder();
            sb.append("Round3 payload already created for ");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        } else if (this.p < 50) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Keying material must be calculated prior to creating Round3 payload for ");
            sb2.append(this.a);
            throw new IllegalStateException(sb2.toString());
        } else {
            BigInteger calculateMacTag = JPAKEUtil.calculateMacTag(this.a, this.h, this.k, this.l, this.m, this.n, bigInteger, this.c);
            this.p = 60;
            return new JPAKERound3Payload(this.a, calculateMacTag);
        }
    }

    public int getState() {
        return this.p;
    }

    public void validateRound1PayloadReceived(JPAKERound1Payload jPAKERound1Payload) {
        if (this.p >= 20) {
            StringBuilder sb = new StringBuilder();
            sb.append("Validation already attempted for round1 payload for");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        }
        this.h = jPAKERound1Payload.getParticipantId();
        this.m = jPAKERound1Payload.getGx1();
        this.n = jPAKERound1Payload.getGx2();
        BigInteger[] knowledgeProofForX1 = jPAKERound1Payload.getKnowledgeProofForX1();
        BigInteger[] knowledgeProofForX2 = jPAKERound1Payload.getKnowledgeProofForX2();
        JPAKEUtil.validateParticipantIdsDiffer(this.a, jPAKERound1Payload.getParticipantId());
        JPAKEUtil.validateGx4(this.n);
        JPAKEUtil.validateZeroKnowledgeProof(this.e, this.f, this.g, this.m, knowledgeProofForX1, jPAKERound1Payload.getParticipantId(), this.c);
        JPAKEUtil.validateZeroKnowledgeProof(this.e, this.f, this.g, this.n, knowledgeProofForX2, jPAKERound1Payload.getParticipantId(), this.c);
        this.p = 20;
    }

    public void validateRound2PayloadReceived(JPAKERound2Payload jPAKERound2Payload) {
        if (this.p >= 40) {
            StringBuilder sb = new StringBuilder();
            sb.append("Validation already attempted for round2 payload for");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        } else if (this.p < 20) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Round1 payload must be validated prior to validating Round2 payload for ");
            sb2.append(this.a);
            throw new IllegalStateException(sb2.toString());
        } else {
            BigInteger calculateGA = JPAKEUtil.calculateGA(this.e, this.m, this.k, this.l);
            this.o = jPAKERound2Payload.getA();
            BigInteger[] knowledgeProofForX2s = jPAKERound2Payload.getKnowledgeProofForX2s();
            JPAKEUtil.validateParticipantIdsDiffer(this.a, jPAKERound2Payload.getParticipantId());
            JPAKEUtil.validateParticipantIdsEqual(this.h, jPAKERound2Payload.getParticipantId());
            JPAKEUtil.validateGa(calculateGA);
            JPAKEUtil.validateZeroKnowledgeProof(this.e, this.f, calculateGA, this.o, knowledgeProofForX2s, jPAKERound2Payload.getParticipantId(), this.c);
            this.p = 40;
        }
    }

    public void validateRound3PayloadReceived(JPAKERound3Payload jPAKERound3Payload, BigInteger bigInteger) {
        if (this.p >= 70) {
            StringBuilder sb = new StringBuilder();
            sb.append("Validation already attempted for round3 payload for");
            sb.append(this.a);
            throw new IllegalStateException(sb.toString());
        } else if (this.p < 50) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Keying material must be calculated validated prior to validating Round3 payload for ");
            sb2.append(this.a);
            throw new IllegalStateException(sb2.toString());
        } else {
            JPAKEUtil.validateParticipantIdsDiffer(this.a, jPAKERound3Payload.getParticipantId());
            JPAKEUtil.validateParticipantIdsEqual(this.h, jPAKERound3Payload.getParticipantId());
            JPAKEUtil.validateMacTag(this.a, this.h, this.k, this.l, this.m, this.n, bigInteger, this.c, jPAKERound3Payload.getMacTag());
            this.k = null;
            this.l = null;
            this.m = null;
            this.n = null;
            this.p = 70;
        }
    }
}
