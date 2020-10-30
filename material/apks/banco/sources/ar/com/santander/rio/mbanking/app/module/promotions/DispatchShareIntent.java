package ar.com.santander.rio.mbanking.app.module.promotions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.components.mapInfo.MyMarker;
import ar.com.santander.rio.mbanking.components.share.ShareIntentComponent;
import ar.com.santander.rio.mbanking.components.share.intent.AllIntent;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetClasificadoresBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.Utils;
import com.orhanobut.logger.Logger;

public class DispatchShareIntent extends ShareIntentComponent {
    private SessionManager a;
    private PackageManager b;
    private Context c;

    public DispatchShareIntent(SessionManager sessionManager, PackageManager packageManager, Context context) {
        this.a = sessionManager;
        this.b = packageManager;
        this.c = context;
    }

    public Intent getListIntentToShare(MyMarker myMarker) {
        return a(myMarker);
    }

    private Intent a(MyMarker myMarker) {
        Bundle bundle = new Bundle();
        try {
            GetClasificadoresBodyResponseBean getClasificadores = this.a.getGetClasificadores();
            if (getClasificadores == null) {
                bundle.putString("android.intent.extra.TEXT", a(myMarker, this.c.getString(R.string.IDX_PROMO_SHARE_BODY_EMAIL)));
                bundle.putString("android.intent.extra.SUBJECT", a(myMarker, this.c.getString(R.string.IDX_PROMO_SHARE_TITLE_EMAIL)));
            } else {
                bundle.putString("android.intent.extra.TEXT", a(myMarker, Html.fromHtml(getClasificadores.configuraciones.getConfigBeanForKey(Constants.SHARE_BODY_EMAIL).value).toString()));
                bundle.putString("android.intent.extra.SUBJECT", Html.fromHtml(getClasificadores.configuraciones.getConfigBeanForKey(Constants.SHARE_TITLE_EMAIL).value).toString());
            }
            AllIntent allIntent = new AllIntent();
            allIntent.setExtras(bundle);
            return allIntent.getAllIntents();
        } catch (Exception e) {
            Logger.e(e, e.getMessage(), new Object[0]);
            return null;
        }
    }

    private String a(MyMarker myMarker, String str) {
        return str.replace("{descripcion}", myMarker != null ? Html.fromHtml(Utils.formatIsbanHTMLCode(myMarker.desc)).toString() : "").replace("{nombre}", myMarker != null ? Html.fromHtml(Utils.formatIsbanHTMLCode(myMarker.title)).toString() : "").replace("{plataforma}", Constants.CURRENT_SO).replace("{link_promo}", myMarker != null ? myMarker.url : "");
    }
}
