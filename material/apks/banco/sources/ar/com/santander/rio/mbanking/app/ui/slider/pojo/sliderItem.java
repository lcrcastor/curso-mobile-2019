package ar.com.santander.rio.mbanking.app.ui.slider.pojo;

import android.view.View.OnClickListener;

public class sliderItem {
    private int a;
    private String b;
    private String c;
    private OnClickListener d;

    public String getNroTarjeta() {
        return this.b;
    }

    public void setNroTarjeta(String str) {
        this.b = str;
    }

    public String getAliasTarjeta() {
        return this.c;
    }

    public void setAliasTarjeta(String str) {
        this.c = str;
    }

    public OnClickListener getButtonClick() {
        return this.d;
    }

    public void setButtonClick(OnClickListener onClickListener) {
        this.d = onClickListener;
    }

    public int getImage() {
        return this.a;
    }

    public void setImage(int i) {
        this.a = i;
    }
}
