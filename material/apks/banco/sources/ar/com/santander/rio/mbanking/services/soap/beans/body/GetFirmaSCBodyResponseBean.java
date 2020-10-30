package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetFirmaSCBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetFirmaSCBodyResponseBean> CREATOR = new Creator<GetFirmaSCBodyResponseBean>() {
        public GetFirmaSCBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetFirmaSCBodyResponseBean(parcel);
        }

        public GetFirmaSCBodyResponseBean[] newArray(int i) {
            return new GetFirmaSCBodyResponseBean[i];
        }
    };
    @SerializedName("firmaSuperclub")
    private String firmaSuperClub;

    public int describeContents() {
        return 0;
    }

    public GetFirmaSCBodyResponseBean() {
    }

    public GetFirmaSCBodyResponseBean(String str) {
        this.firmaSuperClub = str;
    }

    protected GetFirmaSCBodyResponseBean(Parcel parcel) {
        this.firmaSuperClub = parcel.readString();
    }

    public String getFirmaSuperClub() {
        return this.firmaSuperClub;
    }

    public void setFirmaSuperClub(String str) {
        this.firmaSuperClub = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.firmaSuperClub);
    }
}
