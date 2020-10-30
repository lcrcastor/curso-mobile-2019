package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TarjetasBean {
    @SerializedName("tarjeta")
    private List<TarjetaBean> listaTarjetas;

    public TarjetasBean() {
    }

    public TarjetasBean(List<TarjetaBean> list) {
        this.listaTarjetas = list;
    }

    public void setListaTarjetas(List<TarjetaBean> list) {
        this.listaTarjetas = list;
    }
}
