package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleDebinCompradorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetalleDebinCompradorRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetDetalleDebinCompradorBodyRequestBean getDetalleDebinCompradorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetDetalleDebinCompradorRequestBean() {
    }

    public GetDetalleDebinCompradorRequestBean(PrivateHeaderBean privateHeaderBean, GetDetalleDebinCompradorBodyRequestBean getDetalleDebinCompradorBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getDetalleDebinCompradorBodyRequestBean = getDetalleDebinCompradorBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetDetalleDebinCompradorBodyRequestBean getGetDetalleDebinCompradorBodyRequestBean() {
        return this.getDetalleDebinCompradorBodyRequestBean;
    }

    public void setGetDetalleDebinCompradorBodyRequestBean(GetDetalleDebinCompradorBodyRequestBean getDetalleDebinCompradorBodyRequestBean2) {
        this.getDetalleDebinCompradorBodyRequestBean = getDetalleDebinCompradorBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
