package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class PaisBean implements Parcelable {
    public static final Creator<PaisBean> CREATOR = new Creator<PaisBean>() {
        public PaisBean createFromParcel(Parcel parcel) {
            return new PaisBean(parcel);
        }

        public PaisBean[] newArray(int i) {
            return new PaisBean[i];
        }
    };
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("id")

    /* renamed from: id reason: collision with root package name */
    private String f264id;

    public int describeContents() {
        return 0;
    }

    public PaisBean() {
    }

    public PaisBean(String str, String str2) {
        this.f264id = str;
        this.descripcion = str2;
    }

    public String getId() {
        return this.f264id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setId(String str) {
        this.f264id = str;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f264id);
        parcel.writeString(this.descripcion);
    }

    protected PaisBean(Parcel parcel) {
        this.f264id = parcel.readString();
        this.descripcion = parcel.readString();
    }
}
