package com.test.ad.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.anythink.banner.api.ATBannerListener;
import com.anythink.banner.api.ATBannerView;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.network.admob.AdmobATConst;

import java.util.HashMap;
import java.util.Map;

public class BannerAdActivity extends Activity {


    String placementIds[] = new String[]{
            DemoApplicaion.mPlacementId_banner_all
            , DemoApplicaion.mPlacementId_banner_admob
            , DemoApplicaion.mPlacementId_banner_facebook
            , DemoApplicaion.mPlacementId_banner_inmobi
            , DemoApplicaion.mPlacementId_banner_applovin
            , DemoApplicaion.mPlacementId_banner_mintegral
            , DemoApplicaion.mPlacementId_banner_mopub
            , DemoApplicaion.mPlacementId_banner_appnext
            , DemoApplicaion.mPlacementId_banner_nend
            , DemoApplicaion.mPlacementId_banner_fyber
            , DemoApplicaion.mPlacementId_banner_startapp
            , DemoApplicaion.mPlacementId_banner_vungle
            , DemoApplicaion.mPlacementId_banner_adcolony
            , DemoApplicaion.mPlacementId_banner_chartboost
            , DemoApplicaion.mPlacementId_banner_googleAdManager
            , DemoApplicaion.mPlacementId_banner_myoffer
            , DemoApplicaion.mPlacementId_banner_huawei
            , DemoApplicaion.mPlacementId_banner_unityads

    };

    String unitGroupName[] = new String[]{
            "All",
            "Admob",
            "Facebook",
            "Inmobi",
            "Applovin",
            "Mintegral",
            "Mopub",
            "Appnext",
            "Nend",
            "Fyber",
            "StartApp",
            "Vungle",
            "AdColony",
            "Chartboost",
            "Google Ad Manager",
            "MyOffer",
            "Huawei",
            "UnityAds"
    };

    ATBannerView mBannerView;

    int mCurrentSelectIndex;
    boolean hasAddBannerView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_banner);

        Spinner spinner = (Spinner) findViewById(R.id.banner_spinner);
        final FrameLayout frameLayout = findViewById(R.id.adview_container);
        mBannerView = new ATBannerView(this);
        mBannerView.setPlacementId(placementIds[mCurrentSelectIndex]);
        frameLayout.addView(mBannerView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dip2px(300)));
        mBannerView.setBannerAdListener(new ATBannerListener() {
            @Override
            public void onBannerLoaded() {
                Log.i("BannerAdActivity", "onBannerLoaded");
                Toast.makeText(BannerAdActivity.this,
                        "onBannerLoaded",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFailed(AdError adError) {
                Log.i("BannerAdActivity", "onBannerFailed：" + adError.printStackTrace());
                Toast.makeText(BannerAdActivity.this,
                        "onBannerFailed",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClicked(ATAdInfo entity) {
                Log.i("BannerAdActivity", "onBannerClicked:" + entity.toString());
                Toast.makeText(BannerAdActivity.this,
                        "onBannerClicked",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerShow(ATAdInfo entity) {
                Log.i("BannerAdActivity", "onBannerShow:"  + entity.toString());
                Toast.makeText(BannerAdActivity.this,
                        "onBannerShow",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClose(ATAdInfo entity) {
                Log.i("BannerAdActivity", "onBannerClose:"  + entity.toString());
                Toast.makeText(BannerAdActivity.this,
                        "onBannerClose",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerAutoRefreshed(ATAdInfo entity) {
                Log.i("BannerAdActivity", "onBannerAutoRefreshed:"  + entity.toString());
            }

            @Override
            public void onBannerAutoRefreshFail(AdError adError) {
                Log.i("BannerAdActivity", "onBannerAutoRefreshFail");

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                BannerAdActivity.this, android.R.layout.simple_spinner_dropdown_item,
                unitGroupName);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(BannerAdActivity.this,
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                mCurrentSelectIndex = position;
                mBannerView.setPlacementId(placementIds[mCurrentSelectIndex]);
                mBannerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        findViewById(R.id.loadAd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> maps = new HashMap<>();
                //since v5.6.5
                Map<String, Object> localExtra = new HashMap<>();
                localExtra.put(AdmobATConst.ADAPTIVE_TYPE, AdmobATConst.ADAPTIVE_ANCHORED);
                localExtra.put(AdmobATConst.ADAPTIVE_ORIENTATION, AdmobATConst.ORIENTATION_CURRENT);
//                localExtra.put(AdmobATConst.INLINE_ADAPTIVE_ORIENTATION, AdmobATConst.ORIENTATION_PORTRAIT);
//                localExtra.put(AdmobATConst.INLINE_ADAPTIVE_ORIENTATION, AdmobATConst.ORIENTATION_LANDSCAPE);
                localExtra.put(AdmobATConst.ADAPTIVE_WIDTH, getResources().getDisplayMetrics().widthPixels);
                mBannerView.setLocalExtra(localExtra);

                mBannerView.loadAd();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBannerView != null) {
            mBannerView.destroy();
        }
    }

    public int dip2px(float dipValue) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
