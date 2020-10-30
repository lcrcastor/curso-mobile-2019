package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class AbmDebinCompradorResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public AbmDebinCompradorResponseBean(PrivateHeaderBean privateHeaderBean, AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.abmDebinCompradorBodyResponseBean = abmDebinCompradorBodyResponseBean2;
    }

    public AbmDebinCompradorResponseBean() {
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public AbmDebinCompradorBodyResponseBean getAbmDebinCompradorBodyResponseBean() {
        return this.abmDebinCompradorBodyResponseBean;
    }

    public void setAbmDebinCompradorBodyResponseBean(AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean2) {
        this.abmDebinCompradorBodyResponseBean = abmDebinCompradorBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.abmDebinCompradorBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
