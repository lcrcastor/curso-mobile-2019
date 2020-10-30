package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetPromocionesPushBodyResponseBean {
    @SerializedName("datosPromocion")
    public PromocionSucursalBean promocionBean;

    public PromocionSucursalBean getPromocionBean() {
        return this.promocionBean;
    }

    public void setPromocionBean(PromocionSucursalBean promocionSucursalBean) {
        this.promocionBean = promocionSucursalBean;
    }
}
