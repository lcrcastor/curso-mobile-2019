package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class SeguroBean implements Parcelable {
    public static final Creator<SeguroBean> CREATOR = new Creator<SeguroBean>() {
        public SeguroBean createFromParcel(Parcel parcel) {
            return new SeguroBean(parcel);
        }

        public SeguroBean[] newArray(int i) {
            return new SeguroBean[i];
        }
    };
    @SerializedName("aseguradora")
    private String aseguradora;
    @SerializedName("asistencias")
    private AsistenciasSeguroBean asistencias;
    @SerializedName("codPlan")
    private String codPlan;
    @SerializedName("codProducto")
    private String codProducto;
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("cuota")
    private String cuota;
    @SerializedName("datos")
    private DatosSeguroBean datos;
    @SerializedName("descCorta")
    private String descCorta;
    @SerializedName("eMail")
    private String eMail;
    @SerializedName("fechaInicio")
    private String fechaInicio;
    @SerializedName("idDispositivo")
    private String idDispositivo;
    @SerializedName("marca")
    private String marca;
    @SerializedName("medioPago")
    private String medioPago;
    @SerializedName("modelo")
    private String modelo;
    @SerializedName("numCertificado")
    private String numCertificado;
    @SerializedName("numPoliza")
    private String numPoliza;
    @SerializedName("ocupacion")
    private String ocupacion;
    @SerializedName("propietario")
    private String propietario;
    @SerializedName("subtituloDatos")
    private String subtituloDatos;
    @SerializedName("sumaAsegurada")
    private String sumaAsegurada;
    @SerializedName("tipoEnvioPoliza")
    private String tipoEnvioPoliza;
    @SerializedName("titulo")
    private String titulo;

    public int describeContents() {
        return 0;
    }

    public SeguroBean() {
    }

    public SeguroBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, DatosSeguroBean datosSeguroBean, AsistenciasSeguroBean asistenciasSeguroBean) {
        this.codRamo = str;
        this.codProducto = str2;
        this.codPlan = str3;
        this.numPoliza = str4;
        this.numCertificado = str5;
        this.titulo = str6;
        this.descCorta = str7;
        this.cuota = str8;
        this.aseguradora = str9;
        this.fechaInicio = str10;
        this.sumaAsegurada = str11;
        this.propietario = str12;
        this.medioPago = str13;
        this.idDispositivo = str14;
        this.marca = str15;
        this.modelo = str16;
        this.ocupacion = str17;
        this.eMail = str18;
        this.tipoEnvioPoliza = str19;
        this.subtituloDatos = str20;
        this.datos = datosSeguroBean;
        this.asistencias = asistenciasSeguroBean;
    }

    public String getCodRamo() {
        return this.codRamo;
    }

    public void setCodRamo(String str) {
        this.codRamo = str;
    }

    public String getCodProducto() {
        return this.codProducto;
    }

    public void setCodProducto(String str) {
        this.codProducto = str;
    }

    public String getCodPlan() {
        return this.codPlan;
    }

    public void setCodPlan(String str) {
        this.codPlan = str;
    }

    public String getNumPoliza() {
        return this.numPoliza;
    }

    public void setNumPoliza(String str) {
        this.numPoliza = str;
    }

    public String getNumCertificado() {
        return this.numCertificado;
    }

    public void setNumCertificado(String str) {
        this.numCertificado = str;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String str) {
        this.titulo = str;
    }

    public String getDescCorta() {
        return this.descCorta;
    }

    public void setDescCorta(String str) {
        this.descCorta = str;
    }

    public String getCuota() {
        return this.cuota;
    }

    public void setCuota(String str) {
        this.cuota = str;
    }

    public String getAseguradora() {
        return this.aseguradora;
    }

    public void setAseguradora(String str) {
        this.aseguradora = str;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String str) {
        this.fechaInicio = str;
    }

    public String getSumaAsegurada() {
        return this.sumaAsegurada;
    }

    public void setSumaAsegurada(String str) {
        this.sumaAsegurada = str;
    }

    public String getPropietario() {
        return this.propietario;
    }

    public void setPropietario(String str) {
        this.propietario = str;
    }

    public String getMedioPago() {
        return this.medioPago;
    }

    public void setMedioPago(String str) {
        this.medioPago = str;
    }

    public String getIdDispositivo() {
        return this.idDispositivo;
    }

    public void setIdDispositivo(String str) {
        this.idDispositivo = str;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String str) {
        this.marca = str;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String str) {
        this.modelo = str;
    }

    public String getOcupacion() {
        return this.ocupacion;
    }

    public void setOcupacion(String str) {
        this.ocupacion = str;
    }

    public String geteMail() {
        return this.eMail;
    }

    public void seteMail(String str) {
        this.eMail = str;
    }

    public String getTipoEnvioPoliza() {
        return this.tipoEnvioPoliza;
    }

    public void setTipoEnvioPoliza(String str) {
        this.tipoEnvioPoliza = str;
    }

    public String getSubtituloDatos() {
        return this.subtituloDatos;
    }

    public void setSubtituloDatos(String str) {
        this.subtituloDatos = str;
    }

    public DatosSeguroBean getDatos() {
        return this.datos;
    }

    public void setDatos(DatosSeguroBean datosSeguroBean) {
        this.datos = datosSeguroBean;
    }

    public AsistenciasSeguroBean getAsistencias() {
        return this.asistencias;
    }

    public void setAsistencias(AsistenciasSeguroBean asistenciasSeguroBean) {
        this.asistencias = asistenciasSeguroBean;
    }

    public static Creator<SeguroBean> getCREATOR() {
        return CREATOR;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codRamo);
        parcel.writeString(this.codProducto);
        parcel.writeString(this.codPlan);
        parcel.writeString(this.numPoliza);
        parcel.writeString(this.numCertificado);
        parcel.writeString(this.titulo);
        parcel.writeString(this.descCorta);
        parcel.writeString(this.cuota);
        parcel.writeString(this.aseguradora);
        parcel.writeString(this.fechaInicio);
        parcel.writeString(this.sumaAsegurada);
        parcel.writeString(this.propietario);
        parcel.writeString(this.medioPago);
        parcel.writeString(this.idDispositivo);
        parcel.writeString(this.marca);
        parcel.writeString(this.modelo);
        parcel.writeString(this.ocupacion);
        parcel.writeString(this.eMail);
        parcel.writeString(this.tipoEnvioPoliza);
        parcel.writeString(this.subtituloDatos);
        parcel.writeParcelable(this.datos, i);
        parcel.writeParcelable(this.asistencias, i);
    }

    protected SeguroBean(Parcel parcel) {
        this.codRamo = parcel.readString();
        this.codProducto = parcel.readString();
        this.codPlan = parcel.readString();
        this.numPoliza = parcel.readString();
        this.numCertificado = parcel.readString();
        this.titulo = parcel.readString();
        this.descCorta = parcel.readString();
        this.cuota = parcel.readString();
        this.aseguradora = parcel.readString();
        this.fechaInicio = parcel.readString();
        this.sumaAsegurada = parcel.readString();
        this.propietario = parcel.readString();
        this.medioPago = parcel.readString();
        this.idDispositivo = parcel.readString();
        this.marca = parcel.readString();
        this.modelo = parcel.readString();
        this.ocupacion = parcel.readString();
        this.eMail = parcel.readString();
        this.tipoEnvioPoliza = parcel.readString();
        this.subtituloDatos = parcel.readString();
        this.datos = (DatosSeguroBean) parcel.readParcelable(DatosSeguroBean.class.getClassLoader());
        this.asistencias = (AsistenciasSeguroBean) parcel.readParcelable(AsistenciasSeguroBean.class.getClassLoader());
    }
}
