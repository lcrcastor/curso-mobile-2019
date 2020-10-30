package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class LinkSeguroBean implements Parcelable {
    public static final Creator<LinkSeguroBean> CREATOR = new Creator<LinkSeguroBean>() {
        public LinkSeguroBean createFromParcel(Parcel parcel) {
            return new LinkSeguroBean(parcel);
        }

        public LinkSeguroBean[] newArray(int i) {
            return new LinkSeguroBean[i];
        }
    };
    @SerializedName("mostrar")
    private String mostrar;
    @SerializedName("opcion")
    private String opcion;
    @SerializedName("resCod")
    private String resCod;
    @SerializedName("resDesc")
    private String resDesc;

    public int describeContents() {
        return 0;
    }

    public LinkSeguroBean(String str, String str2, String str3, String str4) {
        this.resCod = str;
        this.resDesc = str2;
        this.mostrar = str3;
        this.opcion = str4;
    }

    public LinkSeguroBean() {
    }

    protected LinkSeguroBean(Parcel parcel) {
        this.resCod = parcel.readString();
        this.resDesc = parcel.readString();
        this.mostrar = parcel.readString();
        this.opcion = parcel.readString();
    }

    public String getResCod() {
        return this.resCod;
    }

    public void setResCod(String str) {
        this.resCod = str;
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

    public String getOpcion() {
        return this.opcion;
    }

    public void setOpcion(String str) {
        this.opcion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.resCod);
        parcel.writeString(this.resDesc);
        parcel.writeString(this.mostrar);
        parcel.writeString(this.opcion);
    }
}
