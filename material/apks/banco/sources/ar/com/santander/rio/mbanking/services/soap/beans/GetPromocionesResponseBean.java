package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetPromocionesResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetPromocionesBodyResponseBean getPromocionesBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetPromocionesResponseBean() {
        this.headerBean = new HeaderBean();
        this.getPromocionesBodyResponseBean = new GetPromocionesBodyResponseBean();
    }

    public GetPromocionesResponseBean(GetPromocionesBodyResponseBean getPromocionesBodyResponseBean2) {
        this.getPromocionesBodyResponseBean = getPromocionesBodyResponseBean2;
    }

    public GetPromocionesResponseBean(HeaderBean headerBean2, GetPromocionesBodyResponseBean getPromocionesBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.getPromocionesBodyResponseBean = getPromocionesBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getPromocionesBodyResponseBean;
    }

    public GetPromocionesBodyResponseBean getGetPromocionesBodyResponseBean() {
        return this.getPromocionesBodyResponseBean;
    }

    public void setGetPromocionesBodyResponseBean(GetPromocionesBodyResponseBean getPromocionesBodyResponseBean2) {
        this.getPromocionesBodyResponseBean = getPromocionesBodyResponseBean2;
    }
}
