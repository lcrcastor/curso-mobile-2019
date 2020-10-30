package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class UltimoResumenTCResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public UltimoResumenTCBodyResponseBean body;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public UltimoResumenTCResponseBean(PrivateHeaderBean privateHeaderBean, UltimoResumenTCBodyResponseBean ultimoResumenTCBodyResponseBean) {
        this.header = privateHeaderBean;
        this.body = ultimoResumenTCBodyResponseBean;
    }

    public UltimoResumenTCResponseBean() {
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

    public UltimoResumenTCBodyResponseBean getBody() {
        return this.body;
    }

    public void setBody(UltimoResumenTCBodyResponseBean ultimoResumenTCBodyResponseBean) {
        this.body = ultimoResumenTCBodyResponseBean;
    }
}
