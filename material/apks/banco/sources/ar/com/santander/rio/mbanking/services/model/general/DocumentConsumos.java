package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class DocumentConsumos {
    @SerializedName("autorizaciones")
    private AutorizacionesConsumos autorizaciones;
    @SerializedName("datos")
    private DatosConsumos datos;
    @SerializedName("movimientos")
    private MovimientosConsumos movimientos;
    @SerializedName("sessionID")
    private String sessionID;
    @SerializedName("totalMovAuto")
    private TotalMovAuto totalMovAuto;

    public DocumentConsumos(String str, DatosConsumos datosConsumos, AutorizacionesConsumos autorizacionesConsumos, MovimientosConsumos movimientosConsumos, TotalMovAuto totalMovAuto2) {
        this.sessionID = str;
        this.datos = datosConsumos;
        this.autorizaciones = autorizacionesConsumos;
        this.movimientos = movimientosConsumos;
        this.totalMovAuto = totalMovAuto2;
    }

    public DocumentConsumos() {
    }

    public String getSessionID() {
        return this.sessionID;
    }

    public void setSessionID(String str) {
        this.sessionID = str;
    }

    public DatosConsumos getDatos() {
        return this.datos;
    }

    public void setDatos(DatosConsumos datosConsumos) {
        this.datos = datosConsumos;
    }

    public AutorizacionesConsumos getAutorizaciones() {
        return this.autorizaciones;
    }

    public void setAutorizaciones(AutorizacionesConsumos autorizacionesConsumos) {
        this.autorizaciones = autorizacionesConsumos;
    }

    public MovimientosConsumos getMovimientos() {
        return this.movimientos;
    }

    public void setMovimientos(MovimientosConsumos movimientosConsumos) {
        this.movimientos = movimientosConsumos;
    }

    public TotalMovAuto getTotalMovAuto() {
        return this.totalMovAuto;
    }

    public void setTotalMovAuto(TotalMovAuto totalMovAuto2) {
        this.totalMovAuto = totalMovAuto2;
    }
}
