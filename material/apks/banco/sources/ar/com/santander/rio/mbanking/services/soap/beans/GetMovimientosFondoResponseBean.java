package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetMovimientosFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetMovimientosFondoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetMovimientosFondoBodyResponseBean getMovimientosFondoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetMovimientosFondoResponseBean(GetMovimientosFondoBodyResponseBean getMovimientosFondoBodyResponseBean2) {
        this.getMovimientosFondoBodyResponseBean = getMovimientosFondoBodyResponseBean2;
    }

    public GetMovimientosFondoResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getMovimientosFondoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetMovimientosFondoBodyResponseBean getGetMovimientosFondoBodyResponseBean() {
        return this.getMovimientosFondoBodyResponseBean;
    }

    public void setGetMovimientosFondoBodyResponseBean(GetMovimientosFondoBodyResponseBean getMovimientosFondoBodyResponseBean2) {
        this.getMovimientosFondoBodyResponseBean = getMovimientosFondoBodyResponseBean2;
    }
}
