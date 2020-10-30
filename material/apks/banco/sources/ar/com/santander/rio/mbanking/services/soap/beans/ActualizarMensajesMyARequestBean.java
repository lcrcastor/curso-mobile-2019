package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ActualizarMensajesMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ActualizarMensajesMyARequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ActualizarMensajesMyABodyRequestBean actualizarMensajesMyABodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ActualizarMensajesMyARequestBean() {
    }

    public ActualizarMensajesMyARequestBean(PrivateHeaderBean privateHeaderBean, ActualizarMensajesMyABodyRequestBean actualizarMensajesMyABodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.actualizarMensajesMyABodyRequestBean = actualizarMensajesMyABodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
