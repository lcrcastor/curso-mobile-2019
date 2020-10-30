package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmAdhesionVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class AbmAdhesionVendedorResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private AbmAdhesionVendedorBodyResponseBean abmAdhesionVendedorBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public AbmAdhesionVendedorBodyResponseBean getAbmAdhesionVendedorBodyResponseBean() {
        return this.abmAdhesionVendedorBodyResponseBean;
    }

    public void setAbmAdhesionVendedorBodyResponseBean(AbmAdhesionVendedorBodyResponseBean abmAdhesionVendedorBodyResponseBean2) {
        this.abmAdhesionVendedorBodyResponseBean = abmAdhesionVendedorBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.abmAdhesionVendedorBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
