package test.bwei.com.jd;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.bwei.com.jd.Adapter.GridetAdapter;
import test.bwei.com.jd.Adapter.ProductAdapter;
import test.bwei.com.jd.Bean.ProductInfo;
import test.bwei.com.okhttputils.OkCallback;
import test.bwei.com.okhttputils.OkHttpMethod;
import test.bwei.com.okhttputils.OkhttpUtils;

public class GoodInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView zh;
    private TextView xl;
    private TextView jg;
    private RecyclerView rlv;
    private String sort;
    private List<ProductInfo.DataBean> data;
    private int status = 0;
    private String psdcid;
    private boolean flag=false;
    private EditText sousuo;
    private Intent intent;
    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_info);
        getSupportActionBar().hide();
        intent = getIntent();
        psdcid = intent.getStringExtra("pscid");
        initView();
        initData();
    }

    private void initData() {

        String json = intent.getStringExtra("mingzi");
        if(!TextUtils.isEmpty(json)){
            OkhttpUtils utils=OkhttpUtils.getOkhttpInstance(GoodInfoActivity.this);
            Map<String,Object> parms=new HashMap<>();
            parms.put("keywords",json);
            if(sort!=null){
                parms.put("sort",sort);
            }
            if(page!=null){
                parms.put("page",page);
            }
            utils.call(OkHttpMethod.POST, Api.SEARCH, parms, new OkCallback() {
                @Override
                public void onFailure(String e, String msg) {

                }

                @Override
                public void onResponse(String result) {
                    ProductInfo productInfo = new Gson().fromJson(result, ProductInfo.class);
                    data = new ArrayList<>();
                    data = productInfo.data;
                    final ProductAdapter adapter = new ProductAdapter(data, GoodInfoActivity.this);
                    adapter.setOnclickListener(new ProductAdapter.OnclickListener() {
                        @Override
                        public void itemOnclick(View v, String pid) {
                            Intent intent = new Intent(GoodInfoActivity.this, GoodsDetail.class);
                            intent.putExtra("pid", pid);
                            Toast.makeText(GoodInfoActivity.this, pid, Toast.LENGTH_SHORT).show();
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
            });


        }else{


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
                            intent.putExtra("pid", pid);
                            Toast.makeText(GoodInfoActivity.this, pid, Toast.LENGTH_SHORT).show();
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
    }

    private void initView() {
        zh = (TextView) findViewById(R.id.zh);
        xl = (TextView) findViewById(R.id.xl);
        jg = (TextView) findViewById(R.id.jg);
        sousuo = (EditText) findViewById(R.id.ss);
        zh.setOnClickListener(this);
        xl.setOnClickListener(this);
        jg.setOnClickListener(this);
        zh.setTextColor(Color.RED);
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
            rlv.setLayoutManager(new LinearLayoutManager(GoodInfoActivity.this, LinearLayoutManager.VERTICAL, flag));
            rlv.setAdapter(adapter);
            status = 1;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zh:
                sort=0+"";
                initData();
                zh.setTextColor(Color.RED);
                xl.setTextColor(Color.BLACK);
                jg.setTextColor(Color.BLACK);
                break;
            case R.id.xl:
                sort=1+"";
                initData();
                xl.setTextColor(Color.RED);
                zh.setTextColor(Color.BLACK);
                jg.setTextColor(Color.BLACK);
                break;
            case R.id.jg:
                sort=2+"";
                if(flag==false){
                    flag=true;
                }
                initData();
                jg.setTextColor(Color.RED);
                xl.setTextColor(Color.BLACK);
                zh.setTextColor(Color.BLACK);
                break;
        }
    }
}
