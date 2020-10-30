package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectEnvioDineroOperacionesRealizadas;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import java.util.ArrayList;

public class AdapterExtraccionSinTarjetaOperacionesRealizadas extends ArrayAdapter<ObjectEnvioDineroOperacionesRealizadas> {
    Context a;
    SessionManager b;
    int c;
    ArrayList<ObjectEnvioDineroOperacionesRealizadas> d = null;

    public AdapterExtraccionSinTarjetaOperacionesRealizadas(Context context, SessionManager sessionManager, int i, ArrayList<ObjectEnvioDineroOperacionesRealizadas> arrayList) {
        super(context, i, arrayList);
        this.c = i;
        this.a = context;
        this.b = sessionManager;
        this.d = arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0095, code lost:
        if (r1.equals(ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.Estado.Cobrado) != false) goto L_0x00ad;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r5, android.view.View r6, android.view.ViewGroup r7) {
        /*
            r4 = this;
            r0 = 0
            if (r6 != 0) goto L_0x0011
            android.content.Context r6 = r4.a
            android.app.Activity r6 = (android.app.Activity) r6
            android.view.LayoutInflater r6 = r6.getLayoutInflater()
            int r1 = r4.c
            android.view.View r6 = r6.inflate(r1, r7, r0)
        L_0x0011:
            java.util.ArrayList<ar.com.santander.rio.mbanking.app.ui.Model.ObjectEnvioDineroOperacionesRealizadas> r7 = r4.d
            java.lang.Object r5 = r7.get(r5)
            ar.com.santander.rio.mbanking.app.ui.Model.ObjectEnvioDineroOperacionesRealizadas r5 = (ar.com.santander.rio.mbanking.app.ui.Model.ObjectEnvioDineroOperacionesRealizadas) r5
            r7 = 2131362583(0x7f0a0317, float:1.834495E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.TextView r7 = (android.widget.TextView) r7
            java.lang.String r1 = r5.getFechaAlta()
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_APP_2
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_CREDIT_CARDS_MAIN
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r1, r2, r3)
            r7.setText(r1)
            r7 = 2131362592(0x7f0a0320, float:1.8344969E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.TextView r7 = (android.widget.TextView) r7
            java.lang.String r1 = r5.getFechaVencimiento()
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_APP_2
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_CREDIT_CARDS_MAIN
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r1, r2, r3)
            r7.setText(r1)
            r7 = 2131362585(0x7f0a0319, float:1.8344955E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.TextView r7 = (android.widget.TextView) r7
            java.lang.String r1 = r5.getImporte()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getFormattedValue(r1)
            r7.setText(r1)
            r7 = 2131362582(0x7f0a0316, float:1.8344949E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.ImageView r7 = (android.widget.ImageView) r7
            java.lang.String r1 = r5.getEstado()
            java.lang.String r1 = r1.toUpperCase()
            r2 = -1
            int r3 = r1.hashCode()
            switch(r3) {
                case 65861: goto L_0x00a2;
                case 66480: goto L_0x0098;
                case 66902: goto L_0x008f;
                case 79097: goto L_0x0085;
                case 84863: goto L_0x007b;
                default: goto L_0x007a;
            }
        L_0x007a:
            goto L_0x00ac
        L_0x007b:
            java.lang.String r0 = "VEN"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00ac
            r0 = 1
            goto L_0x00ad
        L_0x0085:
            java.lang.String r0 = "PEN"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00ac
            r0 = 4
            goto L_0x00ad
        L_0x008f:
            java.lang.String r3 = "COB"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00ac
            goto L_0x00ad
        L_0x0098:
            java.lang.String r0 = "CAN"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00ac
            r0 = 2
            goto L_0x00ad
        L_0x00a2:
            java.lang.String r0 = "BLO"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00ac
            r0 = 3
            goto L_0x00ad
        L_0x00ac:
            r0 = -1
        L_0x00ad:
            switch(r0) {
                case 0: goto L_0x00d3;
                case 1: goto L_0x00c2;
                case 2: goto L_0x00c2;
                case 3: goto L_0x00c2;
                case 4: goto L_0x00b1;
                default: goto L_0x00b0;
            }
        L_0x00b0:
            goto L_0x00e3
        L_0x00b1:
            android.content.Context r0 = r4.a
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131231064(0x7f080158, float:1.8078198E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
            goto L_0x00e3
        L_0x00c2:
            android.content.Context r0 = r4.a
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131231065(0x7f080159, float:1.80782E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
            goto L_0x00e3
        L_0x00d3:
            android.content.Context r0 = r4.a
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131231063(0x7f080157, float:1.8078196E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
        L_0x00e3:
            r4.applyContentDescription(r6, r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.adapters.AdapterExtraccionSinTarjetaOperacionesRealizadas.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public void applyLetterSpacing(View view) {
        UtilStyleCommons.setLetterSpacing(view.findViewById(R.id.F19_07_lbl_alta), -1.5f);
        UtilStyleCommons.setLetterSpacing(view.findViewById(R.id.F19_07_lbl_vencimiento), -1.5f);
        UtilStyleCommons.setLetterSpacing(view.findViewById(R.id.F19_07_lbl_importe), -1.5f);
    }

    public void applyContentDescription(View view, ObjectEnvioDineroOperacionesRealizadas objectEnvioDineroOperacionesRealizadas) {
        CAccessibility instance = CAccessibility.getInstance(this.a);
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.a);
        ((TextView) view.findViewById(R.id.F19_07_lbl_alta)).setContentDescription(String.format(this.a.getString(R.string.CONTENT_FECHA_ALTA), new Object[]{cFiltersAccessibility.filterDateText(objectEnvioDineroOperacionesRealizadas.getFechaAlta())}));
        TextView textView = (TextView) view.findViewById(R.id.F19_07_lbl_vencimiento);
        try {
            textView.setContentDescription(String.format(this.a.getString(R.string.CONTENT_FECHA_VENCIMIENTO), new Object[]{instance.applyFilterDate(UtilDate.getDateFormat(objectEnvioDineroOperacionesRealizadas.getFechaVencimiento(), Constants.FORMAT_DATE_TIME_COMPROBANTE, Constants.FORMAT_DATE_APP))}));
        } catch (Exception unused) {
        }
        TextView textView2 = (TextView) view.findViewById(R.id.F19_07_lbl_importe);
        try {
            textView2.setContentDescription(String.format(this.a.getString(R.string.CONTENT_IMPORTE), new Object[]{instance.applyFilterAmount(textView2.getText().toString())}));
        } catch (Exception unused2) {
        }
        ((ImageView) view.findViewById(R.id.F19_07_img_estado)).setContentDescription(String.format(this.a.getString(R.string.CONTENT_ESTADO), new Object[]{CExtEnv.getDescripcionByEstado(this.b, objectEnvioDineroOperacionesRealizadas.getEstado())}));
    }
}
