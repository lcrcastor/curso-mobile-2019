package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class PromocionDetalleBodyResponseBean extends ErrorBodyBean {
    @SerializedName("datosPromocion")
    public DatosPromocion datosPromocion;

    /* renamed from: id reason: collision with root package name */
    public String f266id;

    public PromocionDetalleBodyResponseBean(String str, DatosPromocion datosPromocion2) {
        this.f266id = str;
        this.datosPromocion = this.datosPromocion;
    }

    public PromocionDetalleBodyResponseBean() {
    }
}
