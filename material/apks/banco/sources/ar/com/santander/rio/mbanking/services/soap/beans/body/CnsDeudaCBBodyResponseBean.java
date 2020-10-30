package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsDeudaCBBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cantEmpresas")
    public String cantEmpresas;
    @SerializedName("cuentas")
    public CuentasDeudasBean cuentasDeudasBean;
    @SerializedName("datosDeudas")
    public DatosDeudasBean datosDeudas;
    @SerializedName("listaEmpresas")
    public CnsEmpresaDatosEmpresas listaEmpresa;

    public CnsDeudaCBBodyResponseBean() {
        this.cuentasDeudasBean = new CuentasDeudasBean();
        this.datosDeudas = new DatosDeudasBean();
    }

    public CnsDeudaCBBodyResponseBean(String str, CuentasDeudasBean cuentasDeudasBean2, DatosDeudasBean datosDeudasBean) {
        this.cantEmpresas = str;
        this.cuentasDeudasBean = cuentasDeudasBean2;
        this.datosDeudas = datosDeudasBean;
    }
}
