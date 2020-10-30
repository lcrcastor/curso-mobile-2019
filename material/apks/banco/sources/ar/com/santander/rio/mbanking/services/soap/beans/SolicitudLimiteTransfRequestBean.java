package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudLimiteTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class SolicitudLimiteTransfRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    private PrivateHeaderBean headerBean;
    @SerializedName("body")
    private SolicitudLimiteTransfBodyRequestBean solicitudLimiteTransfBodyRequestBean;

    public SolicitudLimiteTransfRequestBean() {
    }

    public SolicitudLimiteTransfRequestBean(PrivateHeaderBean privateHeaderBean, SolicitudLimiteTransfBodyRequestBean solicitudLimiteTransfBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.solicitudLimiteTransfBodyRequestBean = solicitudLimiteTransfBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public SolicitudLimiteTransfBodyRequestBean getSolicitudLimiteTransfBodyRequestBean() {
        return this.solicitudLimiteTransfBodyRequestBean;
    }

    public void setSolicitudLimiteTransfBodyRequestBean(SolicitudLimiteTransfBodyRequestBean solicitudLimiteTransfBodyRequestBean2) {
        this.solicitudLimiteTransfBodyRequestBean = solicitudLimiteTransfBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
