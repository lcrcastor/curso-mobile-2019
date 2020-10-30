package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.WatsonFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.model.general.CustomDimenssion;
import ar.com.santander.rio.mbanking.services.model.general.DatoWatson;
import ar.com.santander.rio.mbanking.services.model.general.EventTracker;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GenesysChatBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MensajeGenesysChat;
import com.google.common.net.HttpHeaders;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.bouncycastle.crypto.tls.CipherSuite;

public class WatsonMessagesRecyclerViewAdapter extends Adapter<ViewHolder> {
    @Inject
    AnalyticsManager a;
    /* access modifiers changed from: private */
    public List<DatoWatson> b;
    /* access modifiers changed from: private */
    public LayoutInflater c;
    /* access modifiers changed from: private */
    public ItemClickListener d;
    /* access modifiers changed from: private */
    public Context e;
    /* access modifiers changed from: private */
    public GenesysChatBodyResponseBean f;
    public int type;

    public interface ItemClickListener {
        void onItemClick(View view, int i, int i2, String str);
    }

    public class ViewHolder0 extends ViewHolder {
        LinearLayout m;
        TextView n;

        ViewHolder0(View view) {
            super(view);
            this.m = (LinearLayout) view.findViewById(R.id.parent);
            this.n = (TextView) WatsonMessagesRecyclerViewAdapter.this.c.inflate(R.layout.vista_watson_mensaje_servicio_burbuja_fecha, this.m, false);
        }
    }

    public class ViewHolder1 extends ViewHolder {
        TextView m;
        TextView n;

        ViewHolder1(View view) {
            super(view);
            this.m = (TextView) view.findViewById(R.id.tvCliente);
            this.n = (TextView) view.findViewById(R.id.fecha);
        }
    }

    public class ViewHolder2 extends ViewHolder {
        LinearLayout m;
        TextView n;

        ViewHolder2(View view) {
            super(view);
            this.m = (LinearLayout) view.findViewById(R.id.parent);
            this.n = (TextView) WatsonMessagesRecyclerViewAdapter.this.c.inflate(R.layout.vista_watson_mensaje_servicio_burbuja_fecha, this.m, false);
        }
    }

    public class ViewHolder3 extends ViewHolder {
        LinearLayout m = ((LinearLayout) this.itemView.findViewById(R.id.parentSugerencia));
        ConstraintLayout n;

        ViewHolder3(View view) {
            super(view);
            this.n = (ConstraintLayout) WatsonMessagesRecyclerViewAdapter.this.c.inflate(R.layout.vista_watson_mensaje_burbuja_sugerencia, this.m, false);
            this.m.addView(this.n);
        }
    }

    public class ViewHolder4 extends ViewHolder {
        TextView m = ((TextView) this.itemView.findViewById(R.id.hora));
        TextView n = ((TextView) this.itemView.findViewById(R.id.estaSesionHaExpirado));

        ViewHolder4(View view) {
            super(view);
        }
    }

    public class ViewHolder5 extends ViewHolder {
        TextView m = ((TextView) this.itemView.findViewById(R.id.txtMensaje));
        ImageView n = ((ImageView) this.itemView.findViewById(R.id.si));
        ImageView o = ((ImageView) this.itemView.findViewById(R.id.no));

        ViewHolder5(View view) {
            super(view);
        }
    }

    public WatsonMessagesRecyclerViewAdapter(Context context, List<DatoWatson> list, AnalyticsManager analyticsManager) {
        this.c = LayoutInflater.from(context);
        this.b = list;
        this.e = context;
        this.a = analyticsManager;
    }

