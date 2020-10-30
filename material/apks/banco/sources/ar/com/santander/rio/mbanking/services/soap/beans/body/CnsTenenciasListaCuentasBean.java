package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CnsTenenciasListaCuentasBean {
    @SerializedName("Cuenta")
    public List<CnsTenenciasDatosCuentaBean> cuenta;

    public CnsTenenciasListaCuentasBean() {
        this.cuenta = new ArrayList();
    }

    public CnsTenenciasListaCuentasBean(List<CnsTenenciasDatosCuentaBean> list) {
        this.cuenta = list;
    }
}
