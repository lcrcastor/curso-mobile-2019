package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferenciasBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class TransferenciasRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public TransferenciasBodyRequestBean transferenciasBodyRequestBean;

    public TransferenciasRequestBean(PrivateHeaderBean privateHeaderBean, TransferenciasBodyRequestBean transferenciasBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.transferenciasBodyRequestBean = transferenciasBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public TransferenciasBodyRequestBean getTransferenciasBodyRequestBean() {
        return this.transferenciasBodyRequestBean;
    }

    public void setTransferenciasBodyRequestBean(TransferenciasBodyRequestBean transferenciasBodyRequestBean2) {
        this.transferenciasBodyRequestBean = transferenciasBodyRequestBean2;
    }
}
