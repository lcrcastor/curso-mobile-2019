package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AvisoItemBean implements Parcelable {
    public static final Creator<AvisoItemBean> CREATOR = new Creator<AvisoItemBean>() {
        public AvisoItemBean createFromParcel(Parcel parcel) {
            return new AvisoItemBean(parcel);
        }

        public AvisoItemBean[] newArray(int i) {
            return new AvisoItemBean[i];
        }
    };
    public String mostrarAviso;
    public String msjCod;
    public String msjDesc;
    public String msjTitulo;

    public int describeContents() {
        return 0;
    }

    protected AvisoItemBean(Parcel parcel) {
        this.mostrarAviso = parcel.readString();
        this.msjCod = parcel.readString();
        this.msjTitulo = parcel.readString();
        this.msjDesc = parcel.readString();
    }

    public String getMostrarAviso() {
        return this.mostrarAviso;
    }

    public void setMostrarAviso(String str) {
        this.mostrarAviso = str;
    }

    public String getMsjCod() {
        return this.msjCod;
    }

    public void setMsjCod(String str) {
        this.msjCod = str;
    }

    public String getMsjTitulo() {
        return this.msjTitulo;
    }

    public void setMsjTitulo(String str) {
        this.msjTitulo = str;
    }

    public String getMsjDesc() {
        return this.msjDesc;
    }

    public void setMsjDesc(String str) {
        this.msjDesc = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mostrarAviso);
        parcel.writeString(this.msjCod);
        parcel.writeString(this.msjTitulo);
        parcel.writeString(this.msjDesc);
    }
}
