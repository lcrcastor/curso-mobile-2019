package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSegurosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetSegurosRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetSegurosBodyRequestBean getSegurosBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetSegurosRequestBean() {
    }

    public GetSegurosRequestBean(PrivateHeaderBean privateHeaderBean, GetSegurosBodyRequestBean getSegurosBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.getSegurosBodyRequestBean = getSegurosBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
