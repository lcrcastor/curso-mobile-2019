package ar.com.santander.rio.mbanking.services.model.creditos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountResponseBean;
import com.google.gson.annotations.SerializedName;

public class ConsultaCalificacionCrediticia extends ClassLoader implements Parcelable {
    public static final Creator<ConsultaCalificacionCrediticia> CREATOR = new Creator<ConsultaCalificacionCrediticia>() {
        public ConsultaCalificacionCrediticia createFromParcel(Parcel parcel) {
            return new ConsultaCalificacionCrediticia(parcel);
        }

        public ConsultaCalificacionCrediticia[] newArray(int i) {
            return new ConsultaCalificacionCrediticia[i];
        }
    };
    @SerializedName("datosCuenta")
    public AccountResponseBean accountResponseBean;
    @SerializedName("datosCalifCred")
    public DatosCalificacionCrediticia datosCalificacionCrediticia;

    public int describeContents() {
        return 0;
    }

    protected ConsultaCalificacionCrediticia(Parcel parcel) {
        this.accountResponseBean = (AccountResponseBean) parcel.readParcelable(AccountResponseBean.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.accountResponseBean, i);
        parcel.writeParcelable(this.datosCalificacionCrediticia, i);
    }

    public AccountResponseBean getAccountResponseBean() {
        return this.accountResponseBean;
    }

    public void setAccountResponseBean(AccountResponseBean accountResponseBean2) {
        this.accountResponseBean = accountResponseBean2;
    }

    public DatosCalificacionCrediticia getDatosCalificacionCrediticia() {
        return this.datosCalificacionCrediticia;
    }

    public void setDatosCalificacionCrediticia(DatosCalificacionCrediticia datosCalificacionCrediticia2) {
        this.datosCalificacionCrediticia = datosCalificacionCrediticia2;
    }
}
