package cz.msebera.android.httpclient.auth;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.util.Queue;

@NotThreadSafe
public class AuthState {
    private AuthProtocolState a = AuthProtocolState.UNCHALLENGED;
    private AuthScheme b;
    private AuthScope c;
    private Credentials d;
    private Queue<AuthOption> e;

    public void reset() {
        this.a = AuthProtocolState.UNCHALLENGED;
        this.e = null;
        this.b = null;
        this.c = null;
        this.d = null;
    }

    public AuthProtocolState getState() {
        return this.a;
    }

    public void setState(AuthProtocolState authProtocolState) {
        if (authProtocolState == null) {
            authProtocolState = AuthProtocolState.UNCHALLENGED;
        }
        this.a = authProtocolState;
    }

    public AuthScheme getAuthScheme() {
        return this.b;
    }

    public Credentials getCredentials() {
        return this.d;
    }

    public void update(AuthScheme authScheme, Credentials credentials) {
        Args.notNull(authScheme, "Auth scheme");
        Args.notNull(credentials, "Credentials");
        this.b = authScheme;
        this.d = credentials;
        this.e = null;
    }

    public Queue<AuthOption> getAuthOptions() {
        return this.e;
    }

    public boolean hasAuthOptions() {
        return this.e != null && !this.e.isEmpty();
    }

    public void update(Queue<AuthOption> queue) {
        Args.notEmpty(queue, "Queue of auth options");
        this.e = queue;
        this.b = null;
        this.d = null;
    }

    @Deprecated
    public void invalidate() {
        reset();
    }

    @Deprecated
    public boolean isValid() {
        return this.b != null;
    }

    @Deprecated
    public void setAuthScheme(AuthScheme authScheme) {
        if (authScheme == null) {
            reset();
        } else {
            this.b = authScheme;
        }
    }

    @Deprecated
    public void setCredentials(Credentials credentials) {
        this.d = credentials;
    }

    @Deprecated
    public AuthScope getAuthScope() {
        return this.c;
    }

    @Deprecated
    public void setAuthScope(AuthScope authScope) {
        this.c = authScope;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("state:");
        sb.append(this.a);
        sb.append(";");
        if (this.b != null) {
            sb.append("auth scheme:");
            sb.append(this.b.getSchemeName());
            sb.append(";");
        }
        if (this.d != null) {
            sb.append("credentials present");
        }
        return sb.toString();
    }
}
