package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.PhoneSelectorView;
import ar.com.santander.rio.mbanking.view.tables.RowTwoColumnViewStyled;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class SuscripcionSorpresaFragment$$ViewInjector {
    public static void inject(Finder finder, final SuscripcionSorpresaFragment suscripcionSorpresaFragment, Object obj) {
        suscripcionSorpresaFragment.vTitle = (TextView) finder.findRequiredView(obj, R.id.vTitle, "field 'vTitle'");
        View findRequiredView = finder.findRequiredView(obj, R.id.vLink, "field 'vLink' and method 'onLinkRequisitos'");
        suscripcionSorpresaFragment.vLink = (TextView) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                suscripcionSorpresaFragment.y();
            }
        });
        suscripcionSorpresaFragment.label1 = (TextView) finder.findRequiredView(obj, R.id.f173suscripcionsorpresalabel1, "field 'label1'");
        suscripcionSorpresaFragment.mailView = (RowTwoColumnViewStyled) finder.findRequiredView(obj, R.id.f174suscripcionsorpresamail, "field 'mailView'");
        suscripcionSorpresaFragment.celularPrincipalView = (PhoneSelectorView) finder.findRequiredView(obj, R.id.f169suscripcionsorpresacelularprincipal, "field 'celularPrincipalView'");
        suscripcionSorpresaFragment.celularSeecundarioView = (PhoneSelectorView) finder.findRequiredView(obj, R.id.f170suscripcionsorpresacelularsecundario, "field 'celularSeecundarioView'");
        suscripcionSorpresaFragment.detalleNoSuscrito = (TextView) finder.findRequiredView(obj, R.id.f172suscripcionsorpresadetallenosuscrito, "field 'detalleNoSuscrito'");
        View findRequiredView2 = finder.findRequiredView(obj, R.id.f177suscripcionsorpresatycsuscrito, "field 'terminosSuscrito' and method 'onVerTerminosSuscrito'");
        suscripcionSorpresaFragment.terminosSuscrito = (TextView) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                suscripcionSorpresaFragment.B();
            }
        });
        View findRequiredView3 = finder.findRequiredView(obj, R.id.f175suscripcionsorpresatycnosuscrito, "field 'terminosNoSuscrito' and method 'onVerTerminosNoSuscrito'");
        suscripcionSorpresaFragment.terminosNoSuscrito = (TextView) findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                suscripcionSorpresaFragment.A();
            }
        });
        suscripcionSorpresaFragment.layoutTerminosNoSuscrito = finder.findRequiredView(obj, R.id.f176suscripcionsorpresatycnosuscritolayout, "field 'layoutTerminosNoSuscrito'");
        View findRequiredView4 = finder.findRequiredView(obj, R.id.f171suscripcionsorpresacheckTyC, "field 'checkAceptoTyC' and method 'onChecked'");
        suscripcionSorpresaFragment.checkAceptoTyC = (ToggleButton) findRequiredView4;
        ((CompoundButton) findRequiredView4).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                suscripcionSorpresaFragment.a(z);
            }
        });
        View findRequiredView5 = finder.findRequiredView(obj, R.id.f168suscripcionsorpresaaction, "field 'actionButton' and method 'onSubscripcionSorpresaAction'");
        suscripcionSorpresaFragment.actionButton = (Button) findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                suscripcionSorpresaFragment.z();
            }
        });
        suscripcionSorpresaFragment.viewFragment = finder.findRequiredView(obj, R.id.f178suscripcionsorpresaview, "field 'viewFragment'");
    }

    public static void reset(SuscripcionSorpresaFragment suscripcionSorpresaFragment) {
        suscripcionSorpresaFragment.vTitle = null;
        suscripcionSorpresaFragment.vLink = null;
        suscripcionSorpresaFragment.label1 = null;
        suscripcionSorpresaFragment.mailView = null;
        suscripcionSorpresaFragment.celularPrincipalView = null;
        suscripcionSorpresaFragment.celularSeecundarioView = null;
        suscripcionSorpresaFragment.detalleNoSuscrito = null;
        suscripcionSorpresaFragment.terminosSuscrito = null;
        suscripcionSorpresaFragment.terminosNoSuscrito = null;
        suscripcionSorpresaFragment.layoutTerminosNoSuscrito = null;
        suscripcionSorpresaFragment.checkAceptoTyC = null;
        suscripcionSorpresaFragment.actionButton = null;
        suscripcionSorpresaFragment.viewFragment = null;
    }
}
