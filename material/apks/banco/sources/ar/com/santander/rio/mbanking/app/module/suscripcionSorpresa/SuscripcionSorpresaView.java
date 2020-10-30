package ar.com.santander.rio.mbanking.app.module.suscripcionSorpresa;

import android.content.Context;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;

public interface SuscripcionSorpresaView {
    void esClienteEstadoTO();

    Context getContext();

    IDataManager getDataManager();

    SessionManager getSessionManager();

    void modificarCelular(String str, String str2, String str3);

    void setCelularPrincipal(String str, String str2, String str3);

    void setCelularSecundario(String str, String str2, String str3, boolean z);

    void setEstadoSuscripcion(boolean z, String str);

    void setMail(String str);

    void setTitle();

    void showMessageNoDataModified();

    void showProgress(String str);

    void verRequisitos(String str);

    void verTerminosYCondiciones(String str);
}
