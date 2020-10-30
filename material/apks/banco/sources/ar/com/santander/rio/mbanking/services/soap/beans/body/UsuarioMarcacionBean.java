package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class UsuarioMarcacionBean implements Parcelable {
    public static final Creator<UsuarioMarcacionBean> CREATOR = new Creator<UsuarioMarcacionBean>() {
        public UsuarioMarcacionBean createFromParcel(Parcel parcel) {
            return new UsuarioMarcacionBean(parcel);
        }

        public UsuarioMarcacionBean[] newArray(int i) {
            return new UsuarioMarcacionBean[i];
        }
    };
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("otrasTarjetas")
    private String otrasTarjetas;
    @SerializedName("tarjetas")
    private TarjetasMarcacionBean tarjetas;

    public int describeContents() {
        return 0;
    }

    public UsuarioMarcacionBean(String str, TarjetasMarcacionBean tarjetasMarcacionBean, String str2) {
        this.nombre = str;
        this.tarjetas = tarjetasMarcacionBean;
        this.otrasTarjetas = str2;
    }

    public UsuarioMarcacionBean(String str, TarjetasMarcacionBean tarjetasMarcacionBean) {
        this.nombre = str;
        this.tarjetas = tarjetasMarcacionBean;
    }

    public UsuarioMarcacionBean() {
    }

    protected UsuarioMarcacionBean(Parcel parcel) {
        this.nombre = parcel.readString();
        this.tarjetas = (TarjetasMarcacionBean) parcel.readParcelable(TarjetasMarcacionBean.class.getClassLoader());
        this.otrasTarjetas = parcel.readString();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getOtrasTarjetas() {
        return this.otrasTarjetas;
    }

    public void setOtrasTarjetas(String str) {
        this.otrasTarjetas = str;
    }

    public TarjetasMarcacionBean getTarjetas() {
        return this.tarjetas;
    }

    public void setTarjetas(TarjetasMarcacionBean tarjetasMarcacionBean) {
        this.tarjetas = tarjetasMarcacionBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nombre);
        parcel.writeParcelable(this.tarjetas, i);
        parcel.writeString(this.otrasTarjetas);
    }
}
