package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class ImageDataTurnoCaja extends ImageData {
    private TypeField a = TypeField.ITEM_IMAGE_TURNO_CAJA;

    public static ImageDataTurnoCaja newInstance() {
        return new ImageDataTurnoCaja();
    }

    public int getType() {
        return this.a.getValue();
    }
}
