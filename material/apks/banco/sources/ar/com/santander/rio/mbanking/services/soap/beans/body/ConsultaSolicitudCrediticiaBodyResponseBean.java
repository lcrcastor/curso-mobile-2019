package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.model.creditos.ConsultaCalificacionCrediticia;
import com.google.gson.annotations.SerializedName;

public class ConsultaSolicitudCrediticiaBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<ConsultaSolicitudCrediticiaBodyResponseBean> CREATOR = new Creator<ConsultaSolicitudCrediticiaBodyResponseBean>() {
        public ConsultaSolicitudCrediticiaBodyResponseBean createFromParcel(Parcel parcel) {
            return new ConsultaSolicitudCrediticiaBodyResponseBean(parcel);
        }

        public ConsultaSolicitudCrediticiaBodyResponseBean[] newArray(int i) {
            return new ConsultaSolicitudCrediticiaBodyResponseBean[i];
        }
    };
    @SerializedName("consCalifCred")
    ConsultaCalificacionCrediticia consultaCalificacionCrediticia;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public ConsultaSolicitudCrediticiaBodyResponseBean(Parcel parcel) {
    }

    public ConsultaSolicitudCrediticiaBodyResponseBean() {
    }

    public ConsultaCalificacionCrediticia getConsultaCalificacionCrediticia() {
        return this.consultaCalificacionCrediticia;
    }

    public void setConsultaCalificacionCrediticia(ConsultaCalificacionCrediticia consultaCalificacionCrediticia2) {
        this.consultaCalificacionCrediticia = consultaCalificacionCrediticia2;
    }
}
