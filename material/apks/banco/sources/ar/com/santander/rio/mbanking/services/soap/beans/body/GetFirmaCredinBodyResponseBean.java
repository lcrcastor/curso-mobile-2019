package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetFirmaCredinBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetFirmaCredinBodyResponseBean> CREATOR = new Creator<GetFirmaCredinBodyResponseBean>() {
        public GetFirmaCredinBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetFirmaCredinBodyResponseBean(parcel);
        }

        public GetFirmaCredinBodyResponseBean[] newArray(int i) {
            return new GetFirmaCredinBodyResponseBean[i];
        }
    };
    @SerializedName("firma")
    private String firma;
    @SerializedName("url")
    private String url;

    public int describeContents() {
        return 0;
    }

    protected GetFirmaCredinBodyResponseBean(Parcel parcel) {
        this.firma = (String) parcel.readValue(String.class.getClassLoader());
        this.url = (String) parcel.readValue(String.class.getClassLoader());
    }

    public GetFirmaCredinBodyResponseBean() {
    }

    public GetFirmaCredinBodyResponseBean(String str, String str2) {
        this.firma = str2;
        this.url = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.firma);
        parcel.writeValue(this.url);
    }

    public String getFirma() {
        return this.firma;
    }

    public void setFirma(String str) {
        this.firma = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
