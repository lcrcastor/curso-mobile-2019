package ar.com.santander.rio.mbanking.app.module.softtoken;

public interface SoftTokenView {
    void onAttempDoLogin();

    void onCreatePageTokenConfirmation();

    void onCreatePageTokenNew();

    void onCreatePageTokenTimer();

    void onFinishPageTokenConfirmation();

    void onFinishPageTokenNew();

    void onFinishPageTokenTimer();

    void onVerToken();

    void onVolverAlInicio();
}
