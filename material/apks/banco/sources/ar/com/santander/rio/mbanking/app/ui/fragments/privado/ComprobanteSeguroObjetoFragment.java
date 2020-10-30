package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.base.ShareReceiptPolizaListener;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter;
import ar.com.santander.rio.mbanking.app.ui.forms.FormConstraintLayout;
import ar.com.santander.rio.mbanking.app.ui.forms.FormRecyclerView;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormData;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataButton;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataHeader;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataLeyend;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataText;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataTitle;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormData;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ItemDecoratorForm;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter.OnButtonClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetPolizaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPoliza;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ComprobanteSeguroObjetoFragment extends ITRSABaseFragment implements ShareReceiptPolizaListener {
    public static final String CONTRATAR_SEGURO_OBJETO = "CONTRATAR_SEGURO_OBJETO";
    ArrayList<String> a = new ArrayList<>();
    private GetCotizacionSeguroObjetoBodyResponseBean ad;
    private ContratarSeguroObjetoBodyResponseBean ae;
    private View af;
    @Inject
    public AnalyticsManager analyticsManager;
    private OnFragmentInteractionListener b;
    private List<IFormData> c = new ArrayList();
    private List<IFormData> d = new ArrayList();
    private List<IFormData> e = new ArrayList();
    private FamiliaBean f;
    private ImageView g;
    private FormAdapter h;
    private FormRecyclerView i;
    @Inject
    public IDataManager mDataManager;

    public interface OnFragmentInteractionListener extends IFragmentBase {
    }

    private void z() {
    }

    public static ComprobanteSeguroObjetoFragment newInstance(ContratarSeguroObjetoBodyResponseBean contratarSeguroObjetoBodyResponseBean, List<IFormData> list, List<IFormData> list2, FamiliaBean familiaBean, GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean) {
        ComprobanteSeguroObjetoFragment comprobanteSeguroObjetoFragment = new ComprobanteSeguroObjetoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ConfirmarSeguroObjetosFragment.IFORM_DATA_LIST, (ArrayList) list);
        bundle.putParcelableArrayList(ConfirmarSeguroObjetosFragment.IFORM_DATA_PREGUNTAS, (ArrayList) list2);
        bundle.putParcelable("FAMILIA_BEAN_SELECTED", familiaBean);
        bundle.putParcelable(CompletarSeguroObjetosFragment.COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN, getCotizacionSeguroObjetoBodyResponseBean);
        bundle.putParcelable(CONTRATAR_SEGURO_OBJETO, contratarSeguroObjetoBodyResponseBean);
        comprobanteSeguroObjetoFragment.setArguments(bundle);
        return comprobanteSeguroObjetoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.e = getArguments().getParcelableArrayList(ConfirmarSeguroObjetosFragment.IFORM_DATA_LIST);
            this.d = getArguments().getParcelableArrayList(ConfirmarSeguroObjetosFragment.IFORM_DATA_PREGUNTAS);
            this.f = (FamiliaBean) getArguments().getParcelable("FAMILIA_BEAN_SELECTED");
            this.ad = (GetCotizacionSeguroObjetoBodyResponseBean) getArguments().getParcelable(CompletarSeguroObjetosFragment.COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN);
            this.ae = (ContratarSeguroObjetoBodyResponseBean) getArguments().getParcelable(CONTRATAR_SEGURO_OBJETO);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_comprobante_seguro_objeto, viewGroup, false);
        b(inflate);
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.analyticsManager.trackScreen(Screens.insuranceVoucher(this.f.getNombreFamilia()));
    }

    private void b(View view) {
        c(view);
        y();
        z();
    }

    private void c(View view) {
        this.af = view.findViewById(R.id.constraintLayout);
        ((FormConstraintLayout) this.af).setFooter(true);
        View view2 = this.af;
        String numCertificado = this.ae.getNumCertificado();
        StringBuilder sb = new StringBuilder();
        sb.append(getActivity().getString(R.string.message_share_seguro_objeto));
        sb.append(this.ae.getNumPoliza());
        configureShareReceiptPoliza(view2, numCertificado, sb.toString());
        setShareReceiptPolizaListener(this);
        this.i = (FormRecyclerView) view.findViewById(R.id.family_questions_form);
        this.i.setItemViewCacheSize(this.c.size());
        this.i.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        this.g = (ImageView) view.findViewById(R.id.img_checkbox_tyc);
    }

    @SuppressLint({"StringFormatInvalid"})
    private void y() {
        this.c.addAll(a(this.e, this.d));
        this.i.addItemDecoration(new ItemDecoratorForm(getContext(), this.c));
        this.h = new FormAdapter(this.c, getFragmentManager(), getContext());
        this.h.setOnButtonClickListener(new OnButtonClickListener() {
            public void setActionButtton() {
                ComprobanteSeguroObjetoFragment.this.canExit();
            }
        });
        this.i.setAdapter(this.h);
    }

    public FormData getField(String str, String str2, String str3, String str4, boolean z, String str5) {
        Integer num;
        int i2;
        boolean equalsIgnoreCase = str5.equalsIgnoreCase("N");
        if (str.equals("01")) {
            if (str4 != null) {
                try {
                    if (!str4.isEmpty()) {
                        i2 = Integer.parseInt(str4);
                        num = Integer.valueOf(i2);
                        FormData formData = (FormData) FormDataText.newInstance(Constants.LOCALE_DEFAULT_APP).setMaxOfCharacters(num).setRequired(Boolean.valueOf(equalsIgnoreCase)).setEditable(Boolean.valueOf(z)).setLabelText(str2);
                        if (!z && str3 != null) {
                            formData.setValueText(str3);
                        }
                        return formData;
                    }
                } catch (Exception unused) {
                    num = Integer.valueOf(100);
                }
            }
            i2 = 100;
            num = Integer.valueOf(i2);
            FormData formData2 = (FormData) FormDataText.newInstance(Constants.LOCALE_DEFAULT_APP).setMaxOfCharacters(num).setRequired(Boolean.valueOf(equalsIgnoreCase)).setEditable(Boolean.valueOf(z)).setLabelText(str2);
            formData2.setValueText(str3);
            return formData2;
        } else if (str.equals("05")) {
            return (FormDataLeyend) FormDataLeyend.newInstance(Constants.LOCALE_DEFAULT_APP).setValueText(str2);
        } else {
            return null;
        }
    }

    @SuppressLint({"StringFormatInvalid"})
    private List<IFormData> a(List<IFormData> list, List<IFormData> list2) {
        String descripcion = this.ad.cotizacion.getListaLeyendas().getLeyendaById("SEGOBJ_CONTRA").getDescripcion();
        String concat = descripcion.concat("<br/>").concat(this.ad.cotizacion.getListaLeyendas().getLeyendaById("SEGOBJ_COMP").getDescripcion());
        ArrayList arrayList = new ArrayList();
        arrayList.add(FormDataTitle.newInstance().setTitle(getString(R.string.ID_4151_SEGUROS_LBL_COMP_CONT_SGRO, Html.fromHtml(this.f.getNombreFamilia()))));
        arrayList.add(FormDataHeader.newInstance().setTitle(Html.fromHtml(this.f.getNombreFamilia()).toString()).setDescription("").setIconUrl(this.f.getImagenDetalle()));
        arrayList.addAll(list);
        a((List<IFormData>) arrayList, "Medio de Pago", (IFormData) getField("01", "Póliza / Certificado", this.ae.getNumPoliza().concat("/").concat(this.ae.getNumCertificado()), null, false, "N"));
        a((List<IFormData>) arrayList, "Póliza / Certificado", (IFormData) getField("01", "Fecha de Inicio", this.ae.getFechaInicio(), null, false, "N"));
        a((List<IFormData>) arrayList, "Alias", list2);
        a((List<IFormData>) arrayList, (IFormData) getField("05", concat, null, null, false, "N"));
        arrayList.add(FormDataButton.newInstance().setText(getContext().getString(R.string.ID_4166_SEGUROS_LBL_VOLVER)).setEnableButton(true));
        return arrayList;
    }

    private void a(List<IFormData> list, String str, List<IFormData> list2) {
        boolean z = false;
        for (int i2 = 0; i2 < list.size(); i2++) {
            IFormData iFormData = (IFormData) list.get(i2);
            if (!z && iFormData.getLabelText().equalsIgnoreCase(str)) {
                list.addAll(i2 + 1, list2);
                z = true;
            }
        }
    }

    private void a(List<IFormData> list, IFormData iFormData) {
        list.add(list.size(), iFormData);
    }

    private void a(List<IFormData> list, String str, IFormData iFormData) {
        boolean z = false;
        for (int i2 = 0; i2 < list.size(); i2++) {
            IFormData iFormData2 = (IFormData) list.get(i2);
            if (!z && iFormData2.getLabelText().equalsIgnoreCase(str)) {
                list.add(i2, iFormData);
                z = true;
            }
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.b = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.b = null;
    }

    @Subscribe
    public void getPoliza(GetPolizaEvent getPolizaEvent) {
        dismissProgress();
        final GetPolizaEvent getPolizaEvent2 = getPolizaEvent;
        AnonymousClass2 r0 = new BaseWSResponseHandler(getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, (BaseActivity) getContext(), getContext().getString(R.string.f182ID_4078_SEGUROS_LABEL_COMPROBANTEDECONTRATACINDESEGURO)) {
            /* access modifiers changed from: protected */
            public void onOk() {
                try {
                    if (UtilFile.canDisplayPdf(ComprobanteSeguroObjetoFragment.this.getContext())) {
                        UtilFile.showPdf(UtilFile.saveFileFromBase64(getPolizaEvent2.getResponse().getPolizaBodyResponseBean.getArchivoPoliza(), String.format(SegurosConstants.POLIZA_FILE_NAME, new Object[]{getPolizaEvent2.getResponse().getPolizaBodyResponseBean.getArchivoNombre()}), ComprobanteSeguroObjetoFragment.this.getContext()), ComprobanteSeguroObjetoFragment.this.getContext());
                        return;
                    }
                    ComprobanteSeguroObjetoFragment.this.showNoPdfApplicationPopUp(ComprobanteSeguroObjetoFragment.this.getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        r0.handleWSResponse(getPolizaEvent);
    }

    public void onClikPoliza() {
        showProgress(VGetPoliza.nameService);
        this.mDataManager.getPoliza(this.ad.cotizacion.getCodRamo(), this.ae.getNumPoliza(), this.ae.getNumCertificado());
    }
}
