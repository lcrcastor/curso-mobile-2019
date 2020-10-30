package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetalleFondoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GetDetalleFondoBodyResponseBean getDetalleFondoBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GetDetalleFondoResponseBean(GetDetalleFondoBodyResponseBean getDetalleFondoBodyResponseBean2) {
        this.getDetalleFondoBodyResponseBean = getDetalleFondoBodyResponseBean2;
    }

    public GetDetalleFondoResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getDetalleFondoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetDetalleFondoBodyResponseBean getGetDetalleFondoBodyResponseBean() {
        return this.getDetalleFondoBodyResponseBean;
    }

    public void setGetDetalleFondoBodyResponseBean(GetDetalleFondoBodyResponseBean getDetalleFondoBodyResponseBean2) {
        this.getDetalleFondoBodyResponseBean = getDetalleFondoBodyResponseBean2;
    }
}
