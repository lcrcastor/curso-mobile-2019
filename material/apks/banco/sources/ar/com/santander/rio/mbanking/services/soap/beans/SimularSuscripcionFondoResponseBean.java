package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularSuscripcionFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class SimularSuscripcionFondoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean;

    public Class getClassBean() {
        return null;
    }

    public Object getErrorBeanObject() {
        return null;
    }

    public SimularSuscripcionFondoResponseBean(SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean2) {
        this.simularSuscripcionFondoBodyResponseBean = simularSuscripcionFondoBodyResponseBean2;
    }

    public SimularSuscripcionFondoResponseBean() {
    }

    public SimularSuscripcionFondoBodyResponseBean getSimularSuscripcionFondoBodyResponseBean() {
        return this.simularSuscripcionFondoBodyResponseBean;
    }

    public void setSimularSuscripcionFondoBodyResponseBean(SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean2) {
        this.simularSuscripcionFondoBodyResponseBean = simularSuscripcionFondoBodyResponseBean2;
    }
}
