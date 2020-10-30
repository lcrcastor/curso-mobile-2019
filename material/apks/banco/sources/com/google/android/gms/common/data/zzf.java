package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class zzf<T> extends AbstractDataBuffer<T> {
    private boolean a = false;
    private ArrayList<Integer> b;

    protected zzf(DataHolder dataHolder) {
        super(dataHolder);
    }

    private void a() {
        synchronized (this) {
            if (!this.a) {
                int count = this.xi.getCount();
                this.b = new ArrayList<>();
                if (count > 0) {
                    this.b.add(Integer.valueOf(0));
                    String zzath = zzath();
                    String zzd = this.xi.zzd(zzath, 0, this.xi.zzgb(0));
                    for (int i = 1; i < count; i++) {
                        int zzgb = this.xi.zzgb(i);
                        String zzd2 = this.xi.zzd(zzath, i, zzgb);
                        if (zzd2 == null) {
                            StringBuilder sb = new StringBuilder(String.valueOf(zzath).length() + 78);
                            sb.append("Missing value for markerColumn: ");
                            sb.append(zzath);
                            sb.append(", at row: ");
                            sb.append(i);
                            sb.append(", for window: ");
                            sb.append(zzgb);
                            throw new NullPointerException(sb.toString());
                        }
                        if (!zzd2.equals(zzd)) {
                            this.b.add(Integer.valueOf(i));
                            zzd = zzd2;
                        }
                    }
                }
                this.a = true;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int a(int i) {
        if (i >= 0 && i < this.b.size()) {
            return ((Integer) this.b.get(i)).intValue();
        }
        StringBuilder sb = new StringBuilder(53);
        sb.append("Position ");
        sb.append(i);
        sb.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(sb.toString());
    }

    public final T get(int i) {
        a();
        return zzl(a(i), zzgg(i));
    }

    public int getCount() {
        a();
        return this.b.size();
    }

    /* access modifiers changed from: protected */
    public abstract String zzath();

    /* access modifiers changed from: protected */
    public String zzatj() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int zzgg(int i) {
        if (i < 0 || i == this.b.size()) {
            return 0;
        }
        int count = (i == this.b.size() - 1 ? this.xi.getCount() : ((Integer) this.b.get(i + 1)).intValue()) - ((Integer) this.b.get(i)).intValue();
        if (count == 1) {
            int a2 = a(i);
            int zzgb = this.xi.zzgb(a2);
            String zzatj = zzatj();
            if (zzatj == null || this.xi.zzd(zzatj, a2, zzgb) != null) {
                return count;
            }
            return 0;
        }
        return count;
    }

    /* access modifiers changed from: protected */
    public abstract T zzl(int i, int i2);
}
