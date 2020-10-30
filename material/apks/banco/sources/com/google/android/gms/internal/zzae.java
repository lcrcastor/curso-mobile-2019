package com.google.android.gms.internal;

import cz.msebera.android.httpclient.HttpStatus;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.tls.CipherSuite;

public interface zzae {

    public static final class zza extends zzare<zza> {
        public String zzcs;
        public String zzct;
        public Long zzcu;
        public Long zzcv;
        public Long zzcw;
        public Long zzcx;
        public Long zzcy;
        public Long zzcz;
        public Long zzda;
        public Long zzdb;
        public Long zzdc;
        public Long zzdd;
        public String zzde;
        public Long zzdf;
        public Long zzdg;
        public Long zzdh;
        public Long zzdi;
        public Long zzdj;
        public Long zzdk;
        public Long zzdl;
        public Long zzdm;
        public Long zzdn;
        public String zzdo;
        public String zzdp;
        public Long zzdq;
        public Long zzdr;
        public Long zzds;
        public String zzdt;
        public Long zzdu;
        public Long zzdv;
        public Long zzdw;
        public zzb zzdx;
        public Long zzdy;
        public Long zzdz;
        public Long zzea;
        public Long zzeb;
        public Long zzec;
        public Long zzed;
        public String zzee;
        public String zzef;
        public Integer zzeg;
        public Integer zzeh;
        public Long zzei;
        public Long zzej;
        public Long zzek;
        public Long zzel;
        public Long zzem;
        public Integer zzen;
        public C0022zza zzeo;
        public C0022zza[] zzep;
        public zzb zzeq;
        public Long zzer;
        public String zzes;
        public Integer zzet;
        public Boolean zzeu;
        public String zzev;
        public Long zzew;
        public zze zzex;

        /* renamed from: com.google.android.gms.internal.zzae$zza$zza reason: collision with other inner class name */
        public static final class C0022zza extends zzare<C0022zza> {
            private static volatile C0022zza[] a;
            public Long zzdf;
            public Long zzdg;
            public Long zzez;
            public Long zzfa;
            public Long zzfb;
            public Long zzfc;
            public Integer zzfd;
            public Long zzfe;
            public Long zzff;
            public Long zzfg;
            public Integer zzfh;
            public Long zzfi;

            public C0022zza() {
                this.zzdf = null;
                this.zzdg = null;
                this.zzez = null;
                this.zzfa = null;
                this.zzfb = null;
                this.zzfc = null;
                this.zzfd = null;
                this.zzfe = null;
                this.zzff = null;
                this.zzfg = null;
                this.zzfh = null;
                this.zzfi = null;
                this.bqE = -1;
            }

            public static C0022zza[] zzy() {
                if (a == null) {
                    synchronized (zzari.bqD) {
                        if (a == null) {
                            a = new C0022zza[0];
                        }
                    }
                }
                return a;
            }

            public void zza(zzard zzard) {
                if (this.zzdf != null) {
                    zzard.zzb(1, this.zzdf.longValue());
                }
                if (this.zzdg != null) {
                    zzard.zzb(2, this.zzdg.longValue());
                }
                if (this.zzez != null) {
                    zzard.zzb(3, this.zzez.longValue());
                }
                if (this.zzfa != null) {
                    zzard.zzb(4, this.zzfa.longValue());
                }
                if (this.zzfb != null) {
                    zzard.zzb(5, this.zzfb.longValue());
                }
                if (this.zzfc != null) {
                    zzard.zzb(6, this.zzfc.longValue());
                }
                if (this.zzfd != null) {
                    zzard.zzae(7, this.zzfd.intValue());
                }
                if (this.zzfe != null) {
                    zzard.zzb(8, this.zzfe.longValue());
                }
                if (this.zzff != null) {
                    zzard.zzb(9, this.zzff.longValue());
                }
                if (this.zzfg != null) {
                    zzard.zzb(10, this.zzfg.longValue());
                }
                if (this.zzfh != null) {
                    zzard.zzae(11, this.zzfh.intValue());
                }
                if (this.zzfi != null) {
                    zzard.zzb(12, this.zzfi.longValue());
                }
                super.zza(zzard);
            }

