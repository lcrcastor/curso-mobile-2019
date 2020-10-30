package org.bouncycastle.util.io.pem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PemObject implements PemObjectGenerator {
    private static final List a = Collections.unmodifiableList(new ArrayList());
    private String b;
    private List c;
    private byte[] d;

    public PemObject(String str, List list, byte[] bArr) {
        this.b = str;
        this.c = Collections.unmodifiableList(list);
        this.d = bArr;
    }

    public PemObject(String str, byte[] bArr) {
        this(str, a, bArr);
    }

    public PemObject generate() {
        return this;
    }

    public byte[] getContent() {
        return this.d;
    }

    public List getHeaders() {
        return this.c;
    }

    public String getType() {
        return this.b;
    }
}
