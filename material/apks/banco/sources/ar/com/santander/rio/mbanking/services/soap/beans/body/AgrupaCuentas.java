package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AgrupaCuentas implements Parcelable {
    public static final Creator<AgrupaCuentas> CREATOR = new Creator<AgrupaCuentas>() {
        public AgrupaCuentas createFromParcel(Parcel parcel) {
            return new AgrupaCuentas(parcel);
        }

        public AgrupaCuentas[] newArray(int i) {
            return new AgrupaCuentas[i];
        }
    };
    private Cuenta cuenta;
    private String msjeError;

    public int describeContents() {
        return 0;
    }

    public String getMsjeError() {
        return this.msjeError;
    }

    public void setMsjeError(String str) {
        this.msjeError = str;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta2) {
        this.cuenta = cuenta2;
    }

    protected AgrupaCuentas(Parcel parcel) {
        this.msjeError = parcel.readString();
        this.cuenta = (Cuenta) parcel.readParcelable(Cuenta.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.msjeError);
        parcel.writeParcelable(this.cuenta, i);
    }
}
