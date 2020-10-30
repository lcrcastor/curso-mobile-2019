package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class LimitesYDisponiblesTCResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private LimitesYDisponiblesTCBodyResponseBean body;
    @SerializedName("header")
    private PrivateHeaderBean header;

    public LimitesYDisponiblesTCResponseBean(PrivateHeaderBean privateHeaderBean, LimitesYDisponiblesTCBodyResponseBean limitesYDisponiblesTCBodyResponseBean) {
        this.header = privateHeaderBean;
        this.body = limitesYDisponiblesTCBodyResponseBean;
    }

    public LimitesYDisponiblesTCResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.body;
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public LimitesYDisponiblesTCBodyResponseBean getBody() {
        return this.body;
    }

    public void setBody(LimitesYDisponiblesTCBodyResponseBean limitesYDisponiblesTCBodyResponseBean) {
        this.body = limitesYDisponiblesTCBodyResponseBean;
    }
}
