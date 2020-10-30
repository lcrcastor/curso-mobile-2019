package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.EnviarSugerenciaObjetoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VEnviarSugerenciaObjeto;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.indra.httpclient.beans.IBeanErroWS;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class FormularioSugerirSeguroObjetosFragment extends BaseFragment {
    public static final int MIN_CHAR = 1;
    @Inject
    IDataManager a;
    @Inject
    public AnalyticsManager analyticsManager;
    InputFilter b = new InputFilter() {
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            while (i < i2) {
                if (a(charSequence.charAt(i))) {
                    return "";
                }
                i++;
            }
            return null;
        }

        private boolean a(char c) {
            return String.valueOf(c).equalsIgnoreCase(".");
        }
    };
    @InjectView(2131364224)
    Button buttonEnviarSugerirObjetoAsegurar;
    private String c;
    @InjectView(2131364649)
    EditText editTextMarca;
    @InjectView(2131364650)
    EditText editTextModelo;
    @InjectView(2131364653)
    EditText editTextTipoDeProducto;
    @InjectView(2131364654)
    EditText editTextValorEstimado;
    @InjectView(2131366185)
    TextView tvSugerencia;

    class EmojiExcludeFilter implements InputFilter {
        private EmojiExcludeFilter() {
        }

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            while (i < i2) {
                int type = Character.getType(charSequence.charAt(i));
                if (type == 19 || type == 28) {
                    return "";
                }
                i++;
            }
            return null;
        }
    }

    public static FormularioSugerirSeguroObjetosFragment newInstance(String str) {
        FormularioSugerirSeguroObjetosFragment formularioSugerirSeguroObjetosFragment = new FormularioSugerirSeguroObjetosFragment();
        Bundle bundle = new Bundle();
        bundle.putString("leyenda", str);
        formularioSugerirSeguroObjetosFragment.setArguments(bundle);
        return formularioSugerirSeguroObjetosFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.c = getArguments().getString("leyenda");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_formulario_sugerir_seguro_objetos, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        z();
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.analyticsManager.trackScreen(Screens.suggestObject());
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onDetach() {
        super.onDetach();
    }

    private void z() {
        validateTextWatcherForm(this.editTextMarca);
        validateTextWatcherForm(this.editTextModelo);
        validateTextWatcherForm(this.editTextTipoDeProducto);
        validateTextWatcherForm(this.editTextValorEstimado);
        this.editTextMarca.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        this.editTextModelo.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        this.editTextTipoDeProducto.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        this.editTextValorEstimado.setFilters(new InputFilter[]{this.b});
        this.tvSugerencia.setText(this.c);
        this.buttonEnviarSugerirObjetoAsegurar.setEnabled(false);
        this.buttonEnviarSugerirObjetoAsegurar.setOnClickListener(new OneClickListener(new OneClicked() {
            public void onClicked(View view) {
                FormularioSugerirSeguroObjetosFragment.this.showProgress(VEnviarSugerenciaObjeto.nameService);
                FormularioSugerirSeguroObjetosFragment.this.y();
            }
        }));
    }

    public void enableEnviarButton(Boolean bool) {
        if (bool.booleanValue()) {
            this.buttonEnviarSugerirObjetoAsegurar.setBackground(getResources().getDrawable(R.drawable.button_active));
        } else {
            this.buttonEnviarSugerirObjetoAsegurar.setBackground(getResources().getDrawable(R.drawable.button_dissable));
        }
        this.buttonEnviarSugerirObjetoAsegurar.setEnabled(bool.booleanValue());
    }

    /* access modifiers changed from: private */
    public void A() {
        if (this.editTextTipoDeProducto.getText().length() < 1 || this.editTextMarca.getText().length() < 1 || this.editTextModelo.getText().length() < 1 || this.editTextValorEstimado.getText().length() < 1) {
            enableEnviarButton(Boolean.valueOf(false));
        } else {
            enableEnviarButton(Boolean.valueOf(true));
        }
    }

    public void validateTextWatcherForm(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                FormularioSugerirSeguroObjetosFragment.this.A();
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                FormularioSugerirSeguroObjetosFragment.this.A();
            }

            public void afterTextChanged(Editable editable) {
                FormularioSugerirSeguroObjetosFragment.this.A();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void y() {
        String obj = this.editTextMarca.getText().toString();
        String obj2 = this.editTextModelo.getText().toString();
        this.a.enviarSugerenciaObjeto(this.editTextTipoDeProducto.getText().toString(), obj, obj2, this.editTextValorEstimado.getText().toString());
    }

    @Subscribe
    public void enviarSugerenciaObjetos(EnviarSugerenciaObjetoEvent enviarSugerenciaObjetoEvent) {
        final EnviarSugerenciaObjetoEvent enviarSugerenciaObjetoEvent2 = enviarSugerenciaObjetoEvent;
        AnonymousClass4 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGURO_ENVIAR_SUGERENCIA_OBJETO, this, true, (BaseActivity) getContext()) {
            public void onOk() {
                FormularioSugerirSeguroObjetosFragment.this.dismissProgress();
                FormularioSugerirSeguroObjetosFragment.this.a(enviarSugerenciaObjetoEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes1Error(WebServiceEvent webServiceEvent) {
                super.onRes1Error(webServiceEvent);
                FormularioSugerirSeguroObjetosFragment.this.dismissProgress();
            }

            /* access modifiers changed from: protected */
            public void onRes1Error() {
                super.onRes1Error();
                FormularioSugerirSeguroObjetosFragment.this.dismissProgress();
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                super.commonAllErrorsAfterProcess(webServiceEvent);
                FormularioSugerirSeguroObjetosFragment.this.dismissProgress();
            }
        };
        r0.handleWSResponse(enviarSugerenciaObjetoEvent);
    }

    /* access modifiers changed from: private */
    public void a(EnviarSugerenciaObjetoEvent enviarSugerenciaObjetoEvent) {
        ErrorBodyBean errorBodyBean = (ErrorBodyBean) ((IBeanErroWS) enviarSugerenciaObjetoEvent.getBeanResponse()).getErrorBeanObject();
        if (errorBodyBean.resTitle != null && errorBodyBean.resCod != null && errorBodyBean.resDesc != null) {
            String obj = Html.fromHtml(errorBodyBean.resTitle).toString();
            String obj2 = Html.fromHtml(errorBodyBean.resCod).toString();
            Html.fromHtml(errorBodyBean.resDesc).toString();
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(obj2, null, obj, getContext().getString(R.string.ID1_ALERT_BTN_ACCEPT));
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                    FormularioSugerirSeguroObjetosFragment.this.getActivity().finish();
                }
            });
            newInstance.show(getActivity().getSupportFragmentManager(), obj2);
        }
    }
}
