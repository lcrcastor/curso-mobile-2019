package ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail;

public enum TitulosValoresViewElements {
    TIPO_ESPECIE("Tipo de Especie"),
    VALOR_NOMINAL("Cantidad Valor Nominal"),
    PRECIO_MERCADO("Precio Mercado"),
    FECHA_ULTIMA_COTIZACION("Fecha Última Cotización"),
    TENENCIA_VALUADA_HOY("Tenencia Valuada Hoy"),
    PRECIO_COMPRA("Precio de Compra"),
    VALUACION_COSTO("Valuación Costo"),
    DIVIDENDOS_COBRADOS("Dividendos Cobrados"),
    RESULTADOS("Resultados"),
    ESTADO("Estado");
    
    private String a;

    private TitulosValoresViewElements(String str) {
        this.a = str;
    }

    public String getTitle() {
        return this.a;
    }

    public void setTitle(String str) {
        this.a = str;
    }
}
