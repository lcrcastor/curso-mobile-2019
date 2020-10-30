package ar.com.santander.rio.mbanking.services.model.general;

public enum TenenciaInversionesProductosEnum {
    TITULOS_VALORES("TV"),
    CUSTODIA("CUS"),
    FONDO_COMUNES("FCI"),
    PLAZOS_FIJOS("PF"),
    LIQUIDEZ("LIQ");
    
    private final String codProd;

    private TenenciaInversionesProductosEnum(String str) {
        this.codProd = str;
    }

    public String getCodProd() {
        return this.codProd;
    }
}
