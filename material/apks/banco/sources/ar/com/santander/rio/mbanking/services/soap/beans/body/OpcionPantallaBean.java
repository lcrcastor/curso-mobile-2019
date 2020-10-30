package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class OpcionPantallaBean implements Parcelable {
    public static final Creator<OpcionPantallaBean> CREATOR = new Creator<OpcionPantallaBean>() {
        public OpcionPantallaBean createFromParcel(Parcel parcel) {
            return new OpcionPantallaBean(parcel);
        }

        public OpcionPantallaBean[] newArray(int i) {
            return new OpcionPantallaBean[i];
        }
    };
    @SerializedName("accion")
    private String accion;
    @SerializedName("idOpcion")
    private String idOpcion;
    @SerializedName("idProximaPantalla")
    private String idProximaPantalla;
    @SerializedName("imagen")
    private String imagen;
    @SerializedName("mensaje")
    private MensajeTurnoBean mensaje;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("nombreNV")
    private String nombreNV;

    public int describeContents() {
        return 0;
    }

    public OpcionPantallaBean() {
    }

    public OpcionPantallaBean(String str, String str2, String str3, String str4, String str5, MensajeTurnoBean mensajeTurnoBean, String str6) {
        this.idOpcion = str;
        this.idProximaPantalla = str2;
        this.nombre = str3;
        this.nombreNV = str4;
        this.accion = str5;
        this.mensaje = mensajeTurnoBean;
        this.imagen = str6;
    }

    protected OpcionPantallaBean(Parcel parcel) {
        this.idOpcion = parcel.readString();
        this.idProximaPantalla = parcel.readString();
        this.nombre = parcel.readString();
        this.nombreNV = parcel.readString();
        this.accion = parcel.readString();
        this.mensaje = (MensajeTurnoBean) parcel.readParcelable(MensajeTurnoBean.class.getClassLoader());
        this.imagen = parcel.readString();
    }

    public String getIdOpcion() {
        return this.idOpcion;
    }

    public void setIdOpcion(String str) {
        this.idOpcion = str;
    }

    public String getIdProximaPantalla() {
        return this.idProximaPantalla;
    }

    public void setIdProximaPantalla(String str) {
        this.idProximaPantalla = str;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getNombreNV() {
        return this.nombreNV;
    }

    public void setNombreNV(String str) {
        this.nombreNV = str;
    }

    public String getAccion() {
        return this.accion;
    }

    public void setAccion(String str) {
        this.accion = str;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String str) {
        this.imagen = str;
    }

    public MensajeTurnoBean getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(MensajeTurnoBean mensajeTurnoBean) {
        this.mensaje = mensajeTurnoBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idOpcion);
        parcel.writeString(this.idProximaPantalla);
        parcel.writeString(this.nombre);
        parcel.writeString(this.nombreNV);
        parcel.writeString(this.accion);
        parcel.writeParcelable(this.mensaje, i);
        parcel.writeString(this.imagen);
    }
}
