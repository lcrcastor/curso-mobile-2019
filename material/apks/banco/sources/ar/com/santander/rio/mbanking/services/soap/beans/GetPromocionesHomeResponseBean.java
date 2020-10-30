package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesHomeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetPromocionesHomeResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetPromocionesHomeBodyResponseBean getPromocionesHomeBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetPromocionesHomeResponseBean() {
        this.headerBean = new HeaderBean();
        this.getPromocionesHomeBodyResponseBean = new GetPromocionesHomeBodyResponseBean();
    }

    public GetPromocionesHomeResponseBean(GetPromocionesHomeBodyResponseBean getPromocionesHomeBodyResponseBean2) {
        this.getPromocionesHomeBodyResponseBean = getPromocionesHomeBodyResponseBean2;
    }

    public GetPromocionesHomeResponseBean(HeaderBean headerBean2, GetPromocionesHomeBodyResponseBean getPromocionesHomeBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.getPromocionesHomeBodyResponseBean = getPromocionesHomeBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getPromocionesHomeBodyResponseBean;
    }

    public GetPromocionesHomeBodyResponseBean getGetPromocionesHomeBodyResponseBean() {
        return this.getPromocionesHomeBodyResponseBean;
    }

    public void setGetPromocionesHomeBodyResponseBean(GetPromocionesHomeBodyResponseBean getPromocionesHomeBodyResponseBean2) {
        this.getPromocionesHomeBodyResponseBean = getPromocionesHomeBodyResponseBean2;
    }
}
