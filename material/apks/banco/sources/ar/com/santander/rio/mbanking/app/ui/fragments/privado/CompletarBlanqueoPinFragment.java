package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.BlanqueoPinConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class CompletarBlanqueoPinFragment extends BaseFragment {
    RelativeLayout a;
    private String ad = null;
    /* access modifiers changed from: private */
    public String ae = "";
    /* access modifiers changed from: private */
    public String af = "";
    private Activity ag = null;
    private Context ah;
    LinearLayout b;
    TextView c;
    CheckBox d;
    CheckBox e;
    Button f;
    private boolean g;
    private List<Tarjeta> h;
    /* access modifiers changed from: private */
    public IsbanDialogFragment i;

    public static CompletarBlanqueoPinFragment newInstance(List<Tarjeta> list, boolean z) {
        CompletarBlanqueoPinFragment completarBlanqueoPinFragment = new CompletarBlanqueoPinFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LISTA_TARJETAS", (ArrayList) list);
        bundle.putBoolean("TOKEN_AVAILABLE", z);
        completarBlanqueoPinFragment.setArguments(bundle);
        return completarBlanqueoPinFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.h = getArguments().getParcelableArrayList("LISTA_TARJETAS");
            this.g = getArguments().getBoolean("TOKEN_AVAILABLE", false);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_completar_blanqueo_pin, viewGroup, false);
        ((SantanderRioMainActivity) this.ag).restartActionBar();
        initialize(inflate);
        return inflate;
    }

    public void initialize(View view) {
        this.a = (RelativeLayout) view.findViewById(R.id.llCheckBoxClaveAlfabetica);
        this.b = (LinearLayout) view.findViewById(R.id.llSelectorTarjetaSantander);
        this.c = (TextView) view.findViewById(R.id.tvSelectorTarjetaSantanderValue);
        if (!this.af.isEmpty()) {
            this.c.setText(this.af);
        }
        this.d = (CheckBox) view.findViewById(R.id.CheckBoxPin);
        this.e = (CheckBox) view.findViewById(R.id.CheckBoxClaveAlfabetica);
        this.f = (Button) view.findViewById(R.id.buttonHomeBlanqueoDePines);
        this.f.setEnabled(false);
        z();
        y();
        selectTarjetaValidation();
    }

    private void y() {
        this.d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CompletarBlanqueoPinFragment.this.A();
            }
        });
        this.e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CompletarBlanqueoPinFragment.this.A();
            }
        });
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((SantanderRioMainActivity) CompletarBlanqueoPinFragment.this.getActivity()).changeFragment(ConfirmarBlanqueoPinFragment.newInstance(CompletarBlanqueoPinFragment.this.c.getText().toString(), CompletarBlanqueoPinFragment.this.opcionDeBlanqueo(), CompletarBlanqueoPinFragment.this.ae));
            }
        });
    }

    private void z() {
        this.a.setVisibility(!this.g ? 8 : 0);
    }

    public void selectTarjetaValidation() {
        if (this.h.size() == 1) {
            this.c.setText(((Tarjeta) this.h.get(0)).getNroTarjetaDebito());
            this.c.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            AccountSelectedValidation();
            return;
        }
        this.c.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        this.c.setCompoundDrawablePadding(Utils.dpToPx(5, getContext()));
        this.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CompletarBlanqueoPinFragment.this.popUpTajetas();
            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.ah = context;
        this.ag = getActivity();
    }

    public void popUpTajetas() {
        ArrayList arrayList = new ArrayList();
        for (Tarjeta nroTarjetaDebito : this.h) {
            arrayList.add(nroTarjetaDebito.getNroTarjetaDebito());
        }
        this.i = IsbanDialogFragment.newInstance("SeleccioneTarjeta", "Seleccionar", null, arrayList, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, null, null);
        this.i.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                CompletarBlanqueoPinFragment.this.c.setText(str);
                CompletarBlanqueoPinFragment.this.af = str;
                CompletarBlanqueoPinFragment.this.A();
            }

            public void onSimpleActionButton() {
                CompletarBlanqueoPinFragment.this.i.dismiss();
            }
        });
        this.i.show(getFragmentManager(), "SeleccioneTarjeta");
    }

    /* access modifiers changed from: private */
    public void A() {
        if ((isCheckBoxClaveAlfabeticaChecked() || isCheckBoxPinChecked()) && AccountSelectedValidation()) {
            this.f.setEnabled(true);
            this.f.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_rojo));
            return;
        }
        this.f.setEnabled(false);
        this.f.setBackground(getResources().getDrawable(R.drawable.boton_redondeado_gris_claro));
    }

    public boolean isCheckBoxClaveAlfabeticaChecked() {
        return this.e.isChecked();
    }

    public boolean isCheckBoxPinChecked() {
        return this.d.isChecked();
    }

    public String opcionDeBlanqueo() {
        if (isCheckBoxClaveAlfabeticaChecked() && isCheckBoxPinChecked()) {
            this.ae = BlanqueoPinConstants.OPCION_AMBAS;
            String string = getString(R.string.ID_SIN_ID_CLAVE_NUMERICA_ALFABETICA);
            this.ad = string;
            return string;
        } else if (isCheckBoxClaveAlfabeticaChecked()) {
            this.ae = BlanqueoPinConstants.OPCION_CLAVE_ALFA;
            String string2 = getString(R.string.ID_SIN_ID_CLAVE_ALFABETICA);
            this.ad = string2;
            return string2;
        } else if (!isCheckBoxPinChecked()) {
            return null;
        } else {
            this.ae = BlanqueoPinConstants.OPCION_BLANQUEO_PIN;
            String string3 = getString(R.string.ID_SIN_ID_CLAVE_NUMERICA);
            this.ad = string3;
            return string3;
        }
    }

    public boolean AccountSelectedValidation() {
        return !this.c.getText().equals("Seleccionar");
    }
}
