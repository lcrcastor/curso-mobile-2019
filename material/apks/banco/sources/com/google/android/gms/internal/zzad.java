package com.google.android.gms.internal;

public interface zzad {

    public static final class zza extends zzare<zza> {
        public String stackTrace;
        public String zzck;
        public Long zzcl;
        public String zzcm;
        public String zzcn;
        public Long zzco;
        public Long zzcp;
        public String zzcq;
        public Long zzcr;
        public String zzcs;

        public zza() {
            this.zzck = null;
            this.zzcl = null;
            this.stackTrace = null;
            this.zzcm = null;
            this.zzcn = null;
            this.zzco = null;
            this.zzcp = null;
            this.zzcq = null;
            this.zzcr = null;
            this.zzcs = null;
            this.bqE = -1;
        }

        /* renamed from: zza */
        public zza zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                switch (cw) {
                    case 0:
                        return this;
                    case 10:
                        this.zzck = zzarc.readString();
                        break;
                    case 16:
                        this.zzcl = Long.valueOf(zzarc.cz());
                        break;
                    case 26:
                        this.stackTrace = zzarc.readString();
                        break;
                    case 34:
                        this.zzcm = zzarc.readString();
                        break;
                    case 42:
                        this.zzcn = zzarc.readString();
                        break;
                    case 48:
                        this.zzco = Long.valueOf(zzarc.cz());
                        break;
                    case 56:
                        this.zzcp = Long.valueOf(zzarc.cz());
                        break;
                    case 66:
                        this.zzcq = zzarc.readString();
                        break;
                    case 72:
                        this.zzcr = Long.valueOf(zzarc.cz());
                        break;
                    case 82:
                        this.zzcs = zzarc.readString();
                        break;
                    default:
                        if (super.zza(zzarc, cw)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        public void zza(zzard zzard) {
            if (this.zzck != null) {
                zzard.zzr(1, this.zzck);
            }
            if (this.zzcl != null) {
                zzard.zzb(2, this.zzcl.longValue());
            }
            if (this.stackTrace != null) {
                zzard.zzr(3, this.stackTrace);
            }
            if (this.zzcm != null) {
                zzard.zzr(4, this.zzcm);
            }
            if (this.zzcn != null) {
                zzard.zzr(5, this.zzcn);
            }
            if (this.zzco != null) {
                zzard.zzb(6, this.zzco.longValue());
            }
            if (this.zzcp != null) {
                zzard.zzb(7, this.zzcp.longValue());
            }
            if (this.zzcq != null) {
                zzard.zzr(8, this.zzcq);
            }
            if (this.zzcr != null) {
                zzard.zzb(9, this.zzcr.longValue());
            }
            if (this.zzcs != null) {
                zzard.zzr(10, this.zzcs);
            }
            super.zza(zzard);
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzck != null) {
                zzx += zzard.zzs(1, this.zzck);
            }
            if (this.zzcl != null) {
                zzx += zzard.zzf(2, this.zzcl.longValue());
            }
            if (this.stackTrace != null) {
                zzx += zzard.zzs(3, this.stackTrace);
            }
            if (this.zzcm != null) {
                zzx += zzard.zzs(4, this.zzcm);
            }
            if (this.zzcn != null) {
                zzx += zzard.zzs(5, this.zzcn);
            }
            if (this.zzco != null) {
                zzx += zzard.zzf(6, this.zzco.longValue());
            }
            if (this.zzcp != null) {
                zzx += zzard.zzf(7, this.zzcp.longValue());
            }
            if (this.zzcq != null) {
                zzx += zzard.zzs(8, this.zzcq);
            }
            if (this.zzcr != null) {
                zzx += zzard.zzf(9, this.zzcr.longValue());
            }
            return this.zzcs != null ? zzx + zzard.zzs(10, this.zzcs) : zzx;
        }
    }
}
