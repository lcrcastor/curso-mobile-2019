package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class TransferirFondoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    private PrivateHeaderBean headerBean;
    @SerializedName("body")
    private TransferirFondoBodyRequestBean transferirFondoBodyRequestBean;

    public TransferirFondoRequestBean(PrivateHeaderBean privateHeaderBean, TransferirFondoBodyRequestBean transferirFondoBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.transferirFondoBodyRequestBean = transferirFondoBodyRequestBean2;
    }

    public TransferirFondoRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
