package com.google.android.gms.common.internal;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.base.Ascii;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class zzg {
    public static final zzg BB = zza((CharSequence) "\t\n\u000b\f\r     　 ᠎ ").zza(zza(8192, 8202));
    public static final zzg BC = zza((CharSequence) "\t\n\u000b\f\r     　").zza(zza(8192, 8198)).zza(zza(8200, 8202));
    public static final zzg BD = zza(0, Ascii.MAX);
    public static final zzg BE;
    public static final zzg BF = zza(9, TokenParser.CR).zza(zza(28, TokenParser.SP)).zza(zzc(5760)).zza(zzc(6158)).zza(zza(8192, 8198)).zza(zza(8200, 8203)).zza(zza(8232, 8233)).zza(zzc(8287)).zza(zzc(12288));
    public static final zzg BG = new zzg() {
        public boolean zzd(char c) {
            return Character.isDigit(c);
        }
    };
    public static final zzg BH = new zzg() {
        public boolean zzd(char c) {
            return Character.isLetter(c);
        }
    };
    public static final zzg BI = new zzg() {
        public boolean zzd(char c) {
            return Character.isLetterOrDigit(c);
        }
    };
    public static final zzg BJ = new zzg() {
        public boolean zzd(char c) {
            return Character.isUpperCase(c);
        }
    };
    public static final zzg BK = new zzg() {
        public boolean zzd(char c) {
            return Character.isLowerCase(c);
        }
    };
    public static final zzg BL = zza(0, 31).zza(zza(Ascii.MAX, 159));
    public static final zzg BM = zza(0, TokenParser.SP).zza(zza(Ascii.MAX, 160)).zza(zzc(173)).zza(zza(1536, 1539)).zza(zza((CharSequence) "۝܏ ឴឵᠎")).zza(zza(8192, 8207)).zza(zza(8232, 8239)).zza(zza(8287, 8292)).zza(zza(8298, 8303)).zza(zzc(12288)).zza(zza(55296, 63743)).zza(zza((CharSequence) "﻿￹￺￻"));
    public static final zzg BN = zza(0, 1273).zza(zzc(1470)).zza(zza(1488, 1514)).zza(zzc(1523)).zza(zzc(1524)).zza(zza(1536, 1791)).zza(zza(1872, 1919)).zza(zza(3584, 3711)).zza(zza(7680, 8367)).zza(zza(8448, 8506)).zza(zza(64336, 65023)).zza(zza(65136, 65279)).zza(zza(65377, 65500));
    public static final zzg BO = new zzg() {
        public zzg zza(zzg zzg) {
            zzac.zzy(zzg);
            return this;
        }

        public boolean zzb(CharSequence charSequence) {
            zzac.zzy(charSequence);
            return true;
        }

        public boolean zzd(char c) {
            return true;
        }
    };
    public static final zzg BP = new zzg() {
        public zzg zza(zzg zzg) {
            return (zzg) zzac.zzy(zzg);
        }

        public boolean zzb(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        public boolean zzd(char c) {
            return false;
        }
    };

    static class zza extends zzg {
        List<zzg> a;

        zza(List<zzg> list) {
            this.a = list;
        }

        public zzg zza(zzg zzg) {
            ArrayList arrayList = new ArrayList(this.a);
            arrayList.add((zzg) zzac.zzy(zzg));
            return new zza(arrayList);
        }

        public boolean zzd(char c) {
            for (zzg zzd : this.a) {
                if (zzd.zzd(c)) {
                    return true;
                }
            }
            return false;
        }
    }

    static {
        char[] charArray;
        zzg zza2 = zza(TarjetasConstants.ULT_NUM_AMEX, '9');
        zzg zzg = zza2;
        for (char c : "٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".toCharArray()) {
            zzg = zzg.zza(zza(c, (char) (c + 9)));
        }
        BE = zzg;
    }

    public static zzg zza(final char c, final char c2) {
        zzac.zzbs(c2 >= c);
        return new zzg() {
            public boolean zzd(char c) {
                return c <= c && c <= c2;
            }
        };
    }

    public static zzg zza(CharSequence charSequence) {
        switch (charSequence.length()) {
            case 0:
                return BP;
            case 1:
                return zzc(charSequence.charAt(0));
            case 2:
                final char charAt = charSequence.charAt(0);
                final char charAt2 = charSequence.charAt(1);
                return new zzg() {
                    public boolean zzd(char c) {
                        return c == charAt || c == charAt2;
                    }
                };
            default:
                final char[] charArray = charSequence.toString().toCharArray();
                Arrays.sort(charArray);
                return new zzg() {
                    public boolean zzd(char c) {
                        return Arrays.binarySearch(charArray, c) >= 0;
                    }
                };
        }
    }

    public static zzg zzc(final char c) {
        return new zzg() {
            public zzg zza(zzg zzg) {
                return zzg.zzd(c) ? zzg : zzg.super.zza(zzg);
            }

            public boolean zzd(char c) {
                return c == c;
            }
        };
    }

    public zzg zza(zzg zzg) {
        return new zza(Arrays.asList(new zzg[]{this, (zzg) zzac.zzy(zzg)}));
    }

    public boolean zzb(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!zzd(charSequence.charAt(length))) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean zzd(char c);
}
