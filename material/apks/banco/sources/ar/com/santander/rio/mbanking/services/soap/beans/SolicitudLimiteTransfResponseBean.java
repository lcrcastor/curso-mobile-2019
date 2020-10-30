package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudLimiteTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeader;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class SolicitudLimiteTransfResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeader headerBean;
    @SerializedName("body")
    public SolicitudLimiteTransfBodyResponseBean solicitudLimiteTransfBodyResponseBean;

    public SolicitudLimiteTransfResponseBean() {
    }

    public SolicitudLimiteTransfResponseBean(PrivateHeader privateHeader, SolicitudLimiteTransfBodyResponseBean solicitudLimiteTransfBodyResponseBean2) {
        this.headerBean = privateHeader;
        this.solicitudLimiteTransfBodyResponseBean = solicitudLimiteTransfBodyResponseBean2;
    }

    public PrivateHeader getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeader privateHeader) {
        this.headerBean = privateHeader;
    }

    public SolicitudLimiteTransfBodyResponseBean getSolicitudLimiteTransfBodyResponseBean() {
        return this.solicitudLimiteTransfBodyResponseBean;
    }

    public void setSolicitudLimiteTransfBodyResponseBean(SolicitudLimiteTransfBodyResponseBean solicitudLimiteTransfBodyResponseBean2) {
        this.solicitudLimiteTransfBodyResponseBean = solicitudLimiteTransfBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.solicitudLimiteTransfBodyResponseBean;
    }
}
