package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class SimulacionDolarBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cotizacionDolar")
    public String cotizacionDolar;
    @SerializedName("importeAAcreditar")
    public String importeAAcreditar;
    @SerializedName("importeADebitar")
    public String importeADebitar;
    @SerializedName("legales1")
    public String legales1;
    @SerializedName("monedaImporteAAcreditar")
    public String monedaImporteAAcreditar;
    @SerializedName("monedaImporteADebitar")
    public String monedaImporteADebitar;

    public SimulacionDolarBodyResponseBean(String str, String str2, String str3, String str4) {
        this.importeAAcreditar = str;
        this.importeADebitar = str2;
        this.cotizacionDolar = str3;
        this.legales1 = str4;
    }
}
