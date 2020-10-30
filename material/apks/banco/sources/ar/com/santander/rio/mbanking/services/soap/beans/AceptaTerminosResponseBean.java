package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class AceptaTerminosResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ErrorBodyBean errorBodyBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public AceptaTerminosResponseBean(HeaderBean headerBean2, ErrorBodyBean errorBodyBean2) {
        this.headerBean = headerBean2;
        this.errorBodyBean = errorBodyBean2;
    }

    public AceptaTerminosResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.errorBodyBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }
}
