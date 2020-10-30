package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.adapters.WatsonMessagesRecyclerViewAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.WatsonMessagesRecyclerViewAdapter.ItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.constants.WatsonConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.EmptyFragment;
import ar.com.santander.rio.mbanking.components.IngresarMailDialog;
import ar.com.santander.rio.mbanking.components.IngresarMailDialog.IngresarMailDialogListener;
import ar.com.santander.rio.mbanking.components.webviews.ShowZoomImage;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GenesysChatEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.CustomDimenssion;
import ar.com.santander.rio.mbanking.services.model.general.DatoWatson;
import ar.com.santander.rio.mbanking.services.model.general.Destino;
import ar.com.santander.rio.mbanking.services.model.general.EventTracker;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GenesysChatBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaMensajes;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MensajeGenesysChat;
import ar.com.santander.rio.mbanking.services.soap.versions.VGenesysChat;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.genesyschat.WhatsonChatBackgroundService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import io.fabric.sdk.android.services.common.IdManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.bouncycastle.crypto.tls.CipherSuite;

public class WatsonFragment extends BaseFragment implements ItemClickListener {
    public static InputFilter EMOJI_FILTER = new InputFilter() {
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            while (i < i2) {
                int type = Character.getType(charSequence.charAt(i));
                if (type == 19 || type == 28) {
                    return "";
                }
                i++;
            }
            return null;
        }
    };
    public static final int HOLDER_CLICK_IMAGEN = 6;
    public static final int HOLDER_CONTENEDOR_DE_LISTA_DE_MENSAJES = 0;
    public static final int HOLDER_CONTENEDOR_DE_LISTA_DE_MENSAJES_OPCIONES_PREGUNTAS = 2;
    public static final int HOLDER_CONTENEDOR_DE_LISTA_DE_SUGERENCIAS = 3;
    public static final int HOLDER_CONTENEDOR_DE_RESULTO_UTIL_ESTA_RESPUESTA = 5;
    public static final int HOLDER_CONTENEDOR_DE_RES_ERRORS = 4;
    public static final int HOLDER_CONTENEDOR_MENSAJE_GENERADO_POR_EL_USUARIO = 1;
    public static final int HOLDER_CONTENEDOR_MENSAJE_GENERADO_POR_EL_USUARIO_CLICK_SUGERENCIA = 8;
    public static final int HOLDER_CONTENEDOR_WAITING_ESCRIBIENDO = 7;
    public static boolean bFirstRequest = false;
    public static boolean bLoadingSugerencia = false;
    @Inject
    IDataManager a;
    /* access modifiers changed from: private */
    public LinearLayout ad;
    /* access modifiers changed from: private */
    public RecyclerView ae;
    /* access modifiers changed from: private */
    public String af;
    /* access modifiers changed from: private */
    public String ag = "";
    private FragmentActivity ah;
    private BroadcastReceiver ai;
    private boolean aj = true;
    /* access modifiers changed from: private */
    public long ak;
    /* access modifiers changed from: private */
    public long al;
    private Intent am;
    /* access modifiers changed from: private */
    public int an = 0;
    /* access modifiers changed from: private */
    public boolean ao = true;
    SettingsManager b;
    public boolean bLoadingDataFromServer = false;
    SessionManager c;
    AnalyticsManager d;
    /* access modifiers changed from: private */
    public WatsonMessagesRecyclerViewAdapter e;
    /* access modifiers changed from: private */
    public boolean f = false;
    /* access modifiers changed from: private */
    public GenesysChatBodyResponseBean g;
    /* access modifiers changed from: private */
    public List<DatoWatson> h = new ArrayList();
    /* access modifiers changed from: private */
    public EditText i;

    public static WatsonFragment newInstance(SessionManager sessionManager, SettingsManager settingsManager, AnalyticsManager analyticsManager) {
        WatsonFragment watsonFragment = new WatsonFragment();
        Bundle bundle = new Bundle();
        watsonFragment.c = sessionManager;
        watsonFragment.b = settingsManager;
        watsonFragment.d = analyticsManager;
        watsonFragment.setArguments(bundle);
        return watsonFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            new Bus().register(this);
            showProgress(VGenesysChat.nameService);
            I();
            EventTracker eventTracker = new EventTracker("Apertura", "Apertura", "Chat", "Apertura", null);
            this.d.trackCustomDimension(getResources().getString(R.string.analytics_trackevent_category_watson_apertura), eventTracker);
            this.a.genesysChat("1", "");
            bFirstRequest = true;
            this.d.trackEvent(getResources().getString(R.string.analytics_trackevent_category_watson), getResources().getString(R.string.analytics_trackevent_category_watson_apertura), getResources().getString(R.string.analytics_trackevent_category_watson_apertura));
        }
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.watson_fragment, viewGroup, false);
        b(inflate);
        E();
        final View findViewById = getActivity().getWindow().getDecorView().findViewById(16908290);
        findViewById.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (findViewById.getRootView().getHeight() - findViewById.getHeight() > 100) {
                    WatsonFragment.this.f = true;
                } else if (WatsonFragment.this.f) {
                    WatsonFragment.this.f = false;
                }
            }
        });
        this.i.setInputType(CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA);
        this.i.setFilters(new InputFilter[]{EMOJI_FILTER});
        this.i.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    WatsonFragment.this.I();
                }
            }
        });
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        this.ai = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                WatsonFragment.this.g = (GenesysChatBodyResponseBean) intent.getExtras().get(WhatsonChatBackgroundService.RESPONSE_EXTRA);
                if (WatsonFragment.this.g != null) {
                    WatsonFragment.this.crearMensajeOperador(WatsonFragment.this.g);
                    WatsonFragment.this.ae.smoothScrollToPosition(WatsonFragment.this.h.size() - 1);
                }
                Log.d("ServiceCall", intent.toString());
                WatsonFragment.this.d.trackCustomDimension(WatsonFragment.this.getActivity().getString(R.string.analytics_trackevent_action_watson_de_chat), 60, WatsonFragment.this.getActivity().getString(R.string.analytics_custom_dimensions_operador));
            }
        };
        LocalBroadcastManager.getInstance(this.ah).registerReceiver(this.ai, new IntentFilter(WhatsonChatBackgroundService.NEW_GENESYS_ACTION_MESSAGE));
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.ah = getActivity();
    }

    private void b(View view) {
        this.ad = (LinearLayout) view.findViewById(R.id.layout_icon);
        this.i = (EditText) view.findViewById(R.id.editTextChatWatson);
        this.ae = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.ae.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.e = new WatsonMessagesRecyclerViewAdapter(getActivity(), this.h, this.d, this.g);
        this.e.setClickListener(this);
        this.ae.setAdapter(this.e);
        crearMensajeOperador(this.g);
        G();
    }

    public void onItemClick(View view, int i2, int i3, String str) {
        if (i3 != 6) {
            if (i3 != 8) {
                switch (i3) {
                    case 0:
                        if (!str.isEmpty()) {
                            showZoomPopUpImage(Html.fromHtml(str).toString());
                            return;
                        }
                        return;
                    case 1:
                        StringBuilder sb = new StringBuilder();
                        if (this.g.getListaPreguntas() != null && this.g.getListaPreguntas().getPregunta() != null) {
                            for (String append : this.g.getListaPreguntas().getPregunta()) {
                                sb.append(append);
                            }
                            c(String.valueOf(Html.fromHtml(str)));
                            this.d.trackCustomDimension(getContext().getString(R.string.analytics_trackevent_action_watson_de_chat), 64, str);
                            z();
                            I();
                            this.ae.smoothScrollToPosition(this.h.size() - 1);
                            this.a.genesysChat("2", Html.fromHtml(str).toString());
                            return;
                        } else if (this.g.getListaOpciones() != null && this.g.getListaOpciones().getOpcion() != null) {
                            if (str.equalsIgnoreCase(getResources().getString(R.string.analytics_trackevent_label_watson_si))) {
                                b(str);
                            } else if (str.equalsIgnoreCase(getResources().getString(R.string.analytics_trackevent_label_watson_no))) {
                                b(str);
                            }
                            c(String.valueOf(Html.fromHtml(str)));
                            z();
                            I();
                            this.ae.smoothScrollToPosition(this.h.size() - 1);
                            this.a.genesysChat("2", Html.fromHtml(str).toString());
                            return;
                        } else {
                            return;
                        }
                    case 2:
                        if (str.equalsIgnoreCase(getResources().getString(R.string.analytics_trackevent_label_watson_si))) {
                            b(str);
                        }
                        c(this.g);
                        return;
                    default:
                        Toast.makeText(getActivity(), "Asignar alguna accion para este Item", 0).show();
                        return;
                }
            } else {
                AnalyticsManager analyticsManager = this.d;
                String string = getActivity().getString(R.string.analytics_trackevent_action_watson_de_chat);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("|T|");
                analyticsManager.trackCustomDimension(string, 63, sb2.toString());
                if (!this.bLoadingDataFromServer) {
                    c(String.valueOf(Html.fromHtml(str)));
                    z();
                } else {
                    if (this.h.size() > 1 && ((DatoWatson) this.h.get(this.h.size() - 1)).getType() == 7) {
                        this.h.remove(this.h.size() - 1);
                        this.e.notifyItemRemoved(this.h.size() - 1);
                        this.bLoadingDataFromServer = false;
                    }
                    c(String.valueOf(Html.fromHtml(str)));
                    z();
                }
                this.ae.smoothScrollToPosition(this.h.size() - 1);
                I();
                this.a.genesysChat("2", Html.fromHtml(str).toString());
            }
        } else if (!str.isEmpty()) {
            showZoomPopUpImage(Html.fromHtml(str).toString());
        }
    }

    private void b(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CustomDimenssion(20, Html.fromHtml(this.g.getChatId()).toString(), getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
        this.d.trackCustomDimension(getResources().getString(R.string.analytics_trackevent_category_watson), new EventTracker(str, getResources().getString(R.string.analytics_trackevent_category_watson), getResources().getString(R.string.analytics_trackevent_action_watson_satisfaccion), arrayList));
    }

    private String y() {
        String[] split = Calendar.getInstance().getTime().toString().split(UtilsCuentas.SEPARAOR2)[3].split(":");
        String str = split[0];
        return str.concat(":").concat(split[1]);
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        if (!str.trim().isEmpty()) {
            this.af = str;
            this.h.add(new DatoWatson(y(), str));
            this.e.notifyItemInserted(this.h.size() - 1);
        }
        this.i.setText("");
    }

    /* access modifiers changed from: private */
    public void z() {
        if (!this.bLoadingDataFromServer) {
            this.h.add(new DatoWatson(7));
            this.e.notifyItemInserted(this.h.size() - 1);
            this.bLoadingDataFromServer = true;
        }
    }

    /* access modifiers changed from: protected */
    public void crearMensajeOperador(GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
        if (genesysChatBodyResponseBean != null) {
            if (genesysChatBodyResponseBean.getListaMensajes() != null && genesysChatBodyResponseBean.getListaOpciones() == null) {
                C();
            }
            if (genesysChatBodyResponseBean.getListaOpciones() != null) {
                a(genesysChatBodyResponseBean);
            }
            if (genesysChatBodyResponseBean.getListaSugerencias() != null) {
                b(genesysChatBodyResponseBean);
                D();
            } else if (genesysChatBodyResponseBean.getEsPregunta().equalsIgnoreCase("1")) {
                D();
            }
        }
    }

    /* access modifiers changed from: private */
    public void A() {
        DatoWatson datoWatson = new DatoWatson(y(), getResources().getString(R.string.f185ID_4740_WATSON_ESTA_SESIN_HA_EXPIRADO), 4);
        this.d.trackEvent(getResources().getString(R.string.analytics_trackevent_category_watson), getResources().getString(R.string.analytics_trackevent_action_watson_sesion_expirada), getResources().getString(R.string.analytics_trackevent_action_watson_sesion_expirada));
        this.h.add(datoWatson);
        this.e.notifyItemInserted(this.h.size() - 1);
        this.i.setText("");
        c(((DatoWatson) this.h.get(this.h.size() - 2)).getUserMessage());
        z();
        this.d.trackEvent(getResources().getString(R.string.analytics_trackevent_category_watson), getResources().getString(R.string.analytics_trackevent_action_watson_retorno), getResources().getString(R.string.analytics_trackevent_action_watson_retorno));
        I();
        this.a.genesysChat("5", ((DatoWatson) this.h.get(this.h.size() - 2)).getUserMessage());
    }

    /* access modifiers changed from: private */
    public void B() {
        this.h.add(new DatoWatson(y(), "   ".concat(getResources().getString(R.string.ID_4739_WATSON_MENSAJE_NO_ENVIADO)), 4));
        this.e.notifyItemInserted(this.h.size() - 1);
        this.i.setText("");
    }

    private void C() {
        this.h.add(new DatoWatson(y(), this.g.getListaMensajes()));
        this.e.notifyItemInserted(this.h.size() - 1);
        this.i.setText("");
    }

    private void a(GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
        this.h.add(new DatoWatson(y(), genesysChatBodyResponseBean.getListaMensajes(), genesysChatBodyResponseBean.getListaOpciones()));
        this.e.notifyItemInserted(this.h.size() - 1);
        this.i.setText("");
    }

    private void b(GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
        this.h.add(new DatoWatson(y(), genesysChatBodyResponseBean.getListaSugerencias()));
        this.e.notifyItemInserted(this.h.size() - 1);
        this.i.setText("");
    }

    private void D() {
        this.h.add(new DatoWatson(5));
        this.e.notifyItemInserted(this.h.size() - 1);
        this.i.setText("");
    }

    private void c(GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
        if (genesysChatBodyResponseBean.getListaPreguntas() != null) {
            ListaMensajes listaMensajes = new ListaMensajes();
            ArrayList arrayList = new ArrayList();
            MensajeGenesysChat mensajeGenesysChat = new MensajeGenesysChat();
            mensajeGenesysChat.setTexto(getResources().getString(R.string.ID_4743_WATSON_A_LO_MEJOR_QUISISTE_PREGUNTAR_POR_ESTO));
            arrayList.add(mensajeGenesysChat);
            listaMensajes.setMensaje(arrayList);
            this.h.add(new DatoWatson(y(), listaMensajes, genesysChatBodyResponseBean.getListaPreguntas()));
            this.e.notifyItemInserted(this.h.size() - 1);
            this.i.setText("");
        }
    }

    private void E() {
        this.ad.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                WatsonFragment.this.af = WatsonFragment.this.i.getText().toString();
                if (!WatsonFragment.this.af.trim().isEmpty()) {
                    WatsonFragment.this.an = WatsonFragment.this.an + 1;
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new CustomDimenssion(20, Html.fromHtml(WatsonFragment.this.g.getChatId()).toString(), WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                    arrayList.add(new CustomDimenssion(98, String.valueOf(WatsonFragment.this.an), WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                    EventTracker eventTracker = new EventTracker("Pregunta", String.valueOf(WatsonFragment.this.an).concat("-").concat(WatsonFragment.this.af), "Chat", "Pregunta", arrayList);
                    WatsonFragment.this.d.trackCustomDimension(WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_category_watson_apertura), eventTracker);
                    if (WatsonFragment.this.h.size() > 1 && ((DatoWatson) WatsonFragment.this.h.get(WatsonFragment.this.h.size() - 1)).getType() == 7) {
                        WatsonFragment.this.h.remove(WatsonFragment.this.h.size() - 1);
                        WatsonFragment.this.e.notifyItemRemoved(WatsonFragment.this.h.size() - 1);
                        WatsonFragment.this.bLoadingDataFromServer = false;
                    }
                    WatsonFragment.this.I();
                    WatsonFragment.this.a.genesysChat("2", WatsonFragment.this.af);
                    WatsonFragment.this.c(WatsonFragment.this.i.getText().toString());
                    WatsonFragment.this.z();
                    WatsonFragment.this.ae.smoothScrollToPosition(WatsonFragment.this.h.size() - 1);
                    WatsonFragment.this.ad.setEnabled(false);
                    WatsonFragment.this.i.setEnabled(false);
                }
                InputMethodManager inputMethodManager = (InputMethodManager) WatsonFragment.this.getActivity().getSystemService("input_method");
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    private void F() {
        this.i.setOnClickListener(null);
        this.ad.setOnClickListener(null);
    }

    public void mostrarPopPapIngresarMail() {
        this.d.trackScreen(getResources().getString(R.string.analytics_trackevent_action_watson_consulta_fuera_de_horario));
        IngresarMailDialog.showFragmentManager(getFragmentManager(), new IngresarMailDialogListener() {
            public void enviarMailAlservioGenesysCaht(String str) {
            }
        }, "", this.c, this.b, this.d);
    }

    public void showZoomPopUpImage(String str) {
        if (getChildFragmentManager().findFragmentByTag(ShowZoomImage.class.getName()) == null) {
            try {
                getChildFragmentManager().beginTransaction().replace(R.id.child_fragment, ShowZoomImage.newInstance(str), ShowZoomImage.class.getName()).addToBackStack(null).commit();
            } catch (Exception e2) {
                e2.fillInStackTrace();
            }
        }
    }

    private void G() {
        String str = "";
        if (this.c.getLoginUnico().getDestinosMyA().getDestinos() != null) {
            Iterator it = this.c.getLoginUnico().getDestinosMyA().getDestinos().getDestinos().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Destino destino = (Destino) it.next();
                if (destino.getDestinoTipo().equals("MAIL") && destino.getDestinoSecuencia().equals("1")) {
                    str = destino.getDestinoDescripcion();
                    break;
                }
            }
        }
        IngresarMailDialog.setMailDefaul(str);
    }

    public void onBackPressed() {
        if (getChildFragmentManager().findFragmentByTag(ShowZoomImage.class.getName()) != null) {
            getChildFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe
    public void onGenesysChat(GenesysChatEvent genesysChatEvent) {
        dismissProgress();
        this.ad.setEnabled(true);
        this.i.setEnabled(true);
        if (this.h.size() > 1 && ((DatoWatson) this.h.get(this.h.size() - 1)).getType() == 7) {
            this.h.remove(this.h.size() - 1);
            this.e.notifyItemRemoved(this.h.size() - 1);
        }
        final GenesysChatEvent genesysChatEvent2 = genesysChatEvent;
        AnonymousClass7 r3 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, VGenesysChat.nameService, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                WatsonFragment.this.g = genesysChatEvent2.getResponse().getGenesysChatBodyResponseBean();
                if (WatsonFragment.this.g != null) {
                    a(WatsonFragment.this.g);
                    if (WatsonFragment.this.g.getRellamados() != null) {
                        WatsonFragment.this.ak = WatsonFragment.this.g.getRellamados().getCantidad();
                        WatsonFragment.this.al = WatsonFragment.this.g.getRellamados().getTiempo();
                    }
                    WatsonFragment.this.crearMensajeOperador(WatsonFragment.this.g);
                    WatsonFragment.this.ae.smoothScrollToPosition(WatsonFragment.this.h.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            WatsonFragment.this.H();
                            WatsonFragment.this.ae.smoothScrollToPosition(WatsonFragment.this.h.size() - 1);
                        }
                    }, 1000);
                    WatsonFragment.this.bLoadingDataFromServer = false;
                    WatsonFragment.bFirstRequest = false;
                    WatsonFragment.bLoadingSugerencia = false;
                }
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                WatsonFragment.this.bLoadingDataFromServer = false;
                WatsonFragment.bFirstRequest = false;
                EmptyFragment newInstance = EmptyFragment.newInstance(WatsonFragment.this.getResources().getString(R.string.ID_4737_WATSON_TU_ASISTENTEN_VIRTUAL), Html.fromHtml(genesysChatEvent2.getErrorBodyBean().resDesc).toString(), R.drawable.error_continuacion);
                FragmentManager fragmentManager = WatsonFragment.this.getFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                    beginTransaction.add((int) R.id.content_frame, (Fragment) newInstance);
                    beginTransaction.commit();
                }
            }

            /* access modifiers changed from: protected */
            public void onRes1Error(WebServiceEvent webServiceEvent) {
                WatsonFragment.this.bLoadingDataFromServer = false;
                WatsonFragment.bFirstRequest = false;
                WatsonFragment.this.B();
            }

            /* access modifiers changed from: protected */
            public void onRes3Error() {
                WatsonFragment.this.bLoadingDataFromServer = false;
                WatsonFragment.bFirstRequest = false;
                WatsonFragment.this.A();
                WatsonFragment.this.ae.smoothScrollToPosition(WatsonFragment.this.h.size() - 1);
            }

            private void a(GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
                if (WatsonFragment.this.ao) {
                    b(genesysChatBodyResponseBean);
                    WatsonFragment.this.ao = false;
                }
                c(genesysChatBodyResponseBean);
            }

            private void b(GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new CustomDimenssion(60, genesysChatBodyResponseBean.getNickName(), WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                arrayList.add(new CustomDimenssion(20, genesysChatBodyResponseBean.getChatId(), WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                EventTracker eventTracker = new EventTracker("Inicio", genesysChatBodyResponseBean.getChatId(), "Chat", "Inicio", arrayList);
                WatsonFragment.this.d.trackCustomDimension(WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_category_watson_apertura), eventTracker);
            }

            private void a(String str, String str2) {
                if (str != null) {
                    String lowerCase = str.trim().toLowerCase();
                    String texto = (WatsonFragment.this.g.getListaMensajes() == null || WatsonFragment.this.g.getListaMensajes().mensaje.get(0) == null) ? "" : ((MensajeGenesysChat) WatsonFragment.this.g.getListaMensajes().mensaje.get(0)).getTexto();
                    if (WatsonFragment.this.ag.isEmpty()) {
                        WatsonFragment.this.ag = lowerCase;
                    } else if (!WatsonFragment.this.ag.equalsIgnoreCase(lowerCase)) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(new CustomDimenssion(20, str2, WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                        arrayList.add(new CustomDimenssion(64, texto, WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                        EventTracker eventTracker = new EventTracker("Cambio Operador", "Call Center", "Chat", "Cambio Operador", arrayList);
                        WatsonFragment.this.d.trackCustomDimension("Cambio Operador", eventTracker);
                    }
                }
            }

            private void c(GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                if (genesysChatBodyResponseBean.getConfianza() != null && !genesysChatBodyResponseBean.getConfianza().equals(IdManager.DEFAULT_VERSION_NAME)) {
                    hashMap.put(Integer.valueOf(1), Integer.valueOf(((int) Float.parseFloat(genesysChatBodyResponseBean.getConfianza())) / 100));
                }
                String texto = (genesysChatBodyResponseBean.getListaMensajes() == null || genesysChatBodyResponseBean.getListaMensajes().mensaje.get(0) == null) ? "" : ((MensajeGenesysChat) genesysChatBodyResponseBean.getListaMensajes().mensaje.get(0)).getTexto();
                String d = d(genesysChatBodyResponseBean);
                arrayList.add(new CustomDimenssion(20, genesysChatBodyResponseBean.getChatId(), WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                arrayList.add(new CustomDimenssion(9, d, WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                arrayList.add(new CustomDimenssion(10, genesysChatBodyResponseBean.getIntencion(), WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                arrayList.add(new CustomDimenssion(98, String.valueOf(WatsonFragment.this.an), WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
                EventTracker eventTracker = new EventTracker("Respuesta", String.valueOf(WatsonFragment.this.an).concat("-").concat(texto), "Chat", "Respuesta", arrayList, hashMap);
                WatsonFragment.this.d.trackCustomDimension(WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_category_watson_apertura), eventTracker);
                a(genesysChatBodyResponseBean.getNickName(), genesysChatBodyResponseBean.getChatId());
            }

            private String d(GenesysChatBodyResponseBean genesysChatBodyResponseBean) {
                String str = WatsonConstants.TIPO_RESPUESTA;
                if (genesysChatBodyResponseBean.getListaOpciones() != null && genesysChatBodyResponseBean.getListaOpciones().getOpcion().size() > 0 && genesysChatBodyResponseBean.getListaSugerencias() != null && genesysChatBodyResponseBean.getListaSugerencias().getSugerencia().size() > 0) {
                    return WatsonConstants.TIPO_RESPUESTA_DESAMBIGUACION_Y_SUGERENCIA;
                }
                if (genesysChatBodyResponseBean.getListaOpciones() == null || genesysChatBodyResponseBean.getListaOpciones().getOpcion().size() <= 0 || genesysChatBodyResponseBean.getListaSugerencias() == null || genesysChatBodyResponseBean.getListaSugerencias().getSugerencia().size() != 0) {
                    return (genesysChatBodyResponseBean.getListaOpciones() == null || genesysChatBodyResponseBean.getListaOpciones().getOpcion().size() != 0 || genesysChatBodyResponseBean.getListaSugerencias() == null || genesysChatBodyResponseBean.getListaSugerencias().getSugerencia().size() <= 0) ? str : WatsonConstants.TIPO_RESPUESTA_SUGERENCIA;
                }
                return WatsonConstants.TIPO_RESPUESTA_DESAMBIGUACION;
            }

            /* access modifiers changed from: protected */
            public void onServerError(WebServiceEvent webServiceEvent) {
                WatsonFragment.this.bLoadingDataFromServer = false;
                if (WatsonFragment.bFirstRequest) {
                    EmptyFragment newInstance = EmptyFragment.newInstance(WatsonFragment.this.getResources().getString(R.string.ID_4737_WATSON_TU_ASISTENTEN_VIRTUAL), WatsonFragment.this.getString(R.string.WATSON_res_4), R.drawable.error_continuacion);
                    FragmentManager fragmentManager = WatsonFragment.this.getFragmentManager();
                    if (fragmentManager != null) {
                        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                        beginTransaction.add((int) R.id.content_frame, (Fragment) newInstance);
                        beginTransaction.commit();
                    }
                } else {
                    super.onServerError(webServiceEvent);
                }
                WatsonFragment.this.d.trackScreen(WatsonFragment.this.getResources().getString(R.string.analytics_trackevent_action_watson_error_de_chat));
                WatsonFragment.this.B();
                WatsonFragment.bFirstRequest = false;
            }

            /* access modifiers changed from: protected */
            public void onRes8Error() {
                WatsonFragment.this.bLoadingDataFromServer = false;
                WatsonFragment.this.mostrarPopPapIngresarMail();
            }
        };
        r3.handleWSResponse(genesysChatEvent);
    }

    public void onDetach() {
        super.onDetach();
        F();
        I();
        LocalBroadcastManager.getInstance(this.ah).unregisterReceiver(this.ai);
    }

    /* access modifiers changed from: private */
    public void H() {
        if (!this.aj) {
            this.am = new Intent(this.ah, WhatsonChatBackgroundService.class);
            Long l = null;
            this.am.putExtra("COUNT", this.ak > 0 ? Long.valueOf(this.ak) : null);
            Intent intent = this.am;
            String str = WhatsonChatBackgroundService.PERIODO_EXTRA;
            if (this.al > 0) {
                l = Long.valueOf(this.al);
            }
            intent.putExtra(str, l);
            this.ah.startService(this.am);
            return;
        }
        this.aj = false;
    }

    /* access modifiers changed from: private */
    public void I() {
        this.ah.stopService(new Intent(this.ah, WhatsonChatBackgroundService.class));
    }
}
