package ar.com.santander.rio.mbanking.app.ui.fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.adapters.EnvironmentAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.EnvironmentTypeAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.TokenEnviromentAdapter;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.EnvironmentToken;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.EnvironmentType;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;

public class SelectorEnvironmentFragment extends DialogFragment implements OnItemSelectedListener {
    private final String ad = "PREF_CUSTOM_URL";
    private final String ae = "PREF_LAST_ENV";
    private final String af = "PREF_LAST_ENV_TYPE";
    private final String ag = "cPREF_LAST_ENV_TOKEN";
    private SharedPreferences ah;
    private Environment ai;
    private EnvironmentToken aj;
    public ISelectorEnvironmentFragment listener;
    @InjectView(2131365752)
    public Spinner mEnvironmentToken;
    @InjectView(2131365750)
    public Spinner mEnvironmentType;
    @InjectView(2131364675)
    public EditText mInputToken;
    @InjectView(2131364676)
    public EditText mInputUrl;
    @InjectView(2131365751)
    public Spinner mSelectorEnvironment;
    @InjectView(2131365589)
    public LinearLayout rowToken;

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void setListener(ISelectorEnvironmentFragment iSelectorEnvironmentFragment) {
        this.listener = iSelectorEnvironmentFragment;
    }

    private EnvironmentAdapter a(EnvironmentType environmentType) {
        ArrayList arrayList = new ArrayList();
        if (this.mSelectorEnvironment != null) {
            for (Environment add : ManagerTypeEnvironment.getEnvironmentsOfType(environmentType)) {
                arrayList.add(add);
            }
        }
        return new EnvironmentAdapter(getActivity(), 17367048, arrayList);
    }

    /* access modifiers changed from: private */
    public EnvironmentTypeAdapter y() {
        ArrayList arrayList = new ArrayList();
        if (this.mSelectorEnvironment != null) {
            for (EnvironmentType add : EnvironmentType.values()) {
                arrayList.add(add);
            }
        }
        return new EnvironmentTypeAdapter(getActivity(), 17367048, arrayList);
    }

    /* access modifiers changed from: private */
    public TokenEnviromentAdapter z() {
        ArrayList arrayList = new ArrayList();
        if (this.mEnvironmentToken != null) {
            for (EnvironmentToken add : ManagerTypeEnvironment.getEnvironmentsToken()) {
                arrayList.add(add);
            }
        }
        return new TokenEnviromentAdapter(getActivity(), 17367048, arrayList);
    }

