package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.module.notifications.NotificationPresenter;
import ar.com.santander.rio.mbanking.app.module.notifications.NotificationPresenterImp;
import ar.com.santander.rio.mbanking.app.module.notifications.NotificationView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Notificacion;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;
import javax.inject.Inject;

public class NotificacionesFragment extends BaseFragment implements NotificationView {
    @Inject
    SessionManager a;
    @Inject
    AnalyticsManager b;
    NotificationPresenter c;
    private LayoutInflater d;
    @InjectView(2131366452)
    ViewGroup vgNotifications;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = new NotificationPresenterImp(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.d = layoutInflater;
        View inflate = layoutInflater.inflate(R.layout.notificaciones_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.c.onCreateView(this.a.getLoginUnico().getCrm());
        this.b.trackScreen(getString(R.string.analytics_screen_notification_and_alerts));
        return inflate;
    }

    private View y() {
        return this.d.inflate(R.layout.item_notificacion, null);
    }

    public void showListNotifications(List<Notificacion> list) {
        for (Notificacion notificacion : list) {
            View y = y();
            a(y, notificacion.titulo);
            b(y, notificacion.mensaje);
            c(y, UtilDate.getDateFormat(notificacion.fechaAlerta, Constants.FORMAT_DATE_WS_2, Constants.FORMAT_DATE_APP_2));
            this.vgNotifications.addView(y);
        }
    }

    private TextView b(View view) {
        return (TextView) view.findViewById(R.id.tvTitle);
    }

    private TextView c(View view) {
        return (TextView) view.findViewById(R.id.tvDescription);
    }

    private TextView d(View view) {
        return (TextView) view.findViewById(R.id.tvDate);
    }

    private void a(View view, String str) {
        TextView b2 = b(view);
        if (b2 != null) {
            b2.setText(str != null ? Html.fromHtml(str) : "");
        }
    }

    private void b(View view, String str) {
        TextView c2 = c(view);
        if (c2 != null) {
            c2.setText(str != null ? Html.fromHtml(str) : "");
        }
    }

    private void c(View view, String str) {
        TextView d2 = d(view);
        if (d2 != null) {
            if (str == null) {
                str = "";
            }
            d2.setText(str);
        }
    }
}
