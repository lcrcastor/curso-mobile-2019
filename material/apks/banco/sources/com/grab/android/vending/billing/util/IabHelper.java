package com.grab.android.vending.billing.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.android.vending.billing.IInAppBillingService;
import com.android.vending.billing.IInAppBillingService.Stub;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class IabHelper {
    public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
    public static final int BILLING_RESPONSE_RESULT_ERROR = 6;
    public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
    public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;
    public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;
    public static final int BILLING_RESPONSE_RESULT_OK = 0;
    public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    public static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
    public static final String GET_SKU_DETAILS_ITEM_TYPE_LIST = "ITEM_TYPE_LIST";
    public static final int IABHELPER_BAD_RESPONSE = -1002;
    public static final int IABHELPER_ERROR_BASE = -1000;
    public static final int IABHELPER_INVALID_CONSUMPTION = -1010;
    public static final int IABHELPER_MISSING_TOKEN = -1007;
    public static final int IABHELPER_REMOTE_EXCEPTION = -1001;
    public static final int IABHELPER_SEND_INTENT_FAILED = -1004;
    public static final int IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE = -1009;
    public static final int IABHELPER_UNKNOWN_ERROR = -1008;
    public static final int IABHELPER_UNKNOWN_PURCHASE_RESPONSE = -1006;
    public static final int IABHELPER_USER_CANCELLED = -1005;
    public static final int IABHELPER_VERIFICATION_FAILED = -1003;
    public static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
    public static final String ITEM_TYPE_INAPP = "inapp";
    public static final String ITEM_TYPE_SUBS = "subs";
    public static final String RESPONSE_BUY_INTENT = "BUY_INTENT";
    public static final String RESPONSE_CODE = "RESPONSE_CODE";
    public static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
    public static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";
    public static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    public static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    public static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
    public static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
    boolean a = false;
    String b = "IabHelper";
    boolean c = false;
    boolean d = false;
    boolean e = false;
    String f = "";
    Context g;
    IInAppBillingService h;
    ServiceConnection i;
    int j;
    String k;
    String l = null;
    OnIabPurchaseFinishedListener m;

    public interface OnConsumeFinishedListener {
        void onConsumeFinished(Purchase purchase, IabResult iabResult);
    }

    public interface OnConsumeMultiFinishedListener {
        void onConsumeMultiFinished(List<Purchase> list, List<IabResult> list2);
    }

    public interface OnIabPurchaseFinishedListener {
        void onIabPurchaseFinished(IabResult iabResult, Purchase purchase);
    }

    public interface OnIabSetupFinishedListener {
        void onIabSetupFinished(IabResult iabResult);
    }

    public interface QueryInventoryFinishedListener {
        void onQueryInventoryFinished(IabResult iabResult, Inventory inventory);
    }

    public IabHelper(Context context, String str) {
        this.g = context.getApplicationContext();
        this.l = str;
        c("IAB helper created.");
    }

    public void enableDebugLogging(boolean z, String str) {
        this.a = z;
        this.b = str;
    }

    public void enableDebugLogging(boolean z) {
        this.a = z;
    }

    public void startSetup(final OnIabSetupFinishedListener onIabSetupFinishedListener) {
        if (this.c) {
            throw new IllegalStateException("IAB helper is already set up.");
        }
        c("Starting in-app billing setup.");
        this.i = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName componentName) {
                IabHelper.this.c("Billing service disconnected.");
                IabHelper.this.h = null;
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IabHelper.this.c("Billing service connected.");
                IabHelper.this.h = Stub.asInterface(iBinder);
                String packageName = IabHelper.this.g.getPackageName();
                try {
                    IabHelper.this.c("Checking for in-app billing 3 support.");
                    int isBillingSupported = IabHelper.this.h.isBillingSupported(3, packageName, IabHelper.ITEM_TYPE_INAPP);
                    if (isBillingSupported != 0) {
                        if (onIabSetupFinishedListener != null) {
                            onIabSetupFinishedListener.onIabSetupFinished(new IabResult(isBillingSupported, "Error checking for billing v3 support."));
                        }
                        IabHelper.this.d = false;
                        return;
                    }
                    IabHelper iabHelper = IabHelper.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("In-app billing version 3 supported for ");
                    sb.append(packageName);
                    iabHelper.c(sb.toString());
                    int isBillingSupported2 = IabHelper.this.h.isBillingSupported(3, packageName, IabHelper.ITEM_TYPE_SUBS);
                    if (isBillingSupported2 == 0) {
                        IabHelper.this.c("Subscriptions AVAILABLE.");
                        IabHelper.this.d = true;
                    } else {
                        IabHelper iabHelper2 = IabHelper.this;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Subscriptions NOT AVAILABLE. Response: ");
                        sb2.append(isBillingSupported2);
                        iabHelper2.c(sb2.toString());
                    }
                    IabHelper.this.c = true;
                    if (onIabSetupFinishedListener != null) {
                        onIabSetupFinishedListener.onIabSetupFinished(new IabResult(0, "Setup successful."));
                    }
                } catch (RemoteException e) {
                    if (onIabSetupFinishedListener != null) {
                        onIabSetupFinishedListener.onIabSetupFinished(new IabResult(IabHelper.IABHELPER_REMOTE_EXCEPTION, "RemoteException while setting up in-app billing."));
                    }
                    e.printStackTrace();
                }
            }
        };
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        if (!this.g.getPackageManager().queryIntentServices(intent, 0).isEmpty()) {
            this.g.bindService(intent, this.i, 1);
        } else if (onIabSetupFinishedListener != null) {
            onIabSetupFinishedListener.onIabSetupFinished(new IabResult(3, "Billing service unavailable on device."));
        }
    }

    public void dispose() {
        c("Disposing.");
        this.c = false;
        if (this.i != null) {
            c("Unbinding from service.");
            if (this.g != null) {
                this.g.unbindService(this.i);
            }
            this.i = null;
            this.h = null;
            this.m = null;
        }
    }

    public boolean subscriptionsSupported() {
        return this.d;
    }

    public void launchPurchaseFlow(Activity activity, String str, int i2, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        launchPurchaseFlow(activity, str, i2, onIabPurchaseFinishedListener, "");
    }

    public void launchPurchaseFlow(Activity activity, String str, int i2, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String str2) {
        launchPurchaseFlow(activity, str, ITEM_TYPE_INAPP, i2, onIabPurchaseFinishedListener, str2);
    }

    public void launchSubscriptionPurchaseFlow(Activity activity, String str, int i2, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        launchSubscriptionPurchaseFlow(activity, str, i2, onIabPurchaseFinishedListener, "");
    }

    public void launchSubscriptionPurchaseFlow(Activity activity, String str, int i2, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String str2) {
        launchPurchaseFlow(activity, str, ITEM_TYPE_SUBS, i2, onIabPurchaseFinishedListener, str2);
    }

    public void launchPurchaseFlow(Activity activity, String str, String str2, int i2, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String str3) {
        a("launchPurchaseFlow");
        b("launchPurchaseFlow");
        if (!str2.equals(ITEM_TYPE_SUBS) || this.d) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("Constructing buy intent for ");
                sb.append(str);
                sb.append(", item type: ");
                sb.append(str2);
                c(sb.toString());
                Bundle buyIntent = this.h.getBuyIntent(3, this.g.getPackageName(), str, str2, str3);
                int a2 = a(buyIntent);
                if (a2 != 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unable to buy item, Error response: ");
                    sb2.append(getResponseDesc(a2));
                    d(sb2.toString());
                    IabResult iabResult = new IabResult(a2, "Unable to buy item");
                    if (onIabPurchaseFinishedListener != null) {
                        onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult, null);
                    }
                    return;
                }
                PendingIntent pendingIntent = (PendingIntent) buyIntent.getParcelable(RESPONSE_BUY_INTENT);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Launching buy intent for ");
                sb3.append(str);
                sb3.append(". Request code: ");
                sb3.append(i2);
                c(sb3.toString());
                this.j = i2;
                this.m = onIabPurchaseFinishedListener;
                this.k = str2;
                activity.startIntentSenderForResult(pendingIntent.getIntentSender(), i2, new Intent(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue());
            } catch (SendIntentException e2) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("SendIntentException while launching purchase flow for sku ");
                sb4.append(str);
                d(sb4.toString());
                e2.printStackTrace();
                IabResult iabResult2 = new IabResult(IABHELPER_SEND_INTENT_FAILED, "Failed to send intent.");
                if (onIabPurchaseFinishedListener != null) {
                    onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult2, null);
                }
            } catch (RemoteException e3) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("RemoteException while launching purchase flow for sku ");
                sb5.append(str);
                d(sb5.toString());
                e3.printStackTrace();
                IabResult iabResult3 = new IabResult(IABHELPER_REMOTE_EXCEPTION, "Remote exception while starting purchase flow");
                if (onIabPurchaseFinishedListener != null) {
                    onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult3, null);
                }
            }
        } else {
            IabResult iabResult4 = new IabResult(IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE, "Subscriptions are not available.");
            if (onIabPurchaseFinishedListener != null) {
                onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult4, null);
            }
        }
    }

    public boolean handleActivityResult(int i2, int i3, Intent intent) {
        if (i2 != this.j) {
            return false;
        }
        a("handleActivityResult");
        a();
        if (intent == null) {
            d("Null data in IAB activity result.");
            IabResult iabResult = new IabResult(IABHELPER_BAD_RESPONSE, "Null data in IAB result");
            if (this.m != null) {
                this.m.onIabPurchaseFinished(iabResult, null);
            }
            return true;
        }
        int a2 = a(intent);
        String stringExtra = intent.getStringExtra(RESPONSE_INAPP_PURCHASE_DATA);
        String stringExtra2 = intent.getStringExtra(RESPONSE_INAPP_SIGNATURE);
        if (i3 == -1 && a2 == 0) {
            c("Successful resultcode from purchase activity.");
            StringBuilder sb = new StringBuilder();
            sb.append("Purchase data: ");
            sb.append(stringExtra);
            c(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Data signature: ");
            sb2.append(stringExtra2);
            c(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Extras: ");
            sb3.append(intent.getExtras());
            c(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Expected item type: ");
            sb4.append(this.k);
            c(sb4.toString());
            if (stringExtra == null || stringExtra2 == null) {
                d("BUG: either purchaseData or dataSignature is null.");
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Extras: ");
                sb5.append(intent.getExtras().toString());
                c(sb5.toString());
                IabResult iabResult2 = new IabResult(IABHELPER_UNKNOWN_ERROR, "IAB returned null purchaseData or dataSignature");
                if (this.m != null) {
                    this.m.onIabPurchaseFinished(iabResult2, null);
                }
                return true;
            }
            try {
                Purchase purchase = new Purchase(this.k, stringExtra, stringExtra2);
                String sku = purchase.getSku();
                if (!Security.verifyPurchase(this.l, stringExtra, stringExtra2)) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("Purchase signature verification FAILED for sku ");
                    sb6.append(sku);
                    d(sb6.toString());
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append("Signature verification failed for sku ");
                    sb7.append(sku);
                    IabResult iabResult3 = new IabResult(IABHELPER_VERIFICATION_FAILED, sb7.toString());
                    if (this.m != null) {
                        this.m.onIabPurchaseFinished(iabResult3, purchase);
                    }
                    return true;
                }
                c("Purchase signature successfully verified.");
                if (this.m != null) {
                    this.m.onIabPurchaseFinished(new IabResult(0, "Success"), purchase);
                }
            } catch (JSONException e2) {
                d("Failed to parse purchase data.");
                e2.printStackTrace();
                IabResult iabResult4 = new IabResult(IABHELPER_BAD_RESPONSE, "Failed to parse purchase data.");
                if (this.m != null) {
                    this.m.onIabPurchaseFinished(iabResult4, null);
                }
                return true;
            }
        } else if (i3 == -1) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Result code was OK but in-app billing response was not OK: ");
            sb8.append(getResponseDesc(a2));
            c(sb8.toString());
            if (this.m != null) {
                this.m.onIabPurchaseFinished(new IabResult(a2, "Problem purchashing item."), null);
            }
        } else if (i3 == 0) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("Purchase canceled - Response: ");
            sb9.append(getResponseDesc(a2));
            c(sb9.toString());
            IabResult iabResult5 = new IabResult(IABHELPER_USER_CANCELLED, "User canceled.");
            if (this.m != null) {
                this.m.onIabPurchaseFinished(iabResult5, null);
            }
        } else {
            StringBuilder sb10 = new StringBuilder();
            sb10.append("Purchase failed. Result code: ");
            sb10.append(Integer.toString(i3));
            sb10.append(". Response: ");
            sb10.append(getResponseDesc(a2));
            d(sb10.toString());
            IabResult iabResult6 = new IabResult(IABHELPER_UNKNOWN_PURCHASE_RESPONSE, "Unknown purchase response.");
            if (this.m != null) {
                this.m.onIabPurchaseFinished(iabResult6, null);
            }
        }
        return true;
    }

    public Inventory queryInventory(boolean z, List<String> list) {
        return queryInventory(z, list, null);
    }

    public Inventory queryInventory(boolean z, List<String> list, List<String> list2) {
        a("queryInventory");
        try {
            Inventory inventory = new Inventory();
            int a2 = a(inventory, ITEM_TYPE_INAPP);
            if (a2 != 0) {
                throw new IabException(a2, "Error refreshing inventory (querying owned items).");
            }
            if (z) {
                int a3 = a(ITEM_TYPE_INAPP, inventory, list);
                if (a3 != 0) {
                    throw new IabException(a3, "Error refreshing inventory (querying prices of items).");
                }
            }
            if (this.d) {
                int a4 = a(inventory, ITEM_TYPE_SUBS);
                if (a4 != 0) {
                    throw new IabException(a4, "Error refreshing inventory (querying owned subscriptions).");
                } else if (z) {
                    int a5 = a(ITEM_TYPE_SUBS, inventory, list);
                    if (a5 != 0) {
                        throw new IabException(a5, "Error refreshing inventory (querying prices of subscriptions).");
                    }
                }
            }
            return inventory;
        } catch (RemoteException e2) {
            throw new IabException(IABHELPER_REMOTE_EXCEPTION, "Remote exception while refreshing inventory.", e2);
        } catch (JSONException e3) {
            throw new IabException(IABHELPER_BAD_RESPONSE, "Error parsing JSON response while refreshing inventory.", e3);
        }
    }

    public void queryInventoryAsync(boolean z, List<String> list, QueryInventoryFinishedListener queryInventoryFinishedListener) {
        final Handler handler = new Handler();
        a("queryInventory");
        b("refresh inventory");
        final boolean z2 = z;
        final List<String> list2 = list;
        final QueryInventoryFinishedListener queryInventoryFinishedListener2 = queryInventoryFinishedListener;
        AnonymousClass2 r0 = new Runnable() {
            public void run() {
                final Inventory inventory;
                final IabResult iabResult = new IabResult(0, "Inventory refresh successful.");
                try {
                    inventory = IabHelper.this.queryInventory(z2, list2);
                } catch (IabException e2) {
                    iabResult = e2.getResult();
                    inventory = null;
                }
                IabHelper.this.a();
                handler.post(new Runnable() {
                    public void run() {
                        queryInventoryFinishedListener2.onQueryInventoryFinished(iabResult, inventory);
                    }
                });
            }
        };
        new Thread(r0).start();
    }

    public void queryInventoryAsync(QueryInventoryFinishedListener queryInventoryFinishedListener) {
        queryInventoryAsync(true, null, queryInventoryFinishedListener);
    }

    public void queryInventoryAsync(boolean z, QueryInventoryFinishedListener queryInventoryFinishedListener) {
        queryInventoryAsync(z, null, queryInventoryFinishedListener);
    }

    /* access modifiers changed from: 0000 */
    public void a(Purchase purchase) {
        a("consume");
        if (!purchase.a.equals(ITEM_TYPE_INAPP)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Items of type '");
            sb.append(purchase.a);
            sb.append("' can't be consumed.");
            throw new IabException((int) IABHELPER_INVALID_CONSUMPTION, sb.toString());
        }
        try {
            String token = purchase.getToken();
            String sku = purchase.getSku();
            if (token != null) {
                if (!token.equals("")) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Consuming sku: ");
                    sb2.append(sku);
                    sb2.append(", token: ");
                    sb2.append(token);
                    c(sb2.toString());
                    int consumePurchase = this.h.consumePurchase(3, this.g.getPackageName(), token);
                    if (consumePurchase == 0) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Successfully consumed sku: ");
                        sb3.append(sku);
                        c(sb3.toString());
                        return;
                    }
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Error consuming consuming sku ");
                    sb4.append(sku);
                    sb4.append(". ");
                    sb4.append(getResponseDesc(consumePurchase));
                    c(sb4.toString());
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Error consuming sku ");
                    sb5.append(sku);
                    throw new IabException(consumePurchase, sb5.toString());
                }
            }
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Can't consume ");
            sb6.append(sku);
            sb6.append(". No token.");
            d(sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append("PurchaseInfo is missing token for sku: ");
            sb7.append(sku);
            sb7.append(UtilsCuentas.SEPARAOR2);
            sb7.append(purchase);
            throw new IabException((int) IABHELPER_MISSING_TOKEN, sb7.toString());
        } catch (RemoteException e2) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Remote exception while consuming. PurchaseInfo: ");
            sb8.append(purchase);
            throw new IabException(IABHELPER_REMOTE_EXCEPTION, sb8.toString(), e2);
        }
    }

    public void consumeAsync(Purchase purchase, OnConsumeFinishedListener onConsumeFinishedListener) {
        a("consume");
        ArrayList arrayList = new ArrayList();
        arrayList.add(purchase);
        a((List<Purchase>) arrayList, onConsumeFinishedListener, (OnConsumeMultiFinishedListener) null);
    }

    public void consumeAsync(List<Purchase> list, OnConsumeMultiFinishedListener onConsumeMultiFinishedListener) {
        a("consume");
        a(list, (OnConsumeFinishedListener) null, onConsumeMultiFinishedListener);
    }

    public static String getResponseDesc(int i2) {
        String[] split = "0:OK/1:User Canceled/2:Unknown/3:Billing Unavailable/4:Item unavailable/5:Developer Error/6:Error/7:Item Already Owned/8:Item not owned".split("/");
        String[] split2 = "0:OK/-1001:Remote exception during initialization/-1002:Bad response received/-1003:Purchase signature verification failed/-1004:Send intent failed/-1005:User cancelled/-1006:Unknown purchase response/-1007:Missing token/-1008:Unknown error/-1009:Subscriptions not available/-1010:Invalid consumption attempt".split("/");
        if (i2 <= -1000) {
            int i3 = -1000 - i2;
            if (i3 >= 0 && i3 < split2.length) {
                return split2[i3];
            }
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(i2));
            sb.append(":Unknown IAB Helper Error");
            return sb.toString();
        } else if (i2 >= 0 && i2 < split.length) {
            return split[i2];
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(String.valueOf(i2));
            sb2.append(":Unknown");
            return sb2.toString();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        if (!this.c) {
            StringBuilder sb = new StringBuilder();
            sb.append("Illegal state for operation (");
            sb.append(str);
            sb.append("): IAB helper is not set up.");
            d(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("IAB helper is not set up. Can't perform operation: ");
            sb2.append(str);
            throw new IllegalStateException(sb2.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public int a(Bundle bundle) {
        Object obj = bundle.get(RESPONSE_CODE);
        if (obj == null) {
            c("Bundle with null response code, assuming OK (known issue)");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            if (obj instanceof Long) {
                return (int) ((Long) obj).longValue();
            }
            d("Unexpected type for bundle response code.");
            d(obj.getClass().getName());
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected type for bundle response code: ");
            sb.append(obj.getClass().getName());
            throw new RuntimeException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public int a(Intent intent) {
        Object obj = intent.getExtras().get(RESPONSE_CODE);
        if (obj == null) {
            d("Intent with no response code, assuming OK (known issue)");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            if (obj instanceof Long) {
                return (int) ((Long) obj).longValue();
            }
            d("Unexpected type for intent response code.");
            d(obj.getClass().getName());
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected type for intent response code: ");
            sb.append(obj.getClass().getName());
            throw new RuntimeException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(String str) {
        if (this.e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Can't start async operation (");
            sb.append(str);
            sb.append(") because another async operation(");
            sb.append(this.f);
            sb.append(") is in progress.");
            throw new IllegalStateException(sb.toString());
        }
        this.f = str;
        this.e = true;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Starting async operation: ");
        sb2.append(str);
        c(sb2.toString());
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ending async operation: ");
        sb.append(this.f);
        c(sb.toString());
        this.f = "";
        this.e = false;
    }

    /* access modifiers changed from: 0000 */
    public int a(Inventory inventory, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Querying owned items, item type: ");
        sb.append(str);
        c(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Package name: ");
        sb2.append(this.g.getPackageName());
        c(sb2.toString());
        int i2 = 0;
        String str2 = null;
        boolean z = false;
        while (true) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Calling getPurchases with continuation token: ");
            sb3.append(str2);
            c(sb3.toString());
            Bundle purchases = this.h.getPurchases(3, this.g.getPackageName(), str, str2);
            int a2 = a(purchases);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Owned items response: ");
            sb4.append(String.valueOf(a2));
            c(sb4.toString());
            if (a2 != 0) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("getPurchases() failed: ");
                sb5.append(getResponseDesc(a2));
                c(sb5.toString());
                return a2;
            } else if (!purchases.containsKey(RESPONSE_INAPP_ITEM_LIST) || !purchases.containsKey(RESPONSE_INAPP_PURCHASE_DATA_LIST) || !purchases.containsKey(RESPONSE_INAPP_SIGNATURE_LIST)) {
                d("Bundle returned from getPurchases() doesn't contain required fields.");
            } else {
                ArrayList stringArrayList = purchases.getStringArrayList(RESPONSE_INAPP_ITEM_LIST);
                ArrayList stringArrayList2 = purchases.getStringArrayList(RESPONSE_INAPP_PURCHASE_DATA_LIST);
                ArrayList stringArrayList3 = purchases.getStringArrayList(RESPONSE_INAPP_SIGNATURE_LIST);
                boolean z2 = z;
                for (int i3 = 0; i3 < stringArrayList2.size(); i3++) {
                    String str3 = (String) stringArrayList2.get(i3);
                    String str4 = (String) stringArrayList3.get(i3);
                    String str5 = (String) stringArrayList.get(i3);
                    if (Security.verifyPurchase(this.l, str3, str4)) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("Sku is owned: ");
                        sb6.append(str5);
                        c(sb6.toString());
                        Purchase purchase = new Purchase(str, str3, str4);
                        if (TextUtils.isEmpty(purchase.getToken())) {
                            e("BUG: empty/null token!");
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append("Purchase data: ");
                            sb7.append(str3);
                            c(sb7.toString());
                        }
                        inventory.a(purchase);
                    } else {
                        e("Purchase signature verification **FAILED**. Not adding item.");
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("   Purchase data: ");
                        sb8.append(str3);
                        c(sb8.toString());
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("   Signature: ");
                        sb9.append(str4);
                        c(sb9.toString());
                        z2 = true;
                    }
                }
                str2 = purchases.getString(INAPP_CONTINUATION_TOKEN);
                StringBuilder sb10 = new StringBuilder();
                sb10.append("Continuation token: ");
                sb10.append(str2);
                c(sb10.toString());
                if (TextUtils.isEmpty(str2)) {
                    if (z2) {
                        i2 = IABHELPER_VERIFICATION_FAILED;
                    }
                    return i2;
                }
                z = z2;
            }
        }
        d("Bundle returned from getPurchases() doesn't contain required fields.");
        return IABHELPER_BAD_RESPONSE;
    }

    /* access modifiers changed from: 0000 */
    public int a(String str, Inventory inventory, List<String> list) {
        c("Querying SKU details.");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(inventory.a(str));
        if (list != null) {
            arrayList.addAll(list);
        }
        if (arrayList.size() == 0) {
            c("queryPrices: nothing to do because there are no SKUs.");
            return 0;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(GET_SKU_DETAILS_ITEM_LIST, arrayList);
        Bundle skuDetails = this.h.getSkuDetails(3, this.g.getPackageName(), str, bundle);
        if (!skuDetails.containsKey(RESPONSE_GET_SKU_DETAILS_LIST)) {
            int a2 = a(skuDetails);
            if (a2 != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("getSkuDetails() failed: ");
                sb.append(getResponseDesc(a2));
                c(sb.toString());
                return a2;
            }
            d("getSkuDetails() returned a bundle with neither an error nor a detail list.");
            return IABHELPER_BAD_RESPONSE;
        }
        Iterator it = skuDetails.getStringArrayList(RESPONSE_GET_SKU_DETAILS_LIST).iterator();
        while (it.hasNext()) {
            SkuDetails skuDetails2 = new SkuDetails(str, (String) it.next());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Got sku details: ");
            sb2.append(skuDetails2);
            c(sb2.toString());
            inventory.a(skuDetails2);
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void a(List<Purchase> list, OnConsumeFinishedListener onConsumeFinishedListener, OnConsumeMultiFinishedListener onConsumeMultiFinishedListener) {
        final Handler handler = new Handler();
        b("consume");
        final List<Purchase> list2 = list;
        final OnConsumeFinishedListener onConsumeFinishedListener2 = onConsumeFinishedListener;
        final OnConsumeMultiFinishedListener onConsumeMultiFinishedListener2 = onConsumeMultiFinishedListener;
        AnonymousClass3 r0 = new Runnable() {
            public void run() {
                final ArrayList arrayList = new ArrayList();
                for (Purchase purchase : list2) {
                    try {
                        IabHelper.this.a(purchase);
                        StringBuilder sb = new StringBuilder();
                        sb.append("Successful consume of sku ");
                        sb.append(purchase.getSku());
                        arrayList.add(new IabResult(0, sb.toString()));
                    } catch (IabException e2) {
                        arrayList.add(e2.getResult());
                    }
                }
                IabHelper.this.a();
                if (onConsumeFinishedListener2 != null) {
                    handler.post(new Runnable() {
                        public void run() {
                            onConsumeFinishedListener2.onConsumeFinished((Purchase) list2.get(0), (IabResult) arrayList.get(0));
                        }
                    });
                }
                if (onConsumeMultiFinishedListener2 != null) {
                    handler.post(new Runnable() {
                        public void run() {
                            onConsumeMultiFinishedListener2.onConsumeMultiFinished(list2, arrayList);
                        }
                    });
                }
            }
        };
        new Thread(r0).start();
    }

    /* access modifiers changed from: 0000 */
    public void c(String str) {
        if (this.a) {
            Log.d(this.b, str);
        }
    }

    /* access modifiers changed from: 0000 */
    public void d(String str) {
        String str2 = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append("In-app billing error: ");
        sb.append(str);
        Log.e(str2, sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public void e(String str) {
        String str2 = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append("In-app billing warning: ");
        sb.append(str);
        Log.w(str2, sb.toString());
    }
}
