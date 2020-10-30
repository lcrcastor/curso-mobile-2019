package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaMovimientosFondosBean {
    @SerializedName("movimiento")
    private List<MovimientoFondosBean> movimientosFondosBean;

    public ListaMovimientosFondosBean() {
    }

    public ListaMovimientosFondosBean(List<MovimientoFondosBean> list) {
        this.movimientosFondosBean = list;
    }

    public List<MovimientoFondosBean> getMovimientosFondosBean() {
        return this.movimientosFondosBean;
    }

    public void setMovimientosFondosBean(List<MovimientoFondosBean> list) {
        this.movimientosFondosBean = list;
    }
}
