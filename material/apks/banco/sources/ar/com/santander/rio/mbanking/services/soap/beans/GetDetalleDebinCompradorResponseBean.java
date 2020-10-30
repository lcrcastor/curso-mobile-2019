package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleDebinCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetalleDebinCompradorResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private GetDetalleDebinCompradorBodyResponseBean getDetalleDebinCompradorBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetDetalleDebinCompradorResponseBean(GetDetalleDebinCompradorBodyResponseBean getDetalleDebinCompradorBodyResponseBean2) {
        this.getDetalleDebinCompradorBodyResponseBean = getDetalleDebinCompradorBodyResponseBean2;
    }

    public GetDetalleDebinCompradorResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getDetalleDebinCompradorBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetDetalleDebinCompradorBodyResponseBean getDetalleDebinCompradorBodyResponseBean() {
        return this.getDetalleDebinCompradorBodyResponseBean;
    }

    public void setGetDetalleDebinCompradorBodyResponseBean(GetDetalleDebinCompradorBodyResponseBean getDetalleDebinCompradorBodyResponseBean2) {
        this.getDetalleDebinCompradorBodyResponseBean = getDetalleDebinCompradorBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
