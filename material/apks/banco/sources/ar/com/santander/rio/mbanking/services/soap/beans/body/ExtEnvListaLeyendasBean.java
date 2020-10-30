package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ExtEnvListaLeyendasBean {
    @SerializedName("leyenda")
    public List<ExtEnvLeyendaBean> leyenda;

    public ExtEnvListaLeyendasBean() {
        this.leyenda = new ArrayList();
    }

    public ExtEnvListaLeyendasBean(List<ExtEnvLeyendaBean> list) {
        this.leyenda = list;
    }
}
