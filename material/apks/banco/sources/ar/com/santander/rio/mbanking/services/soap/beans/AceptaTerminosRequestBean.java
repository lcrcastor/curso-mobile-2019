package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AceptaTerminosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class AceptaTerminosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public AceptaTerminosBodyRequestBean aceptaTerminosBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public AceptaTerminosRequestBean(PrivateHeaderBean privateHeaderBean, AceptaTerminosBodyRequestBean aceptaTerminosBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.aceptaTerminosBodyRequestBean = aceptaTerminosBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
