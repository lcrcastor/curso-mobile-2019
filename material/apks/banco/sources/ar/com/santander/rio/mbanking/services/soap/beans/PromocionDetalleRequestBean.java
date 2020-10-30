package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionDetalleBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class PromocionDetalleRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public PromocionDetalleBodyRequestBean getPromocionDetalleBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public PromocionDetalleRequestBean() {
    }

    public PromocionDetalleRequestBean(PromocionDetalleBodyRequestBean promocionDetalleBodyRequestBean) {
        this.headerBean = new HeaderBean();
        this.getPromocionDetalleBodyRequestBean = promocionDetalleBodyRequestBean;
    }

    public PromocionDetalleRequestBean(HeaderBean headerBean2, PromocionDetalleBodyRequestBean promocionDetalleBodyRequestBean) {
        this.headerBean = headerBean2;
        this.getPromocionDetalleBodyRequestBean = promocionDetalleBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
