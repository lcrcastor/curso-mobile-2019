package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ItemDecoratorList;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.AvisoRojoClaro;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.IData;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.ImageDataTurnoCaja;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.SubTitle;
import ar.com.santander.rio.mbanking.app.ui.list_forms.fields.Title;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCircuitoTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTurnoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkRetiroPiezaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OpcionPantallaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PantallaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SucursalBean;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class SolicitarTurnoCajaFragment extends BaseFragment {
    public static final String GET_CIRCUITO_TURNO_BODY_RESPONSE_BEAN = "GET_CIRCUITO_TURNO_BODY_RESPONSE_BEAN";
    public static final String INITIAL_DISPLAY = "INITIAL_DISPLAY";
    public static final String OPCION_SELECCIONADA = "OPCION_SELECCIONADA";
    public static final String SUCURSAL_BEAN = "SUCURSAL_BEAN";
    @Inject
    SessionManager a;
    private Context ad;
    private FragmentActivity ae;
    /* access modifiers changed from: private */
    public SucursalBean af;
    private boolean ag = true;
    private List<IData> ah = new ArrayList();
    /* access modifiers changed from: private */
    public GetCircuitoTurnoBodyResponseBean b;
    /* access modifiers changed from: private */
    public GetTurnoBodyResponseBean c;
    private View d;
    private ListAdapter e;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener f;
    /* access modifiers changed from: private */
    public OpcionPantallaBean g;
    private RecyclerView h;
    /* access modifiers changed from: private */
    public PantallaBean i;

    public interface OnFragmentInteractionListener extends IFragmentBase {
        void finishActivity();
    }

    public static SolicitarTurnoCajaFragment newInstance(GetCircuitoTurnoBodyResponseBean getCircuitoTurnoBodyResponseBean, SucursalBean sucursalBean, GetTurnoBodyResponseBean getTurnoBodyResponseBean) {
        SolicitarTurnoCajaFragment solicitarTurnoCajaFragment = new SolicitarTurnoCajaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GET_CIRCUITO_TURNO_BODY_RESPONSE_BEAN, getCircuitoTurnoBodyResponseBean);
        bundle.putParcelable(SUCURSAL_BEAN, sucursalBean);
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_TURNO, getTurnoBodyResponseBean);
        bundle.putBoolean(INITIAL_DISPLAY, true);
        solicitarTurnoCajaFragment.setArguments(bundle);
        return solicitarTurnoCajaFragment;
    }

    public static SolicitarTurnoCajaFragment newInstance(GetCircuitoTurnoBodyResponseBean getCircuitoTurnoBodyResponseBean, OpcionPantallaBean opcionPantallaBean, SucursalBean sucursalBean, GetTurnoBodyResponseBean getTurnoBodyResponseBean) {
        SolicitarTurnoCajaFragment solicitarTurnoCajaFragment = new SolicitarTurnoCajaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GET_CIRCUITO_TURNO_BODY_RESPONSE_BEAN, getCircuitoTurnoBodyResponseBean);
        bundle.putParcelable(OPCION_SELECCIONADA, opcionPantallaBean);
        bundle.putParcelable(SUCURSAL_BEAN, sucursalBean);
        bundle.putParcelable(SeleccionSucursalTurnoFragment.GET_TURNO, getTurnoBodyResponseBean);
        bundle.putBoolean(INITIAL_DISPLAY, false);
        solicitarTurnoCajaFragment.setArguments(bundle);
        return solicitarTurnoCajaFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.b = (GetCircuitoTurnoBodyResponseBean) getArguments().getParcelable(GET_CIRCUITO_TURNO_BODY_RESPONSE_BEAN);
            this.g = (OpcionPantallaBean) getArguments().getParcelable(OPCION_SELECCIONADA);
            this.ag = getArguments().getBoolean(INITIAL_DISPLAY);
            if (this.g == null || this.g.getIdProximaPantalla() == null || this.g.getIdProximaPantalla().isEmpty()) {
                this.i = (PantallaBean) this.b.getListaPantallas().getListaPantallas().get(0);
            } else {
                this.i = this.b.getListaPantallas().getPantallaById(this.g.getIdProximaPantalla());
            }
            this.af = (SucursalBean) getArguments().getParcelable(SUCURSAL_BEAN);
            this.c = (GetTurnoBodyResponseBean) getArguments().getParcelable(SeleccionSucursalTurnoFragment.GET_TURNO);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.d = layoutInflater.inflate(R.layout.fragment_solicitar_turno_caja, viewGroup, false);
        if (this.ah.size() == 0 && this.i != null) {
            this.ah.addAll(getListItemGeneric(this.i.getListaOpciones().getOpcion(), this.b.getLinkRetiroPieza()));
        } else if (this.ah.size() == 0 && this.g != null && this.g.getAccion().equalsIgnoreCase("M")) {
            this.ah.addAll(getItemTitle());
        }
        y();
        return this.d;
    }

    private void y() {
        this.h = (RecyclerView) this.d.findViewById(R.id.list_form);
        this.h.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        this.h.addItemDecoration(new ItemDecoratorList(getContext(), this.ah));
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        if (this.g == null || !this.g.getAccion().equalsIgnoreCase("M")) {
            this.e = new ListAdapter(this.ah, getFragmentManager(), getContext(), this.a);
            this.e.setmOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(int i, int i2) {
                    OpcionPantallaBean opcionPantallaBean = (OpcionPantallaBean) SolicitarTurnoCajaFragment.this.i.getListaOpciones().getOpcion().get(i2);
                    if (opcionPantallaBean.getAccion().equalsIgnoreCase("O")) {
                        SolicitarTurnoCajaFragment fragmentPantallaForId = SolicitarTurnoCajaFragment.this.getFragmentPantallaForId(opcionPantallaBean, SolicitarTurnoCajaFragment.this.b);
                        if (fragmentPantallaForId != null) {
                            SolicitarTurnoCajaFragment.this.f.changeFragment(fragmentPantallaForId, FragmentConstants.NERS_CONFIRMAR_TURNO);
                        } else {
                            SolicitarTurnoCajaFragment.this.showErrorNoFound();
                        }
                    } else if (opcionPantallaBean.getAccion().equalsIgnoreCase("T")) {
                        SolicitarTurnoCajaFragment.this.f.changeFragment(ConfirmarTurnoFragment.newInstance(SolicitarTurnoCajaFragment.this.c, SolicitarTurnoCajaFragment.this.af, opcionPantallaBean, SolicitarTurnoCajaFragment.this.b, false), FragmentConstants.NERS_CONFIRMAR_TURNO);
                    } else if (opcionPantallaBean.getAccion().equalsIgnoreCase("M")) {
                        SolicitarTurnoCajaFragment.this.f.changeFragment(SolicitarTurnoCajaFragment.newInstance(SolicitarTurnoCajaFragment.this.b, opcionPantallaBean, SolicitarTurnoCajaFragment.this.af, SolicitarTurnoCajaFragment.this.c), FragmentConstants.NERS_CONFIRMAR_TURNO);
                    }
                }

                public void onFooterLinkClick() {
                    SolicitarTurnoCajaFragment.this.f.changeFragment(ConfirmarTurnoFragment.newInstance(SolicitarTurnoCajaFragment.this.c, SolicitarTurnoCajaFragment.this.af, null, SolicitarTurnoCajaFragment.this.b, true), FragmentConstants.NERS_CONFIRMAR_TURNO);
                }
            });
            this.h.setAdapter(this.e);
            return;
        }
        errorTransaction();
    }

    public String StringRemoveLastCharacter(String str, char c2) {
        return (str == null || str.length() <= 0 || str.charAt(str.length() + -1) != c2) ? str : str.substring(0, str.length() - 1);
    }

    public SolicitarTurnoCajaFragment getFragmentPantallaForId(OpcionPantallaBean opcionPantallaBean, GetCircuitoTurnoBodyResponseBean getCircuitoTurnoBodyResponseBean) {
        for (PantallaBean idPantalla : getCircuitoTurnoBodyResponseBean.getListaPantallas().getListaPantallas()) {
            if (idPantalla.getIdPantalla().equalsIgnoreCase(opcionPantallaBean.getIdProximaPantalla())) {
                return newInstance(getCircuitoTurnoBodyResponseBean, opcionPantallaBean, this.af, this.c);
            }
        }
        return null;
    }

    public List<IData> getItemTitle() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Title.newInstance().setTitle(getContext().getString(R.string.ID_4865_TURNOS_TIT_SOLICITAR_TICKET_PARA_ATENCION_EN_CAJA)));
        return arrayList;
    }

    public List<IData> getListItemGeneric(List<OpcionPantallaBean> list, LinkRetiroPiezaBean linkRetiroPiezaBean) {
        String str;
        ArrayList arrayList = new ArrayList();
        arrayList.add(Title.newInstance().setTitle(getContext().getString(R.string.ID_4865_TURNOS_TIT_SOLICITAR_TICKET_PARA_ATENCION_EN_CAJA)));
        if (this.ag) {
            str = getContext().getString(R.string.ID_4905_TURNOS_LBL_SELECCIONA_EL_TIPO_DE_TRAMITE_QUE_QUERES_REALIZAR);
        } else {
            String StringRemoveLastCharacter = StringRemoveLastCharacter(this.g.getNombre().toLowerCase(), 's');
            StringBuilder sb = new StringBuilder();
            sb.append("Indica el tipo de ");
            sb.append(StringRemoveLastCharacter);
            sb.append(" que quéres realizar.");
            str = sb.toString();
        }
        arrayList.add(SubTitle.newInstance().setSubTitle(str));
        if (!linkRetiroPiezaBean.getMostrar().equalsIgnoreCase("N") && this.ag) {
            AvisoRojoClaro label = AvisoRojoClaro.newInstance().setTitle(Html.fromHtml(linkRetiroPiezaBean.getResTitulo()).toString()).setSpanable(this.ad.getString(R.string.ID_4893_TURNOS_LBL_SOLICITAR_TICKET_PARA_RETIRARLA)).setLabel(Html.fromHtml(linkRetiroPiezaBean.getResDesc()).toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Html.fromHtml(linkRetiroPiezaBean.getResTitulo()).toString());
            sb2.append(",, ");
            sb2.append(Html.fromHtml(linkRetiroPiezaBean.getResDesc()).toString());
            sb2.append(",  ");
            sb2.append(this.ad.getString(R.string.ID_4893_TURNOS_LBL_SOLICITAR_TICKET_PARA_RETIRARLA));
            arrayList.add(label.setContentDescription(sb2.toString()));
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            OpcionPantallaBean opcionPantallaBean = (OpcionPantallaBean) list.get(i2);
            try {
                arrayList.add(ImageDataTurnoCaja.newInstance().setPositionItem(i2).setImageResourceURL(opcionPantallaBean.getImagen()).setLabel(opcionPantallaBean.getNombre()).setContentDescription(Html.fromHtml(opcionPantallaBean.getNombre()).toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return arrayList;
    }

    public void showErrorNoFound() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000004), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }
        });
        newInstance.show(getFragmentManager(), "NoFound");
    }

    public void errorTransaction() {
        Button button = (Button) this.d.findViewById(R.id.btn_red_back);
        Button button2 = (Button) this.d.findViewById(R.id.btn_gray_continue);
        View findViewById = this.d.findViewById(R.id.error_transaction);
        HtmlTextView htmlTextView = (HtmlTextView) findViewById.findViewById(R.id.title_error);
        ImageView imageView = (ImageView) findViewById.findViewById(R.id.image_error);
        TextView textView = (TextView) findViewById.findViewById(R.id.description_error);
        View findViewById2 = this.d.findViewById(R.id.seprarador_list_form);
        this.e = new ListAdapter(this.ah, getFragmentManager(), getContext(), this.a);
        this.h.setAdapter(this.e);
        findViewById2.setVisibility(8);
        findViewById.setVisibility(0);
        button.setVisibility(0);
        htmlTextView.setText(this.g.getMensaje().getResTitulo());
        imageView.setBackgroundResource(R.drawable.campana);
        String[] split = Html.fromHtml(this.g.getMensaje().getResDesc()).toString().split("\\.");
        String concat = split[0].concat(".\n\n");
        StringBuilder sb = new StringBuilder();
        sb.append(split[1]);
        sb.append(".");
        SpannableString spannableString = new SpannableString(concat.concat(sb.toString()));
        spannableString.setSpan(new StyleSpan(R.style.generic_fontSemibold), 0, split[0].length(), 0);
        textView.setText(spannableString);
        button.setText(getContext().getString(R.string.ID_4903_TURNOS_BTN_VOLVER_AL_INICIO));
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SolicitarTurnoCajaFragment.this.f.finishActivity();
            }
        });
        if (this.g.getMensaje().getMostrarSolictarTurno().equalsIgnoreCase("S")) {
            button2.setVisibility(0);
            button2.setText(getContext().getString(R.string.ID_4904_TURNOS_BTN_CONTINUAR_Y_SOLICITAR_TICKET));
            button2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SolicitarTurnoCajaFragment.this.f.changeFragment(ConfirmarTurnoFragment.newInstance(SolicitarTurnoCajaFragment.this.c, SolicitarTurnoCajaFragment.this.af, SolicitarTurnoCajaFragment.this.g, SolicitarTurnoCajaFragment.this.b, false), FragmentConstants.NERS_CONFIRMAR_TURNO);
                }
            });
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.f = (OnFragmentInteractionListener) context;
            this.ad = context;
            this.ae = getActivity();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.f = null;
    }
}
