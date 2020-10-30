package com.google.android.gms.analytics.internal;

public abstract class zzd extends zzc {
    private boolean a;

    protected zzd(zzf zzf) {
        super(zzf);
    }

    public void initialize() {
        zzym();
        this.a = true;
    }

    public boolean isInitialized() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void zzaax() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public abstract void zzym();
}