            /* renamed from: zzd */
            public C0022zza zzb(zzarc zzarc) {
                while (true) {
                    int cw = zzarc.cw();
                    switch (cw) {
                        case 0:
                            return this;
                        case 8:
                            this.zzdf = Long.valueOf(zzarc.cz());
                            break;
                        case 16:
                            this.zzdg = Long.valueOf(zzarc.cz());
                            break;
                        case 24:
                            this.zzez = Long.valueOf(zzarc.cz());
                            break;
                        case 32:
                            this.zzfa = Long.valueOf(zzarc.cz());
                            break;
                        case 40:
                            this.zzfb = Long.valueOf(zzarc.cz());
                            break;
                        case 48:
                            this.zzfc = Long.valueOf(zzarc.cz());
                            break;
                        case 56:
                            int cA = zzarc.cA();
                            if (cA != 1000) {
                                switch (cA) {
                                    case 0:
                                    case 1:
                                    case 2:
                                        break;
                                }
                            }
                            this.zzfd = Integer.valueOf(cA);
                            break;
                        case 64:
                            this.zzfe = Long.valueOf(zzarc.cz());
                            break;
                        case 72:
                            this.zzff = Long.valueOf(zzarc.cz());
                            break;
                        case 80:
                            this.zzfg = Long.valueOf(zzarc.cz());
                            break;
                        case 88:
                            int cA2 = zzarc.cA();
                            if (cA2 != 1000) {
                                switch (cA2) {
                                    case 0:
                                    case 1:
                                    case 2:
                                        break;
                                }
                            }
                            this.zzfh = Integer.valueOf(cA2);
                            break;
                        case 96:
                            this.zzfi = Long.valueOf(zzarc.cz());
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
                if (this.zzdf != null) {
                    zzx += zzard.zzf(1, this.zzdf.longValue());
                }
                if (this.zzdg != null) {
                    zzx += zzard.zzf(2, this.zzdg.longValue());
                }
                if (this.zzez != null) {
                    zzx += zzard.zzf(3, this.zzez.longValue());
                }
                if (this.zzfa != null) {
                    zzx += zzard.zzf(4, this.zzfa.longValue());
                }
                if (this.zzfb != null) {
                    zzx += zzard.zzf(5, this.zzfb.longValue());
                }
                if (this.zzfc != null) {
                    zzx += zzard.zzf(6, this.zzfc.longValue());
                }
                if (this.zzfd != null) {
                    zzx += zzard.zzag(7, this.zzfd.intValue());
                }
                if (this.zzfe != null) {
                    zzx += zzard.zzf(8, this.zzfe.longValue());
                }
                if (this.zzff != null) {
                    zzx += zzard.zzf(9, this.zzff.longValue());
                }
                if (this.zzfg != null) {
                    zzx += zzard.zzf(10, this.zzfg.longValue());
                }
                if (this.zzfh != null) {
                    zzx += zzard.zzag(11, this.zzfh.intValue());
                }
                return this.zzfi != null ? zzx + zzard.zzf(12, this.zzfi.longValue()) : zzx;
            }
        }

        public static final class zzb extends zzare<zzb> {
            public Long zzel;
            public Long zzem;
            public Long zzfj;

            public zzb() {
                this.zzel = null;
                this.zzem = null;
                this.zzfj = null;
                this.bqE = -1;
            }

            public void zza(zzard zzard) {
                if (this.zzel != null) {
                    zzard.zzb(1, this.zzel.longValue());
                }
                if (this.zzem != null) {
                    zzard.zzb(2, this.zzem.longValue());
                }
                if (this.zzfj != null) {
                    zzard.zzb(3, this.zzfj.longValue());
                }
                super.zza(zzard);
            }

            /* renamed from: zze */
            public zzb zzb(zzarc zzarc) {
                while (true) {
                    int cw = zzarc.cw();
                    if (cw == 0) {
                        return this;
                    }
                    if (cw == 8) {
                        this.zzel = Long.valueOf(zzarc.cz());
                    } else if (cw == 16) {
                        this.zzem = Long.valueOf(zzarc.cz());
                    } else if (cw == 24) {
                        this.zzfj = Long.valueOf(zzarc.cz());
                    } else if (!super.zza(zzarc, cw)) {
                        return this;
                    }
                }
            }

            /* access modifiers changed from: protected */
            public int zzx() {
                int zzx = super.zzx();
                if (this.zzel != null) {
                    zzx += zzard.zzf(1, this.zzel.longValue());
                }
                if (this.zzem != null) {
                    zzx += zzard.zzf(2, this.zzem.longValue());
                }
                return this.zzfj != null ? zzx + zzard.zzf(3, this.zzfj.longValue()) : zzx;
            }
        }

        public zza() {
            this.zzct = null;
            this.zzcs = null;
            this.zzcu = null;
            this.zzcv = null;
            this.zzcw = null;
            this.zzcx = null;
            this.zzcy = null;
            this.zzcz = null;
            this.zzda = null;
            this.zzdb = null;
            this.zzdc = null;
            this.zzdd = null;
            this.zzde = null;
            this.zzdf = null;
            this.zzdg = null;
            this.zzdh = null;
            this.zzdi = null;
            this.zzdj = null;
            this.zzdk = null;
            this.zzdl = null;
            this.zzdm = null;
            this.zzdn = null;
            this.zzdo = null;
            this.zzdp = null;
            this.zzdq = null;
            this.zzdr = null;
            this.zzds = null;
            this.zzdt = null;
            this.zzdu = null;
            this.zzdv = null;
            this.zzdw = null;
            this.zzdy = null;
            this.zzdz = null;
            this.zzea = null;
            this.zzeb = null;
            this.zzec = null;
            this.zzed = null;
            this.zzee = null;
            this.zzef = null;
            this.zzeg = null;
            this.zzeh = null;
            this.zzei = null;
            this.zzej = null;
            this.zzek = null;
            this.zzel = null;
            this.zzem = null;
            this.zzen = null;
            this.zzep = C0022zza.zzy();
            this.zzer = null;
            this.zzes = null;
            this.zzet = null;
            this.zzeu = null;
            this.zzev = null;
            this.zzew = null;
            this.bqE = -1;
        }

        public static zza zzc(byte[] bArr) {
            return (zza) zzark.zza(new zza(), bArr);
        }

        public void zza(zzard zzard) {
            if (this.zzct != null) {
                zzard.zzr(1, this.zzct);
            }
            if (this.zzcs != null) {
                zzard.zzr(2, this.zzcs);
            }
            if (this.zzcu != null) {
                zzard.zzb(3, this.zzcu.longValue());
            }
            if (this.zzcv != null) {
                zzard.zzb(4, this.zzcv.longValue());
            }
            if (this.zzcw != null) {
                zzard.zzb(5, this.zzcw.longValue());
            }
            if (this.zzcx != null) {
                zzard.zzb(6, this.zzcx.longValue());
            }
            if (this.zzcy != null) {
                zzard.zzb(7, this.zzcy.longValue());
            }
            if (this.zzcz != null) {
                zzard.zzb(8, this.zzcz.longValue());
            }
            if (this.zzda != null) {
                zzard.zzb(9, this.zzda.longValue());
            }
            if (this.zzdb != null) {
                zzard.zzb(10, this.zzdb.longValue());
            }
            if (this.zzdc != null) {
                zzard.zzb(11, this.zzdc.longValue());
            }
            if (this.zzdd != null) {
                zzard.zzb(12, this.zzdd.longValue());
            }
            if (this.zzde != null) {
                zzard.zzr(13, this.zzde);
            }
            if (this.zzdf != null) {
                zzard.zzb(14, this.zzdf.longValue());
            }
            if (this.zzdg != null) {
                zzard.zzb(15, this.zzdg.longValue());
            }
            if (this.zzdh != null) {
                zzard.zzb(16, this.zzdh.longValue());
            }
            if (this.zzdi != null) {
                zzard.zzb(17, this.zzdi.longValue());
            }
            if (this.zzdj != null) {
                zzard.zzb(18, this.zzdj.longValue());
            }
            if (this.zzdk != null) {
                zzard.zzb(19, this.zzdk.longValue());
            }
            if (this.zzdl != null) {
                zzard.zzb(20, this.zzdl.longValue());
            }
            if (this.zzer != null) {
                zzard.zzb(21, this.zzer.longValue());
            }
            if (this.zzdm != null) {
                zzard.zzb(22, this.zzdm.longValue());
            }
            if (this.zzdn != null) {
                zzard.zzb(23, this.zzdn.longValue());
            }
            if (this.zzes != null) {
                zzard.zzr(24, this.zzes);
            }
            if (this.zzew != null) {
                zzard.zzb(25, this.zzew.longValue());
            }
            if (this.zzet != null) {
                zzard.zzae(26, this.zzet.intValue());
            }
            if (this.zzdo != null) {
                zzard.zzr(27, this.zzdo);
            }
            if (this.zzeu != null) {
                zzard.zzj(28, this.zzeu.booleanValue());
            }
            if (this.zzdp != null) {
                zzard.zzr(29, this.zzdp);
            }
            if (this.zzev != null) {
                zzard.zzr(30, this.zzev);
            }
            if (this.zzdq != null) {
                zzard.zzb(31, this.zzdq.longValue());
            }
            if (this.zzdr != null) {
                zzard.zzb(32, this.zzdr.longValue());
            }
            if (this.zzds != null) {
                zzard.zzb(33, this.zzds.longValue());
            }
            if (this.zzdt != null) {
                zzard.zzr(34, this.zzdt);
            }
            if (this.zzdu != null) {
                zzard.zzb(35, this.zzdu.longValue());
            }
            if (this.zzdv != null) {
                zzard.zzb(36, this.zzdv.longValue());
            }
            if (this.zzdw != null) {
                zzard.zzb(37, this.zzdw.longValue());
            }
            if (this.zzdx != null) {
                zzard.zza(38, (zzark) this.zzdx);
            }
            if (this.zzdy != null) {
                zzard.zzb(39, this.zzdy.longValue());
            }
            if (this.zzdz != null) {
                zzard.zzb(40, this.zzdz.longValue());
            }
            if (this.zzea != null) {
                zzard.zzb(41, this.zzea.longValue());
            }
            if (this.zzeb != null) {
                zzard.zzb(42, this.zzeb.longValue());
            }
            if (this.zzep != null && this.zzep.length > 0) {
                for (C0022zza zza : this.zzep) {
                    if (zza != null) {
                        zzard.zza(43, (zzark) zza);
                    }
                }
            }
            if (this.zzec != null) {
                zzard.zzb(44, this.zzec.longValue());
            }
            if (this.zzed != null) {
                zzard.zzb(45, this.zzed.longValue());
            }
            if (this.zzee != null) {
                zzard.zzr(46, this.zzee);
            }
            if (this.zzef != null) {
                zzard.zzr(47, this.zzef);
            }
            if (this.zzeg != null) {
                zzard.zzae(48, this.zzeg.intValue());
            }
            if (this.zzeh != null) {
                zzard.zzae(49, this.zzeh.intValue());
            }
            if (this.zzeo != null) {
                zzard.zza(50, (zzark) this.zzeo);
            }
            if (this.zzei != null) {
                zzard.zzb(51, this.zzei.longValue());
            }
            if (this.zzej != null) {
                zzard.zzb(52, this.zzej.longValue());
            }
            if (this.zzek != null) {
                zzard.zzb(53, this.zzek.longValue());
            }
            if (this.zzel != null) {
                zzard.zzb(54, this.zzel.longValue());
            }
            if (this.zzem != null) {
                zzard.zzb(55, this.zzem.longValue());
            }
            if (this.zzen != null) {
                zzard.zzae(56, this.zzen.intValue());
            }
            if (this.zzeq != null) {
                zzard.zza(57, (zzark) this.zzeq);
            }
            if (this.zzex != null) {
                zzard.zza((int) HttpStatus.SC_CREATED, (zzark) this.zzex);
            }
            super.zza(zzard);
        }

        /* renamed from: zzc */
        public zza zzb(zzarc zzarc) {
            zzark zzark;
            while (true) {
                int cw = zzarc.cw();
                switch (cw) {
                    case 0:
                        return this;
                    case 10:
                        this.zzct = zzarc.readString();
                        continue;
                    case 18:
                        this.zzcs = zzarc.readString();
                        continue;
                    case 24:
                        this.zzcu = Long.valueOf(zzarc.cz());
                        continue;
                    case 32:
                        this.zzcv = Long.valueOf(zzarc.cz());
                        continue;
                    case 40:
                        this.zzcw = Long.valueOf(zzarc.cz());
                        continue;
                    case 48:
                        this.zzcx = Long.valueOf(zzarc.cz());
                        continue;
                    case 56:
                        this.zzcy = Long.valueOf(zzarc.cz());
                        continue;
                    case 64:
                        this.zzcz = Long.valueOf(zzarc.cz());
                        continue;
                    case 72:
                        this.zzda = Long.valueOf(zzarc.cz());
                        continue;
                    case 80:
                        this.zzdb = Long.valueOf(zzarc.cz());
                        continue;
                    case 88:
                        this.zzdc = Long.valueOf(zzarc.cz());
                        continue;
                    case 96:
                        this.zzdd = Long.valueOf(zzarc.cz());
                        continue;
                    case 106:
                        this.zzde = zzarc.readString();
                        continue;
                    case 112:
                        this.zzdf = Long.valueOf(zzarc.cz());
                        continue;
                    case EACTags.COMPATIBLE_TAG_ALLOCATION_AUTHORITY /*120*/:
                        this.zzdg = Long.valueOf(zzarc.cz());
                        continue;
                    case 128:
                        this.zzdh = Long.valueOf(zzarc.cz());
                        continue;
                    case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA /*136*/:
                        this.zzdi = Long.valueOf(zzarc.cz());
                        continue;
                    case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /*144*/:
                        this.zzdj = Long.valueOf(zzarc.cz());
                        continue;
                    case CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA /*152*/:
                        this.zzdk = Long.valueOf(zzarc.cz());
                        continue;
                    case CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256 /*160*/:
                        this.zzdl = Long.valueOf(zzarc.cz());
                        continue;
                    case 168:
                        this.zzer = Long.valueOf(zzarc.cz());
                        continue;
                    case CipherSuite.TLS_PSK_WITH_NULL_SHA256 /*176*/:
                        this.zzdm = Long.valueOf(zzarc.cz());
                        continue;
                    case CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256 /*184*/:
                        this.zzdn = Long.valueOf(zzarc.cz());
                        continue;
                    case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256 /*194*/:
                        this.zzes = zzarc.readString();
                        continue;
                    case 200:
                        this.zzew = Long.valueOf(zzarc.cz());
                        continue;
                    case 208:
                        int cA = zzarc.cA();
                        switch (cA) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                this.zzet = Integer.valueOf(cA);
                                break;
                            default:
                                continue;
                        }
                    case 218:
                        this.zzdo = zzarc.readString();
                        continue;
                    case 224:
                        this.zzeu = Boolean.valueOf(zzarc.cC());
                        continue;
                    case 234:
                        this.zzdp = zzarc.readString();
                        continue;
                    case 242:
                        this.zzev = zzarc.readString();
                        continue;
                    case 248:
                        this.zzdq = Long.valueOf(zzarc.cz());
                        continue;
                    case 256:
                        this.zzdr = Long.valueOf(zzarc.cz());
                        continue;
                    case 264:
                        this.zzds = Long.valueOf(zzarc.cz());
                        continue;
                    case 274:
                        this.zzdt = zzarc.readString();
                        continue;
                    case 280:
                        this.zzdu = Long.valueOf(zzarc.cz());
                        continue;
                    case 288:
                        this.zzdv = Long.valueOf(zzarc.cz());
                        continue;
                    case 296:
                        this.zzdw = Long.valueOf(zzarc.cz());
                        continue;
                    case 306:
                        if (this.zzdx == null) {
                            this.zzdx = new zzb();
                        }
                        zzark = this.zzdx;
                        break;
                    case 312:
                        this.zzdy = Long.valueOf(zzarc.cz());
                        continue;
                    case 320:
                        this.zzdz = Long.valueOf(zzarc.cz());
                        continue;
                    case 328:
                        this.zzea = Long.valueOf(zzarc.cz());
                        continue;
                    case 336:
                        this.zzeb = Long.valueOf(zzarc.cz());
                        continue;
                    case 346:
                        int zzc = zzarn.zzc(zzarc, 346);
                        int length = this.zzep == null ? 0 : this.zzep.length;
                        C0022zza[] zzaArr = new C0022zza[(zzc + length)];
                        if (length != 0) {
                            System.arraycopy(this.zzep, 0, zzaArr, 0, length);
                        }
                        while (length < zzaArr.length - 1) {
                            zzaArr[length] = new C0022zza();
                            zzarc.zza(zzaArr[length]);
                            zzarc.cw();
                            length++;
                        }
                        zzaArr[length] = new C0022zza();
                        zzarc.zza(zzaArr[length]);
                        this.zzep = zzaArr;
                        continue;
                    case 352:
                        this.zzec = Long.valueOf(zzarc.cz());
                        continue;
                    case 360:
                        this.zzed = Long.valueOf(zzarc.cz());
                        continue;
                    case 370:
                        this.zzee = zzarc.readString();
                        continue;
                    case 378:
                        this.zzef = zzarc.readString();
                        continue;
                    case 384:
                        int cA2 = zzarc.cA();
                        if (cA2 != 1000) {
                            switch (cA2) {
                                case 0:
                                case 1:
                                case 2:
                                    break;
                                default:
                                    continue;
                            }
                        }
                        this.zzeg = Integer.valueOf(cA2);
                        break;
                    case 392:
                        int cA3 = zzarc.cA();
                        if (cA3 != 1000) {
                            switch (cA3) {
                                case 0:
                                case 1:
                                case 2:
                                    break;
                                default:
                                    continue;
                            }
                        }
                        this.zzeh = Integer.valueOf(cA3);
                        break;
                    case HttpStatus.SC_PAYMENT_REQUIRED /*402*/:
                        if (this.zzeo == null) {
                            this.zzeo = new C0022zza();
                        }
                        zzark = this.zzeo;
                        break;
                    case HttpStatus.SC_REQUEST_TIMEOUT /*408*/:
                        this.zzei = Long.valueOf(zzarc.cz());
                        continue;
                    case HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE /*416*/:
                        this.zzej = Long.valueOf(zzarc.cz());
                        continue;
                    case HttpStatus.SC_FAILED_DEPENDENCY /*424*/:
                        this.zzek = Long.valueOf(zzarc.cz());
                        continue;
                    case 432:
                        this.zzel = Long.valueOf(zzarc.cz());
                        continue;
                    case 440:
                        this.zzem = Long.valueOf(zzarc.cz());
                        continue;
                    case 448:
                        int cA4 = zzarc.cA();
                        if (cA4 != 1000) {
                            switch (cA4) {
                                case 0:
                                case 1:
                                case 2:
                                    break;
                                default:
                                    continue;
                            }
                        }
                        this.zzen = Integer.valueOf(cA4);
                        break;
                    case 458:
                        if (this.zzeq == null) {
                            this.zzeq = new zzb();
                        }
                        zzark = this.zzeq;
                        break;
                    case 1610:
                        if (this.zzex == null) {
                            this.zzex = new zze();
                        }
                        zzark = this.zzex;
                        break;
                    default:
                        if (!super.zza(zzarc, cw)) {
                            return this;
                        }
                        continue;
                }
                zzarc.zza(zzark);
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzct != null) {
                zzx += zzard.zzs(1, this.zzct);
            }
            if (this.zzcs != null) {
                zzx += zzard.zzs(2, this.zzcs);
            }
            if (this.zzcu != null) {
                zzx += zzard.zzf(3, this.zzcu.longValue());
            }
            if (this.zzcv != null) {
                zzx += zzard.zzf(4, this.zzcv.longValue());
            }
            if (this.zzcw != null) {
                zzx += zzard.zzf(5, this.zzcw.longValue());
            }
            if (this.zzcx != null) {
                zzx += zzard.zzf(6, this.zzcx.longValue());
            }
            if (this.zzcy != null) {
                zzx += zzard.zzf(7, this.zzcy.longValue());
            }
            if (this.zzcz != null) {
                zzx += zzard.zzf(8, this.zzcz.longValue());
            }
            if (this.zzda != null) {
                zzx += zzard.zzf(9, this.zzda.longValue());
            }
            if (this.zzdb != null) {
                zzx += zzard.zzf(10, this.zzdb.longValue());
            }
            if (this.zzdc != null) {
                zzx += zzard.zzf(11, this.zzdc.longValue());
            }
            if (this.zzdd != null) {
                zzx += zzard.zzf(12, this.zzdd.longValue());
            }
            if (this.zzde != null) {
                zzx += zzard.zzs(13, this.zzde);
            }
            if (this.zzdf != null) {
                zzx += zzard.zzf(14, this.zzdf.longValue());
            }
            if (this.zzdg != null) {
                zzx += zzard.zzf(15, this.zzdg.longValue());
            }
            if (this.zzdh != null) {
                zzx += zzard.zzf(16, this.zzdh.longValue());
            }
            if (this.zzdi != null) {
                zzx += zzard.zzf(17, this.zzdi.longValue());
            }
            if (this.zzdj != null) {
                zzx += zzard.zzf(18, this.zzdj.longValue());
            }
            if (this.zzdk != null) {
                zzx += zzard.zzf(19, this.zzdk.longValue());
            }
            if (this.zzdl != null) {
                zzx += zzard.zzf(20, this.zzdl.longValue());
            }
            if (this.zzer != null) {
                zzx += zzard.zzf(21, this.zzer.longValue());
            }
            if (this.zzdm != null) {
                zzx += zzard.zzf(22, this.zzdm.longValue());
            }
            if (this.zzdn != null) {
                zzx += zzard.zzf(23, this.zzdn.longValue());
            }
            if (this.zzes != null) {
                zzx += zzard.zzs(24, this.zzes);
            }
            if (this.zzew != null) {
                zzx += zzard.zzf(25, this.zzew.longValue());
            }
            if (this.zzet != null) {
                zzx += zzard.zzag(26, this.zzet.intValue());
            }
            if (this.zzdo != null) {
                zzx += zzard.zzs(27, this.zzdo);
            }
            if (this.zzeu != null) {
                zzx += zzard.zzk(28, this.zzeu.booleanValue());
            }
            if (this.zzdp != null) {
                zzx += zzard.zzs(29, this.zzdp);
            }
            if (this.zzev != null) {
                zzx += zzard.zzs(30, this.zzev);
            }
            if (this.zzdq != null) {
                zzx += zzard.zzf(31, this.zzdq.longValue());
            }
            if (this.zzdr != null) {
                zzx += zzard.zzf(32, this.zzdr.longValue());
            }
            if (this.zzds != null) {
                zzx += zzard.zzf(33, this.zzds.longValue());
            }
            if (this.zzdt != null) {
                zzx += zzard.zzs(34, this.zzdt);
            }
            if (this.zzdu != null) {
                zzx += zzard.zzf(35, this.zzdu.longValue());
            }
            if (this.zzdv != null) {
                zzx += zzard.zzf(36, this.zzdv.longValue());
            }
            if (this.zzdw != null) {
                zzx += zzard.zzf(37, this.zzdw.longValue());
            }
            if (this.zzdx != null) {
                zzx += zzard.zzc(38, (zzark) this.zzdx);
            }
            if (this.zzdy != null) {
                zzx += zzard.zzf(39, this.zzdy.longValue());
            }
            if (this.zzdz != null) {
                zzx += zzard.zzf(40, this.zzdz.longValue());
            }
            if (this.zzea != null) {
                zzx += zzard.zzf(41, this.zzea.longValue());
            }
            if (this.zzeb != null) {
                zzx += zzard.zzf(42, this.zzeb.longValue());
            }
            if (this.zzep != null && this.zzep.length > 0) {
                for (C0022zza zza : this.zzep) {
                    if (zza != null) {
                        zzx += zzard.zzc(43, (zzark) zza);
                    }
                }
            }
            if (this.zzec != null) {
                zzx += zzard.zzf(44, this.zzec.longValue());
            }
            if (this.zzed != null) {
                zzx += zzard.zzf(45, this.zzed.longValue());
            }
            if (this.zzee != null) {
                zzx += zzard.zzs(46, this.zzee);
            }
            if (this.zzef != null) {
                zzx += zzard.zzs(47, this.zzef);
            }
            if (this.zzeg != null) {
                zzx += zzard.zzag(48, this.zzeg.intValue());
            }
            if (this.zzeh != null) {
                zzx += zzard.zzag(49, this.zzeh.intValue());
            }
            if (this.zzeo != null) {
                zzx += zzard.zzc(50, (zzark) this.zzeo);
            }
            if (this.zzei != null) {
                zzx += zzard.zzf(51, this.zzei.longValue());
            }
            if (this.zzej != null) {
                zzx += zzard.zzf(52, this.zzej.longValue());
            }
            if (this.zzek != null) {
                zzx += zzard.zzf(53, this.zzek.longValue());
            }
            if (this.zzel != null) {
                zzx += zzard.zzf(54, this.zzel.longValue());
            }
            if (this.zzem != null) {
                zzx += zzard.zzf(55, this.zzem.longValue());
            }
            if (this.zzen != null) {
                zzx += zzard.zzag(56, this.zzen.intValue());
            }
            if (this.zzeq != null) {
                zzx += zzard.zzc(57, (zzark) this.zzeq);
            }
            return this.zzex != null ? zzx + zzard.zzc((int) HttpStatus.SC_CREATED, (zzark) this.zzex) : zzx;
        }
    }

