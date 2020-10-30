package ar.com.santander.rio.mbanking.services.model.general;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaMensajes;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaOpciones;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaSugerencias;

public class DatoWatson {
    public static final int TYPE_SERVIDOR = 0;
    public static final int TYPE_SERVIDOR_LISTA_OPCIONES = 2;
    public static final int TYPE_SERVIDOR_LISTA_SUGERENCIAS = 3;
    public static final int TYPE_SERVIDOR_RES_1 = 4;
    public static final int TYPE_USER = 1;
    private String esPregunta;
    private String fecha;
    private ListaMensajes listaMensajes;
    private ListaOpciones listaOpciones;
    private ListaPreguntas listaPreguntas;
    private ListaSugerencias listaSugerencias;
    private String mensaje;
    private int type;
    private String userMessage;

    public DatoWatson(String str, String str2, int i) {
        this.fecha = str;
        this.mensaje = str2;
        this.type = i;
    }

    public DatoWatson(String str, int i) {
        this.fecha = str;
        this.type = i;
    }

    public DatoWatson(int i) {
        this.type = i;
    }

    public DatoWatson(String str, String str2) {
        this.fecha = str;
        this.userMessage = str2;
        this.type = 1;
    }

    public DatoWatson(String str, ListaMensajes listaMensajes2) {
        this.fecha = str;
        this.listaMensajes = listaMensajes2;
        this.type = 0;
    }

    public DatoWatson(String str, ListaMensajes listaMensajes2, ListaOpciones listaOpciones2) {
        this.fecha = str;
        this.listaMensajes = listaMensajes2;
        this.listaOpciones = listaOpciones2;
        this.type = 2;
    }

    public DatoWatson(String str, ListaSugerencias listaSugerencias2) {
        this.fecha = str;
        this.listaSugerencias = listaSugerencias2;
        this.type = 3;
    }

    public DatoWatson(String str, ListaMensajes listaMensajes2, ListaPreguntas listaPreguntas2) {
        this.fecha = str;
        this.listaMensajes = listaMensajes2;
        this.listaPreguntas = listaPreguntas2;
        this.type = 2;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String str) {
        this.mensaje = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
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

    public ListaMensajes getListaMensajes() {
        return this.listaMensajes;
    }

    public void setListaMensajes(ListaMensajes listaMensajes2) {
        this.listaMensajes = listaMensajes2;
    }

    public String getUserMessage() {
        return this.userMessage;
    }

    public void setUserMessage(String str) {
        this.userMessage = str;
    }
}
