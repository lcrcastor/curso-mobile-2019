package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Dolar implements Parcelable {
    public static final Creator<Dolar> CREATOR = new Creator<Dolar>() {
        public Dolar createFromParcel(Parcel parcel) {
            return new Dolar(parcel);
        }

        public Dolar[] newArray(int i) {
            return new Dolar[i];
        }
    };
    private String exprePesos;
    private String totalDolares;
    private String totalExprePesos;
    private String totalPesos;

    public int describeContents() {
        return 0;
    }

    protected Dolar(Parcel parcel) {
        this.totalDolares = parcel.readString();
        this.totalPesos = parcel.readString();
        this.exprePesos = parcel.readString();
        this.totalExprePesos = parcel.readString();
    }

    public String getTotalDolares() {
        return this.totalDolares;
    }

    public void setTotalDolares(String str) {
        this.totalDolares = str;
    }

    public String getTotalPesos() {
        return this.totalPesos;
    }

    public void setTotalPesos(String str) {
        this.totalPesos = str;
    }

    public String getExprePesos() {
        return this.exprePesos;
    }

    public void setExprePesos(String str) {
        this.exprePesos = str;
    }

    public String getTotalExprePesos() {
        return this.totalExprePesos;
    }

    public void setTotalExprePesos(String str) {
        this.totalExprePesos = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.totalDolares);
        parcel.writeString(this.totalPesos);
        parcel.writeString(this.exprePesos);
        parcel.writeString(this.totalExprePesos);
    }
}
