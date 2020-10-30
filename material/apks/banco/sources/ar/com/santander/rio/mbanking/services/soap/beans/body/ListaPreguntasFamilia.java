package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ListaPreguntasFamilia implements Parcelable {
    public static final Creator<ListaPreguntasFamilia> CREATOR = new Creator<ListaPreguntasFamilia>() {
        public ListaPreguntasFamilia createFromParcel(Parcel parcel) {
            ListaPreguntasFamilia listaPreguntasFamilia = new ListaPreguntasFamilia();
            listaPreguntasFamilia.pregunta = parcel.readBundle(PreguntasFamiliaBean.class.getClassLoader()).getParcelableArrayList(ListaPreguntasFamilia.LISTA_PREGUNTAS_ITEM);
            return listaPreguntasFamilia;
        }

        public ListaPreguntasFamilia[] newArray(int i) {
            return new ListaPreguntasFamilia[i];
        }
    };
    private static final String LISTA_PREGUNTAS_ITEM = "LISTA_PREGUNTAS_ITEM";
    /* access modifiers changed from: private */
    @SerializedName("pregunta")
    public List<PreguntasFamiliaBean> pregunta;

    public int describeContents() {
        return 0;
    }

    public ListaPreguntasFamilia() {
    }

    protected ListaPreguntasFamilia(Parcel parcel) {
        this.pregunta = (List) parcel.readParcelable(PreguntasFamiliaBean.class.getClassLoader());
    }

    public List<PreguntasFamiliaBean> getPregunta() {
        return this.pregunta;
    }

    public void setPregunta(List<PreguntasFamiliaBean> list) {
        this.pregunta = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_PREGUNTAS_ITEM, (ArrayList) this.pregunta);
        parcel.writeBundle(bundle);
    }
}
