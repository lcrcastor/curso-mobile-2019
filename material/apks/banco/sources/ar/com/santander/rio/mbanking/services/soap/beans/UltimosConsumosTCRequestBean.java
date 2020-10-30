package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class UltimosConsumosTCRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private UltimosConsumosTCBodyRequestBean body;
    @SerializedName("header")
    private PrivateHeaderBean header;

    public UltimosConsumosTCRequestBean(PrivateHeaderBean privateHeaderBean, UltimosConsumosTCBodyRequestBean ultimosConsumosTCBodyRequestBean) {
        this.header = privateHeaderBean;
        this.body = ultimosConsumosTCBodyRequestBean;
    }

    public UltimosConsumosTCRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public UltimosConsumosTCBodyRequestBean getBody() {
        return this.body;
    }

    public void setBody(UltimosConsumosTCBodyRequestBean ultimosConsumosTCBodyRequestBean) {
        this.body = ultimosConsumosTCBodyRequestBean;
    }
}
