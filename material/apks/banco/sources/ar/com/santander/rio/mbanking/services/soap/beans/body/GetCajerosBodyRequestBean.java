package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetCajerosBodyRequestBean {
    @SerializedName("location")
    public LocalizacionBean localizacionCajerosBean;
    public String pagina;
    @SerializedName("valores")
    public ValoresBean valoresCajerosBean;

    public GetCajerosBodyRequestBean(String str, LocalizacionBean localizacionBean, ValoresBean valoresBean) {
        this.pagina = str;
        this.localizacionCajerosBean = localizacionBean;
        this.valoresCajerosBean = valoresBean;
    }

    public GetCajerosBodyRequestBean() {
    }
}
