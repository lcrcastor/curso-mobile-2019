package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class FormDataSelectionKeyValue extends FormDataSelection implements Parcelable, IFormDataSelectionKeyValue {
    public static final Creator<FormDataSelectionKeyValue> CREATOR = new Creator<FormDataSelectionKeyValue>() {
        /* renamed from: a */
        public FormDataSelectionKeyValue createFromParcel(Parcel parcel) {
            return new FormDataSelectionKeyValue(parcel);
        }

        /* renamed from: a */
        public FormDataSelectionKeyValue[] newArray(int i) {
            return new FormDataSelectionKeyValue[i];
        }
    };
    protected InputType INPUTTYPE = InputType.STRING_SELECTION_KEY_VALUE;
    protected KeyValue keyValue = new KeyValue();
    protected HashMap<String, String> stringList = new HashMap<>();

    public class KeyValue {
        private String b = "";
        private String c = "";

        public KeyValue() {
        }

        public String getKey() {
            return this.b;
        }

        public void setKey(String str) {
            this.b = str;
        }

        public String getText() {
            return this.c;
        }

        public void setText(String str) {
            this.c = str;
        }

        public boolean isEmpty() {
            return TextUtils.isEmpty(this.b) || TextUtils.isEmpty(this.c);
        }
    }

    public int describeContents() {
        return 0;
    }

    public IFormDataSelection setStringList(ArrayList<String> arrayList) {
        return this;
    }

    protected FormDataSelectionKeyValue(Parcel parcel) {
        super(parcel);
    }

    public FormDataSelectionKeyValue(Locale locale) {
        super(locale);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public static FormDataSelectionKeyValue newInstance(@NonNull Locale locale) {
        return new FormDataSelectionKeyValue(locale);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public ErrorMessage isValid() {
        ErrorMessage errorMessage = new ErrorMessage("", Boolean.valueOf(true));
        return (!this.keyValue.isEmpty() || !this.requiered.booleanValue()) ? errorMessage : new ErrorMessage(this.messageValidation, Boolean.valueOf(false));
    }

    public String getTitle() {
        return this.selectionTitle;
    }

    public IFormDataSelectionKeyValue setStringHashMapList(HashMap<String, String> hashMap) {
        if (!hashMap.isEmpty()) {
            this.stringList = hashMap;
        }
        return this;
    }

    public HashMap<String, String> getStringHasgMapList() {
        return this.stringList;
    }

    public IFormData setValueText(String str) {
        Iterator it = this.stringList.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str2 = (String) it.next();
            if (((String) this.stringList.get(str2)).equalsIgnoreCase(str)) {
                this.keyValue.setKey(str2);
                this.keyValue.setText(str);
                break;
            }
        }
        return this;
    }

    public KeyValue getKeyValue() {
        return this.keyValue;
    }

    public ArrayList<String> getStringList() {
        return new ArrayList<>(new ArrayList(this.stringList.values()));
    }

    public int getType() {
        return this.INPUTTYPE.getValue();
    }
}
