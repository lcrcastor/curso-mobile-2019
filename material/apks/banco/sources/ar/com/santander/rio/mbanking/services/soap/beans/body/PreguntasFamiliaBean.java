package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaOpcionesFamiliaObjetos;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class PreguntasFamiliaBean implements Parcelable {
    public static final Creator<PreguntasFamiliaBean> CREATOR = new Creator<PreguntasFamiliaBean>() {
        public PreguntasFamiliaBean createFromParcel(Parcel parcel) {
            return new PreguntasFamiliaBean(parcel);
        }

        public PreguntasFamiliaBean[] newArray(int i) {
            return new PreguntasFamiliaBean[i];
        }
    };
    @SerializedName("cantMaxCaracteres")
    private String cantMaxCaracteres;
    @SerializedName("idPregunta")
    private String idPregunta;
    @SerializedName("listaOpciones")
    private ListaOpcionesFamiliaObjetos listaOpciones;
    @SerializedName("respuesta")
    private Respuesta respuesta;
    @SerializedName("respuestaOpcional")
    private String respuestaOpcional;
    @SerializedName("textoPregunta")
    private String textoPregunta;
    @SerializedName("tipoRespuesta")
    private String tipoRespuesta;

    public int describeContents() {
        return 0;
    }

    public PreguntasFamiliaBean() {
    }

    protected PreguntasFamiliaBean(Parcel parcel) {
        this.idPregunta = parcel.readString();
        this.textoPregunta = parcel.readString();
        this.tipoRespuesta = parcel.readString();
        this.cantMaxCaracteres = parcel.readString();
        this.respuestaOpcional = parcel.readString();
        this.respuesta = (Respuesta) parcel.readValue(Respuesta.class.getClassLoader());
        this.listaOpciones = (ListaOpcionesFamiliaObjetos) parcel.readParcelable(ListaOpcionesFamiliaObjetos.class.getClassLoader());
    }

    public String getIdPregunta() {
        return this.idPregunta;
    }

    public void setIdPregunta(String str) {
        this.idPregunta = str;
    }

    public String getTextoPregunta() {
        return this.textoPregunta;
    }

    public void setTextoPregunta(String str) {
        this.textoPregunta = str;
    }

    public String getTipoRespuesta() {
        return this.tipoRespuesta;
    }

    public void setTipoRespuesta(String str) {
        this.tipoRespuesta = str;
    }

    public Respuesta getRespuesta() {
        return this.respuesta;
    }

    public void setRespuesta(Respuesta respuesta2) {
        this.respuesta = respuesta2;
    }

    public String getCantMaxCaracteres() {
        return this.cantMaxCaracteres;
    }

    public void setCantMaxCaracteres(String str) {
        this.cantMaxCaracteres = str;
    }

    public String getRespuestaOpcional() {
        return this.respuestaOpcional;
    }

    public void setRespuestaOpcional(String str) {
        this.respuestaOpcional = str;
    }

    public ListaOpcionesFamiliaObjetos getListaOpciones() {
        return this.listaOpciones;
    }

    public void setListaOpciones(ListaOpcionesFamiliaObjetos listaOpcionesFamiliaObjetos) {
        this.listaOpciones = listaOpcionesFamiliaObjetos;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idPregunta);
        parcel.writeString(this.textoPregunta);
        parcel.writeString(this.tipoRespuesta);
        parcel.writeString(this.cantMaxCaracteres);
        parcel.writeString(this.respuestaOpcional);
        parcel.writeValue(this.respuesta);
        parcel.writeParcelable(this.listaOpciones, i);
    }

    public static PreguntasFamiliaBean clone(PreguntasFamiliaBean preguntasFamiliaBean) {
        PreguntasFamiliaBean preguntasFamiliaBean2 = new PreguntasFamiliaBean();
        preguntasFamiliaBean2.setRespuesta(preguntasFamiliaBean.getRespuesta());
        preguntasFamiliaBean2.setIdPregunta(preguntasFamiliaBean.getIdPregunta());
        preguntasFamiliaBean2.setCantMaxCaracteres(preguntasFamiliaBean.getCantMaxCaracteres());
        preguntasFamiliaBean2.setListaOpciones(preguntasFamiliaBean.getListaOpciones());
        preguntasFamiliaBean2.setRespuestaOpcional(preguntasFamiliaBean.getRespuestaOpcional());
        preguntasFamiliaBean2.setTextoPregunta(preguntasFamiliaBean.getTextoPregunta());
        preguntasFamiliaBean2.setTipoRespuesta(preguntasFamiliaBean.getTipoRespuesta());
        return preguntasFamiliaBean2;
    }

    public static List<PreguntasFamiliaBean> clone(List<PreguntasFamiliaBean> list) {
        ArrayList arrayList = new ArrayList();
        for (PreguntasFamiliaBean clone : list) {
            arrayList.add(clone(clone));
        }
        return arrayList;
    }
}
