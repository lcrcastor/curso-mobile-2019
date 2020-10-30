package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GenesysChatBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GenesysChatRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GenesysChatBodyRequestBean genesysChatBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GenesysChatRequestBean() {
    }

    public GenesysChatRequestBean(PrivateHeaderBean privateHeaderBean, GenesysChatBodyRequestBean genesysChatBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.genesysChatBodyRequestBean = genesysChatBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public GenesysChatBodyRequestBean getGenesysChatBodyRequestBean() {
        return this.genesysChatBodyRequestBean;
    }

    public void setGenesysChatBodyRequestBean(GenesysChatBodyRequestBean genesysChatBodyRequestBean2) {
        this.genesysChatBodyRequestBean = genesysChatBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
