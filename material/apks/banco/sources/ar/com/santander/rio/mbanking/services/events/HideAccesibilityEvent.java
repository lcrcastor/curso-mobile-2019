package ar.com.santander.rio.mbanking.services.events;

public class HideAccesibilityEvent {
    public int typeStateView;

    public HideAccesibilityEvent() {
    }

    public HideAccesibilityEvent(int i) {
        this.typeStateView = i;
    }

    public int getResponse() {
        return this.typeStateView;
    }

    public void setResponse(int i) {
        this.typeStateView = i;
    }
}
