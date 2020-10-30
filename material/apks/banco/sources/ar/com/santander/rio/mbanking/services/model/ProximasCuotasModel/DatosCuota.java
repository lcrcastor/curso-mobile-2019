package ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosCuota implements Parcelable {
    public static final Creator<DatosCuota> CREATOR = new Creator<DatosCuota>() {
        public DatosCuota createFromParcel(Parcel parcel) {
            return new DatosCuota(parcel);
        }

        public DatosCuota[] newArray(int i) {
            return new DatosCuota[i];
        }
    };
    @SerializedName("capitalCuotaCredito")
    @Expose
    private String capitalCuotaCredito;
    @SerializedName("fechaVencimiento")
    @Expose
    private String fechaVencimiento;
    @SerializedName("habPagarCredito")
    @Expose
    private String habPagarCredito;
    @SerializedName("importeCuota")
    @Expose
    private String importeCuota;
    @SerializedName("impuestoIVA")
    @Expose
    private String impuestoIVA;
    @SerializedName("nroCuotaCredito")
    @Expose
    private String nroCuotaCredito;
    @SerializedName("otrosImportes")
    @Expose
    private String otrosImportes;

    public int describeContents() {
        return 0;
    }

    protected DatosCuota(Parcel parcel) {
        this.habPagarCredito = parcel.readString();
        this.nroCuotaCredito = parcel.readString();
        this.fechaVencimiento = parcel.readString();
        this.importeCuota = parcel.readString();
        this.capitalCuotaCredito = parcel.readString();
        this.impuestoIVA = parcel.readString();
        this.otrosImportes = parcel.readString();
    }

    public String getHabPagarCredito() {
        return this.habPagarCredito;
    }

    public void setHabPagarCredito(String str) {
        this.habPagarCredito = str;
    }

    public String getNroCuotaCredito() {
        return this.nroCuotaCredito;
    }

    public void setNroCuotaCredito(String str) {
        this.nroCuotaCredito = str;
    }

    public String getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public void setFechaVencimiento(String str) {
        this.fechaVencimiento = str;
    }

    public String getImporteCuota() {
        return this.importeCuota;
    }

    public void setImporteCuota(String str) {
        this.importeCuota = str;
    }

    public String getCapitalCuotaCredito() {
        return this.capitalCuotaCredito;
    }

    public void setCapitalCuotaCredito(String str) {
        this.capitalCuotaCredito = str;
    }

    public String getImpuestoIVA() {
        return this.impuestoIVA;
    }

    public void setImpuestoIVA(String str) {
        this.impuestoIVA = str;
    }

    public String getOtrosImportes() {
        return this.otrosImportes;
    }

    public void setOtrosImportes(String str) {
        this.otrosImportes = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.habPagarCredito);
        parcel.writeString(this.nroCuotaCredito);
        parcel.writeString(this.fechaVencimiento);
        parcel.writeString(this.importeCuota);
        parcel.writeString(this.capitalCuotaCredito);
        parcel.writeString(this.impuestoIVA);
        parcel.writeString(this.otrosImportes);
    }
}
