package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.soap.beans.BaseResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class TenenciaInversionesRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public TenenciaInversionesBodyRequestBean tenenciaInversionesBodyRequestBean;

    public Class getClassBean() {
        return getClass();
    }
}
