package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmPreautorizacionCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class AbmPreautorizacionCompradorResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public AbmPreautorizacionCompradorBodyResponseBean abmPreautorizacionCompradorBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public AbmPreautorizacionCompradorResponseBean(PrivateHeaderBean privateHeaderBean, AbmPreautorizacionCompradorBodyResponseBean abmPreautorizacionCompradorBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.abmPreautorizacionCompradorBodyResponseBean = abmPreautorizacionCompradorBodyResponseBean2;
    }

    public AbmPreautorizacionCompradorResponseBean() {
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public AbmPreautorizacionCompradorBodyResponseBean getAbmPreautorizacionCompradorResponseBean() {
        return this.abmPreautorizacionCompradorBodyResponseBean;
    }

    public void setAbmPreautorizacionCompradorResponseBean(AbmPreautorizacionCompradorBodyResponseBean abmPreautorizacionCompradorBodyResponseBean2) {
        this.abmPreautorizacionCompradorBodyResponseBean = abmPreautorizacionCompradorBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.abmPreautorizacionCompradorBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
