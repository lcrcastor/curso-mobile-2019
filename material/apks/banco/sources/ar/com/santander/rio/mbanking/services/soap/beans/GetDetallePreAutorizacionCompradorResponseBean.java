package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetallePreAutorizacionCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetallePreAutorizacionCompradorResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private GetDetallePreAutorizacionCompradorBodyResponseBean getDetallePreAutorizacionCompradorBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetDetallePreAutorizacionCompradorResponseBean() {
    }

    public GetDetallePreAutorizacionCompradorResponseBean(PrivateHeaderBean privateHeaderBean, GetDetallePreAutorizacionCompradorBodyResponseBean getDetallePreAutorizacionCompradorBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.getDetallePreAutorizacionCompradorBodyResponseBean = getDetallePreAutorizacionCompradorBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.getDetallePreAutorizacionCompradorBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetDetallePreAutorizacionCompradorBodyResponseBean getGetDetallePreAutorizacionCompradorBodyResponseBean() {
        return this.getDetallePreAutorizacionCompradorBodyResponseBean;
    }

    public void setGetDetallePreAutorizacionCompradorBodyResponseBean(GetDetallePreAutorizacionCompradorBodyResponseBean getDetallePreAutorizacionCompradorBodyResponseBean2) {
        this.getDetallePreAutorizacionCompradorBodyResponseBean = getDetallePreAutorizacionCompradorBodyResponseBean2;
    }
}
