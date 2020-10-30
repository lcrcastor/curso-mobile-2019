package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularTransferenciaFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class SimularTransferenciaFondoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean;

    public SimularTransferenciaFondoResponseBean(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean2) {
        this.simularTransferenciaFondoBodyResponseBean = this.simularTransferenciaFondoBodyResponseBean;
    }

    public SimularTransferenciaFondoResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.simularTransferenciaFondoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public SimularTransferenciaFondoBodyResponseBean getSimularTransferenciaFondoBodyResponseBean() {
        return this.simularTransferenciaFondoBodyResponseBean;
    }

    public void setSimularTransferenciaFondoBodyResponseBean(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean2) {
        this.simularTransferenciaFondoBodyResponseBean = simularTransferenciaFondoBodyResponseBean2;
    }
}
