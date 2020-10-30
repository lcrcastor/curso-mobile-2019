package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

public interface IData {
    String getContentDescription();

    String getContentDescriptionLabel();

    String getDetail();

    String getLabel();

    MarginValue getMargin();

    int getPosition();

    String getTag();

    String getTitle();

    int getType();

    IData setContentDescription(String str);

    IData setContentDescriptionLabel(String str);

    IData setDetail(String str);

    IData setLabel(String str);

    IData setMargin(MarginValue marginValue);

    IData setPositionItem(int i);

    IData setTag(String str);

    IData setTitle(String str);
}
