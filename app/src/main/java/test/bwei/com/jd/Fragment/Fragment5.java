package test.bwei.com.jd.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.bwei.com.jd.Api;
import test.bwei.com.jd.Bean.User;
import test.bwei.com.jd.LoginActivity;
import test.bwei.com.jd.R;
import test.bwei.com.jd.UserInfoActivity;

/**
 * Created by C on 2017/9/29.
 */

public class Fragment5 extends Fragment {

    private View v;
    private LinearLayout line5;
    private ImageView img;
    private TextView tv;
    private SharedPreferences sp;
    private SharedPreferences loginstatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = View.inflate(getActivity(), R.layout.fragment5, null);
        sp = getActivity().getSharedPreferences("user_uid", Context.MODE_PRIVATE);
        loginstatus = getActivity().getSharedPreferences("loginstatus", Context.MODE_PRIVATE);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {

        if (!TextUtils.isEmpty(sp.getString("uid", null))) {
            String uid = sp.getString("uid", null);
            OkHttpClient ok = new OkHttpClient();
            FormBody.Builder bulider = new FormBody.Builder();
            bulider.add("uid", uid);
            FormBody formBody = bulider.build();
            Request request = new Request.Builder().post(formBody).url(Api.USERINFO).build();
            ok.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    Gson gson = new Gson();
                    final User user = gson.fromJson(s, User.class);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(user.data.nickname);
                                System.out.println(user.data.icon);
                                String s = (String) user.data.icon;
                                Glide.with(getActivity()).load(s).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true) .dontAnimate().into(img);
                            }
                        });

                    }


                }
            });
        }


    }

    private void initView() {
        img = v.findViewById(R.id.limg);
        tv = v.findViewById(R.id.name);

        line5 = v.findViewById(R.id.line5);
        line5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginstatus.getBoolean("flag", true)) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    getActivity().startActivity(new Intent(getActivity(), UserInfoActivity.class));
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
