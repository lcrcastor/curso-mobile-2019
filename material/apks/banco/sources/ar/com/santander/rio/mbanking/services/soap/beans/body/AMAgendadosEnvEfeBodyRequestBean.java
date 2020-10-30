package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class AMAgendadosEnvEfeBodyRequestBean {
    @SerializedName("destinatario")
    public AMAgendadosEnvEfeDestinatarioBean destinatario;
    public String tipoOperacion;

    public AMAgendadosEnvEfeBodyRequestBean() {
    }

    public AMAgendadosEnvEfeBodyRequestBean(String str, AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean) {
        this.tipoOperacion = str;
        this.destinatario = aMAgendadosEnvEfeDestinatarioBean;
    }
}
