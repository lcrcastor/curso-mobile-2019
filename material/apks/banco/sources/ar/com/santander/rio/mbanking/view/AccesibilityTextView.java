package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;

public class AccesibilityTextView extends AppCompatTextView {
    public AccesibilityTextView(Context context) {
        super(context);
    }

    public AccesibilityTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AccesibilityTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.getText().add(getContentDescription());
    }
}
