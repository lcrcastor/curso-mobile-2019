package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetPreAutorizacionesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetPreAutorizacionesBodyRequestBean getPreAutorizacionesBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    /* access modifiers changed from: 0000 */
    public void GetPreAutorizacionesRequestBean() {
    }

    public GetPreAutorizacionesRequestBean(PrivateHeaderBean privateHeaderBean, GetPreAutorizacionesBodyRequestBean getPreAutorizacionesBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getPreAutorizacionesBodyRequestBean = getPreAutorizacionesBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetPreAutorizacionesBodyRequestBean getGetPreAutorizacionesBodyRequestBean() {
        return this.getPreAutorizacionesBodyRequestBean;
    }

    public void setGetPreAutorizacionesBodyRequestBean(GetPreAutorizacionesBodyRequestBean getPreAutorizacionesBodyRequestBean2) {
        this.getPreAutorizacionesBodyRequestBean = getPreAutorizacionesBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
