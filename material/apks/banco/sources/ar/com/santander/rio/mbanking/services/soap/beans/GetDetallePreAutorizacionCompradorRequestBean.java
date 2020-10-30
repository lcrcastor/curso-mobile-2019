package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetallePreAutorizacionCompradorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetallePreAutorizacionCompradorRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetDetallePreAutorizacionCompradorBodyRequestBean getDetallePreAutorizacionCompradorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetDetallePreAutorizacionCompradorRequestBean() {
    }

    public GetDetallePreAutorizacionCompradorRequestBean(PrivateHeaderBean privateHeaderBean, GetDetallePreAutorizacionCompradorBodyRequestBean getDetallePreAutorizacionCompradorBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getDetallePreAutorizacionCompradorBodyRequestBean = getDetallePreAutorizacionCompradorBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetDetallePreAutorizacionCompradorBodyRequestBean getGetDetallePreAutorizacionCompradorBodyRequestBean() {
        return this.getDetallePreAutorizacionCompradorBodyRequestBean;
    }

    public void setGetDetallePreAutorizacionCompradorBodyRequestBean(GetDetallePreAutorizacionCompradorBodyRequestBean getDetallePreAutorizacionCompradorBodyRequestBean2) {
        this.getDetallePreAutorizacionCompradorBodyRequestBean = getDetallePreAutorizacionCompradorBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
