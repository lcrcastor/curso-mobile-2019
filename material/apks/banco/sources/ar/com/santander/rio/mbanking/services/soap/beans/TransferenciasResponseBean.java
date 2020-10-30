package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferenciasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeader;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class TransferenciasResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeader headerBean;
    @SerializedName("body")
    public TransferenciasBodyResponseBean transferenciasBodyResponseBean;

    public TransferenciasResponseBean(TransferenciasBodyResponseBean transferenciasBodyResponseBean2) {
        this.transferenciasBodyResponseBean = transferenciasBodyResponseBean2;
    }

    public TransferenciasResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.transferenciasBodyResponseBean;
    }

    public void setTransferenciasBodyResponseBean(TransferenciasBodyResponseBean transferenciasBodyResponseBean2) {
        this.transferenciasBodyResponseBean = transferenciasBodyResponseBean2;
    }

    public TransferenciasBodyResponseBean getTransferenciasBodyResponseBean() {
        return this.transferenciasBodyResponseBean;
    }
}
