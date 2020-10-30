package ar.com.santander.rio.mbanking.services.events;

public class ConsDescripcionesEvent extends WebServiceEvent {
    private boolean a;

    public boolean isFromLogin() {
        return this.a;
    }

    public void setFromLogin(boolean z) {
        this.a = z;
    }
}
