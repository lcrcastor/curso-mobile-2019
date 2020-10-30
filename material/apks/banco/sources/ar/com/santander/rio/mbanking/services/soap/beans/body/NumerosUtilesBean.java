package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class NumerosUtilesBean {
    @SerializedName("datoUtil")
    public NumeroUtilBean numeroUtilBean;

    public NumerosUtilesBean() {
    }

    public NumerosUtilesBean(NumeroUtilBean numeroUtilBean2) {
        this.numeroUtilBean = numeroUtilBean2;
    }
}
