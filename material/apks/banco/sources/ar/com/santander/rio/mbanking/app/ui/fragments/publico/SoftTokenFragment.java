package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.softtoken.ISoftTokenConfirmation;
import ar.com.santander.rio.mbanking.app.module.softtoken.ISoftTokenNew;
import ar.com.santander.rio.mbanking.app.module.softtoken.ISoftTokenTimer;
import ar.com.santander.rio.mbanking.app.module.softtoken.SoftTokenConfirmation;
import ar.com.santander.rio.mbanking.app.module.softtoken.SoftTokenNew;
import ar.com.santander.rio.mbanking.app.module.softtoken.SoftTokenTimer;
import ar.com.santander.rio.mbanking.app.module.softtoken.SoftTokenView;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.MutableCountDownTimer;
import ar.com.santander.rio.mbanking.components.OnSingleClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SecuredPreferences;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager.SeedState;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager.SinchronizeDeviceListener;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import javax.inject.Inject;

public class SoftTokenFragment extends BaseFragment implements SoftTokenView {
    @Inject
    SoftTokenManager a;
    private MutableCountDownTimer ad;
    /* access modifiers changed from: private */
    public int ae = 0;
    /* access modifiers changed from: private */
    public int af = 0;
    /* access modifiers changed from: private */
    public SecuredPreferences ag;
    @Inject
    AnalyticsManager b;
    ISoftTokenNew c;
    @InjectView(2131362499)
    ProgressBar countDownProgressBar;
    @InjectView(2131362488)
    TextView countDownText;
    ISoftTokenConfirmation d;
    ISoftTokenTimer e;
    String f = "";
    private View g;
    private View h;
    /* access modifiers changed from: private */
    public IsbanDialogFragment i;
    @InjectView(2131362480)
    TextView label1_termsCondition;
    @InjectView(2131362481)
    TextView label2_termsCondition;
    @InjectView(2131362475)
    TextView lbl_recuperar;
    @InjectView(2131362477)
    TextView lbl_softtokensubtitulo;
    @InjectView(2131362482)
    TextView lbl_softtokentitulo;
    @InjectView(2131362467)
    TextView lbl_stfTitulo;
    @InjectView(2131362466)
    TextView lbl_stfsubtitulo;
    @InjectView(2131362490)
    TextView lbl_sttOpciones;
    @InjectView(2131362494)
    TextView lbl_sttSubtitulo;
    @InjectView(2131362496)
    TextView lbl_sttTitulo;
    @InjectView(2131362497)
    TextView lbl_sttTokenBloqueado;
    @InjectView(2131362478)
    TextView lbl_subtitulClaveToken;
    @InjectView(2131362479)
    TextView lbl_subtituloCodigoAsociacion;
    @InjectView(2131362473)
    EditText mClaveView;
    @InjectView(2131362474)
    EditText mCodigoView;
    @InjectView(2131362471)
    Button mContinuarButton;
    @InjectView(2131366380)
    ViewFlipper mControlPager;
    @InjectView(2131362462)
    Button mGetTokenButton;
    @InjectView(2131362489)
    TextView mNuevoToken;
    @InjectView(2131362493)
    TextView mSincronizarHorario;
    @InjectView(2131362495)
    TextView mSubtituloClaveToken;
    @InjectView(2131362472)
    ImageView mTerminosCondicionesView;
    @InjectView(2131362463)
    Button mVolverButton;

    public void onFinishPageTokenConfirmation() {
    }

