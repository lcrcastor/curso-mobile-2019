package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ExtEnvFiltrosMandatosBean {
    @SerializedName("filtroCantDias1")
    public String filtroCantDias1;
    @SerializedName("filtroCantDias2")
    public String filtroCantDias2;
    @SerializedName("filtroDefault")
    public String filtroDefault;

    public ExtEnvFiltrosMandatosBean() {
    }

    public ExtEnvFiltrosMandatosBean(String str, String str2, String str3) {
        this.filtroDefault = str;
        this.filtroCantDias2 = str2;
        this.filtroCantDias1 = str3;
    }
}
