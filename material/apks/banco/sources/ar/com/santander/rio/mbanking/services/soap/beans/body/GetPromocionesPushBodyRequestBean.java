package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetPromocionesPushBodyRequestBean {
    @SerializedName("id")

    /* renamed from: id reason: collision with root package name */
    public String f261id;
    @SerializedName("location")
    public LocalizacionBean localizacionPromocionesBean;

    public LocalizacionBean getLocalizacionPromocionesBean() {
        return this.localizacionPromocionesBean;
    }

    public void setLocalizacionPromocionesBean(LocalizacionBean localizacionBean) {
        this.localizacionPromocionesBean = localizacionBean;
    }

    public String getId() {
        return this.f261id;
    }

    public void setId(String str) {
        this.f261id = str;
    }
}
