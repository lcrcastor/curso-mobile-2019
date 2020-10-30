package org.bouncycastle.asn1.eac;

public class Flags {
    int a = 0;

    public Flags() {
    }

    public Flags(int i) {
        this.a = i;
    }

    public int getFlags() {
        return this.a;
    }

    public boolean isSet(int i) {
        return (i & this.a) != 0;
    }

    public void set(int i) {
        this.a = i | this.a;
    }
}
