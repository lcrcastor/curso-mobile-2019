package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleDebinVendedorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetalleDebinVendedorRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetDetalleDebinVendedorBodyRequestBean getDetalleDebinVendedorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetDetalleDebinVendedorRequestBean() {
    }

    public GetDetalleDebinVendedorRequestBean(PrivateHeaderBean privateHeaderBean, GetDetalleDebinVendedorBodyRequestBean getDetalleDebinVendedorBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getDetalleDebinVendedorBodyRequestBean = getDetalleDebinVendedorBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GetDetalleDebinVendedorBodyRequestBean getGetDetalleDebinVendedorBodyRequestBean() {
        return this.getDetalleDebinVendedorBodyRequestBean;
    }

    public void setGetDetalleDebinVendedorBodyRequestBean(GetDetalleDebinVendedorBodyRequestBean getDetalleDebinVendedorBodyRequestBean2) {
        this.getDetalleDebinVendedorBodyRequestBean = getDetalleDebinVendedorBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
