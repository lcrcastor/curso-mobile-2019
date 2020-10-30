package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSeguroBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetFirmaSeguroRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetFirmaSeguroBodyRequestBean getFirmaSegurosBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public GetFirmaSeguroRequestBean() {
    }

    public GetFirmaSeguroRequestBean(PrivateHeaderBean privateHeaderBean, GetFirmaSeguroBodyRequestBean getFirmaSeguroBodyRequestBean) {
        this.header = privateHeaderBean;
        this.getFirmaSegurosBodyRequestBean = getFirmaSeguroBodyRequestBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
