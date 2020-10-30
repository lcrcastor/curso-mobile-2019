package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaPreguntas implements Parcelable {
    public static final Creator<ListaPreguntas> CREATOR = new Creator<ListaPreguntas>() {
        public ListaPreguntas createFromParcel(Parcel parcel) {
            return new ListaPreguntas(parcel);
        }

        public ListaPreguntas[] newArray(int i) {
            return new ListaPreguntas[i];
        }
    };
    @SerializedName("pregunta")
    @Expose
    private List<String> pregunta = new ArrayList();

    public int describeContents() {
        return 0;
    }

    protected ListaPreguntas(Parcel parcel) {
        parcel.readList(this.pregunta, String.class.getClassLoader());
    }

    public ListaPreguntas() {
    }

    public ListaPreguntas(List<String> list) {
        this.pregunta = list;
    }

    public List<String> getPregunta() {
        return this.pregunta;
    }

    public void setPregunta(List<String> list) {
        this.pregunta = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.pregunta);
    }
}