    public WatsonMessagesRecyclerViewAdapter(Context context, List<DatoWatson> list, AnalyticsManager analyticsManager, GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
        this.c = LayoutInflater.from(context);
        this.b = list;
        this.e = context;
        this.a = analyticsManager;
        this.f = genesysChatBodyResponseBean;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = this.c.inflate(R.layout.vista_watson_mensaje_cliente, viewGroup, false);
        View inflate2 = this.c.inflate(R.layout.vista_watson_mensaje_res1, viewGroup, false);
        View inflate3 = this.c.inflate(R.layout.vista_watson_mensaje_operador_resulto_util, viewGroup, false);
        LinearLayout linearLayout = (LinearLayout) this.c.inflate(R.layout.vista_watson_mensaje_servicio_burbuja, viewGroup, false);
        LinearLayout linearLayout2 = (LinearLayout) this.c.inflate(R.layout.vista_watson_mensaje_servicio_burbuja_sugerencias, viewGroup, false);
        if (i != 7) {
            switch (i) {
                case 0:
                    linearLayout.findViewById(R.id.content);
                    return new ViewHolder0(linearLayout);
                case 1:
                    return new ViewHolder1(inflate);
                case 2:
                    linearLayout.findViewById(R.id.content);
                    return new ViewHolder2(linearLayout);
                case 3:
                    linearLayout2.findViewById(R.id.content);
                    return new ViewHolder3(linearLayout2);
                case 4:
                    return new ViewHolder4(inflate2);
                case 5:
                    return new ViewHolder5(inflate3);
                default:
                    linearLayout.findViewById(R.id.content);
                    return new ViewHolder0(linearLayout);
            }
        } else {
            linearLayout.findViewById(R.id.content);
            return new ViewHolder0(linearLayout);
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        DatoWatson datoWatson = (DatoWatson) this.b.get(i);
        int type2 = datoWatson.getType();
        if (type2 != 7) {
            switch (type2) {
                case 0:
                    ViewHolder0 viewHolder0 = (ViewHolder0) viewHolder;
                    viewHolder0.m.removeAllViews();
                    for (MensajeGenesysChat a2 : datoWatson.getListaMensajes().getMensaje()) {
                        a(a2, viewHolder0);
                    }
                    viewHolder0.n.setText(datoWatson.getFecha());
                    viewHolder0.m.addView(viewHolder0.n);
                    return;
                case 1:
                    ViewHolder1 viewHolder1 = (ViewHolder1) viewHolder;
                    viewHolder1.m.setText(Html.fromHtml(datoWatson.getUserMessage()));
                    viewHolder1.m.setContentDescription(CAccessibility.getInstance(this.e).applyVos(viewHolder1.m.getText().toString()));
                    viewHolder1.n.setText(Html.fromHtml(datoWatson.getFecha()));
                    return;
                case 2:
                    ViewHolder2 viewHolder2 = (ViewHolder2) viewHolder;
                    viewHolder2.m.removeAllViews();
                    if (datoWatson.getListaMensajes() != null) {
                        for (MensajeGenesysChat a3 : datoWatson.getListaMensajes().getMensaje()) {
                            a(a3, viewHolder2);
                        }
                    }
                    if (datoWatson.getListaOpciones() != null) {
                        for (String str : datoWatson.getListaOpciones().getOpcion()) {
                            AnalyticsManager analyticsManager = this.a;
                            String string = this.e.getString(R.string.analytics_trackevent_action_watson_de_chat);
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append("|F|");
                            analyticsManager.trackCustomDimension(string, 81, sb.toString());
                            a(str, viewHolder2);
                        }
                    }
                    if (datoWatson.getListaPreguntas() != null) {
                        ArrayList arrayList = new ArrayList();
                        for (String str2 : datoWatson.getListaPreguntas().getPregunta()) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str2);
                            sb2.append("|F|");
                            arrayList.add(new CustomDimenssion(63, sb2.toString(), this.e.getString(R.string.analytics_trackevent_action_watson_de_chat)));
                            AnalyticsManager analyticsManager2 = this.a;
                            String string2 = this.e.getString(R.string.analytics_trackevent_action_watson_de_chat);
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str2);
                            sb3.append("|F|");
                            analyticsManager2.trackCustomDimension(string2, 80, sb3.toString());
                            a(str2, viewHolder2);
                        }
                        EventTracker eventTracker = new EventTracker();
                        eventTracker.setCustomDimenssion(arrayList);
                        this.a.trackCustomDimension(this.e.getString(R.string.analytics_trackevent_action_watson_pregunta_alternativa), eventTracker);
                    }
                    viewHolder2.n.setText(Html.fromHtml(datoWatson.getFecha()));
                    viewHolder2.m.addView(viewHolder2.n);
                    return;
                case 3:
                    ViewHolder3 viewHolder3 = (ViewHolder3) viewHolder;
                    viewHolder3.m.removeAllViews();
                    viewHolder3.m.addView(viewHolder3.n);
                    if (datoWatson.getListaSugerencias().getSugerencia() != null) {
                        int i2 = 0;
                        for (String str3 : datoWatson.getListaSugerencias().getSugerencia()) {
                            int i3 = 1;
                            i2++;
                            if (i2 == ((DatoWatson) this.b.get(i)).getListaSugerencias().getSugerencia().size()) {
                                i3 = 0;
                            }
                            a(str3, viewHolder3, i2, i3);
                        }
                        return;
                    }
                    return;
                case 4:
                    ViewHolder4 viewHolder4 = (ViewHolder4) viewHolder;
                    viewHolder4.m.setText(Html.fromHtml(datoWatson.getFecha()));
                    viewHolder4.n.setText(Html.fromHtml(datoWatson.getMensaje()));
                    return;
                case 5:
                    final ViewHolder5 viewHolder5 = (ViewHolder5) viewHolder;
                    viewHolder5.m.setText(Html.fromHtml(this.e.getResources().getString(R.string.f186ID_4742_WATSON_TE_RESULT_TIL_LA_RESPUESTA)));
                    viewHolder5.o.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            WatsonMessagesRecyclerViewAdapter.this.d.onItemClick(viewHolder5.m, WatsonMessagesRecyclerViewAdapter.this.getItemCount(), 2, WatsonMessagesRecyclerViewAdapter.this.e.getResources().getString(R.string.analytics_trackevent_label_watson_no));
                            WatsonMessagesRecyclerViewAdapter.this.a.trackCustomDimension(WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat), 10, WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_custom_dimensions_intencion));
                            WatsonMessagesRecyclerViewAdapter.this.a.trackCustomDimension(WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat), 61, WatsonMessagesRecyclerViewAdapter.this.e.getResources().getString(R.string.analytics_trackevent_label_watson_no));
                            WatsonMessagesRecyclerViewAdapter.this.b.remove(i);
                            WatsonMessagesRecyclerViewAdapter.this.notifyItemRemoved(WatsonMessagesRecyclerViewAdapter.this.b.size());
                        }
                    });
                    viewHolder5.n.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            WatsonMessagesRecyclerViewAdapter.this.a.trackCustomDimension(WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat), 10, WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_custom_dimensions_intencion));
                            WatsonMessagesRecyclerViewAdapter.this.a.trackCustomDimension(WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat), 61, WatsonMessagesRecyclerViewAdapter.this.e.getResources().getString(R.string.analytics_trackevent_label_watson_si));
                            WatsonMessagesRecyclerViewAdapter.this.b.remove(i);
                            WatsonMessagesRecyclerViewAdapter.this.notifyItemRemoved(WatsonMessagesRecyclerViewAdapter.this.b.size());
                        }
                    });
                    return;
                default:
                    return;
            }
        } else {
            ViewHolder0 viewHolder02 = (ViewHolder0) viewHolder;
            viewHolder02.m.removeAllViews();
            a(viewHolder02);
        }
    }

    private void a(final MensajeGenesysChat mensajeGenesysChat, ViewHolder0 viewHolder0) {
        LinearLayout linearLayout = viewHolder0.m;
        if (!TextUtils.isEmpty(mensajeGenesysChat.getTexto())) {
            TextView textView = (TextView) this.c.inflate(R.layout.vista_watson_mensaje_servicio_burbuja_texto, linearLayout, false);
            String replace = mensajeGenesysChat.getTexto().replace("<li>", "&#8226  ").replace("</li>", "<br/>");
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(Html.fromHtml(replace));
            URLSpan[] uRLSpanArr = (URLSpan[]) spannableStringBuilder.getSpans(0, replace.length(), URLSpan.class);
            for (URLSpan a2 : uRLSpanArr) {
                a(spannableStringBuilder, a2, uRLSpanArr);
            }
            textView.setText(spannableStringBuilder);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            try {
                textView.setContentDescription(new CAccessibility(this.e).applyFilterAmount(new CAccessibility(this.e).applyOperador(textView.getText().toString())));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            linearLayout.addView(textView);
        }
        if (!TextUtils.isEmpty(mensajeGenesysChat.getImagen())) {
            FrameLayout frameLayout = (FrameLayout) this.c.inflate(R.layout.vista_watson_mensaje_servicio_burbuja_imagen, linearLayout, false);
            final ImageView imageView = (ImageView) frameLayout.findViewById(R.id.textViewBurbujaImagen);
            ImageView imageView2 = (ImageView) frameLayout.findViewById(R.id.lupa);
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    WatsonMessagesRecyclerViewAdapter.this.d.onItemClick(imageView, WatsonMessagesRecyclerViewAdapter.this.getItemCount(), 6, mensajeGenesysChat.getImagen());
                }
            });
            Picasso.with(this.e).load(mensajeGenesysChat.getImagen()).resize(287, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA).into(imageView);
            imageView2.setImageResource(R.drawable.lupa);
            linearLayout.addView(frameLayout);
        }
    }

    private void a(SpannableStringBuilder spannableStringBuilder, final URLSpan uRLSpan, final URLSpan[] uRLSpanArr) {
        spannableStringBuilder.setSpan(new ClickableSpan() {
            public void onClick(View view) {
                URLSpan[] uRLSpanArr;
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uRLSpan.getURL()));
                ArrayList arrayList = new ArrayList();
                arrayList.add(new CustomDimenssion(20, WatsonMessagesRecyclerViewAdapter.this.f.getChatId(), WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat)));
                StringBuilder sb = new StringBuilder();
                sb.append(uRLSpan.getURL());
                sb.append("|T|");
                arrayList.add(new CustomDimenssion(62, sb.toString(), WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat)));
                for (URLSpan uRLSpan : uRLSpanArr) {
                    if (uRLSpan != uRLSpan) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(uRLSpan.getURL());
                        sb2.append("|F|");
                        arrayList.add(new CustomDimenssion(62, sb2.toString(), WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat)));
                    }
                }
                EventTracker eventTracker = new EventTracker(HttpHeaders.LINK, WatsonMessagesRecyclerViewAdapter.this.f.getIntencion(), "Chat", HttpHeaders.LINK, arrayList);
                WatsonMessagesRecyclerViewAdapter.this.a.trackCustomDimension(WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_category_watson_apertura), eventTracker);
                WatsonMessagesRecyclerViewAdapter.this.e.startActivity(intent);
            }
        }, spannableStringBuilder.getSpanStart(uRLSpan), spannableStringBuilder.getSpanEnd(uRLSpan), spannableStringBuilder.getSpanFlags(uRLSpan));
    }

    private void a(final MensajeGenesysChat mensajeGenesysChat, ViewHolder2 viewHolder2) {
        LinearLayout linearLayout = viewHolder2.m;
        if (!TextUtils.isEmpty(mensajeGenesysChat.getTexto())) {
            TextView textView = (TextView) this.c.inflate(R.layout.vista_watson_mensaje_servicio_burbuja_texto, linearLayout, false);
            textView.setText(Html.fromHtml(mensajeGenesysChat.getTexto()));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            linearLayout.addView(textView);
        }
        if (!TextUtils.isEmpty(mensajeGenesysChat.getImagen())) {
            FrameLayout frameLayout = (FrameLayout) this.c.inflate(R.layout.vista_watson_mensaje_servicio_burbuja_imagen, linearLayout, false);
            final ImageView imageView = (ImageView) frameLayout.findViewById(R.id.textViewBurbujaImagen);
            ImageView imageView2 = (ImageView) frameLayout.findViewById(R.id.lupa);
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    WatsonMessagesRecyclerViewAdapter.this.d.onItemClick(imageView, WatsonMessagesRecyclerViewAdapter.this.getItemCount(), 6, mensajeGenesysChat.getImagen());
                }
            });
            Picasso.with(this.e).load(mensajeGenesysChat.getImagen()).resize(287, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA).into(imageView);
            imageView2.setImageResource(R.drawable.lupa);
            linearLayout.addView(frameLayout);
        }
    }

    private void a(final String str, ViewHolder2 viewHolder2) {
        LinearLayout linearLayout = viewHolder2.m;
        if (!TextUtils.isEmpty(str)) {
            final TextView textView = (TextView) this.c.inflate(R.layout.vista_watson_mensaje_burbuja_opcion_si, linearLayout, false);
            textView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!WatsonFragment.bLoadingSugerencia) {
                        WatsonMessagesRecyclerViewAdapter.this.d.onItemClick(textView, WatsonMessagesRecyclerViewAdapter.this.getItemCount(), 1, str);
                        AnalyticsManager analyticsManager = WatsonMessagesRecyclerViewAdapter.this.a;
                        String string = WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat);
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append("|T|");
                        analyticsManager.trackCustomDimension(string, 63, sb.toString());
                        AnalyticsManager analyticsManager2 = WatsonMessagesRecyclerViewAdapter.this.a;
                        String string2 = WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append("|T|");
                        analyticsManager2.trackCustomDimension(string2, 80, sb2.toString());
                        AnalyticsManager analyticsManager3 = WatsonMessagesRecyclerViewAdapter.this.a;
                        String string3 = WatsonMessagesRecyclerViewAdapter.this.e.getString(R.string.analytics_trackevent_action_watson_de_chat);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append("|T|");
                        analyticsManager3.trackCustomDimension(string3, 81, sb3.toString());
                        WatsonFragment.bLoadingSugerencia = true;
                    }
                }
            });
            textView.setText(Html.fromHtml(str).toString());
            linearLayout.addView(textView);
        }
    }

    private void a(final String str, ViewHolder3 viewHolder3, int i, int i2) {
        LinearLayout linearLayout = viewHolder3.m;
        if (!TextUtils.isEmpty(str)) {
            final TextView textView = (TextView) this.c.inflate(R.layout.vista_watson_mensaje_textview_sugerencia, linearLayout, false);
            ConstraintLayout constraintLayout = (ConstraintLayout) this.c.inflate(R.layout.vista_watson_mensaje_sugerencia_linea_separacion, linearLayout, false);
            textView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!WatsonFragment.bLoadingSugerencia) {
                        textView.setTextColor(WatsonMessagesRecyclerViewAdapter.this.e.getResources().getColor(R.color.grey_medium_light));
                        WatsonMessagesRecyclerViewAdapter.this.d.onItemClick(textView, WatsonMessagesRecyclerViewAdapter.this.getItemCount(), 8, str);
                        WatsonFragment.bLoadingSugerencia = true;
                    }
                }
            });
            textView.setText(Html.fromHtml(str).toString());
            textView.setPaintFlags(8);
            linearLayout.addView(textView);
            if (i2 == 1) {
                linearLayout.addView(constraintLayout);
            }
        }
    }

    private void a(ViewHolder0 viewHolder0) {
        LinearLayout linearLayout = viewHolder0.m;
        ImageView imageView = (ImageView) this.c.inflate(R.layout.vista_watson_linear_waiting_escribiendo, linearLayout, false);
        imageView.setImageDrawable(this.e.getResources().getDrawable(R.drawable.vista_watson_sucesion_de_puntos));
        ((AnimationDrawable) imageView.getDrawable()).start();
        linearLayout.addView(imageView);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.d = itemClickListener;
    }

    public DatoWatson getItem(int i) {
        return (DatoWatson) this.b.get(i);
    }

    public int getItemCount() {
        return this.b.size();
    }

    public int getItemViewType(int i) {
        return ((DatoWatson) this.b.get(i)).getType();
    }
}
