package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class ImageData extends Data implements IImageData {
    private int a;
    private String b;
    private TypeField c = TypeField.ITEM_IMAGE;
    private int d;

    public ImageData() {
    }

    public ImageData(int i) {
        this.a = i;
    }

    public ImageData(String str) {
        this.b = str;
    }

    public static ImageData newInstance() {
        return new ImageData();
    }

    public IImageData setImageResource(int i) {
        this.a = i;
        return this;
    }

    public IImageData setImageResourceURL(String str) {
        this.b = str;
        return this;
    }

    public IImageData setPositionItem(int i) {
        this.d = i;
        return this;
    }

    public int getPosition() {
        return this.d;
    }

    public int getImageResource() {
        return this.a;
    }

    public String getImageResourceURL() {
        return this.b;
    }

    public int getType() {
        return this.c.getValue();
    }
}
