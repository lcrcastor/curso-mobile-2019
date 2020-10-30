package com.google.android.gms.internal;

import com.google.android.gms.internal.zzah.zzf;
import com.google.android.gms.internal.zzah.zzj;

public interface zzafe {

    public static final class zza extends zzare<zza> {
        public long aJj;
        public zzj aJk;
        public zzf zzxr;

        public zza() {
            zzcks();
        }

        public static zza zzao(byte[] bArr) {
            return (zza) zzark.zza(new zza(), bArr);
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            if (this.aJj != zza.aJj) {
                return false;
            }
            if (this.zzxr == null) {
                if (zza.zzxr != null) {
                    return false;
                }
            } else if (!this.zzxr.equals(zza.zzxr)) {
                return false;
            }
            if (this.aJk == null) {
                if (zza.aJk != null) {
                    return false;
                }
            } else if (!this.aJk.equals(zza.aJk)) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zza.bqv);
            }
            if (zza.bqv != null) {
                if (zza.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.aJj ^ (this.aJj >>> 32)))) * 31) + (this.zzxr == null ? 0 : this.zzxr.hashCode())) * 31) + (this.aJk == null ? 0 : this.aJk.hashCode())) * 31;
            if (this.bqv != null && !this.bqv.isEmpty()) {
                i = this.bqv.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzard zzard) {
            zzard.zzb(1, this.aJj);
            if (this.zzxr != null) {
                zzard.zza(2, (zzark) this.zzxr);
            }
            if (this.aJk != null) {
                zzard.zza(3, (zzark) this.aJk);
            }
            super.zza(zzard);
        }

        /* renamed from: zzat */
        public zza zzb(zzarc zzarc) {
            zzark zzark;
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw != 8) {
                    if (cw == 18) {
                        if (this.zzxr == null) {
                            this.zzxr = new zzf();
                        }
                        zzark = this.zzxr;
                    } else if (cw == 26) {
                        if (this.aJk == null) {
                            this.aJk = new zzj();
                        }
                        zzark = this.aJk;
                    } else if (!super.zza(zzarc, cw)) {
                        return this;
                    }
                    zzarc.zza(zzark);
                } else {
                    this.aJj = zzarc.cz();
                }
            }
        }

        public zza zzcks() {
            this.aJj = 0;
            this.zzxr = null;
            this.aJk = null;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx() + zzard.zzf(1, this.aJj);
            if (this.zzxr != null) {
                zzx += zzard.zzc(2, (zzark) this.zzxr);
            }
            return this.aJk != null ? zzx + zzard.zzc(3, (zzark) this.aJk) : zzx;
        }
    }
}
