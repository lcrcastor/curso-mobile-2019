package ar.com.santander.rio.mbanking.app.ui.slider.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.slider.pojo.sliderItem;
import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private Context a;
    private List<sliderItem> b;

    @Nullable
    public CharSequence getPageTitle(int i) {
        return "";
    }

    public float getPageWidth(int i) {
        return 0.93f;
    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    public SliderAdapter(Context context, List<sliderItem> list) {
        this.a = context;
        this.b = list;
    }

    public CharSequence getContent(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tarjeta SUBE ");
        sb.append(i + 1);
        sb.append(" , número ");
        sb.append(CAccessibility.getInstance(this.a).applyNumberCharToChar(((sliderItem) this.b.get(i)).getNroTarjeta()));
        return sb.toString();
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
        int i2 = 0;
        View inflate = ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(R.layout.slider_item, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(R.id.slider_alias);
        ((TextView) inflate.findViewById(R.id.slider_num_tarjeta)).setText(((sliderItem) this.b.get(i)).getNroTarjeta());
        textView.setText(((sliderItem) this.b.get(i)).getAliasTarjeta());
        if (TextUtils.isEmpty(((sliderItem) this.b.get(i)).getAliasTarjeta())) {
            i2 = 8;
        }
        textView.setVisibility(i2);
        textView.setAlpha(0.3f);
        inflate.setContentDescription(getPageTitle(i));
        viewGroup.addView(inflate);
        return inflate;
    }

    public int getCount() {
        return this.b.size();
    }

    public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        viewGroup.removeView((View) obj);
    }
}
