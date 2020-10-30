package ar.com.santander.rio.mbanking.view.tables;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import java.util.Map;
import java.util.Map.Entry;

public class TableView extends LinearLayout {
    public TableView(Context context) {
        super(context);
        setOrientation(1);
    }

    public TableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(1);
    }

    public TableView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(1);
    }

    public void addValues(Map<String, String> map) {
        for (Entry entry : map.entrySet()) {
            RowTwoColumnView rowTwoColumnView = new RowTwoColumnView(getContext());
            rowTwoColumnView.setRow((String) entry.getKey(), (String) entry.getValue());
            addView(rowTwoColumnView);
        }
    }

    public void addColumnsViews(Map<View, View> map) {
        for (Entry entry : map.entrySet()) {
            RowColumnView rowColumnView = new RowColumnView(getContext());
            rowColumnView.setRow((BaseColumn) entry.getKey(), (BaseColumn) entry.getValue());
            addView(rowColumnView);
        }
    }

    public void addRowView(RowColumnView rowColumnView) {
        addView(rowColumnView);
    }

    public void clearValues() {
        removeAllViews();
    }
}
