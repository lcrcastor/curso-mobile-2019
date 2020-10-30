package ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosCredito implements Parcelable {
    public static final Creator<DatosCredito> CREATOR = new Creator<DatosCredito>() {
        public DatosCredito createFromParcel(Parcel parcel) {
            return new DatosCredito(parcel);
        }

        public DatosCredito[] newArray(int i) {
            return new DatosCredito[i];
        }
    };
    @SerializedName("ajusteCapitalMora")
    private String ajusteCapitalMora;
    @SerializedName("capitalAjustado")
    private String capitalAjustado;
    @SerializedName("capitalPesosUVA")
    private String capitalPesosUVA;
    @SerializedName("capitalUVA")
    private String capitalUVA;
    @SerializedName("capitalCuotaCredito")
    private String capitalcuotacredito;
    @SerializedName("cargoSeguroVida")
    private String cargoSeguroVida;
    @SerializedName("coeficiente")
    private String coeficiente;
    @SerializedName("descripCredito")
    private String descripcredito;
    @SerializedName("fechaCotizaUVA")
    private String fechaCotizaUVA;
    @SerializedName("fechaVencimiento")
    private String fechavencimiento;
    @SerializedName("habPagarCredito")
    private String habpagarcredito;
    @SerializedName("identificadorUVA")
    private String identificadorUVA;
    @SerializedName("importeCuotaUVA")
    private String importeCuotaUVA;
    @SerializedName("importeCuota")
    private String impoteCuota;
    @SerializedName("impuestoIVA")
    private String impuestoIva;
    @SerializedName("impuestoSellado")
    private String impuestoSellado;
    @SerializedName("interesesPunitorios")
    private String interesesPunitorios;
    @SerializedName("interesesUVA")
    private String interesesUVA;
    @SerializedName("interesesComp")
    private String interesescomp;
    @SerializedName("iva")
    private String iva;
    @SerializedName("mensajeUVA")
    private String mensajeUVA;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("nombreCredito")
    private String nombrecredito;
    @SerializedName("nroCuotaCredito")
    private String nroCuotaCredito;
    @SerializedName("nroCredito")
    private String nrocredito;
    @SerializedName("opcionProxCuota")
    private String opcionProxCuota;
    @SerializedName("otrosImpuestos")
    private String otrosImpuestos;
    @SerializedName("saldoSinAjustar")
    private String saldoSinAjustar;
    @SerializedName("seguroBien")
    private String seguroBien;
    @SerializedName("seguroIncendio")
    private String seguroIncendio;
    @SerializedName("sucCredito")
    private String succredito;
    @SerializedName("tasaCFTNA")
    private String tasacftna;
    @SerializedName("tasaCTFNAIVA")
    private String tasactfnaiva;
    @SerializedName("tasaTEA")
    private String tasatea;
    @SerializedName("tasaTNA")
    private String tasatna;
    @SerializedName("tipoCredito")
    private String tipocredito;
    @SerializedName("valorUVA")
    private String valorUVA;

    public int describeContents() {
        return 0;
    }

    protected DatosCredito(Parcel parcel) {
        this.succredito = parcel.readString();
        this.tipocredito = parcel.readString();
        this.nrocredito = parcel.readString();
        this.nombrecredito = parcel.readString();
        this.habpagarcredito = parcel.readString();
        this.descripcredito = parcel.readString();
        this.fechavencimiento = parcel.readString();
        this.capitalcuotacredito = parcel.readString();
        this.interesescomp = parcel.readString();
        this.tasatea = parcel.readString();
        this.tasatna = parcel.readString();
        this.tasacftna = parcel.readString();
        this.tasactfnaiva = parcel.readString();
        this.coeficiente = parcel.readString();
        this.ajusteCapitalMora = parcel.readString();
        this.interesesPunitorios = parcel.readString();
        this.saldoSinAjustar = parcel.readString();
        this.otrosImpuestos = parcel.readString();
        this.seguroIncendio = parcel.readString();
        this.nroCuotaCredito = parcel.readString();
        this.impoteCuota = parcel.readString();
        this.cargoSeguroVida = parcel.readString();
        this.valorUVA = parcel.readString();
        this.capitalUVA = parcel.readString();
        this.interesesUVA = parcel.readString();
        this.capitalPesosUVA = parcel.readString();
        this.capitalAjustado = parcel.readString();
        this.seguroBien = parcel.readString();
        this.moneda = parcel.readString();
        this.mensajeUVA = parcel.readString();
        this.impuestoIva = parcel.readString();
        this.impuestoSellado = parcel.readString();
        this.opcionProxCuota = parcel.readString();
        this.importeCuotaUVA = parcel.readString();
        this.fechaCotizaUVA = parcel.readString();
        this.identificadorUVA = parcel.readString();
        this.iva = parcel.readString();
    }

    public String getImpoteCuota() {
        return this.impoteCuota;
    }

    public void setImpoteCuota(String str) {
        this.impoteCuota = str;
    }

    public String getCargoSeguroVida() {
        return this.cargoSeguroVida;
    }

    public void setCargoSeguroVida(String str) {
        this.cargoSeguroVida = str;
    }

    public String getValorUVA() {
        return this.valorUVA;
    }

    public void setValorUVA(String str) {
        this.valorUVA = str;
    }

    public String getCapitalUVA() {
        return this.capitalUVA;
    }

    public void setCapitalUVA(String str) {
        this.capitalUVA = str;
    }

    public String getInteresesUVA() {
        return this.interesesUVA;
    }

    public void setInteresesUVA(String str) {
        this.interesesUVA = str;
    }

    public String getCapitalPesosUVA() {
        return this.capitalPesosUVA;
    }

    public void setCapitalPesosUVA(String str) {
        this.capitalPesosUVA = str;
    }

    public String getCapitalAjustado() {
        return this.capitalAjustado;
    }

    public void setCapitalAjustado(String str) {
        this.capitalAjustado = str;
    }

    public String getSeguroBien() {
        return this.seguroBien;
    }

    public void setSeguroBien(String str) {
        this.seguroBien = str;
    }

    public String getNroCuotaCredito() {
        return this.nroCuotaCredito;
    }

    public void setNroCuotaCredito(String str) {
        this.nroCuotaCredito = str;
    }

    public String getCoeficiente() {
        return this.coeficiente;
    }

    public void setCoeficiente(String str) {
        this.coeficiente = str;
    }

    public String getAjusteCapitalMora() {
        return this.ajusteCapitalMora;
    }

    public void setAjusteCapitalMora(String str) {
        this.ajusteCapitalMora = str;
    }

    public String getInteresesPunitorios() {
        return this.interesesPunitorios;
    }

    public void setInteresesPunitorios(String str) {
        this.interesesPunitorios = str;
    }

    public String getSaldoSinAjustar() {
        return this.saldoSinAjustar;
    }

    public void setSaldoSinAjustar(String str) {
        this.saldoSinAjustar = str;
    }

    public String getOtrosImpuestos() {
        return this.otrosImpuestos;
    }

    public void setOtrosImpuestos(String str) {
        this.otrosImpuestos = str;
    }

    public String getSeguroIncendio() {
        return this.seguroIncendio;
    }

    public void setSeguroIncendio(String str) {
        this.seguroIncendio = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public void setSuccredito(String str) {
        this.succredito = str;
    }

    public String getSuccredito() {
        return this.succredito;
    }

    public void setTipocredito(String str) {
        this.tipocredito = str;
    }

    public String getTipocredito() {
        return this.tipocredito;
    }

    public void setNrocredito(String str) {
        this.nrocredito = str;
    }

    public String getNrocredito() {
        return this.nrocredito;
    }

    public void setNombrecredito(String str) {
        this.nombrecredito = str;
    }

    public String getNombrecredito() {
        return this.nombrecredito;
    }

    public void setHabpagarcredito(String str) {
        this.habpagarcredito = str;
    }

    public String getHabpagarcredito() {
        return this.habpagarcredito;
    }

    public void setDescripcredito(String str) {
        this.descripcredito = str;
    }

    public String getDescripcredito() {
        return this.descripcredito;
    }

    public void setFechavencimiento(String str) {
        this.fechavencimiento = str;
    }

    public String getFechavencimiento() {
        return this.fechavencimiento;
    }

    public void setCapitalcuotacredito(String str) {
        this.capitalcuotacredito = str;
    }

    public String getCapitalcuotacredito() {
        return this.capitalcuotacredito;
    }

    public void setInteresescomp(String str) {
        this.interesescomp = str;
    }

    public String getInteresescomp() {
        return this.interesescomp;
    }

    public void setTasatea(String str) {
        this.tasatea = str;
    }

    public String getTasatea() {
        return this.tasatea;
    }

    public void setTasatna(String str) {
        this.tasatna = str;
    }

    public String getTasatna() {
        return this.tasatna;
    }

    public void setTasacftna(String str) {
        this.tasacftna = str;
    }

    public String getTasacftna() {
        return this.tasacftna;
    }

    public void setTasactfnaiva(String str) {
        this.tasactfnaiva = str;
    }

    public String getTasactfnaiva() {
        return this.tasactfnaiva;
    }

    public String getMensajeUVA() {
        return this.mensajeUVA;
    }

    public String getImpuestoIva() {
        return this.impuestoIva;
    }

    public String getOpcionProxCuota() {
        return this.opcionProxCuota;
    }

    public void setOpcionProxCuota(String str) {
        this.opcionProxCuota = str;
    }

    public String getImpuestoSellado() {
        return this.impuestoSellado;
    }

    public String getImporteCuotaUVA() {
        return this.importeCuotaUVA;
    }

    public void setImporteCuotaUVA(String str) {
        this.importeCuotaUVA = str;
    }

    public String getFechaCotizaUVA() {
        return this.fechaCotizaUVA;
    }

    public void setFechaCotizaUVA(String str) {
        this.fechaCotizaUVA = str;
    }

    public String getIdentificadorUVA() {
        return this.identificadorUVA;
    }

    public void setIdentificadorUVA(String str) {
        this.identificadorUVA = str;
    }

    public String getIva() {
        return this.iva;
    }

    public void setIva(String str) {
        this.iva = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.succredito);
        parcel.writeString(this.tipocredito);
        parcel.writeString(this.nrocredito);
        parcel.writeString(this.nombrecredito);
        parcel.writeString(this.habpagarcredito);
        parcel.writeString(this.descripcredito);
        parcel.writeString(this.fechavencimiento);
        parcel.writeString(this.capitalcuotacredito);
        parcel.writeString(this.interesescomp);
        parcel.writeString(this.tasatea);
        parcel.writeString(this.tasatna);
        parcel.writeString(this.tasacftna);
        parcel.writeString(this.tasactfnaiva);
        parcel.writeString(this.coeficiente);
        parcel.writeString(this.ajusteCapitalMora);
        parcel.writeString(this.interesesPunitorios);
        parcel.writeString(this.saldoSinAjustar);
        parcel.writeString(this.otrosImpuestos);
        parcel.writeString(this.seguroIncendio);
        parcel.writeString(this.nroCuotaCredito);
        parcel.writeString(this.impoteCuota);
        parcel.writeString(this.cargoSeguroVida);
        parcel.writeString(this.valorUVA);
        parcel.writeString(this.capitalUVA);
        parcel.writeString(this.interesesUVA);
        parcel.writeString(this.capitalPesosUVA);
        parcel.writeString(this.capitalAjustado);
        parcel.writeString(this.seguroBien);
        parcel.writeString(this.moneda);
        parcel.writeString(this.mensajeUVA);
        parcel.writeString(this.impuestoIva);
        parcel.writeString(this.impuestoSellado);
        parcel.writeString(this.opcionProxCuota);
        parcel.writeString(this.importeCuotaUVA);
        parcel.writeString(this.fechaCotizaUVA);
        parcel.writeString(this.identificadorUVA);
        parcel.writeString(this.iva);
    }
}
