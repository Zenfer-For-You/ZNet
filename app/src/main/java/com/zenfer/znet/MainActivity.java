package com.zenfer.znet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zenfer.network.error.NetwordException;
import com.zenfer.znet.api.callback.BaseCallBack;
import com.zenfer.znet.api.callback.NetworkCallBack;
import com.zenfer.znet.api.common.CommonApiRequest;
import com.zenfer.znet.api.employer.EmployerApiRequest1;
import com.zenfer.znet.bean.CompanyBaseBean;
import com.zenfer.znet.bean.Pages;
import com.zenfer.znet.bean.RevenueAndExpenditureBean;

import java.util.List;
import java.util.Map;

import static com.zenfer.znet.api.NetCommonParams.commonObjectParam;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = commonObjectParam();
                map.put("page", 1);
                CommonApiRequest.getRechargeList(map, new NetworkCallBack<>(new BaseCallBack<Pages<RevenueAndExpenditureBean>>() {
                    @Override
                    public void onSuccess(String tag, Pages<RevenueAndExpenditureBean> result) {
                        Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String tag, NetwordException msg) {
                        Toast.makeText(MainActivity.this, msg.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBegin(String tag) {

                    }

                    @Override
                    public void onEnd(String tag) {

                    }
                }));
                EmployerApiRequest1.getCompanyList(new BaseCallBack<List<CompanyBaseBean>>() {
                    @Override
                    public void onSuccess(String tag, List<CompanyBaseBean> result) {
                        Toast.makeText(MainActivity.this, result.toArray().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String tag, NetwordException msg) {
                        Toast.makeText(MainActivity.this, msg.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBegin(String tag) {

                    }

                    @Override
                    public void onEnd(String tag) {

                    }
                });
//                try {
//                    EmployerApiSelector.get(EmployerApiEnum.GETCOMPANYLIST, null)
//                            .subscribeOn(Schedulers.io())
//                            .unsubscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .map(new NetworkResultFunction<List<CompanyBaseBean>>())
//                            .subscribe(new DisposableObserver<List<CompanyBaseBean>>() {
//                                @Override
//                                protected void onStart() {
//                                    super.onStart();
//                                }
//
//                                @Override
//                                public void onNext(List<CompanyBaseBean> companyBaseBeans) {
//                                    Log.d("zenfer", "result = " + companyBaseBeans.toArray());
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    Log.d("zenfer", "Throwable = " + e.getMessage());
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });

    }
}
