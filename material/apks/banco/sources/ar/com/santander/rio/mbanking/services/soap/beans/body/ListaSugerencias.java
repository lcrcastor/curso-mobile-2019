package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaSugerencias implements Parcelable {
    public static final Creator<ListaSugerencias> CREATOR = new Creator<ListaSugerencias>() {
        public ListaSugerencias createFromParcel(Parcel parcel) {
            return new ListaSugerencias(parcel);
        }

        public ListaSugerencias[] newArray(int i) {
            return new ListaSugerencias[i];
        }
    };
    @SerializedName("sugerencia")
    @Expose
    private List<String> sugerencia = new ArrayList();

    public int describeContents() {
        return 0;
    }

    protected ListaSugerencias(Parcel parcel) {
        parcel.readList(this.sugerencia, String.class.getClassLoader());
    }

    public ListaSugerencias() {
    }

    public ListaSugerencias(List<String> list) {
        this.sugerencia = list;
    }

    public List<String> getSugerencia() {
        return this.sugerencia;
    }

    public void setSugerencia(List<String> list) {
        this.sugerencia = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.sugerencia);
    }
}
