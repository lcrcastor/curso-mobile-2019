package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroAccidenteBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ContratarSeguroAccidenteRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ContratarSeguroAccidenteBodyRequestBean contratarSeguroAccidenteBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public ContratarSeguroAccidenteRequestBean() {
    }

    public ContratarSeguroAccidenteRequestBean(PrivateHeaderBean privateHeaderBean, ContratarSeguroAccidenteBodyRequestBean contratarSeguroAccidenteBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.contratarSeguroAccidenteBodyRequestBean = contratarSeguroAccidenteBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
