package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.activities.RecargaSubeActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionFragment.OnFragmentInteractionListener;
import butterknife.ButterKnife;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class RecargaSubeErrorFragment extends BaseFragment {
    public static final String MESSAGE_TAG = "message";
    public static final String TITLE_TAG = "title";
    TextView a;
    TextView b;
    /* access modifiers changed from: private */
    public View c;
    private Button d;
    private TextView e;
    /* access modifiers changed from: private */
    public ImageView f;
    /* access modifiers changed from: private */
    public View g;
    /* access modifiers changed from: private */
    public RelativeLayout h;
    public OnFragmentInteractionListener mListener;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        this.c = layoutInflater.inflate(R.layout.layout_error_sube, viewGroup, false);
        B();
        y();
        A();
        z();
        return this.c;
    }

    private void y() {
        if (VERSION.SDK_INT >= 21) {
            this.g.setVisibility(0);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(RecargaSubeErrorFragment.this.g, RecargaSubeErrorFragment.this.h.getWidth() / 2, ((int) RecargaSubeErrorFragment.this.f.getY()) + (RecargaSubeErrorFragment.this.f.getHeight() / 2) + RecargaSubeErrorFragment.this.h.getHeight(), (float) Math.hypot((double) RecargaSubeErrorFragment.this.c.getWidth(), (double) RecargaSubeErrorFragment.this.c.getHeight()), BitmapDescriptorFactory.HUE_RED);
                    createCircularReveal.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            RecargaSubeErrorFragment.this.g.setVisibility(8);
                            RecargaSubeErrorFragment.this.configureActionBar();
                        }
                    });
                    createCircularReveal.setDuration(750);
                    createCircularReveal.start();
                }
            }, 50);
            return;
        }
        this.g.setVisibility(8);
        configureActionBar();
    }

    private void z() {
        if (getArguments() != null) {
            String string = getArguments().getString("title");
            String string2 = getArguments().getString("message");
            if (!TextUtils.isEmpty(string)) {
                this.a.setText(Html.fromHtml(string));
            }
            if (!TextUtils.isEmpty(string2)) {
                this.b.setText(Html.fromHtml(string2));
            }
        }
    }

    public void configureActionBar() {
        enableBackButton();
    }

    public void enableBackButton() {
        View findViewById = this.h.findViewById(R.id.ok);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ((RecargaSubeActivity) RecargaSubeErrorFragment.this.getActivity()).goBackToHome();
                    RecargaSubeErrorFragment.this.getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
                }
            });
        }
    }

    private void A() {
        this.d.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((RecargaSubeActivity) RecargaSubeErrorFragment.this.getActivity()).backToPrincipalPage();
            }
        });
        this.e.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((RecargaSubeActivity) RecargaSubeErrorFragment.this.getActivity()).goBackToHome();
                RecargaSubeErrorFragment.this.getActivity().overridePendingTransition(R.anim.slide_in_up_recarga_sube, R.anim.slide_out_down_recarga_sube);
            }
        });
    }

    private void B() {
        this.a = (TextView) this.c.findViewById(R.id.algo_salio_mal_text);
        this.b = (TextView) this.c.findViewById(R.id.intenta_nuevamente_text);
        this.e = (TextView) this.c.findViewById(R.id.btn_volver_home);
        this.d = (Button) this.c.findViewById(R.id.btn_intenta_otra_vez);
        this.f = (ImageView) this.c.findViewById(R.id.error_sube_imagen);
        this.g = this.c.findViewById(R.id.close_animation_view);
        this.h = (RelativeLayout) this.c.findViewById(R.id.recarga_sube_error_actionbar);
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
