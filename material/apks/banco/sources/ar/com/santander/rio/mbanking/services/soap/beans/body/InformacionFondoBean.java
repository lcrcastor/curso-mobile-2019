package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class InformacionFondoBean implements Parcelable {
    public static final Creator<InformacionFondoBean> CREATOR = new Creator<InformacionFondoBean>() {
        public InformacionFondoBean createFromParcel(Parcel parcel) {
            return new InformacionFondoBean(parcel);
        }

        public InformacionFondoBean[] newArray(int i) {
            return new InformacionFondoBean[i];
        }
    };
    @SerializedName("aptoSuscrip")
    private String aptoSuscrip;
    @SerializedName("cartera")
    private String cartera;
    @SerializedName("descripcionCorta")
    private String descripcionCorta;
    @SerializedName("descripcionLarga")
    private String descripcionLarga;
    @SerializedName("honorarios")
    private HonorariosFondosBean honorariosFondosBean;
    @SerializedName("horarioDesde")
    private String horarioDesde;
    @SerializedName("horarioHasta")
    private String horarioHasta;
    @SerializedName("idFondo")

    /* renamed from: id reason: collision with root package name */
    private String f262id;
    @SerializedName("legales")
    private LegalesFondosBean legalesFondosBean;
    @SerializedName("cotizaciones")
    private ListaCotizacionesFondosBean listaCotizacionesFondosBean;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("plazoPago")
    private String plazoPago;
    @SerializedName("reglamento")
    private String reglamento;
    @SerializedName("valorCuotaparte")
    private String valorCuotaParte;

    public int describeContents() {
        return 0;
    }

    public InformacionFondoBean(String str, String str2, String str3, ListaCotizacionesFondosBean listaCotizacionesFondosBean2, String str4, String str5, LegalesFondosBean legalesFondosBean2, String str6, String str7, String str8, String str9, String str10, HonorariosFondosBean honorariosFondosBean2, String str11, String str12) {
        this.f262id = str;
        this.nombre = str2;
        this.aptoSuscrip = str3;
        this.listaCotizacionesFondosBean = listaCotizacionesFondosBean2;
        this.descripcionCorta = str4;
        this.descripcionLarga = str5;
        this.legalesFondosBean = legalesFondosBean2;
        this.valorCuotaParte = str6;
        this.moneda = str7;
        this.plazoPago = str8;
        this.horarioDesde = str9;
        this.horarioHasta = str10;
        this.honorariosFondosBean = honorariosFondosBean2;
        this.reglamento = str11;
        this.cartera = str12;
    }

    public InformacionFondoBean() {
    }

    protected InformacionFondoBean(Parcel parcel) {
        this.f262id = parcel.readString();
        this.nombre = parcel.readString();
        this.aptoSuscrip = parcel.readString();
        this.listaCotizacionesFondosBean = (ListaCotizacionesFondosBean) parcel.readParcelable(ListaCotizacionesFondosBean.class.getClassLoader());
        this.descripcionCorta = parcel.readString();
        this.descripcionLarga = parcel.readString();
        this.legalesFondosBean = (LegalesFondosBean) parcel.readParcelable(LegalesFondosBean.class.getClassLoader());
        this.valorCuotaParte = parcel.readString();
        this.moneda = parcel.readString();
        this.plazoPago = parcel.readString();
        this.horarioDesde = parcel.readString();
        this.horarioHasta = parcel.readString();
        this.honorariosFondosBean = (HonorariosFondosBean) parcel.readParcelable(HonorariosFondosBean.class.getClassLoader());
        this.reglamento = parcel.readString();
        this.cartera = parcel.readString();
    }

    public String getId() {
        return this.f262id;
    }

    public void setId(String str) {
        this.f262id = str;
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

    public ListaCotizacionesFondosBean getListaCotizacionesFondosBean() {
        return this.listaCotizacionesFondosBean;
    }

    public void setListaCotizacionesFondosBean(ListaCotizacionesFondosBean listaCotizacionesFondosBean2) {
        this.listaCotizacionesFondosBean = listaCotizacionesFondosBean2;
    }

    public String getDescripcionCorta() {
        return this.descripcionCorta;
    }

    public void setDescripcionCorta(String str) {
        this.descripcionCorta = str;
    }

    public String getDescripcionLarga() {
        return this.descripcionLarga;
    }

    public void setDescripcionLarga(String str) {
        this.descripcionLarga = str;
    }

    public LegalesFondosBean getLegalesFondosBean() {
        return this.legalesFondosBean;
    }

    public void setLegalesFondosBean(LegalesFondosBean legalesFondosBean2) {
        this.legalesFondosBean = legalesFondosBean2;
    }

    public String getValorCuotaParte() {
        return this.valorCuotaParte;
    }

    public void setValorCuotaParte(String str) {
        this.valorCuotaParte = str;
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

    public HonorariosFondosBean getHonorariosFondosBean() {
        return this.honorariosFondosBean;
    }

    public void setHonorariosFondosBean(HonorariosFondosBean honorariosFondosBean2) {
        this.honorariosFondosBean = honorariosFondosBean2;
    }

    public String getReglamento() {
        return this.reglamento;
    }

    public void setReglamento(String str) {
        this.reglamento = str;
    }

    public String getCartera() {
        return this.cartera;
    }

    public void setCartera(String str) {
        this.cartera = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f262id);
        parcel.writeString(this.nombre);
        parcel.writeString(this.aptoSuscrip);
        parcel.writeParcelable(this.listaCotizacionesFondosBean, i);
        parcel.writeString(this.descripcionCorta);
        parcel.writeString(this.descripcionLarga);
        parcel.writeParcelable(this.legalesFondosBean, i);
        parcel.writeString(this.valorCuotaParte);
        parcel.writeString(this.moneda);
        parcel.writeString(this.plazoPago);
        parcel.writeString(this.horarioDesde);
        parcel.writeString(this.horarioHasta);
        parcel.writeParcelable(this.honorariosFondosBean, i);
        parcel.writeString(this.reglamento);
        parcel.writeString(this.cartera);
    }
}
