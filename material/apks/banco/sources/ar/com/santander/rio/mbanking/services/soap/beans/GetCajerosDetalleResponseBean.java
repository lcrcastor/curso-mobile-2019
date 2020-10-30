package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosDetalleBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetCajerosDetalleResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCajerosDetalleBodyResponseBean getCajerosDetalleBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetCajerosDetalleResponseBean() {
        this.headerBean = new HeaderBean();
        this.getCajerosDetalleBodyResponseBean = new GetCajerosDetalleBodyResponseBean();
    }

    public GetCajerosDetalleResponseBean(GetCajerosDetalleBodyResponseBean getCajerosDetalleBodyResponseBean2) {
        this.getCajerosDetalleBodyResponseBean = getCajerosDetalleBodyResponseBean2;
    }

    public GetCajerosDetalleResponseBean(HeaderBean headerBean2, GetCajerosDetalleBodyResponseBean getCajerosDetalleBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.getCajerosDetalleBodyResponseBean = getCajerosDetalleBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getCajerosDetalleBodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public GetCajerosDetalleBodyResponseBean getGetCajerosDetalleBodyResponseBean() {
        return this.getCajerosDetalleBodyResponseBean;
    }

    public void setGetCajerosDetalleBodyResponseBean(GetCajerosDetalleBodyResponseBean getCajerosDetalleBodyResponseBean2) {
        this.getCajerosDetalleBodyResponseBean = getCajerosDetalleBodyResponseBean2;
    }
}
