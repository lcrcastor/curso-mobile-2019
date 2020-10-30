package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AlertaEvaluacionRiesgoBean implements Parcelable {
    public static final Creator<AlertaEvaluacionRiesgoBean> CREATOR = new Creator<AlertaEvaluacionRiesgoBean>() {
        public AlertaEvaluacionRiesgoBean createFromParcel(Parcel parcel) {
            return new AlertaEvaluacionRiesgoBean(parcel);
        }

        public AlertaEvaluacionRiesgoBean[] newArray(int i) {
            return new AlertaEvaluacionRiesgoBean[i];
        }
    };
    @SerializedName("cabecera")
    private String cabecera;
    @SerializedName("cantidadDisclaimer")
    private String cantidadDisclaimer;
    @SerializedName("disclaimers")
    private ListaDisclaimersFondo disclaimers;
    @SerializedName("pie")
    private String pie;

    public int describeContents() {
        return 0;
    }

    public AlertaEvaluacionRiesgoBean(String str, String str2, ListaDisclaimersFondo listaDisclaimersFondo, String str3) {
        this.cabecera = str;
        this.cantidadDisclaimer = str2;
        this.disclaimers = listaDisclaimersFondo;
        this.pie = str3;
    }

    public AlertaEvaluacionRiesgoBean() {
    }

    protected AlertaEvaluacionRiesgoBean(Parcel parcel) {
        this.cabecera = parcel.readString();
        this.cantidadDisclaimer = parcel.readString();
        this.disclaimers = (ListaDisclaimersFondo) parcel.readParcelable(ListaDisclaimersFondo.class.getClassLoader());
        this.pie = parcel.readString();
    }

    public String getCabecera() {
        return this.cabecera;
    }

    public void setCabecera(String str) {
        this.cabecera = str;
    }

    public String getCantidadDisclaimer() {
        return this.cantidadDisclaimer;
    }

    public void setCantidadDisclaimer(String str) {
        this.cantidadDisclaimer = str;
    }

    public ListaDisclaimersFondo getDisclaimers() {
        return this.disclaimers;
    }

    public void setDisclaimers(ListaDisclaimersFondo listaDisclaimersFondo) {
        this.disclaimers = listaDisclaimersFondo;
    }

    public String getPie() {
        return this.pie;
    }

    public void setPie(String str) {
        this.pie = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.cabecera);
        parcel.writeString(this.cantidadDisclaimer);
        parcel.writeParcelable(this.disclaimers, i);
        parcel.writeString(this.pie);
    }
}
