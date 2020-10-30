package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetPromocionesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetPromocionesBodyRequestBean getPromocionesBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetPromocionesRequestBean() {
        this.headerBean = new HeaderBean();
        this.getPromocionesBodyRequestBean = new GetPromocionesBodyRequestBean();
    }

    public GetPromocionesRequestBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public GetPromocionesBodyRequestBean getGetPromocionesBodyRequestBean() {
        return this.getPromocionesBodyRequestBean;
    }

    public GetPromocionesRequestBean(HeaderBean headerBean2, GetPromocionesBodyRequestBean getPromocionesBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.getPromocionesBodyRequestBean = getPromocionesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
