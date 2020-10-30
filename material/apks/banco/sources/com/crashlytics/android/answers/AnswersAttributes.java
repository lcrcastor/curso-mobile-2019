package com.crashlytics.android.answers;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

class AnswersAttributes {
    final AnswersEventValidator a;
    final Map<String, Object> b = new ConcurrentHashMap();

    public AnswersAttributes(AnswersEventValidator answersEventValidator) {
        this.a = answersEventValidator;
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, String str2) {
        if (!this.a.a((Object) str, "key") && !this.a.a((Object) str2, TarjetasConstants.VALUE)) {
            a(this.a.a(str), (Object) this.a.a(str2));
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, Number number) {
        if (!this.a.a((Object) str, "key") && !this.a.a((Object) number, TarjetasConstants.VALUE)) {
            a(this.a.a(str), (Object) number);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, Object obj) {
        if (!this.a.a(this.b, str)) {
            this.b.put(str, obj);
        }
    }

    public String toString() {
        return new JSONObject(this.b).toString();
    }
}
