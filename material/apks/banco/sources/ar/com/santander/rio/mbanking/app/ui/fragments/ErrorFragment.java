package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.utils.Utils;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ErrorFragment extends BaseFragment {
    private static String a = "message";
    private static String b = "icon";
    private static String c = "backgroundColor";
    private static String d = "tittle";
    public int icon;
    public String message;
    @InjectView(2131364790)
    ImageView messageIC;
    @InjectView(2131364936)
    RelativeLayout messageLayout;
    @InjectView(2131366022)
    TextView messageTV;
    public String tittle;
    @InjectView(2131366013)
    TextView tittleTV;

    public static ErrorFragment newInstance(String str, int i, int i2) {
        ErrorFragment errorFragment = new ErrorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(d, i2);
        bundle.putString(a, str);
        bundle.putInt(b, i);
        if (i == R.drawable.ico_reloj_gris) {
            bundle.putInt(c, R.color.white);
        }
        errorFragment.setArguments(bundle);
        return errorFragment;
    }

    public static ErrorFragment newInstance(String str, int i, String str2) {
        ErrorFragment errorFragment = new ErrorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(d, str2);
        bundle.putString(a, str);
        bundle.putInt(b, i);
        if (i == R.drawable.ico_reloj_gris) {
            bundle.putInt(c, R.color.white);
        }
        errorFragment.setArguments(bundle);
        return errorFragment;
    }

    public static ErrorFragment newInstance(String str, String str2) {
        ErrorFragment errorFragment = new ErrorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(d, str2);
        bundle.putString(a, str);
        errorFragment.setArguments(bundle);
        return errorFragment;
    }

    public static ErrorFragment newInstance(String str, int i) {
        ErrorFragment errorFragment = new ErrorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(a, str);
        bundle.putInt(b, i);
        if (i == R.drawable.ico_reloj_gris) {
            bundle.putInt(c, R.color.white);
        }
        errorFragment.setArguments(bundle);
        return errorFragment;
    }

    public static ErrorFragment newInstance(String str) {
        ErrorFragment errorFragment = new ErrorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(a, str);
        errorFragment.setArguments(bundle);
        return errorFragment;
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.empty_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        Bundle arguments = getArguments();
        if (!(arguments == null || (arguments.get(d) == null && arguments.get(a) == null))) {
            this.tittle = arguments.getInt(d) != 0 ? getString(arguments.getInt(d)) : arguments.getString(d);
            this.messageLayout.setVisibility(0);
            if (!TextUtils.isEmpty(this.tittle)) {
                this.tittleTV.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.tittle)));
            }
            try {
                this.tittleTV.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.tittleTV.getText().toString()));
                this.tittleTV.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterTelephoneMask(this.tittleTV.getContentDescription().toString()));
                this.tittleTV.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterTimeAvailability(this.tittleTV.getContentDescription().toString()));
                CAccessibility.sendAccessibilityEvent(getActivity(), this.tittleTV.getContentDescription().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!(arguments == null || arguments.get(a) == null)) {
            this.message = arguments.getString(a);
            this.messageLayout.setVisibility(0);
            this.messageTV.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.message)));
            Linkify.addLinks(this.messageTV, 1);
            if (this.message.equalsIgnoreCase(getString(R.string.IDXXX_DEEP_LINK_NO_PRODUCTS)) || this.message.equalsIgnoreCase(getString(R.string.IDXXX_DEEP_LINK_NO_ACCOUNTS))) {
                this.messageTV.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ErrorFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ErrorFragment.this.message.equalsIgnoreCase(ErrorFragment.this.getString(R.string.IDXXX_DEEP_LINK_NO_PRODUCTS)) ? "https://santander.com.ar/banco/online/personas/solicita-tu-producto/" : "https://santander.com.ar/banco/online/personas/solicita-tu-producto/#cuentas")));
                    }
                });
            }
            try {
                this.messageTV.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.messageTV.getText().toString()));
                this.messageTV.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterTelephoneMask(this.messageTV.getContentDescription().toString()));
                this.messageTV.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterTimeAvailability(this.messageTV.getContentDescription().toString()));
                CAccessibility.sendAccessibilityEvent(getActivity(), this.messageTV.getContentDescription().toString());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (!(arguments == null || arguments.get(b) == null)) {
            this.icon = arguments.getInt(b);
            if (this.icon == 0) {
                this.messageIC.setVisibility(8);
            } else {
                this.messageIC.setVisibility(0);
                this.messageIC.setImageResource(this.icon);
            }
        }
        if (!(arguments == null || arguments.get(c) == null)) {
            inflate.findViewById(R.id.layout_ko_result).setBackgroundColor(getResources().getColor(arguments.getInt(c)));
        }
        return inflate;
    }
}
