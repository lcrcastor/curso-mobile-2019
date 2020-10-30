package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmAdhesionVendedorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class AbmAdhesionVendedorRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private AbmAdhesionVendedorBodyRequestBean abmAdhesionVendedorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public AbmAdhesionVendedorRequestBean(PrivateHeaderBean privateHeaderBean, AbmAdhesionVendedorBodyRequestBean abmAdhesionVendedorBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.abmAdhesionVendedorBodyRequestBean = abmAdhesionVendedorBodyRequestBean2;
    }

    public AbmAdhesionVendedorRequestBean() {
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public AbmAdhesionVendedorBodyRequestBean getAbmAdhesionVendedorBodyRequestBean() {
        return this.abmAdhesionVendedorBodyRequestBean;
    }

    public void setAbmAdhesionVendedorBodyRequestBean(AbmAdhesionVendedorBodyRequestBean abmAdhesionVendedorBodyRequestBean2) {
        this.abmAdhesionVendedorBodyRequestBean = abmAdhesionVendedorBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
