package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CheckVersionBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class CheckVersionRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public CheckVersionBodyRequestBean checkVersionBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public CheckVersionRequestBean() {
    }

    public CheckVersionRequestBean(HeaderBean headerBean2, CheckVersionBodyRequestBean checkVersionBodyRequestBean2, String str) {
        this.headerBean = headerBean2;
        this.checkVersionBodyRequestBean = checkVersionBodyRequestBean2;
        this.headerBean.deviceHeaderBean.idBl = str;
    }

    public CheckVersionRequestBean(CheckVersionBodyRequestBean checkVersionBodyRequestBean2) {
        this.headerBean = new HeaderBean();
        this.checkVersionBodyRequestBean = checkVersionBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
