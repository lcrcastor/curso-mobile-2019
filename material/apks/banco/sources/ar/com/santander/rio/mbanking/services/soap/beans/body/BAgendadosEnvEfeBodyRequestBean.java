package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class BAgendadosEnvEfeBodyRequestBean {
    @SerializedName("destinatario")
    public BAgendadosEnvEfeDestinatarioBean destinatario;
    public String tipoOperacion;

    public BAgendadosEnvEfeBodyRequestBean() {
    }

    public BAgendadosEnvEfeBodyRequestBean(String str, BAgendadosEnvEfeDestinatarioBean bAgendadosEnvEfeDestinatarioBean) {
        this.tipoOperacion = str;
        this.destinatario = bAgendadosEnvEfeDestinatarioBean;
    }
}
