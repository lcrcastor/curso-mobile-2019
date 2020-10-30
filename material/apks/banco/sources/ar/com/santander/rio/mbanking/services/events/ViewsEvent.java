package ar.com.santander.rio.mbanking.services.events;

import android.view.View;

public class ViewsEvent {
    private View a;

    public ViewsEvent(View view) {
        this.a = view;
    }

    public View getView() {
        return this.a;
    }

    public void setView(View view) {
        this.a = view;
    }
}
