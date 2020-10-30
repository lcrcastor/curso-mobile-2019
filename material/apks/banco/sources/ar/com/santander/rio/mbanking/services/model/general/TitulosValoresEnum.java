package ar.com.santander.rio.mbanking.services.model.general;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;

public enum TitulosValoresEnum {
    TITULOS_PUBLICOS("PUB"),
    TITULOS_PRIVADOS("PRV"),
    ACCIONES("ACC"),
    CEDEAR("CDR");
    
    private String categoriaTitulo;

    private TitulosValoresEnum(String str) {
        this.categoriaTitulo = str;
    }

    public String getCategoriaTitulo() {
        return this.categoriaTitulo;
    }

    public String getTitulo(Context context) {
        String[] stringArray = context.getResources().getStringArray(R.array.titulos);
        switch (this) {
            case TITULOS_PUBLICOS:
                return stringArray[0];
            case TITULOS_PRIVADOS:
                return stringArray[1];
            case ACCIONES:
                return stringArray[2];
            case CEDEAR:
                return stringArray[3];
            default:
                return this.categoriaTitulo;
        }
    }
}
