package ar.com.santander.rio.mbanking.app.base;

import ar.com.santander.rio.mbanking.app.base.IBaseView;

public interface IBasePresenter<V extends IBaseView> {
    void attachView(V v);

    void detachView();
}
