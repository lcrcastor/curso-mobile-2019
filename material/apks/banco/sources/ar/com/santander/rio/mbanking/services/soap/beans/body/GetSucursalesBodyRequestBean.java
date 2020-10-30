package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetSucursalesBodyRequestBean {
    @SerializedName("location")
    public LocalizacionBean localizacionSucursalesBean;
    public String pagina;
    public String textoBuscar;
    @SerializedName("ubicacion")
    public UbicacionBean ubicacion;
    @SerializedName("valores")
    public ValoresBean valoresSucursalesBean;

    public GetSucursalesBodyRequestBean() {
    }

    public GetSucursalesBodyRequestBean(String str, String str2, LocalizacionBean localizacionBean, ValoresBean valoresBean) {
        this.pagina = str2;
        this.textoBuscar = str;
        this.localizacionSucursalesBean = localizacionBean;
        this.valoresSucursalesBean = valoresBean;
    }

    public GetSucursalesBodyRequestBean(String str, String str2, UbicacionBean ubicacionBean) {
        this.pagina = str;
        this.textoBuscar = str2;
        this.ubicacion = ubicacionBean;
    }
}
