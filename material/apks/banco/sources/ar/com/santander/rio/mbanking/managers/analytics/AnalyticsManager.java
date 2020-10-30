package ar.com.santander.rio.mbanking.managers.analytics;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.CustomDimenssion;
import ar.com.santander.rio.mbanking.services.model.general.EventTracker;
import ar.com.santander.rio.mbanking.services.soap.constants.ConstantsWS;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ItemBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class AnalyticsManager {
    private Tracker a;
    private SessionManager b;
    protected BaseApplication baseApplication;

    public AnalyticsManager(BaseApplication baseApplication2, SessionManager sessionManager) {
        this.a = baseApplication2.getTracker();
        this.baseApplication = baseApplication2;
        this.b = sessionManager;
    }

    private String a() {
        String nup = this.b.getNup();
        String str = this.a.get("&uid");
        if (TextUtils.isEmpty(nup) || nup.equalsIgnoreCase(str)) {
            return (!TextUtils.isEmpty(nup) || !TextUtils.isEmpty(str)) ? null : null;
        }
        return nup;
    }

    public void trackScreen(String str) {
        String a2 = a();
        this.a.set("&uid", a2);
        this.a.setScreenName(a(str));
        ScreenViewBuilder screenViewBuilder = new ScreenViewBuilder();
        if (!TextUtils.isEmpty(a2)) {
            screenViewBuilder = (ScreenViewBuilder) ((ScreenViewBuilder) ((ScreenViewBuilder) new ScreenViewBuilder().setCustomDimension(1, a2)).setCustomDimension(2, String.valueOf("Cliente Santander"))).setCustomDimension(3, String.valueOf("Usuario Logueado"));
        }
        this.a.send(screenViewBuilder.build());
    }

    public void trackEvent(String str, String str2, String str3) {
        this.a.send(new EventBuilder().setCategory(str).setAction(str2).setLabel(str3).build());
    }

    public void trackEvent(Map<String, String> map) {
        this.a.send(map);
    }

    public void trackEvent(String str, String str2, String str3, String str4, String str5) {
        this.a.send(((EventBuilder) new EventBuilder().setCategory(str).setAction(str2).setLabel(str3).set(a(str4), a(str5))).build());
    }

    public void trackTransaction(String str, String str2, String str3, String str4, double d, long j) {
        this.a.send(new ItemBuilder().setTransactionId(str).setName(str2).setSku(str3).setCategory(str4).setPrice(d).setQuantity(j).build());
    }

    public void trackTransaction(String str, String str2) {
        String concat = str2.concat(UtilDate.getCurrentDateTimeToString(ConstantsWS.FORMAT_DATETIME_REQUEST));
        trackTransaction(concat, str, concat, "transaction", 1.0d, 1);
    }

    public void trackCustomDimension(String str, int i, String str2) {
        this.a.setScreenName(a(str));
        this.a.send(((ScreenViewBuilder) new ScreenViewBuilder().setCustomDimension(i, a(str2))).build());
    }

    public void trackCustomDimension(String str, EventTracker eventTracker) {
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.setAction(eventTracker.getAction());
        if (eventTracker.getLabel() != null) {
            eventBuilder.setLabel(a(eventTracker.getLabel()));
        }
        eventBuilder.setCategory(eventTracker.getCategory());
        if (eventTracker.getCustomDimenssion() != null && eventTracker.getCustomDimenssion().size() >= 1) {
            for (CustomDimenssion customDimenssion : eventTracker.getCustomDimenssion()) {
                if (customDimenssion.getParametro() != null) {
                    eventBuilder.setCustomDimension(customDimenssion.getCustomDimenssion(), a(customDimenssion.getParametro()));
                }
            }
        }
        if (eventTracker.getCustomMetrics() != null && !eventTracker.getCustomMetrics().isEmpty()) {
            for (Entry entry : eventTracker.getCustomMetrics().entrySet()) {
                eventBuilder.setCustomMetric(((Integer) entry.getKey()).intValue(), (float) ((Integer) entry.getValue()).intValue());
            }
        }
        this.a.send(eventBuilder.build());
    }

    private static String a(String str) {
        return Html.fromHtml(str).toString().replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
    }

    public String formatIDFilters(ArrayList<String> arrayList) {
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            sb.append(a((String) it.next()));
            sb.append(";");
        }
        return sb.toString();
    }

    public void setScreenName(String str) {
        this.a.setScreenName(str);
    }

    public Context getApplicationContext() {
        return this.baseApplication.getApplicationContext();
    }
}
