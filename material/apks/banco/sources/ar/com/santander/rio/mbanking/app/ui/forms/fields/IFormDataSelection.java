package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import java.util.ArrayList;

public interface IFormDataSelection extends IFormData {
    ArrayList<String> getStringList();

    IFormDataSelection setStringList(ArrayList<String> arrayList);
}
