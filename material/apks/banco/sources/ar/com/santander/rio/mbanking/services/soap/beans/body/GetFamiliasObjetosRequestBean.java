package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetFamiliasObjetosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetFamiliasObjetosBodyRequestBean getFamiliasObjetosBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetFamiliasObjetosRequestBean() {
    }

    public GetFamiliasObjetosRequestBean(PrivateHeaderBean privateHeaderBean, GetFamiliasObjetosBodyRequestBean getFamiliasObjetosBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getFamiliasObjetosBodyRequestBean = getFamiliasObjetosBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
