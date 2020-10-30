package ar.com.santander.rio.mbanking.components;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseDialogFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.components.adapters.DialogAdapter;
import ar.com.santander.rio.mbanking.utils.UtilAccesibility;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IsbanDialogFragment extends BaseDialogFragment implements OnClickListener, OnItemClickListener {
    private static String ad = "id";
    private static String ae = "showOnlyText";
    private static String af = "title";
    private static String ag = "message";
    private static String ah = "options";
    private static String ai = "optionsContentDescription";
    private static String aj = "optionsEnableState";
    private static String ak = "simpleAction";
    private static String al = "positiveAction";
    private static String am = "negativeAction";
    private static String an = "selectedOption";
    private static String ao = "threeOptions1Arg";
    private static String ap = "threeOptions2Arg";
    private static String aq = "threeOptions3Arg";

    /* renamed from: ar reason: collision with root package name */
    private static String f244ar = "autoClose";
    private String aA;
    private String aB;
    private Boolean aC = Boolean.valueOf(true);
    private String aD;
    private String aE;
    private String aF;
    private String aG = new String("¿Confirmás el blanqueo de clave?");
    private IDialogListener aH;
    private IDialogListenerExtended aI;
    private IDialogListenerThreeOptions aJ;
    private DialogAdapter aK;
    private DismissListener aL;
    /* access modifiers changed from: private */
    public boolean aM = false;
    private List<String> as;
    private boolean[] at;
    private List<String> au;
    private String av;
    private String aw;
    private String ax;
    private String ay;
    private String az;
    @InjectView(2131364974)
    LinearLayout layoutThreeOption;
    @InjectView(2131364975)
    LinearLayout layoutTwoOption;
    @InjectView(2131365108)
    ListView listOptions;
    @InjectView(2131365229)
    TextView messageView;
    @InjectView(2131366267)
    Button negativeButton;
    @InjectView(2131365279)
    Button optionButton;
    @InjectView(2131366268)
    Button positiveButton;
    @InjectView(2131366030)
    Button threeOptionsButton1;
    @InjectView(2131366031)
    Button threeOptionsButton2;
    @InjectView(2131366032)
    Button threeOptionsButton3;
    @InjectView(2131364619)
    TextView titleView;
    @InjectView(2131364771)
    LinearLayout vTitle;
    @InjectView(2131364937)
    View vTitleSeparator;

    public interface DismissListener {
        void onIsbanDismiss();
    }

    public interface IDialogListener {
        void onItemSelected(String str);

        void onNegativeButton();

        void onPositiveButton();

        void onSimpleActionButton();
    }

    public interface IDialogListenerExtended {
        void onItemSelected(String str, String str2);

        void onNegativeButton(String str);

        void onPositiveButton(String str);

        void onSimpleActionButton(String str);
    }

    public interface IDialogListenerThreeOptions {
        void onOption1Button();

        void onOption2Button();

        void onOption3Button();
    }

    public void onBackPressed() {
    }

    public static IsbanDialogFragment newInstance(String str, String str2, String str3, ArrayList<String> arrayList, String str4, String str5, String str6, String str7, ArrayList<String> arrayList2, boolean[] zArr, String str8, String str9, String str10) {
        IsbanDialogFragment isbanDialogFragment = new IsbanDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ad, str);
        bundle.putString(af, str2);
        bundle.putString(ag, str3);
        bundle.putStringArrayList(ah, arrayList);
        bundle.putString(al, str5);
        bundle.putString(am, str6);
        bundle.putString(ak, str4);
        bundle.putString(an, str7);
        bundle.putString(ao, str8);
        bundle.putString(ap, str9);
        bundle.putString(aq, str10);
        bundle.putStringArrayList(ai, arrayList2);
        bundle.putBooleanArray(aj, zArr);
        isbanDialogFragment.setCancelable(false);
        isbanDialogFragment.setArguments(bundle);
        return isbanDialogFragment;
    }

    public static IsbanDialogFragment newInstance(String str, String str2, String str3, ArrayList<String> arrayList, String str4, String str5, String str6, String str7, ArrayList<String> arrayList2, boolean[] zArr) {
        return newInstance(str, str2, str3, arrayList, str4, str5, str6, str7, arrayList2, zArr, null, null, null);
    }

    public static IsbanDialogFragment newInstance(String str, String str2, String str3, String str4, String str5, String str6) {
        return newInstance(str, str2, str3, null, null, null, null, null, null, new boolean[0], str4, str5, str6);
    }

    public static IsbanDialogFragment newInstance(String str, String str2, ArrayList<String> arrayList, String str3, String str4, String str5, String str6) {
        return newInstance(null, str, str2, arrayList, str3, str4, str5, str6, null);
    }

    public static IsbanDialogFragment newInstance(String str, String str2, String str3, ArrayList<String> arrayList, String str4, String str5, String str6, String str7) {
        return newInstance(str, str2, str3, arrayList, str4, str5, str6, str7, null);
    }

    public static IsbanDialogFragment newInstance(String str, String str2, String str3, ArrayList<String> arrayList, String str4, String str5, String str6, String str7, ArrayList<String> arrayList2) {
        boolean[] zArr = new boolean[((arrayList == null || arrayList.size() <= 0) ? 0 : arrayList.size())];
        if (zArr.length > 0) {
            Arrays.fill(zArr, Boolean.TRUE.booleanValue());
        }
        return newInstance(str, str2, str3, arrayList, str4, str5, str6, str7, arrayList2, zArr);
    }

    public static IsbanDialogFragment newInstance(String str, String str2, String str3, String str4, String str5) {
        return newInstance(str, str2, str3, null, null, str4, str5, null, null);
    }

    public static IsbanDialogFragment newInstance(String str, String str2, String str3, String str4) {
        return newInstance(str, str2, str3, null, str4, null, null, null, null);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        this.aM = true;
        y();
        Builder builder = new Builder(getActivity());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.options_dialog_body_layout, null);
        ButterKnife.inject((Object) this, inflate);
        builder.setView(inflate);
        AlertDialog create = builder.create();
        if (this.aw == null && this.titleView != null) {
            this.titleView.setVisibility(8);
            this.vTitleSeparator.setVisibility(8);
        }
        if (this.ay != null && this.az != null) {
            this.optionButton.setVisibility(8);
            this.layoutTwoOption.setVisibility(0);
            this.layoutThreeOption.setVisibility(8);
            this.positiveButton.setText(this.ay);
            this.positiveButton.setOnClickListener(this);
            this.negativeButton.setText(this.az);
            this.negativeButton.setOnClickListener(this);
        } else if (this.ay != null && this.az == null) {
            this.optionButton.setVisibility(0);
            this.layoutTwoOption.setVisibility(8);
            this.layoutThreeOption.setVisibility(8);
            this.optionButton.setText(this.ay);
            this.optionButton.setBackgroundResource(R.drawable.boton_redondeado_blanco_borde_gris);
            this.optionButton.setOnClickListener(this);
        } else if (this.aD != null && this.aE != null && this.aF != null) {
            this.optionButton.setVisibility(8);
            this.layoutTwoOption.setVisibility(8);
            this.layoutThreeOption.setVisibility(0);
            this.threeOptionsButton1.setText(this.aD);
            this.threeOptionsButton1.setOnClickListener(this);
            this.threeOptionsButton2.setText(this.aE);
            this.threeOptionsButton2.setOnClickListener(this);
            this.threeOptionsButton3.setText(this.aF);
            this.threeOptionsButton3.setOnClickListener(this);
        } else if (this.ay == null && this.az == null && this.aA == null && this.aD == null && this.aE == null && this.aF == null) {
            this.optionButton.setVisibility(8);
            this.layoutTwoOption.setVisibility(8);
            this.layoutThreeOption.setVisibility(8);
        } else {
            this.optionButton.setVisibility(0);
            this.layoutTwoOption.setVisibility(8);
            this.layoutThreeOption.setVisibility(8);
            this.optionButton.setText(this.aA);
            this.optionButton.setOnClickListener(this);
        }
        if (this.as == null || this.as.size() <= 0) {
            this.listOptions.setVisibility(8);
            setCancelable(false);
            create.setCanceledOnTouchOutside(false);
            this.optionButton.setBackgroundResource(R.drawable.boton_redondeado_rojo);
            this.optionButton.setTextColor(getResources().getColor(R.color.white));
            this.titleView.setGravity(1);
        } else {
            this.listOptions.setVisibility(0);
            setCancelable(true);
            create.setCanceledOnTouchOutside(true);
            this.titleView.setGravity(GravityCompat.START);
            DialogAdapter dialogAdapter = new DialogAdapter(getActivity(), this.as, this.aB, this.au, this.at);
            this.aK = dialogAdapter;
            this.listOptions.setAdapter(this.aK);
            this.listOptions.setOnItemClickListener(this);
            this.optionButton.setBackgroundResource(R.drawable.boton_redondeado_blanco_borde_negro_fino);
            this.optionButton.setTextColor(getResources().getColor(R.color.generic_black));
        }
        if (this.ax != null) {
            try {
                CAccessibility cAccessibility = new CAccessibility(getActivity().getApplicationContext());
                this.messageView.setVisibility(0);
                this.messageView.setText(Html.fromHtml(Utils.formatIsbanHTMLCode(this.ax)));
                this.messageView.setContentDescription(Html.fromHtml(Utils.formatIsbanHTMLCode(cAccessibility.applyFilterAlertDialog(this.messageView.getText().toString()))));
                if (Utils.formatIsbanHTMLCode(this.ax).contains("href")) {
                    this.messageView.setMovementMethod(LinkMovementMethod.getInstance());
                }
                separadorview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.messageView.setVisibility(8);
        }
        this.titleView.setText(this.aw);
        try {
            this.titleView.setContentDescription(new CAccessibility(getActivity().getApplicationContext()).applyFilterGeneral(this.aw));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return create;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().requestWindowFeature(1);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onResume() {
        super.onResume();
    }

    private void y() {
        if (getArguments().get(ad) != null) {
            this.av = getArguments().getString(ad);
        }
        this.aw = getArguments().getString(af);
        this.ax = getArguments().getString(ag);
        this.as = this.as == null ? getArguments().getStringArrayList(ah) : this.as;
        this.au = this.au == null ? getArguments().getStringArrayList(ai) : this.au;
        this.at = this.at == null ? getArguments().getBooleanArray(aj) : this.at;
        this.ay = getArguments().getString(al);
        this.az = getArguments().getString(am);
        this.aB = this.aB == null ? getArguments().getString(an) : this.aB;
        this.aA = getArguments().getString(ak);
        this.aD = getArguments().getString(ao);
        this.aE = getArguments().getString(ap);
        this.aF = getArguments().getString(aq);
    }

    public void setId(String str) {
        this.av = str;
    }

    public void setSelectedOption(String str) {
        this.aB = str;
    }

    public List<String> getOptions() {
        return this.as;
    }

    public void setOptions(List<String> list) {
        this.as = list;
    }

    public String getTitle() {
        return this.aw;
    }

    public void setTitle(String str) {
        this.aw = str;
    }

    public String getMessage() {
        return this.ax;
    }

    public void setMessage(String str) {
        this.ax = str;
    }

    public void closeDialog() {
        if (this.aC.booleanValue()) {
            try {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        IsbanDialogFragment.this.aM = false;
                        IsbanDialogFragment.this.dismiss();
                    }
                }, 10);
            } catch (Exception unused) {
                dismiss();
            }
        }
    }

    public void setDismissListener(DismissListener dismissListener) {
        this.aL = dismissListener;
    }

    public void onIsbanDismiss() {
        if (this.aL != null) {
            this.aL.onIsbanDismiss();
        }
    }

    private void a(View view, boolean z) {
        ImageView imageView = (ImageView) view.findViewById(R.id.radioButton);
        if (imageView == null) {
            return;
        }
        if (z) {
            this.aK.setActiveOption(imageView);
        } else {
            this.aK.setDisableOption(imageView);
        }
    }

    public IDialogListener getDialogListener() {
        return this.aH;
    }

    public void setDialogListener(IDialogListener iDialogListener) {
        this.aH = iDialogListener;
    }

    public IDialogListenerExtended getDialogListenerExtended() {
        return this.aI;
    }

    public void setDialogListenerExtended(IDialogListenerExtended iDialogListenerExtended) {
        this.aI = iDialogListenerExtended;
    }

    public IDialogListenerThreeOptions getDialogListenerThreeOptions() {
        return this.aJ;
    }

    public void setDialogListenerThreeOptions(IDialogListenerThreeOptions iDialogListenerThreeOptions) {
        this.aJ = iDialogListenerThreeOptions;
    }

    public Boolean getAutoClose() {
        return this.aC;
    }

    public void setAutoClose(Boolean bool) {
        this.aC = bool;
    }

    public void show(FragmentManager fragmentManager, String str) {
        IsbanDialogFragment isbanDialogFragment;
        try {
            isbanDialogFragment = (IsbanDialogFragment) fragmentManager.findFragmentByTag(str);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            isbanDialogFragment = null;
        }
        if (isbanDialogFragment == null) {
            super.show(fragmentManager, str);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public void onClick(View view) {
        closeDialog();
        if (view.getId() == R.id.twoOptionsPositiveButton) {
            if (this.aH != null) {
                this.aH.onPositiveButton();
            } else if (this.aI != null) {
                this.aI.onPositiveButton(this.av);
            }
        } else if (view.getId() == R.id.twoOptionsNegativeButton) {
            if (this.aH != null) {
                this.aH.onNegativeButton();
            } else if (this.aI != null) {
                this.aI.onNegativeButton(this.av);
            }
        } else if (view.getId() == R.id.oneOptionButton) {
            if (this.aH != null) {
                this.aH.onSimpleActionButton();
            } else if (this.aI != null) {
                this.aI.onSimpleActionButton(this.av);
            }
        } else if (view.getId() == R.id.threeOptionsButton1) {
            this.aJ.onOption1Button();
        } else if (view.getId() == R.id.threeOptionsButton2) {
            this.aJ.onOption2Button();
        } else if (view.getId() == R.id.threeOptionsButton3) {
            this.aJ.onOption3Button();
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        try {
            if (this.aM && (this.at.length == 0 || this.at[i])) {
                this.aM = false;
                a(view, true);
                if (this.aH != null) {
                    this.aH.onItemSelected((String) this.as.get(i));
                }
                if (this.aI != null) {
                    this.aI.onItemSelected((String) this.as.get(i), this.av);
                }
                if (this.listOptions.getAdapter() != null) {
                    ((DialogAdapter) this.listOptions.getAdapter()).updateAdapterChecked();
                    UtilAccesibility.sendAccessibilityEvent(getActivity(), getString(R.string.CHECK_ON).concat(UtilsCuentas.SEPARAOR2).concat(new CAccessibility(getActivity()).applyFilterGeneral((String) this.as.get(i))));
                }
            }
        } catch (Throwable th) {
            closeDialog();
            throw th;
        }
        closeDialog();
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (this.aL != null) {
            this.aL.onIsbanDismiss();
        }
    }

    public void separadorview() {
        if (this.messageView.getText().toString() != null && this.messageView.getText().toString().equals(this.aG)) {
            this.vTitleSeparator.setVisibility(8);
        }
    }
}
