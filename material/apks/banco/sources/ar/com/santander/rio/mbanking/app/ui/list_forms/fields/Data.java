package ar.com.santander.rio.mbanking.app.ui.list_forms.fields;

import ar.com.santander.rio.mbanking.app.ui.list_forms.TypeField;

public class Data implements IData {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private TypeField g = TypeField.ITEM;
    private int h;
    private MarginValue i;

    public Data() {
    }

    public Data(String str, String str2) {
        this.b = str;
        this.c = str2;
    }

    public static Data newInstance() {
        return new Data();
    }

    public static Data newInstance(String str, String str2) {
        if (str.isEmpty() || str2.isEmpty()) {
            return null;
        }
        return new Data(str, str2);
    }

    public IData setMargin(MarginValue marginValue) {
        this.i = marginValue;
        return this;
    }

    public MarginValue getMargin() {
        return this.i;
    }

    public IData setLabel(String str) {
        this.b = str;
        return this;
    }

    public IData setDetail(String str) {
        this.c = str;
        return this;
    }

    public IData setTag(String str) {
        this.d = str;
        return this;
    }

    public IData setContentDescription(String str) {
        this.f = str;
        return this;
    }

    public IData setContentDescriptionLabel(String str) {
        this.e = str;
        return this;
    }

    public IData setTitle(String str) {
        this.a = str;
        return this;
    }

    public IData setPositionItem(int i2) {
        this.h = i2;
        return this;
    }

    public int getPosition() {
        return this.h;
    }

    public String getTitle() {
        return this.a;
    }

    public String getLabel() {
        return this.b;
    }

    public String getDetail() {
        return this.c;
    }

    public String getTag() {
        return this.d;
    }

    public String getContentDescription() {
        return this.f;
    }

    public String getContentDescriptionLabel() {
        return this.e;
    }

    public int getType() {
        return this.g.getValue();
    }
}
