package test.bwei.com.jd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.bwei.com.jd.Adapter.GridetAdapter;
import test.bwei.com.jd.Adapter.ProductAdapter;
import test.bwei.com.jd.Bean.ProductInfo;

public class GoodInfoActivity extends AppCompatActivity {

    private TextView zh;
    private TextView xl;
    private TextView jg;
    private RecyclerView rlv;
    private String sort;
    private List<ProductInfo.DataBean> data;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_info);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String psdcid = intent.getStringExtra("pscid");
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("pscid", psdcid);
        if (!TextUtils.isEmpty(sort)) {
            builder.add("sort", sort);
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().post(formBody).url(Api.GOODSINFO).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (!TextUtils.isEmpty(s)) {
                    Gson gson = new Gson();
                    ProductInfo productInfo = gson.fromJson(s, ProductInfo.class);
                    data = new ArrayList<>();
                    data = productInfo.data;
                    final ProductAdapter adapter = new ProductAdapter(data, GoodInfoActivity.this);
                    adapter.setOnclickListener(new ProductAdapter.OnclickListener() {
                        @Override
                        public void itemOnclick(View v, String pid) {
                            Intent intent = new Intent(GoodInfoActivity.this, GoodsDetail.class);
                            intent.putExtra("pid",pid);
                            Toast.makeText(GoodInfoActivity.this,pid,Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rlv.setLayoutManager(new LinearLayoutManager(GoodInfoActivity.this, LinearLayoutManager.VERTICAL, false));
                            rlv.setAdapter(adapter);
                            status = 1;
                        }
                    });

                }


            }
        });
    }

    private void initView() {
        zh = (TextView) findViewById(R.id.zh);
        xl = (TextView) findViewById(R.id.xl);
        jg = (TextView) findViewById(R.id.jg);
        rlv = (RecyclerView) findViewById(R.id.girlv);
    }

    public void ggg(View view) {
        if (status == 1) {
            GridetAdapter adapter = new GridetAdapter(data, GoodInfoActivity.this);
            rlv.setLayoutManager(new GridLayoutManager(GoodInfoActivity.this, 2));
            rlv.setAdapter(adapter);
            status = 0;
        } else {
            ProductAdapter adapter = new ProductAdapter(data, GoodInfoActivity.this);
            rlv.setLayoutManager(new LinearLayoutManager(GoodInfoActivity.this, LinearLayoutManager.VERTICAL, false));
            rlv.setAdapter(adapter);
            status = 1;
        }

    }
}
