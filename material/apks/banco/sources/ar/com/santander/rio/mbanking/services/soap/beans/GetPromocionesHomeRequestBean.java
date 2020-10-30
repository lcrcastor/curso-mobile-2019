package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesHomeBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetPromocionesHomeRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetPromocionesHomeBodyRequestBean getPromocionesHomeBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetPromocionesHomeRequestBean() {
        this.headerBean = new HeaderBean();
        this.getPromocionesHomeBodyRequestBean = new GetPromocionesHomeBodyRequestBean();
    }

    public GetPromocionesHomeRequestBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public GetPromocionesHomeBodyRequestBean getGetPromocionesHomeBodyRequestBean() {
        return this.getPromocionesHomeBodyRequestBean;
    }

    public void setGetPromocionesHomeBodyRequestBean(GetPromocionesHomeBodyRequestBean getPromocionesHomeBodyRequestBean2) {
        this.getPromocionesHomeBodyRequestBean = getPromocionesHomeBodyRequestBean2;
        new GetPromocionesHomeBodyRequestBean();
    }

    public Class getClassBean() {
        return getClass();
    }
}
