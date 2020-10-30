package okhttp3;

import java.io.IOException;

public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2"),
    H2_PRIOR_KNOWLEDGE("h2_prior_knowledge"),
    QUIC("quic");
    
    private final String a;

    private Protocol(String str) {
        this.a = str;
    }

    public static Protocol get(String str) {
        if (str.equals(HTTP_1_0.a)) {
            return HTTP_1_0;
        }
        if (str.equals(HTTP_1_1.a)) {
            return HTTP_1_1;
        }
        if (str.equals(H2_PRIOR_KNOWLEDGE.a)) {
            return H2_PRIOR_KNOWLEDGE;
        }
        if (str.equals(HTTP_2.a)) {
            return HTTP_2;
        }
        if (str.equals(SPDY_3.a)) {
            return SPDY_3;
        }
        if (str.equals(QUIC.a)) {
            return QUIC;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected protocol: ");
        sb.append(str);
        throw new IOException(sb.toString());
    }

    public String toString() {
        return this.a;
    }
}
