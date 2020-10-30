package ar.com.santander.rio.mbanking.utils.genesyschat;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GenesysChatBackgroundEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;

public class WhatsonChatBackgroundService extends Service {
    public static final String COUNT_EXTRA = "COUNT";
    public static final String NEW_GENESYS_ACTION_MESSAGE = "NewGenesysMessage";
    public static final String PERIODO_EXTRA = "PERIODO";
    public static final String RESPONSE_EXTRA = "RESPONSE";
    private TimerTask a;
    private int b;
    @Inject
    public Bus bus;
    private long c;
    /* access modifiers changed from: private */
    public boolean d = true;
    @Inject
    public IDataManager mDatamanager;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        ((BaseApplication) getApplicationContext()).getGraph().inject(this);
        this.bus.register(this);
    }

    public void onDestroy() {
        a();
        this.bus.unregister(this);
        a();
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        this.bus.register(this);
        this.c = (long) Integer.parseInt(intent.getExtras().get(PERIODO_EXTRA).toString());
        this.b = Integer.parseInt(intent.getExtras().get("COUNT").toString());
        final Handler handler = new Handler();
        Timer timer = new Timer();
        if (this.a == null) {
            this.a = new TimerTask() {
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            if (WhatsonChatBackgroundService.this.d) {
                                WhatsonChatBackgroundService.this.d = false;
                                WhatsonChatBackgroundService.this.mDatamanager.genesysChatBackground("6", "");
                            }
                        }
                    });
                }
            };
            timer.schedule(this.a, 0, 1000 * this.c);
        }
        return 1;
    }

    @Subscribe
    public void onGenesysChatResponse(GenesysChatBackgroundEvent genesysChatBackgroundEvent) {
        if (genesysChatBackgroundEvent.getResult() == TypeResult.OK) {
            Intent intent = new Intent();
            intent.setAction(NEW_GENESYS_ACTION_MESSAGE);
            intent.putExtra(RESPONSE_EXTRA, genesysChatBackgroundEvent.getResponse().getGenesysChatBodyResponseBean());
            try {
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } catch (Exception unused) {
            }
        }
        this.b--;
        if (this.b <= 0) {
            stopSelf();
        }
        this.d = true;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.a == null) {
            this.a.cancel();
            this.a = null;
        }
    }
}
