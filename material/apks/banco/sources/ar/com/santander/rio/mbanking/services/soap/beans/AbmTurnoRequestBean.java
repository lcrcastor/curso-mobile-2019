package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class AbmTurnoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public AbmTurnoBodyRequestBean abmTurnoBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean header;

    public AbmTurnoRequestBean() {
    }

    public AbmTurnoRequestBean(PrivateHeaderBean privateHeaderBean, AbmTurnoBodyRequestBean abmTurnoBodyRequestBean2) {
        this.header = privateHeaderBean;
        this.abmTurnoBodyRequestBean = abmTurnoBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
