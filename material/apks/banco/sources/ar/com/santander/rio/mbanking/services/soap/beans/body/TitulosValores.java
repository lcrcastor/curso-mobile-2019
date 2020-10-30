package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TitulosValores implements Parcelable {
    public static final Creator<TitulosValores> CREATOR = new Creator<TitulosValores>() {
        public TitulosValores createFromParcel(Parcel parcel) {
            return new TitulosValores(parcel);
        }

        public TitulosValores[] newArray(int i) {
            return new TitulosValores[i];
        }
    };
    private Moneda dolares;
    private Moneda pesos;

    public int describeContents() {
        return 0;
    }

    protected TitulosValores(Parcel parcel) {
        this.pesos = (Moneda) parcel.readParcelable(Moneda.class.getClassLoader());
        this.dolares = (Moneda) parcel.readParcelable(Moneda.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.pesos, i);
        parcel.writeParcelable(this.dolares, i);
    }

    public Moneda getPesos() {
        return this.pesos;
    }

    public void setPesos(Moneda moneda) {
        this.pesos = moneda;
    }

    public Moneda getDolares() {
        return this.dolares;
    }

    public void setDolares(Moneda moneda) {
        this.dolares = moneda;
    }
}
