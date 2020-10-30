package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class AbmDebinVendedorRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private AbmDebinVendedorBodyRequestBean abmDebinVendedorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public AbmDebinVendedorRequestBean() {
    }

    public AbmDebinVendedorRequestBean(PrivateHeaderBean privateHeaderBean, AbmDebinVendedorBodyRequestBean abmDebinVendedorBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.abmDebinVendedorBodyRequestBean = abmDebinVendedorBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public AbmDebinVendedorBodyRequestBean getAbmDebinVendedorBodyRequestBean() {
        return this.abmDebinVendedorBodyRequestBean;
    }

    public void setAbmDebinVendedorBodyRequestBean(AbmDebinVendedorBodyRequestBean abmDebinVendedorBodyRequestBean2) {
        this.abmDebinVendedorBodyRequestBean = abmDebinVendedorBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
