package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinkSugerencia implements Parcelable {
    public static final Creator<LinkSugerencia> CREATOR = new Creator<LinkSugerencia>() {
        public LinkSugerencia createFromParcel(Parcel parcel) {
            return new LinkSugerencia(parcel);
        }

        public LinkSugerencia[] newArray(int i) {
            return new LinkSugerencia[i];
        }
    };
    @SerializedName("resCod")
    @Expose
    private String resCod;
    @SerializedName("resDesc")
    @Expose
    private String resDesc;

    public int describeContents() {
        return 0;
    }

    protected LinkSugerencia(Parcel parcel) {
        this.resCod = (String) parcel.readValue(String.class.getClassLoader());
        this.resDesc = (String) parcel.readValue(String.class.getClassLoader());
    }

    public LinkSugerencia() {
    }

    public LinkSugerencia(String str, String str2) {
        this.resCod = str;
        this.resDesc = str2;
    }

    public String getResCod() {
        return this.resCod;
    }

    public void setResCod(String str) {
        this.resCod = str;
    }

    public String getResDesc() {
        return this.resDesc;
    }

    public void setResDesc(String str) {
        this.resDesc = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.resCod);
        parcel.writeValue(this.resDesc);
    }
}
