package com.google.android.gms.internal;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.tls.CipherSuite;

public interface zzah {

    public static final class zza extends zzare<zza> {
        public int level;
        public int zzvn;
        public int zzvo;

        public zza() {
            zzaa();
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
            if (this.level != zza.level || this.zzvn != zza.zzvn || this.zzvo != zza.zzvo) {
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
            return ((((((((getClass().getName().hashCode() + 527) * 31) + this.level) * 31) + this.zzvn) * 31) + this.zzvo) * 31) + ((this.bqv == null || this.bqv.isEmpty()) ? 0 : this.bqv.hashCode());
        }

        public void zza(zzard zzard) {
            if (this.level != 1) {
                zzard.zzae(1, this.level);
            }
            if (this.zzvn != 0) {
                zzard.zzae(2, this.zzvn);
            }
            if (this.zzvo != 0) {
                zzard.zzae(3, this.zzvo);
            }
            super.zza(zzard);
        }

        public zza zzaa() {
            this.level = 1;
            this.zzvn = 0;
            this.zzvo = 0;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzk */
        public zza zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw != 0) {
                    if (cw == 8) {
                        int cA = zzarc.cA();
                        switch (cA) {
                            case 1:
                            case 2:
                            case 3:
                                this.level = cA;
                                break;
                        }
                    } else if (cw == 16) {
                        this.zzvn = zzarc.cA();
                    } else if (cw == 24) {
                        this.zzvo = zzarc.cA();
                    } else if (!super.zza(zzarc, cw)) {
                        return this;
                    }
                } else {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.level != 1) {
                zzx += zzard.zzag(1, this.level);
            }
            if (this.zzvn != 0) {
                zzx += zzard.zzag(2, this.zzvn);
            }
            return this.zzvo != 0 ? zzx + zzard.zzag(3, this.zzvo) : zzx;
        }
    }

    public static final class zzb extends zzare<zzb> {
        private static volatile zzb[] a;
        public int name;
        public int[] zzvq;
        public int zzvr;
        public boolean zzvs;
        public boolean zzvt;

        public zzb() {
            zzac();
        }

        public static zzb[] zzab() {
            if (a == null) {
                synchronized (zzari.bqD) {
                    if (a == null) {
                        a = new zzb[0];
                    }
                }
            }
            return a;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb zzb = (zzb) obj;
            if (!zzari.equals(this.zzvq, zzb.zzvq) || this.zzvr != zzb.zzvr || this.name != zzb.name || this.zzvs != zzb.zzvs || this.zzvt != zzb.zzvt) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zzb.bqv);
            }
            if (zzb.bqv != null) {
                if (zzb.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            int i = 1237;
            int hashCode = (((((((((getClass().getName().hashCode() + 527) * 31) + zzari.hashCode(this.zzvq)) * 31) + this.zzvr) * 31) + this.name) * 31) + (this.zzvs ? 1231 : 1237)) * 31;
            if (this.zzvt) {
                i = 1231;
            }
            return ((hashCode + i) * 31) + ((this.bqv == null || this.bqv.isEmpty()) ? 0 : this.bqv.hashCode());
        }

        public void zza(zzard zzard) {
            if (this.zzvt) {
                zzard.zzj(1, this.zzvt);
            }
            zzard.zzae(2, this.zzvr);
            if (this.zzvq != null && this.zzvq.length > 0) {
                for (int zzae : this.zzvq) {
                    zzard.zzae(3, zzae);
                }
            }
            if (this.name != 0) {
                zzard.zzae(4, this.name);
            }
            if (this.zzvs) {
                zzard.zzj(6, this.zzvs);
            }
            super.zza(zzard);
        }

        public zzb zzac() {
            this.zzvq = zzarn.bqF;
            this.zzvr = 0;
            this.name = 0;
            this.zzvs = false;
            this.zzvt = false;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzl */
        public zzb zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 8) {
                    this.zzvt = zzarc.cC();
                } else if (cw == 16) {
                    this.zzvr = zzarc.cA();
                } else if (cw == 24) {
                    int zzc = zzarn.zzc(zzarc, 24);
                    int length = this.zzvq == null ? 0 : this.zzvq.length;
                    int[] iArr = new int[(zzc + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzvq, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = zzarc.cA();
                        zzarc.cw();
                        length++;
                    }
                    iArr[length] = zzarc.cA();
                    this.zzvq = iArr;
                } else if (cw == 26) {
                    int zzahc = zzarc.zzahc(zzarc.cF());
                    int position = zzarc.getPosition();
                    int i = 0;
                    while (zzarc.cK() > 0) {
                        zzarc.cA();
                        i++;
                    }
                    zzarc.zzahe(position);
                    int length2 = this.zzvq == null ? 0 : this.zzvq.length;
                    int[] iArr2 = new int[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzvq, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = zzarc.cA();
                        length2++;
                    }
                    this.zzvq = iArr2;
                    zzarc.zzahd(zzahc);
                } else if (cw == 32) {
                    this.name = zzarc.cA();
                } else if (cw == 48) {
                    this.zzvs = zzarc.cC();
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzvt) {
                zzx += zzard.zzk(1, this.zzvt);
            }
            int zzag = zzx + zzard.zzag(2, this.zzvr);
            if (this.zzvq != null && this.zzvq.length > 0) {
                int i = 0;
                for (int zzahi : this.zzvq) {
                    i += zzard.zzahi(zzahi);
                }
                zzag = zzag + i + (this.zzvq.length * 1);
            }
            if (this.name != 0) {
                zzag += zzard.zzag(4, this.name);
            }
            return this.zzvs ? zzag + zzard.zzk(6, this.zzvs) : zzag;
        }
    }

    public static final class zzc extends zzare<zzc> {
        private static volatile zzc[] a;
        public String zzcb;
        public long zzvv;
        public long zzvw;
        public boolean zzvx;
        public long zzvy;

        public zzc() {
            zzae();
        }

