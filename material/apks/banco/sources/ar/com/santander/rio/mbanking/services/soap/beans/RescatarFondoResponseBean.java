package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class RescatarFondoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean;

    public RescatarFondoResponseBean(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean2) {
        this.rescatarFondoBodyResponseBean = rescatarFondoBodyResponseBean2;
    }

    public RescatarFondoResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.rescatarFondoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public RescatarFondoBodyResponseBean getRescatarFondoBodyResponseBean() {
        return this.rescatarFondoBodyResponseBean;
    }

    public void setRescatarFondoBodyResponseBean(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean2) {
        this.rescatarFondoBodyResponseBean = rescatarFondoBodyResponseBean2;
    }
}
