package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmTurnoBodyRequestBean implements Parcelable {
    public static final Creator<AbmTurnoBodyRequestBean> CREATOR = new Creator<AbmTurnoBodyRequestBean>() {
        public AbmTurnoBodyRequestBean createFromParcel(Parcel parcel) {
            return new AbmTurnoBodyRequestBean(parcel);
        }

        public AbmTurnoBodyRequestBean[] newArray(int i) {
            return new AbmTurnoBodyRequestBean[i];
        }
    };
    @SerializedName("idTurno")
    private String idTurno;
    @SerializedName("numeroSucursal")
    private String numeroSucursal;
    @SerializedName("operacion")
    private String operacion;
    @SerializedName("retiroPiesa")
    private String retiroPiesa;

    public int describeContents() {
        return 0;
    }

    public AbmTurnoBodyRequestBean() {
    }

    public AbmTurnoBodyRequestBean(String str, String str2, String str3, String str4) {
        this.operacion = str;
        this.numeroSucursal = str2;
        this.retiroPiesa = str3;
        this.idTurno = str4;
    }

    protected AbmTurnoBodyRequestBean(Parcel parcel) {
        this.operacion = parcel.readString();
        this.numeroSucursal = parcel.readString();
        this.retiroPiesa = parcel.readString();
        this.idTurno = parcel.readString();
    }

    public String getOperacion() {
        return this.operacion;
    }

    public void getOperacion(String str) {
        this.operacion = str;
    }

    public String getNumeroSucursal() {
        return this.numeroSucursal;
    }

    public void setNumeroSucursal(String str) {
        this.numeroSucursal = str;
    }

    public String getRetiroPiesa() {
        return this.retiroPiesa;
    }

    public void setRetiroPiesa(String str) {
        this.retiroPiesa = str;
    }

    public String getIdTurno() {
        return this.idTurno;
    }

    public void setIdTurno(String str) {
        this.idTurno = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.operacion);
        parcel.writeString(this.numeroSucursal);
        parcel.writeString(this.retiroPiesa);
        parcel.writeString(this.idTurno);
    }
}
