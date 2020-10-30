package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetCotizacionCompraProtegidaBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cotizacion")
    private CotizacionBean cotizacion;

    public GetCotizacionCompraProtegidaBodyResponseBean() {
    }

    public GetCotizacionCompraProtegidaBodyResponseBean(CotizacionBean cotizacionBean) {
        this.cotizacion = cotizacionBean;
    }

    public CotizacionBean getCotizacion() {
        return this.cotizacion;
    }

    public void setCotizacion(CotizacionBean cotizacionBean) {
        this.cotizacion = cotizacionBean;
    }
}
