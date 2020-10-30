package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener.OneClicked;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.BlanqueoPINEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VblanqueoPIN;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class ConfirmarBlanqueoPinFragment extends BaseFragment {
    @Inject
    IDataManager a;
    private Button b;
    private TextView c;
    private TextView d;
    /* access modifiers changed from: private */
    public String e;
    private String f;
    /* access modifiers changed from: private */
    public String g;
    private TextView h;

    public static ConfirmarBlanqueoPinFragment newInstance(String str, String str2, String str3) {
        ConfirmarBlanqueoPinFragment confirmarBlanqueoPinFragment = new ConfirmarBlanqueoPinFragment();
        Bundle bundle = new Bundle();
        bundle.putString("NRO_TARJETA", str);
        bundle.putString("OPCION_BLANQUEO", str2);
        bundle.putString("BLANQUEO_PIN_COD", str3);
        confirmarBlanqueoPinFragment.setArguments(bundle);
        return confirmarBlanqueoPinFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.e = getArguments().getString("NRO_TARJETA");
            this.f = getArguments().getString("OPCION_BLANQUEO");
            this.g = getArguments().getString("BLANQUEO_PIN_COD");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_confirmar_blanqueo_pin, viewGroup, false);
        b(inflate);
        return inflate;
    }

    private void b(View view) {
        this.b = (Button) view.findViewById(R.id.btnConfirmarBlanqueoPin);
        this.c = (TextView) view.findViewById(R.id.tvTarjetaSantanderValue);
        this.d = (TextView) view.findViewById(R.id.tvPinAlfabetica);
        this.h = (TextView) view.findViewById(R.id.subtitleBlanqueoDeClave);
        this.c.setText(this.e);
        this.d.setText(this.f);
        A();
        z();
        y();
    }

    private void y() {
        try {
            this.h.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterGeneral(this.h.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void z() {
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ConfirmarBlanqueoPinFragment.this.showConfirmarBlanqueoPin();
            }
        });
    }

    public void showConfirmarBlanqueoPin() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.F27_13_TITLE_DIALOG_CONFIRMAR), getContext().getString(R.string.ID_4933_CONFIRMAR_BLANQUEO_CLAVE), null, null, getContext().getString(R.string.ID_4929_ACEPTAR), getContext().getString(R.string.ID_4928_CANCELAR), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ConfirmarBlanqueoPinFragment.this.showProgress(VblanqueoPIN.nameService);
                ConfirmarBlanqueoPinFragment.this.a.blanqueoPIN(new BlanqueoPinBodyRequestBean(ConfirmarBlanqueoPinFragment.this.e, "", ConfirmarBlanqueoPinFragment.this.g), ConfirmarBlanqueoPinFragment.this.getActivity());
            }
        });
        newInstance.show(getFragmentManager(), "isbanDialogConfirm");
    }

    @Subscribe
    public void blanqueoPin(BlanqueoPINEvent blanqueoPINEvent) {
        dismissProgress();
        final BlanqueoPINEvent blanqueoPINEvent2 = blanqueoPINEvent;
        AnonymousClass3 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.BLANQUEO_DE_CLAVE_BANELCO, (BaseActivity) getActivity(), getContext().getString(R.string.ID_4923_CONFIRMAR_BLANQUEO_DE_CLAVE_BANELCO)) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ConfirmarBlanqueoPinFragment.this.getFragmentManager().beginTransaction().replace(R.id.content_frame, ComprobanteBlanqueoPinFragment.newInstance(blanqueoPINEvent2.getBlanqueoPinResponseBean().getBlanqueoPinBodyResponseBean()), "tag_fragment_load").commit();
            }
        };
        r0.handleWSResponse(blanqueoPINEvent);
    }

    private void A() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        enableBackButton();
    }

    public void enableBackButton() {
        View findViewById = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.findViewById(R.id.toggle).setOnClickListener(new OneClickListener(new OneClicked() {
                public void onClicked(View view) {
                    ConfirmarBlanqueoPinFragment.this.onBackPressed();
                }
            }));
        }
    }

    public void onBackPressed() {
        B();
    }

    private void B() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}
