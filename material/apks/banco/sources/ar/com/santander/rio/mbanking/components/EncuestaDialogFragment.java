package ar.com.santander.rio.mbanking.components;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.GenericRadioAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.GenericRadioAdapter.Event;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RespuestaBean;
import java.util.ArrayList;
import java.util.List;

public class EncuestaDialogFragment extends DialogFragment {
    /* access modifiers changed from: private */
    public EncuestaDialogListener ad;
    private GenericRadioAdapter ae;
    private RecyclerView af;
    private TextView ag;
    private TextView ah;
    private TextView ai;
    private EditText aj;
    private Button ak;
    private Button al;
    private Button am;
    private LinearLayout an;
    private List<PreguntaBean> ao;
    private List<RespuestaBean> ap = new ArrayList();

    public interface EncuestaDialogListener {
        void onCallService(List<RespuestaBean> list);

        void onCompleteEncuesta();

        void onRejectEncuesta();
    }

    public static EncuestaDialogFragment newInstance(List<PreguntaBean> list) {
        EncuestaDialogFragment encuestaDialogFragment = new EncuestaDialogFragment();
        encuestaDialogFragment.ao = list;
        return encuestaDialogFragment;
    }

    public static EncuestaDialogFragment newInstance(List<PreguntaBean> list, EncuestaDialogListener encuestaDialogListener) {
        EncuestaDialogFragment encuestaDialogFragment = new EncuestaDialogFragment();
        encuestaDialogFragment.ao = list;
        encuestaDialogFragment.ad = encuestaDialogListener;
        return encuestaDialogFragment;
    }

    public void setListener(EncuestaDialogListener encuestaDialogListener) {
        this.ad = encuestaDialogListener;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.encuesta_dialog_fragment, viewGroup);
        getDialog().requestWindowFeature(1);
        this.ag = (TextView) inflate.findViewById(R.id.tvPreguntaTitle);
        this.ah = (TextView) inflate.findViewById(R.id.tvPreguntaIntro);
        this.ai = (TextView) inflate.findViewById(R.id.tVDespedida);
        this.af = (RecyclerView) inflate.findViewById(R.id.rvRadios);
        this.aj = (EditText) inflate.findViewById(R.id.etComentario);
        this.aj.setImeOptions(6);
        this.aj.setRawInputType(1);
        this.ak = (Button) inflate.findViewById(R.id.btnNoGracias);
        this.al = (Button) inflate.findViewById(R.id.btnCalificar);
        this.am = (Button) inflate.findViewById(R.id.btnSalir);
        this.an = (LinearLayout) inflate.findViewById(R.id.llBotonera);
        this.ai.setVisibility(8);
        this.am.setVisibility(8);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 11; i++) {
            arrayList.add(String.valueOf(i));
        }
        this.af.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.ae = new GenericRadioAdapter(arrayList, containerWidth());
        this.ae.setOnClickOnRadioItem(new Event() {
            public void onClickItem() {
                EncuestaDialogFragment.this.y();
            }
        });
        this.af.setAdapter(this.ae);
        setCancelable(false);
        z();
        return inflate;
    }

    /* access modifiers changed from: private */
    public void y() {
        this.al.setEnabled(this.ae.isRbSelected() && !this.aj.getText().toString().trim().isEmpty());
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        this.ah.setText(new SpannableString(Html.fromHtml(((PreguntaBean) this.ao.get(0)).consulta)), BufferType.SPANNABLE);
        this.aj.setHint(new SpannableString(Html.fromHtml(((PreguntaBean) this.ao.get(1)).consulta)));
        this.al.setEnabled(false);
        containerWidth();
    }

    private void z() {
        this.ak.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EncuestaDialogFragment.this.dismiss();
                EncuestaDialogFragment.this.ad.onRejectEncuesta();
            }
        });
        this.al.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EncuestaDialogFragment.this.A();
                EncuestaDialogFragment.this.F();
                EncuestaDialogFragment.this.G();
                EncuestaDialogFragment.this.B();
            }
        });
        this.aj.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                EncuestaDialogFragment.this.y();
            }
        });
    }

    /* access modifiers changed from: private */
    public void A() {
        ((SantanderRioMainActivity) getActivity()).hideKeyboard();
    }

    /* access modifiers changed from: private */
    public void B() {
        if (hasUserRateAppWithGoodMark()) {
            C();
        } else {
            D();
        }
    }

    public int containerWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private void C() {
        A();
        this.an.setOrientation(1);
        this.ag.setVisibility(8);
        this.ah.setVisibility(8);
        this.af.setVisibility(8);
        this.aj.setVisibility(8);
        this.ak.setVisibility(0);
        this.al.setVisibility(0);
        this.am.setVisibility(8);
        this.ag.setVisibility(0);
        this.ag.setText(getString(R.string.VALORACION_APP_MUCHAS_GRACIAS));
        this.ai.setVisibility(0);
        this.ai.setText(getString(R.string.VALORACION_APP_PLAY_STORE));
        this.ak.setVisibility(8);
        this.al.setVisibility(8);
        this.ak.setVisibility(0);
        this.ak.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EncuestaDialogFragment.this.E();
            }
        });
        this.am.setVisibility(0);
        this.am.setText(getString(R.string.VALORACION_APP_SI));
        this.am.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EncuestaDialogFragment.this.E();
                EncuestaDialogFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=ar.com.santander.rio.mbanking")));
            }
        });
    }

    private void D() {
        A();
        this.ag.setVisibility(8);
        this.ah.setVisibility(8);
        this.af.setVisibility(8);
        this.aj.setVisibility(8);
        this.ak.setVisibility(8);
        this.al.setVisibility(8);
        this.am.setVisibility(0);
        this.ai.setVisibility(0);
        this.ai.setText(getString(R.string.VALORACION_APP_MUCHAS_GRACIAS));
        this.am.setText(getString(R.string.VALORACION_APP_SALIR));
        this.am.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EncuestaDialogFragment.this.E();
            }
        });
    }

    /* access modifiers changed from: private */
    public void E() {
        dismiss();
        this.ad.onCompleteEncuesta();
    }

    /* access modifiers changed from: private */
    public void F() {
        RespuestaBean respuestaBean = new RespuestaBean();
        respuestaBean.ordenPregunta = ((PreguntaBean) this.ao.get(0)).ordenPregunta;
        respuestaBean.comentario = this.ae.getSelectedOption();
        this.ap.add(respuestaBean);
        RespuestaBean respuestaBean2 = new RespuestaBean();
        respuestaBean2.ordenPregunta = ((PreguntaBean) this.ao.get(1)).ordenPregunta;
        respuestaBean2.comentario = this.aj.getText().toString();
        this.ap.add(respuestaBean2);
    }

    public boolean hasUserRateAppWithGoodMark() {
        return Integer.parseInt(((RespuestaBean) this.ap.get(0)).comentario) >= 9;
    }

    /* access modifiers changed from: private */
    public void G() {
        if (!this.ap.isEmpty() && this.ap != null) {
            this.ad.onCallService(this.ap);
        }
    }

    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(-1, -2);
    }
}
