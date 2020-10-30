package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetDetalleCuotaTenenciaCreditoBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetDetalleCuotaTenenciaCreditoBodyResponseBean> CREATOR = new Creator<GetDetalleCuotaTenenciaCreditoBodyResponseBean>() {
        public GetDetalleCuotaTenenciaCreditoBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetDetalleCuotaTenenciaCreditoBodyResponseBean(parcel);
        }

        public GetDetalleCuotaTenenciaCreditoBodyResponseBean[] newArray(int i) {
            return new GetDetalleCuotaTenenciaCreditoBodyResponseBean[i];
        }
    };
    @SerializedName("creCotizacionUva")
    private String CotizacionUva;
    @SerializedName("impSolUva")
    private String ImpSolUva;
    @SerializedName("creTasaTEA")
    private String creTasaTEA;
    @SerializedName("creTasaCFTNA")
    private String creTtasaCFTNA;
    @SerializedName("creTasaCFTNAIVA")
    private String creTtasaCTFNAIVA;
    @SerializedName("creTasaTNA")
    private String creTtasaTNA;
    @SerializedName("cuentaDebito")
    private String cuentaDebito;
    @SerializedName("disponibleUsos")
    private String disponibleUsos;
    @SerializedName("crefechaCotizaUva")
    private String fechaCotizaUva;
    @SerializedName("fechaSolicitud")
    private String fechaSolicitud;
    @SerializedName("importeSolicitado")
    private String importeSolicitado;
    @SerializedName("impuestoSellado")
    private String impuestoSellado;
    @SerializedName("lineaTotalCredito")
    private String lineaTotalCredit;
    @SerializedName("motivoCredito")
    private String motivoCredito;
    @SerializedName("plazoCredito")
    private String plazoCredito;
    @SerializedName("saldoDeudaCapital")
    private String saldoDeudaCapital;
    @SerializedName("seguroIncendio")
    private String seguroIncendio;

    public int describeContents() {
        return 0;
    }

    public GetDetalleCuotaTenenciaCreditoBodyResponseBean() {
    }

    private GetDetalleCuotaTenenciaCreditoBodyResponseBean(Parcel parcel) {
        this.cuentaDebito = parcel.readString();
        this.importeSolicitado = parcel.readString();
        this.plazoCredito = parcel.readString();
        this.impuestoSellado = parcel.readString();
        this.disponibleUsos = parcel.readString();
        this.saldoDeudaCapital = parcel.readString();
        this.lineaTotalCredit = parcel.readString();
        this.seguroIncendio = parcel.readString();
        this.motivoCredito = parcel.readString();
        this.fechaSolicitud = parcel.readString();
        this.ImpSolUva = parcel.readString();
        this.CotizacionUva = parcel.readString();
        this.fechaCotizaUva = parcel.readString();
        this.creTasaTEA = parcel.readString();
        this.creTtasaTNA = parcel.readString();
        this.creTtasaCFTNA = parcel.readString();
        this.creTtasaCTFNAIVA = parcel.readString();
    }

    public String getCuentaDebito() {
        return this.cuentaDebito;
    }

    public void setCuentaDebito(String str) {
        this.cuentaDebito = str;
    }

    public String getImporteSolicitado() {
        return this.importeSolicitado;
    }

    public void setImporteSolicitado(String str) {
        this.importeSolicitado = str;
    }

    public String getPlazoCredito() {
        return this.plazoCredito;
    }

    public void setPlazoCredito(String str) {
        this.plazoCredito = str;
    }

    public String getImpuestoSellado() {
        return this.impuestoSellado;
    }

    public void setImpuestoSellado(String str) {
        this.impuestoSellado = str;
    }

    public String getDisponibleUsos() {
        return this.disponibleUsos;
    }

    public void setDisponibleUsos(String str) {
        this.disponibleUsos = str;
    }

    public String getSaldoDeudaCapital() {
        return this.saldoDeudaCapital;
    }

    public void setSaldoDeudaCapital(String str) {
        this.saldoDeudaCapital = str;
    }

    public String getLineaTotalCredit() {
        return this.lineaTotalCredit;
    }

    public void setLineaTotalCredit(String str) {
        this.lineaTotalCredit = str;
    }

    public String getSeguroIncendio() {
        return this.seguroIncendio;
    }

    public void setSeguroIncendio(String str) {
        this.seguroIncendio = str;
    }

    public String getMotivoCredito() {
        return this.motivoCredito;
    }

    public void setMotivoCredito(String str) {
        this.motivoCredito = str;
    }

    public String getRes() {
        return this.res;
    }

    public void setRes(String str) {
        this.res = str;
    }

    public String getFechaSolicitud() {
        return this.fechaSolicitud;
    }

    public void setFechaSolicitud(String str) {
        this.fechaSolicitud = str;
    }

    public String getImpSolUva() {
        return this.ImpSolUva;
    }

    public void setImpSolUva(String str) {
        this.ImpSolUva = str;
    }

    public String getCotizacionUva() {
        return this.CotizacionUva;
    }

    public void setCotizacionUva(String str) {
        this.CotizacionUva = str;
    }

    public String getFechaCotizaUva() {
        return this.fechaCotizaUva;
    }

    public void setFechaCotizaUva(String str) {
        this.fechaCotizaUva = str;
    }

    public String getCreTasaTEA() {
        return this.creTasaTEA;
    }

    public void setCreTasaTEA(String str) {
        this.creTasaTEA = str;
    }

    public String getCreTtasaTNA() {
        return this.creTtasaTNA;
    }

    public void setCreTtasaTNA(String str) {
        this.creTtasaTNA = str;
    }

    public String getCreTtasaCFTNA() {
        return this.creTtasaCFTNA;
    }

    public void setCreTtasaCFTNA(String str) {
        this.creTtasaCFTNA = str;
    }

    public String getCreTtasaCTFNAIVA() {
        return this.creTtasaCTFNAIVA;
    }

    public void setCreTtasaCTFNAIVA(String str) {
        this.creTtasaCTFNAIVA = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GetDetalleCuotaTenenciaCreditoBodyResponseBean{cuentaDebito='");
        sb.append(this.cuentaDebito);
        sb.append('\'');
        sb.append(", importeSolicitado='");
        sb.append(this.importeSolicitado);
        sb.append('\'');
        sb.append(", plazoCredito='");
        sb.append(this.plazoCredito);
        sb.append('\'');
        sb.append(", impuestoSellado='");
        sb.append(this.impuestoSellado);
        sb.append('\'');
        sb.append(", disponibleUsos='");
        sb.append(this.disponibleUsos);
        sb.append('\'');
        sb.append(", saldoDeudaCapital='");
        sb.append(this.saldoDeudaCapital);
        sb.append('\'');
        sb.append(", lineaTotalCredit='");
        sb.append(this.lineaTotalCredit);
        sb.append('\'');
        sb.append(", seguroIncendio='");
        sb.append(this.seguroIncendio);
        sb.append('\'');
        sb.append(", motivoCredito='");
        sb.append(this.motivoCredito);
        sb.append('\'');
        sb.append(", fechaSolicitud='");
        sb.append(this.fechaSolicitud);
        sb.append('\'');
        sb.append(", ImpSolUva='");
        sb.append(this.ImpSolUva);
        sb.append('\'');
        sb.append(", CotizacionUva='");
        sb.append(this.CotizacionUva);
        sb.append('\'');
        sb.append(", fechaCotizaUva='");
        sb.append(this.fechaCotizaUva);
        sb.append('\'');
        sb.append(", creTasaTEA='");
        sb.append(this.creTasaTEA);
        sb.append('\'');
        sb.append(", creTtasaTNA='");
        sb.append(this.creTtasaTNA);
        sb.append('\'');
        sb.append(", creTtasaCFTNA='");
        sb.append(this.creTtasaCFTNA);
        sb.append('\'');
        sb.append(", creTtasaCTFNAIVA='");
        sb.append(this.creTtasaCTFNAIVA);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.cuentaDebito);
        parcel.writeString(this.importeSolicitado);
        parcel.writeString(this.plazoCredito);
        parcel.writeString(this.impuestoSellado);
        parcel.writeString(this.disponibleUsos);
        parcel.writeString(this.saldoDeudaCapital);
        parcel.writeString(this.lineaTotalCredit);
        parcel.writeString(this.seguroIncendio);
        parcel.writeString(this.motivoCredito);
        parcel.writeString(this.fechaSolicitud);
        parcel.writeString(this.ImpSolUva);
        parcel.writeString(this.CotizacionUva);
        parcel.writeString(this.fechaCotizaUva);
        parcel.writeString(this.creTasaTEA);
        parcel.writeString(this.creTtasaTNA);
        parcel.writeString(this.creTtasaCFTNA);
        parcel.writeString(this.creTtasaCTFNAIVA);
    }
}
