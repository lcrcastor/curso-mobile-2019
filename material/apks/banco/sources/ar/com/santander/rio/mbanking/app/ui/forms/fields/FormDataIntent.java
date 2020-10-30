package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import java.util.Locale;

public class FormDataIntent extends FormData implements Parcelable, IFormDataIntent {
    public static final Creator<FormDataIntent> CREATOR = new Creator<FormDataIntent>() {
        /* renamed from: a */
        public FormDataIntent createFromParcel(Parcel parcel) {
            return new FormDataIntent(parcel);
        }

        /* renamed from: a */
        public FormDataIntent[] newArray(int i) {
            return new FormDataIntent[i];
        }
    };
    private Class a = null;
    private Bundle b = new Bundle();
    private Integer c = Integer.valueOf(99);
    private Bundle d;
    private String e = "";

    public void ApplyAccesibilityFiltersLabels() {
    }

    public void ApplyAccesibilityFiltersValues() {
    }

    public int describeContents() {
        return 0;
    }

    public FormDataIntent() {
    }

    protected FormDataIntent(Parcel parcel) {
        super(parcel);
        this.b = parcel.readBundle();
        if (parcel.readByte() == 0) {
            this.c = null;
        } else {
            this.c = Integer.valueOf(parcel.readInt());
        }
        this.d = parcel.readBundle();
        this.e = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBundle(this.b);
        if (this.c == null) {
            parcel.writeByte(0);
        } else {
            parcel.writeByte(1);
            parcel.writeInt(this.c.intValue());
        }
        parcel.writeBundle(this.d);
        parcel.writeString(this.e);
    }

    public Bundle getValue() {
        return this.d;
    }

    public void setValue(Bundle bundle) {
        this.d = bundle;
    }

    public Bundle getBundle() {
        return this.b;
    }

    public FormDataIntent setBundle(Bundle bundle) {
        if (bundle != null) {
            this.b = bundle;
        }
        return this;
    }

    public String getSelectedMessage() {
        return this.e;
    }

    public FormDataIntent setSelectedMessage(String str) {
        if (str != null) {
            this.e = str;
        }
        return this;
    }

    public Integer getRequestId() {
        return this.c;
    }

    public FormDataIntent setRequestId(Integer num) {
        if (num != null) {
            this.c = num;
        }
        return this;
    }

    public Class getIntentClass() {
        return this.a;
    }

    public FormDataIntent setIntentClass(Class cls) {
        if (cls != null) {
            this.a = cls;
        }
        return this;
    }

    public ErrorMessage isValid() {
        ErrorMessage errorMessage = new ErrorMessage("", Boolean.valueOf(true));
        return (this.d != null || !this.requiered.booleanValue()) ? errorMessage : new ErrorMessage(this.messageValidation, Boolean.valueOf(false));
    }

    public Locale getLocale() {
        return this.locale;
    }

    public int getType() {
        return InputType.INTENT_SELECTION.getValue();
    }
}
