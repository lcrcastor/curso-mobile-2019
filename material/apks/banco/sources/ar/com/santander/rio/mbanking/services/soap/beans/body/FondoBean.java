package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class FondoBean implements Parcelable {
    public static final Creator<FondoBean> CREATOR = new Creator<FondoBean>() {
        public FondoBean createFromParcel(Parcel parcel) {
            return new FondoBean(parcel);
        }

        public FondoBean[] newArray(int i) {
            return new FondoBean[i];
        }
    };
    @SerializedName("aptoSuscrip")
    private String aptoSuscrip;
    @SerializedName("cantidadCuotapartes")
    private String cantidadCuotapartes;
    @SerializedName("honorarios")
    private HonorariosFondosBean honorarios;
    @SerializedName("horarioDesde")
    private String horarioDesde;
    @SerializedName("horarioHasta")
    private String horarioHasta;
    @SerializedName("horarios")
    private HorariosFondosBean horarios;
    @SerializedName("idFondo")

    /* renamed from: id reason: collision with root package name */
    private String f258id;
    @SerializedName("importe")
    private String importe;
    @SerializedName("leyendas")
    private LegalesFondosBean listaLegales;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("plazoPago")
    private String plazoPago;
    @SerializedName("valorCuota")
    private String valorCuota;
    @SerializedName("valorCuotaparte")
    private String valorCuotaParte;
    @SerializedName("valorUltimoAno")
    private String valorUltimoAno;
    @SerializedName("valorUltimoDia")
    private String valorUltimoDia;
    @SerializedName("valorUltimoMes")
    private String valorUltimoMes;
    @SerializedName("valorUltimoTrimestre")
    private String valorUltimoTrimestre;
    @SerializedName("variacionCotizaDiaria")
    private String variacionCotizaDiaria;

    public int describeContents() {
        return 0;
    }

    public FondoBean() {
    }

    public FondoBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, HonorariosFondosBean honorariosFondosBean, LegalesFondosBean legalesFondosBean) {
        this.f258id = str;
        this.nombre = str2;
        this.aptoSuscrip = str3;
        this.variacionCotizaDiaria = str4;
        this.cantidadCuotapartes = str5;
        this.valorCuotaParte = str6;
        this.importe = str7;
        this.moneda = str8;
        this.plazoPago = str9;
        this.horarioDesde = str10;
        this.horarioHasta = str11;
        this.honorarios = honorariosFondosBean;
        this.listaLegales = legalesFondosBean;
    }

    public FondoBean(String str, String str2) {
        this.f258id = str;
        this.nombre = str2;
    }

    public FondoBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.valorUltimoAno = str;
        this.valorUltimoTrimestre = str2;
        this.valorUltimoMes = str3;
        this.valorUltimoDia = str4;
        this.valorCuota = str5;
        this.f258id = str6;
        this.nombre = str7;
    }

    public FondoBean(String str, String str2, HonorariosFondosBean honorariosFondosBean, HorariosFondosBean horariosFondosBean) {
        this.f258id = str;
        this.nombre = str2;
        this.honorarios = honorariosFondosBean;
        this.horarios = horariosFondosBean;
    }

    protected FondoBean(Parcel parcel) {
        this.f258id = parcel.readString();
        this.nombre = parcel.readString();
        this.aptoSuscrip = parcel.readString();
        this.variacionCotizaDiaria = parcel.readString();
        this.cantidadCuotapartes = parcel.readString();
        this.valorCuotaParte = parcel.readString();
        this.importe = parcel.readString();
        this.moneda = parcel.readString();
        this.plazoPago = parcel.readString();
        this.horarioDesde = parcel.readString();
        this.horarioHasta = parcel.readString();
        this.honorarios = (HonorariosFondosBean) parcel.readParcelable(HonorariosFondosBean.class.getClassLoader());
        this.listaLegales = (LegalesFondosBean) parcel.readParcelable(LegalesFondosBean.class.getClassLoader());
        this.valorCuota = parcel.readString();
        this.valorUltimoDia = parcel.readString();
        this.valorUltimoMes = parcel.readString();
        this.valorUltimoTrimestre = parcel.readString();
        this.valorUltimoAno = parcel.readString();
        this.horarios = (HorariosFondosBean) parcel.readParcelable(HorariosFondosBean.class.getClassLoader());
    }

    public String getId() {
        return this.f258id;
    }

    public void setId(String str) {
        this.f258id = str;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getAptoSuscrip() {
        return this.aptoSuscrip;
    }

    public void setAptoSuscrip(String str) {
        this.aptoSuscrip = str;
    }

    public String getVariacionCotizaDiaria() {
        return this.variacionCotizaDiaria;
    }

    public void setVariacionCotizaDiaria(String str) {
        this.variacionCotizaDiaria = str;
    }

    public String getCantidadCuotapartes() {
        return this.cantidadCuotapartes;
    }

    public void setCantidadCuotapartes(String str) {
        this.cantidadCuotapartes = str;
    }

    public String getValorCuotaParte() {
        return this.valorCuotaParte;
    }

    public void setValorCuotaParte(String str) {
        this.valorCuotaParte = str;
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

    public String getPlazoPago() {
        return this.plazoPago;
    }

    public void setPlazoPago(String str) {
        this.plazoPago = str;
    }

    public String getHorarioDesde() {
        return this.horarioDesde;
    }

    public void setHorarioDesde(String str) {
        this.horarioDesde = str;
    }

    public String getHorarioHasta() {
        return this.horarioHasta;
    }

    public void setHorarioHasta(String str) {
        this.horarioHasta = str;
    }

    public HonorariosFondosBean getHonorarios() {
        return this.honorarios;
    }

    public void setHonorarios(HonorariosFondosBean honorariosFondosBean) {
        this.honorarios = honorariosFondosBean;
    }

    public LegalesFondosBean getListaLegales() {
        return this.listaLegales;
    }

    public void setListaLegales(LegalesFondosBean legalesFondosBean) {
        this.listaLegales = legalesFondosBean;
    }

    public String getValorCuota() {
        return this.valorCuota;
    }

    public void setValorCuota(String str) {
        this.valorCuota = str;
    }

    public String getValorUltimoDia() {
        return this.valorUltimoDia;
    }

    public void setValorUltimoDia(String str) {
        this.valorUltimoDia = str;
    }

    public String getValorUltimoMes() {
        return this.valorUltimoMes;
    }

    public void setValorUltimoMes(String str) {
        this.valorUltimoMes = str;
    }

    public String getValorUltimoTrimestre() {
        return this.valorUltimoTrimestre;
    }

    public void setValorUltimoTrimestre(String str) {
        this.valorUltimoTrimestre = str;
    }

    public String getValorUltimoAno() {
        return this.valorUltimoAno;
    }

    public void setValorUltimoAno(String str) {
        this.valorUltimoAno = str;
    }

    public HorariosFondosBean getHorarios() {
        return this.horarios;
    }

    public void setHorarios(HorariosFondosBean horariosFondosBean) {
        this.horarios = horariosFondosBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f258id);
        parcel.writeString(this.nombre);
        parcel.writeString(this.aptoSuscrip);
        parcel.writeString(this.variacionCotizaDiaria);
        parcel.writeString(this.cantidadCuotapartes);
        parcel.writeString(this.valorCuotaParte);
        parcel.writeString(this.importe);
        parcel.writeString(this.moneda);
        parcel.writeString(this.plazoPago);
        parcel.writeString(this.horarioDesde);
        parcel.writeString(this.horarioHasta);
        parcel.writeParcelable(this.honorarios, i);
        parcel.writeParcelable(this.listaLegales, i);
        parcel.writeString(this.valorCuota);
        parcel.writeString(this.valorUltimoDia);
        parcel.writeString(this.valorUltimoMes);
        parcel.writeString(this.valorUltimoTrimestre);
        parcel.writeString(this.valorUltimoAno);
        parcel.writeParcelable(this.horarios, i);
    }
}
