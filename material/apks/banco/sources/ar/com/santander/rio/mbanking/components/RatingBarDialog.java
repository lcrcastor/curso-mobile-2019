package ar.com.santander.rio.mbanking.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import ar.com.santander.rio.mbanking.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Timer;
import java.util.TimerTask;

public class RatingBarDialog extends DialogFragment {
    /* access modifiers changed from: private */
    public static RatingBarDialog aj = null;
    /* access modifiers changed from: private */
    public static boolean ak = true;
    /* access modifiers changed from: private */
    public RatingDialogListener ad;
    /* access modifiers changed from: private */
    public Button ae;
    private Button af;
    private RatingBar ag;
    /* access modifiers changed from: private */
    public ConstraintLayout ah;
    /* access modifiers changed from: private */
    public ConstraintLayout ai;

    public interface RatingDialogListener {
        void onRatingChanged(RatingBar ratingBar, float f, boolean z);
    }

    public static RatingBarDialog newInstance(RatingDialogListener ratingDialogListener) {
        RatingBarDialog ratingBarDialog = new RatingBarDialog();
        ratingBarDialog.ad = ratingDialogListener;
        return ratingBarDialog;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.vista_watson_mensaje_calificacion, viewGroup);
        setInitialize(inflate);
        setOnClickListeners();
        return inflate;
    }

    public void setInitialize(View view) {
        this.ae = (Button) view.findViewById(R.id.buttonCalificar);
        this.af = (Button) view.findViewById(R.id.buttonNoGracias);
        this.ag = (RatingBar) view.findViewById(R.id.ratingBar);
        this.ah = (ConstraintLayout) view.findViewById(R.id.constrainCalificar);
        this.ai = (ConstraintLayout) view.findViewById(R.id.constrainGraciasPorCalificar);
        this.ae.setEnabled(false);
    }

    public void setOnClickListeners() {
        this.ae.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RatingBarDialog.this.ah.setVisibility(8);
                RatingBarDialog.this.ai.setVisibility(0);
                RatingBarDialog.this.z();
            }
        });
        this.af.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RatingBarDialog.ak = true;
                RatingBarDialog.aj.dismiss();
            }
        });
        this.ag.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
                if (f > BitmapDescriptorFactory.HUE_RED) {
                    RatingBarDialog.ak = false;
                    RatingBarDialog.this.ae.setEnabled(true);
                } else {
                    RatingBarDialog.ak = false;
                    ratingBar.setRating(1.0f);
                    RatingBarDialog.this.ae.setEnabled(true);
                }
                if (RatingBarDialog.this.ad != null) {
                    RatingBarDialog.this.ad.onRatingChanged(ratingBar, f, z);
                }
            }
        });
    }

    public static void show(FragmentManager fragmentManager, RatingDialogListener ratingDialogListener, String str) {
        RatingBarDialog newInstance = newInstance(ratingDialogListener);
        newInstance.show(fragmentManager, str);
        aj = newInstance;
    }

    /* access modifiers changed from: private */
    public void z() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                RatingBarDialog.aj.dismiss();
                timer.cancel();
            }
        }, 4000);
    }

    public static boolean getFlagShowPopUpCalificacion() {
        return ak;
    }

    public static void setFlagShowPopUpCalificacion(boolean z) {
        ak = z;
    }
}
