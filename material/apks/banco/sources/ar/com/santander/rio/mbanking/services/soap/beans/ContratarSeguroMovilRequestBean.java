package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ContratarSeguroMovilRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ContratarSeguroMovilBodyRequestBean contratarSeguroMovilBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public ContratarSeguroMovilRequestBean() {
    }

    public ContratarSeguroMovilRequestBean(PrivateHeaderBean privateHeaderBean, ContratarSeguroMovilBodyRequestBean contratarSeguroMovilBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.contratarSeguroMovilBodyRequestBean = contratarSeguroMovilBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
