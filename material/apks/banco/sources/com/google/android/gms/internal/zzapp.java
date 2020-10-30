package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class zzapp extends zzaqa {
    private static final Writer a = new Writer() {
        public void close() {
            throw new AssertionError();
        }

        public void flush() {
            throw new AssertionError();
        }

        public void write(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }
    };
    private static final zzaon b = new zzaon("closed");
    private final List<zzaoh> c = new ArrayList();
    private String d;
    private zzaoh e = zzaoj.bld;

    public zzapp() {
        super(a);
    }

    private zzaoh a() {
        return (zzaoh) this.c.get(this.c.size() - 1);
    }

    private void a(zzaoh zzaoh) {
        if (this.d != null) {
            if (!zzaoh.aV() || bK()) {
                ((zzaok) a()).zza(this.d, zzaoh);
            }
            this.d = null;
        } else if (this.c.isEmpty()) {
            this.e = zzaoh;
        } else {
            zzaoh a2 = a();
            if (a2 instanceof zzaoe) {
                ((zzaoe) a2).zzc(zzaoh);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public zzaoh br() {
        if (this.c.isEmpty()) {
            return this.e;
        }
        String valueOf = String.valueOf(this.c);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
        sb.append("Expected one JSON element but was ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }

    public zzaqa bt() {
        zzaoe zzaoe = new zzaoe();
        a(zzaoe);
        this.c.add(zzaoe);
        return this;
    }

    public zzaqa bu() {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (a() instanceof zzaoe) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public zzaqa bv() {
        zzaok zzaok = new zzaok();
        a(zzaok);
        this.c.add(zzaok);
        return this;
    }

    public zzaqa bw() {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (a() instanceof zzaok) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public zzaqa bx() {
        a(zzaoj.bld);
        return this;
    }

    public void close() {
        if (!this.c.isEmpty()) {
            throw new IOException("Incomplete document");
        }
        this.c.add(b);
    }

    public void flush() {
    }

    public zzaqa zza(Number number) {
        if (number == null) {
            return bx();
        }
        if (!isLenient()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                String valueOf = String.valueOf(number);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 33);
                sb.append("JSON forbids NaN and infinities: ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        a(new zzaon(number));
        return this;
    }

    public zzaqa zzcu(long j) {
        a(new zzaon((Number) Long.valueOf(j)));
        return this;
    }

    public zzaqa zzdf(boolean z) {
        a(new zzaon(Boolean.valueOf(z)));
        return this;
    }

    public zzaqa zzus(String str) {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (a() instanceof zzaok) {
            this.d = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public zzaqa zzut(String str) {
        if (str == null) {
            return bx();
        }
        a(new zzaon(str));
        return this;
    }
}
