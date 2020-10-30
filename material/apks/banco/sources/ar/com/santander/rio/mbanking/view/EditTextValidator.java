package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment.IDateDialogListener;
import ar.com.santander.rio.mbanking.view.accessibility.EtAccessibility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

public class EditTextValidator extends EtAccessibility implements OnClickListener, OnFocusChangeListener, OnTouchListener {
    Date a;
    private final SharedPreferences b;
    private final boolean c;
    private Context d;
    private boolean e;
    private boolean f;
    /* access modifiers changed from: private */
    public String g;
    private int h;
    private boolean i;
    private Matcher j;
    private ListenerValidator k;

    public interface ListenerValidator {
        void inValidate(String str);

        void infoChecked();
    }

    public ListenerValidator getListenerValidator() {
        return this.k;
    }

    public void setListenerValidator(ListenerValidator listenerValidator) {
        this.k = listenerValidator;
    }

    public EditTextValidator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.d = context;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.EditTextValidator, 0, 0);
        try {
            this.e = obtainStyledAttributes.getBoolean(4, false);
            this.f = obtainStyledAttributes.getBoolean(5, false);
            this.g = obtainStyledAttributes.getString(2);
            this.h = obtainStyledAttributes.getInt(6, 0);
            this.i = obtainStyledAttributes.getBoolean(3, false);
            this.c = obtainStyledAttributes.getBoolean(1, false);
            this.b = PreferenceManager.getDefaultSharedPreferences(context);
            if (this.i) {
                setOnClickListener(this);
                setOnFocusChangeListener(this);
            }
            String string = this.b.getString(String.valueOf(getId()), null);
            if (string != null) {
                setValue(string);
                if (this.i) {
                    this.a = new SimpleDateFormat("dd/MM/yyyy").parse(string);
                }
            } else {
                setValue("");
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
        if (getText() == null || (getText().toString().equalsIgnoreCase("") && getHint() != null)) {
            setContentDescription(getHint().toString());
        }
        obtainStyledAttributes.recycle();
        setOnTouchListener(this);
    }

    private void setValue(String str) {
        setText(str);
    }

    public String applyFilters(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterAccount(str);
    }

    public boolean validate() {
        boolean z = false;
        if (this.i) {
            Date time = Calendar.getInstance().getTime();
            if (this.a == null) {
                try {
                    this.a = new SimpleDateFormat(this.g).parse(getText().toString());
                } catch (ParseException unused) {
                }
            }
            if (this.a != null && !this.a.after(time)) {
                z = true;
            }
        } else if (this.e) {
            this.j = Patterns.EMAIL_ADDRESS.matcher(getText().toString());
            z = this.j.matches();
        } else if (getText().length() >= this.h) {
            String obj = getText().toString();
            boolean z2 = true;
            for (int i2 = 0; i2 < obj.length(); i2++) {
                if (!Character.isLetterOrDigit(obj.charAt(i2))) {
                    z2 = false;
                }
            }
            z = z2;
        }
        if (!z && this.k != null) {
            this.k.inValidate(getHint().toString());
        }
        return z;
    }

    public void setRememberMe(boolean z) {
        if (z) {
            this.b.edit().putString(String.valueOf(getId()), getText().toString()).commit();
        } else {
            this.b.edit().putString(String.valueOf(getId()), null).commit();
        }
    }

    public void onClick(View view) {
        IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(this.d.getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), getText().toString(), this.g);
        newInstance.setDialogListener(new IDateDialogListener() {
            public void onDateSelected(Date date) {
                EditTextValidator.this.setText(DateFormat.format(EditTextValidator.this.g, date));
                EditTextValidator.this.a = date;
            }
        });
        newInstance.show(((SantanderRioMainActivity) this.d).getSupportFragmentManager(), "Dialog");
    }

    public void onFocusChange(View view, boolean z) {
        if (z) {
            IsbanDatePickerDialogFragment newInstance = IsbanDatePickerDialogFragment.newInstance(this.d.getString(R.string.IDX_ALERT_LBL_TITLE_SELECCION_FECHA), getText().toString(), this.g);
            newInstance.setDialogListener(new IDateDialogListener() {
                public void onDateSelected(Date date) {
                    EditTextValidator.this.setText(DateFormat.format(EditTextValidator.this.g, date));
                    EditTextValidator.this.a = date;
                }
            });
            newInstance.show(((SantanderRioMainActivity) this.d).getSupportFragmentManager(), "Dialog");
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        try {
            if (!this.f || this.k == null) {
                boolean z = this.c;
                return false;
            }
            int width = getCompoundDrawables()[2].getBounds().width();
            if (motionEvent.getAction() == 1 && motionEvent.getRawX() >= ((float) (getRight() - width))) {
                this.k.infoChecked();
            }
            return false;
        } catch (Exception unused) {
        }
    }
}
