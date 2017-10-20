package test.bwei.com.jd.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Streams;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.bwei.com.jd.Adapter.CartFatherAdapter;
import test.bwei.com.jd.Api;
import test.bwei.com.jd.ArithUtils;
import test.bwei.com.jd.Bean.CartBean;
import test.bwei.com.jd.Bean.UpdateBean;
import test.bwei.com.jd.R;
import test.bwei.com.okhttputils.OkCallback;
import test.bwei.com.okhttputils.OkHttpMethod;
import test.bwei.com.okhttputils.OkhttpUtils;

/**
 * Created by C on 2017/9/29.
 */

public class Fragment4 extends Fragment {

    private View v;
    private CheckBox cb;
    private TextView seller;
    private RecyclerView rlv;
    private SharedPreferences sp;
    private String uid;
    private CartFatherAdapter adapter;
    private CheckBox sumcb;
    private List<CartBean.DataBean> data;
    private double sum;
    private TextView sumprice;
    private List<UpdateBean> uplist;
    int cs = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = View.inflate(getActivity(), R.layout.cartfather, null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sp = getActivity().getSharedPreferences("user_uid", Context.MODE_PRIVATE);
        uid = sp.getString("uid", null);
        initView();
        initData();

    }



    private void initData() {
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(getActivity(), "请先登录再查看购物车", Toast.LENGTH_SHORT).show();
            return;
        }
        OkhttpUtils utils = OkhttpUtils.getOkhttpInstance(getActivity());
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        utils.call(OkHttpMethod.POST, Api.GETCART, map, new OkCallback() {
            @Override
            public void onFailure(String e, String msg) {

            }

            @Override
            public void onResponse(String result) {
                System.out.println(result + "cart+++++++++++++++");
                if (result.equals("null")) {
                    Toast.makeText(getActivity(), "购物车为空", Toast.LENGTH_SHORT).show();

                } else {
                    uplist = new ArrayList<>();
                    UpdateBean bean;
                    CartBean cartBean = new Gson().fromJson(result, CartBean.class);
                    data = cartBean.data;
                    adapter = new CartFatherAdapter(data, getActivity());

                    adapter.setFpriceListener(new CartFatherAdapter.FPriceListener() {
                        @Override
                        public void SumPrices() {
                            double price=0;
                            for (CartBean.DataBean dataBean : data) {
                                for (CartBean.DataBean.ListBean listBean : dataBean.list) {
                                    if(listBean.selected==1){
                                        double bargainPrice = (double)listBean.bargainPrice;
                                        double num =(double) listBean.num;
                                        double v = bargainPrice * num;
                                        price=price+v;

                                    }

                                }

                            }
                            DecimalFormat    df   = new DecimalFormat("######0.00");
                            sumprice.setText("¥ "+df.format(price));
                        }
                    });
                    for (CartBean.DataBean dataBean : data) {
                        List<CartBean.DataBean.ListBean> list = dataBean.list;
                        for (int i = 0; i < list.size(); i++) {
                            bean = new UpdateBean();
                            bean.num = list.get(i).num + "";
                            bean.pid = list.get(i).pid + "";
                            bean.price = list.get(i).bargainPrice + "";
                            bean.sellerid = list.get(i).sellerid + "";
                            bean.Selected = list.get(i).selected + "";
                            bean.uid = uid;
                            uplist.add(bean);
                        }


                    }
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rlv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                rlv.setAdapter(adapter);
                            }
                        });
                    }

                }
            }
        });

    }


    private void initView() {
        rlv = v.findViewById(R.id.frlv);
        sumcb = v.findViewById(R.id.sumcb);
        sumprice = v.findViewById(R.id.sumprice);
        sumcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OkhttpUtils utils = OkhttpUtils.getOkhttpInstance(getActivity());
                if (isChecked) {

                    for (UpdateBean bean : uplist) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("uid", bean.uid);
                        map.put("sellerid", bean.sellerid);
                        map.put("pid", bean.pid);
                        map.put("num", bean.num);
                        map.put("selected", "1");
                        utils.call(OkHttpMethod.POST, Api.CHANGECART, map, new OkCallback() {
                            @Override
                            public void onFailure(String e, String msg) {

                            }

                            @Override
                            public void onResponse(String result) {

                            }
                        });
                        try {
                            new Thread().sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                    for (UpdateBean bean : uplist) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("uid", bean.uid);
                        map.put("sellerid", bean.sellerid);
                        map.put("pid", bean.pid);
                        map.put("num", bean.num);
                        map.put("selected", "0");
                        utils.call(OkHttpMethod.POST, Api.CHANGECART, map, new OkCallback() {
                            @Override
                            public void onFailure(String e, String msg) {

                            }

                            @Override
                            public void onResponse(String result) {

                            }
                        });
                        try {
                            new Thread().sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                initData();
            }
        });
    }
}
