package ar.com.santander.rio.mbanking.app.ui.slider.impl;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.slider.adapter.SliderAdapter;
import ar.com.santander.rio.mbanking.app.ui.slider.pojo.sliderItem;
import ar.com.santander.rio.mbanking.app.ui.slider.transitions.expandingViewPagerTransformer;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaBean;
import com.crashlytics.android.Crashlytics;
import java.util.ArrayList;
import java.util.List;

public class SliderImpl {
    /* access modifiers changed from: private */
    public static SliderAdapter a = null;
    private static Context b = null;
    private static LinearLayout c = null;
    /* access modifiers changed from: private */
    public static int d = -1;

    public static List<sliderItem> getServicesItems(List<RecargaBean> list) {
        String str;
        ArrayList arrayList = new ArrayList();
        for (RecargaBean recargaBean : list) {
            sliderItem slideritem = new sliderItem();
            slideritem.setNroTarjeta(a(recargaBean.getIdentificacion()));
            if (TextUtils.isEmpty(recargaBean.getAlias())) {
                str = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Tarjeta de ");
                sb.append(recargaBean.getAlias());
                str = sb.toString();
            }
            slideritem.setAliasTarjeta(str);
            slideritem.setImage(R.drawable.sube_tarjeta);
            slideritem.setButtonClick(new OnClickListener() {
                public void onClick(View view) {
                }
            });
            arrayList.add(slideritem);
        }
        return arrayList;
    }

    private static String a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("(.{");
        sb.append("4");
        sb.append("})");
        return str.replaceAll(sb.toString(), "$1  ").trim();
    }

    public static List<sliderItem> getMockItems() {
        ArrayList arrayList = new ArrayList();
        sliderItem slideritem = new sliderItem();
        slideritem.setNroTarjeta("0000 2222 4444 6666");
        slideritem.setAliasTarjeta("Ekeko Volador");
        slideritem.setImage(R.drawable.sube_tarjeta);
        slideritem.setButtonClick(new OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click en en tarjeta 1", 0).show();
            }
        });
        sliderItem slideritem2 = new sliderItem();
        slideritem2.setNroTarjeta("1111 3333 5555 7777");
        slideritem2.setAliasTarjeta("Mauro Viale");
        slideritem2.setImage(R.drawable.sube_tarjeta);
        slideritem2.setButtonClick(new OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click en en tarjeta 2", 0).show();
            }
        });
        sliderItem slideritem3 = new sliderItem();
        slideritem3.setNroTarjeta("2222 4444 6666 8888");
        slideritem3.setAliasTarjeta("juan Carlos Baglietto");
        slideritem3.setImage(R.drawable.sube_tarjeta);
        slideritem3.setButtonClick(new OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click en en tarjeta 3", 0).show();
            }
        });
        arrayList.add(slideritem);
        arrayList.add(slideritem2);
        arrayList.add(slideritem3);
        return arrayList;
    }

    public static ViewPager getViewPager(View view, List<sliderItem> list) {
        b = view.getContext();
        a = new SliderAdapter(b, list);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.tarjetas_viewpager);
        viewPager.setPageTransformer(true, new expandingViewPagerTransformer());
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(-12);
        viewPager.setAdapter(a);
        viewPager.setContentDescription(a.getContent(0));
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                SliderImpl.d = i;
                SliderImpl.c(SliderImpl.d);
                viewPager.setContentDescription(SliderImpl.a.getContent(i));
            }
        });
        c = (LinearLayout) view.findViewById(R.id.viewPager_dots);
        c(0);
        return viewPager;
    }

    /* access modifiers changed from: private */
    public static void c(int i) {
        TextView[] textViewArr = new TextView[a.getCount()];
        c.removeAllViews();
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            textViewArr[i2] = new TextView(b);
            textViewArr[i2].setText(Html.fromHtml("&#8226;"));
            textViewArr[i2].setTextSize(25.0f);
            textViewArr[i2].setTextColor(b.getResources().getColor(R.color.grey_table_header));
            textViewArr[i2].setAlpha(0.85f);
            c.addView(textViewArr[i2]);
        }
        if (textViewArr.length > 0) {
            try {
                textViewArr[i].setTextColor(b.getResources().getColor(R.color.red_light));
                textViewArr[i].setAlpha(0.95f);
            } catch (Exception e) {
                Crashlytics.logException(e);
            }
        }
    }
}
