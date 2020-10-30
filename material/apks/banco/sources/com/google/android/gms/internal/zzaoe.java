package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class zzaoe extends zzaoh implements Iterable<zzaoh> {
    private final List<zzaoh> a = new ArrayList();

    public Number aQ() {
        if (this.a.size() == 1) {
            return ((zzaoh) this.a.get(0)).aQ();
        }
        throw new IllegalStateException();
    }

    public String aR() {
        if (this.a.size() == 1) {
            return ((zzaoh) this.a.get(0)).aR();
        }
        throw new IllegalStateException();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzaoe) && ((zzaoe) obj).a.equals(this.a));
    }

    public boolean getAsBoolean() {
        if (this.a.size() == 1) {
            return ((zzaoh) this.a.get(0)).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    public double getAsDouble() {
        if (this.a.size() == 1) {
            return ((zzaoh) this.a.get(0)).getAsDouble();
        }
        throw new IllegalStateException();
    }

    public int getAsInt() {
        if (this.a.size() == 1) {
            return ((zzaoh) this.a.get(0)).getAsInt();
        }
        throw new IllegalStateException();
    }

    public long getAsLong() {
        if (this.a.size() == 1) {
            return ((zzaoh) this.a.get(0)).getAsLong();
        }
        throw new IllegalStateException();
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public Iterator<zzaoh> iterator() {
        return this.a.iterator();
    }

    public int size() {
        return this.a.size();
    }

    public zzaoh zzagv(int i) {
        return (zzaoh) this.a.get(i);
    }

    public void zzc(zzaoh zzaoh) {
        if (zzaoh == null) {
            zzaoh = zzaoj.bld;
        }
        this.a.add(zzaoh);
    }
}
