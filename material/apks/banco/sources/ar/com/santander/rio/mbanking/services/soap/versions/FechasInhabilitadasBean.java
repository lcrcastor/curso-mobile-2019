package ar.com.santander.rio.mbanking.services.soap.versions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FechasInhabilitadasBean implements Parcelable {
    public static final Creator<FechasInhabilitadasBean> CREATOR = new Creator<FechasInhabilitadasBean>() {
        public FechasInhabilitadasBean createFromParcel(Parcel parcel) {
            return new FechasInhabilitadasBean(parcel);
        }

        public FechasInhabilitadasBean[] newArray(int i) {
            return new FechasInhabilitadasBean[i];
        }
    };
    @SerializedName("fecha")
    private List<String> listaFechas;

    public int describeContents() {
        return 0;
    }

    public FechasInhabilitadasBean(List<String> list) {
        this.listaFechas = list;
    }

    public FechasInhabilitadasBean() {
    }

    public List<String> getListaFechas() {
        return this.listaFechas;
    }

    public void setListaFechas(List<String> list) {
        this.listaFechas = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.listaFechas);
    }

    protected FechasInhabilitadasBean(Parcel parcel) {
        this.listaFechas = parcel.createStringArrayList();
    }
}
