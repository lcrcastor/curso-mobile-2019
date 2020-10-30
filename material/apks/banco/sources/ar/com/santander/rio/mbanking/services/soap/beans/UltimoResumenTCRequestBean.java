package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class UltimoResumenTCRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private UltimoResumenTCBodyRequestBean body;
    @SerializedName("header")
    private PrivateHeaderBean header;

    public UltimoResumenTCRequestBean(PrivateHeaderBean privateHeaderBean, UltimoResumenTCBodyRequestBean ultimoResumenTCBodyRequestBean) {
        this.header = privateHeaderBean;
        this.body = ultimoResumenTCBodyRequestBean;
    }

    public UltimoResumenTCRequestBean() {
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

    public UltimoResumenTCBodyRequestBean getBody() {
        return this.body;
    }

    public void setBody(UltimoResumenTCBodyRequestBean ultimoResumenTCBodyRequestBean) {
        this.body = ultimoResumenTCBodyRequestBean;
    }
}