    public static final class zzb extends zzare<zzb> {
        public Long zzfk;
        public Integer zzfl;
        public Boolean zzfm;
        public int[] zzfn;
        public Long zzfo;

        public zzb() {
            this.zzfk = null;
            this.zzfl = null;
            this.zzfm = null;
            this.zzfn = zzarn.bqF;
            this.zzfo = null;
            this.bqE = -1;
        }

        public void zza(zzard zzard) {
            if (this.zzfk != null) {
                zzard.zzb(1, this.zzfk.longValue());
            }
            if (this.zzfl != null) {
                zzard.zzae(2, this.zzfl.intValue());
            }
            if (this.zzfm != null) {
                zzard.zzj(3, this.zzfm.booleanValue());
            }
            if (this.zzfn != null && this.zzfn.length > 0) {
                for (int zzae : this.zzfn) {
                    zzard.zzae(4, zzae);
                }
            }
            if (this.zzfo != null) {
                zzard.zza(5, this.zzfo.longValue());
            }
            super.zza(zzard);
        }

        /* renamed from: zzf */
        public zzb zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 8) {
                    this.zzfk = Long.valueOf(zzarc.cz());
                } else if (cw == 16) {
                    this.zzfl = Integer.valueOf(zzarc.cA());
                } else if (cw == 24) {
                    this.zzfm = Boolean.valueOf(zzarc.cC());
                } else if (cw == 32) {
                    int zzc = zzarn.zzc(zzarc, 32);
                    int length = this.zzfn == null ? 0 : this.zzfn.length;
                    int[] iArr = new int[(zzc + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzfn, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = zzarc.cA();
                        zzarc.cw();
                        length++;
                    }
                    iArr[length] = zzarc.cA();
                    this.zzfn = iArr;
                } else if (cw == 34) {
                    int zzahc = zzarc.zzahc(zzarc.cF());
                    int position = zzarc.getPosition();
                    int i = 0;
                    while (zzarc.cK() > 0) {
                        zzarc.cA();
                        i++;
                    }
                    zzarc.zzahe(position);
                    int length2 = this.zzfn == null ? 0 : this.zzfn.length;
                    int[] iArr2 = new int[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzfn, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = zzarc.cA();
                        length2++;
                    }
                    this.zzfn = iArr2;
                    zzarc.zzahd(zzahc);
                } else if (cw == 40) {
                    this.zzfo = Long.valueOf(zzarc.cy());
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzfk != null) {
                zzx += zzard.zzf(1, this.zzfk.longValue());
            }
            if (this.zzfl != null) {
                zzx += zzard.zzag(2, this.zzfl.intValue());
            }
            if (this.zzfm != null) {
                zzx += zzard.zzk(3, this.zzfm.booleanValue());
            }
            if (this.zzfn != null && this.zzfn.length > 0) {
                int i = 0;
                for (int zzahi : this.zzfn) {
                    i += zzard.zzahi(zzahi);
                }
                zzx = zzx + i + (this.zzfn.length * 1);
            }
            return this.zzfo != null ? zzx + zzard.zze(5, this.zzfo.longValue()) : zzx;
        }
    }

