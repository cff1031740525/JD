package test.bwei.com.jd;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
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
import test.bwei.com.jd.Bean.GoodsDetails;
import test.bwei.com.jd.Fragment.Fragment6;
import test.bwei.com.jd.Fragment.Framgentweb;
import test.bwei.com.jd.Fragment.GoodsDetailsFrag;

public class GoodsDetail extends AppCompatActivity {

    private ImageView img;
    private ViewPager vp;
    private TabLayout tab;
    private List<GoodsDetails> list;
    private List<String> na=new ArrayList<>();
    private String pid;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        getSupportActionBar().hide();
        na.add("商品");
        na.add("详情");
        na.add("评价");

        initView();
        initData();
    }

    private void initData() {

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        if(!TextUtils.isEmpty(pid)){
            OkHttpClient okHttpClient=new OkHttpClient();
            FormBody.Builder builder=new FormBody.Builder();
            builder.add("pid", pid);
            FormBody formBody=builder.build();
            Request request=new Request.Builder().post(formBody).url(Api.GOODSDETAILS).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    s = response.body().string();
                    System.out.println(s +"+++++++++++++");
                    if(s !=null){
                        Gson gson=new Gson();
                        final GoodsDetails goodsDetails = gson.fromJson(s, GoodsDetails.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                vp.setAdapter(new FragmentAdapter(getSupportFragmentManager(),goodsDetails,GoodsDetail.this));
                                tab.setupWithViewPager(vp);
                            }
                        });

                    }

                }
            });
        }else{
            Toast.makeText(GoodsDetail.this,"接口没有数据",Toast.LENGTH_SHORT).show();
        }


    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        img = (ImageView) findViewById(R.id.fx);
        vp = (ViewPager) findViewById(R.id.vp);
        tab.setTabMode(TabLayout.MODE_FIXED);



    }
    class FragmentAdapter extends FragmentPagerAdapter{
        GoodsDetails goodsDetails;
        Context context;
        public FragmentAdapter(FragmentManager fm,GoodsDetails goodsDetails, Context context) {
            super(fm);
            this.context=context;
            this.goodsDetails=goodsDetails;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment =null;
            switch (position){
                case 0:
                fragment=new GoodsDetailsFrag(context,goodsDetails);
                    Bundle bundle=new Bundle();
                    bundle.putString("json",s);
                    fragment.setArguments(bundle);
                break;
                case 1:
                   fragment=new Framgentweb(context,goodsDetails);
                    Bundle bundles=new Bundle();
                    bundles.putString("json",s);
                    fragment.setArguments(bundles);
                    break;
                case 2:
                    fragment=new Fragment6();
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return na.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
