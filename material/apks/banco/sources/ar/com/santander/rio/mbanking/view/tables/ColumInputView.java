package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CElementAcc;
import java.util.ArrayList;
import java.util.Iterator;

public class ColumInputView extends LinearLayout implements BaseColumn {
    private ArrayList<TextWatcher> a;
    private CElementAcc b;
    public EditText mViewInput;

    public View getViewColumn() {
        return this;
    }

    public ColumInputView(Context context) {
        super(context);
        this.a = null;
        a();
    }

    public ColumInputView(Context context, String str) {
        super(context);
        this.a = null;
        a();
        setValue(str);
    }

    public ColumInputView(Context context, String str, int i) {
        super(context);
        this.a = null;
        a();
        setValue(str);
        this.mViewInput.setId(i);
    }

    public ColumInputView(Context context, String str, int i, int i2) {
        this(context, str, i);
        this.mViewInput.setTextColor(getResources().getColor(i2));
    }

    public ColumInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = null;
        a();
    }

    public ColumInputView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = null;
        a();
    }

    private void a() {
        addView(((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.layout_column_input, null));
        this.mViewInput = (EditText) findViewById(R.id.vInput);
    }

    public void setValue(String str) {
        if (this.mViewInput != null) {
            this.mViewInput.setText(str);
        }
        if (this.b != null) {
            this.b.applyFilter(this.mViewInput, str);
        }
    }

    public View getInputView() {
        return this.mViewInput;
    }

    public void setCElementAcc(CElementAcc cElementAcc) {
        this.b = cElementAcc;
    }

    public CElementAcc getCElementAcc() {
        return this.b;
    }

    public void setMaxLenght(int i) {
        if (this.mViewInput != null) {
            this.mViewInput.setFilters(new InputFilter[]{new LengthFilter(i)});
        }
    }

    public void setKeyboardNumeric() {
        if (this.mViewInput != null) {
            this.mViewInput.setRawInputType(2);
        }
    }

    public void setOnlyNumbers() {
        if (this.mViewInput != null) {
            this.mViewInput.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        }
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        if (this.a == null) {
            this.a = new ArrayList<>();
        }
        this.a.add(textWatcher);
        this.mViewInput.addTextChangedListener(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        if (this.a != null) {
            int indexOf = this.a.indexOf(textWatcher);
            if (indexOf >= 0) {
                this.a.remove(indexOf);
            }
        }
        this.mViewInput.removeTextChangedListener(textWatcher);
    }

    public void clearTextChangedListeners() {
        if (this.a != null) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                this.mViewInput.removeTextChangedListener((TextWatcher) it.next());
            }
            this.a.clear();
            this.a = null;
        }
    }

    public void setIMEOptions(int i) {
        if (this.mViewInput != null) {
            this.mViewInput.setImeOptions(i);
        }
    }
}
