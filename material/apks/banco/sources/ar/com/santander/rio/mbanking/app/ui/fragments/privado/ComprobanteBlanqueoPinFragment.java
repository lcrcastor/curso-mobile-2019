package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.base.ShareReceiptListener;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BlanqueoPinBodyResponseBean;

public class ComprobanteBlanqueoPinFragment extends ITRSABaseFragment {
    /* access modifiers changed from: private */
    public ShareReceiptListener a = null;
    private Button b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private ImageView g;
    private BlanqueoPinBodyResponseBean h;

    public void setShareReceiptListener(ShareReceiptListener shareReceiptListener) {
        this.a = shareReceiptListener;
    }

    public static ComprobanteBlanqueoPinFragment newInstance(BlanqueoPinBodyResponseBean blanqueoPinBodyResponseBean) {
        ComprobanteBlanqueoPinFragment comprobanteBlanqueoPinFragment = new ComprobanteBlanqueoPinFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("BLANQUEO_PIN_EVENT", blanqueoPinBodyResponseBean);
        comprobanteBlanqueoPinFragment.setArguments(bundle);
        return comprobanteBlanqueoPinFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.h = (BlanqueoPinBodyResponseBean) getArguments().getParcelable("BLANQUEO_PIN_EVENT");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_comprobante_blanqueo_pin, viewGroup, false);
        createActionBarNotShared();
        setShareReceiptListener((SantanderRioMainActivity) getActivity());
        this.bus.register(this);
        initialize(inflate);
        validateRetorn(this.h);
        return inflate;
    }

    public void validateRetorn(BlanqueoPinBodyResponseBean blanqueoPinBodyResponseBean) {
        this.c.setText(getContext().getString(R.string.ID_4930_BLANQUEO_CLAVE_BANELCO));
        this.f.setText(Html.fromHtml(blanqueoPinBodyResponseBean.getMensaje()));
        this.d.setText("Info");
        this.e.setText("Recordá que deberás acercarte a un cajero de la red Banelco para generar tus nuevas claves");
    }

    public void initialize(View view) {
        this.b = (Button) view.findViewById(R.id.qoute_button);
        this.c = (TextView) view.findViewById(R.id.title);
        this.f = (TextView) view.findViewById(R.id.description);
        this.g = (ImageView) view.findViewById(R.id.image);
        this.d = (TextView) view.findViewById(R.id.titleLeyend);
        this.e = (TextView) view.findViewById(R.id.descriptionLeyend);
        this.b.setText(getContext().getString(R.string.WOMEN_FINAL_BACK_BUTTON_TEXT));
        this.b.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ComprobanteBlanqueoPinFragment.this.a.onClikVolver();
            }
        });
        setSizeCheckImage(this.g);
    }

    public void setSizeCheckImage(ImageView imageView) {
        int i;
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        if (point.x < 600) {
            i = point.x / 4;
        } else {
            i = point.x / 3;
        }
        LayoutParams layoutParams = new LayoutParams(i, i);
        layoutParams.gravity = 17;
        imageView.setLayoutParams(layoutParams);
    }
}
