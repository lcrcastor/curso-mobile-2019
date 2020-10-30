package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MensajeRecalificacionBean implements Parcelable {
    public static final Creator<MensajeRecalificacionBean> CREATOR = new Creator<MensajeRecalificacionBean>() {
        public MensajeRecalificacionBean createFromParcel(Parcel parcel) {
            return new MensajeRecalificacionBean(parcel);
        }

        public MensajeRecalificacionBean[] newArray(int i) {
            return new MensajeRecalificacionBean[i];
        }
    };
    public String msjCod;
    public String msjDesc;

    public int describeContents() {
        return 0;
    }

    protected MensajeRecalificacionBean(Parcel parcel) {
        this.msjCod = parcel.readString();
        this.msjDesc = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.msjCod);
        parcel.writeString(this.msjDesc);
    }
}
