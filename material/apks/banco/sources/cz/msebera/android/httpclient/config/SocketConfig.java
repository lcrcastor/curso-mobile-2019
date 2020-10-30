package cz.msebera.android.httpclient.config;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;

@Immutable
public class SocketConfig implements Cloneable {
    public static final SocketConfig DEFAULT = new Builder().build();
    private final int a;
    private final boolean b;
    private final int c;
    private final boolean d;
    private final boolean e;
    private final int f;
    private final int g;
    private int h;

    public static class Builder {
        private int a;
        private boolean b;
        private int c = -1;
        private boolean d;
        private boolean e = true;
        private int f;
        private int g;
        private int h;

        Builder() {
        }

        public Builder setSoTimeout(int i) {
            this.a = i;
            return this;
        }

        public Builder setSoReuseAddress(boolean z) {
            this.b = z;
            return this;
        }

        public Builder setSoLinger(int i) {
            this.c = i;
            return this;
        }

        public Builder setSoKeepAlive(boolean z) {
            this.d = z;
            return this;
        }

        public Builder setTcpNoDelay(boolean z) {
            this.e = z;
            return this;
        }

        public Builder setSndBufSize(int i) {
            this.f = i;
            return this;
        }

        public Builder setRcvBufSize(int i) {
            this.g = i;
            return this;
        }

        public Builder setBacklogSize(int i) {
            this.h = i;
            return this;
        }

        public SocketConfig build() {
            SocketConfig socketConfig = new SocketConfig(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
            return socketConfig;
        }
    }

    SocketConfig(int i, boolean z, int i2, boolean z2, boolean z3, int i3, int i4, int i5) {
        this.a = i;
        this.b = z;
        this.c = i2;
        this.d = z2;
        this.e = z3;
        this.f = i3;
        this.g = i4;
        this.h = i5;
    }

    public int getSoTimeout() {
        return this.a;
    }

    public boolean isSoReuseAddress() {
        return this.b;
    }

    public int getSoLinger() {
        return this.c;
    }

    public boolean isSoKeepAlive() {
        return this.d;
    }

    public boolean isTcpNoDelay() {
        return this.e;
    }

    public int getSndBufSize() {
        return this.f;
    }

    public int getRcvBufSize() {
        return this.g;
    }

    public int getBacklogSize() {
        return this.h;
    }

    /* access modifiers changed from: protected */
    public SocketConfig clone() {
        return (SocketConfig) super.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[soTimeout=");
        sb.append(this.a);
        sb.append(", soReuseAddress=");
        sb.append(this.b);
        sb.append(", soLinger=");
        sb.append(this.c);
        sb.append(", soKeepAlive=");
        sb.append(this.d);
        sb.append(", tcpNoDelay=");
        sb.append(this.e);
        sb.append(", sndBufSize=");
        sb.append(this.f);
        sb.append(", rcvBufSize=");
        sb.append(this.g);
        sb.append(", backlogSize=");
        sb.append(this.h);
        sb.append("]");
        return sb.toString();
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(SocketConfig socketConfig) {
        Args.notNull(socketConfig, "Socket config");
        return new Builder().setSoTimeout(socketConfig.getSoTimeout()).setSoReuseAddress(socketConfig.isSoReuseAddress()).setSoLinger(socketConfig.getSoLinger()).setSoKeepAlive(socketConfig.isSoKeepAlive()).setTcpNoDelay(socketConfig.isTcpNoDelay()).setSndBufSize(socketConfig.getSndBufSize()).setRcvBufSize(socketConfig.getRcvBufSize()).setBacklogSize(socketConfig.getBacklogSize());
    }
}
