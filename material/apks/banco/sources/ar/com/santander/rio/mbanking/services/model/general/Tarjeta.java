package ar.com.santander.rio.mbanking.services.model.general;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;

public class Tarjeta implements Parcelable {
    public static final Creator<Tarjeta> CREATOR = new Creator<Tarjeta>() {
        public Tarjeta createFromParcel(Parcel parcel) {
            return new Tarjeta(parcel);
        }

        public Tarjeta[] newArray(int i) {
            return new Tarjeta[i];
        }
    };
    @SerializedName("clase")
    String clase;
    @SerializedName("clasePaquete")
    String clasePaquete;
    @SerializedName("claveBancariaUnificada")
    String claveBancariaUnificada;
    @SerializedName("codigo")
    private String codigo;
    @SerializedName("codigoTitularidad")
    String codigoTitularidad;
    @SerializedName("estadoTarjetaCredito")
    String estadoTarjetaCredito;
    @SerializedName("formaPagoTCredito")
    private String formaPagoTCredito;
    @SerializedName("importeD")
    private String importeD;
    @SerializedName("importeDConvertido")
    private String importeDConvertido;
    private String importeDFormatted;
    @SerializedName("importeP")
    private String importeP;
    @SerializedName("importePConvertido")
    private String importePConvertido;
    private String importePFormatted;
    @SerializedName("importePM")
    private String importePM;
    @SerializedName("importe_total_dolares")
    private String importe_total_dolares;
    @SerializedName("importe_total_pesos")
    private String importe_total_pesos;
    @SerializedName("indJerarquia")
    String indJerarquia;
    @SerializedName("nombreTarjeta")
    private String nombreTarjeta;
    @SerializedName("nroCuentaDebito")
    private String nroCuentaDebito;
    @SerializedName("nroPaq")
    String nroPaq;
    @SerializedName("nroSuc")
    String nroSuc;
    @SerializedName("nroTarjetaCredito")
    String nroTarjetaCredito;
    @SerializedName("nroTarjetaDebito")
    String nroTarjetaDebito;
    @SerializedName("numCuentaProduc")
    private String numCuentaProduc;
    @SerializedName("numTarjeta")
    private String numTarjeta;
    private String numTarjetaFormatted;
    @SerializedName("numero")
    String numero;
    @SerializedName("programado")
    private String programado;
    @SerializedName("sucursalCuentaDebito")
    String sucursalCuentaDebito;
    @SerializedName("sucursalPaq")
    String sucursalPaq;
    @SerializedName("tipo")
    String tipo;
    @SerializedName("tipoCuentaDebito")
    String tipoCuentaDebito;
    @SerializedName("tipoTarjeta")
    private String tipoTarjeta;
    @SerializedName("vencimiento")
    private String vencimiento;
    private String vencimientoFormatted;

    public int describeContents() {
        return 0;
    }

    public Tarjeta(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        if (!TextUtils.isEmpty(str5)) {
            if (str5.equalsIgnoreCase("05")) {
                this.nroTarjetaDebito = str;
            } else {
                this.nroTarjetaCredito = str;
            }
        }
        this.nroSuc = str2;
        this.indJerarquia = str3;
        this.clasePaquete = str4;
        this.tipo = str5;
        this.estadoTarjetaCredito = str6;
        this.clase = str7;
        this.codigoTitularidad = str8;
        this.nroPaq = str9;
        this.sucursalPaq = str10;
        this.claveBancariaUnificada = str11;
        this.numero = str12;
    }

    public Tarjeta() {
    }

