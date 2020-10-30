package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class EnviarTokenOBPBodyRequestBean implements Parcelable {
    public static final Creator<EnviarTokenOBPBodyRequestBean> CREATOR = new Creator<EnviarTokenOBPBodyRequestBean>() {
        public EnviarTokenOBPBodyRequestBean createFromParcel(Parcel parcel) {
            return new EnviarTokenOBPBodyRequestBean(parcel);
        }

        public EnviarTokenOBPBodyRequestBean[] newArray(int i) {
            return new EnviarTokenOBPBodyRequestBean[i];
        }
    };
    @SerializedName("estado")
    private String estado;
    @SerializedName("hash")
    private String hash;
    @SerializedName("nup")
    private String nup;
    @SerializedName("tokenOBP")
    private String tokenOBP;

    public int describeContents() {
        return 0;
    }

    public EnviarTokenOBPBodyRequestBean(String str, String str2, String str3, String str4) {
        this.nup = str;
        this.hash = str2;
        this.tokenOBP = str3;
        this.estado = str4;
    }

    protected EnviarTokenOBPBodyRequestBean(Parcel parcel) {
        this.nup = parcel.readString();
        this.hash = parcel.readString();
        this.tokenOBP = parcel.readString();
        this.estado = parcel.readString();
    }

    public String getNup() {
        return this.nup;
    }

    public void setNup(String str) {
        this.nup = str;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String str) {
        this.hash = str;
    }

    public String getTokenOBP() {
        return this.tokenOBP;
    }

    public void setTokenOBP(String str) {
        this.tokenOBP = str;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String str) {
        this.estado = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nup);
        parcel.writeString(this.hash);
        parcel.writeString(this.tokenOBP);
        parcel.writeString(this.estado);
    }
}
