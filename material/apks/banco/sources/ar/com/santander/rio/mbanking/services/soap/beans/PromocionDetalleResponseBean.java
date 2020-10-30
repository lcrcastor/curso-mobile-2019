package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionDetalleBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class PromocionDetalleResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public PromocionDetalleBodyResponseBean getPromocionDetalleBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public PromocionDetalleResponseBean() {
        this.headerBean = new HeaderBean();
        this.getPromocionDetalleBodyResponseBean = new PromocionDetalleBodyResponseBean();
    }

    public PromocionDetalleResponseBean(PromocionDetalleBodyResponseBean promocionDetalleBodyResponseBean) {
        this.getPromocionDetalleBodyResponseBean = promocionDetalleBodyResponseBean;
    }

    public PromocionDetalleResponseBean(HeaderBean headerBean2, PromocionDetalleBodyResponseBean promocionDetalleBodyResponseBean) {
        this.headerBean = headerBean2;
        this.getPromocionDetalleBodyResponseBean = promocionDetalleBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getPromocionDetalleBodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }
}
