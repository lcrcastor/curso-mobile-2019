package ar.com.santander.rio.mbanking.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.EmptyFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.DataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GenesysChatEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.versions.VGenesysChat;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.Timer;
import java.util.TimerTask;

public class IngresarMailDialog extends DialogFragment {
    private static String ad;
    /* access modifiers changed from: private */
    public static EditText ag;
    /* access modifiers changed from: private */
    public static IngresarMailDialog ah;
    private static SessionManager am;
    private static SettingsManager an;
    /* access modifiers changed from: private */
    public Button ae;
    /* access modifiers changed from: private */
    public Button af;
    private ConstraintLayout ai;
    /* access modifiers changed from: private */
    public TextView aj;
    /* access modifiers changed from: private */
    public IngresarMailDialogListener ak;
    /* access modifiers changed from: private */
    public DataManager al;
    /* access modifiers changed from: private */
    public AnalyticsManager ao;

    public interface IngresarMailDialogListener {
        void enviarMailAlservioGenesysCaht(String str);
    }

    public static IngresarMailDialog newInstance(IngresarMailDialogListener ingresarMailDialogListener) {
        IngresarMailDialog ingresarMailDialog = new IngresarMailDialog();
        ingresarMailDialog.ak = ingresarMailDialogListener;
        return ingresarMailDialog;
    }

    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawableResource(17170445);
        getDialog().setCanceledOnTouchOutside(false);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.vista_watson_mensaje_operador_no_disponible, viewGroup);
        setInitialize(inflate);
        setOnClickListeners();
        return inflate;
    }

    public void setInitialize(View view) {
        this.ae = (Button) view.findViewById(R.id.buttonConfirmar);
        this.af = (Button) view.findViewById(R.id.buttonCancelar);
        ag = (EditText) view.findViewById(R.id.editTextOperadorNoDisponible);
        this.aj = (TextView) view.findViewById(R.id.muchasGracias);
        this.ai = (ConstraintLayout) view.findViewById(R.id.ingresaTuMail);
        this.ai.setVisibility(0);
        if (ad != null && !ad.equalsIgnoreCase("")) {
            ag.setText(ad);
            ag.setFocusable(false);
        }
        Bus bus = new Bus();
        bus.register(this);
        this.al = new DataManager(BaseApplication.get(getActivity()), bus, am, an);
    }

    public void setOnClickListeners() {
        this.ae.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String obj = IngresarMailDialog.ag.getText().toString();
                if (!obj.equalsIgnoreCase("") && IngresarMailDialog.isValidEmail(obj)) {
                    IngresarMailDialog.this.ak.enviarMailAlservioGenesysCaht(obj);
                    IngresarMailDialog.this.ao.trackScreen(IngresarMailDialog.this.getResources().getString(R.string.analytics_trackevent_action_watson_consulta_fuera_de_horario_exito));
                    IngresarMailDialog.this.al.genesysChat("4", obj);
                    IngresarMailDialog.this.af.setBackground(IngresarMailDialog.this.getResources().getDrawable(R.drawable.boton_redondeado_gris));
                    IngresarMailDialog.this.af.setTextColor(IngresarMailDialog.this.getResources().getColor(R.color.white));
                    IngresarMailDialog.this.ae.setBackground(IngresarMailDialog.this.getResources().getDrawable(R.drawable.boton_redondeado_gris));
                }
                InputMethodManager inputMethodManager = (InputMethodManager) IngresarMailDialog.this.getActivity().getSystemService("input_method");
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        this.af.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                IngresarMailDialog.ah.dismiss();
            }
        });
    }

    public static void showFragmentManager(FragmentManager fragmentManager, IngresarMailDialogListener ingresarMailDialogListener, String str, SessionManager sessionManager, SettingsManager settingsManager, AnalyticsManager analyticsManager) {
        IngresarMailDialog newInstance = newInstance(ingresarMailDialogListener);
        newInstance.show(fragmentManager, str);
        ah = newInstance;
        am = sessionManager;
        an = settingsManager;
    }

    public static boolean isValidEmail(CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    /* access modifiers changed from: private */
    public void A() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                IngresarMailDialog.ah.dismiss();
                timer.cancel();
            }
        }, 3000);
    }

    public static EditText getEditText() {
        return ag;
    }

    public static String getMailDefaul() {
        return ad;
    }

    public static void setMailDefaul(String str) {
        ad = str;
    }

    @Subscribe
    public void onGenesysChat(GenesysChatEvent genesysChatEvent) {
        final GenesysChatEvent genesysChatEvent2 = genesysChatEvent;
        AnonymousClass4 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, VGenesysChat.nameService, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                IngresarMailDialog.this.ae.setVisibility(8);
                IngresarMailDialog.this.af.setVisibility(8);
                IngresarMailDialog.this.aj.setVisibility(0);
                IngresarMailDialog.this.A();
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                EmptyFragment newInstance = EmptyFragment.newInstance("Watson", Html.fromHtml(genesysChatEvent2.getErrorBodyBean().resDesc).toString(), R.drawable.error_continuacion);
                FragmentManager fragmentManager = IngresarMailDialog.this.getFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                    beginTransaction.add((int) R.id.content_frame, (Fragment) newInstance);
                    beginTransaction.commit();
                }
            }
        };
        r0.handleWSResponse(genesysChatEvent);
    }
}
