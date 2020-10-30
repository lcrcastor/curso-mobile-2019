package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.CCreditos;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.CreditosConstants;
import ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel.DatosCuota;
import ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel.ProximasCuotas;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

public class ProximasCuotasActivity extends BaseActivity {
    /* access modifiers changed from: private */
    public String A;
    /* access modifiers changed from: private */
    public String B;
    public CAccessibility cAccessibility;
    @InjectView(2131365452)
    ListView cuotasListView;
    /* access modifiers changed from: private */
    public ProximasCuotas p = new ProximasCuotas();
    /* access modifiers changed from: private */
    public Listaleyendas q = new Listaleyendas();
    private String r;
    /* access modifiers changed from: private */
    public String s;
    private String t;
    private String u;
    /* access modifiers changed from: private */
    public Context v;
    /* access modifiers changed from: private */
    public DatosCredito w;
    /* access modifiers changed from: private */
    public String x;
    /* access modifiers changed from: private */
    public String y;
    /* access modifiers changed from: private */
    public String z;

    public class CustomAdapter extends ArrayAdapter<DatosCuota> {
        Context a;
        int b;
        List<DatosCuota> c = null;

        public CustomAdapter(Context context, int i, List<DatosCuota> list) {
            super(context, i, list);
            this.b = i;
            this.a = context;
            this.c = list;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((Activity) this.a).getLayoutInflater().inflate(this.b, viewGroup, false);
            }
            DatosCuota datosCuota = (DatosCuota) this.c.get(i);
            TextView textView = (TextView) view.findViewById(R.id.item_fecha_vto);
            TextView textView2 = (TextView) view.findViewById(R.id.item_num_cuota);
            TextView textView3 = (TextView) view.findViewById(R.id.item_cuota_impo);
            textView.setText(datosCuota.getFechaVencimiento());
            textView2.setText(datosCuota.getNroCuotaCredito());
            StringBuilder sb = new StringBuilder();
            sb.append("$");
            sb.append(datosCuota.getImporteCuota());
            textView3.setText(sb.toString());
            ImageView imageView = (ImageView) view.findViewById(R.id.lmbimagen2);
            imageView.setVisibility(0);
            try {
                textView.setContentDescription(ProximasCuotasActivity.this.cAccessibility.applyFilterDate("Fila seleccionable. Fecha de vencimiento ".concat(datosCuota.getFechaVencimiento()).concat(".")));
                textView2.setContentDescription(ProximasCuotasActivity.this.cAccessibility.applyFilterGeneral("Número de cuota ".concat(datosCuota.getNroCuotaCredito().concat("."))));
                CAccessibility cAccessibility = ProximasCuotasActivity.this.cAccessibility;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("$");
                sb2.append(datosCuota.getImporteCuota());
                textView3.setContentDescription(cAccessibility.applyFilterAmount("Importe ".concat(sb2.toString())));
            } catch (Throwable unused) {
            }
            if (i > 0) {
                DatosCuota datosCuota2 = (DatosCuota) this.c.get(i);
                TextView textView4 = (TextView) view.findViewById(R.id.item_fecha_vto);
                TextView textView5 = (TextView) view.findViewById(R.id.item_num_cuota);
                TextView textView6 = (TextView) view.findViewById(R.id.item_cuota_impo);
                textView4.setText(datosCuota2.getFechaVencimiento());
                textView5.setText(datosCuota2.getNroCuotaCredito());
                StringBuilder sb3 = new StringBuilder();
                sb3.append("$");
                sb3.append(datosCuota2.getImporteCuota());
                textView6.setText(sb3.toString());
                imageView.setVisibility(4);
                try {
                    textView4.setContentDescription(ProximasCuotasActivity.this.cAccessibility.applyFilterDate("Fecha de vencimiento ".concat(datosCuota2.getFechaVencimiento()).concat(".")));
                    textView5.setContentDescription(ProximasCuotasActivity.this.cAccessibility.applyFilterGeneral("Número de cuota ".concat(datosCuota2.getNroCuotaCredito().concat("."))));
                    CAccessibility cAccessibility2 = ProximasCuotasActivity.this.cAccessibility;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("$");
                    sb4.append(datosCuota2.getImporteCuota());
                    textView6.setContentDescription(cAccessibility2.applyFilterAmount("Importe ".concat(sb4.toString())));
                } catch (Throwable unused2) {
                }
            }
            return view;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_proximas_cuotas);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.u = extras.getString("tipo_credito");
            this.q = (Listaleyendas) extras.getParcelable("lista_leyendas");
            this.p = (ProximasCuotas) extras.getParcelable("lista_cuotas");
            this.r = extras.getString("nombre_credito");
            this.t = extras.getString("nro_credito");
            this.s = extras.getString("linea_total");
            this.w = (DatosCredito) extras.getParcelable("selected_credit");
            this.x = extras.getString("cuenta_debito");
            this.y = extras.getString("disponible_usos");
            this.z = extras.getString("saldo_deuda_capital");
            this.A = extras.getString("plazo_credito");
            this.B = extras.getString("des_leyenda");
        }
        this.cAccessibility = CAccessibility.getInstance(this);
        this.v = this;
        ButterKnife.inject((Activity) this);
        b();
        configureActionBar();
    }

    private void b() {
        ((TextView) findViewById(R.id.F10_01_lbl_leyenda)).setText(Html.fromHtml(this.B));
        c();
    }

    private void c() {
        this.cuotasListView.setAdapter(new CustomAdapter(this, R.layout.item_cuotas, this.p.getDatosCuota()));
        this.cuotasListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ProximasCuotasActivity.this.cuotasListView.getItemAtPosition(i).toString();
                if (i == 0) {
                    String leyendaTasa = CCreditos.getLeyendaTasa(ProximasCuotasActivity.this.q, ProximasCuotasActivity.this.w);
                    String descripcion = ProximasCuotasActivity.this.a(CreditosConstants.TC_DETA_CUOTA).getDescripcion();
                    Intent intent = new Intent(ProximasCuotasActivity.this.v, DetalleCuotaActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtra("leyenda_int_punit", descripcion);
                    intent.putExtra("selected_credit", ProximasCuotasActivity.this.w);
                    intent.putExtra("cuota", (Parcelable) ProximasCuotasActivity.this.p.getDatosCuota().get(i));
                    bundle.putString("cuenta_debito", ProximasCuotasActivity.this.x);
                    bundle.putString("disponible_usos", ProximasCuotasActivity.this.y);
                    bundle.putString("saldo_deuda_capital", ProximasCuotasActivity.this.z);
                    bundle.putString("plazo_credito", ProximasCuotasActivity.this.A);
                    bundle.putString("linea_total_credito", ProximasCuotasActivity.this.s);
                    intent.putExtra("leyenda", ProximasCuotasActivity.this.B);
                    intent.putExtra("tasaLeyendas", leyendaTasa);
                    intent.putExtras(bundle);
                    ProximasCuotasActivity.this.startActivity(intent, ActivityOptions.makeCustomAnimation(ProximasCuotasActivity.this.getApplicationContext(), R.anim.slide_up, R.anim.no_animation).toBundle());
                }
            }
        });
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.BACK);
        enableCloseButton();
    }

    public void enableCloseButton() {
        View findViewById = getSupportActionBar().getCustomView().findViewById(R.id.toggle);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ProximasCuotasActivity.this.onBackPressed();
                }
            });
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right);
    }

    /* access modifiers changed from: private */
    public DatosLeyenda a(String str) {
        for (DatosLeyenda datosLeyenda : this.q.getDatosleyenda()) {
            if (datosLeyenda.getIdleyenda().equals(str)) {
                return datosLeyenda;
            }
        }
        return null;
    }
}
