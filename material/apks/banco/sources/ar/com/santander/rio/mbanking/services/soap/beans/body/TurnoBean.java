package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class TurnoBean implements Parcelable {
    public static final Creator<TurnoBean> CREATOR = new Creator<TurnoBean>() {
        public TurnoBean createFromParcel(Parcel parcel) {
            return new TurnoBean(parcel);
        }

        public TurnoBean[] newArray(int i) {
            return new TurnoBean[i];
        }
    };
    @SerializedName("fechaCreacion")
    private String fechaCreacion;
    @SerializedName("fechaTurno")
    private String fechaTurno;
    @SerializedName("horaCreacion")
    private String horaCreacion;
    @SerializedName("idTurno")
    private String idTurno;
    @SerializedName("numeroSucursal")
    private String numeroSucursal;
    @SerializedName("numeroTicket")
    private String numeroTicket;
    @SerializedName("sucursal")
    private SucursalBean sucursal;
    @SerializedName("tiempoEspera")
    private String tiempoEspera;

    public int describeContents() {
        return 0;
    }

    public TurnoBean(SucursalBean sucursalBean, String str, String str2, String str3, String str4, String str5, String str6) {
        this.sucursal = sucursalBean;
        this.idTurno = str;
        this.fechaTurno = str2;
        this.tiempoEspera = str3;
        this.numeroTicket = str4;
        this.fechaCreacion = str5;
        this.horaCreacion = str6;
    }

    public TurnoBean(SucursalBean sucursalBean, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.sucursal = sucursalBean;
        this.idTurno = str;
        this.fechaTurno = str2;
        this.tiempoEspera = str3;
        this.numeroTicket = str4;
        this.fechaCreacion = str5;
        this.horaCreacion = str6;
        this.numeroSucursal = str7;
    }

    public TurnoBean() {
    }

    protected TurnoBean(Parcel parcel) {
        this.idTurno = parcel.readString();
        this.fechaTurno = parcel.readString();
        this.tiempoEspera = parcel.readString();
        this.numeroTicket = parcel.readString();
        this.fechaCreacion = parcel.readString();
        this.horaCreacion = parcel.readString();
        this.numeroSucursal = parcel.readString();
    }

    public SucursalBean getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(SucursalBean sucursalBean) {
        this.sucursal = sucursalBean;
    }

    public String getIdTurno() {
        return this.idTurno;
    }

    public void setIdTurno(String str) {
        this.idTurno = str;
    }

    public String getFechaTurno() {
        return this.fechaTurno;
    }

    public void setFechaTurno(String str) {
        this.fechaTurno = str;
    }

    public String getTiempoEspera() {
        return this.tiempoEspera;
    }

    public void setTiempoEspera(String str) {
        this.tiempoEspera = str;
    }

    public String getNumeroTicket() {
        return this.numeroTicket;
    }

    public void setNumeroTicket(String str) {
        this.numeroTicket = str;
    }

    public String getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(String str) {
        this.fechaCreacion = str;
    }

    public String getHoraCreacion() {
        return this.horaCreacion;
    }

    public void setHoraCreacion(String str) {
        this.horaCreacion = str;
    }

    public String getNumeroSucursal() {
        return this.numeroSucursal;
    }

    public void setNumeroSucursal(String str) {
        this.numeroSucursal = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idTurno);
        parcel.writeString(this.fechaTurno);
        parcel.writeString(this.tiempoEspera);
        parcel.writeString(this.numeroTicket);
        parcel.writeString(this.fechaCreacion);
        parcel.writeString(this.horaCreacion);
        parcel.writeString(this.numeroSucursal);
    }
}
