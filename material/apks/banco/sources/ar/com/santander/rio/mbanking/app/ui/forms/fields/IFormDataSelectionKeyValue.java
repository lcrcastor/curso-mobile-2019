package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSelectionKeyValue.KeyValue;
import java.util.HashMap;

public interface IFormDataSelectionKeyValue extends IFormDataSelection {
    KeyValue getKeyValue();

    HashMap<String, String> getStringHasgMapList();

    IFormDataSelectionKeyValue setStringHashMapList(HashMap<String, String> hashMap);
}
