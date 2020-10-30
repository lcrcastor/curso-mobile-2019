package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaTarjetas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuscripcionWomenBodyRequestBean implements Parcelable {
    public static final Creator<SuscripcionWomenBodyRequestBean> CREATOR = new Creator<SuscripcionWomenBodyRequestBean>() {
        public SuscripcionWomenBodyRequestBean createFromParcel(Parcel parcel) {
            return new SuscripcionWomenBodyRequestBean(parcel);
        }

        public SuscripcionWomenBodyRequestBean[] newArray(int i) {
            return new SuscripcionWomenBodyRequestBean[i];
        }
    };
    @SerializedName("listaTarjetas")
    @Expose
    private ListaTarjetas listaTarjetas;
    @SerializedName("operacion")
    @Expose
    private String operacion;

    public int describeContents() {
        return 0;
    }

    protected SuscripcionWomenBodyRequestBean(Parcel parcel) {
        this.operacion = (String) parcel.readValue(String.class.getClassLoader());
        this.listaTarjetas = (ListaTarjetas) parcel.readValue(ListaTarjetas.class.getClassLoader());
    }

    public SuscripcionWomenBodyRequestBean() {
    }

    public SuscripcionWomenBodyRequestBean(String str, ListaTarjetas listaTarjetas2) {
        this.operacion = str;
        this.listaTarjetas = listaTarjetas2;
    }

    public String getOperacion() {
        return this.operacion;
    }

    public void setOperacion(String str) {
        this.operacion = str;
    }

    public ListaTarjetas getListaTarjetas() {
        return this.listaTarjetas;
    }

    public void setListaTarjetas(ListaTarjetas listaTarjetas2) {
        this.listaTarjetas = listaTarjetas2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.operacion);
        parcel.writeValue(this.listaTarjetas);
    }
}
