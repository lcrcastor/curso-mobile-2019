package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ContratarSeguroObjetoResquestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ContratarSeguroObjetoBodyRequestBean contratarSeguroObjetoBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public ContratarSeguroObjetoResquestBean() {
    }

    public ContratarSeguroObjetoResquestBean(PrivateHeaderBean privateHeaderBean, ContratarSeguroObjetoBodyRequestBean contratarSeguroObjetoBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.contratarSeguroObjetoBodyRequestBean = contratarSeguroObjetoBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
