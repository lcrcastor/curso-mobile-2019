package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConsultaAgendadosEnvEfeBodyResponseBean extends ErrorBodyBean {
    @SerializedName("listaDestinatarios")
    public ConsultaAgendadosEnvEfeListaDestinatariosBean listaDestinatarios;

    public ConsultaAgendadosEnvEfeBodyResponseBean() {
    }

    public ConsultaAgendadosEnvEfeBodyResponseBean(ConsultaAgendadosEnvEfeListaDestinatariosBean consultaAgendadosEnvEfeListaDestinatariosBean) {
        this.listaDestinatarios = consultaAgendadosEnvEfeListaDestinatariosBean;
    }
}
