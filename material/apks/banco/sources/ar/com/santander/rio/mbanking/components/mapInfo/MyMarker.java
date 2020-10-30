package ar.com.santander.rio.mbanking.components.mapInfo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CajeroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionSucursalBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;

public class MyMarker implements Parcelable {
    public static final Creator<MyMarker> CREATOR = new Creator<MyMarker>() {
        /* renamed from: a */
        public MyMarker createFromParcel(Parcel parcel) {
            return new MyMarker(parcel);
        }

        /* renamed from: a */
        public MyMarker[] newArray(int i) {
            return new MyMarker[i];
        }
    };
    public String desc;
    public String desc2;
    public String direc;
    public String dist;
    public String icon;
    public String iconBig;

    /* renamed from: id reason: collision with root package name */
    public String f245id;
    public boolean isClickable = false;
    public Double latitude;
    public Double longitude;
    public String numeroSucursal;
    public String tiempoEspera;
    public String tipo;
    public String title;
    public String url;

    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return null;
    }

    public MyMarker(CajeroBean cajeroBean) {
        this.f245id = cajeroBean.getId();
        this.title = cajeroBean.getNombre();
        this.desc = cajeroBean.getDescripcion();
        this.direc = cajeroBean.getDireccion();
        this.icon = cajeroBean.getThumbnailSmall();
        this.iconBig = cajeroBean.getThumbnailBig();
        this.latitude = Double.valueOf(cajeroBean.getLatitud());
        this.longitude = Double.valueOf(cajeroBean.getLongitud());
        this.isClickable = true;
    }

    public MyMarker(SucursalBean sucursalBean) {
        this.f245id = sucursalBean.f268id;
        this.title = sucursalBean.nombre;
        this.desc = sucursalBean.descripcion;
        this.desc2 = sucursalBean.descripcion2;
        this.direc = sucursalBean.direccion;
        this.icon = sucursalBean.thumbnailSmall == null ? sucursalBean.imagen : sucursalBean.thumbnailSmall;
        this.iconBig = sucursalBean.thumbnailBig == null ? sucursalBean.imagen : sucursalBean.thumbnailBig;
        this.latitude = Double.valueOf(sucursalBean.latitud);
        this.longitude = Double.valueOf(sucursalBean.longitud);
        this.tiempoEspera = sucursalBean.tiempoEspera;
        this.numeroSucursal = sucursalBean.numero;
        this.isClickable = true;
    }

    public MyMarker(PromocionSucursalBean promocionSucursalBean) {
        this.f245id = promocionSucursalBean.f267id;
        this.title = promocionSucursalBean.nombre;
        this.desc = promocionSucursalBean.descripcion;
        this.desc2 = promocionSucursalBean.descripcion2;
        this.direc = promocionSucursalBean.direccion;
        this.icon = promocionSucursalBean.thumbnailSmall;
        this.iconBig = promocionSucursalBean.thumbnailBig;
        this.latitude = Double.valueOf(promocionSucursalBean.latitud);
        this.longitude = Double.valueOf(promocionSucursalBean.longitud);
        this.isClickable = true;
        this.url = promocionSucursalBean.promoURL;
        this.tipo = promocionSucursalBean.tipo;
    }

    protected MyMarker(Parcel parcel) {
        boolean z = false;
        this.f245id = parcel.readString();
        this.url = parcel.readString();
        this.title = parcel.readString();
        this.desc = parcel.readString();
        this.desc2 = parcel.readString();
        this.direc = parcel.readString();
        this.icon = parcel.readString();
        this.iconBig = parcel.readString();
        Double d = null;
        this.latitude = parcel.readByte() == 0 ? null : Double.valueOf(parcel.readDouble());
        if (parcel.readByte() != 0) {
            d = Double.valueOf(parcel.readDouble());
        }
        this.longitude = d;
        this.tiempoEspera = parcel.readString();
        if (parcel.readByte() != 0) {
            z = true;
        }
        this.isClickable = z;
        this.dist = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f245id);
        parcel.writeString(this.url);
        parcel.writeString(this.title);
        parcel.writeString(this.desc);
        parcel.writeString(this.desc2);
        parcel.writeString(this.direc);
        parcel.writeString(this.icon);
        parcel.writeString(this.iconBig);
        parcel.writeString(this.tiempoEspera);
        if (this.latitude == null) {
            parcel.writeByte(0);
        } else {
            parcel.writeByte(1);
            parcel.writeDouble(this.latitude.doubleValue());
        }
        if (this.longitude == null) {
            parcel.writeByte(0);
        } else {
            parcel.writeByte(1);
            parcel.writeDouble(this.longitude.doubleValue());
        }
        parcel.writeByte(this.isClickable ? (byte) 1 : 0);
        parcel.writeString(this.dist);
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }
}
