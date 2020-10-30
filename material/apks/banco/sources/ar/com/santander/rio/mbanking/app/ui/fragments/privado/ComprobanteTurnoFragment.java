package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.forms.FormConstraintLayout;
import ar.com.santander.rio.mbanking.app.ui.forms.FormRecyclerView;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ItemDecoratorList;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Data;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Footer;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Header;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IData;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Title;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OpcionPantallaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TurnoBean;
import java.util.ArrayList;
import java.util.List;

public class ComprobanteTurnoFragment extends ITRSABaseFragment {
    public static final String TIPO_COMPROBANTE = "TIPO_COMPROBANTE";
    public static final String TIPO_TRAMITE = "TIPO_TRAMITE";
    private View a;
    private List<IData> ad;
    /* access modifiers changed from: private */
    public Activity b;
    /* access modifiers changed from: private */
    public AbmTurnoBodyResponseBean c;
    /* access modifiers changed from: private */
    public SucursalBean d;
    private OpcionPantallaBean e;
    private String f = "";
    private String g;
    /* access modifiers changed from: private */
    public String h;
    /* access modifiers changed from: private */
    public TurnoBean i;

    public class CreateProductAlta implements Product {
        public CreateProductAlta() {
        }

        public List<IData> getListItem() {
            ArrayList arrayList = new ArrayList();
            ComprobanteTurnoFragment.this.addItemList(arrayList, Title.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4897_TURNOS_TIT_COMPROBANTE_DE_TICKET_PARA_ATENCION_EN_CAJA)));
            ComprobanteTurnoFragment.this.addItemList(arrayList, Header.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4898_TURNOS_LBL_TICKET), ComprobanteTurnoFragment.this.c.getTurno().getNumeroTicket()).setDescription(ComprobanteTurnoFragment.this.b.getString(R.string.COMROBANTE_LEYENDA)));
            try {
                ComprobanteTurnoFragment comprobanteTurnoFragment = ComprobanteTurnoFragment.this;
                Data newInstance = Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4880_TURNOS_LBL_FECHA_DEL_TICKET), ComprobanteTurnoFragment.this.c.getFechaOperacion());
                String string = ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4880_TURNOS_LBL_FECHA_DEL_TICKET);
                StringBuilder sb = new StringBuilder();
                sb.append(", ");
                sb.append(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterDate(ComprobanteTurnoFragment.this.c.getFechaOperacion()));
                comprobanteTurnoFragment.addItemList(arrayList, newInstance.setContentDescription(string.concat(sb.toString())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ComprobanteTurnoFragment comprobanteTurnoFragment2 = ComprobanteTurnoFragment.this;
                Data newInstance2 = Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4894_TURNOS_LBL_TIEMPO_ESTIMADO_DE_ESPERA), ComprobanteTurnoFragment.this.c.getTurno().getTiempoEspera());
                String string2 = ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4894_TURNOS_LBL_TIEMPO_ESTIMADO_DE_ESPERA);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(", ");
                sb2.append(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterTime(ComprobanteTurnoFragment.this.c.getTurno().getTiempoEspera()));
                comprobanteTurnoFragment2.addItemList(arrayList, newInstance2.setContentDescription(string2.concat(sb2.toString())));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                ComprobanteTurnoFragment comprobanteTurnoFragment3 = ComprobanteTurnoFragment.this;
                Data newInstance3 = Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4895_TURNOS_LBL_TIPO_DE_TRAMITE), ComprobanteTurnoFragment.this.h);
                String string3 = ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4895_TURNOS_LBL_TIPO_DE_TRAMITE);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(", ");
                sb3.append(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterAmount(Html.fromHtml(ComprobanteTurnoFragment.this.h).toString()));
                comprobanteTurnoFragment3.addItemList(arrayList, newInstance3.setContentDescription(string3.concat(sb3.toString())));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            ComprobanteTurnoFragment comprobanteTurnoFragment4 = ComprobanteTurnoFragment.this;
            Data newInstance4 = Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4881_TURNOS_LBL_SUCURSAL_DE_ATENCION), ComprobanteTurnoFragment.this.d.descripcion);
            String string4 = ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4881_TURNOS_LBL_SUCURSAL_DE_ATENCION);
            StringBuilder sb4 = new StringBuilder();
            sb4.append(", ");
            sb4.append(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyGuion(ComprobanteTurnoFragment.this.d.descripcion));
            comprobanteTurnoFragment4.addItemList(arrayList, newInstance4.setContentDescription(string4.concat(sb4.toString())));
            String applyFilter_BsAs = CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilter_BsAs(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilter_Ciudad(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyGuion(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterDireccion(Html.fromHtml(ComprobanteTurnoFragment.this.d.direccion).toString()))));
            ComprobanteTurnoFragment comprobanteTurnoFragment5 = ComprobanteTurnoFragment.this;
            Data newInstance5 = Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4882_TURNOS_LBL_DIRECCION), ComprobanteTurnoFragment.this.d.direccion);
            String string5 = ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4882_TURNOS_LBL_DIRECCION);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(", ");
            sb5.append(applyFilter_BsAs);
            comprobanteTurnoFragment5.addItemList(arrayList, newInstance5.setContentDescription(string5.concat(sb5.toString())));
            try {
                ComprobanteTurnoFragment comprobanteTurnoFragment6 = ComprobanteTurnoFragment.this;
                Data newInstance6 = Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4899_TURNOS_LBL_FECHA_DE_SOLICITUD_DEL_TICKET), ComprobanteTurnoFragment.this.c.getFechaOperacion());
                String string6 = ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4899_TURNOS_LBL_FECHA_DE_SOLICITUD_DEL_TICKET);
                StringBuilder sb6 = new StringBuilder();
                sb6.append(", ");
                sb6.append(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterDate(ComprobanteTurnoFragment.this.c.getFechaOperacion()));
                comprobanteTurnoFragment6.addItemList(arrayList, newInstance6.setContentDescription(string6.concat(sb6.toString())));
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            try {
                ComprobanteTurnoFragment comprobanteTurnoFragment7 = ComprobanteTurnoFragment.this;
                Data newInstance7 = Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4900_TURNOS_LBL_HORARIO_DE_SOLICITUD), ComprobanteTurnoFragment.this.c.getHoraOperacion());
                String string7 = ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4900_TURNOS_LBL_HORARIO_DE_SOLICITUD);
                StringBuilder sb7 = new StringBuilder();
                sb7.append(", ");
                sb7.append(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterTime(ComprobanteTurnoFragment.this.c.getHoraOperacion()));
                comprobanteTurnoFragment7.addItemList(arrayList, newInstance7.setContentDescription(string7.concat(sb7.toString())));
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            try {
                ComprobanteTurnoFragment comprobanteTurnoFragment8 = ComprobanteTurnoFragment.this;
                Data newInstance8 = Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4885_TURNOS_LBL_NRO_COMPROBANTE), ComprobanteTurnoFragment.this.c.getNroComprobante());
                String applyFilterNumeroGradoBarra = CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterNumeroGradoBarra(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4885_TURNOS_LBL_NRO_COMPROBANTE));
                StringBuilder sb8 = new StringBuilder();
                sb8.append(", ");
                sb8.append(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyNumberCharToChar(ComprobanteTurnoFragment.this.c.getNroComprobante()));
                comprobanteTurnoFragment8.addItemList(arrayList, newInstance8.setContentDescription(applyFilterNumeroGradoBarra.concat(sb8.toString())));
            } catch (Exception e6) {
                e6.printStackTrace();
            }
            ComprobanteTurnoFragment.this.addItemList(arrayList, Footer.newInstance(ComprobanteTurnoFragment.this.c.getListaLegales().getLegalById("SOLIC_TICKET") == null ? "" : ComprobanteTurnoFragment.this.c.getListaLegales().getLegalById("SOLIC_TICKET").getDescripcion()));
            return arrayList;
        }
    }

    public class CreateProductBaja implements Product {
        public CreateProductBaja() {
        }

        public List<IData> getListItem() {
            ArrayList arrayList = new ArrayList();
            ComprobanteTurnoFragment.this.addItemList(arrayList, Title.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4878_TURNOS_TIT_COMPROBANTE_DE_CANCELACION_DE_TICKET)));
            ComprobanteTurnoFragment.this.addItemList(arrayList, Header.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4879_TURNOS_LBL_TICKET_CANCELADO), ComprobanteTurnoFragment.this.i.getNumeroTicket()));
            try {
                ComprobanteTurnoFragment.this.addItemList(arrayList, Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4880_TURNOS_LBL_FECHA_DEL_TICKET), ComprobanteTurnoFragment.this.c.getFechaOperacion()).setContentDescription(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterDate(ComprobanteTurnoFragment.this.c.getFechaOperacion())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ComprobanteTurnoFragment.this.addItemList(arrayList, Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4881_TURNOS_LBL_SUCURSAL_DE_ATENCION), ComprobanteTurnoFragment.this.d.descripcion).setContentDescription(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyGuion(ComprobanteTurnoFragment.this.d.descripcion)));
            ComprobanteTurnoFragment.this.addItemList(arrayList, Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4882_TURNOS_LBL_DIRECCION), ComprobanteTurnoFragment.this.d.direccion).setContentDescription(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilter_BsAs(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilter_Ciudad(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyGuion(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterDireccion(Html.fromHtml(ComprobanteTurnoFragment.this.d.direccion).toString()))))));
            try {
                ComprobanteTurnoFragment.this.addItemList(arrayList, Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4883_TURNOS_LBL_FECHA_DE_CANCELACION), ComprobanteTurnoFragment.this.c.getFechaOperacion()).setContentDescription(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterDate(ComprobanteTurnoFragment.this.c.getFechaOperacion())));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                ComprobanteTurnoFragment.this.addItemList(arrayList, Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4884_TURNOS_LBL_HORARIO_DE_CANCELACION), ComprobanteTurnoFragment.this.c.getHoraOperacion()).setContentDescription(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterHS(ComprobanteTurnoFragment.this.c.getHoraOperacion())));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            ComprobanteTurnoFragment.this.addItemList(arrayList, Data.newInstance(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4885_TURNOS_LBL_NRO_COMPROBANTE), ComprobanteTurnoFragment.this.c.getNroComprobante()).setContentDescription(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyNumberCharToChar(ComprobanteTurnoFragment.this.c.getNroComprobante())).setContentDescriptionLabel(CAccessibility.getInstance(ComprobanteTurnoFragment.this.getContext()).applyFilterNumeroGradoBarra(ComprobanteTurnoFragment.this.getContext().getString(R.string.ID_4885_TURNOS_LBL_NRO_COMPROBANTE))));
            ComprobanteTurnoFragment.this.addItemList(arrayList, Footer.newInstance(ComprobanteTurnoFragment.this.c.getListaLegales().getLegalById("CANCELAR_TICKET") == null ? "" : ComprobanteTurnoFragment.this.c.getListaLegales().getLegalById("CANCELAR_TICKET").getDescripcion()));
            return arrayList;
        }
    }

    public interface Product {
        List<IData> getListItem();
    }

    public static ComprobanteTurnoFragment newInstance(AbmTurnoBodyResponseBean abmTurnoBodyResponseBean, SucursalBean sucursalBean, String str, String str2) {
        ComprobanteTurnoFragment comprobanteTurnoFragment = new ComprobanteTurnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_TURNO, abmTurnoBodyResponseBean);
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_SUCURSAL_SELECCIONADA, sucursalBean);
        bundle.putString(TIPO_COMPROBANTE, str);
        bundle.putString(TIPO_TRAMITE, str2);
        comprobanteTurnoFragment.setArguments(bundle);
        return comprobanteTurnoFragment;
    }

    public static ComprobanteTurnoFragment newInstance(AbmTurnoBodyResponseBean abmTurnoBodyResponseBean, SucursalBean sucursalBean, TurnoBean turnoBean, String str) {
        ComprobanteTurnoFragment comprobanteTurnoFragment = new ComprobanteTurnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_TURNO, abmTurnoBodyResponseBean);
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_SUCURSAL_SELECCIONADA, sucursalBean);
        bundle.putParcelable(UbicacionTurnoFragment.GET_TURNO_CANCELACION, turnoBean);
        bundle.putString(TIPO_COMPROBANTE, str);
        comprobanteTurnoFragment.setArguments(bundle);
        return comprobanteTurnoFragment;
    }

    public static ComprobanteTurnoFragment newInstance(AbmTurnoBodyResponseBean abmTurnoBodyResponseBean, SucursalBean sucursalBean, OpcionPantallaBean opcionPantallaBean, String str) {
        ComprobanteTurnoFragment comprobanteTurnoFragment = new ComprobanteTurnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_TURNO, abmTurnoBodyResponseBean);
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_SUCURSAL_SELECCIONADA, sucursalBean);
        bundle.putParcelable(ConfirmarTurnoFragment.OPCION, opcionPantallaBean);
        bundle.putString(TIPO_COMPROBANTE, str);
        comprobanteTurnoFragment.setArguments(bundle);
        return comprobanteTurnoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.d = (SucursalBean) getArguments().getParcelable(SeleccionSucursalTurnoFragment.GET_SUCURSAL_SELECCIONADA);
            this.c = (AbmTurnoBodyResponseBean) getArguments().getParcelable(SeleccionSucursalTurnoFragment.GET_TURNO);
            this.e = (OpcionPantallaBean) getArguments().getParcelable(ConfirmarTurnoFragment.OPCION);
            this.g = getArguments().getString(TIPO_COMPROBANTE);
            this.i = (TurnoBean) getArguments().getParcelable(UbicacionTurnoFragment.GET_TURNO_CANCELACION);
            this.h = getArguments().getString(TIPO_TRAMITE);
            this.f = (this.i == null ? this.c.getTurno() : this.i).getIdTurno();
        }
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_comprobante_turno, viewGroup, false);
        this.a = inflate.findViewById(R.id.constraintLayoutComprobanteTurno);
        createActionBarSharedreceipt();
        setShareReceiptListener((SantanderRioMainActivity) this.b);
        initUI(inflate);
        this.bus.register(this);
        return inflate;
    }

    public void initUI(View view) {
        ((FormConstraintLayout) this.a).setFooter(true);
        if (this.g.equalsIgnoreCase("A")) {
            configureShareReceipt(this.a, this.c.getNroComprobante(), getContext().getString(R.string.NOMBRE_ARCHIVO_COMPROBANTE));
            this.receiptHasBeenDownloaded = false;
        } else {
            configureShareReceipt(this.a, this.c.getNroComprobante(), getContext().getString(R.string.NOMBRE_ARCHIVO_COMPROBANTE_CANCELACION));
        }
        this.ad = crearLista(this.g).getListItem();
        FormRecyclerView formRecyclerView = (FormRecyclerView) view.findViewById(R.id.recyclerRowsComprobante);
        formRecyclerView.addItemDecoration(new ItemDecoratorList(getContext(), this.ad));
        formRecyclerView.setItemViewCacheSize(this.ad.size());
        formRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        view.findViewById(R.id.proof_of_payment_form_button_back).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ComprobanteTurnoFragment.this.canExit();
            }
        });
        formRecyclerView.setAdapter(new ListAdapter(this.ad, getFragmentManager()));
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.b = getActivity();
    }

    public Product crearLista(String str) {
        if (str.equalsIgnoreCase("A")) {
            return new CreateProductAlta();
        }
        if (str.equalsIgnoreCase("B")) {
            return new CreateProductBaja();
        }
        return null;
    }

    public void addItemList(List<IData> list, IData iData) {
        if (iData != null) {
            list.add(iData);
        }
    }
}
