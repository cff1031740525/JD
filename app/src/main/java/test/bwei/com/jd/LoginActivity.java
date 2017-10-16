package test.bwei.com.jd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.bwei.com.jd.Bean.LoginBean;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user;
    private EditText psd;
    private Button login;
    private Button reg;
    private SharedPreferences sp;
    private SharedPreferences loginstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("user_uid", MODE_PRIVATE);
        loginstatus = getSharedPreferences("loginstatus", MODE_PRIVATE);
        initView();
        initData();
    }

    private void initView() {
        user = (EditText) findViewById(R.id.et_user);
        psd = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.lbtn);
        reg = (Button) findViewById(R.id.lreg);
        login.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lbtn:
                if (TextUtils.isEmpty(user.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(psd.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpClient okHttpClient=new OkHttpClient();
                FormBody.Builder builder=new FormBody.Builder();
                builder.add("mobile",user.getText().toString());
                builder.add("password",psd.getText().toString());
                FormBody formBody=builder.build();
                Request request=new Request.Builder().url(Api.LOGIN).post(formBody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        System.out.println(s+"]]]]]]]]]]]]");
                        Gson gson=new Gson();
                        final LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if("0".equals(loginBean.code)){
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    sp.edit().putString("uid",loginBean.data.uid+"").commit();
                                    System.out.println(loginBean.data.uid+"[[[[[[[[[[[[[[");
                                    loginstatus.edit().putBoolean("flag",false).commit();
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.lreg:
                break;
        }
    }
}
