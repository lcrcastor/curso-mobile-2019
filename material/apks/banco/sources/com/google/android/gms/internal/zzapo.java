package com.google.android.gms.internal;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class zzapo extends zzapy {
    private static final Reader a = new Reader() {
        public void close() {
            throw new AssertionError();
        }

        public int read(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }
    };
    private static final Object b = new Object();
    private final List<Object> c = new ArrayList();

    public zzapo(zzaoh zzaoh) {
        super(a);
        this.c.add(zzaoh);
    }

    private Object a() {
        return this.c.get(this.c.size() - 1);
    }

    private void a(zzapz zzapz) {
        if (bn() != zzapz) {
            String valueOf = String.valueOf(zzapz);
            String valueOf2 = String.valueOf(bn());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18 + String.valueOf(valueOf2).length());
            sb.append("Expected ");
            sb.append(valueOf);
            sb.append(" but was ");
            sb.append(valueOf2);
            throw new IllegalStateException(sb.toString());
        }
    }

    private Object b() {
        return this.c.remove(this.c.size() - 1);
    }

    public void beginArray() {
        a(zzapz.BEGIN_ARRAY);
        this.c.add(((zzaoe) a()).iterator());
    }

    public void beginObject() {
        a(zzapz.BEGIN_OBJECT);
        this.c.add(((zzaok) a()).entrySet().iterator());
    }

    public zzapz bn() {
        if (this.c.isEmpty()) {
            return zzapz.END_DOCUMENT;
        }
        Object a2 = a();
        if (a2 instanceof Iterator) {
            boolean z = this.c.get(this.c.size() - 2) instanceof zzaok;
            Iterator it = (Iterator) a2;
            if (!it.hasNext()) {
                return z ? zzapz.END_OBJECT : zzapz.END_ARRAY;
            }
            if (z) {
                return zzapz.NAME;
            }
            this.c.add(it.next());
            return bn();
        } else if (a2 instanceof zzaok) {
            return zzapz.BEGIN_OBJECT;
        } else {
            if (a2 instanceof zzaoe) {
                return zzapz.BEGIN_ARRAY;
            }
            if (a2 instanceof zzaon) {
                zzaon zzaon = (zzaon) a2;
                if (zzaon.bc()) {
                    return zzapz.STRING;
                }
                if (zzaon.ba()) {
                    return zzapz.BOOLEAN;
                }
                if (zzaon.bb()) {
                    return zzapz.NUMBER;
                }
                throw new AssertionError();
            } else if (a2 instanceof zzaoj) {
                return zzapz.NULL;
            } else {
                if (a2 == b) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    public void bq() {
        a(zzapz.NAME);
        Entry entry = (Entry) ((Iterator) a()).next();
        this.c.add(entry.getValue());
        this.c.add(new zzaon((String) entry.getKey()));
    }

    public void close() {
        this.c.clear();
        this.c.add(b);
    }

    public void endArray() {
        a(zzapz.END_ARRAY);
        b();
        b();
    }

    public void endObject() {
        a(zzapz.END_OBJECT);
        b();
        b();
    }

    public boolean hasNext() {
        zzapz bn = bn();
        return (bn == zzapz.END_OBJECT || bn == zzapz.END_ARRAY) ? false : true;
    }

    public boolean nextBoolean() {
        a(zzapz.BOOLEAN);
        return ((zzaon) b()).getAsBoolean();
    }

    public double nextDouble() {
        zzapz bn = bn();
        if (bn == zzapz.NUMBER || bn == zzapz.STRING) {
            double asDouble = ((zzaon) a()).getAsDouble();
            if (isLenient() || (!Double.isNaN(asDouble) && !Double.isInfinite(asDouble))) {
                b();
                return asDouble;
            }
            StringBuilder sb = new StringBuilder(57);
            sb.append("JSON forbids NaN and infinities: ");
            sb.append(asDouble);
            throw new NumberFormatException(sb.toString());
        }
        String valueOf = String.valueOf(zzapz.NUMBER);
        String valueOf2 = String.valueOf(bn);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 18 + String.valueOf(valueOf2).length());
        sb2.append("Expected ");
        sb2.append(valueOf);
        sb2.append(" but was ");
        sb2.append(valueOf2);
        throw new IllegalStateException(sb2.toString());
    }

    public int nextInt() {
        zzapz bn = bn();
        if (bn == zzapz.NUMBER || bn == zzapz.STRING) {
            int asInt = ((zzaon) a()).getAsInt();
            b();
            return asInt;
        }
        String valueOf = String.valueOf(zzapz.NUMBER);
        String valueOf2 = String.valueOf(bn);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18 + String.valueOf(valueOf2).length());
        sb.append("Expected ");
        sb.append(valueOf);
        sb.append(" but was ");
        sb.append(valueOf2);
        throw new IllegalStateException(sb.toString());
    }

    public long nextLong() {
        zzapz bn = bn();
        if (bn == zzapz.NUMBER || bn == zzapz.STRING) {
            long asLong = ((zzaon) a()).getAsLong();
            b();
            return asLong;
        }
        String valueOf = String.valueOf(zzapz.NUMBER);
        String valueOf2 = String.valueOf(bn);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18 + String.valueOf(valueOf2).length());
        sb.append("Expected ");
        sb.append(valueOf);
        sb.append(" but was ");
        sb.append(valueOf2);
        throw new IllegalStateException(sb.toString());
    }

    public String nextName() {
        a(zzapz.NAME);
        Entry entry = (Entry) ((Iterator) a()).next();
        this.c.add(entry.getValue());
        return (String) entry.getKey();
    }

    public void nextNull() {
        a(zzapz.NULL);
        b();
    }

    public String nextString() {
        zzapz bn = bn();
        if (bn == zzapz.STRING || bn == zzapz.NUMBER) {
            return ((zzaon) b()).aR();
        }
        String valueOf = String.valueOf(zzapz.STRING);
        String valueOf2 = String.valueOf(bn);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18 + String.valueOf(valueOf2).length());
        sb.append("Expected ");
        sb.append(valueOf);
        sb.append(" but was ");
        sb.append(valueOf2);
        throw new IllegalStateException(sb.toString());
    }

    public void skipValue() {
        if (bn() == zzapz.NAME) {
            nextName();
        } else {
            b();
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
