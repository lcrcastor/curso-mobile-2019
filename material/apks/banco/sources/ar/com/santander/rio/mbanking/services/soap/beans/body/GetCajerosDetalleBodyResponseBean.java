package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetCajerosDetalleBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetCajerosDetalleBodyResponseBean> CREATOR = new Creator<GetCajerosDetalleBodyResponseBean>() {
        public GetCajerosDetalleBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetCajerosDetalleBodyResponseBean(parcel);
        }

        public GetCajerosDetalleBodyResponseBean[] newArray(int i) {
            return new GetCajerosDetalleBodyResponseBean[i];
        }
    };
    @SerializedName("datosCajeros")
    public DatosCajeros datosCajeros;

    /* renamed from: id reason: collision with root package name */
    public String f260id;

    public int describeContents() {
        return 0;
    }

    public GetCajerosDetalleBodyResponseBean(String str, DatosCajeros datosCajeros2) {
        this.f260id = str;
        this.datosCajeros = datosCajeros2;
    }

    public GetCajerosDetalleBodyResponseBean() {
    }

    protected GetCajerosDetalleBodyResponseBean(Parcel parcel) {
        this.f260id = parcel.readString();
        this.datosCajeros = (DatosCajeros) parcel.readParcelable(DatosCajeros.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f260id);
        parcel.writeParcelable(this.datosCajeros, i);
    }
}
