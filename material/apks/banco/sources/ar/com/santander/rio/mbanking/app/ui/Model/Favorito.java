package ar.com.santander.rio.mbanking.app.ui.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Favorito implements Parcelable {
    public static final Creator<Favorito> CREATOR = new Creator<Favorito>() {
        /* renamed from: a */
        public Favorito createFromParcel(Parcel parcel) {
            return new Favorito(parcel);
        }

        /* renamed from: a */
        public Favorito[] newArray(int i) {
            return new Favorito[i];
        }
    };
    public String direccion;
    public double lat;
    public double lon;
    public String nombre;

    public int describeContents() {
        return 0;
    }

    public Favorito(double d, double d2, String str, String str2) {
        this.lat = d;
        this.lon = d2;
        this.direccion = str;
        this.nombre = str2;
    }

    public Favorito(double d, double d2, String str) {
        this.lat = d;
        this.lon = d2;
        this.direccion = str;
    }

    protected Favorito(Parcel parcel) {
        this.lat = parcel.readDouble();
        this.lon = parcel.readDouble();
        this.direccion = parcel.readString();
        this.nombre = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.lon);
        parcel.writeString(this.direccion);
        parcel.writeString(this.nombre);
    }
}
