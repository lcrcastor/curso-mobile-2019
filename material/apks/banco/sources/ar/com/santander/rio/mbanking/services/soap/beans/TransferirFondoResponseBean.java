package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class TransferirFondoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public TransferirFondoBodyResponseBean transferirFondoBodyResponseBean;

    public TransferirFondoResponseBean(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean2) {
        this.transferirFondoBodyResponseBean = transferirFondoBodyResponseBean2;
    }

    public TransferirFondoResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.transferirFondoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public TransferirFondoBodyResponseBean getTransferirFondoBodyResponseBean() {
        return this.transferirFondoBodyResponseBean;
    }

    public void setTransferirFondoBodyResponseBean(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean2) {
        this.transferirFondoBodyResponseBean = transferirFondoBodyResponseBean2;
    }
}
