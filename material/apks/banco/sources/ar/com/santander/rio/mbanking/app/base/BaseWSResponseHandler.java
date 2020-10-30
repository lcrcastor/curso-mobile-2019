package ar.com.santander.rio.mbanking.app.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.utils.Utils;

public abstract class BaseWSResponseHandler {
    protected BaseActivity mActivity;
    protected IBaseView mBaseView;
    protected Context mContext;
    protected boolean mDismissOnResponse = false;
    protected WebServiceEvent mErrorEvent;
    protected BaseFragment mFragment;
    protected FragmentManager mFragmentManager;
    protected boolean mProcessErrors = true;
    protected String mTagFragment;
    protected TypeOption mWSType;
    protected String title;

    public interface IActionCustom {
        void action();
    }

    public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
    }

    public abstract void onOk();

    public void onRes3Error() {
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, BaseActivity baseActivity) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = null;
        this.mFragment = null;
        this.mActivity = baseActivity;
        this.mProcessErrors = true;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, BaseActivity baseActivity, String str2) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = null;
        this.mFragment = null;
        this.mActivity = baseActivity;
        this.mProcessErrors = true;
        this.title = str2;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, BaseFragment baseFragment, BaseActivity baseActivity, String str2) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = null;
        this.mFragment = baseFragment;
        this.mActivity = baseActivity;
        this.mProcessErrors = true;
        this.title = str2;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, BaseActivity baseActivity, boolean z) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = null;
        this.mFragment = null;
        this.mActivity = baseActivity;
        this.mProcessErrors = z;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, BaseFragment baseFragment, BaseActivity baseActivity) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = null;
        this.mFragment = baseFragment;
        this.mActivity = baseActivity;
        this.mProcessErrors = true;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, BaseFragment baseFragment, boolean z, BaseActivity baseActivity) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = null;
        this.mFragment = baseFragment;
        this.mActivity = baseActivity;
        this.mProcessErrors = z;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, IBaseView iBaseView, BaseActivity baseActivity) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = iBaseView;
        this.mFragment = null;
        this.mActivity = baseActivity;
        this.mProcessErrors = true;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, IBaseView iBaseView, boolean z, boolean z2, BaseActivity baseActivity) {
        this.mDismissOnResponse = z2;
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = iBaseView;
        this.mFragment = null;
        this.mActivity = baseActivity;
        this.mProcessErrors = true;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, IBaseView iBaseView, String str2, BaseActivity baseActivity) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = iBaseView;
        this.mFragment = null;
        this.mActivity = baseActivity;
        this.mProcessErrors = true;
        this.title = str2;
    }

    public BaseWSResponseHandler(Context context, TypeOption typeOption, String str, IBaseView iBaseView, boolean z, BaseActivity baseActivity) {
        this.mContext = context;
        this.mWSType = typeOption;
        this.mFragmentManager = ((BaseActivity) context).getSupportFragmentManager();
        this.mTagFragment = str;
        this.mBaseView = iBaseView;
        this.mFragment = null;
        this.mActivity = baseActivity;
        this.mProcessErrors = z;
    }

    public void handleWSResponse(WebServiceEvent webServiceEvent) {
        this.mErrorEvent = webServiceEvent;
        if (webServiceEvent.getResult() == TypeResult.BEAN_WARNING) {
            if (this.mDismissOnResponse && this.mBaseView != null) {
                this.mBaseView.dismissProgressIndicator();
            }
            onWarning(webServiceEvent);
        } else if (webServiceEvent.getResult() == TypeResult.OK) {
            if (this.mDismissOnResponse && this.mBaseView != null) {
                this.mBaseView.dismissProgressIndicator();
            }
            onOk();
        } else {
            commonAllErrorsBeforeProcess(webServiceEvent);
            if (this.mProcessErrors) {
                if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_1) {
                    onRes1Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_2) {
                    onRes2Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_3) {
                    onRes3Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_4) {
                    onRes4Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_5) {
                    onRes5Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_6) {
                    onRes6Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_7) {
                    onRes7Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_8) {
                    onRes8Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.BEAN_ERROR_RES_9) {
                    onRes9Error(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.SERVER_ERROR) {
                    onServerError(webServiceEvent);
                } else if (webServiceEvent.getResult() == TypeResult.UNKNOWN_ERROR) {
                    onUnknownError(webServiceEvent);
                }
            }
            commonAllErrorsAfterProcess(webServiceEvent);
        }
    }

    /* access modifiers changed from: protected */
    public void onWarning(WebServiceEvent webServiceEvent) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onOk();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    public void onRes1Error(WebServiceEvent webServiceEvent) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onRes1Error();
            }
        });
        newInstance.show(this.mFragmentManager, "DialogRes1");
    }

    public void onRes1Error() {
        if (this.mBaseView != null) {
            this.mBaseView.clearScreenData();
        } else if (this.mFragment != null) {
            this.mFragment.clearScreenData();
        } else if (this.mActivity != null) {
            this.mActivity.clearScreenData();
        }
    }

    /* access modifiers changed from: protected */
    public void onRes2Error(WebServiceEvent webServiceEvent) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onRes2Error();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    /* access modifiers changed from: protected */
    public void onRes2Error() {
        if (this.mBaseView != null) {
            this.mBaseView.clearScreenData();
        } else if (this.mFragment != null) {
            this.mFragment.clearScreenData();
        } else if (this.mActivity != null) {
            this.mActivity.clearScreenData();
        }
    }

    public void onRes3Error(WebServiceEvent webServiceEvent) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onRes3Error();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    public void onRes4Error(WebServiceEvent webServiceEvent) {
        onRes4Error();
    }

    public void onRes4Error() {
        if (this.mBaseView != null || this.mFragment != null || this.mActivity != null) {
            Intent intent = new Intent();
            if (this.mWSType == TypeOption.INITIAL_VIEW) {
                intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_HOME_ERROR_FRAGMENT);
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, this.mErrorEvent.getMessageToShow());
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE, this.title);
            } else if (this.mWSType == TypeOption.INTERMDIATE_VIEW) {
                intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_NEXT_ERROR_SCREEN);
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, Html.fromHtml(this.mErrorEvent.getMessageToShow()).toString());
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE, ActionBarType.BACK_ONLY);
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, R.drawable.error_continuacion);
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE, this.title);
            } else if (this.mWSType == TypeOption.NO_TRANSACTIONAL_FINAL_VIEW || this.mWSType == TypeOption.TRANSACTIONAL_FINAL_VIEW) {
                intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_NEXT_ERROR_SCREEN);
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, Html.fromHtml(this.mErrorEvent.getMessageToShow()).toString());
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE, ActionBarType.MENU);
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, R.drawable.error_continuacion);
            }
            if (this.mBaseView != null) {
                this.mBaseView.handleWSError(intent);
            } else if (this.mFragment != null) {
                this.mFragment.handleWSError(intent);
            } else if (this.mActivity != null) {
                this.mActivity.handleWSError(intent);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRes5Error(WebServiceEvent webServiceEvent) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onRes5Error();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    /* access modifiers changed from: protected */
    public void onRes5Error(WebServiceEvent webServiceEvent, final IActionCustom iActionCustom) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                iActionCustom.action();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    /* access modifiers changed from: protected */
    public void onRes5Error() {
        if (this.mActivity != null) {
            Intent intent = new Intent(this.mActivity, SantanderRioMainActivity.class);
            intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_LOGIN);
            intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_BACK_ANIMATION, true);
            if (this.mBaseView != null) {
                this.mBaseView.handleWSError(intent);
            } else if (this.mActivity != null) {
                this.mActivity.handleWSError(intent);
            } else if (this.mFragment != null) {
                this.mFragment.handleWSError(intent);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRes6Error(WebServiceEvent webServiceEvent) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onRes6Error();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    /* access modifiers changed from: protected */
    public void onRes6Error() {
        if (this.mBaseView != null || this.mFragment != null || this.mActivity != null) {
            Intent intent = new Intent(this.mActivity, SantanderRioMainActivity.class);
            intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_HOME_FUNCIONALIDAD);
            if (this.mBaseView != null) {
                this.mBaseView.handleWSError(intent);
            } else if (this.mFragment != null) {
                this.mFragment.handleWSError(intent);
            } else if (this.mActivity != null) {
                this.mActivity.handleWSError(intent);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRes7Error(WebServiceEvent webServiceEvent) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onRes7Error();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    /* access modifiers changed from: protected */
    public void onRes7Error() {
        if (this.mBaseView != null || this.mFragment != null || this.mActivity != null) {
            Intent intent = new Intent(this.mActivity, SantanderRioMainActivity.class);
            intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_CUENTAS);
            if (this.mBaseView != null) {
                this.mBaseView.handleWSError(intent);
            } else if (this.mFragment != null) {
                this.mFragment.handleWSError(intent);
            } else if (this.mActivity != null) {
                this.mActivity.handleWSError(intent);
            }
        }
    }

    public void onRes8Error(WebServiceEvent webServiceEvent) {
        onRes8Error();
    }

    public void onRes8Error() {
        if (this.mBaseView != null || this.mFragment != null || this.mActivity != null) {
            Intent intent = new Intent();
            intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_HOME_ERROR_CLOCK);
            intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, this.mErrorEvent.getMessageToShow());
            intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE, this.mErrorEvent.getTitleToShow());
            if (this.mBaseView != null) {
                this.mBaseView.handleWSError(intent);
            } else if (this.mFragment != null) {
                this.mFragment.handleWSError(intent);
            } else if (this.mActivity != null) {
                this.mActivity.handleWSError(intent);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRes9Error(WebServiceEvent webServiceEvent) {
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(titleToShow, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onRes9Error();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    /* access modifiers changed from: protected */
    public void onRes9Error() {
        if ((this.mBaseView != null || this.mFragment != null || this.mActivity != null) && this.mWSType != TypeOption.INITIAL_VIEW) {
            if (this.mBaseView != null) {
                this.mBaseView.onBackPressed();
            } else if (this.mFragment != null) {
                this.mFragment.onBackPressed();
            } else if (this.mActivity != null) {
                this.mActivity.onBackPressed();
            }
        }
    }

    public void onServerError(WebServiceEvent webServiceEvent) {
        String str;
        if (this.mWSType == TypeOption.INITIAL_VIEW) {
            onServerError();
            return;
        }
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        String str2 = titleToShow;
        if (this.mWSType == TypeOption.TRANSACTIONAL_FINAL_VIEW || this.mWSType == TypeOption.NO_TRANSACTIONAL_FINAL_VIEW) {
            str = this.mContext.getResources().getString(R.string.MSG_USER000055);
        } else {
            str = this.mContext.getResources().getString(R.string.MSG_USER000002_General_errorNoconexion);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(str).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onServerError();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    public void onServerError() {
        if ((this.mBaseView != null || this.mFragment != null || this.mActivity != null) && this.mErrorEvent.getTypeOption() != TypeOption.INTERMDIATE_VIEW) {
            Intent intent = new Intent();
            if (this.mErrorEvent.getTypeOption() == TypeOption.TRANSACTIONAL_FINAL_VIEW || this.mErrorEvent.getTypeOption() == TypeOption.NO_TRANSACTIONAL_FINAL_VIEW) {
                intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_CUENTAS);
            } else if (this.mErrorEvent.getTypeOption() == TypeOption.INITIAL_VIEW) {
                intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_HOME_ERROR_FRAGMENT);
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, this.mContext.getString(R.string.MSG_USER000002_General_errorNoconexion));
            }
            if (this.mBaseView != null) {
                this.mBaseView.handleWSError(intent);
            } else if (this.mFragment != null) {
                this.mFragment.handleWSError(intent);
            } else if (this.mActivity != null) {
                this.mActivity.handleWSError(intent);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onUnknownError(WebServiceEvent webServiceEvent) {
        String str;
        if (this.mWSType == TypeOption.INITIAL_VIEW) {
            onUnknownError();
            return;
        }
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            titleToShow = this.mContext.getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
        }
        String str2 = titleToShow;
        if (this.mWSType == TypeOption.TRANSACTIONAL_FINAL_VIEW || this.mWSType == TypeOption.NO_TRANSACTIONAL_FINAL_VIEW) {
            str = this.mContext.getResources().getString(R.string.MSG_USER000055);
        } else {
            str = this.mContext.getResources().getString(R.string.MSG_USER000002_General_errorNoconexion);
        }
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(str).toString()), null, null, this.mContext.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                BaseWSResponseHandler.this.onUnknownError();
            }
        });
        newInstance.show(this.mFragmentManager, "Dialog");
    }

    public void onUnknownError() {
        if ((this.mBaseView != null || this.mFragment != null || this.mActivity != null) && this.mErrorEvent.getTypeOption() != TypeOption.INTERMDIATE_VIEW) {
            Intent intent = new Intent();
            if (this.mErrorEvent.getTypeOption() == TypeOption.TRANSACTIONAL_FINAL_VIEW || this.mErrorEvent.getTypeOption() == TypeOption.NO_TRANSACTIONAL_FINAL_VIEW) {
                intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_CUENTAS);
            } else if (this.mErrorEvent.getTypeOption() == TypeOption.INITIAL_VIEW) {
                intent.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, WSErrorHandlerConstants.GO_TO_HOME_ERROR_FRAGMENT);
                intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, this.mContext.getString(R.string.MSG_USER000004_ErrorFiltrosyLogin));
            }
            if (this.mBaseView != null) {
                this.mBaseView.handleWSError(intent);
            } else if (this.mFragment != null) {
                this.mFragment.handleWSError(intent);
            } else if (this.mActivity != null) {
                this.mActivity.handleWSError(intent);
            }
        }
    }

    public void commonAllErrorsBeforeProcess(WebServiceEvent webServiceEvent) {
        if (this.mDismissOnResponse && this.mBaseView != null) {
            this.mBaseView.dismissProgressIndicator();
        }
    }
}
