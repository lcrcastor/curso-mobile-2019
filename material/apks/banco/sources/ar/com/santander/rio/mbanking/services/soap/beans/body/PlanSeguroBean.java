package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class PlanSeguroBean implements Parcelable {
    public static final Creator<PlanSeguroBean> CREATOR = new Creator<PlanSeguroBean>() {
        public PlanSeguroBean createFromParcel(Parcel parcel) {
            return new PlanSeguroBean(parcel);
        }

        public PlanSeguroBean[] newArray(int i) {
            return new PlanSeguroBean[i];
        }
    };
    @SerializedName("cantTarjetas")
    private String cantTarjetas;
    @SerializedName("codPlan")
    private String codPlan;
    @SerializedName("cuota")
    private String cuota;
    @SerializedName("desc")
    private String desc;
    @SerializedName("descripcionSuma1")
    private String descripcionSuma1;
    @SerializedName("descripcionSuma2")
    private String descripcionSuma2;
    private boolean isChecked;
    private int listPosition;
    @SerializedName("listaCoberturas")
    private ListaCoberturas listaCoberturas;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("sumaAsegurada")
    private String sumaAsegurada;
    @SerializedName("sumaAsegurada1")
    private String sumaAsegurada1;
    @SerializedName("sumaAsegurada2")
    private String sumaAsegurada2;

    public int describeContents() {
        return 0;
    }

    public PlanSeguroBean() {
    }

    protected PlanSeguroBean(Parcel parcel) {
        this.codPlan = parcel.readString();
        this.nombre = parcel.readString();
        this.desc = parcel.readString();
        this.cuota = parcel.readString();
        this.descripcionSuma1 = parcel.readString();
        this.sumaAsegurada = parcel.readString();
        this.listaCoberturas = (ListaCoberturas) parcel.readParcelable(ListaCoberturas.class.getClassLoader());
        this.sumaAsegurada1 = parcel.readString();
        this.descripcionSuma2 = parcel.readString();
        this.sumaAsegurada2 = parcel.readString();
        this.cantTarjetas = parcel.readString();
        this.isChecked = parcel.readByte() != 0;
        this.listPosition = parcel.readInt();
    }

    public String getCodPlan() {
        return this.codPlan;
    }

    public void setCodPlan(String str) {
        this.codPlan = str;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public String getCuota() {
        return this.cuota;
    }

    public void setCuota(String str) {
        this.cuota = str;
    }

    public String getDescripcionSuma1() {
        return this.descripcionSuma1;
    }

    public void setDescripcionSuma1(String str) {
        this.descripcionSuma1 = str;
    }

    public String getSumaAsegurada() {
        return this.sumaAsegurada;
    }

    public void setSumaAsegurada(String str) {
        this.sumaAsegurada = str;
    }

    public String getSumaAsegurada1() {
        return this.sumaAsegurada1;
    }

    public void setSumaAsegurada1(String str) {
        this.sumaAsegurada1 = str;
    }

    public String getDescripcionSuma2() {
        return this.descripcionSuma2;
    }

    public void setDescripcionSuma2(String str) {
        this.descripcionSuma2 = str;
    }

    public String getSumaAsegurada2() {
        return this.sumaAsegurada2;
    }

    public void setSumaAsegurada2(String str) {
        this.sumaAsegurada2 = str;
    }

    public String getCantTarjetas() {
        return this.cantTarjetas;
    }

    public void setCantTarjetas(String str) {
        this.cantTarjetas = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public int getListPosition() {
        return this.listPosition;
    }

    public void setListPosition(int i) {
        this.listPosition = i;
    }

    public ListaCoberturas getListaCoberturas() {
        return this.listaCoberturas;
    }

    public void setListaCoberturas(ListaCoberturas listaCoberturas2) {
        this.listaCoberturas = listaCoberturas2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codPlan);
        parcel.writeString(this.nombre);
        parcel.writeString(this.desc);
        parcel.writeString(this.cuota);
        parcel.writeString(this.descripcionSuma1);
        parcel.writeString(this.sumaAsegurada);
        parcel.writeParcelable(this.listaCoberturas, i);
        parcel.writeString(this.sumaAsegurada1);
        parcel.writeString(this.descripcionSuma2);
        parcel.writeString(this.sumaAsegurada2);
        parcel.writeString(this.cantTarjetas);
        parcel.writeByte(this.isChecked ? (byte) 1 : 0);
        parcel.writeInt(this.listPosition);
    }
}
