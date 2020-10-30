package ar.com.santander.rio.mbanking.services.model.general;

import java.util.List;

public class Logado extends Resultado {
    private DatosPersonales datosPersonales;
    private DestinosMyA destinosMyA;
    private String estado;
    private List<Object> productos;
    private String sesionUsuario;

    public DestinosMyA getDestinosMyA() {
        return this.destinosMyA;
    }

    public void setDestinosMyA(DestinosMyA destinosMyA2) {
        this.destinosMyA = destinosMyA2;
    }

    public String getSesionUsuario() {
        return this.sesionUsuario;
    }

    public void setSesionUsuario(String str) {
        this.sesionUsuario = str;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String str) {
        this.estado = str;
    }

    public DatosPersonales getDatosPersonales() {
        return this.datosPersonales;
    }

    public void setDatosPersonales(DatosPersonales datosPersonales2) {
        this.datosPersonales = datosPersonales2;
    }
}
