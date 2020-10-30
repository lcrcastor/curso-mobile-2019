package com.crashlytics.android.answers;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import com.facebook.Response;

public class LevelEndEvent extends PredefinedEvent<LevelEndEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "levelEnd";
    }

    public LevelEndEvent putLevelName(String str) {
        this.d.a("levelName", str);
        return this;
    }

    public LevelEndEvent putScore(Number number) {
        this.d.a("score", number);
        return this;
    }

    public LevelEndEvent putSuccess(boolean z) {
        this.d.a(Response.SUCCESS_KEY, z ? "true" : Reintento.Reintento_Falso);
        return this;
    }
}
