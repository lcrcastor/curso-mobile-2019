package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetCajerosResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetCajerosBodyResponseBean getCajerosBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetCajerosResponseBean() {
        this.headerBean = new HeaderBean();
        this.getCajerosBodyResponseBean = new GetCajerosBodyResponseBean();
    }

    public GetCajerosResponseBean(GetCajerosBodyResponseBean getCajerosBodyResponseBean2) {
        this.getCajerosBodyResponseBean = getCajerosBodyResponseBean2;
    }

    public GetCajerosResponseBean(HeaderBean headerBean2, GetCajerosBodyResponseBean getCajerosBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.getCajerosBodyResponseBean = getCajerosBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getCajerosBodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public GetCajerosBodyResponseBean getGetCajerosBodyResponseBean() {
        return this.getCajerosBodyResponseBean;
    }

    public void setGetCajerosBodyResponseBean(GetCajerosBodyResponseBean getCajerosBodyResponseBean2) {
        this.getCajerosBodyResponseBean = getCajerosBodyResponseBean2;
    }
}
