package ar.com.santander.rio.mbanking.components.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFragmentDialogAcc;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

public class DialogAdapter extends BaseAdapter {
    private boolean a;
    private Context b;
    private String c;
    private List<String> d;
    private List<String> e;
    private boolean[] f;
    private LayoutInflater g;
    private CFragmentDialogAcc h;
    private AccessibilityDelegate i = new AccessibilityDelegate() {
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setText(view.getContentDescription());
            if (view.getContentDescription() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("adapter ");
                sb.append(view.getContentDescription().toString());
                Log.i("## accesibilidad", sb.toString());
            }
        }
    };

    static class ViewHolder {
        @InjectView(2131365460)
        ImageView radioButton;
        @InjectView(2131364770)
        HtmlTextView textOption;

        public ViewHolder(View view) {
            ButterKnife.inject((Object) this, view);
        }
    }

    public long getItemId(int i2) {
        return (long) i2;
    }

    public DialogAdapter(Context context, List<String> list, String str, List<String> list2, boolean[] zArr) {
        this.b = context;
        this.d = list;
        this.g = LayoutInflater.from(context);
        this.c = str;
        this.e = list2;
        this.f = zArr;
        this.h = new CFragmentDialogAcc();
        a();
    }

    private void a() {
        if (!isAccessibilityEnabled(this.b) || !isExploreByTouchEnabled(this.b)) {
            this.a = false;
        } else {
            this.a = true;
        }
    }

    public int getCount() {
        return this.d.size();
    }

    public Object getItem(int i2) {
        return this.d.get(i2);
    }

    public View getView(int i2, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        CAccessibility cAccessibility = new CAccessibility(this.b);
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = this.g.inflate(R.layout.dialog_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.textOption.setText((CharSequence) this.d.get(i2));
        try {
            viewHolder.textOption.setContentDescription(cAccessibility.applyFilterGeneral(Html.fromHtml(viewHolder.textOption.getText().toString()).toString()));
            viewHolder.textOption.setAccessibilityDelegate(this.i);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.f.length == 0 || this.f[i2]) {
            viewHolder.textOption.setTextColor(this.b.getResources().getColor(R.color.grey_dark));
        } else {
            viewHolder.textOption.setTextColor(this.b.getResources().getColor(R.color.grey_medium_light));
            HtmlTextView htmlTextView = viewHolder.textOption;
            StringBuilder sb = new StringBuilder();
            sb.append(viewHolder.textOption.getContentDescription());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(this.b.getResources().getString(R.string.OPTION_MENU_DISABLE));
            htmlTextView.setContentDescription(sb.toString());
        }
        if (view.isActivated() || Html.fromHtml((String) this.d.get(i2)).toString().equalsIgnoreCase(this.c)) {
            setActiveOption(viewHolder.radioButton);
            viewHolder.radioButton.setContentDescription(this.b.getString(R.string.CHECK_ON));
        } else {
            setDisableOption(viewHolder.radioButton);
            viewHolder.radioButton.setContentDescription(this.b.getString(R.string.CHECK_OFF));
        }
        return view;
    }

    public void updateAdapterChecked() {
        this.c = "";
        if (!((AccessibilityManager) this.b.getApplicationContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
            notifyDataSetChanged();
        }
    }

    public void setActiveOption(ImageView imageView) {
        imageView.setImageDrawable(this.b.getResources().getDrawable(R.drawable.check_on));
    }

    public void setDisableOption(ImageView imageView) {
        imageView.setImageDrawable(this.b.getResources().getDrawable(R.drawable.check_down));
    }

    public boolean isAccessibilityEnabled(Context context) {
        return ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled();
    }

    public boolean isExploreByTouchEnabled(Context context) {
        return ((AccessibilityManager) context.getSystemService("accessibility")).isTouchExplorationEnabled();
    }
}
