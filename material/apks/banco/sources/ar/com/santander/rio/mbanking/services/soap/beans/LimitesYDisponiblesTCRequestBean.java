package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class LimitesYDisponiblesTCRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private LimitesYDisponiblesTCBodyRequestBean body;
    @SerializedName("header")
    private PrivateHeaderBean header;

    public LimitesYDisponiblesTCRequestBean(PrivateHeaderBean privateHeaderBean, LimitesYDisponiblesTCBodyRequestBean limitesYDisponiblesTCBodyRequestBean) {
        this.header = privateHeaderBean;
        this.body = limitesYDisponiblesTCBodyRequestBean;
    }

    public LimitesYDisponiblesTCRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public LimitesYDisponiblesTCBodyRequestBean getBody() {
        return this.body;
    }

    public void setBody(LimitesYDisponiblesTCBodyRequestBean limitesYDisponiblesTCBodyRequestBean) {
        this.body = limitesYDisponiblesTCBodyRequestBean;
    }
}
