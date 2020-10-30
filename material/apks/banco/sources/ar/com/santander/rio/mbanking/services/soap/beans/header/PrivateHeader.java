package ar.com.santander.rio.mbanking.services.soap.beans.header;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class PrivateHeader extends HeaderBean implements Parcelable {
    public static final Creator<PrivateHeader> CREATOR = new Creator<PrivateHeader>() {
        public PrivateHeader createFromParcel(Parcel parcel) {
            return new PrivateHeader(parcel);
        }

        public PrivateHeader[] newArray(int i) {
            return new PrivateHeader[i];
        }
    };
    @SerializedName("nup")
    public String nup;
    @SerializedName("sesionUsuario")
    public String session;

    public int describeContents() {
        return 0;
    }

    public PrivateHeader() {
    }

    protected PrivateHeader(Parcel parcel) {
        this.nup = parcel.readString();
        this.session = parcel.readString();
    }

    public String getNup() {
        return this.nup;
    }

    public void setNup(String str) {
        this.nup = str;
    }

    public String getSession() {
        return this.session;
    }

    public void setSession(String str) {
        this.session = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nup);
        parcel.writeString(this.session);
    }
}
