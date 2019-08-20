package com.zenfer.znet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zenfer.network.error.NetwordException;
import com.zenfer.znet.api.callback.BaseCallBack;
import com.zenfer.znet.api.callback.NetworkCallBack;
import com.zenfer.znet.api.common.CommonApiRequest;
import com.zenfer.znet.bean.Pages;
import com.zenfer.znet.bean.RevenueAndExpenditureBean;

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
            }
        });

    }
}
