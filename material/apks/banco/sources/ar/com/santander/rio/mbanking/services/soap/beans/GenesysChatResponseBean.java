package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GenesysChatBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GenesysChatResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public GenesysChatBodyResponseBean genesysChatBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public GenesysChatResponseBean(GenesysChatBodyResponseBean genesysChatBodyResponseBean2) {
        this.genesysChatBodyResponseBean = genesysChatBodyResponseBean2;
    }

    public GenesysChatResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.genesysChatBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GenesysChatBodyResponseBean getGenesysChatBodyResponseBean() {
        return this.genesysChatBodyResponseBean;
    }

    public void setGenesysChatBodyResponseBean(GenesysChatBodyResponseBean genesysChatBodyResponseBean2) {
        this.genesysChatBodyResponseBean = genesysChatBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
