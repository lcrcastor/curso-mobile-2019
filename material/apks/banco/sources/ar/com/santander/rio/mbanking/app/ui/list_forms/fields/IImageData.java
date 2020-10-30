package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

public interface IImageData extends IData {
    int getImageResource();

    String getImageResourceURL();

    IImageData setImageResource(int i);

    IImageData setImageResourceURL(String str);
}
