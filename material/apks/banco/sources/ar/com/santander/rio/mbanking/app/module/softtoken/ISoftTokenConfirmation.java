package ar.com.santander.rio.mbanking.app.module.softtoken;

public interface ISoftTokenConfirmation extends ISoftTokenPage {
    void onVerToken();

    void onVolverAlInicio();
}
