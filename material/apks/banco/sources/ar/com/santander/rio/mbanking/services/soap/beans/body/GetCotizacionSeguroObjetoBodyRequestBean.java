package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GetCotizacionSeguroObjetoBodyRequestBean {
    public String idFamilia;
    public ListaPreguntasFamilia listaPreguntas;
    public String numCotizacion;
    public String sumaAsegurada;
    public String tipoOperacion;

    public GetCotizacionSeguroObjetoBodyRequestBean(String str, String str2, String str3, String str4, ListaPreguntasFamilia listaPreguntasFamilia) {
        this.idFamilia = str;
        this.tipoOperacion = str2;
        this.numCotizacion = str3;
        this.sumaAsegurada = str4;
        this.listaPreguntas = listaPreguntasFamilia;
    }
}
