package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleDebinVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetalleDebinVendedorResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private GetDetalleDebinVendedorBodyResponseBean getDetalleDebinVendedorBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetDetalleDebinVendedorResponseBean(GetDetalleDebinVendedorBodyResponseBean getDetalleDebinVendedorBodyResponseBean2) {
        this.getDetalleDebinVendedorBodyResponseBean = getDetalleDebinVendedorBodyResponseBean2;
    }

    public GetDetalleDebinVendedorResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getDetalleDebinVendedorBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetDetalleDebinVendedorBodyResponseBean getDetalleDebinVendedorBodyResponseBean() {
        return this.getDetalleDebinVendedorBodyResponseBean;
    }

    public void setGetDetalleDebinVendedorBodyResponseBean(GetDetalleDebinVendedorBodyResponseBean getDetalleDebinVendedorBodyResponseBean2) {
        this.getDetalleDebinVendedorBodyResponseBean = getDetalleDebinVendedorBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
