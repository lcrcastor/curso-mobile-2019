package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class DatosDeudasBean {
    public String cantDeudas;
    @SerializedName("datosDeuda")
    public List<DatosDeudaBean> lstDatosDeudaBean;
    public String mensaje;

    public DatosDeudasBean() {
        this.lstDatosDeudaBean = new ArrayList();
    }

    public DatosDeudasBean(String str, String str2, List<DatosDeudaBean> list) {
        this.cantDeudas = str;
        this.mensaje = str2;
        this.lstDatosDeudaBean = list;
    }

    public List<DatosDeudaBean> getLstDatosDeudaBean() {
        return this.lstDatosDeudaBean;
    }

    public void setLstDatosDeudaBean(List<DatosDeudaBean> list) {
        this.lstDatosDeudaBean = list;
    }
}
