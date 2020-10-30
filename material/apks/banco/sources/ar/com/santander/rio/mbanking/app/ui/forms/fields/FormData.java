package ar.com.santander.rio.mbanking.app.ui.forms.fields;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.InputType;
import java.util.ArrayList;
import java.util.Locale;

public class FormData implements Parcelable, IFormData {
    public static final Creator<FormData> CREATOR = new Creator<FormData>() {
        /* renamed from: a */
        public FormData createFromParcel(Parcel parcel) {
            return new FormData(parcel);
        }

        /* renamed from: a */
        public FormData[] newArray(int i) {
            return new FormData[i];
        }
    };
    protected Object[] accesibilityLabelFilters;
    protected Object[] accesibilityValueFilters;
    protected String contentDescriptionLabelText = "";
    protected String contentDescriptionValueText = "";
    protected Boolean editable = Boolean.valueOf(true);
    protected String hint = "";
    protected InputType inputType = InputType.TEXT;
    protected int keyboardType = 0;
    protected String labelText = "";
    protected Locale locale;
    protected String messageValidation = "";
    protected int position = 0;
    protected Boolean requiered = Boolean.valueOf(false);
    protected String requieredMessage = "Campo Requerido";
    protected String selectionTitle = "Seleccioná";
    protected ArrayList<String> stringList = new ArrayList<>();
    protected String tag;
    protected String valueText = "";
    protected String valueValidation = "";

    public void ApplyAccesibilityFiltersLabels() {
    }

    public void ApplyAccesibilityFiltersValues() {
    }

    public int describeContents() {
        return 0;
    }

    public int getType() {
        return 0;
    }

    protected FormData(Parcel parcel) {
        Boolean bool;
        boolean z = false;
        this.tag = parcel.readString();
        byte readByte = parcel.readByte();
        Boolean bool2 = null;
        if (readByte == 0) {
            bool = null;
        } else {
            bool = Boolean.valueOf(readByte == 1);
        }
        this.requiered = bool;
        this.requieredMessage = parcel.readString();
        byte readByte2 = parcel.readByte();
        if (readByte2 != 0) {
            if (readByte2 == 1) {
                z = true;
            }
            bool2 = Boolean.valueOf(z);
        }
        this.editable = bool2;
        this.labelText = parcel.readString();
        this.valueText = parcel.readString();
        this.valueValidation = parcel.readString();
        this.hint = parcel.readString();
        this.keyboardType = parcel.readInt();
        this.contentDescriptionValueText = parcel.readString();
        this.contentDescriptionLabelText = parcel.readString();
        this.messageValidation = parcel.readString();
        this.selectionTitle = parcel.readString();
        this.stringList = parcel.createStringArrayList();
    }

    protected FormData() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tag);
        int i2 = 2;
        int i3 = this.requiered == null ? 0 : this.requiered.booleanValue() ? 1 : 2;
        parcel.writeByte((byte) i3);
        parcel.writeString(this.requieredMessage);
        if (this.editable == null) {
            i2 = 0;
        } else if (this.editable.booleanValue()) {
            i2 = 1;
        }
        parcel.writeByte((byte) i2);
        parcel.writeString(this.labelText);
        parcel.writeString(this.valueText);
        parcel.writeString(this.valueValidation);
        parcel.writeString(this.hint);
        parcel.writeInt(this.keyboardType);
        parcel.writeString(this.contentDescriptionValueText);
        parcel.writeString(this.contentDescriptionLabelText);
        parcel.writeString(this.messageValidation);
        parcel.writeString(this.selectionTitle);
        parcel.writeStringList(this.stringList);
    }

    public IFormData setRequiredMessage(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.requieredMessage = str;
        }
        return this;
    }

    public IFormData setTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.selectionTitle = str;
        }
        return this;
    }

    public IFormData setTag(String str) {
        if (str != null) {
            this.tag = str;
        }
        return this;
    }

    public IFormData setHint(String str) {
        if (str != null) {
            this.hint = str;
        }
        return this;
    }

    public IFormData setLocale(Locale locale2) {
        if (locale2 != null) {
            this.locale = locale2;
        }
        return this;
    }

    public IFormData setValueText(String str) {
        if (str != null) {
            this.valueText = str;
        }
        return this;
    }

    public IFormData setPosition(int i) {
        this.position = i;
        return this;
    }

    public FormData setRequired(Boolean bool) {
        if (bool != null) {
            this.requiered = bool;
        }
        return this;
    }

    public IFormData setLabelText(String str) {
        if (str != null) {
            this.labelText = str;
        }
        return this;
    }

    public IFormData setValueValidation(String str) {
        if (str != null) {
            this.valueValidation = str;
        }
        return this;
    }

    public IFormData setMessageValidation(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.messageValidation = str;
        }
        return this;
    }

    public ErrorMessage isValid() {
        return new ErrorMessage("", Boolean.valueOf(true));
    }

    public IFormData setEditable(Boolean bool) {
        if (bool != null) {
            this.editable = bool;
        }
        return this;
    }

    public String getHint() {
        return this.hint;
    }

    public Boolean getEditable() {
        return this.editable;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getValueText() {
        return this.valueText;
    }

    public String getLabelText() {
        return this.labelText;
    }

    public String getTitle() {
        return this.selectionTitle;
    }

    public int getPosition() {
        return this.position;
    }
}
