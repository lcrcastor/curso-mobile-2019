package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CExtEnv;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectEnvioDineroOperacionesRealizadas;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import java.util.ArrayList;

public class AdapterEnvioDineroOperacionesRealizadas extends ArrayAdapter<ObjectEnvioDineroOperacionesRealizadas> {
    Context a;
    SessionManager b;
    int c;
    ArrayList<ObjectEnvioDineroOperacionesRealizadas> d = null;

    public AdapterEnvioDineroOperacionesRealizadas(Context context, SessionManager sessionManager, int i, ArrayList<ObjectEnvioDineroOperacionesRealizadas> arrayList) {
        super(context, i, arrayList);
        this.c = i;
        this.a = context;
        this.b = sessionManager;
        this.d = arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00b0, code lost:
        if (r1.equals(ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.Estado.Cobrado) != false) goto L_0x00c8;
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
            r7 = 2131362347(0x7f0a022b, float:1.8344472E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.TextView r7 = (android.widget.TextView) r7
            java.lang.String r1 = r5.getFechaVencimiento()
            java.lang.String r2 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_APP_2
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.Constants.FORMAT_DATE_CREDIT_CARDS_MAIN
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilDate.getDateFormat(r1, r2, r3)
            r7.setText(r1)
            r7 = 2131362346(0x7f0a022a, float:1.834447E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.TextView r7 = (android.widget.TextView) r7
            java.lang.String r1 = r5.getNombre()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0055
            java.lang.String r1 = r5.getNombre()
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilString.capitalize(r1)
            goto L_0x0061
        L_0x0055:
            java.lang.String r1 = r5.getTipoDoc()
            java.lang.String r2 = r5.getDocumento()
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.Utils.Documento.mask(r1, r2)
        L_0x0061:
            r7.setText(r1)
            r7 = 2131362348(0x7f0a022c, float:1.8344474E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.TextView r7 = (android.widget.TextView) r7
            java.lang.String r1 = r5.getImporte()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.String r1 = ar.com.santander.rio.mbanking.app.commons.CExtEnv.getFormattedValue(r1)
            r7.setText(r1)
            r7 = 2131362344(0x7f0a0228, float:1.8344466E38)
            android.view.View r7 = r6.findViewById(r7)
            android.widget.ImageView r7 = (android.widget.ImageView) r7
            java.lang.String r1 = r5.getEstado()
            java.lang.String r1 = r1.toUpperCase()
            r2 = -1
            int r3 = r1.hashCode()
            switch(r3) {
                case 65861: goto L_0x00bd;
                case 66480: goto L_0x00b3;
                case 66902: goto L_0x00aa;
                case 79097: goto L_0x00a0;
                case 84863: goto L_0x0096;
                default: goto L_0x0095;
            }
        L_0x0095:
            goto L_0x00c7
        L_0x0096:
            java.lang.String r0 = "VEN"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 1
            goto L_0x00c8
        L_0x00a0:
            java.lang.String r0 = "PEN"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 4
            goto L_0x00c8
        L_0x00aa:
            java.lang.String r3 = "COB"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c7
            goto L_0x00c8
        L_0x00b3:
            java.lang.String r0 = "CAN"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 2
            goto L_0x00c8
        L_0x00bd:
            java.lang.String r0 = "BLO"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 3
            goto L_0x00c8
        L_0x00c7:
            r0 = -1
        L_0x00c8:
            switch(r0) {
                case 0: goto L_0x00ee;
                case 1: goto L_0x00dd;
                case 2: goto L_0x00dd;
                case 3: goto L_0x00dd;
                case 4: goto L_0x00cc;
                default: goto L_0x00cb;
            }
        L_0x00cb:
            goto L_0x00fe
        L_0x00cc:
            android.content.Context r0 = r4.a
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131231064(0x7f080158, float:1.8078198E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
            goto L_0x00fe
        L_0x00dd:
            android.content.Context r0 = r4.a
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131231065(0x7f080159, float:1.80782E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
            goto L_0x00fe
        L_0x00ee:
            android.content.Context r0 = r4.a
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131231063(0x7f080157, float:1.8078196E38)
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r7.setImageDrawable(r0)
        L_0x00fe:
            r4.applyContentDescription(r6, r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.adapters.AdapterEnvioDineroOperacionesRealizadas.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public void applyLetterSpacing(View view) {
        UtilStyleCommons.setLetterSpacing(view.findViewById(R.id.F12_07_lbl_fechaalta), -1.5f);
        UtilStyleCommons.setLetterSpacing(view.findViewById(R.id.F12_07_lbl_destinatario), -1.5f);
        UtilStyleCommons.setLetterSpacing(view.findViewById(R.id.F12_07_lbl_importe), -1.5f);
    }

    public void applyContentDescription(View view, ObjectEnvioDineroOperacionesRealizadas objectEnvioDineroOperacionesRealizadas) {
        CAccessibility instance = CAccessibility.getInstance(this.a);
        TextView textView = (TextView) view.findViewById(R.id.F12_07_lbl_fechaalta);
        try {
            textView.setContentDescription(String.format(this.a.getString(R.string.CONTENT_FECHA_VENCIMIENTO), new Object[]{instance.applyFilterDate(UtilDate.getDateFormat(objectEnvioDineroOperacionesRealizadas.getFechaVencimiento(), Constants.FORMAT_DATE_TIME_COMPROBANTE, Constants.FORMAT_DATE_APP))}));
        } catch (Exception unused) {
        }
        TextView textView2 = (TextView) view.findViewById(R.id.F12_07_lbl_destinatario);
        textView2.setContentDescription(String.format(this.a.getString(R.string.CONTENT_DESTINATARIO), new Object[]{textView2.getText().toString()}));
        TextView textView3 = (TextView) view.findViewById(R.id.F12_07_lbl_importe);
        try {
            textView3.setContentDescription(String.format(this.a.getString(R.string.CONTENT_IMPORTE), new Object[]{instance.applyFilterAmount(textView3.getText().toString())}));
        } catch (Exception unused2) {
        }
        ((ImageView) view.findViewById(R.id.F12_07_img_estado)).setContentDescription(String.format(this.a.getString(R.string.CONTENT_ESTADO), new Object[]{CExtEnv.getDescripcionByEstado(this.b, objectEnvioDineroOperacionesRealizadas.getEstado())}));
    }
}
