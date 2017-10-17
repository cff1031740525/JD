package test.bwei.com.jd.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import test.bwei.com.jd.Bean.BannerBean;
import test.bwei.com.jd.Bean.GoodsDetails;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/10/13.
 */

public class GoodsDetailsFrag extends Fragment{
    private Context context;
    private GoodsDetails details;
    private XBanner banner;
    private TextView title;
    private TextView price;
    private TextView ctitle;
    private List<String> imglist=new ArrayList<>();
    public GoodsDetailsFrag(Context context, GoodsDetails details) {
        this.context = context;
        this.details = details;
    }

    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = View.inflate(getActivity(), R.layout.goodsdetails,null);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();


    }

    private void initData() {
        Bundle bundle=getArguments();
        String json = bundle.getString("json", null);
        GoodsDetails goodsDetails = new Gson().fromJson(json, GoodsDetails.class);
        String images = goodsDetails.data.images;
        String[] split = images.split("\\|");
        for (int i=0;i<split.length;i++){
            imglist.add(split[i]);
        }
        banner.setData(imglist,null);
        banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(imglist.get(position)).into((ImageView) view);
            }
        });
        title.setText(goodsDetails.data.title);
        ctitle.setText(goodsDetails.data.subhead);
        price.setText("¥  "+goodsDetails.data.price+"");
    }

    private void initView() {
        banner = v.findViewById(R.id.gdbanner);
        title = v.findViewById(R.id.detailtitle);
        price = v.findViewById(R.id.detailprice);
        ctitle = v.findViewById(R.id.childtitle);
    }
}
