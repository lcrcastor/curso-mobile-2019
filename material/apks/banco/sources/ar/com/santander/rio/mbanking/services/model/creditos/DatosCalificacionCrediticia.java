package ar.com.santander.rio.mbanking.services.model.creditos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosCalificacionCrediticia implements Parcelable {
    public static final Creator<DatosCalificacionCrediticia> CREATOR = new Creator<DatosCalificacionCrediticia>() {
        public DatosCalificacionCrediticia createFromParcel(Parcel parcel) {
            return new DatosCalificacionCrediticia(parcel);
        }

        public DatosCalificacionCrediticia[] newArray(int i) {
            return new DatosCalificacionCrediticia[i];
        }
    };
    @SerializedName("codInhabilitCli")
    public String codInhabilitCli;
    @SerializedName("descripInhabilitado")
    public String descripInhabilitado;
    @SerializedName("impDispCuota")
    public String impDispCuota;
    @SerializedName("impDispPrest")
    public String impDispPrest;
    @SerializedName("impIngreso")
    public String impIngreso;
    @SerializedName("porcAfectAcuerdo")
    public String porcAfectAcuerdo;

    public int describeContents() {
        return 0;
    }

    protected DatosCalificacionCrediticia(Parcel parcel) {
        this.porcAfectAcuerdo = parcel.readString();
        this.impDispPrest = parcel.readString();
        this.impDispCuota = parcel.readString();
        this.impIngreso = parcel.readString();
        this.codInhabilitCli = parcel.readString();
        this.descripInhabilitado = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.porcAfectAcuerdo);
        parcel.writeString(this.impDispPrest);
        parcel.writeString(this.impDispCuota);
        parcel.writeString(this.impIngreso);
        parcel.writeString(this.codInhabilitCli);
        parcel.writeString(this.descripInhabilitado);
    }

    public String getPorcAfectAcuerdo() {
        return this.porcAfectAcuerdo;
    }

    public void setPorcAfectAcuerdo(String str) {
        this.porcAfectAcuerdo = str;
    }

    public String getImpDispPrest() {
        return this.impDispPrest;
    }

    public void setImpDispPrest(String str) {
        this.impDispPrest = str;
    }

    public String getImpDispCuota() {
        return this.impDispCuota;
    }

    public void setImpDispCuota(String str) {
        this.impDispCuota = str;
    }

    public String getImpIngreso() {
        return this.impIngreso;
    }

    public void setImpIngreso(String str) {
        this.impIngreso = str;
    }

    public String getCodInhabilitCli() {
        return this.codInhabilitCli;
    }

    public void setCodInhabilitCli(String str) {
        this.codInhabilitCli = str;
    }

    public String getDescripInhabilitado() {
        return this.descripInhabilitado;
    }

    public void setDescripInhabilitado(String str) {
        this.descripInhabilitado = str;
    }
}
