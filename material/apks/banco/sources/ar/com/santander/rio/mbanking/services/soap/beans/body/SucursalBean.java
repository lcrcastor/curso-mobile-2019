package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SucursalBean implements Parcelable {
    public static final Creator<SucursalBean> CREATOR = new Creator<SucursalBean>() {
        public SucursalBean createFromParcel(Parcel parcel) {
            return new SucursalBean(parcel);
        }

        public SucursalBean[] newArray(int i) {
            return new SucursalBean[i];
        }
    };
    public String descripcion;
    public String descripcion2;
    public String direccion;
    public String horaApertura;
    public String horaCierre;

    /* renamed from: id reason: collision with root package name */
    public String f268id;
    public String imagen;
    public String latitud;
    public String longitud;
    public String nombre;
    public String numero;
    public String thumbnailBig;
    public String thumbnailSmall;
    public String tiempoEspera;

    public int describeContents() {
        return 0;
    }

    public SucursalBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.f268id = str;
        this.nombre = str2;
        this.direccion = str3;
        this.descripcion = str4;
        this.descripcion2 = str5;
        this.latitud = str6;
        this.longitud = str7;
        this.thumbnailSmall = str8;
        this.thumbnailBig = str9;
    }

    public SucursalBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        this.f268id = str;
        this.nombre = str2;
        this.direccion = str3;
        this.descripcion = str4;
        this.descripcion2 = str5;
        this.latitud = str6;
        this.longitud = str7;
        this.thumbnailSmall = str8;
        this.thumbnailBig = str9;
        this.numero = str10;
        this.horaApertura = str11;
        this.horaCierre = str12;
        this.tiempoEspera = str13;
        this.imagen = str14;
    }

    protected SucursalBean(Parcel parcel) {
        this.f268id = parcel.readString();
        this.nombre = parcel.readString();
        this.direccion = parcel.readString();
        this.descripcion = parcel.readString();
        this.descripcion2 = parcel.readString();
        this.latitud = parcel.readString();
        this.longitud = parcel.readString();
        this.thumbnailSmall = parcel.readString();
        this.thumbnailBig = parcel.readString();
        this.numero = parcel.readString();
        this.horaApertura = parcel.readString();
        this.horaCierre = parcel.readString();
        this.tiempoEspera = parcel.readString();
        this.imagen = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f268id);
        parcel.writeString(this.nombre);
        parcel.writeString(this.direccion);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.descripcion2);
        parcel.writeString(this.latitud);
        parcel.writeString(this.longitud);
        parcel.writeString(this.thumbnailSmall);
        parcel.writeString(this.thumbnailBig);
        parcel.writeString(this.numero);
        parcel.writeString(this.horaApertura);
        parcel.writeString(this.horaCierre);
        parcel.writeString(this.tiempoEspera);
        parcel.writeString(this.imagen);
    }
}
