package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmPreautorizacionCompradorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class AbmPreautorizacionCompradorRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private AbmPreautorizacionCompradorBodyRequestBean abmPreautorizacionCompradorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public AbmPreautorizacionCompradorRequestBean() {
    }

    public AbmPreautorizacionCompradorRequestBean(PrivateHeaderBean privateHeaderBean, AbmPreautorizacionCompradorBodyRequestBean abmPreautorizacionCompradorBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.abmPreautorizacionCompradorBodyRequestBean = abmPreautorizacionCompradorBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public AbmPreautorizacionCompradorBodyRequestBean getAbmPreautorizacionCompradorRequestBean() {
        return this.abmPreautorizacionCompradorBodyRequestBean;
    }

    public void setAbmPreautorizacionCompradorRequestBean(AbmPreautorizacionCompradorBodyRequestBean abmPreautorizacionCompradorBodyRequestBean2) {
        this.abmPreautorizacionCompradorBodyRequestBean = abmPreautorizacionCompradorBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
