package ar.com.santander.rio.mbanking.app.module.comodinessuperclub;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.CanjearPuntosSuperClubEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCanjearPuntosSuperClub;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class CouponDetailSuperClubPresenter extends BasePresenter<CouponDetailSuperClubView> {
    protected Context mContext;

    public CouponDetailSuperClubPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void onCanjearComodin(CuponSuperClubBean cuponSuperClubBean) {
        ((CouponDetailSuperClubView) getBaseView()).showProgressIndicator(VCanjearPuntosSuperClub.nameService);
        this.mDataManager.canjearPuntosSuperClub(cuponSuperClubBean.idCupon, cuponSuperClubBean.grupo, cuponSuperClubBean.segmento, cuponSuperClubBean.puntos, cuponSuperClubBean.idPunto, cuponSuperClubBean.idPartner);
    }

    @Subscribe
    public void onCanjearPuntosSuperClub(CanjearPuntosSuperClubEvent canjearPuntosSuperClubEvent) {
        ((CouponDetailSuperClubView) getBaseView()).dismissProgressIndicator();
        final CanjearPuntosSuperClubEvent canjearPuntosSuperClubEvent2 = canjearPuntosSuperClubEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.COMODINES_SUPERCLUB, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                CouponDetailSuperClubPresenter.this.a(canjearPuntosSuperClubEvent2);
            }
        };
        r1.handleWSResponse(canjearPuntosSuperClubEvent);
    }

    /* access modifiers changed from: private */
    public void a(CanjearPuntosSuperClubEvent canjearPuntosSuperClubEvent) {
        try {
            ((CouponDetailSuperClubView) getBaseView()).showComodinReceipt(canjearPuntosSuperClubEvent.getResponse().canjearPuntosBodyResponseBean.nroComprobante, canjearPuntosSuperClubEvent.getResponse().canjearPuntosBodyResponseBean.fechaCanje, canjearPuntosSuperClubEvent.getResponse().canjearPuntosBodyResponseBean.listaLeyendas.leyenda);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCanjearPuntosSuperClubResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void showCouponDetail() {
        ((CouponDetailSuperClubView) getBaseView()).showCouponDetail();
    }
}
