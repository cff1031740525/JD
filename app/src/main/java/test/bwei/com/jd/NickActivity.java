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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NickActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick);
        sp = getSharedPreferences("user_uid", MODE_PRIVATE);
        final Intent intent = getIntent();
        String nick = intent.getStringExtra("nick");
        final EditText ed= (EditText) findViewById(R.id.et_nickname);
        Button btn= (Button) findViewById(R.id.btn);
        ed.setText(nick);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ed.getText().toString();
                if(TextUtils.isEmpty(s)){
                    Toast.makeText(NickActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("uid", sp.getString("uid", null));
                builder.add("nickname",s );
                FormBody formBody = builder.build();
                Request request = new Request.Builder().url(Api.UPDATANICKNAME).post(formBody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NickActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
                finish();
            }
        });

    }
}
