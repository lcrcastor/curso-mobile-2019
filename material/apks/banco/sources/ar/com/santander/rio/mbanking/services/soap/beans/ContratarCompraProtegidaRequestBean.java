package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ContratarCompraProtegidaRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ContratarCompraProtegidaBodyRequestBean contratarCompraProtegidaBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public ContratarCompraProtegidaRequestBean() {
    }

    public ContratarCompraProtegidaRequestBean(PrivateHeaderBean privateHeaderBean, ContratarCompraProtegidaBodyRequestBean contratarCompraProtegidaBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.contratarCompraProtegidaBodyRequestBean = contratarCompraProtegidaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
