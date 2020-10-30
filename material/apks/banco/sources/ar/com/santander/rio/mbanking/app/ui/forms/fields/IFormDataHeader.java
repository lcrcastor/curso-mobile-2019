package ar.com.santander.rio.mbanking.app.ui.forms.fields;

public interface IFormDataHeader extends IFormData {
    String getContentDescriptionSubTitle();

    String getDescription();

    String getIconUrl();

    String getSubTitle();

    String getTitle();

    IFormDataHeader setContentDescriptionSubTitle(String str);

    IFormDataHeader setDescription(String str);

    IFormDataHeader setIconUrl(String str);

    IFormDataHeader setSubTitle(String str);

    IFormDataHeader setTitle(String str);
}
