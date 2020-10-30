package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;

public class zza implements Creator<GoogleSignInAccount> {
    static void a(GoogleSignInAccount googleSignInAccount, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, googleSignInAccount.a);
        zzb.zza(parcel, 2, googleSignInAccount.getId(), false);
        zzb.zza(parcel, 3, googleSignInAccount.getIdToken(), false);
        zzb.zza(parcel, 4, googleSignInAccount.getEmail(), false);
        zzb.zza(parcel, 5, googleSignInAccount.getDisplayName(), false);
        zzb.zza(parcel, 6, (Parcelable) googleSignInAccount.getPhotoUrl(), i, false);
        zzb.zza(parcel, 7, googleSignInAccount.getServerAuthCode(), false);
        zzb.zza(parcel, 8, googleSignInAccount.zzahe());
        zzb.zza(parcel, 9, googleSignInAccount.zzahf(), false);
        zzb.zzc(parcel, 10, googleSignInAccount.b, false);
        zzb.zza(parcel, 11, googleSignInAccount.getGivenName(), false);
        zzb.zza(parcel, 12, googleSignInAccount.getFamilyName(), false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzav */
    public GoogleSignInAccount createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = com.google.android.gms.common.internal.safeparcel.zza.zzcq(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        Uri uri = null;
        String str5 = null;
        String str6 = null;
        List list = null;
        String str7 = null;
        String str8 = null;
        long j = 0;
        int i = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = com.google.android.gms.common.internal.safeparcel.zza.zzcp(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzgv(zzcp)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                case 2:
                    str = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 3:
                    str2 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 4:
                    str3 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 5:
                    str4 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 6:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel2, zzcp, Uri.CREATOR);
                    break;
                case 7:
                    str5 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 8:
                    j = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel2, zzcp);
                    break;
                case 9:
                    str6 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 10:
                    list = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel2, zzcp, Scope.CREATOR);
                    break;
                case 11:
                    str7 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 12:
                    str8 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel2, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel2);
        }
        GoogleSignInAccount googleSignInAccount = new GoogleSignInAccount(i, str, str2, str3, str4, uri, str5, j, str6, list, str7, str8);
        return googleSignInAccount;
    }

    /* renamed from: zzdg */
    public GoogleSignInAccount[] newArray(int i) {
        return new GoogleSignInAccount[i];
    }
}
