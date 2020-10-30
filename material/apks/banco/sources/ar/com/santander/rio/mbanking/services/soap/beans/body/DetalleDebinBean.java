package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DetalleDebinBean implements Parcelable {
    public static final Creator<DetalleDebinBean> CREATOR = new Creator<DetalleDebinBean>() {
        public DetalleDebinBean createFromParcel(Parcel parcel) {
            return new DetalleDebinBean(parcel);
        }

        public DetalleDebinBean[] newArray(int i) {
            return new DetalleDebinBean[i];
        }
    };
    @SerializedName("codConcepto")
    private String codConcepto;
    @SerializedName("codEstado")
    private String codEstado;
    @SerializedName("comprador")
    private CompradorBean compradorBean;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("fechaCreacion")
    private String fechaCreacion;
    @SerializedName("fechaVencimiento")
    private String fechaVencimiento;
    @SerializedName("idDebin")
    private String idDebin;
    @SerializedName("importe")
    private String importe;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("mostrarAceptarRechazar")
    private String mostrarAceptarRechazar;
    @SerializedName("mostrarDesconocimiento")
    private String mostrarDesconocimiento;
    @SerializedName("vendedor")
    private VendedorBean vendedorBean;

    public int describeContents() {
        return 0;
    }

    public DetalleDebinBean() {
    }

    protected DetalleDebinBean(Parcel parcel) {
        this.importe = parcel.readString();
        this.moneda = parcel.readString();
        this.compradorBean = (CompradorBean) parcel.readParcelable(CompradorBean.class.getClassLoader());
        this.vendedorBean = (VendedorBean) parcel.readParcelable(VendedorBean.class.getClassLoader());
        this.codEstado = parcel.readString();
        this.fechaCreacion = parcel.readString();
        this.fechaVencimiento = parcel.readString();
        this.codConcepto = parcel.readString();
        this.descripcion = parcel.readString();
        this.idDebin = parcel.readString();
        this.mostrarAceptarRechazar = parcel.readString();
        this.mostrarDesconocimiento = parcel.readString();
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public CompradorBean getCompradorBean() {
        return this.compradorBean;
    }

    public void setCompradorBean(CompradorBean compradorBean2) {
        this.compradorBean = compradorBean2;
    }

    public VendedorBean getVendedorBean() {
        return this.vendedorBean;
    }

    public void setVendedorBean(VendedorBean vendedorBean2) {
        this.vendedorBean = vendedorBean2;
    }

    public String getCodEstado() {
        return this.codEstado;
    }

    public void setCodEstado(String str) {
        this.codEstado = str;
    }

    public String getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(String str) {
        this.fechaCreacion = str;
    }

    public String getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public void setFechaVencimiento(String str) {
        this.fechaVencimiento = str;
    }

    public String getCodConcepto() {
        return this.codConcepto;
    }

    public void setCodConcepto(String str) {
        this.codConcepto = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getIdDebin() {
        return this.idDebin;
    }

    public void setIdDebin(String str) {
        this.idDebin = str;
    }

    public String getMostrarDesconocimiento() {
        return this.mostrarDesconocimiento;
    }

    public void setMostrarDesconocimiento(String str) {
        this.mostrarDesconocimiento = str;
    }

    public String getMostrarAceptarRechazar() {
        return this.mostrarAceptarRechazar;
    }

    public void setMostrarAceptarRechazar(String str) {
        this.mostrarAceptarRechazar = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.importe);
        parcel.writeString(this.moneda);
        parcel.writeParcelable(this.compradorBean, i);
        parcel.writeParcelable(this.vendedorBean, i);
        parcel.writeString(this.codEstado);
        parcel.writeString(this.fechaCreacion);
        parcel.writeString(this.fechaVencimiento);
        parcel.writeString(this.codConcepto);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.idDebin);
        parcel.writeString(this.mostrarAceptarRechazar);
        parcel.writeString(this.mostrarDesconocimiento);
    }

    public DetalleDebinBean clone() {
        DetalleDebinBean detalleDebinBean;
        try {
            Parcel obtain = Parcel.obtain();
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            detalleDebinBean = (DetalleDebinBean) CREATOR.createFromParcel(obtain);
            try {
                obtain.recycle();
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            detalleDebinBean = null;
            e.printStackTrace();
            return detalleDebinBean;
        }
        return detalleDebinBean;
    }
}
