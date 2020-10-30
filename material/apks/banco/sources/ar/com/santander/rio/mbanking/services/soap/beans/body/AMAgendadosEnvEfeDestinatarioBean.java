package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AMAgendadosEnvEfeDestinatarioBean implements Parcelable {
    public static final Creator<AMAgendadosEnvEfeDestinatarioBean> CREATOR = new Creator<AMAgendadosEnvEfeDestinatarioBean>() {
        public AMAgendadosEnvEfeDestinatarioBean createFromParcel(Parcel parcel) {
            return new AMAgendadosEnvEfeDestinatarioBean(parcel);
        }

        public AMAgendadosEnvEfeDestinatarioBean[] newArray(int i) {
            return new AMAgendadosEnvEfeDestinatarioBean[i];
        }
    };
    public String emailDest;
    public String nombreDest;
    public String numeroDocDest;
    public String tipoDocDest;

    public int describeContents() {
        return 0;
    }

    public AMAgendadosEnvEfeDestinatarioBean() {
    }

    public AMAgendadosEnvEfeDestinatarioBean(Parcel parcel) {
        this.nombreDest = parcel.readString();
        this.tipoDocDest = parcel.readString();
        this.numeroDocDest = parcel.readString();
        this.emailDest = parcel.readString();
    }

    public AMAgendadosEnvEfeDestinatarioBean(String str, String str2, String str3, String str4) {
        this.nombreDest = str;
        this.tipoDocDest = str2;
        this.numeroDocDest = str3;
        this.emailDest = str4;
    }

    public boolean equals(AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean) {
        return this.nombreDest.equals(aMAgendadosEnvEfeDestinatarioBean.nombreDest) && this.tipoDocDest.equals(aMAgendadosEnvEfeDestinatarioBean.tipoDocDest) && this.numeroDocDest.equals(aMAgendadosEnvEfeDestinatarioBean.numeroDocDest) && this.emailDest.equals(aMAgendadosEnvEfeDestinatarioBean.emailDest);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nombreDest);
        parcel.writeString(this.tipoDocDest);
        parcel.writeString(this.numeroDocDest);
        parcel.writeString(this.emailDest);
    }
}
