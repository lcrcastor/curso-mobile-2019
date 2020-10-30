package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class RecargaSubeNotOKFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public View a;
    private ActionBar b;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public View e;
    /* access modifiers changed from: private */
    public ImageView f;

    public void onBackPressed() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.a = layoutInflater.inflate(R.layout.layout_not_done_payment, viewGroup, false);
        this.b = ((RecargaSubeActivity) getActivity()).getSupportActionBar();
        ((RecargaSubeActivity) getActivity()).setActionBarType(ActionBarType.WHITE);
        this.c = getArguments().getString("title");
        this.d = getArguments().getString("message");
        y();
        z();
        return this.a;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.e.getVisibility() == 4) {
            A();
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    private void y() {
        this.e = this.a.findViewById(R.id.close_animation_view);
        this.f = (ImageView) this.a.findViewById(R.id.checkmark_imagen);
    }

    private void z() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", RecargaSubeNotOKFragment.this.c);
                    bundle.putString("message", RecargaSubeNotOKFragment.this.d);
                    ((RecargaSubeActivity) RecargaSubeNotOKFragment.this.getActivity()).changeFragmentToShow(new RecargaSubeErrorFragment(), bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    private void A() {
        this.e.setVisibility(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int x = ((int) RecargaSubeNotOKFragment.this.f.getX()) + (RecargaSubeNotOKFragment.this.f.getWidth() / 2);
                int y = ((int) RecargaSubeNotOKFragment.this.f.getY()) + (RecargaSubeNotOKFragment.this.f.getHeight() / 2);
                float hypot = (float) Math.hypot((double) RecargaSubeNotOKFragment.this.a.getWidth(), (double) RecargaSubeNotOKFragment.this.a.getHeight());
                if (VERSION.SDK_INT >= 21) {
                    Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(RecargaSubeNotOKFragment.this.e, x, y, hypot, BitmapDescriptorFactory.HUE_RED);
                    createCircularReveal.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            RecargaSubeNotOKFragment.this.e.setVisibility(8);
                        }
                    });
                    createCircularReveal.setDuration(375);
                    if (!RecargaSubeNotOKFragment.this.isDetached()) {
                        createCircularReveal.start();
                        return;
                    }
                    return;
                }
                RecargaSubeNotOKFragment.this.e.setVisibility(8);
            }
        }, 50);
    }
}
