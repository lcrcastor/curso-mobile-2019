package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetalleCuotaTenenciaCreditoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    private GetDetalleCuotaTenenciaCreditoBodyResponseBean mGetDetalleCuotaTenenciaCreditoBodyResponseBean;

    public GetDetalleCuotaTenenciaCreditoResponseBean() {
        this.headerBean = new HeaderBean();
        this.mGetDetalleCuotaTenenciaCreditoBodyResponseBean = new GetDetalleCuotaTenenciaCreditoBodyResponseBean();
    }

    public GetDetalleCuotaTenenciaCreditoResponseBean(GetDetalleCuotaTenenciaCreditoBodyResponseBean getDetalleCuotaTenenciaCreditoBodyResponseBean) {
        this.mGetDetalleCuotaTenenciaCreditoBodyResponseBean = getDetalleCuotaTenenciaCreditoBodyResponseBean;
    }

    public GetDetalleCuotaTenenciaCreditoResponseBean(HeaderBean headerBean2, GetDetalleCuotaTenenciaCreditoBodyResponseBean getDetalleCuotaTenenciaCreditoBodyResponseBean) {
        this.headerBean = headerBean2;
        this.mGetDetalleCuotaTenenciaCreditoBodyResponseBean = getDetalleCuotaTenenciaCreditoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.mGetDetalleCuotaTenenciaCreditoBodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public GetDetalleCuotaTenenciaCreditoBodyResponseBean getDetalleCuotaTenenciaCreditoBodyResponseBean() {
        return this.mGetDetalleCuotaTenenciaCreditoBodyResponseBean;
    }

    public void setGetCajerosDetalleBodyResponseBean(GetDetalleCuotaTenenciaCreditoBodyResponseBean getDetalleCuotaTenenciaCreditoBodyResponseBean) {
        this.mGetDetalleCuotaTenenciaCreditoBodyResponseBean = getDetalleCuotaTenenciaCreditoBodyResponseBean;
    }
}
