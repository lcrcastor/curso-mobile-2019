package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CnsEmpresaValidaciones implements Parcelable {
    public static final Creator<CnsEmpresaValidaciones> CREATOR = new Creator<CnsEmpresaValidaciones>() {
        public CnsEmpresaValidaciones createFromParcel(Parcel parcel) {
            return new CnsEmpresaValidaciones(parcel);
        }

        public CnsEmpresaValidaciones[] newArray(int i) {
            return new CnsEmpresaValidaciones[i];
        }
    };
    public String longitud;
    public String prefijosCuit;
    public String tipoId;

    public int describeContents() {
        return 0;
    }

    public CnsEmpresaValidaciones(String str, String str2, String str3) {
        this.tipoId = str;
        this.prefijosCuit = str2;
        this.longitud = str3;
    }

    public CnsEmpresaValidaciones() {
    }

    protected CnsEmpresaValidaciones(Parcel parcel) {
        this.tipoId = parcel.readString();
        this.prefijosCuit = parcel.readString();
        this.longitud = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoId);
        parcel.writeString(this.prefijosCuit);
        parcel.writeString(this.longitud);
    }
}
