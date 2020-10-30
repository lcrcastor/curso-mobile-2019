package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Titulos implements Parcelable {
    public static final Creator<Titulos> CREATOR = new Creator<Titulos>() {
        public Titulos createFromParcel(Parcel parcel) {
            return new Titulos(parcel);
        }

        public Titulos[] newArray(int i) {
            return new Titulos[i];
        }
    };
    @SerializedName("titulo")
    private List<Titulo> tituloList;

    public int describeContents() {
        return 0;
    }

    public List<Titulo> getTituloList() {
        return this.tituloList;
    }

    public void setTituloList(List<Titulo> list) {
        this.tituloList = list;
    }

    protected Titulos(Parcel parcel) {
        this.tituloList = parcel.createTypedArrayList(Titulo.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.tituloList);
    }
}
