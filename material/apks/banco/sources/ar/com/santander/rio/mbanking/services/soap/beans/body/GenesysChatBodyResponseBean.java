package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class GenesysChatBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<GenesysChatBodyResponseBean> CREATOR = new Creator<GenesysChatBodyResponseBean>() {
        public GenesysChatBodyResponseBean createFromParcel(Parcel parcel) {
            return new GenesysChatBodyResponseBean(parcel);
        }

        public GenesysChatBodyResponseBean[] newArray(int i) {
            return new GenesysChatBodyResponseBean[i];
        }
    };
    @SerializedName("chatId")
    private String chatId;
    @SerializedName("confianza")
    private String confianza;
    @SerializedName("conversacionId")
    private String conversacionId;
    @SerializedName("esPregunta")
    private String esPregunta;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("intencion")
    private String intencion;
    @SerializedName("listaMensajes")
    private ListaMensajes listaMensajes;
    @SerializedName("listaOpciones")
    private ListaOpciones listaOpciones;
    @SerializedName("listaPreguntas")
    private ListaPreguntas listaPreguntas;
    @SerializedName("listaSugerencias")
    private ListaSugerencias listaSugerencias;
    private String mensaje;
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("rellamados")
    private RellamadosBean rellamados;
    @SerializedName("res")
    public String res;

    public int describeContents() {
        return 0;
    }

    public GenesysChatBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, ListaPreguntas listaPreguntas2, ListaOpciones listaOpciones2, String str9, ListaSugerencias listaSugerencias2, ListaMensajes listaMensajes2, RellamadosBean rellamadosBean) {
        this.mensaje = str;
        this.res = str2;
        this.fecha = str3;
        this.conversacionId = str4;
        this.chatId = str5;
        this.intencion = str6;
        this.confianza = str7;
        this.esPregunta = str8;
        this.listaPreguntas = listaPreguntas2;
        this.listaOpciones = listaOpciones2;
        this.nickName = str9;
        this.listaSugerencias = listaSugerencias2;
        this.listaMensajes = listaMensajes2;
        this.rellamados = rellamadosBean;
    }

    public GenesysChatBodyResponseBean() {
    }

    protected GenesysChatBodyResponseBean(Parcel parcel) {
        this.mensaje = parcel.readString();
        this.res = parcel.readString();
        this.fecha = parcel.readString();
        this.conversacionId = parcel.readString();
        this.chatId = parcel.readString();
        this.intencion = parcel.readString();
        this.confianza = parcel.readString();
        this.esPregunta = parcel.readString();
        this.listaPreguntas = (ListaPreguntas) parcel.readParcelable(ListaPreguntas.class.getClassLoader());
        this.listaOpciones = (ListaOpciones) parcel.readParcelable(ListaOpciones.class.getClassLoader());
        this.nickName = parcel.readString();
        this.listaSugerencias = (ListaSugerencias) parcel.readParcelable(ListaSugerencias.class.getClassLoader());
        this.listaMensajes = (ListaMensajes) parcel.readParcelable(ListaMensajes.class.getClassLoader());
        this.rellamados = (RellamadosBean) parcel.readParcelable(RellamadosBean.class.getClassLoader());
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String str) {
        this.mensaje = str;
    }

    public String getRes() {
        return this.res;
    }

    public void setRes(String str) {
        this.res = str;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getConversacionId() {
        return this.conversacionId;
    }

    public void setConversacionId(String str) {
        this.conversacionId = str;
    }

    public String getChatId() {
        return this.chatId;
    }

    public void setChatId(String str) {
        this.chatId = str;
    }

    public String getIntencion() {
        return this.intencion;
    }

    public void setIntencion(String str) {
        this.intencion = str;
    }

    public String getConfianza() {
        return this.confianza;
    }

    public void setConfianza(String str) {
        this.confianza = str;
    }

    public String getEsPregunta() {
        return this.esPregunta;
    }

    public void setEsPregunta(String str) {
        this.esPregunta = str;
    }

    public ListaPreguntas getListaPreguntas() {
        return this.listaPreguntas;
    }

    public void setListaPreguntas(ListaPreguntas listaPreguntas2) {
        this.listaPreguntas = listaPreguntas2;
    }

    public ListaOpciones getListaOpciones() {
        return this.listaOpciones;
    }

    public void setListaOpciones(ListaOpciones listaOpciones2) {
        this.listaOpciones = listaOpciones2;
    }

    public ListaSugerencias getListaSugerencias() {
        return this.listaSugerencias;
    }

    public void setListaSugerencias(ListaSugerencias listaSugerencias2) {
        this.listaSugerencias = listaSugerencias2;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String str) {
        this.nickName = str;
    }

    public ListaMensajes getListaMensajes() {
        return this.listaMensajes;
    }

    public void setListaMensajes(ListaMensajes listaMensajes2) {
        this.listaMensajes = listaMensajes2;
    }

    public RellamadosBean getRellamados() {
        return this.rellamados;
    }

    public void setRellamados(RellamadosBean rellamadosBean) {
        this.rellamados = rellamadosBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mensaje);
        parcel.writeString(this.res);
        parcel.writeString(this.fecha);
        parcel.writeString(this.conversacionId);
        parcel.writeString(this.chatId);
        parcel.writeString(this.intencion);
        parcel.writeString(this.confianza);
        parcel.writeString(this.esPregunta);
        parcel.writeParcelable(this.listaPreguntas, i);
        parcel.writeParcelable(this.listaOpciones, i);
        parcel.writeString(this.nickName);
        parcel.writeParcelable(this.listaSugerencias, i);
        parcel.writeParcelable(this.listaMensajes, i);
        parcel.writeParcelable(this.rellamados, i);
    }
}
