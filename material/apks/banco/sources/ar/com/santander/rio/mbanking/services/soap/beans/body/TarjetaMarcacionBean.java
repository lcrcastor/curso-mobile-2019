package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.versions.FechasInhabilitadasBean;
import com.google.gson.annotations.SerializedName;

public class TarjetaMarcacionBean implements Parcelable {
    public static final Creator<TarjetaMarcacionBean> CREATOR = new Creator<TarjetaMarcacionBean>() {
        public TarjetaMarcacionBean createFromParcel(Parcel parcel) {
            return new TarjetaMarcacionBean(parcel);
        }

        public TarjetaMarcacionBean[] newArray(int i) {
            return new TarjetaMarcacionBean[i];
        }
    };
    @SerializedName("condicion")
    private String condicion;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("fechasInhabilitadas")
    private FechasInhabilitadasBean listaFechasInhabilitadas;
    @SerializedName("nombre")
    private String nombre;
    private Boolean selected = Boolean.valueOf(false);

    public int describeContents() {
        return 0;
    }

    public TarjetaMarcacionBean() {
    }

    public TarjetaMarcacionBean(String str, String str2) {
        this.descripcion = str;
        this.condicion = str2;
    }

    public TarjetaMarcacionBean(String str, String str2, FechasInhabilitadasBean fechasInhabilitadasBean) {
        this.descripcion = str;
        this.condicion = str2;
        this.listaFechasInhabilitadas = fechasInhabilitadasBean;
    }

    public TarjetaMarcacionBean(String str, String str2, String str3) {
        this.nombre = str;
        this.descripcion = str2;
        this.condicion = str3;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getCondicion() {
        return this.condicion;
    }

    public void setCondicion(String str) {
        this.condicion = str;
    }

    public FechasInhabilitadasBean getListaFechasInhabilitadas() {
        return this.listaFechasInhabilitadas;
    }

    public void setListaFechasInhabilitadas(FechasInhabilitadasBean fechasInhabilitadasBean) {
        this.listaFechasInhabilitadas = fechasInhabilitadasBean;
    }

    public Boolean isSelected() {
        return this.selected;
    }

    public void setSelected(Boolean bool) {
        this.selected = bool;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nombre);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.condicion);
        parcel.writeParcelable(this.listaFechasInhabilitadas, i);
        parcel.writeValue(this.selected);
    }

    protected TarjetaMarcacionBean(Parcel parcel) {
        this.nombre = parcel.readString();
        this.descripcion = parcel.readString();
        this.condicion = parcel.readString();
        this.listaFechasInhabilitadas = (FechasInhabilitadasBean) parcel.readParcelable(FechasInhabilitadasBean.class.getClassLoader());
        this.selected = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
    }
}
