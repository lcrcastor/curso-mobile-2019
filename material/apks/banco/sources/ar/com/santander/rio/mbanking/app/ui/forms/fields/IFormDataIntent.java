package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Bundle;

public interface IFormDataIntent extends IFormData {
    Bundle getBundle();

    Class getIntentClass();

    Integer getRequestId();

    String getSelectedMessage();

    Bundle getValue();

    FormDataIntent setBundle(Bundle bundle);

    FormDataIntent setIntentClass(Class cls);

    FormDataIntent setRequestId(Integer num);

    FormDataIntent setSelectedMessage(String str);

    void setValue(Bundle bundle);
}
