package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaPTBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class CnsDeudaPTRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public CnsDeudaPTBodyRequestBean cnsDeudaPTBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public CnsDeudaPTRequestBean() {
        this.headerBean = new HeaderBean();
        this.cnsDeudaPTBodyRequestBean = new CnsDeudaPTBodyRequestBean();
    }

    public CnsDeudaPTRequestBean(CnsDeudaPTBodyRequestBean cnsDeudaPTBodyRequestBean2) {
        this.cnsDeudaPTBodyRequestBean = cnsDeudaPTBodyRequestBean2;
    }

    public CnsDeudaPTRequestBean(HeaderBean headerBean2, CnsDeudaPTBodyRequestBean cnsDeudaPTBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.cnsDeudaPTBodyRequestBean = cnsDeudaPTBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
