package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.NumerosUtilesListAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.NumerosUtilesListAdapter.NumerosUtilesListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetNumerosUtilesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.ElementoNumeroUtil;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNumerosUtilesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VNumerosUtiles;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.gson.Gson;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import javax.inject.Inject;

public class NumerosUtilesFragment extends BaseFragment implements NumerosUtilesListener {
    public static String LOG_TAG = "ar.com.santander.rio.mbanking.app.ui.fragments.publico.NumerosUtilesFragment";
    @Inject
    IDataManager a;
    private ArrayList<ElementoNumeroUtil> ad;
    @Inject
    Bus b;
    @Inject
    AnalyticsManager c;
    ListView d;
    NumerosUtilesListAdapter e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;
    @InjectView(2131365984)
    TextView txtViewNumber;

    public void onBackPressed() {
        getActivity().finish();
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.numeros_utiles_fragment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.d = (ListView) inflate.findViewById(R.id.numeros_utiles_list);
        this.f = (TextView) inflate.findViewById(R.id.textViewNumUtilesCall);
        this.g = (TextView) inflate.findViewById(R.id.textViewNumUtilesCopy);
        this.h = (TextView) inflate.findViewById(R.id.textViewNumUtilesSave);
        this.i = (TextView) inflate.findViewById(R.id.textViewNumUtilesCancel);
        this.c.trackScreen(getString(R.string.analytics_screen_name_num_utiles));
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    NumerosUtilesFragment.this.c.trackEvent(NumerosUtilesFragment.this.getString(R.string.analytics_category_num_utiles_llamar), NumerosUtilesFragment.this.txtViewNumber.getText().toString(), NumerosUtilesFragment.this.getString(R.string.analytics_label_num_utiles_llamar));
                    StringBuilder sb = new StringBuilder();
                    sb.append("tel:");
                    sb.append(NumerosUtilesFragment.this.txtViewNumber.getText());
                    String sb2 = sb.toString();
                    Intent intent = new Intent("android.intent.action.DIAL");
                    intent.setData(Uri.parse(sb2));
                    intent.setFlags(268435456);
                    NumerosUtilesFragment.this.getActivity().getApplicationContext().startActivity(intent);
                    NumerosUtilesFragment.this.d.setBackgroundColor(NumerosUtilesFragment.this.getResources().getColor(R.color.white));
                    NumerosUtilesFragment.this.d.setEnabled(true);
                } catch (Throwable unused) {
                    Toast.makeText(NumerosUtilesFragment.this.getActivity().getApplicationContext(), R.string.MSG_ERROR_TELEFONO, 1).show();
                }
            }
        });
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    NumerosUtilesFragment.this.c.trackEvent(NumerosUtilesFragment.this.getString(R.string.analytics_category_num_utiles_llamar), NumerosUtilesFragment.this.txtViewNumber.getText().toString(), NumerosUtilesFragment.this.getString(R.string.analytics_label_num_utiles_guardar));
                    Intent intent = new Intent("android.intent.action.INSERT");
                    intent.setType("vnd.android.cursor.dir/raw_contact");
                    intent.putExtra("phone", NumerosUtilesFragment.this.txtViewNumber.getText());
                    NumerosUtilesFragment.this.startActivity(intent);
                    NumerosUtilesFragment.this.d.setBackgroundColor(NumerosUtilesFragment.this.getResources().getColor(R.color.white));
                    NumerosUtilesFragment.this.d.setEnabled(true);
                } catch (Throwable unused) {
                    Toast.makeText(NumerosUtilesFragment.this.getActivity().getApplicationContext(), R.string.MSG_ERROR_TELEFONO, 1).show();
                }
            }
        });
        this.i.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    NumerosUtilesFragment.this.d.setBackgroundColor(NumerosUtilesFragment.this.getResources().getColor(R.color.white));
                    NumerosUtilesFragment.this.d.setEnabled(true);
                } catch (Throwable unused) {
                    Toast.makeText(NumerosUtilesFragment.this.getActivity().getApplicationContext(), R.string.MSG_ERROR_TELEFONO, 1).show();
                }
            }
        });
        this.ad = new ArrayList<>();
        this.e = new NumerosUtilesListAdapter(getActivity(), this.ad, this.txtViewNumber, this.d);
        this.d.setAdapter(this.e);
        this.e.setNumerosUtilesListener(this);
        super.showProgress(VNumerosUtiles.nameService);
        this.a.getNumerosUtiles();
        y();
        return inflate;
    }

    public void onStop() {
        super.onStop();
    }

    @Subscribe
    public void onGetNumerosUtiles(GetNumerosUtilesEvent getNumerosUtilesEvent) {
        JSONObject jSONObject;
        ElementoNumeroUtil elementoNumeroUtil;
        Log.i(LOG_TAG, "onGetNumerosUtiles");
        super.dismissProgress();
        if (getNumerosUtilesEvent == null) {
            Log.e(LOG_TAG, "getNumerosUtilesEvent nulo");
        } else if (getNumerosUtilesEvent.getResult() == TypeResult.OK) {
            Log.i(LOG_TAG, "ResultadoProd servicio OK");
            try {
                JSONArray jSONArray = ((GetNumerosUtilesResponseBean) getNumerosUtilesEvent.getBeanResponse()).getJsonElement().getJSONArray("datoUtil");
                if (jSONArray != null) {
                    new ArrayList();
                    Gson gson = new Gson();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        jSONObject = jSONArray.getJSONObject(i2);
                        elementoNumeroUtil = (ElementoNumeroUtil) gson.fromJson(jSONObject.toString(), ElementoNumeroUtil.class);
                        if (elementoNumeroUtil.getDescripDatoUtil() != null) {
                            elementoNumeroUtil.setDescripDatoUtil(Html.fromHtml(elementoNumeroUtil.getDescripDatoUtil()).toString());
                        }
                        if (elementoNumeroUtil.getDescripAdicDatoUtil() != null) {
                            elementoNumeroUtil.setDescripAdicDatoUtil(Html.fromHtml(elementoNumeroUtil.getDescripAdicDatoUtil()).toString());
                        }
                        JSONArray jSONArray2 = jSONObject.getJSONObject("telsDatosUtiles").getJSONObject("telsDatoUtil").getJSONArray("telDatoUtil");
                        if (jSONArray2 != null) {
                            elementoNumeroUtil.setTel1DatoUtil(jSONArray2.getJSONObject(0).getString("content"));
                            if (jSONArray2.length() > 1) {
                                elementoNumeroUtil.setTel2DatoUtil(jSONArray2.getJSONObject(1).getString("content"));
                            } else {
                                elementoNumeroUtil.setTel2DatoUtil(null);
                            }
                        } else {
                            elementoNumeroUtil.setTel1DatoUtil(null);
                            elementoNumeroUtil.setTel2DatoUtil(null);
                        }
                        this.ad.add(elementoNumeroUtil);
                        this.ad.trimToSize();
                        this.e.notifyDataSetChanged();
                    }
                }
            } catch (JSONException unused) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("telsDatosUtiles").getJSONObject("telsDatoUtil").getJSONObject("telDatoUtil");
                if (jSONObject2 != null) {
                    elementoNumeroUtil.setTel1DatoUtil(jSONObject2.getString("content"));
                }
            } catch (Throwable th) {
                Log.e("", th.toString());
            }
        } else if (getErrorListener() != null) {
            ((SantanderRioMainActivity) getActivity()).setErrorFragment(getString(R.string.MSG_USER000002_General_errorNoconexion), getString(R.string.ID5_HOME_BTN_PHONENUMBERS));
        }
    }

    private void y() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.CALL_PHONE") != 0) {
            showRequestPermissionExplation(1);
        }
    }

    public void showRequestPermissionExplation(final int i2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.call_phone_permission_request_message), null, getString(R.string.BTN_DIALOG_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                NumerosUtilesFragment.this.requestPermissions(new String[]{"android.permission.CALL_PHONE"}, i2);
            }
        });
        newInstance.show(getFragmentManager(), OptionsToShareImpl.PERMISSION_DIALOG_TAG);
    }

    public void onPhoneSelected(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("tel:");
        sb.append(str);
        String sb2 = sb.toString();
        Intent intent = new Intent("android.intent.action.DIAL");
        intent.setData(Uri.parse(sb2));
        intent.setFlags(268435456);
        getActivity().getApplicationContext().startActivity(intent);
        this.c.trackEvent(getString(R.string.analytics_category_num_utiles_llamar), str, getString(R.string.analytics_label_num_utiles_llamar));
    }
}