    public static final class zzc extends zzare<zzc> {
        public byte[] zzfp;
        public byte[] zzfq;

        public zzc() {
            this.zzfp = null;
            this.zzfq = null;
            this.bqE = -1;
        }

        public void zza(zzard zzard) {
            if (this.zzfp != null) {
                zzard.zza(1, this.zzfp);
            }
            if (this.zzfq != null) {
                zzard.zza(2, this.zzfq);
            }
            super.zza(zzard);
        }

        /* renamed from: zzg */
        public zzc zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 10) {
                    this.zzfp = zzarc.readBytes();
                } else if (cw == 18) {
                    this.zzfq = zzarc.readBytes();
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzfp != null) {
                zzx += zzard.zzb(1, this.zzfp);
            }
            return this.zzfq != null ? zzx + zzard.zzb(2, this.zzfq) : zzx;
        }
    }

    public static final class zzd extends zzare<zzd> {
        public byte[] data;
        public byte[] zzfr;
        public byte[] zzfs;
        public byte[] zzft;

        public zzd() {
            this.data = null;
            this.zzfr = null;
            this.zzfs = null;
            this.zzft = null;
            this.bqE = -1;
        }

        public static zzd zzd(byte[] bArr) {
            return (zzd) zzark.zza(new zzd(), bArr);
        }

        public void zza(zzard zzard) {
            if (this.data != null) {
                zzard.zza(1, this.data);
            }
            if (this.zzfr != null) {
                zzard.zza(2, this.zzfr);
            }
            if (this.zzfs != null) {
                zzard.zza(3, this.zzfs);
            }
            if (this.zzft != null) {
                zzard.zza(4, this.zzft);
            }
            super.zza(zzard);
        }

        /* renamed from: zzh */
        public zzd zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 10) {
                    this.data = zzarc.readBytes();
                } else if (cw == 18) {
                    this.zzfr = zzarc.readBytes();
                } else if (cw == 26) {
                    this.zzfs = zzarc.readBytes();
                } else if (cw == 34) {
                    this.zzft = zzarc.readBytes();
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.data != null) {
                zzx += zzard.zzb(1, this.data);
            }
            if (this.zzfr != null) {
                zzx += zzard.zzb(2, this.zzfr);
            }
            if (this.zzfs != null) {
                zzx += zzard.zzb(3, this.zzfs);
            }
            return this.zzft != null ? zzx + zzard.zzb(4, this.zzft) : zzx;
        }
    }

    public static final class zze extends zzare<zze> {
        public Long zzfk;
        public String zzfu;
        public byte[] zzfv;

        public zze() {
            this.zzfk = null;
            this.zzfu = null;
            this.zzfv = null;
            this.bqE = -1;
        }

        public void zza(zzard zzard) {
            if (this.zzfk != null) {
                zzard.zzb(1, this.zzfk.longValue());
            }
            if (this.zzfu != null) {
                zzard.zzr(3, this.zzfu);
            }
            if (this.zzfv != null) {
                zzard.zza(4, this.zzfv);
            }
            super.zza(zzard);
        }

        /* renamed from: zzi */
        public zze zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 8) {
                    this.zzfk = Long.valueOf(zzarc.cz());
                } else if (cw == 26) {
                    this.zzfu = zzarc.readString();
                } else if (cw == 34) {
                    this.zzfv = zzarc.readBytes();
                } else if (!super.zza(zzarc, cw)) {
                    return this;
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzfk != null) {
                zzx += zzard.zzf(1, this.zzfk.longValue());
            }
            if (this.zzfu != null) {
                zzx += zzard.zzs(3, this.zzfu);
            }
            return this.zzfv != null ? zzx + zzard.zzb(4, this.zzfv) : zzx;
        }
    }

    public static final class zzf extends zzare<zzf> {
        public byte[] zzfr;
        public byte[][] zzfw;
        public Integer zzfx;
        public Integer zzfy;

        public zzf() {
            this.zzfw = zzarn.bqL;
            this.zzfr = null;
            this.zzfx = null;
            this.zzfy = null;
            this.bqE = -1;
        }

        public void zza(zzard zzard) {
            if (this.zzfw != null && this.zzfw.length > 0) {
                for (byte[] bArr : this.zzfw) {
                    if (bArr != null) {
                        zzard.zza(1, bArr);
                    }
                }
            }
            if (this.zzfr != null) {
                zzard.zza(2, this.zzfr);
            }
            if (this.zzfx != null) {
                zzard.zzae(3, this.zzfx.intValue());
            }
            if (this.zzfy != null) {
                zzard.zzae(4, this.zzfy.intValue());
            }
            super.zza(zzard);
        }

        /* renamed from: zzj */
        public zzf zzb(zzarc zzarc) {
            while (true) {
                int cw = zzarc.cw();
                if (cw == 0) {
                    return this;
                }
                if (cw == 10) {
                    int zzc = zzarn.zzc(zzarc, 10);
                    int length = this.zzfw == null ? 0 : this.zzfw.length;
                    byte[][] bArr = new byte[(zzc + length)][];
                    if (length != 0) {
                        System.arraycopy(this.zzfw, 0, bArr, 0, length);
                    }
                    while (length < bArr.length - 1) {
                        bArr[length] = zzarc.readBytes();
                        zzarc.cw();
                        length++;
                    }
                    bArr[length] = zzarc.readBytes();
                    this.zzfw = bArr;
                } else if (cw != 18) {
                    if (cw != 24) {
                        if (cw == 32) {
                            int cA = zzarc.cA();
                            switch (cA) {
                                case 0:
                                case 1:
                                    this.zzfy = Integer.valueOf(cA);
                                    break;
                            }
                        } else if (!super.zza(zzarc, cw)) {
                            return this;
                        }
                    } else {
                        int cA2 = zzarc.cA();
                        switch (cA2) {
                            case 0:
                            case 1:
                                this.zzfx = Integer.valueOf(cA2);
                                break;
                        }
                    }
                } else {
                    this.zzfr = zzarc.readBytes();
                }
            }
        }

        /* access modifiers changed from: protected */
        public int zzx() {
            int zzx = super.zzx();
            if (this.zzfw != null && this.zzfw.length > 0) {
                int i = 0;
                int i2 = 0;
                for (byte[] bArr : this.zzfw) {
                    if (bArr != null) {
                        i2++;
                        i += zzard.zzbg(bArr);
                    }
                }
                zzx = zzx + i + (i2 * 1);
            }
            if (this.zzfr != null) {
                zzx += zzard.zzb(2, this.zzfr);
            }
            if (this.zzfx != null) {
                zzx += zzard.zzag(3, this.zzfx.intValue());
            }
            return this.zzfy != null ? zzx + zzard.zzag(4, this.zzfy.intValue()) : zzx;
        }
    }
}
