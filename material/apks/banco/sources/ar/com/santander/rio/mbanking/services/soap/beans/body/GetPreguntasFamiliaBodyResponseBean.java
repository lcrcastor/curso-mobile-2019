package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GetPreguntasFamiliaBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GetPreguntasFamiliaBodyResponseBean> CREATOR = new Creator<GetPreguntasFamiliaBodyResponseBean>() {
        public GetPreguntasFamiliaBodyResponseBean createFromParcel(Parcel parcel) {
            return new GetPreguntasFamiliaBodyResponseBean(parcel);
        }

        public GetPreguntasFamiliaBodyResponseBean[] newArray(int i) {
            return new GetPreguntasFamiliaBodyResponseBean[i];
        }
    };
    @SerializedName("listaPreguntas")
    public ListaPreguntasFamilia listaPreguntas;

    public int describeContents() {
        return 0;
    }

    public GetPreguntasFamiliaBodyResponseBean() {
    }

    public GetPreguntasFamiliaBodyResponseBean(ListaPreguntasFamilia listaPreguntasFamilia) {
        this.listaPreguntas = listaPreguntasFamilia;
    }

    protected GetPreguntasFamiliaBodyResponseBean(Parcel parcel) {
        this.listaPreguntas = (ListaPreguntasFamilia) parcel.readParcelable(ListaPreguntasFamilia.class.getClassLoader());
    }

    public ListaPreguntasFamilia getListaPreguntas() {
        return this.listaPreguntas;
    }

    public void setListaPreguntas(ListaPreguntasFamilia listaPreguntasFamilia) {
        this.listaPreguntas = listaPreguntasFamilia;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.listaPreguntas, i);
    }
}
