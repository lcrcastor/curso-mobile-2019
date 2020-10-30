package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.app.module.buzonNotifPush.BuzonNotifPushPresenter;
import ar.com.santander.rio.mbanking.app.module.buzonNotifPush.BuzonNotifPushView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.NotificationWebViewActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.NotificacionesPushAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.NotificacionesPushAdapter.OnItemClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.NotificacionPushBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import me.leolin.shortcutbadger.ShortcutBadger;

public class BuzonNotifPushFragment extends BaseMvpFragment implements BuzonNotifPushView, OnItemClickListener {
    @Inject
    AnalyticsManager a;
    /* access modifiers changed from: private */
    public boolean ad = false;
    private final String b = "S";
    private final String c = "N";
    private View d;
    /* access modifiers changed from: private */
    public BuzonNotifPushPresenter e;
    private List<NotificacionPushBean> f = new ArrayList();
    private NotificacionesPushAdapter g;
    /* access modifiers changed from: private */
    public int h = 1;
    /* access modifiers changed from: private */
    public String i = "S";
    @InjectView(2131363678)
    TextView lblSinNotificaciones;
    @InjectView(2131363680)
    LinearLayout lnlSinNotificaciones;
    @InjectView(2131363681)
    RecyclerView lstNotifcaciones;

    public void clearScreenData() {
    }

    public Context getFragmentContext() {
        return getActivity();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.d = layoutInflater.inflate(R.layout.fragment_buzon_notif_push, viewGroup, false);
        ButterKnife.inject((Object) this, this.d);
        configureActionBar();
        initialize();
        configureLayout();
        y();
        return this.d;
    }

    public void initialize() {
        this.a.trackScreen(getString(R.string.analytics_screen_buzon_novedades_push));
        this.e = new BuzonNotifPushPresenter(this.mBus, this.mDataManager);
        attachView();
        z();
        if (TextUtils.isEmpty(TwinPushSDK.getInstance(getActivity().getApplicationContext()).getDeviceId())) {
            setBuzonNotifPushView(new ArrayList(), "N", "");
        } else {
            this.e.callGetNotificacionesPush(this.h, true);
        }
    }

    private void y() {
        ShortcutBadger.applyCount(getFragmentContext(), 0);
        Context applicationContext = getFragmentContext().getApplicationContext();
        String str = Constants.PUSH_PREFERENCES;
        getFragmentContext();
        Editor edit = applicationContext.getSharedPreferences(str, 0).edit();
        edit.putInt(Constants.ID_NOTIFICACION_INDEX, 0);
        edit.commit();
    }

    private void z() {
        this.lstNotifcaciones.setHasFixedSize(true);
        this.lstNotifcaciones.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.lstNotifcaciones.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
            }

            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom() - (recyclerView.getHeight() + recyclerView.getScrollY()) == 0 && BuzonNotifPushFragment.this.i.equalsIgnoreCase("S") && !BuzonNotifPushFragment.this.ad) {
                    BuzonNotifPushFragment.this.h = BuzonNotifPushFragment.this.h + 1;
                    BuzonNotifPushFragment.this.ad = true;
                    BuzonNotifPushFragment.this.e.callGetNotificacionesPush(BuzonNotifPushFragment.this.h, false);
                }
            }
        });
        this.f.clear();
        this.f.add(new NotificacionPushBean());
        this.g = new NotificacionesPushAdapter(getActivity(), this.f);
        this.g.setOnItemClickListener(this);
        this.lstNotifcaciones.setAdapter(this.g);
    }

    public void configureLayout() {
        this.lblSinNotificaciones.setText(Html.fromHtml(getString(R.string.ID4003_F28_00_LBL_SIN_NOTIFICACIONES)));
    }

    public void setBuzonNotifPushView(List<NotificacionPushBean> list, String str, String str2) {
        this.i = str;
        this.ad = false;
        if (this.h == 1 && list.size() == 0) {
            this.lnlSinNotificaciones.setVisibility(0);
            this.lstNotifcaciones.setVisibility(8);
            if (TextUtils.isEmpty(str2)) {
                this.lblSinNotificaciones.setText(Html.fromHtml(getString(R.string.ID4003_F28_00_LBL_SIN_NOTIFICACIONES)));
            } else {
                this.lblSinNotificaciones.setText(Html.fromHtml(str2));
            }
        } else {
            if (list.size() > 0) {
                this.f.addAll(this.f.size() - 1, list);
            }
            if (this.i.equalsIgnoreCase("N")) {
                this.f.remove(this.f.size() - 1);
            }
            this.lnlSinNotificaciones.setVisibility(8);
            this.lstNotifcaciones.setVisibility(0);
            this.g.notifyDataSetChanged();
        }
    }

    public void onItemClick(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.white));
        TextView textView = (TextView) view.findViewById(R.id.lbl_mensaje);
        if (textView != null) {
            textView.setTypeface(Typeface.createFromAsset(getFragmentContext().getAssets(), "fonts/OpenSans-Regular.otf"));
        }
        a((NotificacionPushBean) this.f.get(this.lstNotifcaciones.getChildPosition(view)));
    }

    private void a(NotificacionPushBean notificacionPushBean) {
        this.a.trackEvent(getString(R.string.analytics_category_buzon_novedades_push), getString(R.string.analytics_event_action_buzon_novedades_push_click_novedad), notificacionPushBean.getIdNotificacion());
        PushNotification pushNotification = new PushNotification();
        pushNotification.setId(notificacionPushBean.getIdNotificacion());
        TwinPushSDK.getInstance(getFragmentContext()).onNotificationOpen(pushNotification);
        Intent intent = new Intent(getFragmentContext(), NotificationWebViewActivity.class);
        intent.putExtra(NotificationWebViewActivity.URL_NOTIFICATION_PARAM, notificacionPushBean.getDetalleMensaje());
        intent.putExtra(NotificationWebViewActivity.ID_NOTIFICATION_PARAM, notificacionPushBean.getIdNotificacion());
        if (!TextUtils.isEmpty(notificacionPushBean.getSegmento())) {
            intent.putExtra(NotificationWebViewActivity.SEGMENT_PARAM, notificacionPushBean.getSegmento());
        }
        startActivity(intent);
    }

    public void onBackPressed() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void configureActionBar() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = ((BaseActivity) getActivity()).getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) this.mActionBar.findViewById(R.id.back_imgButton);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    BuzonNotifPushFragment.this.onBackPressed();
                }
            });
        }
    }

    public void attachView() {
        if (!this.e.isViewAttached()) {
            this.e.attachView(this);
        }
    }

    public void detachView() {
        if (this.e.isViewAttached()) {
            this.e.detachView();
        }
    }
}
