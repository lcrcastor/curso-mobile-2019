package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TenConsolidada implements Parcelable {
    public static final Creator<TenConsolidada> CREATOR = new Creator<TenConsolidada>() {
        public TenConsolidada createFromParcel(Parcel parcel) {
            return new TenConsolidada(parcel);
        }

        public TenConsolidada[] newArray(int i) {
            return new TenConsolidada[i];
        }
    };
    private String descripcionPerfil;
    private String idPerfilInversor;
    private String importeDolares;
    private String importePesos;
    private String nombrePerfil;

    public int describeContents() {
        return 0;
    }

    public String getImportePesos() {
        return this.importePesos;
    }

    public void setImportePesos(String str) {
        this.importePesos = str;
    }

    public String getImporteDolares() {
        return this.importeDolares;
    }

    public void setImporteDolares(String str) {
        this.importeDolares = str;
    }

    public String getIdPerfilInversor() {
        return this.idPerfilInversor;
    }

    public void setIdPerfilInversor(String str) {
        this.idPerfilInversor = str;
    }

    public String getDescripcionPerfil() {
        return this.descripcionPerfil;
    }

    public void setDescripcionPerfil(String str) {
        this.descripcionPerfil = str;
    }

    public String getNombrePerfil() {
        return this.nombrePerfil;
    }

    public void setNombrePerfil(String str) {
        this.nombrePerfil = str;
    }

    public static Creator<TenConsolidada> getCREATOR() {
        return CREATOR;
    }

    protected TenConsolidada(Parcel parcel) {
        this.importePesos = parcel.readString();
        this.importeDolares = parcel.readString();
        this.idPerfilInversor = parcel.readString();
        this.descripcionPerfil = parcel.readString();
        this.nombrePerfil = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.importePesos);
        parcel.writeString(this.importeDolares);
        parcel.writeString(this.idPerfilInversor);
        parcel.writeString(this.descripcionPerfil);
        parcel.writeString(this.nombrePerfil);
    }
}
