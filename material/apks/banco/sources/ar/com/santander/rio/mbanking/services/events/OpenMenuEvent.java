package ar.com.santander.rio.mbanking.services.events;

public class OpenMenuEvent {
    public String tagFragment;

    public String getTagFragment() {
        return this.tagFragment;
    }

    public void setTagFragment(String str) {
        this.tagFragment = str;
    }

    public OpenMenuEvent(String str) {
        this.tagFragment = str;
    }
}
