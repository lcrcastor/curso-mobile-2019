package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.FotoObjetoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.FotoObjetoFragment.OnFragmentInteractionListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.IntuccionesFotoObjetoFragment;
import java.util.UUID;

public class FotoObjetoActivity extends BaseActivity implements OnFragmentInteractionListener, IntuccionesFotoObjetoFragment.OnFragmentInteractionListener {
    public static final String CAMARA_ERROR = "CAMARA_ERROR";
    public static final String EMPTY_IMAGE_ERROR = "EMPTY_IMAGE_ERROR";
    public static final String FAMILY_NAME = "FAMILY_NAME";
    public static final String GENERIC_ERROR = "GENERIC_ERROR";
    public static final String INSTRUCTIONS_ARG = "INSTRUCTIONS_ARG";
    public static final String PHOTO_STRING_DATA_BASE_64_RESULT_EXTRA = "PHOTO_STRING_DATA_BASE_64";
    public static final String PHOTO_STRING_ERROR_EXTRA = "PHOTO_STRING_ERROR_EXTRA";
    public static final String SEGUROS_2_SHARE_PREFERENCE_EDITOR_NAME = "SEGUROS-2";
    public static final String SHOW_INSTRUCTIONS_ARG = "SHOW_INSTRUCTIONS_ARG";
    public static final String TITLE_INSTRUCTION = "TITLE_INSTRUCTION";
    private boolean p;
    private String q;
    private String r = "";
    private String s;
    private boolean t = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_foto_objeto);
        if (getIntent().getExtras() != null) {
            this.p = getIntent().getExtras().getBoolean("SHOW_INSTRUCTIONS_ARG", true);
            this.q = getIntent().getExtras().getString(INSTRUCTIONS_ARG, "");
            this.r = getIntent().getExtras().getString(TITLE_INSTRUCTION, "");
            this.s = getIntent().getExtras().getString(FAMILY_NAME, "");
        }
        if (!getPackageManager().hasSystemFeature("android.hardware.camera")) {
            a("CAMARA_ERROR");
        } else if (this.p) {
            a((Fragment) IntuccionesFotoObjetoFragment.newInstance(this.q, this.r), FragmentConstants.INSTRUCTION_FOTO_OBJETO_FRAGMENT);
        } else {
            gotoPhotoFragment();
        }
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        configureActionBar();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.t) {
            this.t = false;
            gotoPhotoFragment();
        }
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.PUSH_CLOSE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().getCustomView().findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FotoObjetoActivity.this.a(FotoObjetoActivity.GENERIC_ERROR);
                    FotoObjetoActivity.this.finish();
                }
            });
        }
    }

    public void configureActionFotoActionBar() {
        setActionBarType(ActionBarType.CAM_CLOSE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FotoObjetoActivity.this.a(FotoObjetoActivity.GENERIC_ERROR);
                    FotoObjetoActivity.this.finish();
                }
            });
        }
    }

    private void a(Fragment fragment, String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.content, fragment);
        beginTransaction.commit();
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        Intent intent = new Intent();
        intent.putExtra(PHOTO_STRING_DATA_BASE_64_RESULT_EXTRA, "");
        intent.putExtra(PHOTO_STRING_ERROR_EXTRA, str);
        setResult(0, intent);
        finish();
    }

    private void b(String str) {
        Intent intent = new Intent();
        String uuid = UUID.randomUUID().toString();
        Editor edit = getApplicationContext().getSharedPreferences(SEGUROS_2_SHARE_PREFERENCE_EDITOR_NAME, 0).edit();
        edit.putString(uuid, str);
        edit.apply();
        intent.putExtra(PHOTO_STRING_DATA_BASE_64_RESULT_EXTRA, uuid);
        setResult(-1, intent);
        finish();
    }

    public void onStoreImage(String str) {
        b();
        if (!TextUtils.isEmpty(str)) {
            b(str);
        } else {
            a(EMPTY_IMAGE_ERROR);
        }
    }

    private void b() {
        FotoObjetoFragment fotoObjetoFragment = (FotoObjetoFragment) getSupportFragmentManager().findFragmentByTag(FragmentConstants.FOTO_OBJETO_FRAGMENT);
        if (fotoObjetoFragment != null) {
            fotoObjetoFragment.releaseCameraService();
        }
    }

    public void onError(String str) {
        if (str.equals("CAMARA_ERROR")) {
            a("CAMARA_ERROR");
        } else {
            a(GENERIC_ERROR);
        }
    }

    public void gotoPhotoFragment() {
        a((Fragment) FotoObjetoFragment.newInstance(this.s), FragmentConstants.FOTO_OBJETO_FRAGMENT);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.t = true;
        b();
        super.onPause();
    }
}
