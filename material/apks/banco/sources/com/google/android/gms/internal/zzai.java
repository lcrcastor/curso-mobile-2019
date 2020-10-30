package com.google.android.gms.internal;

public interface zzai {

    public static final class zza extends zzare<zza> {
        private static volatile zza[] a;
        public String string;
        public int type;
        public zza[] zzxu;
        public zza[] zzxv;
        public zza[] zzxw;
        public String zzxx;
        public String zzxy;
        public long zzxz;
        public boolean zzya;
        public zza[] zzyb;
        public int[] zzyc;
        public boolean zzyd;

        public zza() {
            zzaq();
        }

        public static zza[] zzap() {
            if (a == null) {
                synchronized (zzari.bqD) {
                    if (a == null) {
                        a = new zza[0];
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
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            if (this.type != zza.type) {
                return false;
            }
            if (this.string == null) {
                if (zza.string != null) {
                    return false;
                }
            } else if (!this.string.equals(zza.string)) {
                return false;
            }
            if (!zzari.equals((Object[]) this.zzxu, (Object[]) zza.zzxu) || !zzari.equals((Object[]) this.zzxv, (Object[]) zza.zzxv) || !zzari.equals((Object[]) this.zzxw, (Object[]) zza.zzxw)) {
                return false;
            }
            if (this.zzxx == null) {
                if (zza.zzxx != null) {
                    return false;
                }
            } else if (!this.zzxx.equals(zza.zzxx)) {
                return false;
            }
            if (this.zzxy == null) {
                if (zza.zzxy != null) {
                    return false;
                }
            } else if (!this.zzxy.equals(zza.zzxy)) {
                return false;
            }
            if (this.zzxz != zza.zzxz || this.zzya != zza.zzya || !zzari.equals((Object[]) this.zzyb, (Object[]) zza.zzyb) || !zzari.equals(this.zzyc, zza.zzyc) || this.zzyd != zza.zzyd) {
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
            int i2 = 1237;
            int hashCode = (((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + this.type) * 31) + (this.string == null ? 0 : this.string.hashCode())) * 31) + zzari.hashCode((Object[]) this.zzxu)) * 31) + zzari.hashCode((Object[]) this.zzxv)) * 31) + zzari.hashCode((Object[]) this.zzxw)) * 31) + (this.zzxx == null ? 0 : this.zzxx.hashCode())) * 31) + (this.zzxy == null ? 0 : this.zzxy.hashCode())) * 31) + ((int) (this.zzxz ^ (this.zzxz >>> 32)))) * 31) + (this.zzya ? 1231 : 1237)) * 31) + zzari.hashCode((Object[]) this.zzyb)) * 31) + zzari.hashCode(this.zzyc)) * 31;
            if (this.zzyd) {
                i2 = 1231;
            }
            int i3 = (hashCode + i2) * 31;
            if (this.bqv != null && !this.bqv.isEmpty()) {
                i = this.bqv.hashCode();
            }
            return i3 + i;
        }

        public void zza(zzard zzard) {
            zzard.zzae(1, this.type);
            if (!this.string.equals("")) {
                zzard.zzr(2, this.string);
            }
            if (this.zzxu != null && this.zzxu.length > 0) {
                for (zza zza : this.zzxu) {
                    if (zza != null) {
                        zzard.zza(3, (zzark) zza);
                    }
                }
            }
            if (this.zzxv != null && this.zzxv.length > 0) {
                for (zza zza2 : this.zzxv) {
                    if (zza2 != null) {
                        zzard.zza(4, (zzark) zza2);
                    }
                }
            }
            if (this.zzxw != null && this.zzxw.length > 0) {
                for (zza zza3 : this.zzxw) {
                    if (zza3 != null) {
                        zzard.zza(5, (zzark) zza3);
                    }
                }
            }
            if (!this.zzxx.equals("")) {
                zzard.zzr(6, this.zzxx);
            }
            if (!this.zzxy.equals("")) {
                zzard.zzr(7, this.zzxy);
            }
            if (this.zzxz != 0) {
                zzard.zzb(8, this.zzxz);
            }
            if (this.zzyd) {
                zzard.zzj(9, this.zzyd);
            }
            if (this.zzyc != null && this.zzyc.length > 0) {
                for (int zzae : this.zzyc) {
                    zzard.zzae(10, zzae);
                }
            }
            if (this.zzyb != null && this.zzyb.length > 0) {
                for (zza zza4 : this.zzyb) {
                    if (zza4 != null) {
                        zzard.zza(11, (zzark) zza4);
                    }
                }
            }
            if (this.zzya) {
                zzard.zzj(12, this.zzya);
            }
            super.zza(zzard);
        }

        public zza zzaq() {
            this.type = 1;
            this.string = "";
            this.zzxu = zzap();
            this.zzxv = zzap();
            this.zzxw = zzap();
            this.zzxx = "";
            this.zzxy = "";
            this.zzxz = 0;
            this.zzya = false;
            this.zzyb = zzap();
            this.zzyc = zzarn.bqF;
            this.zzyd = false;
            this.bqv = null;
            this.bqE = -1;
            return this;
        }

        /* renamed from: zzu */
        public zza zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                switch (cw) {
                    case 0:
                        return this;
                    case 8:
                        int cA = zzarc.cA();
                        switch (cA) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                                this.type = cA;
                                break;
                        }
                    case 18:
                        this.string = zzarc.readString();
                        break;
                    case 26:
                        int zzc = zzarn.zzc(zzarc, 26);
                        int length = this.zzxu == null ? 0 : this.zzxu.length;
                        zza[] zzaArr = new zza[(zzc + length)];
                        if (length != 0) {
                            System.arraycopy(this.zzxu, 0, zzaArr, 0, length);
                        }
                        while (length < zzaArr.length - 1) {
                            zzaArr[length] = new zza();
                            zzarc.zza(zzaArr[length]);
                            zzarc.cw();
                            length++;
                        }
                        zzaArr[length] = new zza();
                        zzarc.zza(zzaArr[length]);
                        this.zzxu = zzaArr;
                        break;
                    case 34:
                        int zzc2 = zzarn.zzc(zzarc, 34);
                        int length2 = this.zzxv == null ? 0 : this.zzxv.length;
                        zza[] zzaArr2 = new zza[(zzc2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.zzxv, 0, zzaArr2, 0, length2);
                        }
                        while (length2 < zzaArr2.length - 1) {
                            zzaArr2[length2] = new zza();
                            zzarc.zza(zzaArr2[length2]);
                            zzarc.cw();
                            length2++;
                        }
                        zzaArr2[length2] = new zza();
                        zzarc.zza(zzaArr2[length2]);
                        this.zzxv = zzaArr2;
                        break;
                    case 42:
                        int zzc3 = zzarn.zzc(zzarc, 42);
                        int length3 = this.zzxw == null ? 0 : this.zzxw.length;
                        zza[] zzaArr3 = new zza[(zzc3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.zzxw, 0, zzaArr3, 0, length3);
                        }
                        while (length3 < zzaArr3.length - 1) {
                            zzaArr3[length3] = new zza();
                            zzarc.zza(zzaArr3[length3]);
                            zzarc.cw();
                            length3++;
                        }
                        zzaArr3[length3] = new zza();
                        zzarc.zza(zzaArr3[length3]);
                        this.zzxw = zzaArr3;
                        break;
                    case 50:
                        this.zzxx = zzarc.readString();
                        break;
                    case 58:
                        this.zzxy = zzarc.readString();
                        break;
                    case 64:
                        this.zzxz = zzarc.cz();
                        break;
                    case 72:
                        this.zzyd = zzarc.cC();
                        break;
                    case 80:
                        int zzc4 = zzarn.zzc(zzarc, 80);
                        int[] iArr = new int[zzc4];
                        int i = 0;
                        for (int i2 = 0; i2 < zzc4; i2++) {
                            if (i2 != 0) {
                                zzarc.cw();
                            }
                            int cA2 = zzarc.cA();
                            switch (cA2) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                    int i3 = i + 1;
                                    iArr[i] = cA2;
                                    i = i3;
                                    break;
                            }
                        }
                        if (i != 0) {
                            int length4 = this.zzyc == null ? 0 : this.zzyc.length;
                            if (length4 != 0 || i != zzc4) {
                                int[] iArr2 = new int[(length4 + i)];
                                if (length4 != 0) {
                                    System.arraycopy(this.zzyc, 0, iArr2, 0, length4);
                                }
                                System.arraycopy(iArr, 0, iArr2, length4, i);
                                this.zzyc = iArr2;
                                break;
                            } else {
                                this.zzyc = iArr;
                                break;
                            }
                        } else {
                            break;
                        }
                    case 82:
                        int zzahc = zzarc.zzahc(zzarc.cF());
                        int position = zzarc.getPosition();
                        int i4 = 0;
                        while (zzarc.cK() > 0) {
                            switch (zzarc.cA()) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                    i4++;
                                    break;
                            }
                        }
                        if (i4 != 0) {
                            zzarc.zzahe(position);
                            int length5 = this.zzyc == null ? 0 : this.zzyc.length;
                            int[] iArr3 = new int[(i4 + length5)];
                            if (length5 != 0) {
                                System.arraycopy(this.zzyc, 0, iArr3, 0, length5);
                            }
                            while (zzarc.cK() > 0) {
                                int cA3 = zzarc.cA();
                                switch (cA3) {
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 5:
                                    case 6:
                                    case 7:
                                    case 8:
                                    case 9:
                                    case 10:
                                    case 11:
                                    case 12:
                                    case 13:
                                    case 14:
                                    case 15:
                                    case 16:
                                    case 17:
                                        int i5 = length5 + 1;
                                        iArr3[length5] = cA3;
                                        length5 = i5;
                                        break;
                                }
                            }
                            this.zzyc = iArr3;
                        }
                        zzarc.zzahd(zzahc);
                        break;
                    case 90:
                        int zzc5 = zzarn.zzc(zzarc, 90);
                        int length6 = this.zzyb == null ? 0 : this.zzyb.length;
                        zza[] zzaArr4 = new zza[(zzc5 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.zzyb, 0, zzaArr4, 0, length6);
                        }
                        while (length6 < zzaArr4.length - 1) {
                            zzaArr4[length6] = new zza();
                            zzarc.zza(zzaArr4[length6]);
                            zzarc.cw();
                            length6++;
                        }
                        zzaArr4[length6] = new zza();
                        zzarc.zza(zzaArr4[length6]);
                        this.zzyb = zzaArr4;
                        break;
                    case 96:
                        this.zzya = zzarc.cC();
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
            int zzx = super.zzx() + zzard.zzag(1, this.type);
            if (!this.string.equals("")) {
                zzx += zzard.zzs(2, this.string);
            }
            if (this.zzxu != null && this.zzxu.length > 0) {
                int i = zzx;
                for (zza zza : this.zzxu) {
                    if (zza != null) {
                        i += zzard.zzc(3, (zzark) zza);
                    }
                }
                zzx = i;
            }
            if (this.zzxv != null && this.zzxv.length > 0) {
                int i2 = zzx;
                for (zza zza2 : this.zzxv) {
                    if (zza2 != null) {
                        i2 += zzard.zzc(4, (zzark) zza2);
                    }
                }
                zzx = i2;
            }
            if (this.zzxw != null && this.zzxw.length > 0) {
                int i3 = zzx;
                for (zza zza3 : this.zzxw) {
                    if (zza3 != null) {
                        i3 += zzard.zzc(5, (zzark) zza3);
                    }
                }
                zzx = i3;
            }
            if (!this.zzxx.equals("")) {
                zzx += zzard.zzs(6, this.zzxx);
            }
            if (!this.zzxy.equals("")) {
                zzx += zzard.zzs(7, this.zzxy);
            }
            if (this.zzxz != 0) {
                zzx += zzard.zzf(8, this.zzxz);
            }
            if (this.zzyd) {
                zzx += zzard.zzk(9, this.zzyd);
            }
            if (this.zzyc != null && this.zzyc.length > 0) {
                int i4 = 0;
                for (int zzahi : this.zzyc) {
                    i4 += zzard.zzahi(zzahi);
                }
                zzx = zzx + i4 + (this.zzyc.length * 1);
            }
            if (this.zzyb != null && this.zzyb.length > 0) {
                for (zza zza4 : this.zzyb) {
                    if (zza4 != null) {
                        zzx += zzard.zzc(11, (zzark) zza4);
                    }
                }
            }
            return this.zzya ? zzx + zzard.zzk(12, this.zzya) : zzx;
        }
    }
}
