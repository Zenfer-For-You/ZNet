package com.zenfer.znet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.zenfer.network.error.NetwordException;
import com.zenfer.znet.api.employer.EmployerApiEnum;
import com.zenfer.znet.api.employer.EmployerApiSelector;
import com.zenfer.znet.bean.CompanyBaseBean;
import com.zenfer.znet.bean.NetWordResult;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EmployerApiSelector.get(EmployerApiEnum.GETCOMPANYLIST, null)
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<NetWordResult<List<CompanyBaseBean>>, List<CompanyBaseBean>>() {
                                @Override
                                public List<CompanyBaseBean> apply(NetWordResult<List<CompanyBaseBean>> result) throws Exception {
                                    if (result.getStatus_code() == 200) {
                                        return result.getData();
                                    } else if (result.getStatus_code() == 666) {
                                        //请求失败,需要解析data的数据
                                        String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
                                        int errorCode = result.getStatus_code();
                                        throw new NetwordException(null, errorCode, message);
                                    } else {
                                        //请求失败
                                        String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
                                        int errorCode = result.getStatus_code();
                                        throw new NetwordException(null, errorCode, message);
                                    }
                                }
                            })
                            .subscribe(new DisposableObserver<List<CompanyBaseBean>>() {
                                @Override
                                public void onNext(List<CompanyBaseBean> companyBaseBeans) {
                                    Log.d("zenfer", "result = " + companyBaseBeans.toString());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("zenfer", "Throwable = " + e.getMessage());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                EmployerApiRequest.getCompanyList(new BaseCallBack<NetWordResult>() {
//                    @Override
//                    public void onSuccess(String tag, NetWordResult result) {
//                        Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFail(String tag, NetwordException msg) {
//                        Toast.makeText(MainActivity.this, msg.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onBegin(String tag) {
//
//                    }
//
//                    @Override
//                    public void onEnd(String tag) {
//
//                    }
//                });
            }
        });

    }
}
