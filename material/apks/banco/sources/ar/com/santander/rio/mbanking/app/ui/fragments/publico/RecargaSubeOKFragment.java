package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.activities.RecargaSubeActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionFragment;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaRecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RecargaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ValorRecargaBean;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;

public class RecargaSubeOKFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public View a;
    private ActionBar b;
    /* access modifiers changed from: private */
    public PagoServiciosBodyResponseBean c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public List<CuentaRecargaBean> e;
    /* access modifiers changed from: private */
    public List<ValorRecargaBean> f;
    /* access modifiers changed from: private */
    public List<RecargaBean> g;
    /* access modifiers changed from: private */
    public View h;
    /* access modifiers changed from: private */
    public ImageView i;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.a = layoutInflater.inflate(R.layout.layout_done_payment, viewGroup, false);
        ((RecargaSubeActivity) getActivity()).setActionBarType(ActionBarType.WHITE);
        this.b = ((RecargaSubeActivity) getActivity()).getSupportActionBar();
        this.b.getCustomView().setBackgroundColor(this.a.getResources().getColor(R.color.green_leaf));
        ((ImageView) this.b.getCustomView().findViewById(R.id.logo)).setColorFilter(ContextCompat.getColor(this.a.getContext(), R.color.green_leaf), Mode.MULTIPLY);
        this.c = (PagoServiciosBodyResponseBean) getArguments().getParcelable("response");
        this.d = getArguments().getString("tarjeta_nombre");
        this.g = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_RECARGAS);
        this.f = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_VALORES);
        this.e = (List) getArguments().getSerializable(RecargaSubeFragment.LISTA_CUENTAS);
        y();
        z();
        return this.a;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.h.getVisibility() == 4) {
            A();
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    private void y() {
        this.h = this.a.findViewById(R.id.close_animation_view);
        this.i = (ImageView) this.a.findViewById(R.id.checkmark_imagen);
    }

    private void z() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("response", RecargaSubeOKFragment.this.c);
                    bundle.putString("tarjeta_nombre", RecargaSubeOKFragment.this.d);
                    bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_CUENTAS, (ArrayList) RecargaSubeOKFragment.this.e);
                    bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_RECARGAS, (ArrayList) RecargaSubeOKFragment.this.g);
                    bundle.putParcelableArrayList(RecargaSubeFragment.LISTA_VALORES, (ArrayList) RecargaSubeOKFragment.this.f);
                    ((RecargaSubeActivity) RecargaSubeOKFragment.this.getActivity()).changeFragmentToShow(new RecargaSubeConfirmacionFragment(), bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    private void A() {
        this.h.setVisibility(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int x = ((int) RecargaSubeOKFragment.this.i.getX()) + (RecargaSubeOKFragment.this.i.getWidth() / 2);
                int y = ((int) RecargaSubeOKFragment.this.i.getY()) + (RecargaSubeOKFragment.this.i.getHeight() / 2);
                float hypot = (float) Math.hypot((double) RecargaSubeOKFragment.this.a.getWidth(), (double) RecargaSubeOKFragment.this.a.getHeight());
                if (VERSION.SDK_INT >= 21) {
                    Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(RecargaSubeOKFragment.this.h, x, y, hypot, BitmapDescriptorFactory.HUE_RED);
                    createCircularReveal.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            RecargaSubeOKFragment.this.h.setVisibility(8);
                        }
                    });
                    createCircularReveal.setDuration(375);
                    createCircularReveal.start();
                    return;
                }
                RecargaSubeOKFragment.this.h.setVisibility(8);
            }
        }, 50);
    }
}
