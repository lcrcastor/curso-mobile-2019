package com.google.android.gms.internal;

import java.io.IOException;

public class zzarj extends IOException {
    public zzarj(String str) {
        super(str);
    }

    static zzarj a() {
        return new zzarj("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzarj b() {
        return new zzarj("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzarj c() {
        return new zzarj("CodedInputStream encountered a malformed varint.");
    }

    static zzarj d() {
        return new zzarj("Protocol message contained an invalid tag (zero).");
    }

    static zzarj e() {
        return new zzarj("Protocol message end-group tag did not match expected tag.");
    }

    static zzarj f() {
        return new zzarj("Protocol message tag had invalid wire type.");
    }

    static zzarj g() {
        return new zzarj("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }
}
