package test.bwei.com.jd.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.bwei.com.jd.Adapter.KindAdapter;
import test.bwei.com.jd.Api;
import test.bwei.com.jd.Bean.KBean;
import test.bwei.com.jd.Bean.KindBean;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/9/29.
 */

public class Fragment2 extends Fragment {

    private View v;
    private List<KBean> klist;
    private RecyclerView krlv;
    private FrameLayout frame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = View.inflate(getActivity(), R.layout.fragment2, null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        krlv = v.findViewById(R.id.krlv);
        FramgentKind f=new FramgentKind();
        Bundle bundle=new Bundle();
        bundle.putString("cid","1");
        f.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.kindframe,f).commit();
    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Api.Kind).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                System.out.println(s+"++++++++++++++");
                Gson gson = new Gson();
                KindBean kindBean = gson.fromJson(s, KindBean.class);
                List<KindBean.DataBean> data = kindBean.data;
                klist = new ArrayList<>();
                for (KindBean.DataBean dataBean : data) {
                    KBean kb = new KBean();
                    kb.cid = dataBean.cid;
                    kb.name = dataBean.name;
                    klist.add(kb);
                }

                final KindAdapter ada = new KindAdapter(klist, getActivity());
                ada.setKindOnclick(new KindAdapter.KindOnclick() {
                    @Override
                    public void KinditemOnclick(View v, int postion) {
                        FramgentKind framgentKind=new FramgentKind();
                        ada.changerposion(postion);
                        Bundle bundle=new Bundle();
                        bundle.putString("cid",klist.get(postion).cid+"");
                        framgentKind.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.kindframe,framgentKind).commit();
                    }
                });
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            krlv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            krlv.setAdapter(ada);
                        }
                    });
                }


            }
        });
    }

}