    private void A() {
        if (this.mEnvironmentType != null && this.mSelectorEnvironment != null) {
            String b = b("PREF_LAST_ENV_TYPE");
            String b2 = b("PREF_LAST_ENV");
            if (TextUtils.isEmpty(b) || TextUtils.isEmpty(b2)) {
                b = EnvironmentType.DESARROLLO.toString();
                b2 = Environment.MB_1.toString();
            }
            EnvironmentType valueOf = EnvironmentType.valueOf(b);
            this.ai = Environment.valueOf(b2);
            EnvironmentTypeAdapter y = y();
            this.mEnvironmentType.setAdapter(y);
            this.mEnvironmentType.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    SelectorEnvironmentFragment.this.b(SelectorEnvironmentFragment.this.y().getItem(i));
                }
            });
            this.mEnvironmentType.setSelection(y.getPosition(valueOf));
        }
    }

    private void B() {
        if (this.mEnvironmentToken != null) {
            String b = b("cPREF_LAST_ENV_TOKEN");
            if (TextUtils.isEmpty(b)) {
                b = EnvironmentToken.HOMO.toString();
            }
            this.aj = EnvironmentToken.valueOf(b);
            TokenEnviromentAdapter z = z();
            this.mEnvironmentToken.setAdapter(z);
            this.mEnvironmentToken.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (SelectorEnvironmentFragment.this.mInputToken != null) {
                        EnvironmentToken item = SelectorEnvironmentFragment.this.z().getItem(i);
                        SelectorEnvironmentFragment.this.mInputToken.setText(ManagerTypeEnvironment.getUrlofToken(item));
                        if (item.equals(EnvironmentToken.CUSTOM)) {
                            SelectorEnvironmentFragment.this.mInputToken.setEnabled(true);
                        } else {
                            SelectorEnvironmentFragment.this.mInputToken.setEnabled(false);
                        }
                    }
                }
            });
            this.mEnvironmentToken.setSelection(z.getPosition(this.aj));
        }
    }

    /* access modifiers changed from: private */
    public void b(EnvironmentType environmentType) {
        EnvironmentAdapter a = a(environmentType);
        this.mSelectorEnvironment.setAdapter(a);
        this.mSelectorEnvironment.setOnItemSelectedListener(this);
        if (this.ai == null) {
            this.ai = (Environment) ManagerTypeEnvironment.getEnvironmentsOfType(environmentType).get(0);
        }
        this.mSelectorEnvironment.setSelection(a.getPosition(this.ai));
        this.ai = null;
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        getDialog().setCanceledOnTouchOutside(false);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        View inflate = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.environment_selector_fragment, null);
        ButterKnife.inject((Object) this, inflate);
        this.ah = PreferenceManager.getDefaultSharedPreferences(getActivity());
        A();
        B();
        Builder builder = new Builder(getActivity());
        builder.setView(inflate);
        builder.setTitle(getString(R.string.TITLE_DIALOG_SELECT_ENVIRONMENT));
        builder.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return i == 4 && keyEvent.getAction() == 1 && !keyEvent.isCanceled();
            }
        });
        builder.setPositiveButton(getString(R.string.BTN_DIALOG_ACCEPT), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SelectorEnvironmentFragment.this.a(dialogInterface);
            }
        });
        return builder.create();
    }

    private boolean C() {
        return !((Environment) this.mSelectorEnvironment.getSelectedItem()).equals(Environment.CUSTOM) || !(this.mInputUrl == null || this.mInputUrl.getText() == null || this.mInputUrl.getText().length() == 0);
    }

    private void a(EnvironmentType environmentType, Environment environment, EnvironmentToken environmentToken) {
        if (this.mSelectorEnvironment != null) {
            if (environment.equals(Environment.CUSTOM)) {
                String obj = this.mInputUrl.getText().toString();
                ManagerTypeEnvironment.setCurrentEnvironment(environment, obj, environmentToken);
                a("PREF_CUSTOM_URL", obj);
            } else {
                ManagerTypeEnvironment.setCurrentEnvironment(environment, getContext(), environmentToken);
            }
            a("PREF_LAST_ENV", environment.toString());
            a("PREF_LAST_ENV_TYPE", environmentType.toString());
            a("cPREF_LAST_ENV_TOKEN", environmentToken.toString());
        }
    }

    /* access modifiers changed from: private */
    public void a(DialogInterface dialogInterface) {
        if (C()) {
            a((EnvironmentType) this.mEnvironmentType.getSelectedItem(), (Environment) this.mSelectorEnvironment.getSelectedItem(), (EnvironmentToken) this.mEnvironmentToken.getSelectedItem());
            dialogInterface.cancel();
            this.listener.onCloseDialog();
            return;
        }
        Toast.makeText(getActivity(), getString(R.string.MSG_ERROR_FIELD_EMPTY), 0).show();
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.mInputUrl != null) {
            Environment item = a((EnvironmentType) this.mEnvironmentType.getSelectedItem()).getItem(i);
            this.mInputUrl.setText(ManagerTypeEnvironment.getUrlofEnvironment(item, getContext()));
            if (item.equals(Environment.CUSTOM)) {
                this.mInputUrl.setEnabled(true);
                this.mInputUrl.setText(b("PREF_CUSTOM_URL"));
            } else {
                this.mInputUrl.setEnabled(false);
            }
            if (item.equals(Environment.MOCKS)) {
                this.rowToken.setVisibility(8);
            } else {
                this.rowToken.setVisibility(0);
            }
        }
    }

    private void a(String str, String str2) {
        Editor edit = this.ah.edit();
        edit.putString(str, str2);
        edit.commit();
    }

    private String b(String str) {
        return this.ah.getString(str, "");
    }
}
