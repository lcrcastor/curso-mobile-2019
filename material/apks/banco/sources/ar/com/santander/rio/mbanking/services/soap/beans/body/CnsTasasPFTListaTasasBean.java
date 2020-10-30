package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CnsTasasPFTListaTasasBean {
    @SerializedName("tasa")
    public List<CnsTasasPFTTasaBean> tasa;

    public CnsTasasPFTListaTasasBean() {
        this.tasa = new ArrayList();
    }

    public CnsTasasPFTListaTasasBean(List<CnsTasasPFTTasaBean> list) {
        this.tasa = list;
    }

    public List<CnsTasasPFTTasaBean> getListaTasas() {
        return this.tasa;
    }
}
