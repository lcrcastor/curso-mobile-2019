package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ErrorBodyBean implements Parcelable {
    public static final Creator<ErrorBodyBean> CREATOR = new Creator<ErrorBodyBean>() {
        public ErrorBodyBean createFromParcel(Parcel parcel) {
            return new ErrorBodyBean(parcel);
        }

        public ErrorBodyBean[] newArray(int i) {
            return new ErrorBodyBean[i];
        }
    };
    public String ayuda = null;
    public String idSist = "";
    public String res;
    public String resCod;
    public String resDesc = "";
    @SerializedName("resTitulo")
    public String resTitle;

    public enum TypeResError {
        ZERO,
        MINOR_ZERO,
        MAJOR_ZERO
    }

    public int describeContents() {
        return 0;
    }

    public ErrorBodyBean() {
    }

    public ErrorBodyBean(String str) {
        this.res = str;
    }

    public ErrorBodyBean(String str, String str2, String str3, String str4, String str5) {
        this.res = str;
        this.resCod = str2;
        this.resTitle = str3;
        this.resDesc = str4;
        this.idSist = str5;
    }

    protected ErrorBodyBean(Parcel parcel) {
        this.res = parcel.readString();
        this.resCod = parcel.readString();
        this.resTitle = parcel.readString();
        this.resDesc = parcel.readString();
        this.idSist = parcel.readString();
        this.ayuda = parcel.readString();
    }

    public String getAyuda() {
        return this.ayuda;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.res);
        parcel.writeString(this.resCod);
        parcel.writeString(this.resTitle);
        parcel.writeString(this.resDesc);
        parcel.writeString(this.idSist);
        parcel.writeString(this.ayuda);
    }
}
