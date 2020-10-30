package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ExtEnvListaCuentasBean {
    @SerializedName("cuenta")
    public List<ExtEnvCuentaBean> cuenta;

    public ExtEnvListaCuentasBean() {
        this.cuenta = new ArrayList();
    }

    public ExtEnvListaCuentasBean(List<ExtEnvCuentaBean> list) {
        this.cuenta = list;
    }
}
