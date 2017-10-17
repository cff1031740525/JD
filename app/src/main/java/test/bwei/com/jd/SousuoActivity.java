package test.bwei.com.jd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.OkHttpClient;

public class SousuoActivity extends AppCompatActivity {

    private Button btn;
    private EditText ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        initViwe();
        initData();
    }

    private void initData() {
        String s = ss.getText().toString();
        if(TextUtils.isEmpty(s)){
            Toast.makeText(SousuoActivity.this,"搜索内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void initViwe() {
        btn = (Button) findViewById(R.id.search);
        ss = (EditText) findViewById(R.id.xinss);
    }
}
