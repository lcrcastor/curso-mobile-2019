package ar.com.santander.rio.mbanking.services.soap.beans.body;

import java.util.List;

public class ListaRespuestas {
    public List<RespuestaBean> respuesta;

    public ListaRespuestas(List<RespuestaBean> list) {
        this.respuesta = list;
    }
}
