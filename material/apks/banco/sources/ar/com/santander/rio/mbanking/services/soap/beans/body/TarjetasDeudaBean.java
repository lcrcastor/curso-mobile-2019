package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class TarjetasDeudaBean {
    @SerializedName("Tarjeta")
    public List<TarjetaDeudaBean> lstTarjetasDeudaBean;

    public TarjetasDeudaBean() {
        this.lstTarjetasDeudaBean = new ArrayList();
    }

    public TarjetasDeudaBean(List<TarjetaDeudaBean> list) {
        this.lstTarjetasDeudaBean = list;
    }
}
