package com.google.android.gms.internal;

import java.io.IOException;
import java.io.StringWriter;

public abstract class zzaoh {
    /* access modifiers changed from: 0000 */
    public Boolean a() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public Number aQ() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String aR() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public boolean aS() {
        return this instanceof zzaoe;
    }

    public boolean aT() {
        return this instanceof zzaok;
    }

    public boolean aU() {
        return this instanceof zzaon;
    }

    public boolean aV() {
        return this instanceof zzaoj;
    }

    public zzaok aW() {
        if (aT()) {
            return (zzaok) this;
        }
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
        sb.append("Not a JSON Object: ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }

    public zzaoe aX() {
        if (aS()) {
            return (zzaoe) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public zzaon aY() {
        if (aU()) {
            return (zzaon) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    public boolean getAsBoolean() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public double getAsDouble() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int getAsInt() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long getAsLong() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            zzaqa zzaqa = new zzaqa(stringWriter);
            zzaqa.setLenient(true);
            zzapi.zzb(this, zzaqa);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
