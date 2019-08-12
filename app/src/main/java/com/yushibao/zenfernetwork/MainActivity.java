package com.yushibao.zenfernetwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yushibao.zenfernetwork.api.employer.ApiRequest;
import com.zenfer.network.bean.NetWordResult;
import com.zenfer.network.framwork.NetWorkCallBack;
import com.zenfer.network.framwork.NetwordException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequest.getCompanyList(new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
                    @Override
                    public void onSuccess(String tag, NetWordResult result) {
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
