package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.R;

public class zzaj {
    private final Resources a;
    private final String b = this.a.getResourcePackageName(R.string.common_google_play_services_unknown_issue);

    public zzaj(Context context) {
        zzac.zzy(context);
        this.a = context.getResources();
    }

    public String getString(String str) {
        int identifier = this.a.getIdentifier(str, "string", this.b);
        if (identifier == 0) {
            return null;
        }
        return this.a.getString(identifier);
    }
}
