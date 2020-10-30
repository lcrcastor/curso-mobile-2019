package cz.msebera.android.httpclient.impl.auth;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.auth.AuthenticationException;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.auth.InvalidCredentialsException;
import cz.msebera.android.httpclient.auth.MalformedChallengeException;
import cz.msebera.android.httpclient.auth.NTCredentials;
import cz.msebera.android.httpclient.message.BufferedHeader;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;

@NotThreadSafe
public class NTLMScheme extends AuthSchemeBase {
    private final NTLMEngine a;
    private State b;
    private String c;

    enum State {
        UNINITIATED,
        CHALLENGE_RECEIVED,
        MSG_TYPE1_GENERATED,
        MSG_TYPE2_RECEVIED,
        MSG_TYPE3_GENERATED,
        FAILED
    }

    public String getParameter(String str) {
        return null;
    }

    public String getRealm() {
        return null;
    }

    public String getSchemeName() {
        return "ntlm";
    }

    public boolean isConnectionBased() {
        return true;
    }

    public NTLMScheme(NTLMEngine nTLMEngine) {
        Args.notNull(nTLMEngine, "NTLM engine");
        this.a = nTLMEngine;
        this.b = State.UNINITIATED;
        this.c = null;
    }

    public NTLMScheme() {
        this(new NTLMEngineImpl());
    }

    /* access modifiers changed from: protected */
    public void parseChallenge(CharArrayBuffer charArrayBuffer, int i, int i2) {
        this.c = charArrayBuffer.substringTrimmed(i, i2);
        if (this.c.isEmpty()) {
            if (this.b == State.UNINITIATED) {
                this.b = State.CHALLENGE_RECEIVED;
            } else {
                this.b = State.FAILED;
            }
        } else if (this.b.compareTo(State.MSG_TYPE1_GENERATED) < 0) {
            this.b = State.FAILED;
            throw new MalformedChallengeException("Out of sequence NTLM response message");
        } else if (this.b == State.MSG_TYPE1_GENERATED) {
            this.b = State.MSG_TYPE2_RECEVIED;
        }
    }

    public Header authenticate(Credentials credentials, HttpRequest httpRequest) {
        String str;
        try {
            NTCredentials nTCredentials = (NTCredentials) credentials;
            if (this.b == State.FAILED) {
                throw new AuthenticationException("NTLM authentication failed");
            }
            if (this.b == State.CHALLENGE_RECEIVED) {
                str = this.a.generateType1Msg(nTCredentials.getDomain(), nTCredentials.getWorkstation());
                this.b = State.MSG_TYPE1_GENERATED;
            } else if (this.b == State.MSG_TYPE2_RECEVIED) {
                str = this.a.generateType3Msg(nTCredentials.getUserName(), nTCredentials.getPassword(), nTCredentials.getDomain(), nTCredentials.getWorkstation(), this.c);
                this.b = State.MSG_TYPE3_GENERATED;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected state: ");
                sb.append(this.b);
                throw new AuthenticationException(sb.toString());
            }
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
            if (isProxy()) {
                charArrayBuffer.append("Proxy-Authorization");
            } else {
                charArrayBuffer.append("Authorization");
            }
            charArrayBuffer.append(": NTLM ");
            charArrayBuffer.append(str);
            return new BufferedHeader(charArrayBuffer);
        } catch (ClassCastException unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Credentials cannot be used for NTLM authentication: ");
            sb2.append(credentials.getClass().getName());
            throw new InvalidCredentialsException(sb2.toString());
        }
    }

    public boolean isComplete() {
        return this.b == State.MSG_TYPE3_GENERATED || this.b == State.FAILED;
    }
}
