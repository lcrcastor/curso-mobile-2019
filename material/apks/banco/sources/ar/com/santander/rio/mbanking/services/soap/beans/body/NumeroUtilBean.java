package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NumeroUtilBean {
    @SerializedName("descripAdicDatoUtil")
    public String detalle;
    @SerializedName("telsDatosUtiles")
    public List<NumeroUtilTelefonoBean> telefonosList;
    @SerializedName("descripDatoUtil")
    public String titulo;

    public NumeroUtilBean() {
    }

    public NumeroUtilBean(String str, String str2, List<NumeroUtilTelefonoBean> list) {
        this.titulo = str;
        this.detalle = str2;
        this.telefonosList = list;
    }
}
