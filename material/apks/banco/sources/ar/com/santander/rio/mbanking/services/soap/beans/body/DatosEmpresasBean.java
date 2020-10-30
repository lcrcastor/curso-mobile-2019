package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class DatosEmpresasBean {
    @SerializedName("cantEmpresas")
    public String cantEmpresas;
    @SerializedName("datosDeuda")
    public DatosDeudaBean datosDeuda;
    @SerializedName("listaEmpresa")
    public CnsEmpresaDatosEmpresas listaEmpresa;

    public DatosEmpresasBean() {
    }

    public DatosEmpresasBean(String str, DatosDeudaBean datosDeudaBean) {
        this.cantEmpresas = str;
        this.datosDeuda = datosDeudaBean;
    }

    public DatosEmpresasBean(String str, CnsEmpresaDatosEmpresas cnsEmpresaDatosEmpresas) {
        this.cantEmpresas = str;
        this.listaEmpresa = cnsEmpresaDatosEmpresas;
    }
}
