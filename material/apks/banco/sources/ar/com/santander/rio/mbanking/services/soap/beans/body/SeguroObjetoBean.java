package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class SeguroObjetoBean extends SeguroBean implements Parcelable {
    public static final Creator<SeguroObjetoBean> CREATOR = new Creator<SeguroObjetoBean>() {
        public SeguroObjetoBean createFromParcel(Parcel parcel) {
            return new SeguroObjetoBean(parcel);
        }

        public SeguroObjetoBean[] newArray(int i) {
            return new SeguroObjetoBean[i];
        }
    };
    @SerializedName("codFamilia")
    private String codFamilia;
    @SerializedName("nombreFamilia")
    private String nombreFamilia;

    public int describeContents() {
        return 0;
    }

    public SeguroObjetoBean() {
    }

    protected SeguroObjetoBean(Parcel parcel) {
        this.codFamilia = parcel.readString();
        this.nombreFamilia = parcel.readString();
    }

    public String getCodFamilia() {
        return this.codFamilia;
    }

    public void setCodFamilia(String str) {
        this.codFamilia = str;
    }

    public String getNombreFamilia() {
        return this.nombreFamilia;
    }

    public void setNombreFamilia(String str) {
        this.nombreFamilia = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codFamilia);
        parcel.writeString(this.nombreFamilia);
    }
}
