package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConsultaMandatosExtEnvBodyRequestBean {
    @SerializedName("filtros")
    public ExtEnvFiltroBean filtros;
    @SerializedName("tipoFiltro")
    public String tipoFiltro;
    @SerializedName("tipoOperacion")
    public String tipoOperacion;

    public ConsultaMandatosExtEnvBodyRequestBean() {
    }

    public ConsultaMandatosExtEnvBodyRequestBean(String str, String str2, ExtEnvFiltroBean extEnvFiltroBean) {
        this.tipoOperacion = str;
        this.tipoFiltro = str2;
        this.filtros = extEnvFiltroBean;
    }
}
