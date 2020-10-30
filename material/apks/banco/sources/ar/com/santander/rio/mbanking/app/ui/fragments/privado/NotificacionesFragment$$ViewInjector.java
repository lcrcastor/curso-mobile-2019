package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class NotificacionesFragment$$ViewInjector {
    public static void inject(Finder finder, NotificacionesFragment notificacionesFragment, Object obj) {
        notificacionesFragment.vgNotifications = (ViewGroup) finder.findRequiredView(obj, R.id.wrapperNotifications, "field 'vgNotifications'");
    }

    public static void reset(NotificacionesFragment notificacionesFragment) {
        notificacionesFragment.vgNotifications = null;
    }
}
