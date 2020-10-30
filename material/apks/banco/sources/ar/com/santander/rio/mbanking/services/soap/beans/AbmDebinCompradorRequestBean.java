package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinCompradorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class AbmDebinCompradorRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private AbmDebinCompradorBodyRequestBean abmDebinCompradorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public AbmDebinCompradorRequestBean() {
    }

    public AbmDebinCompradorRequestBean(PrivateHeaderBean privateHeaderBean, AbmDebinCompradorBodyRequestBean abmDebinCompradorBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.abmDebinCompradorBodyRequestBean = abmDebinCompradorBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public AbmDebinCompradorBodyRequestBean getAbmDebinCompradorBodyRequestBean() {
        return this.abmDebinCompradorBodyRequestBean;
    }

    public void setAbmDebinCompradorBodyRequestBean(AbmDebinCompradorBodyRequestBean abmDebinCompradorBodyRequestBean2) {
        this.abmDebinCompradorBodyRequestBean = abmDebinCompradorBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
