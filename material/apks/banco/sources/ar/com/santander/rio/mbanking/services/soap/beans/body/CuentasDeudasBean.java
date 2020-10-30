package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CuentasDeudasBean {
    @SerializedName("cta")
    public List<CuentaDebitoBean> lstCuentaDeudas;

    public CuentasDeudasBean() {
        this.lstCuentaDeudas = new ArrayList();
    }

    public CuentasDeudasBean(List<CuentaDebitoBean> list) {
        this.lstCuentaDeudas = list;
    }

    public List<CuentaDebitoBean> getCuentas() {
        return this.lstCuentaDeudas;
    }
}
