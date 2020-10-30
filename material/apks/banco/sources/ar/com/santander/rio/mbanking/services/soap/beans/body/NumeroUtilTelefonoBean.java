package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class NumeroUtilTelefonoBean {
    @SerializedName("telDatoUtil")
    public String telefono;

    public NumeroUtilTelefonoBean() {
    }

    public NumeroUtilTelefonoBean(String str) {
        this.telefono = str;
    }
}
