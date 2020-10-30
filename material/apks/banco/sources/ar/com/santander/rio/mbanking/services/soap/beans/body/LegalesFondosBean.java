package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class LegalesFondosBean implements Parcelable {
    public static final Creator<LegalesFondosBean> CREATOR = new Creator<LegalesFondosBean>() {
        public LegalesFondosBean createFromParcel(Parcel parcel) {
            return new LegalesFondosBean(parcel);
        }

        public LegalesFondosBean[] newArray(int i) {
            return new LegalesFondosBean[i];
        }
    };
    @SerializedName("legal")
    private ArrayList<String> legal;
    @SerializedName("leyendaLegal")
    private ArrayList<String> leyendaLegales;

    public int describeContents() {
        return 0;
    }

    public LegalesFondosBean(ArrayList<String> arrayList) {
        this.leyendaLegales = arrayList;
        this.legal = arrayList;
    }

    public LegalesFondosBean() {
    }

    protected LegalesFondosBean(Parcel parcel) {
        this.leyendaLegales = new ArrayList<>();
        parcel.readList(this.leyendaLegales, String.class.getClassLoader());
        this.legal = new ArrayList<>();
        parcel.readList(this.legal, String.class.getClassLoader());
    }

    public List<String> getLeyendaLegales() {
        if (this.leyendaLegales == null || this.leyendaLegales.size() <= 0) {
            return this.legal;
        }
        return this.leyendaLegales;
    }

    public void setLeyendaLegales(ArrayList<String> arrayList) {
        this.leyendaLegales = arrayList;
        this.legal = arrayList;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.leyendaLegales);
        parcel.writeList(this.legal);
    }
}
