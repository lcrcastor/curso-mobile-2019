package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CargaDatosInicialesExtEnvBodyResponseBean extends ErrorBodyBean {
    @SerializedName("aceptaTyC")
    public String aceptaTyC;
    @SerializedName("datosExtEnv")
    public ExtEnvDatosBean datosExtEnv;
    @SerializedName("filtrosMandatos")
    public ExtEnvFiltrosMandatosBean filtrosMandatos;
    @SerializedName("listaCuentas")
    public ExtEnvListaCuentasBean listaCuentas;
    @SerializedName("listaLeyendas")
    public ExtEnvListaLeyendasBean listaLeyendas;

    public CargaDatosInicialesExtEnvBodyResponseBean() {
    }

    public CargaDatosInicialesExtEnvBodyResponseBean(ExtEnvFiltrosMandatosBean extEnvFiltrosMandatosBean, ExtEnvListaCuentasBean extEnvListaCuentasBean, String str, ExtEnvListaLeyendasBean extEnvListaLeyendasBean, ExtEnvDatosBean extEnvDatosBean) {
        this.filtrosMandatos = extEnvFiltrosMandatosBean;
        this.listaCuentas = extEnvListaCuentasBean;
        this.aceptaTyC = str;
        this.listaLeyendas = extEnvListaLeyendasBean;
        this.datosExtEnv = extEnvDatosBean;
    }
}
