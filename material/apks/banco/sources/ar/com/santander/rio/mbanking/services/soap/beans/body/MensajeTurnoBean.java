package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class MensajeTurnoBean implements Parcelable {
    public static final Creator<MensajeTurnoBean> CREATOR = new Creator<MensajeTurnoBean>() {
        public MensajeTurnoBean createFromParcel(Parcel parcel) {
            return new MensajeTurnoBean(parcel);
        }

        public MensajeTurnoBean[] newArray(int i) {
            return new MensajeTurnoBean[i];
        }
    };
    @SerializedName("mostrarSolicitudTurno")
    private String mostrarSolictarTurno;
    @SerializedName("resCod")
    private String resCod;
    @SerializedName("resDesc")
    private String resDesc;
    @SerializedName("resTitulo")
    private String resTitulo;

    public int describeContents() {
        return 0;
    }

    public MensajeTurnoBean() {
    }

    public MensajeTurnoBean(String str, String str2, String str3, String str4) {
        this.resCod = str;
        this.resTitulo = str2;
        this.resDesc = str3;
        this.mostrarSolictarTurno = str4;
    }

    protected MensajeTurnoBean(Parcel parcel) {
        this.resCod = parcel.readString();
        this.resTitulo = parcel.readString();
        this.resDesc = parcel.readString();
        this.mostrarSolictarTurno = parcel.readString();
    }

    public String getResCod() {
        return this.resCod;
    }

    public void setResCod(String str) {
        this.resCod = str;
    }

    public String getResTitulo() {
        return this.resTitulo;
    }

    public void setResTitulo(String str) {
        this.resTitulo = str;
    }

    public String getResDesc() {
        return this.resDesc;
    }

    public void setResDesc(String str) {
        this.resDesc = str;
    }

    public String getMostrarSolictarTurno() {
        return this.mostrarSolictarTurno;
    }

    public void setMostrarSolictarTurno(String str) {
        this.mostrarSolictarTurno = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.resCod);
        parcel.writeString(this.resTitulo);
        parcel.writeString(this.resDesc);
        parcel.writeString(this.mostrarSolictarTurno);
    }
}
