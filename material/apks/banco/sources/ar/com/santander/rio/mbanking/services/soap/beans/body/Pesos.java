package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Pesos implements Parcelable {
    public static final Creator<Pesos> CREATOR = new Creator<Pesos>() {
        public Pesos createFromParcel(Parcel parcel) {
            return new Pesos(parcel);
        }

        public Pesos[] newArray(int i) {
            return new Pesos[i];
        }
    };
    private String expreDolares;
    private String totalDolares;
    private String totalExpreDolares;
    private String totalPesos;

    public int describeContents() {
        return 0;
    }

    protected Pesos(Parcel parcel) {
        this.totalDolares = parcel.readString();
        this.totalPesos = parcel.readString();
        this.totalExpreDolares = parcel.readString();
        this.expreDolares = parcel.readString();
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

    public String getTotalExpreDolares() {
        return this.totalExpreDolares;
    }

    public void setTotalExpreDolares(String str) {
        this.totalExpreDolares = str;
    }

    public String getExpreDolares() {
        return this.expreDolares;
    }

    public void setExpreDolares(String str) {
        this.expreDolares = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.totalDolares);
        parcel.writeString(this.totalPesos);
        parcel.writeString(this.totalExpreDolares);
        parcel.writeString(this.expreDolares);
    }
}
