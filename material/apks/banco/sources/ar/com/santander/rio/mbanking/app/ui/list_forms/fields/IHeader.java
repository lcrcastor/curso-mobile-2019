package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

public interface IHeader extends IData {
    String getContentDescriptionSubTitle();

    String getDescription();

    String getSubTitle();

    String getTitle();

    IHeader setContentDescriptionSubTitle(String str);

    IHeader setDescription(String str);

    IHeader setSubTitle(String str);

    IHeader setTitle(String str);
}