        public static zzc[] zzad() {
            if (a == null) {
                synchronized (zzari.bqD) {
                    if (a == null) {
                        a = new zzc[0];
                    }
                }
            }
            return a;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzc)) {
                return false;
            }
            zzc zzc = (zzc) obj;
            if (this.zzcb == null) {
                if (zzc.zzcb != null) {
                    return false;
                }
            } else if (!this.zzcb.equals(zzc.zzcb)) {
                return false;
            }
            if (this.zzvv != zzc.zzvv || this.zzvw != zzc.zzvw || this.zzvx != zzc.zzvx || this.zzvy != zzc.zzvy) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zzc.bqv);
            }
            if (zzc.bqv != null) {
                if (zzc.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzcb == null ? 0 : this.zzcb.hashCode())) * 31) + ((int) (this.zzvv ^ (this.zzvv >>> 32)))) * 31) + ((int) (this.zzvw ^ (this.zzvw >>> 32)))) * 31) + (this.zzvx ? 1231 : 1237)) * 31) + ((int) (this.zzvy ^ (this.zzvy >>> 32)))) * 31;
            if (this.bqv != null && !this.bqv.isEmpty()) {
                i = this.bqv.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzard zzard) {
            if (!this.zzcb.equals("")) {
                zzard.zzr(1, this.zzcb);
            }
            if (this.zzvv != 0) {
                zzard.zzb(2, this.zzvv);
            }
            if (this.zzvw != 2147483647L) {
                zzard.zzb(3, this.zzvw);
            }
            if (this.zzvx) {
                zzard.zzj(4, this.zzvx);
            }
            if (this.zzvy != 0) {
                zzard.zzb(5, this.zzvy);
            }
            super.zza(zzard);
        }

        public zzc zzae() {
            this.zzcb = "";
            this.zzvv = 0;
            this.zzvw = 2147483647L;
            this.zzvx = false;
            this.zzvy = 0;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzm */
        public zzc zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 10) {
                    this.zzcb = zzarc.readString();
                } else if (cw == 16) {
                    this.zzvv = zzarc.cz();
                } else if (cw == 24) {
                    this.zzvw = zzarc.cz();
                } else if (cw == 32) {
                    this.zzvx = zzarc.cC();
                } else if (cw == 40) {
                    this.zzvy = zzarc.cz();
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (!this.zzcb.equals("")) {
                zzx += zzard.zzs(1, this.zzcb);
            }
            if (this.zzvv != 0) {
                zzx += zzard.zzf(2, this.zzvv);
            }
            if (this.zzvw != 2147483647L) {
                zzx += zzard.zzf(3, this.zzvw);
            }
            if (this.zzvx) {
                zzx += zzard.zzk(4, this.zzvx);
            }
            return this.zzvy != 0 ? zzx + zzard.zzf(5, this.zzvy) : zzx;
        }
    }

    public static final class zzd extends zzare<zzd> {
        public com.google.android.gms.internal.zzai.zza[] zzvz;
        public com.google.android.gms.internal.zzai.zza[] zzwa;
        public zzc[] zzwb;

        public zzd() {
            zzaf();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzd)) {
                return false;
            }
            zzd zzd = (zzd) obj;
            if (!zzari.equals((Object[]) this.zzvz, (Object[]) zzd.zzvz) || !zzari.equals((Object[]) this.zzwa, (Object[]) zzd.zzwa) || !zzari.equals((Object[]) this.zzwb, (Object[]) zzd.zzwb)) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zzd.bqv);
            }
            if (zzd.bqv != null) {
                if (zzd.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return ((((((((getClass().getName().hashCode() + 527) * 31) + zzari.hashCode((Object[]) this.zzvz)) * 31) + zzari.hashCode((Object[]) this.zzwa)) * 31) + zzari.hashCode((Object[]) this.zzwb)) * 31) + ((this.bqv == null || this.bqv.isEmpty()) ? 0 : this.bqv.hashCode());
        }

        public void zza(zzard zzard) {
            if (this.zzvz != null && this.zzvz.length > 0) {
                for (com.google.android.gms.internal.zzai.zza zza : this.zzvz) {
                    if (zza != null) {
                        zzard.zza(1, (zzark) zza);
                    }
                }
            }
            if (this.zzwa != null && this.zzwa.length > 0) {
                for (com.google.android.gms.internal.zzai.zza zza2 : this.zzwa) {
                    if (zza2 != null) {
                        zzard.zza(2, (zzark) zza2);
                    }
                }
            }
            if (this.zzwb != null && this.zzwb.length > 0) {
                for (zzc zzc : this.zzwb) {
                    if (zzc != null) {
                        zzard.zza(3, (zzark) zzc);
                    }
                }
            }
            super.zza(zzard);
        }

        public zzd zzaf() {
            this.zzvz = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzwa = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzwb = zzc.zzad();
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzn */
        public zzd zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 10) {
                    int zzc = zzarn.zzc(zzarc, 10);
                    int length = this.zzvz == null ? 0 : this.zzvz.length;
                    com.google.android.gms.internal.zzai.zza[] zzaArr = new com.google.android.gms.internal.zzai.zza[(zzc + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzvz, 0, zzaArr, 0, length);
                    }
                    while (length < zzaArr.length - 1) {
                        zzaArr[length] = new com.google.android.gms.internal.zzai.zza();
                        zzarc.zza(zzaArr[length]);
                        zzarc.cw();
                        length++;
                    }
                    zzaArr[length] = new com.google.android.gms.internal.zzai.zza();
                    zzarc.zza(zzaArr[length]);
                    this.zzvz = zzaArr;
                } else if (cw == 18) {
                    int zzc2 = zzarn.zzc(zzarc, 18);
                    int length2 = this.zzwa == null ? 0 : this.zzwa.length;
                    com.google.android.gms.internal.zzai.zza[] zzaArr2 = new com.google.android.gms.internal.zzai.zza[(zzc2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzwa, 0, zzaArr2, 0, length2);
                    }
                    while (length2 < zzaArr2.length - 1) {
                        zzaArr2[length2] = new com.google.android.gms.internal.zzai.zza();
                        zzarc.zza(zzaArr2[length2]);
                        zzarc.cw();
                        length2++;
                    }
                    zzaArr2[length2] = new com.google.android.gms.internal.zzai.zza();
                    zzarc.zza(zzaArr2[length2]);
                    this.zzwa = zzaArr2;
                } else if (cw == 26) {
                    int zzc3 = zzarn.zzc(zzarc, 26);
                    int length3 = this.zzwb == null ? 0 : this.zzwb.length;
                    zzc[] zzcArr = new zzc[(zzc3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzwb, 0, zzcArr, 0, length3);
                    }
                    while (length3 < zzcArr.length - 1) {
                        zzcArr[length3] = new zzc();
                        zzarc.zza(zzcArr[length3]);
                        zzarc.cw();
                        length3++;
                    }
                    zzcArr[length3] = new zzc();
                    zzarc.zza(zzcArr[length3]);
                    this.zzwb = zzcArr;
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzvz != null && this.zzvz.length > 0) {
                int i = zzx;
                for (com.google.android.gms.internal.zzai.zza zza : this.zzvz) {
                    if (zza != null) {
                        i += zzard.zzc(1, (zzark) zza);
                    }
                }
                zzx = i;
            }
            if (this.zzwa != null && this.zzwa.length > 0) {
                int i2 = zzx;
                for (com.google.android.gms.internal.zzai.zza zza2 : this.zzwa) {
                    if (zza2 != null) {
                        i2 += zzard.zzc(2, (zzark) zza2);
                    }
                }
                zzx = i2;
            }
            if (this.zzwb != null && this.zzwb.length > 0) {
                for (zzc zzc : this.zzwb) {
                    if (zzc != null) {
                        zzx += zzard.zzc(3, (zzark) zzc);
                    }
                }
            }
            return zzx;
        }
    }

    public static final class zze extends zzare<zze> {
        private static volatile zze[] a;
        public int key;
        public int value;

        public zze() {
            zzah();
        }

        public static zze[] zzag() {
            if (a == null) {
                synchronized (zzari.bqD) {
                    if (a == null) {
                        a = new zze[0];
                    }
                }
            }
            return a;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zze)) {
                return false;
            }
            zze zze = (zze) obj;
            if (this.key != zze.key || this.value != zze.value) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zze.bqv);
            }
            if (zze.bqv != null) {
                if (zze.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return ((((((getClass().getName().hashCode() + 527) * 31) + this.key) * 31) + this.value) * 31) + ((this.bqv == null || this.bqv.isEmpty()) ? 0 : this.bqv.hashCode());
        }

        public void zza(zzard zzard) {
            zzard.zzae(1, this.key);
            zzard.zzae(2, this.value);
            super.zza(zzard);
        }

        public zze zzah() {
            this.key = 0;
            this.value = 0;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzo */
        public zze zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 8) {
                    this.key = zzarc.cA();
                } else if (cw == 16) {
                    this.value = zzarc.cA();
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            return super.zzx() + zzard.zzag(1, this.key) + zzard.zzag(2, this.value);
        }
    }

    public static final class zzf extends zzare<zzf> {
        public String version;
        public String[] zzwd;
        public String[] zzwe;
        public com.google.android.gms.internal.zzai.zza[] zzwf;
        public zze[] zzwg;
        public zzb[] zzwh;
        public zzb[] zzwi;
        public zzb[] zzwj;
        public zzg[] zzwk;
        public String zzwl;
        public String zzwm;
        public String zzwn;
        public zza zzwo;
        public float zzwp;
        public boolean zzwq;
        public String[] zzwr;
        public int zzws;

        public zzf() {
            zzai();
        }

        public static zzf zze(byte[] bArr) {
            return (zzf) zzark.zza(new zzf(), bArr);
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzf)) {
                return false;
            }
            zzf zzf = (zzf) obj;
            if (!zzari.equals((Object[]) this.zzwd, (Object[]) zzf.zzwd) || !zzari.equals((Object[]) this.zzwe, (Object[]) zzf.zzwe) || !zzari.equals((Object[]) this.zzwf, (Object[]) zzf.zzwf) || !zzari.equals((Object[]) this.zzwg, (Object[]) zzf.zzwg) || !zzari.equals((Object[]) this.zzwh, (Object[]) zzf.zzwh) || !zzari.equals((Object[]) this.zzwi, (Object[]) zzf.zzwi) || !zzari.equals((Object[]) this.zzwj, (Object[]) zzf.zzwj) || !zzari.equals((Object[]) this.zzwk, (Object[]) zzf.zzwk)) {
                return false;
            }
            if (this.zzwl == null) {
                if (zzf.zzwl != null) {
                    return false;
                }
            } else if (!this.zzwl.equals(zzf.zzwl)) {
                return false;
            }
            if (this.zzwm == null) {
                if (zzf.zzwm != null) {
                    return false;
                }
            } else if (!this.zzwm.equals(zzf.zzwm)) {
                return false;
            }
            if (this.zzwn == null) {
                if (zzf.zzwn != null) {
                    return false;
                }
            } else if (!this.zzwn.equals(zzf.zzwn)) {
                return false;
            }
            if (this.version == null) {
                if (zzf.version != null) {
                    return false;
                }
            } else if (!this.version.equals(zzf.version)) {
                return false;
            }
            if (this.zzwo == null) {
                if (zzf.zzwo != null) {
                    return false;
                }
            } else if (!this.zzwo.equals(zzf.zzwo)) {
                return false;
            }
            if (Float.floatToIntBits(this.zzwp) != Float.floatToIntBits(zzf.zzwp) || this.zzwq != zzf.zzwq || !zzari.equals((Object[]) this.zzwr, (Object[]) zzf.zzwr) || this.zzws != zzf.zzws) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zzf.bqv);
            }
            if (zzf.bqv != null) {
                if (zzf.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (((((((((((((((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzari.hashCode((Object[]) this.zzwd)) * 31) + zzari.hashCode((Object[]) this.zzwe)) * 31) + zzari.hashCode((Object[]) this.zzwf)) * 31) + zzari.hashCode((Object[]) this.zzwg)) * 31) + zzari.hashCode((Object[]) this.zzwh)) * 31) + zzari.hashCode((Object[]) this.zzwi)) * 31) + zzari.hashCode((Object[]) this.zzwj)) * 31) + zzari.hashCode((Object[]) this.zzwk)) * 31) + (this.zzwl == null ? 0 : this.zzwl.hashCode())) * 31) + (this.zzwm == null ? 0 : this.zzwm.hashCode())) * 31) + (this.zzwn == null ? 0 : this.zzwn.hashCode())) * 31) + (this.version == null ? 0 : this.version.hashCode())) * 31) + (this.zzwo == null ? 0 : this.zzwo.hashCode())) * 31) + Float.floatToIntBits(this.zzwp)) * 31) + (this.zzwq ? 1231 : 1237)) * 31) + zzari.hashCode((Object[]) this.zzwr)) * 31) + this.zzws) * 31;
            if (this.bqv != null && !this.bqv.isEmpty()) {
                i = this.bqv.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzard zzard) {
            if (this.zzwe != null && this.zzwe.length > 0) {
                for (String str : this.zzwe) {
                    if (str != null) {
                        zzard.zzr(1, str);
                    }
                }
            }
            if (this.zzwf != null && this.zzwf.length > 0) {
                for (com.google.android.gms.internal.zzai.zza zza : this.zzwf) {
                    if (zza != null) {
                        zzard.zza(2, (zzark) zza);
                    }
                }
            }
            if (this.zzwg != null && this.zzwg.length > 0) {
                for (zze zze : this.zzwg) {
                    if (zze != null) {
                        zzard.zza(3, (zzark) zze);
                    }
                }
            }
            if (this.zzwh != null && this.zzwh.length > 0) {
                for (zzb zzb : this.zzwh) {
                    if (zzb != null) {
                        zzard.zza(4, (zzark) zzb);
                    }
                }
            }
            if (this.zzwi != null && this.zzwi.length > 0) {
                for (zzb zzb2 : this.zzwi) {
                    if (zzb2 != null) {
                        zzard.zza(5, (zzark) zzb2);
                    }
                }
            }
            if (this.zzwj != null && this.zzwj.length > 0) {
                for (zzb zzb3 : this.zzwj) {
                    if (zzb3 != null) {
                        zzard.zza(6, (zzark) zzb3);
                    }
                }
            }
            if (this.zzwk != null && this.zzwk.length > 0) {
                for (zzg zzg : this.zzwk) {
                    if (zzg != null) {
                        zzard.zza(7, (zzark) zzg);
                    }
                }
            }
            if (!this.zzwl.equals("")) {
                zzard.zzr(9, this.zzwl);
            }
            if (!this.zzwm.equals("")) {
                zzard.zzr(10, this.zzwm);
            }
            if (!this.zzwn.equals("0")) {
                zzard.zzr(12, this.zzwn);
            }
            if (!this.version.equals("")) {
                zzard.zzr(13, this.version);
            }
            if (this.zzwo != null) {
                zzard.zza(14, (zzark) this.zzwo);
            }
            if (Float.floatToIntBits(this.zzwp) != Float.floatToIntBits(BitmapDescriptorFactory.HUE_RED)) {
                zzard.zzc(15, this.zzwp);
            }
            if (this.zzwr != null && this.zzwr.length > 0) {
                for (String str2 : this.zzwr) {
                    if (str2 != null) {
                        zzard.zzr(16, str2);
                    }
                }
            }
            if (this.zzws != 0) {
                zzard.zzae(17, this.zzws);
            }
            if (this.zzwq) {
                zzard.zzj(18, this.zzwq);
            }
            if (this.zzwd != null && this.zzwd.length > 0) {
                for (String str3 : this.zzwd) {
                    if (str3 != null) {
                        zzard.zzr(19, str3);
                    }
                }
            }
            super.zza(zzard);
        }

        public zzf zzai() {
            this.zzwd = zzarn.bqK;
            this.zzwe = zzarn.bqK;
            this.zzwf = com.google.android.gms.internal.zzai.zza.zzap();
            this.zzwg = zze.zzag();
            this.zzwh = zzb.zzab();
            this.zzwi = zzb.zzab();
            this.zzwj = zzb.zzab();
            this.zzwk = zzg.zzaj();
            this.zzwl = "";
            this.zzwm = "";
            this.zzwn = "0";
            this.version = "";
            this.zzwo = null;
            this.zzwp = BitmapDescriptorFactory.HUE_RED;
            this.zzwq = false;
            this.zzwr = zzarn.bqK;
            this.zzws = 0;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzp */
        public zzf zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                switch (cw) {
                    case 0:
                        return this;
                    case 10:
                        int zzc = zzarn.zzc(zzarc, 10);
                        int length = this.zzwe == null ? 0 : this.zzwe.length;
                        String[] strArr = new String[(zzc + length)];
                        if (length != 0) {
                            System.arraycopy(this.zzwe, 0, strArr, 0, length);
                        }
                        while (length < strArr.length - 1) {
                            strArr[length] = zzarc.readString();
                            zzarc.cw();
                            length++;
                        }
                        strArr[length] = zzarc.readString();
                        this.zzwe = strArr;
                        break;
                    case 18:
                        int zzc2 = zzarn.zzc(zzarc, 18);
                        int length2 = this.zzwf == null ? 0 : this.zzwf.length;
                        com.google.android.gms.internal.zzai.zza[] zzaArr = new com.google.android.gms.internal.zzai.zza[(zzc2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.zzwf, 0, zzaArr, 0, length2);
                        }
                        while (length2 < zzaArr.length - 1) {
                            zzaArr[length2] = new com.google.android.gms.internal.zzai.zza();
                            zzarc.zza(zzaArr[length2]);
                            zzarc.cw();
                            length2++;
                        }
                        zzaArr[length2] = new com.google.android.gms.internal.zzai.zza();
                        zzarc.zza(zzaArr[length2]);
                        this.zzwf = zzaArr;
                        break;
                    case 26:
                        int zzc3 = zzarn.zzc(zzarc, 26);
                        int length3 = this.zzwg == null ? 0 : this.zzwg.length;
                        zze[] zzeArr = new zze[(zzc3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.zzwg, 0, zzeArr, 0, length3);
                        }
                        while (length3 < zzeArr.length - 1) {
                            zzeArr[length3] = new zze();
                            zzarc.zza(zzeArr[length3]);
                            zzarc.cw();
                            length3++;
                        }
                        zzeArr[length3] = new zze();
                        zzarc.zza(zzeArr[length3]);
                        this.zzwg = zzeArr;
                        break;
                    case 34:
                        int zzc4 = zzarn.zzc(zzarc, 34);
                        int length4 = this.zzwh == null ? 0 : this.zzwh.length;
                        zzb[] zzbArr = new zzb[(zzc4 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.zzwh, 0, zzbArr, 0, length4);
                        }
                        while (length4 < zzbArr.length - 1) {
                            zzbArr[length4] = new zzb();
                            zzarc.zza(zzbArr[length4]);
                            zzarc.cw();
                            length4++;
                        }
                        zzbArr[length4] = new zzb();
                        zzarc.zza(zzbArr[length4]);
                        this.zzwh = zzbArr;
                        break;
                    case 42:
                        int zzc5 = zzarn.zzc(zzarc, 42);
                        int length5 = this.zzwi == null ? 0 : this.zzwi.length;
                        zzb[] zzbArr2 = new zzb[(zzc5 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.zzwi, 0, zzbArr2, 0, length5);
                        }
                        while (length5 < zzbArr2.length - 1) {
                            zzbArr2[length5] = new zzb();
                            zzarc.zza(zzbArr2[length5]);
                            zzarc.cw();
                            length5++;
                        }
                        zzbArr2[length5] = new zzb();
                        zzarc.zza(zzbArr2[length5]);
                        this.zzwi = zzbArr2;
                        break;
                    case 50:
                        int zzc6 = zzarn.zzc(zzarc, 50);
                        int length6 = this.zzwj == null ? 0 : this.zzwj.length;
                        zzb[] zzbArr3 = new zzb[(zzc6 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.zzwj, 0, zzbArr3, 0, length6);
                        }
                        while (length6 < zzbArr3.length - 1) {
                            zzbArr3[length6] = new zzb();
                            zzarc.zza(zzbArr3[length6]);
                            zzarc.cw();
                            length6++;
                        }
                        zzbArr3[length6] = new zzb();
                        zzarc.zza(zzbArr3[length6]);
                        this.zzwj = zzbArr3;
                        break;
                    case 58:
                        int zzc7 = zzarn.zzc(zzarc, 58);
                        int length7 = this.zzwk == null ? 0 : this.zzwk.length;
                        zzg[] zzgArr = new zzg[(zzc7 + length7)];
                        if (length7 != 0) {
                            System.arraycopy(this.zzwk, 0, zzgArr, 0, length7);
                        }
                        while (length7 < zzgArr.length - 1) {
                            zzgArr[length7] = new zzg();
                            zzarc.zza(zzgArr[length7]);
                            zzarc.cw();
                            length7++;
                        }
                        zzgArr[length7] = new zzg();
                        zzarc.zza(zzgArr[length7]);
                        this.zzwk = zzgArr;
                        break;
                    case 74:
                        this.zzwl = zzarc.readString();
                        break;
                    case 82:
                        this.zzwm = zzarc.readString();
                        break;
                    case 98:
                        this.zzwn = zzarc.readString();
                        break;
                    case 106:
                        this.version = zzarc.readString();
                        break;
                    case 114:
                        if (this.zzwo == null) {
                            this.zzwo = new zza();
                        }
                        zzarc.zza(this.zzwo);
                        break;
                    case EACTags.SECURE_MESSAGING_TEMPLATE /*125*/:
                        this.zzwp = zzarc.readFloat();
                        break;
                    case 130:
                        int zzc8 = zzarn.zzc(zzarc, 130);
                        int length8 = this.zzwr == null ? 0 : this.zzwr.length;
                        String[] strArr2 = new String[(zzc8 + length8)];
                        if (length8 != 0) {
                            System.arraycopy(this.zzwr, 0, strArr2, 0, length8);
                        }
                        while (length8 < strArr2.length - 1) {
                            strArr2[length8] = zzarc.readString();
                            zzarc.cw();
                            length8++;
                        }
                        strArr2[length8] = zzarc.readString();
                        this.zzwr = strArr2;
                        break;
                    case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA /*136*/:
                        this.zzws = zzarc.cA();
                        break;
                    case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /*144*/:
                        this.zzwq = zzarc.cC();
                        break;
                    case CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA /*154*/:
                        int zzc9 = zzarn.zzc(zzarc, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA);
                        int length9 = this.zzwd == null ? 0 : this.zzwd.length;
                        String[] strArr3 = new String[(zzc9 + length9)];
                        if (length9 != 0) {
                            System.arraycopy(this.zzwd, 0, strArr3, 0, length9);
                        }
                        while (length9 < strArr3.length - 1) {
                            strArr3[length9] = zzarc.readString();
                            zzarc.cw();
                            length9++;
                        }
                        strArr3[length9] = zzarc.readString();
                        this.zzwd = strArr3;
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

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzwe != null && this.zzwe.length > 0) {
                int i = 0;
                int i2 = 0;
                for (String str : this.zzwe) {
                    if (str != null) {
                        i2++;
                        i += zzard.zzuy(str);
                    }
                }
                zzx = zzx + i + (i2 * 1);
            }
            if (this.zzwf != null && this.zzwf.length > 0) {
                int i3 = zzx;
                for (com.google.android.gms.internal.zzai.zza zza : this.zzwf) {
                    if (zza != null) {
                        i3 += zzard.zzc(2, (zzark) zza);
                    }
                }
                zzx = i3;
            }
            if (this.zzwg != null && this.zzwg.length > 0) {
                int i4 = zzx;
                for (zze zze : this.zzwg) {
                    if (zze != null) {
                        i4 += zzard.zzc(3, (zzark) zze);
                    }
                }
                zzx = i4;
            }
            if (this.zzwh != null && this.zzwh.length > 0) {
                int i5 = zzx;
                for (zzb zzb : this.zzwh) {
                    if (zzb != null) {
                        i5 += zzard.zzc(4, (zzark) zzb);
                    }
                }
                zzx = i5;
            }
            if (this.zzwi != null && this.zzwi.length > 0) {
                int i6 = zzx;
                for (zzb zzb2 : this.zzwi) {
                    if (zzb2 != null) {
                        i6 += zzard.zzc(5, (zzark) zzb2);
                    }
                }
                zzx = i6;
            }
            if (this.zzwj != null && this.zzwj.length > 0) {
                int i7 = zzx;
                for (zzb zzb3 : this.zzwj) {
                    if (zzb3 != null) {
                        i7 += zzard.zzc(6, (zzark) zzb3);
                    }
                }
                zzx = i7;
            }
            if (this.zzwk != null && this.zzwk.length > 0) {
                int i8 = zzx;
                for (zzg zzg : this.zzwk) {
                    if (zzg != null) {
                        i8 += zzard.zzc(7, (zzark) zzg);
                    }
                }
                zzx = i8;
            }
            if (!this.zzwl.equals("")) {
                zzx += zzard.zzs(9, this.zzwl);
            }
            if (!this.zzwm.equals("")) {
                zzx += zzard.zzs(10, this.zzwm);
            }
            if (!this.zzwn.equals("0")) {
                zzx += zzard.zzs(12, this.zzwn);
            }
            if (!this.version.equals("")) {
                zzx += zzard.zzs(13, this.version);
            }
            if (this.zzwo != null) {
                zzx += zzard.zzc(14, (zzark) this.zzwo);
            }
            if (Float.floatToIntBits(this.zzwp) != Float.floatToIntBits(BitmapDescriptorFactory.HUE_RED)) {
                zzx += zzard.zzd(15, this.zzwp);
            }
            if (this.zzwr != null && this.zzwr.length > 0) {
                int i9 = 0;
                int i10 = 0;
                for (String str2 : this.zzwr) {
                    if (str2 != null) {
                        i10++;
                        i9 += zzard.zzuy(str2);
                    }
                }
                zzx = zzx + i9 + (i10 * 2);
            }
            if (this.zzws != 0) {
                zzx += zzard.zzag(17, this.zzws);
            }
            if (this.zzwq) {
                zzx += zzard.zzk(18, this.zzwq);
            }
            if (this.zzwd == null || this.zzwd.length <= 0) {
                return zzx;
            }
            int i11 = 0;
            int i12 = 0;
            for (String str3 : this.zzwd) {
                if (str3 != null) {
                    i12++;
                    i11 += zzard.zzuy(str3);
                }
            }
            return zzx + i11 + (i12 * 2);
        }
    }

    public static final class zzg extends zzare<zzg> {
        private static volatile zzg[] a;
        public int[] zzwu;
        public int[] zzwv;
        public int[] zzww;
        public int[] zzwx;
        public int[] zzwy;
        public int[] zzwz;
        public int[] zzxa;
        public int[] zzxb;
        public int[] zzxc;
        public int[] zzxd;

        public zzg() {
            zzak();
        }

        public static zzg[] zzaj() {
            if (a == null) {
                synchronized (zzari.bqD) {
                    if (a == null) {
                        a = new zzg[0];
                    }
                }
            }
            return a;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzg)) {
                return false;
            }
            zzg zzg = (zzg) obj;
            if (!zzari.equals(this.zzwu, zzg.zzwu) || !zzari.equals(this.zzwv, zzg.zzwv) || !zzari.equals(this.zzww, zzg.zzww) || !zzari.equals(this.zzwx, zzg.zzwx) || !zzari.equals(this.zzwy, zzg.zzwy) || !zzari.equals(this.zzwz, zzg.zzwz) || !zzari.equals(this.zzxa, zzg.zzxa) || !zzari.equals(this.zzxb, zzg.zzxb) || !zzari.equals(this.zzxc, zzg.zzxc) || !zzari.equals(this.zzxd, zzg.zzxd)) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zzg.bqv);
            }
            if (zzg.bqv != null) {
                if (zzg.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return ((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzari.hashCode(this.zzwu)) * 31) + zzari.hashCode(this.zzwv)) * 31) + zzari.hashCode(this.zzww)) * 31) + zzari.hashCode(this.zzwx)) * 31) + zzari.hashCode(this.zzwy)) * 31) + zzari.hashCode(this.zzwz)) * 31) + zzari.hashCode(this.zzxa)) * 31) + zzari.hashCode(this.zzxb)) * 31) + zzari.hashCode(this.zzxc)) * 31) + zzari.hashCode(this.zzxd)) * 31) + ((this.bqv == null || this.bqv.isEmpty()) ? 0 : this.bqv.hashCode());
        }

        public void zza(zzard zzard) {
            if (this.zzwu != null && this.zzwu.length > 0) {
                for (int zzae : this.zzwu) {
                    zzard.zzae(1, zzae);
                }
            }
            if (this.zzwv != null && this.zzwv.length > 0) {
                for (int zzae2 : this.zzwv) {
                    zzard.zzae(2, zzae2);
                }
            }
            if (this.zzww != null && this.zzww.length > 0) {
                for (int zzae3 : this.zzww) {
                    zzard.zzae(3, zzae3);
                }
            }
            if (this.zzwx != null && this.zzwx.length > 0) {
                for (int zzae4 : this.zzwx) {
                    zzard.zzae(4, zzae4);
                }
            }
            if (this.zzwy != null && this.zzwy.length > 0) {
                for (int zzae5 : this.zzwy) {
                    zzard.zzae(5, zzae5);
                }
            }
            if (this.zzwz != null && this.zzwz.length > 0) {
                for (int zzae6 : this.zzwz) {
                    zzard.zzae(6, zzae6);
                }
            }
            if (this.zzxa != null && this.zzxa.length > 0) {
                for (int zzae7 : this.zzxa) {
                    zzard.zzae(7, zzae7);
                }
            }
            if (this.zzxb != null && this.zzxb.length > 0) {
                for (int zzae8 : this.zzxb) {
                    zzard.zzae(8, zzae8);
                }
            }
            if (this.zzxc != null && this.zzxc.length > 0) {
                for (int zzae9 : this.zzxc) {
                    zzard.zzae(9, zzae9);
                }
            }
            if (this.zzxd != null && this.zzxd.length > 0) {
                for (int zzae10 : this.zzxd) {
                    zzard.zzae(10, zzae10);
                }
            }
            super.zza(zzard);
        }

        public zzg zzak() {
            this.zzwu = zzarn.bqF;
            this.zzwv = zzarn.bqF;
            this.zzww = zzarn.bqF;
            this.zzwx = zzarn.bqF;
            this.zzwy = zzarn.bqF;
            this.zzwz = zzarn.bqF;
            this.zzxa = zzarn.bqF;
            this.zzxb = zzarn.bqF;
            this.zzxc = zzarn.bqF;
            this.zzxd = zzarn.bqF;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzq */
        public zzg zzb(zzarc zzarc) {
            int i;
            while (true) {
                int cw = zzarc.cw();
                switch (cw) {
                    case 0:
                        return this;
                    case 8:
                        int zzc = zzarn.zzc(zzarc, 8);
                        int length = this.zzwu == null ? 0 : this.zzwu.length;
                        int[] iArr = new int[(zzc + length)];
                        if (length != 0) {
                            System.arraycopy(this.zzwu, 0, iArr, 0, length);
                        }
                        while (length < iArr.length - 1) {
                            iArr[length] = zzarc.cA();
                            zzarc.cw();
                            length++;
                        }
                        iArr[length] = zzarc.cA();
                        this.zzwu = iArr;
                        continue;
                    case 10:
                        i = zzarc.zzahc(zzarc.cF());
                        int position = zzarc.getPosition();
                        int i2 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i2++;
                        }
                        zzarc.zzahe(position);
                        int length2 = this.zzwu == null ? 0 : this.zzwu.length;
                        int[] iArr2 = new int[(i2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.zzwu, 0, iArr2, 0, length2);
                        }
                        while (length2 < iArr2.length) {
                            iArr2[length2] = zzarc.cA();
                            length2++;
                        }
                        this.zzwu = iArr2;
                        break;
                    case 16:
                        int zzc2 = zzarn.zzc(zzarc, 16);
                        int length3 = this.zzwv == null ? 0 : this.zzwv.length;
                        int[] iArr3 = new int[(zzc2 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.zzwv, 0, iArr3, 0, length3);
                        }
                        while (length3 < iArr3.length - 1) {
                            iArr3[length3] = zzarc.cA();
                            zzarc.cw();
                            length3++;
                        }
                        iArr3[length3] = zzarc.cA();
                        this.zzwv = iArr3;
                        continue;
                    case 18:
                        i = zzarc.zzahc(zzarc.cF());
                        int position2 = zzarc.getPosition();
                        int i3 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i3++;
                        }
                        zzarc.zzahe(position2);
                        int length4 = this.zzwv == null ? 0 : this.zzwv.length;
                        int[] iArr4 = new int[(i3 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.zzwv, 0, iArr4, 0, length4);
                        }
                        while (length4 < iArr4.length) {
                            iArr4[length4] = zzarc.cA();
                            length4++;
                        }
                        this.zzwv = iArr4;
                        break;
                    case 24:
                        int zzc3 = zzarn.zzc(zzarc, 24);
                        int length5 = this.zzww == null ? 0 : this.zzww.length;
                        int[] iArr5 = new int[(zzc3 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.zzww, 0, iArr5, 0, length5);
                        }
                        while (length5 < iArr5.length - 1) {
                            iArr5[length5] = zzarc.cA();
                            zzarc.cw();
                            length5++;
                        }
                        iArr5[length5] = zzarc.cA();
                        this.zzww = iArr5;
                        continue;
                    case 26:
                        i = zzarc.zzahc(zzarc.cF());
                        int position3 = zzarc.getPosition();
                        int i4 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i4++;
                        }
                        zzarc.zzahe(position3);
                        int length6 = this.zzww == null ? 0 : this.zzww.length;
                        int[] iArr6 = new int[(i4 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.zzww, 0, iArr6, 0, length6);
                        }
                        while (length6 < iArr6.length) {
                            iArr6[length6] = zzarc.cA();
                            length6++;
                        }
                        this.zzww = iArr6;
                        break;
                    case 32:
                        int zzc4 = zzarn.zzc(zzarc, 32);
                        int length7 = this.zzwx == null ? 0 : this.zzwx.length;
                        int[] iArr7 = new int[(zzc4 + length7)];
                        if (length7 != 0) {
                            System.arraycopy(this.zzwx, 0, iArr7, 0, length7);
                        }
                        while (length7 < iArr7.length - 1) {
                            iArr7[length7] = zzarc.cA();
                            zzarc.cw();
                            length7++;
                        }
                        iArr7[length7] = zzarc.cA();
                        this.zzwx = iArr7;
                        continue;
                    case 34:
                        i = zzarc.zzahc(zzarc.cF());
                        int position4 = zzarc.getPosition();
                        int i5 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i5++;
                        }
                        zzarc.zzahe(position4);
                        int length8 = this.zzwx == null ? 0 : this.zzwx.length;
                        int[] iArr8 = new int[(i5 + length8)];
                        if (length8 != 0) {
                            System.arraycopy(this.zzwx, 0, iArr8, 0, length8);
                        }
                        while (length8 < iArr8.length) {
                            iArr8[length8] = zzarc.cA();
                            length8++;
                        }
                        this.zzwx = iArr8;
                        break;
                    case 40:
                        int zzc5 = zzarn.zzc(zzarc, 40);
                        int length9 = this.zzwy == null ? 0 : this.zzwy.length;
                        int[] iArr9 = new int[(zzc5 + length9)];
                        if (length9 != 0) {
                            System.arraycopy(this.zzwy, 0, iArr9, 0, length9);
                        }
                        while (length9 < iArr9.length - 1) {
                            iArr9[length9] = zzarc.cA();
                            zzarc.cw();
                            length9++;
                        }
                        iArr9[length9] = zzarc.cA();
                        this.zzwy = iArr9;
                        continue;
                    case 42:
                        i = zzarc.zzahc(zzarc.cF());
                        int position5 = zzarc.getPosition();
                        int i6 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i6++;
                        }
                        zzarc.zzahe(position5);
                        int length10 = this.zzwy == null ? 0 : this.zzwy.length;
                        int[] iArr10 = new int[(i6 + length10)];
                        if (length10 != 0) {
                            System.arraycopy(this.zzwy, 0, iArr10, 0, length10);
                        }
                        while (length10 < iArr10.length) {
                            iArr10[length10] = zzarc.cA();
                            length10++;
                        }
                        this.zzwy = iArr10;
                        break;
                    case 48:
                        int zzc6 = zzarn.zzc(zzarc, 48);
                        int length11 = this.zzwz == null ? 0 : this.zzwz.length;
                        int[] iArr11 = new int[(zzc6 + length11)];
                        if (length11 != 0) {
                            System.arraycopy(this.zzwz, 0, iArr11, 0, length11);
                        }
                        while (length11 < iArr11.length - 1) {
                            iArr11[length11] = zzarc.cA();
                            zzarc.cw();
                            length11++;
                        }
                        iArr11[length11] = zzarc.cA();
                        this.zzwz = iArr11;
                        continue;
                    case 50:
                        i = zzarc.zzahc(zzarc.cF());
                        int position6 = zzarc.getPosition();
                        int i7 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i7++;
                        }
                        zzarc.zzahe(position6);
                        int length12 = this.zzwz == null ? 0 : this.zzwz.length;
                        int[] iArr12 = new int[(i7 + length12)];
                        if (length12 != 0) {
                            System.arraycopy(this.zzwz, 0, iArr12, 0, length12);
                        }
                        while (length12 < iArr12.length) {
                            iArr12[length12] = zzarc.cA();
                            length12++;
                        }
                        this.zzwz = iArr12;
                        break;
                    case 56:
                        int zzc7 = zzarn.zzc(zzarc, 56);
                        int length13 = this.zzxa == null ? 0 : this.zzxa.length;
                        int[] iArr13 = new int[(zzc7 + length13)];
                        if (length13 != 0) {
                            System.arraycopy(this.zzxa, 0, iArr13, 0, length13);
                        }
                        while (length13 < iArr13.length - 1) {
                            iArr13[length13] = zzarc.cA();
                            zzarc.cw();
                            length13++;
                        }
                        iArr13[length13] = zzarc.cA();
                        this.zzxa = iArr13;
                        continue;
                    case 58:
                        i = zzarc.zzahc(zzarc.cF());
                        int position7 = zzarc.getPosition();
                        int i8 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i8++;
                        }
                        zzarc.zzahe(position7);
                        int length14 = this.zzxa == null ? 0 : this.zzxa.length;
                        int[] iArr14 = new int[(i8 + length14)];
                        if (length14 != 0) {
                            System.arraycopy(this.zzxa, 0, iArr14, 0, length14);
                        }
                        while (length14 < iArr14.length) {
                            iArr14[length14] = zzarc.cA();
                            length14++;
                        }
                        this.zzxa = iArr14;
                        break;
                    case 64:
                        int zzc8 = zzarn.zzc(zzarc, 64);
                        int length15 = this.zzxb == null ? 0 : this.zzxb.length;
                        int[] iArr15 = new int[(zzc8 + length15)];
                        if (length15 != 0) {
                            System.arraycopy(this.zzxb, 0, iArr15, 0, length15);
                        }
                        while (length15 < iArr15.length - 1) {
                            iArr15[length15] = zzarc.cA();
                            zzarc.cw();
                            length15++;
                        }
                        iArr15[length15] = zzarc.cA();
                        this.zzxb = iArr15;
                        continue;
                    case 66:
                        i = zzarc.zzahc(zzarc.cF());
                        int position8 = zzarc.getPosition();
                        int i9 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i9++;
                        }
                        zzarc.zzahe(position8);
                        int length16 = this.zzxb == null ? 0 : this.zzxb.length;
                        int[] iArr16 = new int[(i9 + length16)];
                        if (length16 != 0) {
                            System.arraycopy(this.zzxb, 0, iArr16, 0, length16);
                        }
                        while (length16 < iArr16.length) {
                            iArr16[length16] = zzarc.cA();
                            length16++;
                        }
                        this.zzxb = iArr16;
                        break;
                    case 72:
                        int zzc9 = zzarn.zzc(zzarc, 72);
                        int length17 = this.zzxc == null ? 0 : this.zzxc.length;
                        int[] iArr17 = new int[(zzc9 + length17)];
                        if (length17 != 0) {
                            System.arraycopy(this.zzxc, 0, iArr17, 0, length17);
                        }
                        while (length17 < iArr17.length - 1) {
                            iArr17[length17] = zzarc.cA();
                            zzarc.cw();
                            length17++;
                        }
                        iArr17[length17] = zzarc.cA();
                        this.zzxc = iArr17;
                        continue;
                    case 74:
                        i = zzarc.zzahc(zzarc.cF());
                        int position9 = zzarc.getPosition();
                        int i10 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i10++;
                        }
                        zzarc.zzahe(position9);
                        int length18 = this.zzxc == null ? 0 : this.zzxc.length;
                        int[] iArr18 = new int[(i10 + length18)];
                        if (length18 != 0) {
                            System.arraycopy(this.zzxc, 0, iArr18, 0, length18);
                        }
                        while (length18 < iArr18.length) {
                            iArr18[length18] = zzarc.cA();
                            length18++;
                        }
                        this.zzxc = iArr18;
                        break;
                    case 80:
                        int zzc10 = zzarn.zzc(zzarc, 80);
                        int length19 = this.zzxd == null ? 0 : this.zzxd.length;
                        int[] iArr19 = new int[(zzc10 + length19)];
                        if (length19 != 0) {
                            System.arraycopy(this.zzxd, 0, iArr19, 0, length19);
                        }
                        while (length19 < iArr19.length - 1) {
                            iArr19[length19] = zzarc.cA();
                            zzarc.cw();
                            length19++;
                        }
                        iArr19[length19] = zzarc.cA();
                        this.zzxd = iArr19;
                        continue;
                    case 82:
                        i = zzarc.zzahc(zzarc.cF());
                        int position10 = zzarc.getPosition();
                        int i11 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i11++;
                        }
                        zzarc.zzahe(position10);
                        int length20 = this.zzxd == null ? 0 : this.zzxd.length;
                        int[] iArr20 = new int[(i11 + length20)];
                        if (length20 != 0) {
                            System.arraycopy(this.zzxd, 0, iArr20, 0, length20);
                        }
                        while (length20 < iArr20.length) {
                            iArr20[length20] = zzarc.cA();
                            length20++;
                        }
                        this.zzxd = iArr20;
                        break;
                    default:
                        if (!super.zza(zzarc, cw)) {
                            return this;
                        }
                        continue;
                }
                zzarc.zzahd(i);
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzwu != null && this.zzwu.length > 0) {
                int i = 0;
                for (int zzahi : this.zzwu) {
                    i += zzard.zzahi(zzahi);
                }
                zzx = zzx + i + (this.zzwu.length * 1);
            }
            if (this.zzwv != null && this.zzwv.length > 0) {
                int i2 = 0;
                for (int zzahi2 : this.zzwv) {
                    i2 += zzard.zzahi(zzahi2);
                }
                zzx = zzx + i2 + (this.zzwv.length * 1);
            }
            if (this.zzww != null && this.zzww.length > 0) {
                int i3 = 0;
                for (int zzahi3 : this.zzww) {
                    i3 += zzard.zzahi(zzahi3);
                }
                zzx = zzx + i3 + (this.zzww.length * 1);
            }
            if (this.zzwx != null && this.zzwx.length > 0) {
                int i4 = 0;
                for (int zzahi4 : this.zzwx) {
                    i4 += zzard.zzahi(zzahi4);
                }
                zzx = zzx + i4 + (this.zzwx.length * 1);
            }
            if (this.zzwy != null && this.zzwy.length > 0) {
                int i5 = 0;
                for (int zzahi5 : this.zzwy) {
                    i5 += zzard.zzahi(zzahi5);
                }
                zzx = zzx + i5 + (this.zzwy.length * 1);
            }
            if (this.zzwz != null && this.zzwz.length > 0) {
                int i6 = 0;
                for (int zzahi6 : this.zzwz) {
                    i6 += zzard.zzahi(zzahi6);
                }
                zzx = zzx + i6 + (this.zzwz.length * 1);
            }
            if (this.zzxa != null && this.zzxa.length > 0) {
                int i7 = 0;
                for (int zzahi7 : this.zzxa) {
                    i7 += zzard.zzahi(zzahi7);
                }
                zzx = zzx + i7 + (this.zzxa.length * 1);
            }
            if (this.zzxb != null && this.zzxb.length > 0) {
                int i8 = 0;
                for (int zzahi8 : this.zzxb) {
                    i8 += zzard.zzahi(zzahi8);
                }
                zzx = zzx + i8 + (this.zzxb.length * 1);
            }
            if (this.zzxc != null && this.zzxc.length > 0) {
                int i9 = 0;
                for (int zzahi9 : this.zzxc) {
                    i9 += zzard.zzahi(zzahi9);
                }
                zzx = zzx + i9 + (this.zzxc.length * 1);
            }
            if (this.zzxd == null || this.zzxd.length <= 0) {
                return zzx;
            }
            int i10 = 0;
            for (int zzahi10 : this.zzxd) {
                i10 += zzard.zzahi(zzahi10);
            }
            return zzx + i10 + (this.zzxd.length * 1);
        }
    }

    public static final class zzh extends zzare<zzh> {
        private static final zzh[] a = new zzh[0];
        public static final zzarf<com.google.android.gms.internal.zzai.zza, zzh> zzxe = zzarf.zza(11, zzh.class, 810);
        public int[] zzxg;
        public int[] zzxh;
        public int[] zzxi;
        public int zzxj;
        public int[] zzxk;
        public int zzxl;
        public int zzxm;

        public zzh() {
            zzal();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzh)) {
                return false;
            }
            zzh zzh = (zzh) obj;
            if (!zzari.equals(this.zzxg, zzh.zzxg) || !zzari.equals(this.zzxh, zzh.zzxh) || !zzari.equals(this.zzxi, zzh.zzxi) || this.zzxj != zzh.zzxj || !zzari.equals(this.zzxk, zzh.zzxk) || this.zzxl != zzh.zzxl || this.zzxm != zzh.zzxm) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zzh.bqv);
            }
            if (zzh.bqv != null) {
                if (zzh.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return ((((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzari.hashCode(this.zzxg)) * 31) + zzari.hashCode(this.zzxh)) * 31) + zzari.hashCode(this.zzxi)) * 31) + this.zzxj) * 31) + zzari.hashCode(this.zzxk)) * 31) + this.zzxl) * 31) + this.zzxm) * 31) + ((this.bqv == null || this.bqv.isEmpty()) ? 0 : this.bqv.hashCode());
        }

        public void zza(zzard zzard) {
            if (this.zzxg != null && this.zzxg.length > 0) {
                for (int zzae : this.zzxg) {
                    zzard.zzae(1, zzae);
                }
            }
            if (this.zzxh != null && this.zzxh.length > 0) {
                for (int zzae2 : this.zzxh) {
                    zzard.zzae(2, zzae2);
                }
            }
            if (this.zzxi != null && this.zzxi.length > 0) {
                for (int zzae3 : this.zzxi) {
                    zzard.zzae(3, zzae3);
                }
            }
            if (this.zzxj != 0) {
                zzard.zzae(4, this.zzxj);
            }
            if (this.zzxk != null && this.zzxk.length > 0) {
                for (int zzae4 : this.zzxk) {
                    zzard.zzae(5, zzae4);
                }
            }
            if (this.zzxl != 0) {
                zzard.zzae(6, this.zzxl);
            }
            if (this.zzxm != 0) {
                zzard.zzae(7, this.zzxm);
            }
            super.zza(zzard);
        }

        public zzh zzal() {
            this.zzxg = zzarn.bqF;
            this.zzxh = zzarn.bqF;
            this.zzxi = zzarn.bqF;
            this.zzxj = 0;
            this.zzxk = zzarn.bqF;
            this.zzxl = 0;
            this.zzxm = 0;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzr */
        public zzh zzb(zzarc zzarc) {
            int i;
            while (true) {
                int cw = zzarc.cw();
                switch (cw) {
                    case 0:
                        return this;
                    case 8:
                        int zzc = zzarn.zzc(zzarc, 8);
                        int length = this.zzxg == null ? 0 : this.zzxg.length;
                        int[] iArr = new int[(zzc + length)];
                        if (length != 0) {
                            System.arraycopy(this.zzxg, 0, iArr, 0, length);
                        }
                        while (length < iArr.length - 1) {
                            iArr[length] = zzarc.cA();
                            zzarc.cw();
                            length++;
                        }
                        iArr[length] = zzarc.cA();
                        this.zzxg = iArr;
                        continue;
                    case 10:
                        i = zzarc.zzahc(zzarc.cF());
                        int position = zzarc.getPosition();
                        int i2 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i2++;
                        }
                        zzarc.zzahe(position);
                        int length2 = this.zzxg == null ? 0 : this.zzxg.length;
                        int[] iArr2 = new int[(i2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.zzxg, 0, iArr2, 0, length2);
                        }
                        while (length2 < iArr2.length) {
                            iArr2[length2] = zzarc.cA();
                            length2++;
                        }
                        this.zzxg = iArr2;
                        break;
                    case 16:
                        int zzc2 = zzarn.zzc(zzarc, 16);
                        int length3 = this.zzxh == null ? 0 : this.zzxh.length;
                        int[] iArr3 = new int[(zzc2 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.zzxh, 0, iArr3, 0, length3);
                        }
                        while (length3 < iArr3.length - 1) {
                            iArr3[length3] = zzarc.cA();
                            zzarc.cw();
                            length3++;
                        }
                        iArr3[length3] = zzarc.cA();
                        this.zzxh = iArr3;
                        continue;
                    case 18:
                        i = zzarc.zzahc(zzarc.cF());
                        int position2 = zzarc.getPosition();
                        int i3 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i3++;
                        }
                        zzarc.zzahe(position2);
                        int length4 = this.zzxh == null ? 0 : this.zzxh.length;
                        int[] iArr4 = new int[(i3 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.zzxh, 0, iArr4, 0, length4);
                        }
                        while (length4 < iArr4.length) {
                            iArr4[length4] = zzarc.cA();
                            length4++;
                        }
                        this.zzxh = iArr4;
                        break;
                    case 24:
                        int zzc3 = zzarn.zzc(zzarc, 24);
                        int length5 = this.zzxi == null ? 0 : this.zzxi.length;
                        int[] iArr5 = new int[(zzc3 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.zzxi, 0, iArr5, 0, length5);
                        }
                        while (length5 < iArr5.length - 1) {
                            iArr5[length5] = zzarc.cA();
                            zzarc.cw();
                            length5++;
                        }
                        iArr5[length5] = zzarc.cA();
                        this.zzxi = iArr5;
                        continue;
                    case 26:
                        i = zzarc.zzahc(zzarc.cF());
                        int position3 = zzarc.getPosition();
                        int i4 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i4++;
                        }
                        zzarc.zzahe(position3);
                        int length6 = this.zzxi == null ? 0 : this.zzxi.length;
                        int[] iArr6 = new int[(i4 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.zzxi, 0, iArr6, 0, length6);
                        }
                        while (length6 < iArr6.length) {
                            iArr6[length6] = zzarc.cA();
                            length6++;
                        }
                        this.zzxi = iArr6;
                        break;
                    case 32:
                        this.zzxj = zzarc.cA();
                        continue;
                    case 40:
                        int zzc4 = zzarn.zzc(zzarc, 40);
                        int length7 = this.zzxk == null ? 0 : this.zzxk.length;
                        int[] iArr7 = new int[(zzc4 + length7)];
                        if (length7 != 0) {
                            System.arraycopy(this.zzxk, 0, iArr7, 0, length7);
                        }
                        while (length7 < iArr7.length - 1) {
                            iArr7[length7] = zzarc.cA();
                            zzarc.cw();
                            length7++;
                        }
                        iArr7[length7] = zzarc.cA();
                        this.zzxk = iArr7;
                        continue;
                    case 42:
                        i = zzarc.zzahc(zzarc.cF());
                        int position4 = zzarc.getPosition();
                        int i5 = 0;
                        while (zzarc.cK() > 0) {
                            zzarc.cA();
                            i5++;
                        }
                        zzarc.zzahe(position4);
                        int length8 = this.zzxk == null ? 0 : this.zzxk.length;
                        int[] iArr8 = new int[(i5 + length8)];
                        if (length8 != 0) {
                            System.arraycopy(this.zzxk, 0, iArr8, 0, length8);
                        }
                        while (length8 < iArr8.length) {
                            iArr8[length8] = zzarc.cA();
                            length8++;
                        }
                        this.zzxk = iArr8;
                        break;
                    case 48:
                        this.zzxl = zzarc.cA();
                        continue;
                    case 56:
                        this.zzxm = zzarc.cA();
                        continue;
                    default:
                        if (!super.zza(zzarc, cw)) {
                            return this;
                        }
                        continue;
                }
                zzarc.zzahd(i);
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzxg != null && this.zzxg.length > 0) {
                int i = 0;
                for (int zzahi : this.zzxg) {
                    i += zzard.zzahi(zzahi);
                }
                zzx = zzx + i + (this.zzxg.length * 1);
            }
            if (this.zzxh != null && this.zzxh.length > 0) {
                int i2 = 0;
                for (int zzahi2 : this.zzxh) {
                    i2 += zzard.zzahi(zzahi2);
                }
                zzx = zzx + i2 + (this.zzxh.length * 1);
            }
            if (this.zzxi != null && this.zzxi.length > 0) {
                int i3 = 0;
                for (int zzahi3 : this.zzxi) {
                    i3 += zzard.zzahi(zzahi3);
                }
                zzx = zzx + i3 + (this.zzxi.length * 1);
            }
            if (this.zzxj != 0) {
                zzx += zzard.zzag(4, this.zzxj);
            }
            if (this.zzxk != null && this.zzxk.length > 0) {
                int i4 = 0;
                for (int zzahi4 : this.zzxk) {
                    i4 += zzard.zzahi(zzahi4);
                }
                zzx = zzx + i4 + (this.zzxk.length * 1);
            }
            if (this.zzxl != 0) {
                zzx += zzard.zzag(6, this.zzxl);
            }
            return this.zzxm != 0 ? zzx + zzard.zzag(7, this.zzxm) : zzx;
        }
    }

    public static final class zzi extends zzare<zzi> {
        private static volatile zzi[] a;
        public String name;
        public com.google.android.gms.internal.zzai.zza zzxo;
        public zzd zzxp;

        public zzi() {
            zzan();
        }

        public static zzi[] zzam() {
            if (a == null) {
                synchronized (zzari.bqD) {
                    if (a == null) {
                        a = new zzi[0];
                    }
                }
            }
            return a;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzi)) {
                return false;
            }
            zzi zzi = (zzi) obj;
            if (this.name == null) {
                if (zzi.name != null) {
                    return false;
                }
            } else if (!this.name.equals(zzi.name)) {
                return false;
            }
            if (this.zzxo == null) {
                if (zzi.zzxo != null) {
                    return false;
                }
            } else if (!this.zzxo.equals(zzi.zzxo)) {
                return false;
            }
            if (this.zzxp == null) {
                if (zzi.zzxp != null) {
                    return false;
                }
            } else if (!this.zzxp.equals(zzi.zzxp)) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zzi.bqv);
            }
            if (zzi.bqv != null) {
                if (zzi.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.zzxo == null ? 0 : this.zzxo.hashCode())) * 31) + (this.zzxp == null ? 0 : this.zzxp.hashCode())) * 31;
            if (this.bqv != null && !this.bqv.isEmpty()) {
                i = this.bqv.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzard zzard) {
            if (!this.name.equals("")) {
                zzard.zzr(1, this.name);
            }
            if (this.zzxo != null) {
                zzard.zza(2, (zzark) this.zzxo);
            }
            if (this.zzxp != null) {
                zzard.zza(3, (zzark) this.zzxp);
            }
            super.zza(zzard);
        }

        public zzi zzan() {
            this.name = "";
            this.zzxo = null;
            this.zzxp = null;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzs */
        public zzi zzb(zzarc zzarc) {
            zzark zzark;
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw != 10) {
                    if (cw == 18) {
                        if (this.zzxo == null) {
                            this.zzxo = new com.google.android.gms.internal.zzai.zza();
                        }
                        zzark = this.zzxo;
                    } else if (cw == 26) {
                        if (this.zzxp == null) {
                            this.zzxp = new zzd();
                        }
                        zzark = this.zzxp;
                    } else if (!super.zza(zzarc, cw)) {
                        return this;
                    }
                    zzarc.zza(zzark);
                } else {
                    this.name = zzarc.readString();
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (!this.name.equals("")) {
                zzx += zzard.zzs(1, this.name);
            }
            if (this.zzxo != null) {
                zzx += zzard.zzc(2, (zzark) this.zzxo);
            }
            return this.zzxp != null ? zzx + zzard.zzc(3, (zzark) this.zzxp) : zzx;
        }
    }

    public static final class zzj extends zzare<zzj> {
        public zzi[] zzxq;
        public zzf zzxr;
        public String zzxs;

        public zzj() {
            zzao();
        }

        public static zzj zzf(byte[] bArr) {
            return (zzj) zzark.zza(new zzj(), bArr);
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzj)) {
                return false;
            }
            zzj zzj = (zzj) obj;
            if (!zzari.equals((Object[]) this.zzxq, (Object[]) zzj.zzxq)) {
                return false;
            }
            if (this.zzxr == null) {
                if (zzj.zzxr != null) {
                    return false;
                }
            } else if (!this.zzxr.equals(zzj.zzxr)) {
                return false;
            }
            if (this.zzxs == null) {
                if (zzj.zzxs != null) {
                    return false;
                }
            } else if (!this.zzxs.equals(zzj.zzxs)) {
                return false;
            }
            if (this.bqv != null && !this.bqv.isEmpty()) {
                return this.bqv.equals(zzj.bqv);
            }
            if (zzj.bqv != null) {
                if (zzj.bqv.isEmpty()) {
                    return true;
                }
                z = false;
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (((((((getClass().getName().hashCode() + 527) * 31) + zzari.hashCode((Object[]) this.zzxq)) * 31) + (this.zzxr == null ? 0 : this.zzxr.hashCode())) * 31) + (this.zzxs == null ? 0 : this.zzxs.hashCode())) * 31;
            if (this.bqv != null && !this.bqv.isEmpty()) {
                i = this.bqv.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzard zzard) {
            if (this.zzxq != null && this.zzxq.length > 0) {
                for (zzi zzi : this.zzxq) {
                    if (zzi != null) {
                        zzard.zza(1, (zzark) zzi);
                    }
                }
            }
            if (this.zzxr != null) {
                zzard.zza(2, (zzark) this.zzxr);
            }
            if (!this.zzxs.equals("")) {
                zzard.zzr(3, this.zzxs);
            }
            super.zza(zzard);
        }

        public zzj zzao() {
            this.zzxq = zzi.zzam();
            this.zzxr = null;
            this.zzxs = "";
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzt */
        public zzj zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 10) {
                    int zzc = zzarn.zzc(zzarc, 10);
                    int length = this.zzxq == null ? 0 : this.zzxq.length;
                    zzi[] zziArr = new zzi[(zzc + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzxq, 0, zziArr, 0, length);
                    }
                    while (length < zziArr.length - 1) {
                        zziArr[length] = new zzi();
                        zzarc.zza(zziArr[length]);
                        zzarc.cw();
                        length++;
                    }
                    zziArr[length] = new zzi();
                    zzarc.zza(zziArr[length]);
                    this.zzxq = zziArr;
                } else if (cw == 18) {
                    if (this.zzxr == null) {
                        this.zzxr = new zzf();
                    }
                    zzarc.zza(this.zzxr);
                } else if (cw == 26) {
                    this.zzxs = zzarc.readString();
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzxq != null && this.zzxq.length > 0) {
                for (zzi zzi : this.zzxq) {
                    if (zzi != null) {
                        zzx += zzard.zzc(1, (zzark) zzi);
                    }
                }
            }
            if (this.zzxr != null) {
                zzx += zzard.zzc(2, (zzark) this.zzxr);
            }
            return !this.zzxs.equals("") ? zzx + zzard.zzs(3, this.zzxs) : zzx;
        }
    }
}
