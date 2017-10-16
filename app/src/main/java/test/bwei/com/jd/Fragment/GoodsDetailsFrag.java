package test.bwei.com.jd.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import test.bwei.com.jd.Bean.GoodsDetails;
import test.bwei.com.jd.R;

/**
 * Created by C on 2017/10/13.
 */

public class GoodsDetailsFrag extends Fragment{
    private Context context;
    private GoodsDetails details;

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
    }
}
