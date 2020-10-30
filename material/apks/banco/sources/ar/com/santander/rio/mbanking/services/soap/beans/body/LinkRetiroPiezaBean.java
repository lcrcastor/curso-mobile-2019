package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class LinkRetiroPiezaBean implements Parcelable {
    public static final Creator<LinkRetiroPiezaBean> CREATOR = new Creator<LinkRetiroPiezaBean>() {
        public LinkRetiroPiezaBean createFromParcel(Parcel parcel) {
            return new LinkRetiroPiezaBean(parcel);
        }

        public LinkRetiroPiezaBean[] newArray(int i) {
            return new LinkRetiroPiezaBean[i];
        }
    };
    @SerializedName("mostrar")
    private String mostrar;
    @SerializedName("resCod")
    private String resCod;
    @SerializedName("resDesc")
    private String resDesc;
    @SerializedName("resTitulo")
    private String resTitulo;

    public int describeContents() {
        return 0;
    }

    public LinkRetiroPiezaBean() {
    }

    public LinkRetiroPiezaBean(String str, String str2, String str3, String str4) {
        this.resCod = str;
        this.resTitulo = str2;
        this.resDesc = str3;
        this.mostrar = str4;
    }

    protected LinkRetiroPiezaBean(Parcel parcel) {
        this.resCod = parcel.readString();
        this.resTitulo = parcel.readString();
        this.resDesc = parcel.readString();
        this.mostrar = parcel.readString();
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

    public String getMostrar() {
        return this.mostrar;
    }

    public void setMostrar(String str) {
        this.mostrar = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.resCod);
        parcel.writeString(this.resTitulo);
        parcel.writeString(this.resDesc);
        parcel.writeString(this.mostrar);
    }
}
