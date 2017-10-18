package test.bwei.com.jd.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.bwei.com.jd.Adapter.MiaoshaAdapter;
import test.bwei.com.jd.Adapter.SYAdapter;
import test.bwei.com.jd.Api;
import test.bwei.com.jd.Bean.BannerBean;
import test.bwei.com.jd.Bean.Kind;
import test.bwei.com.jd.Bean.KindBean;
import test.bwei.com.jd.Bean.Sybean;
import test.bwei.com.jd.GoodsDetail;
import test.bwei.com.jd.R;
import test.bwei.com.jd.SousuoActivity;

/**
 * Created by C on 2017/9/29.
 */

public class Fragment1 extends Fragment {

    private View v;
    private XBanner banner;
    private List<String> bannerInfo;
    private List<Sybean> tjinfo;
    private List<Kind> kindlist;
    private RecyclerView rlv;
    private SwipeRefreshLayout sw;
    private View vv;
    private View v2;
    private ViewPager vp;
    private SwipeRefreshLayout sw1;
    private RecyclerView miaoshas;
    private TextView hour;
    private TextView minute;
    private TextView seconds;
    private int xiaoshi=1;
    private int fenzhong=60;
    private int miao=59;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = View.inflate(getActivity(), R.layout.fragment1, null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        initData();
    }

    private void initData() {

        OkHttpClient okhttpclient = new OkHttpClient();
        final Request request = new Request.Builder().url(Api.BannerInfo).build();
        okhttpclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                BannerBean bannerBean = gson.fromJson(s, BannerBean.class);
                final List<BannerBean.DataBean> data = bannerBean.data;
                BannerBean.MiaoshaBean miaosha = bannerBean.miaosha;
                List<BannerBean.MiaoshaBean.ListBeanX> list1 = miaosha.list;

                final MiaoshaAdapter miaoshaAdapter=new MiaoshaAdapter(list1,getActivity());
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            miaoshas.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                            miaoshas.setAdapter(miaoshaAdapter);
                        }
                    });

                }

                bannerInfo = new ArrayList<>();
                tjinfo = new ArrayList<>();

                for (BannerBean.DataBean dataBean : data) {
                    bannerInfo.add(dataBean.icon);
                }
                BannerBean.TuijianBean tuijian = bannerBean.tuijian;
                List<BannerBean.TuijianBean.ListBean> list = tuijian.list;
                for (BannerBean.TuijianBean.ListBean listBean : list) {
                    Sybean sybean = new Sybean();
                    sybean.price = listBean.price;
                    sybean.pid=listBean.pid+"";
                    sybean.title = listBean.title;
                    String images = listBean.images;
                    String[] split = images.split("\\|");
                    sybean.img = split[0];
                    tjinfo.add(sybean);
                }

                banner.setData(bannerInfo, null);
                if(getActivity()!=null){
                    banner.setmAdapter(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                            Glide.with(getActivity()).load(bannerInfo.get(position)).into((ImageView) view);
                        }
                    });

                }

                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SYAdapter adapter = new SYAdapter(getActivity(), tjinfo);
                            rlv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            RecyclerViewHeader header = new RecyclerViewHeader(getActivity());
                            //添加头布局
                            header.attachTo(rlv);
                            header.addView(vv);
                            rlv.setAdapter(adapter);
                            adapter.setOnItemClick(new SYAdapter.itemClick() {
                                @Override
                                public void ItemOnclick(View v, int postion) {
                                    String pid = tjinfo.get(postion).pid;
                                    Intent intetn=new Intent(getActivity(), GoodsDetail.class);
                                    intetn.putExtra("pid",pid);
                                    startActivity(intetn);
                                }
                            });
                        }
                    });
                }


            }
        });

        final Request request1 = new Request.Builder().url(Api.Kind).build();
        okhttpclient.newCall(request1).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ss = response.body().string();
                Gson gson = new Gson();
                KindBean kindBean = gson.fromJson(ss, KindBean.class);
                List<KindBean.DataBean> data = kindBean.data;
                kindlist = new ArrayList<>();
                for (KindBean.DataBean dataBean : data) {
                    Kind kind = new Kind();
                    kind.cid = dataBean.cid;
                    kind.name = dataBean.name;
                    kind.icon = dataBean.icon;
                    kind.ishome = dataBean.ishome;
                    kindlist.add(kind);
                }
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            vp.setAdapter(new ada());
                        }
                    });

                }

            }
        });
    }

    private void initView() {
        rlv = v.findViewById(R.id.rlv);
        vv = View.inflate(getActivity(), R.layout.lunbo, null);
        banner = vv.findViewById(R.id.banner);
        vp = vv.findViewById(R.id.vp);
        sw1 = v.findViewById(R.id.sw);
        hour = vv.findViewById(R.id.hour);
        minute = vv.findViewById(R.id.minute);
        seconds = vv.findViewById(R.id.second);
        miaoshas = vv.findViewById(R.id.miaosha);
        editText = v.findViewById(R.id.shouyess);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SousuoActivity.class);
                startActivity(intent);
            }
        });
        sw1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                sw1.setRefreshing(false);
            }
        });
    }
    Runnable second=new Runnable() {
        @Override
        public void run() {

        }
    };

    class ada extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

                View v = View.inflate(getActivity(), R.layout.vpitem, null);
            GridView gv = v.findViewById(R.id.gv);
            if(getActivity()!=null){
                gv.setAdapter(new gada());
            }

            container.addView(v);
            return v;
        }
    }

    class gada extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return kindlist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = View.inflate(getActivity(), R.layout.gridviewadapter, null);
            ImageView img = v.findViewById(R.id.kimg);
            TextView tv = v.findViewById(R.id.ktv);
            Glide.with(getActivity()).load(kindlist.get(i).icon).into(img);
            tv.setText(kindlist.get(i).name);
            return v;
        }
    }
}
