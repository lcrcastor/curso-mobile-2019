package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSucursalesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetSucursalesResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetSucursalesBodyResponseBean getSucursalesBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetSucursalesResponseBean() {
        this.headerBean = new HeaderBean();
        this.getSucursalesBodyResponseBean = new GetSucursalesBodyResponseBean();
    }

    public GetSucursalesResponseBean(GetSucursalesBodyResponseBean getSucursalesBodyResponseBean2) {
        this.getSucursalesBodyResponseBean = getSucursalesBodyResponseBean2;
    }

    public GetSucursalesResponseBean(HeaderBean headerBean2, GetSucursalesBodyResponseBean getSucursalesBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.getSucursalesBodyResponseBean = getSucursalesBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.getSucursalesBodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public GetSucursalesBodyResponseBean getGetSucursalesBodyResponseBean() {
        return this.getSucursalesBodyResponseBean;
    }

    public void setGetSucursalesBodyResponseBean(GetSucursalesBodyResponseBean getSucursalesBodyResponseBean2) {
        this.getSucursalesBodyResponseBean = getSucursalesBodyResponseBean2;
    }
}
