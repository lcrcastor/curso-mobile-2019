package ar.com.santander.rio.mbanking.app.base;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public abstract class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {
    protected T mBaseView;
    protected Bus mBus;
    protected IDataManager mDataManager;

    public static class BaseViewNotAttachedException extends RuntimeException {
        public BaseViewNotAttachedException() {
            super("Please call Presenter.attachView(baseView) before requesting data to the Presenter");
        }
    }

    public BasePresenter(Bus bus) {
        this.mBus = bus;
        this.mDataManager = null;
    }

    public BasePresenter(Bus bus, IDataManager iDataManager) {
        this.mBus = bus;
        this.mDataManager = iDataManager;
    }

    public void attachView(T t) {
        this.mBaseView = t;
        this.mBus.register(this);
    }

    public void detachView() {
        this.mBaseView = null;
        this.mBus.unregister(this);
    }

    public boolean isViewAttached() {
        return this.mBaseView != null;
    }

    public T getBaseView() {
        return this.mBaseView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new BaseViewNotAttachedException();
        }
    }
}
