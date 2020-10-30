package ar.com.santander.rio.mbanking.app.ui.forms.fields;

public interface IFormCheckBox extends IFormDataIntent {
    String getDescription();

    String getLink();

    String getUrl();

    IFormCheckBox setDescription(String str);

    IFormCheckBox setLink(String str);

    IFormCheckBox setUrl(String str);
}
