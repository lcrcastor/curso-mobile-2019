package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class ExtractionLimitBean implements Parcelable, Serializable {
    public static final Creator<ExtractionLimitBean> CREATOR = new Creator<ExtractionLimitBean>() {
        public ExtractionLimitBean createFromParcel(Parcel parcel) {
            return new ExtractionLimitBean(parcel);
        }

        public ExtractionLimitBean[] newArray(int i) {
            return new ExtractionLimitBean[i];
        }
    };
    public String cajeroBanelcoOB;
    public String cajeroBanelcoSR;
    public String cajeroLink;

    public int describeContents() {
        return 0;
    }

    public ExtractionLimitBean() {
    }

    protected ExtractionLimitBean(Parcel parcel) {
        this.cajeroBanelcoSR = parcel.readString();
        this.cajeroBanelcoOB = parcel.readString();
        this.cajeroLink = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.cajeroBanelcoSR);
        parcel.writeString(this.cajeroBanelcoOB);
        parcel.writeString(this.cajeroLink);
    }
}
