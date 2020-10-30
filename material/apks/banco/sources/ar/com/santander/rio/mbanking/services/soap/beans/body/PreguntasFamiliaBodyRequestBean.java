package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class PreguntasFamiliaBodyRequestBean implements Parcelable {
    public static final Creator<PreguntasFamiliaBodyRequestBean> CREATOR = new Creator<PreguntasFamiliaBodyRequestBean>() {
        public PreguntasFamiliaBodyRequestBean createFromParcel(Parcel parcel) {
            return new PreguntasFamiliaBodyRequestBean(parcel);
        }

        public PreguntasFamiliaBodyRequestBean[] newArray(int i) {
            return new PreguntasFamiliaBodyRequestBean[i];
        }
    };
    @SerializedName("idFamilia")
    private String idFamilia;

    public int describeContents() {
        return 0;
    }

    public PreguntasFamiliaBodyRequestBean(String str) {
        this.idFamilia = str;
    }

    public PreguntasFamiliaBodyRequestBean() {
    }

    protected PreguntasFamiliaBodyRequestBean(Parcel parcel) {
        this.idFamilia = parcel.readString();
    }

    public String getIdFamilia() {
        return this.idFamilia;
    }

    public void setIdFamilia(String str) {
        this.idFamilia = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idFamilia);
    }
}
