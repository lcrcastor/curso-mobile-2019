package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsEmpresaBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cuentas")
    public CuentasDeudasBean cuentas;
    @SerializedName("datosDeudas")
    public DatosDeudasBean datosDeudas;
    @SerializedName("datosEmpresa")
    public CnsEmpresaDatosEmpresa datosEmpresa;
    @SerializedName("hashCode")
    public String hashCode;
    @SerializedName("leyendaAyuda")
    public String leyendaAyuda;
    @SerializedName("listaEmpresa")
    public CnsEmpresaDatosEmpresas listaEmpresa;

    public CnsEmpresaBodyResponseBean(CnsEmpresaDatosEmpresas cnsEmpresaDatosEmpresas) {
        this.listaEmpresa = cnsEmpresaDatosEmpresas;
    }

    public CnsEmpresaBodyResponseBean(CnsEmpresaDatosEmpresas cnsEmpresaDatosEmpresas, String str) {
        this.listaEmpresa = cnsEmpresaDatosEmpresas;
        this.leyendaAyuda = str;
    }

    public CnsEmpresaBodyResponseBean(CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa, CuentasDeudasBean cuentasDeudasBean, DatosDeudasBean datosDeudasBean) {
        this.datosEmpresa = cnsEmpresaDatosEmpresa;
        this.cuentas = cuentasDeudasBean;
        this.datosDeudas = datosDeudasBean;
    }
}
