package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CheckVersionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class CheckVersionResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public CheckVersionBodyResponseBean checkVersionBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public CheckVersionResponseBean() {
        this.headerBean = new HeaderBean();
        this.checkVersionBodyResponseBean = new CheckVersionBodyResponseBean();
    }

    public CheckVersionResponseBean(CheckVersionBodyResponseBean checkVersionBodyResponseBean2) {
        this.checkVersionBodyResponseBean = checkVersionBodyResponseBean2;
    }

    public CheckVersionResponseBean(HeaderBean headerBean2, CheckVersionBodyResponseBean checkVersionBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.checkVersionBodyResponseBean = checkVersionBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.checkVersionBodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public CheckVersionBodyResponseBean getCheckVersionBodyResponseBean() {
        return this.checkVersionBodyResponseBean;
    }

    public void setCheckVersionBodyResponseBean(CheckVersionBodyResponseBean checkVersionBodyResponseBean2) {
        this.checkVersionBodyResponseBean = checkVersionBodyResponseBean2;
    }
}
