package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ABMDestinatarioTransfRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ABMDestinatarioTransfBodyRequestBean abmDestinatarioTransfBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ABMDestinatarioTransfRequestBean() {
    }

    public ABMDestinatarioTransfRequestBean(PrivateHeaderBean privateHeaderBean, ABMDestinatarioTransfBodyRequestBean aBMDestinatarioTransfBodyRequestBean) {
        this.headerBean = privateHeaderBean;
        this.abmDestinatarioTransfBodyRequestBean = aBMDestinatarioTransfBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
