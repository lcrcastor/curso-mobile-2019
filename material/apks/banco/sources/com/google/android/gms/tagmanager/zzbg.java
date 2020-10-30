package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import java.io.UnsupportedEncodingException;
import java.util.Set;

class zzbg extends zzam {
    private static final String a = zzaf.JOINER.toString();
    private static final String b = zzag.ARG0.toString();
    private static final String c = zzag.ITEM_SEPARATOR.toString();
    private static final String d = zzag.KEY_VALUE_SEPARATOR.toString();
    private static final String e = zzag.ESCAPE.toString();

    enum zza {
        NONE,
        URL,
        BACKSLASH
    }

    public zzbg() {
        super(a, b);
    }

    private String a(String str, zza zza2, Set<Character> set) {
        switch (zza2) {
            case URL:
                try {
                    return zzdq.a(str);
                } catch (UnsupportedEncodingException e2) {
                    zzbo.zzb("Joiner: unsupported encoding", e2);
                    return str;
                }
            case BACKSLASH:
                String replace = str.replace("\\", "\\\\");
                for (Character ch : set) {
                    String ch2 = ch.toString();
                    String str2 = "\\";
                    String valueOf = String.valueOf(ch2);
                    replace = replace.replace(ch2, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
                return replace;
            default:
                return str;
        }
    }

    private void a(StringBuilder sb, String str, zza zza2, Set<Character> set) {
        sb.append(a(str, zza2, set));
    }

    private void a(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.internal.zzai.zza zzaw(java.util.Map<java.lang.String, com.google.android.gms.internal.zzai.zza> r10) {
        /*
            r9 = this;
            java.lang.String r0 = b
            java.lang.Object r0 = r10.get(r0)
            com.google.android.gms.internal.zzai$zza r0 = (com.google.android.gms.internal.zzai.zza) r0
            if (r0 != 0) goto L_0x000f
            com.google.android.gms.internal.zzai$zza r10 = com.google.android.gms.tagmanager.zzdm.zzchl()
            return r10
        L_0x000f:
            java.lang.String r1 = c
            java.lang.Object r1 = r10.get(r1)
            com.google.android.gms.internal.zzai$zza r1 = (com.google.android.gms.internal.zzai.zza) r1
            if (r1 == 0) goto L_0x001e
            java.lang.String r1 = com.google.android.gms.tagmanager.zzdm.zzg(r1)
            goto L_0x0020
        L_0x001e:
            java.lang.String r1 = ""
        L_0x0020:
            java.lang.String r2 = d
            java.lang.Object r2 = r10.get(r2)
            com.google.android.gms.internal.zzai$zza r2 = (com.google.android.gms.internal.zzai.zza) r2
            if (r2 == 0) goto L_0x002f
            java.lang.String r2 = com.google.android.gms.tagmanager.zzdm.zzg(r2)
            goto L_0x0031
        L_0x002f:
            java.lang.String r2 = "="
        L_0x0031:
            com.google.android.gms.tagmanager.zzbg$zza r3 = com.google.android.gms.tagmanager.zzbg.zza.NONE
            java.lang.String r4 = e
            java.lang.Object r10 = r10.get(r4)
            com.google.android.gms.internal.zzai$zza r10 = (com.google.android.gms.internal.zzai.zza) r10
            r4 = 0
            if (r10 == 0) goto L_0x008a
            java.lang.String r10 = com.google.android.gms.tagmanager.zzdm.zzg(r10)
            java.lang.String r3 = "url"
            boolean r3 = r3.equals(r10)
            if (r3 == 0) goto L_0x004d
            com.google.android.gms.tagmanager.zzbg$zza r3 = com.google.android.gms.tagmanager.zzbg.zza.URL
            goto L_0x008a
        L_0x004d:
            java.lang.String r3 = "backslash"
            boolean r3 = r3.equals(r10)
            if (r3 == 0) goto L_0x006c
            com.google.android.gms.tagmanager.zzbg$zza r3 = com.google.android.gms.tagmanager.zzbg.zza.BACKSLASH
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            r9.a(r4, r1)
            r9.a(r4, r2)
            r10 = 92
            java.lang.Character r10 = java.lang.Character.valueOf(r10)
            r4.remove(r10)
            goto L_0x008a
        L_0x006c:
            java.lang.String r0 = "Joiner: unsupported escape type: "
            java.lang.String r10 = java.lang.String.valueOf(r10)
            int r1 = r10.length()
            if (r1 == 0) goto L_0x007d
            java.lang.String r10 = r0.concat(r10)
            goto L_0x0082
        L_0x007d:
            java.lang.String r10 = new java.lang.String
            r10.<init>(r0)
        L_0x0082:
            com.google.android.gms.tagmanager.zzbo.e(r10)
            com.google.android.gms.internal.zzai$zza r10 = com.google.android.gms.tagmanager.zzdm.zzchl()
            return r10
        L_0x008a:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            int r5 = r0.type
            r6 = 0
            switch(r5) {
                case 2: goto L_0x00c3;
                case 3: goto L_0x009d;
                default: goto L_0x0095;
            }
        L_0x0095:
            java.lang.String r0 = com.google.android.gms.tagmanager.zzdm.zzg(r0)
            r9.a(r10, r0, r3, r4)
            goto L_0x00dd
        L_0x009d:
            com.google.android.gms.internal.zzai$zza[] r5 = r0.zzxv
            int r5 = r5.length
            if (r6 >= r5) goto L_0x00dd
            if (r6 <= 0) goto L_0x00a7
            r10.append(r1)
        L_0x00a7:
            com.google.android.gms.internal.zzai$zza[] r5 = r0.zzxv
            r5 = r5[r6]
            java.lang.String r5 = com.google.android.gms.tagmanager.zzdm.zzg(r5)
            com.google.android.gms.internal.zzai$zza[] r7 = r0.zzxw
            r7 = r7[r6]
            java.lang.String r7 = com.google.android.gms.tagmanager.zzdm.zzg(r7)
            r9.a(r10, r5, r3, r4)
            r10.append(r2)
            r9.a(r10, r7, r3, r4)
            int r6 = r6 + 1
            goto L_0x009d
        L_0x00c3:
            com.google.android.gms.internal.zzai$zza[] r0 = r0.zzxu
            int r2 = r0.length
            r5 = 1
            r5 = 0
            r7 = 1
        L_0x00c9:
            if (r5 >= r2) goto L_0x00dd
            r8 = r0[r5]
            if (r7 != 0) goto L_0x00d2
            r10.append(r1)
        L_0x00d2:
            java.lang.String r7 = com.google.android.gms.tagmanager.zzdm.zzg(r8)
            r9.a(r10, r7, r3, r4)
            int r5 = r5 + 1
            r7 = 0
            goto L_0x00c9
        L_0x00dd:
            java.lang.String r10 = r10.toString()
            com.google.android.gms.internal.zzai$zza r10 = com.google.android.gms.tagmanager.zzdm.zzat(r10)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzbg.zzaw(java.util.Map):com.google.android.gms.internal.zzai$zza");
    }

    public boolean zzcds() {
        return true;
    }
}
