package test.bwei.com.jd.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import test.bwei.com.jd.Adapter.FatherAdapter;
import test.bwei.com.jd.Api;
import test.bwei.com.jd.Bean.KCbean;
import test.bwei.com.jd.Bean.Kbean1;
import test.bwei.com.jd.Bean.KindChileBean;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/10/10.
 */

public class FramgentKind extends Fragment{


    private View v;
    private String cid;
    private List<Kbean1> klist;
    private RecyclerView rcl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = View.inflate(getActivity(), R.layout.fragmentkind,null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b=getArguments();
        cid = b.getString("cid");
        initView();
        initData();
    }

    private void initData() {
        OkHttpClient ok=new OkHttpClient();
        FormBody.Builder f=new FormBody.Builder();
        f.add("cid",cid);
        FormBody formBody=f.build();
        Request request=new Request.Builder().post(formBody).url(Api.ChildKind).build();
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson=new Gson();
                KindChileBean kindChileBean = gson.fromJson(s, KindChileBean.class);
                String code = kindChileBean.code;
                if(code!=null&&code.equals("0")){

                    List<KindChileBean.DataBean> data = kindChileBean.data;
                    klist=new ArrayList<Kbean1>();
                    for (KindChileBean.DataBean dataBean : data) {
                        Kbean1 kbean1=new Kbean1();
                        String name = dataBean.name;
                        List<KindChileBean.DataBean.ListBean> list = dataBean.list;
                        kbean1.list=list;
                        kbean1.name=name;
                        klist.add(kbean1);
                    }
                    System.out.println(klist.size()+"%%%%%%%%%%%5");
                    final FatherAdapter fatherAdapter=new FatherAdapter(getActivity(),klist);
                    if(getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rcl.setLayoutManager(new LinearLayoutManager(getActivity()));
                                rcl.setAdapter(fatherAdapter);
                            }
                        });
                    }


                }
            }
        });
    }

    private void initView() {
        rcl = v.findViewById(R.id.father);
    }
}