    protected Tarjeta(Parcel parcel) {
        this.tipoCuentaDebito = parcel.readString();
        this.sucursalCuentaDebito = parcel.readString();
        this.nroTarjetaDebito = parcel.readString();
        this.nroTarjetaCredito = parcel.readString();
        this.nroSuc = parcel.readString();
        this.indJerarquia = parcel.readString();
        this.clasePaquete = parcel.readString();
        this.tipo = parcel.readString();
        this.estadoTarjetaCredito = parcel.readString();
        this.clase = parcel.readString();
        this.codigoTitularidad = parcel.readString();
        this.nroPaq = parcel.readString();
        this.sucursalPaq = parcel.readString();
        this.claveBancariaUnificada = parcel.readString();
        this.numero = parcel.readString();
        this.codigo = parcel.readString();
        this.tipoTarjeta = parcel.readString();
        this.nombreTarjeta = parcel.readString();
        this.numTarjeta = parcel.readString();
        this.vencimiento = parcel.readString();
        this.importeP = parcel.readString();
        this.importePM = parcel.readString();
        this.importePConvertido = parcel.readString();
        this.importe_total_pesos = parcel.readString();
        this.importeD = parcel.readString();
        this.importeDConvertido = parcel.readString();
        this.importe_total_dolares = parcel.readString();
        this.formaPagoTCredito = parcel.readString();
        this.nroCuentaDebito = parcel.readString();
        this.numCuentaProduc = parcel.readString();
        this.programado = parcel.readString();
        this.vencimientoFormatted = parcel.readString();
        this.numTarjetaFormatted = parcel.readString();
        this.importePFormatted = parcel.readString();
        this.importeDFormatted = parcel.readString();
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String str) {
        this.codigo = str;
    }

    public String getTipoTarjeta() {
        return this.tipoTarjeta;
    }

    public void setTipoTarjeta(String str) {
        this.tipoTarjeta = str;
    }

    public String getNombreTarjeta() {
        return this.nombreTarjeta;
    }

    public void setNombreTarjeta(String str) {
        this.nombreTarjeta = str;
    }

    public String getNumTarjeta() {
        return this.numTarjeta;
    }

    public void setNumTarjeta(String str) {
        this.numTarjeta = str;
    }

    public String getVencimiento() {
        return this.vencimiento;
    }

    public void setVencimiento(String str) {
        this.vencimiento = str;
    }

    public String getImporteP() {
        return this.importeP;
    }

    public void setImporteP(String str) {
        this.importeP = str;
    }

    public String getImportePM() {
        return this.importePM;
    }

    public void setImportePM(String str) {
        this.importePM = str;
    }

    public String getImportePConvertido() {
        return this.importePConvertido;
    }

    public void setImportePConvertido(String str) {
        this.importePConvertido = str;
    }

    public String getImporte_total_pesos() {
        return this.importe_total_pesos;
    }

    public void setImporte_total_pesos(String str) {
        this.importe_total_pesos = str;
    }

    public String getImporteD() {
        return this.importeD;
    }

    public void setImporteD(String str) {
        this.importeD = str;
    }

    public String getImporte_total_dolares() {
        return this.importe_total_dolares;
    }

    public void setImporte_total_dolares(String str) {
        this.importe_total_dolares = str;
    }

    public String getImporteDConvertido() {
        return this.importeDConvertido;
    }

    public void setImporteDConvertido(String str) {
        this.importeDConvertido = str;
    }

    public String getFormaPagoTCredito() {
        return this.formaPagoTCredito;
    }

    public void setFormaPagoTCredito(String str) {
        this.formaPagoTCredito = str;
    }

    public String getTipoCuentaDebito() {
        return this.tipoCuentaDebito;
    }

    public void setTipoCuentaDebito(String str) {
        this.tipoCuentaDebito = str;
    }

    public String getSucursalCuentaDebito() {
        return this.sucursalCuentaDebito;
    }

    public void setSucursalCuentaDebito(String str) {
        this.sucursalCuentaDebito = str;
    }

    public String getNroCuentaDebito() {
        return this.nroCuentaDebito;
    }

    public void setNroCuentaDebito(String str) {
        this.nroCuentaDebito = str;
    }

    public String getNumCuentaProduc() {
        return this.numCuentaProduc;
    }

    public void setNumCuentaProduc(String str) {
        this.numCuentaProduc = str;
    }

    public String getProgramado() {
        return this.programado;
    }

    public void setProgramado(String str) {
        this.programado = str;
    }

    public String getNumTarjetaFormatted() {
        return this.numTarjetaFormatted;
    }

