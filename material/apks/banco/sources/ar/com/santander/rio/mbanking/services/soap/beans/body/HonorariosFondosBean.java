package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class HonorariosFondosBean implements Parcelable {
    public static final Creator<HonorariosFondosBean> CREATOR = new Creator<HonorariosFondosBean>() {
        public HonorariosFondosBean createFromParcel(Parcel parcel) {
            return new HonorariosFondosBean(parcel);
        }

        public HonorariosFondosBean[] newArray(int i) {
            return new HonorariosFondosBean[i];
        }
    };
    @SerializedName("admin")
    private String admin;
    @SerializedName("entrada")
    private String entrada;
    @SerializedName("salida")
    private String salida;

    public int describeContents() {
        return 0;
    }

    public HonorariosFondosBean() {
        this.admin = "";
        this.entrada = "";
        this.salida = "";
    }

    public HonorariosFondosBean(String str, String str2, String str3) {
        this.admin = str;
        this.entrada = str2;
        this.salida = str3;
    }

    protected HonorariosFondosBean(Parcel parcel) {
        this.admin = parcel.readString();
        this.entrada = parcel.readString();
        this.salida = parcel.readString();
    }

    public String getAdmin() {
        return this.admin;
    }

    public void setAdmin(String str) {
        this.admin = str;
    }

    public String getEntrada() {
        return this.entrada;
    }

    public void setEntrada(String str) {
        this.entrada = str;
    }

    public String getSalida() {
        return this.salida;
    }

    public void setSalida(String str) {
        this.salida = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.admin);
        parcel.writeString(this.entrada);
        parcel.writeString(this.salida);
    }
}
