package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.forms.FormRecyclerView;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ItemDecoratorList;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter.OnButtonClickListener;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.ButtonField;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Data;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Footer;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Header;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IData;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Title;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.AbmTurnoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OpcionPantallaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class ConfirmarTurnoFragment extends BaseFragment {
    public static final String CONF_TURNO = "CONF_TURNO";
    public static final String LINK_TRANSACTION = "LINK_TRANSACTION";
    public static final String OPCION = "OPCION";
    private View a;
    /* access modifiers changed from: private */
    public IActionCustom ad;
    /* access modifiers changed from: private */
    public boolean ae = false;
    /* access modifiers changed from: private */
    public SucursalBean af;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener ag;
    private GetTurnoBodyResponseBean ah;
    /* access modifiers changed from: private */
    public String ai = "";
    private Context b;
    private Activity c;
    private FormRecyclerView d;
    private ListAdapter e;
    private List<IData> f;
    /* access modifiers changed from: private */
    public boolean g = false;
    private OpcionPantallaBean h;
    /* access modifiers changed from: private */
    public GetCircuitoTurnoBodyResponseBean i;
    @Inject
    public IDataManager mDataManager;

    public interface OnFragmentInteractionListener extends IFragmentBase {
        void showComprobanteTurno(AbmTurnoBodyResponseBean abmTurnoBodyResponseBean, SucursalBean sucursalBean, String str);
    }

    public static ConfirmarTurnoFragment newInstance(GetTurnoBodyResponseBean getTurnoBodyResponseBean, SucursalBean sucursalBean, OpcionPantallaBean opcionPantallaBean, GetCircuitoTurnoBodyResponseBean getCircuitoTurnoBodyResponseBean, boolean z) {
        ConfirmarTurnoFragment confirmarTurnoFragment = new ConfirmarTurnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_TURNO, getTurnoBodyResponseBean);
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_SUCURSAL_SELECCIONADA, sucursalBean);
        bundle.putParcelable(OPCION, opcionPantallaBean);
        bundle.putParcelable(SolicitarTurnoCajaFragment.GET_CIRCUITO_TURNO_BODY_RESPONSE_BEAN, getCircuitoTurnoBodyResponseBean);
        bundle.putBoolean(LINK_TRANSACTION, z);
        confirmarTurnoFragment.setArguments(bundle);
        return confirmarTurnoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.af = (SucursalBean) getArguments().getParcelable(SeleccionSucursalTurnoFragment.GET_SUCURSAL_SELECCIONADA);
            this.ah = (GetTurnoBodyResponseBean) getArguments().getParcelable(SeleccionSucursalTurnoFragment.GET_TURNO);
            this.h = (OpcionPantallaBean) getArguments().getParcelable(OPCION);
            this.i = (GetCircuitoTurnoBodyResponseBean) getArguments().getParcelable(SolicitarTurnoCajaFragment.GET_CIRCUITO_TURNO_BODY_RESPONSE_BEAN);
            this.ae = getArguments().getBoolean(LINK_TRANSACTION);
            this.ai = this.ae ? this.ah.getListaLegales().getLegalById("OP_RETIRO_DOC").getDescripcion() : this.h.getNombre();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.a = layoutInflater.inflate(R.layout.fragment_confirmar_turno, viewGroup, false);
        this.ag.configureBackActionBar();
        initializeLayout();
        return this.a;
    }

    public void initializeLayout() {
        this.f = getListItemGeneric();
        this.d = (FormRecyclerView) this.a.findViewById(R.id.recyclerViewConfirmacion);
        this.d.addItemDecoration(new ItemDecoratorList(getContext(), this.f));
        this.d.setItemViewCacheSize(this.f.size());
        this.d.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        this.e = new ListAdapter(this.f, getFragmentManager());
        this.e.setOnButtonClickListener(new OnButtonClickListener() {
            public void setActionButtton() {
                ConfirmarTurnoFragment.this.showConfirmarMovilDialog();
            }
        });
        this.d.setAdapter(this.e);
    }

    private String y() {
        return new DateTime((Object) Calendar.getInstance().getTime()).toString(getString(R.string.FORMAT_SHORT_DATE_YEAR_COMPLETE));
    }

    public void showConfirmarMovilDialog() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.F27_13_TITLE_DIALOG_CONFIRMAR), getContext().getString(R.string.MSG_USER00535_Turnos), null, null, getContext().getString(R.string.IDX_ALERT_BTN_YES), getContext().getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                if (!ConfirmarTurnoFragment.this.g) {
                    ConfirmarTurnoFragment.this.g = true;
                    ConfirmarTurnoFragment.this.mDataManager.abmTurno("A", ConfirmarTurnoFragment.this.af.numero, ConfirmarTurnoFragment.this.ae ? ConfirmarTurnoFragment.this.i.getLinkRetiroPieza().getMostrar() : "N", "");
                }
            }
        });
        newInstance.show(getFragmentManager(), "isbanDialogConfirm");
    }

    @Subscribe
    public void abmTurno(AbmTurnoEvent abmTurnoEvent) {
        dismissProgress();
        this.g = false;
        final AbmTurnoEvent abmTurnoEvent2 = abmTurnoEvent;
        AnonymousClass3 r1 = new BaseWSResponseHandler(getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getContext(), "Confirmar ticket para atención en caja") {
            /* access modifiers changed from: protected */
            public void onOk() {
                ConfirmarTurnoFragment.this.ag.showComprobanteTurno(abmTurnoEvent2.getResponse().getAbmTurnoBodyResponseBean(), ConfirmarTurnoFragment.this.af, ConfirmarTurnoFragment.this.ai);
            }

            /* access modifiers changed from: protected */
            public void onRes5Error(WebServiceEvent webServiceEvent) {
                super.onRes5Error(webServiceEvent, ConfirmarTurnoFragment.this.ad);
            }
        };
        r1.handleWSResponse(abmTurnoEvent);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.ag = (OnFragmentInteractionListener) context;
            this.ad = (IActionCustom) context;
            this.b = context;
            this.c = getActivity();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.ag = null;
        this.ad = null;
    }

    public List<IData> getListItemGeneric() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Title.newInstance().setTitle(getContext().getString(R.string.ID_4892_TURNOS_TIT_CONFIRMAR_TICKET_PARA_ATENCION_EN_CAJA)));
        arrayList.add(Header.newInstance().setTitle(this.af.descripcion).setSubTitle(this.af.direccion).setContentDescriptionSubTitle(CAccessibility.getInstance(this.b).applyFilter_BsAs(CAccessibility.getInstance(this.b).applyFilter_Ciudad(CAccessibility.getInstance(this.b).applyGuion(CAccessibility.getInstance(this.b).applyFilterDireccion(Html.fromHtml(this.af.direccion).toString()))))).setContentDescription(CAccessibility.getInstance(this.b).applyGuion(Html.fromHtml(this.af.descripcion).toString())));
        try {
            IData detail = Data.newInstance().setLabel(getContext().getString(R.string.ID_4880_TURNOS_LBL_FECHA_DEL_TICKET)).setDetail(y());
            String string = getContext().getString(R.string.ID_4880_TURNOS_LBL_FECHA_DEL_TICKET);
            StringBuilder sb = new StringBuilder();
            sb.append(", ");
            sb.append(CAccessibility.getInstance(this.b).applyFilterDate(y()));
            arrayList.add(detail.setContentDescription(string.concat(sb.toString())));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            IData detail2 = Data.newInstance().setLabel(getContext().getString(R.string.ID_4894_TURNOS_LBL_TIEMPO_ESTIMADO_DE_ESPERA)).setDetail(this.af.tiempoEspera);
            String string2 = getContext().getString(R.string.ID_4894_TURNOS_LBL_TIEMPO_ESTIMADO_DE_ESPERA);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(", ");
            sb2.append(CAccessibility.getInstance(this.b).applyFilterTime(this.af.tiempoEspera));
            arrayList.add(detail2.setContentDescription(string2.concat(sb2.toString())));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            IData detail3 = Data.newInstance().setLabel(getContext().getString(R.string.ID_4895_TURNOS_LBL_TIPO_DE_TRAMITE)).setDetail(this.ai);
            String string3 = getContext().getString(R.string.ID_4895_TURNOS_LBL_TIPO_DE_TRAMITE);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(", ");
            sb3.append(CAccessibility.getInstance(this.b).applyFilterAmount(Html.fromHtml(this.ai).toString()));
            arrayList.add(detail3.setContentDescription(string3.concat(sb3.toString())));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        arrayList.add(Footer.newInstance().setLabel(this.ah.getListaLegales().getLegalById(CONF_TURNO) == null ? "" : this.ah.getListaLegales().getLegalById(CONF_TURNO).getDescripcion()));
        arrayList.add(ButtonField.newInstance().setText(getContext().getString(R.string.ID_4896_TURNOS_BTN_CONFIRMAR)));
        return arrayList;
    }
}
