package ar.com.santander.rio.mbanking.services.model.general;

public class Fondo {
    private String lbl_data_cuotaparte;
    private String lbl_data_fondo;
    private String lbl_data_importe;
    private String lbl_data_valor_cuotaparte;
    private String lbl_fondo;

    public String getLbl_fondo() {
        return this.lbl_fondo;
    }

    public void setLbl_fondo(String str) {
        this.lbl_fondo = str;
    }

    public String getLbl_data_fondo() {
        return this.lbl_data_fondo;
    }

    public void setLbl_data_fondo(String str) {
        this.lbl_data_fondo = str;
    }

    public String getLbl_data_valor_cuotaparte() {
        return this.lbl_data_valor_cuotaparte;
    }

    public void setLbl_data_valor_cuotaparte(String str) {
        this.lbl_data_valor_cuotaparte = str;
    }

    public String getLbl_data_cuotaparte() {
        return this.lbl_data_cuotaparte;
    }

    public void setLbl_data_cuotaparte(String str) {
        this.lbl_data_cuotaparte = str;
    }

    public String getLbl_data_importe() {
        return this.lbl_data_importe;
    }

    public void setLbl_data_importe(String str) {
        this.lbl_data_importe = str;
    }
}
