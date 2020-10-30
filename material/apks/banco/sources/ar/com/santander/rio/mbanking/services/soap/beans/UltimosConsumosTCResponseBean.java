package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimosConsumosTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class UltimosConsumosTCResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private UltimosConsumosTCBodyResponseBean body;
    @SerializedName("header")
    private PrivateHeaderBean header;

    public UltimosConsumosTCResponseBean(PrivateHeaderBean privateHeaderBean, UltimosConsumosTCBodyResponseBean ultimosConsumosTCBodyResponseBean) {
        this.header = privateHeaderBean;
        this.body = ultimosConsumosTCBodyResponseBean;
    }

    public UltimosConsumosTCResponseBean() {
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.body;
    }

    public PrivateHeaderBean getHeader() {
        return this.header;
    }

    public void setHeader(PrivateHeaderBean privateHeaderBean) {
        this.header = privateHeaderBean;
    }

    public UltimosConsumosTCBodyResponseBean getBody() {
        return this.body;
    }

    public void setBody(UltimosConsumosTCBodyResponseBean ultimosConsumosTCBodyResponseBean) {
        this.body = ultimosConsumosTCBodyResponseBean;
    }
}