    public void setNumTarjetaFormatted(String str) {
        this.numTarjetaFormatted = str;
    }

    public String getVencimientoFormatted() {
        return this.vencimientoFormatted;
    }

    public void setVencimientoFormatted(String str) {
        this.vencimientoFormatted = str;
    }

    public String getImportePFormatted() {
        return this.importePFormatted;
    }

    public void setImportePFormatted(String str) {
        this.importePFormatted = str;
    }

    public String getImporteDFormatted() {
        return this.importeDFormatted;
    }

    public void setImporteDFormatted(String str) {
        this.importeDFormatted = str;
    }

    public String getNroTarjetaDebito() {
        return this.nroTarjetaDebito;
    }

    public void setNroTarjetaDebito(String str) {
        this.nroTarjetaDebito = str;
    }

    public String getNroTarjetaCredito() {
        return this.nroTarjetaCredito;
    }

    public void setNroTarjetaCredito(String str) {
        this.nroTarjetaCredito = str;
    }

    public String getNroSuc() {
        return this.nroSuc;
    }

    public void setNroSuc(String str) {
        this.nroSuc = str;
    }

    public String getIndJerarquia() {
        return this.indJerarquia;
    }

    public void setIndJerarquia(String str) {
        this.indJerarquia = str;
    }

    public String getClasePaquete() {
        return this.clasePaquete;
    }

    public void setClasePaquete(String str) {
        this.clasePaquete = str;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getEstadoTarjetaCredito() {
        return this.estadoTarjetaCredito;
    }

    public void setEstadoTarjetaCredito(String str) {
        this.estadoTarjetaCredito = str;
    }

    public String getClase() {
        return this.clase;
    }

    public void setClase(String str) {
        this.clase = str;
    }

    public String getCodigoTitularidad() {
        return this.codigoTitularidad;
    }

    public void setCodigoTitularidad(String str) {
        this.codigoTitularidad = str;
    }

    public String getNroPaq() {
        return this.nroPaq;
    }

    public void setNroPaq(String str) {
        this.nroPaq = str;
    }

    public String getSucursalPaq() {
        return this.sucursalPaq;
    }

    public void setSucursalPaq(String str) {
        this.sucursalPaq = str;
    }

    public String getClaveBancariaUnificada() {
        return this.claveBancariaUnificada;
    }

    public void setClaveBancariaUnificada(String str) {
        this.claveBancariaUnificada = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoCuentaDebito);
        parcel.writeString(this.sucursalCuentaDebito);
        parcel.writeString(this.nroTarjetaDebito);
        parcel.writeString(this.nroTarjetaCredito);
        parcel.writeString(this.nroSuc);
        parcel.writeString(this.indJerarquia);
        parcel.writeString(this.clasePaquete);
        parcel.writeString(this.tipo);
        parcel.writeString(this.estadoTarjetaCredito);
        parcel.writeString(this.clase);
        parcel.writeString(this.codigoTitularidad);
        parcel.writeString(this.nroPaq);
        parcel.writeString(this.sucursalPaq);
        parcel.writeString(this.claveBancariaUnificada);
        parcel.writeString(this.numero);
        parcel.writeString(this.codigo);
        parcel.writeString(this.tipoTarjeta);
        parcel.writeString(this.nombreTarjeta);
        parcel.writeString(this.numTarjeta);
        parcel.writeString(this.vencimiento);
        parcel.writeString(this.importeP);
        parcel.writeString(this.importePM);
        parcel.writeString(this.importePConvertido);
        parcel.writeString(this.importe_total_pesos);
        parcel.writeString(this.importeD);
        parcel.writeString(this.importeDConvertido);
        parcel.writeString(this.importe_total_dolares);
        parcel.writeString(this.formaPagoTCredito);
        parcel.writeString(this.nroCuentaDebito);
        parcel.writeString(this.numCuentaProduc);
        parcel.writeString(this.programado);
        parcel.writeString(this.vencimientoFormatted);
        parcel.writeString(this.numTarjetaFormatted);
        parcel.writeString(this.importePFormatted);
        parcel.writeString(this.importeDFormatted);
    }
}
