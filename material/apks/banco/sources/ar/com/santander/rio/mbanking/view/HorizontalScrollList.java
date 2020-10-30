package ar.com.santander.rio.mbanking.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ToggleButton;
import ar.com.santander.rio.mbanking.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HorizontalScrollList extends LinearLayout {
    private String a;
    private Context b;
    private List<ToggleItem> c;
    private List<ToggleButton> d;
    private View e;
    private boolean f = false;
    private IHorizontalScrollListListener g;

    public interface IHorizontalScrollListListener {
        void OnCheckedChangeListener(List<ToggleItem> list);

        void OnNewItemSelected(ToggleItem toggleItem);
    }

    public class ToggleItem {
        private String b;
        private Integer c;
        private String d;
        private boolean e;

        public ToggleItem(String str, boolean z, String str2) {
            this.d = str;
            this.e = z;
            this.b = str2;
        }

        public ToggleItem(String str, boolean z, int i, String str2) {
            this.d = str;
            this.c = Integer.valueOf(i);
            this.e = z;
            this.b = str2;
        }

        public Integer getColor() {
            return this.c;
        }

        public void setColor(int i) {
            this.c = Integer.valueOf(i);
        }

        public String getLabel() {
            return this.d;
        }

        public void setLabel(String str) {
            this.d = str;
        }

        public boolean isStatus() {
            return this.e;
        }

        public void setStatus(boolean z) {
            this.e = z;
        }

        public String getAccesibilityText() {
            return this.b;
        }

        public void setAccesibilityText(String str) {
            this.b = str;
        }
    }

    public HorizontalScrollList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = context;
        this.e = LayoutInflater.from(context).inflate(R.layout.horizontal_scroll, this);
    }

    public boolean isMultipleSelection() {
        return this.f;
    }

    public void setMultipleSelection(boolean z) {
        this.f = z;
    }

    public void clearData() {
        this.c = null;
        this.d = null;
    }

    public void show() {
        if (this.a != null) {
            ((TextView) this.e.findViewById(R.id.scrollDialogText)).setText(this.a);
        } else {
            this.e.findViewById(R.id.idTitle).setVisibility(8);
        }
        this.d = new ArrayList();
        LinearLayout linearLayout = (LinearLayout) this.e.findViewById(R.id.horizontalScrollViewContainer);
        for (ToggleItem a2 : this.c) {
            linearLayout.addView(a(a2));
        }
    }

    private View a(ToggleItem toggleItem) {
        LinearLayout linearLayout = new LinearLayout(this.b);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2, 1.0f));
        linearLayout.setBackground(this.b.getResources().getDrawable(R.drawable.contorno));
        linearLayout.setOrientation(1);
        final ToggleButton toggleButton = new ToggleButton(this.b);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.gravity = 1;
        toggleButton.setLayoutParams(layoutParams);
        toggleButton.setTextOff(toggleItem.getLabel());
        toggleButton.setContentDescription(toggleItem.getAccesibilityText());
        toggleButton.setTextOn(toggleItem.getLabel());
        toggleButton.setBackgroundColor(0);
        toggleButton.setAllCaps(false);
        getResources().getDimension(R.dimen.bloque_texto_text_size);
        toggleButton.setTextSize(1, 14.0f);
        this.d.add(toggleButton);
        final View view = new View(this.b);
        LayoutParams layoutParams2 = new LayoutParams(-1, (int) TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics()));
        layoutParams2.gravity = 1;
        view.setLayoutParams(layoutParams2);
        if (toggleItem.isStatus()) {
            Typeface createFromAsset = Typeface.createFromAsset(this.b.getAssets(), "fonts/OpenSans-Bold.otf");
            toggleButton.setTextColor(this.b.getResources().getColor(R.color.generic_red));
            toggleButton.setTypeface(createFromAsset);
            if (!isMultipleSelection()) {
                toggleButton.setClickable(false);
            }
            view.setBackgroundColor(getResources().getColor(R.color.generic_red));
        } else {
            Typeface createFromAsset2 = Typeface.createFromAsset(this.b.getAssets(), "fonts/OpenSans-Bold.otf");
            toggleButton.setTextColor(this.b.getResources().getColor(R.color.generic_grey));
            toggleButton.setTypeface(createFromAsset2);
            view.setBackgroundColor(getResources().getColor(R.color.generic_grey));
        }
        toggleButton.setChecked(toggleItem.isStatus());
        linearLayout.addView(toggleButton);
        linearLayout.addView(view);
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, final boolean z) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        HorizontalScrollList.this.a(toggleButton, z, view);
                    }
                }, 200);
            }
        });
        return linearLayout;
    }

    /* access modifiers changed from: private */
    public void a(ToggleButton toggleButton, boolean z, View view) {
        ToggleItem toggleItem;
        Iterator it = this.c.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            toggleItem = (ToggleItem) it.next();
            if (toggleItem.getLabel().equalsIgnoreCase(toggleButton.getText().toString())) {
                if (isMultipleSelection()) {
                    toggleItem.setStatus(z);
                } else if (z) {
                    toggleItem.setStatus(true);
                    a(toggleButton.getText().toString());
                }
            }
        }
        toggleItem = null;
        if (z) {
            if (!isMultipleSelection()) {
                toggleButton.setClickable(false);
            }
            Typeface createFromAsset = Typeface.createFromAsset(this.b.getAssets(), "fonts/OpenSans-Bold.otf");
            toggleButton.setTextColor(this.b.getResources().getColor(R.color.generic_red));
            toggleButton.setTypeface(createFromAsset);
            view.setBackgroundColor(getResources().getColor(R.color.generic_red));
        } else {
            if (!isMultipleSelection()) {
                toggleButton.setClickable(true);
            }
            Typeface createFromAsset2 = Typeface.createFromAsset(this.b.getAssets(), "fonts/OpenSans-Bold.otf");
            toggleButton.setTextColor(this.b.getResources().getColor(R.color.generic_grey));
            toggleButton.setTypeface(createFromAsset2);
            view.setBackgroundColor(getResources().getColor(R.color.generic_grey));
        }
        if (isMultipleSelection()) {
            if (this.g != null) {
                this.g.OnCheckedChangeListener(this.c);
            }
        } else if (this.g != null && z) {
            this.g.OnNewItemSelected(toggleItem);
        }
    }

    private void a(String str) {
        for (ToggleButton toggleButton : this.d) {
            if (!toggleButton.getText().toString().equalsIgnoreCase(str) && toggleButton.isChecked()) {
                toggleButton.toggle();
            }
        }
    }

    public void setTitle(String str) {
        this.a = str;
    }

    public void removeItems() {
        if (this.c != null) {
            this.c.removeAll(this.c);
            this.d.removeAll(this.d);
            ((LinearLayout) this.e.findViewById(R.id.horizontalScrollViewContainer)).removeAllViews();
        }
    }

    public void addItem(String str, boolean z, String str2) {
        if (this.c == null) {
            this.c = new ArrayList();
        }
        this.c.add(new ToggleItem(str, z, str2));
    }

    public void addItem(String str, boolean z, int i) {
        if (this.c == null) {
            this.c = new ArrayList();
        }
        List<ToggleItem> list = this.c;
        ToggleItem toggleItem = new ToggleItem(str, z, i, str);
        list.add(toggleItem);
    }

    public void setHorizontalScrollListener(IHorizontalScrollListListener iHorizontalScrollListListener) {
        this.g = iHorizontalScrollListListener;
    }

    public Boolean isEntrySetEmpty() {
        return Boolean.valueOf(this.c == null || this.c.isEmpty());
    }
}
