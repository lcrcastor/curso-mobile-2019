package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ViajeBean implements Parcelable {
    public static final Creator<ViajeBean> CREATOR = new Creator<ViajeBean>() {
        public ViajeBean createFromParcel(Parcel parcel) {
            return new ViajeBean(parcel);
        }

        public ViajeBean[] newArray(int i) {
            return new ViajeBean[i];
        }
    };
    @SerializedName("acciones")
    private String acciones;
    @SerializedName("fechaFin")
    private String fechaFin;
    @SerializedName("fechaInicio")
    private String fechaInicio;
    @SerializedName("id")

    /* renamed from: id reason: collision with root package name */
    private String f269id;
    @SerializedName("mail")
    private String mail;
    @SerializedName("paises")
    private PaisesBean paises;
    @SerializedName("reintento")
    private String reintento;
    @SerializedName("tarjetas")
    private TarjetasMarcacionBean tarjetas;
    @SerializedName("totalDestinos")
    private String totalDestinos;
    @SerializedName("totalDias")
    private String totalDias;
    @SerializedName("totalTarjetas")
    private String totalTarjetas;
    @SerializedName("usuarios")
    private UsuariosMarcacionBean usuarios;

    public int describeContents() {
        return 0;
    }

    public ViajeBean() {
    }

    public ViajeBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, PaisesBean paisesBean, UsuariosMarcacionBean usuariosMarcacionBean, String str8) {
        this.f269id = str;
        this.fechaInicio = str2;
        this.fechaFin = str3;
        this.mail = str4;
        this.totalDias = str5;
        this.totalDestinos = str6;
        this.totalTarjetas = str7;
        this.paises = paisesBean;
        this.usuarios = usuariosMarcacionBean;
        this.acciones = str8;
    }

    public ViajeBean(String str, String str2, String str3, String str4, PaisesBean paisesBean, TarjetasMarcacionBean tarjetasMarcacionBean) {
        this.f269id = str;
        this.fechaInicio = str2;
        this.fechaFin = str3;
        this.reintento = str4;
        this.paises = paisesBean;
        this.tarjetas = tarjetasMarcacionBean;
    }

    public ViajeBean(ViajeBean viajeBean) {
        this.f269id = viajeBean.getId();
        this.fechaInicio = viajeBean.getFechaInicio();
        this.fechaFin = viajeBean.getFechaFin();
        this.mail = viajeBean.getMail();
        this.totalDias = viajeBean.getTotalDias();
        this.totalDestinos = viajeBean.getTotalDestinos();
        this.totalTarjetas = viajeBean.getTotalTarjetas();
        this.paises = viajeBean.getPaises();
        this.usuarios = viajeBean.getUsuarios();
        this.acciones = viajeBean.getAcciones();
    }

    protected ViajeBean(Parcel parcel) {
        this.f269id = parcel.readString();
        this.fechaInicio = parcel.readString();
        this.fechaFin = parcel.readString();
        this.mail = parcel.readString();
        this.totalDias = parcel.readString();
        this.totalDestinos = parcel.readString();
        this.totalTarjetas = parcel.readString();
        this.paises = (PaisesBean) parcel.readParcelable(PaisesBean.class.getClassLoader());
        this.usuarios = (UsuariosMarcacionBean) parcel.readParcelable(UsuariosMarcacionBean.class.getClassLoader());
        this.tarjetas = (TarjetasMarcacionBean) parcel.readParcelable(TarjetasMarcacionBean.class.getClassLoader());
        this.acciones = parcel.readString();
        this.reintento = parcel.readString();
    }

    public String getId() {
        return this.f269id;
    }

    public void setId(String str) {
        this.f269id = str;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String str) {
        this.fechaInicio = str;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String str) {
        this.fechaFin = str;
    }

    public String getReintento() {
        return this.reintento;
    }

    public void setReintento(String str) {
        this.reintento = str;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String str) {
        this.mail = str;
    }

    public String getTotalDias() {
        return this.totalDias;
    }

    public void setTotalDias(String str) {
        this.totalDias = str;
    }

    public String getTotalDestinos() {
        return this.totalDestinos;
    }

    public void setTotalDestinos(String str) {
        this.totalDestinos = str;
    }

    public String getTotalTarjetas() {
        return this.totalTarjetas;
    }

    public void setTotalTarjetas(String str) {
        this.totalTarjetas = str;
    }

    public PaisesBean getPaises() {
        return this.paises;
    }

    public void setPaises(PaisesBean paisesBean) {
        this.paises = paisesBean;
    }

    public TarjetasMarcacionBean getTarjetas() {
        return this.tarjetas;
    }

    public void setTarjetas(TarjetasMarcacionBean tarjetasMarcacionBean) {
        this.tarjetas = tarjetasMarcacionBean;
    }

    public String getAcciones() {
        return this.acciones;
    }

    public void setAcciones(String str) {
        this.acciones = str;
    }

    public UsuariosMarcacionBean getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(UsuariosMarcacionBean usuariosMarcacionBean) {
        this.usuarios = usuariosMarcacionBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f269id);
        parcel.writeString(this.fechaInicio);
        parcel.writeString(this.fechaFin);
        parcel.writeString(this.mail);
        parcel.writeString(this.totalDias);
        parcel.writeString(this.totalDestinos);
        parcel.writeString(this.totalTarjetas);
        parcel.writeParcelable(this.paises, i);
        parcel.writeParcelable(this.usuarios, i);
        parcel.writeParcelable(this.tarjetas, i);
        parcel.writeString(this.acciones);
        parcel.writeString(this.reintento);
    }

    public ViajeBean clone() {
        ViajeBean viajeBean;
        try {
            Parcel obtain = Parcel.obtain();
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            viajeBean = (ViajeBean) CREATOR.createFromParcel(obtain);
            try {
                obtain.recycle();
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            viajeBean = null;
            e.printStackTrace();
            return viajeBean;
        }
        return viajeBean;
    }
}
