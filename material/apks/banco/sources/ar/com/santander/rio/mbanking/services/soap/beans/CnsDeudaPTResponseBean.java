package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaPTBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class CnsDeudaPTResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CnsDeudaPTBodyResponseBean cnsDeudaPTBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public Class getClassBean() {
        return null;
    }

    public Object getErrorBeanObject() {
        return null;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public CnsDeudaPTBodyResponseBean getCnsDeudaPTBodyResponseBean() {
        return this.cnsDeudaPTBodyResponseBean;
    }

    public void setCnsDeudaPTBodyResponseBean(CnsDeudaPTBodyResponseBean cnsDeudaPTBodyResponseBean2) {
        this.cnsDeudaPTBodyResponseBean = cnsDeudaPTBodyResponseBean2;
    }
}