    private void y() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(R.string.ID2218_TOKENSEGURIDAD_BTN_SINCHS));
        arrayList.add(getResources().getString(R.string.ID2219_TOKENSEGURIDAD_BTN_CONFIGNUETOKSEG));
        arrayList.add(getResources().getString(R.string.ID2220_TOKENSEGURIDAD_BTN_AYUDA));
        this.i = IsbanDialogFragment.newInstance("mPopupMenu", null, null, arrayList, getString(R.string.ID2060_ENVEFECT_BTN_CANCELAR), null, null, null, arrayList);
        this.i.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(SoftTokenFragment.this.getResources().getString(R.string.ID2218_TOKENSEGURIDAD_BTN_SINCHS))) {
                    SoftTokenFragment.this.sincronizarDispositivo();
                } else if (str.equalsIgnoreCase(SoftTokenFragment.this.getResources().getString(R.string.ID2219_TOKENSEGURIDAD_BTN_CONFIGNUETOKSEG))) {
                    SoftTokenFragment.this.popupNewToken();
                } else if (str.equalsIgnoreCase(SoftTokenFragment.this.getResources().getString(R.string.ID2220_TOKENSEGURIDAD_BTN_AYUDA))) {
                    SoftTokenFragment.this.llamarAyuda();
                }
            }
        });
        this.i.setCancelable(true);
    }

    public void llamarAyuda() {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG_TIT));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG));
        this.b.trackScreen(getString(R.string.analytics_softtoken_ayuda_acerca_token_virtual));
        startActivity(intent);
    }

    public void popupNewToken() {
        this.b.trackScreen(getString(R.string.analytics_softtoken_aviso_configuracion_nuevo_token_virtual));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getResources().getString(R.string.USER200046_TITLE), Utils.formatIsbanHTMLCode(Html.fromHtml(getResources().getString(R.string.USER200046)).toString()), null, null, "Sí", "No", null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                SoftTokenFragment.this.e.goToPage(Integer.valueOf(0), SoftTokenFragment.this.c, 1);
            }

            public void onNegativeButton() {
                SoftTokenFragment.this.b.trackEvent(SoftTokenFragment.this.getString(R.string.analytics_category_softtoken), SoftTokenFragment.this.getString(R.string.analytics_action_cancelar_nuevo_token), SoftTokenFragment.this.getString(R.string.analytics_label_cancelar_aviso_nuevo_token));
            }
        });
        newInstance.show(getFragmentManager(), "DialogNewToken");
    }

    public void sincronizarDispositivo() {
        showProgress("synchronizeDevice");
        this.a.synchronizeDevice(new SinchronizeDeviceListener() {
            public void onSuccess() {
                SoftTokenFragment.this.b(SoftTokenFragment.this.a.getToken(), Long.valueOf(SoftTokenFragment.this.a.getTokenTimeLeft()));
                SoftTokenFragment.this.dismissProgress();
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(SoftTokenFragment.this.getResources().getString(R.string.USER200044_TITLE), Utils.formatIsbanHTMLCode(Html.fromHtml(SoftTokenFragment.this.getResources().getString(R.string.USER200044)).toString()), null, null, SoftTokenFragment.this.getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                newInstance.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onNegativeButton() {
                    }

                    public void onPositiveButton() {
                    }

                    public void onSimpleActionButton() {
                    }
                });
                newInstance.show(SoftTokenFragment.this.getFragmentManager(), "Dialog");
                SoftTokenFragment.this.b.trackScreen(SoftTokenFragment.this.getString(R.string.analytics_softtoken_dispositivo_sincronizado));
            }

            public void onError(String str) {
                SoftTokenFragment.this.progressIndicator.dismiss();
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(SoftTokenFragment.this.getResources().getString(R.string.USER200043_TITLE), Utils.formatIsbanHTMLCode(Html.fromHtml(SoftTokenFragment.this.getResources().getString(R.string.USER200043)).toString()), null, null, SoftTokenFragment.this.getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                newInstance.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onNegativeButton() {
                    }

                    public void onPositiveButton() {
                    }

                    public void onSimpleActionButton() {
                    }
                });
                newInstance.show(SoftTokenFragment.this.getFragmentManager(), "Dialog");
                SoftTokenFragment.this.b.trackScreen(SoftTokenFragment.this.getString(R.string.analytics_softtoken_dispositivo_no_sincronizado));
            }
        });
    }

    public void validarIngresoDatos() {
        if (this.mCodigoView.getText().toString().length() == 0 || !b(this.mClaveView.getText().toString()) || !b((View) this.mTerminosCondicionesView) || !c(this.mCodigoView.getText().toString())) {
            this.mContinuarButton.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
            this.mContinuarButton.setTextColor(getResources().getColor(R.color.white));
            this.mContinuarButton.setEnabled(false);
            return;
        }
        this.mContinuarButton.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
        this.mContinuarButton.setTextColor(getResources().getColor(R.color.white));
        this.mContinuarButton.setEnabled(true);
    }

    private boolean b(String str) {
        return str.length() == 6;
    }

    private boolean c(String str) {
        return str.length() == 8;
    }

    private boolean b(View view) {
        Object tag = view.getTag();
        return tag != null && ((Integer) tag).intValue() == R.drawable.ic_checkbox_on_rojo;
    }

    /* access modifiers changed from: private */
    public void c(View view) {
        Object tag = view.getTag();
        if (tag == null || ((Integer) tag).intValue() != R.drawable.ic_checkbox_off_gris) {
            view.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
            view.setTag(Integer.valueOf(R.drawable.ic_checkbox_off_gris));
            return;
        }
        view.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_on_rojo));
        view.setTag(Integer.valueOf(R.drawable.ic_checkbox_on_rojo));
    }

    private int z() {
        return this.a.getSeedState() == SeedState.ACTIVE_SEED ? 2 : 0;
    }

    private void A() {
        int z = z();
        switch (z) {
            case 0:
                this.c.onCreatePage();
                break;
            case 1:
                this.d.onCreatePage();
                break;
            case 2:
                this.e.onCreatePage();
                break;
            default:
                this.c.onCreatePage();
                break;
        }
        this.mControlPager.setDisplayedChild(z);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.g = layoutInflater.inflate(R.layout.fragment_soft_token, viewGroup, false);
        ButterKnife.inject((Object) this, this.g);
        this.c = new SoftTokenNew(this, this.mControlPager);
        this.d = new SoftTokenConfirmation(this, this.mControlPager);
        this.e = new SoftTokenTimer(this, this.mControlPager);
        y();
        AnonymousClass4 r0 = new MutableCountDownTimer(0, 50) {
            public void onTick(long j) {
                final int i = ((int) j) / 1000;
                SoftTokenFragment.this.ae = Long.valueOf(40000 - getMillisInFuture()).intValue() + ((int) (((double) getMillisInFuture()) - (Math.ceil((double) (j / 1000)) * 1000.0d)));
                if (SoftTokenFragment.this.af != i) {
                    SoftTokenFragment.this.af = i;
                    AnonymousClass1 r9 = new AnimatorListenerAdapter() {
                        public void onAnimationStart(Animator animator) {
                            SoftTokenFragment.this.countDownText.setText(String.valueOf(i));
                            TextView textView = SoftTokenFragment.this.countDownText;
                            StringBuilder sb = new StringBuilder();
                            sb.append(String.valueOf(i));
                            sb.append(" Segundos");
                            textView.setContentDescription(sb.toString());
                        }
                    };
                    SoftTokenFragment.this.setAnimatedProgress(SoftTokenFragment.this.countDownProgressBar, SoftTokenFragment.this.ae, r9);
                }
            }

            public void onFinish() {
                SoftTokenFragment.this.b(SoftTokenFragment.this.a.getToken(), Long.valueOf(SoftTokenFragment.this.a.getTokenTimeLeft()));
            }
        };
        this.ad = r0;
        A();
        this.mSincronizarHorario.setContentDescription(CAccessibility.applyFilterMaskVinculo(getString(R.string.ID2216_TOKENSEGURIDAD_LBL_AYUDA_SINCHS_2)));
        this.mNuevoToken.setContentDescription(CAccessibility.applyFilterMaskVinculo(getString(R.string.ID2217_TOKENSEGURIDAD_LBL_AYUDA_BLOQTOKSEG_2)));
        TextView textView = this.label1_termsCondition;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.ID2207_TOKENSEGURIDAD_LBL_TYC_1_ACEPTO));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getString(R.string.ID2207_TOKENSEGURIDAD_LBL_TYC_2));
        textView.setContentDescription(sb.toString());
        TextView textView2 = this.label2_termsCondition;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getString(R.string.ID2207_TOKENSEGURIDAD_LBL_TYC_1_VER));
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(getString(R.string.ID2207_TOKENSEGURIDAD_LBL_TYC_2));
        textView2.setContentDescription(CAccessibility.applyFilterMaskVinculo(sb2.toString()));
        this.mTerminosCondicionesView.setContentDescription(CAccessibility.applyFilterMaskInterruptor(getString(R.string.ID2207_TOKENSEGURIDAD_LBL_TYC_2)));
        String string = getString(R.string.ID2208_TOKENSEGURIDAD_LBL_AYUDA_OLVIDO_1);
        String string2 = getString(R.string.ID2208_TOKENSEGURIDAD_LBL_AYUDA_OLVIDO_2);
        String concat = string.concat(UtilsCuentas.SEPARAOR2).concat(string2);
        int indexOf = concat.indexOf(string2);
        int length = string2.length() + indexOf;
        SpannableString spannableString = new SpannableString(concat);
        spannableString.setSpan(new ClickableSpan() {
            public void onClick(View view) {
                Intent intent = new Intent(SoftTokenFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, SoftTokenFragment.this.getResources().getString(R.string.ID2224_TOKENSEGURIDAD_LBL_AYUDA_TOKYCODASC_TIT));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, SoftTokenFragment.this.getResources().getString(R.string.ID2224_TOKENSEGURIDAD_LBL_AYUDA_TOKYCODASC));
                SoftTokenFragment.this.startActivity(intent);
            }
        }, indexOf, length, 0);
        spannableString.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.generic_red)), indexOf, length, 0);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, length, 0);
        spannableString.setSpan(new StyleSpan(1), indexOf, length, 0);
        this.lbl_recuperar.setText(spannableString);
        return this.g;
    }

    public void onCreatePageTokenNew() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_HELP);
        this.h = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        ((ImageView) this.h.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.switchDrawer();
            }
        });
        ((ImageView) this.h.findViewById(R.id.img_help)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.llamarAyuda();
            }
        });
        this.h.findViewById(R.id.img_help).setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_AYUDA)));
        AnonymousClass8 r0 = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                SoftTokenFragment.this.validarIngresoDatos();
            }
        };
        this.mClaveView.addTextChangedListener(r0);
        this.mCodigoView.addTextChangedListener(r0);
        this.ag = SecuredPreferences.getInstance(getActivity());
        if (this.ag.getBoolean("isban.rio.temporal.softtoken.tyc", Boolean.valueOf(false)).booleanValue()) {
            this.mTerminosCondicionesView.setTag(Integer.valueOf(R.drawable.ic_checkbox_on_rojo));
            this.mTerminosCondicionesView.setVisibility(8);
            this.label1_termsCondition.setText(getString(R.string.ID2207_TOKENSEGURIDAD_LBL_TYC_1_VER));
            this.label1_termsCondition.setImportantForAccessibility(2);
        } else {
            this.mTerminosCondicionesView.setTag(Integer.valueOf(R.drawable.ic_checkbox_off_gris));
        }
        this.mTerminosCondicionesView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.c(view);
                SoftTokenFragment.this.validarIngresoDatos();
            }
        });
        this.mContinuarButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.onAttempDoLogin();
            }
        });
        this.mContinuarButton.setContentDescription(CAccessibility.applyFilterMaskBotton(this.mContinuarButton.getText().toString()));
        this.label2_termsCondition.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.b.trackScreen(SoftTokenFragment.this.getString(R.string.analytics_softtoken_terminos_condiciones));
                Intent intent = new Intent(SoftTokenFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, SoftTokenFragment.this.getResources().getString(R.string.ID2223_TOKENSEGURIDAD_LBL_AYUDA_TYC_TIT));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, SoftTokenFragment.this.getResources().getString(R.string.ID2223_TOKENSEGURIDAD_LBL_AYUDA_TYC));
                SoftTokenFragment.this.startActivity(intent);
            }
        });
        this.lbl_recuperar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SoftTokenFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, SoftTokenFragment.this.getResources().getString(R.string.ID2224_TOKENSEGURIDAD_LBL_AYUDA_TOKYCODASC_TIT));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, SoftTokenFragment.this.getResources().getString(R.string.ID2224_TOKENSEGURIDAD_LBL_AYUDA_TOKYCODASC));
                SoftTokenFragment.this.startActivity(intent);
            }
        });
        this.lbl_subtitulClaveToken.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.f = "subClave";
            }
        });
        this.lbl_subtituloCodigoAsociacion.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SoftTokenFragment.this.f.equalsIgnoreCase("subClave")) {
                    SoftTokenFragment.this.f = "subCodigo";
                    return;
                }
                SoftTokenFragment.this.f = "";
            }
        });
        this.lbl_softtokensubtitulo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SoftTokenFragment.this.f.equalsIgnoreCase("subCodigo")) {
                    SoftTokenFragment.this.f = "subtitle";
                    return;
                }
                SoftTokenFragment.this.f = "";
            }
        });
        this.b.trackScreen(getString(R.string.analytics_softtoken_configuracion_token_virtual));
    }

    public void onFinishPageTokenNew() {
        this.mCodigoView.setText("");
        this.mClaveView.setText("");
        this.mTerminosCondicionesView.setBackground(getResources().getDrawable(R.drawable.ic_checkbox_off_gris));
        this.mTerminosCondicionesView.setTag(Integer.valueOf(R.drawable.ic_checkbox_off_gris));
        this.mContinuarButton.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
        this.mContinuarButton.setTextColor(getResources().getColor(R.color.white));
        this.mContinuarButton.setEnabled(false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0070  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onAttempDoLogin() {
        /*
            r6 = this;
            android.widget.EditText r0 = r6.mClaveView
            r1 = 0
            r0.setError(r1)
            android.widget.EditText r0 = r6.mCodigoView
            r0.setError(r1)
            android.widget.EditText r0 = r6.mClaveView
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            android.widget.EditText r2 = r6.mCodigoView
            android.text.Editable r2 = r2.getText()
            java.lang.String r2 = r2.toString()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            r4 = 1
            if (r3 == 0) goto L_0x002a
            android.widget.EditText r1 = r6.mCodigoView
            r3 = 1
            goto L_0x002b
        L_0x002a:
            r3 = 0
        L_0x002b:
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            if (r5 == 0) goto L_0x0035
            android.widget.EditText r1 = r6.mClaveView
        L_0x0033:
            r3 = 1
            goto L_0x005f
        L_0x0035:
            boolean r5 = r6.b(r0)
            if (r5 != 0) goto L_0x004a
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.b
            r3 = 2131757998(0x7f100bae, float:1.9146948E38)
            java.lang.String r3 = r6.getString(r3)
            r1.trackScreen(r3)
            android.widget.EditText r1 = r6.mClaveView
            r3 = 1
        L_0x004a:
            boolean r5 = r6.c(r2)
            if (r5 != 0) goto L_0x005f
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r6.b
            r3 = 2131757996(0x7f100bac, float:1.9146944E38)
            java.lang.String r3 = r6.getString(r3)
            r1.trackScreen(r3)
            android.widget.EditText r1 = r6.mCodigoView
            goto L_0x0033
        L_0x005f:
            android.widget.ImageView r5 = r6.mTerminosCondicionesView
            boolean r5 = r6.b(r5)
            if (r5 != 0) goto L_0x006a
            android.widget.ImageView r1 = r6.mTerminosCondicionesView
            r3 = 1
        L_0x006a:
            if (r3 == 0) goto L_0x0070
            r1.requestFocus()
            goto L_0x007f
        L_0x0070:
            java.lang.String r1 = "obtainSeedDevice"
            r6.showProgress(r1)
            ar.com.santander.rio.mbanking.managers.security.SoftTokenManager r1 = r6.a
            ar.com.santander.rio.mbanking.app.ui.fragments.publico.SoftTokenFragment$16 r3 = new ar.com.santander.rio.mbanking.app.ui.fragments.publico.SoftTokenFragment$16
            r3.<init>()
            r1.obtainSeedDevice(r2, r0, r3)
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.publico.SoftTokenFragment.onAttempDoLogin():void");
    }

    public void onCreatePageTokenConfirmation() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_HELP);
        this.h = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        ((ImageView) this.h.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.switchDrawer();
            }
        });
        this.h.findViewById(R.id.img_help).setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_AYUDA)));
        ((ImageView) this.h.findViewById(R.id.img_help)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.llamarAyuda();
            }
        });
        this.mVolverButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.d.onVolverAlInicio();
            }
        });
        this.mVolverButton.setContentDescription(CAccessibility.applyFilterMaskBotton(this.mVolverButton.getText().toString()));
        this.mGetTokenButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.d.onVerToken();
            }
        });
        this.mGetTokenButton.setContentDescription(CAccessibility.applyFilterMaskBotton(this.mGetTokenButton.getText().toString()));
    }

    public void onVerToken() {
        this.d.goToPage(Integer.valueOf(2), this.e, 2);
    }

    public void onVolverAlInicio() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void a(String str, Long l) {
        this.ae = Long.valueOf(40000 - l.longValue()).intValue();
        TextView textView = this.mSubtituloClaveToken;
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 3));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(str.substring(3, 6));
        textView.setText(sb.toString());
        try {
            this.mSubtituloClaveToken.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(str));
        } catch (Exception unused) {
            this.mSubtituloClaveToken.setContentDescription(str);
        }
        this.countDownText.setText(String.valueOf(l.longValue() / 1000));
        this.countDownProgressBar.setMax(40000);
        if (this.countDownProgressBar.getProgress() > 0) {
            setAnimatedProgress(this.countDownProgressBar, this.ae, new AnimatorListenerAdapter[0]);
        } else {
            this.countDownProgressBar.setProgress(40000 - l.intValue());
        }
    }

    /* access modifiers changed from: private */
    public void b(String str, Long l) {
        a(str, l);
        this.ad.setMillisInFuture(l.longValue());
        this.ad.start();
    }

    public void setAnimatedProgress(ProgressBar progressBar, int i2, AnimatorListenerAdapter... animatorListenerAdapterArr) {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(progressBar, NotificationCompat.CATEGORY_PROGRESS, new int[]{progressBar.getProgress(), i2});
        ofInt.setDuration(1000);
        ofInt.setInterpolator(new DecelerateInterpolator());
        for (AnimatorListenerAdapter addListener : animatorListenerAdapterArr) {
            ofInt.addListener(addListener);
        }
        ofInt.start();
    }

    public void onCreatePageTokenTimer() {
        ((BaseActivity) getActivity()).setActionBarType(ActionBarType.MAIN_WITH_CONFIG);
        this.h = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        ((ImageView) this.h.findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.switchDrawer();
            }
        });
        this.h.findViewById(R.id.config_imgButton).setContentDescription(CAccessibility.applyFilterMaskBotton(getString(R.string.CONTENT_CONFIGURACION)));
        ((ImageView) this.h.findViewById(R.id.config_imgButton)).setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                SoftTokenFragment.this.i.show(SoftTokenFragment.this.getFragmentManager(), "Dialog");
            }
        });
        b(this.a.getToken(), Long.valueOf(this.a.getTokenTimeLeft()));
        this.mSincronizarHorario.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.sincronizarDispositivo();
            }
        });
        this.mNuevoToken.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SoftTokenFragment.this.b.trackScreen(SoftTokenFragment.this.getString(R.string.analytics_softtoken_ayuda_obtener_pin_codigo));
                Intent intent = new Intent(SoftTokenFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, SoftTokenFragment.this.getResources().getString(R.string.ID2224_TOKENSEGURIDAD_LBL_AYUDA_TOKYCODASC_TIT));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, SoftTokenFragment.this.getResources().getString(R.string.ID2224_TOKENSEGURIDAD_LBL_AYUDA_TOKYCODASC));
                SoftTokenFragment.this.startActivity(intent);
            }
        });
        this.b.trackScreen(getString(R.string.analytics_softtoken_visualizacion_token_virtual));
    }

    public void applyLetterSpacing() {
        UtilStyleCommons.setLetterSpacing(this.lbl_softtokentitulo, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_softtokensubtitulo, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_subtitulClaveToken, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_subtituloCodigoAsociacion, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_recuperar, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.label1_termsCondition, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.label2_termsCondition, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_recuperar, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_stfTitulo, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_stfsubtitulo, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_sttTitulo, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_sttSubtitulo, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.mSubtituloClaveToken, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.mSincronizarHorario, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_sttOpciones, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.lbl_sttTokenBloqueado, -1.5f);
        UtilStyleCommons.setLetterSpacing(this.mNuevoToken, -1.5f);
    }

    public void onFinishPageTokenTimer() {
        this.ad.cancel();
    }

    public void onBackPressed() {
        try {
            getActivity().finish();
        } catch (Exception e2) {
            Log.e("@dev", "Error al destruir la actividad", e2);
        }
    }

    public void onPause() {
        super.onPause();
        this.ad.cancel();
    }

    public void onResume() {
        super.onResume();
        if (this.mControlPager.getDisplayedChild() == 2) {
            b(this.a.getToken(), Long.valueOf(this.a.getTokenTimeLeft()));
        }
    }
}
