package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class AbmDebinVendedorResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public AbmDebinVendedorResponseBean(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean2) {
        this.abmDebinVendedorBodyResponseBean = abmDebinVendedorBodyResponseBean2;
    }

    public AbmDebinVendedorResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.abmDebinVendedorBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public AbmDebinVendedorBodyResponseBean getAbmDebinVendedorBodyResponseBean() {
        return this.abmDebinVendedorBodyResponseBean;
    }

    public void setAbmDebinVendedorBodyResponseBean(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean2) {
        this.abmDebinVendedorBodyResponseBean = abmDebinVendedorBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
