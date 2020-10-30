package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CajerosBean {
    @SerializedName("cajero")
    private List<CajeroBean> cajeroBeans;

    public CajerosBean(List<CajeroBean> list) {
        this.cajeroBeans = list;
    }

    public CajerosBean() {
    }

    public List<CajeroBean> getCajeroBeans() {
        return this.cajeroBeans;
    }

    public void setCajeroBeans(List<CajeroBean> list) {
        this.cajeroBeans = list;
    }
}
